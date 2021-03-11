## 线程池的使用 (ThreadPoolExecutor)

> 只有当任务都是同类型的并且相互独立时，线程池的性能才能达到最佳

#### 在任务与执行策略之间的隐性耦合

* 依赖性任务
* 使用线程封闭机制的任务
* 对响应时间敏感的任务
* 使用`ThreadLocal`的任务
 
线程饥饿死锁：每当提交一个有依赖性的Executor的任务时，都可能会出现线程饥饿死锁。

运行实现较长的任务：任务阻塞时间过长，即使不出现死锁，也会让线程池的响应性变得很糟。(限定任务等待资源的时间，而不要无限制的等待)

在平台类库大多数可阻塞方法中，都同时限定了限时版本和不限时版本，如 `Thread#join, BlockingQueue#put,Object#wait`等，如果等待超时可以把任务标识为失败，然后中止任务或者将任务重新放回队列以便随后执行。保证任务总能执行下去。
#### 设置线程池的大小
> 线程池的理想大小取决于被提交任务的类型以及所部署系统的特性。一般应该通过某种配置机制来提供，或者根据 `java.lang.Runtime.availableProcessors`来动态计算

* 如何正确设置线程池大小：CPU数量，内存大小，任务是计算密集型，I/O密集型还是都可，是否需要像JDBC连接这样稀缺的资源

* 如果执行不同类型任务，并且任务之间的行为相差很大，那么应该考虑使用多个线程池，每个线程池可以根据各自的工作负载来调整

* 对于计算密集型的任务，如果CPU个数为N，那么线程池大小为N+1时，通常能实现最优的利用率

* 对于I/O密集型的任务，线程池的规模应该更大。

* N(threads) = N(cpu数量) * U(期望的CPU利用率) * (1+w/c(任务等待时间/计算时间))
#### 配置 ThreadPoolExecutor
```java
public class ThreadPoolExecutor extends AbstractExecutorService {
    public ThreadPoolExecutor(int corePoolSize,
                                  int maximumPoolSize,
                                  long keepAliveTime,
                                  TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue,
                                  ThreadFactory threadFactory,
                                  RejectedExecutionHandler handler) {
           // ...
    }
}
```
BlockingQueue:保存等待执行的任务。
* 无界队列：LinkedBlockingQueue(构造函数时，不设置capacity,Integer#MAX_VALUE)
* 有界队列：ArrayBlockingQueue，有界的 LinkedBlockingQueue，PriorityBlockingQueue。
    有界队列有助于避免资源耗尽的情况，但是需要设置拒绝策略（队列饱和后，新来的任务如何处理）
* 同步移交：SynchronousQueue，不是一个真正的队列，而是一种在线程之间进行移交的机制。将一个元素放入这个队列中，必须有另一个线程正在等待接受这个元素，否则就会新建一个线程，或者被拒绝。所以只有线程池是无界的（maximumPoolSize为Interger#MAX_VALUE）或者可以拒绝任务时，这个队列才有实际价值。`java.util.concurrent.Executors.newCachedThreadPool()`

只有当任务相互独立时，为线程池或工作队列设置界限才是合理的。如果任务之间存在依赖性，那么有界线程池或队列就可能导致线程饥饿的死锁问题。

RejectedExecutionHandler：拒绝策略，当有界队列被填满后，或者某个任务被提交到一个已经关闭的Executor时 会用到拒绝策略
* AbortPolicy:`throw new RejectedExecutionException`
* DiscardPolicy:悄悄的直接抛弃该任务，也不抛异常，就直接没了
```java
public static class DiscardPolicy implements RejectedExecutionHandler {
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
    }
}
```
* DiscardOldestPolicy：抛弃最旧的任务。对于FIFO的队列会抛弃下一个将被执行的任务，然后尝试提交新的任务。如果是优先级队列使用这个策略会导致抛弃当前队列中优先级最高的任务。（最好不要将该策略与优先队列一起使用）
```java
public static class DiscardOldestPolicy implements RejectedExecutionHandler {
    
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            if (!e.isShutdown()) {
                e.getQueue().poll();
                e.execute(r);
            }
        }
    }
```
* CallerRunsPolicy:在调用execute的线程中执行该方法。将任务回退到调用者，降低新任务的流量
```java
public static class CallerRunsPolicy implements RejectedExecutionHandler {
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        if (!e.isShutdown()) {
            r.run();
        }
    }
}
```
通过Semaphore来限制任务的提交速率
`com.smallyuan.labs.concurrency.chapter8.BoundedExecutor`

ThreadFactory:线程工厂，每当线程池需要创建一个线程时，都是通过线程工厂方法`java.util.concurrent.ThreadFactory.newThread`。默认的线程工厂会创建一个新的，非守护的线程，并且不包含特殊的配置信息。
```java
public interface ThreadFactory {
    Thread newThread(Runnable r);
}
```
许多情况下需要使用定制的线程工厂方法，比如给线程取一个有意义的名称，用来解释线程的转储信息和错误日志
* 自定义线程工厂方法： `com.smallyuan.labs.concurrency.chapter8.CustomizeThreadFactory`
* 自定义线程：`com.smallyuan.labs.concurrency.chapter8.CustomizeThread`
* 如果应用程序中需要利用安全策略控制对某些特殊代码库的访问权限，可以通过`java.util.concurrent.Executors.PrivilegedThreadFactory`来定制自己的线程工厂。通过这种方式创建出来的线程将与创建PrivilegedThreadFactory的线程拥有相同的访问权限，AccessControlContext和ContextClassLoader。否则 线程池创建的线程将从需要新线程时调用execute或submit的客户端中继承访问权限。

* 在调用完构造函数后，依然可以通过setter来修改参数
* 如果不想要别人修改线程池参数，可以通过 `java.util.concurrent.Executors.unconfigurableExecutorService`来封装ExecutorService，这将只暴露ExecutorService的方法，而不能进行配置。

#### 扩展 ThreadPoolExecutor

```java
public class ThreadPoolExecutor extends AbstractExecutorService {
    protected void afterExecute(Runnable r, Throwable t) { }
    protected void beforeExecute(Thread t, Runnable r) { }
    protected void terminated() { }
}
```
`com.smallyuan.labs.concurrency.chapter8.TimingThreadPool`

#### 递归算法的并行性

> 如果循环中的迭代操作都是相互独立的，并且每个迭代操作执行的工作量比管理一个新任务时带来的开销更多，那么该串行循环就是个转为并行循环。

```java
public class SequentiallyToParallel {
    
    /** 串行执行 */
    void processSequentially(List<Element> elements){
        for (Element e : elements) {
            process(e);
        }
    }
    /** 并行执行 */
    void processParallal(List<Element> elements, ExecutorService exec){
        for (final Element e : elements) {
            exec.execute(() -> process(e));
        }
    }
}
```
> 将串行递归转换为并行递归
```java
public class RecursiveS2P {
    public<T> void sequentialRecursive(List<Node<T>> nodes, Collection<T> results) {
        for (Node<T> node : nodes) {
            results.add(node.compute());
            sequentialRecursive(node.getChildren(),results);
        }
    }
    
    public<T> void parallelRecursive(final Executor exec,List<Node<T>> nodes,final Collection<T> results) {
        for (final Node<T> n : nodes) {
            exec.execute(() -> results.add(n.compute()));
            parallelRecursive(exec, n.getChildren(),results);
        }
    }
    
    /** 通过shutdown + awaitTermination , 等待通过并行方法计算的结果 */
    public<T> Collection<T> getParallelResults(List<Node<T>> nodes){
        ExecutorService exec = Executors.newCachedThreadPool();
        Queue<T> resultQueue = new ConcurrentLinkedQueue<T>();
        parallelRecursive(exec, nodes, resultQueue);
        exec.shutdown();
        exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        return resultQueue;
    }
}

```









