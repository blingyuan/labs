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

#### 扩展 ThreadPoolExecutor

#### 递归算法的并行性











