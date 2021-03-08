###任务执行

```
大多数并发程序都是围绕“任务执行”来构造的：
    任务通常是一些抽象的且离散的工作单元。
    通过把应用程序的工作分解到多个任务中，可以简化程序的组织结构，提供一种自然的事务边界来优化错误恢复过程，以及提供一种自然的并行工作结构来提升并发性。
```

   ####1. 在线程中执行任务
   
  > 找出清晰的任务边界。 理想情况下，各个任务之间是相互独立的：任务并不依赖与其他任务的状态，结果或边界效应。大多数服务器应用程序都提供一种自然的任务边界选择方式：以独立的客户请求为边界。
  
  1. 串行执行任务： ` com.smallyuan.labs.concurrency.chapter6.SingleThreadWebServer `
  
  在web请求的处理中包含一组不同的运算 与 I/O 操作。可能阻塞的操作：处理套接字I/O以读取请求和写回响应，服务器处理
  文件I/O或者数据库请求。
  
  在单线程的服务器中，阻塞不仅会推迟当前请求的完成时间，而且还将彻底阻止等待中的请求被处理。如果阻塞时间过长，用户会认为服务器不可用，同时服务器的资源利用率非常低，因为在等待IO操作完成时，cpu处于空闲状态。
     
   2. 显式的为任务创建线程: `com.smallyuan.labs.concurrency.chapter6.ThreadPerTaskWebServer`
  
  * 任务处理过程从主线程中分离出来，使得主循环能够更快的重新等待下一个连接的到来，提高响应性。
  * 任务可以并行处理，能够同时服务多个请求，在多处理器的情况下，程序的吞吐量会有所提高。
  * 任务处理代码必须是线程安全的
   
   不足(无限制创建线程)：
   
   *  线程生命周期的开销非常高。 线程的创建和销毁都是需要时间的，同时需要JVM和操作系统提供一些辅助操作。如果请求的到达率很高且请求的处理过程是轻量级的，那么为每个请求创建一个新的线程会消耗大量的计算资源
   *  资源消耗。 活跃的线程会消耗系统资源，尤其是内存。可运行线程数量多于可用处理器的数量，那么会有线程闲置，占用内存，并且会导致CPU资源的竞争，产生其他性能开销
   *  稳定性。每个平台都会在可创建线程的数量上存在一个限制。这个限制值受多个因素的制约，包括 JVM启动参数，Thread构造函数中请求的栈大小等。如果超出限制很可能会抛出OOM。
   ####2. Executor框架
> 提供了一种标准的方法将任务的提交过程于执行过程解耦开来，并用Runnable来表示任务。 Executor的实现还提供了对生命周期的支持，以及统计信息收集，应用程序管理机制和性能监视等机制。
```
public interface Executor {
    void execute(Runnable command);
}
```
   基于Executor的Web服务器： `com.smallyuan.labs.concurrency.chapter6.TaskExecutionWebServer`
   
   * 执行策略： 各种执行策略都是一种资源管理工具，最佳策略取决于可用的计算资源以及对服务质量的需求。通过将任务的提交与任务的执行策略分离开来，有助于在部署阶段选择与可用硬件资源最匹配的执行策略。（what,where,when,how）
    
        * 在什么线程中执行任务（what）
        * 任务按照什么顺序执行（what，FIFO. LIFO. 优先级）
        * 有多少任务能并发执行（How Many）
        * 在任务队列中有多少个任务在等待执行（How Many）
        * 如果系统由于过载而需要拒绝一个任务，应该选择哪一个（Which），如何（How）通知应用程序有任务被拒绝
        * 在执行一个任务之前或之后，应该进行哪些动作（What）
        
   * 线程池： 线程池是与工作队列密切相关的。(Work Queue),在工作队列中保存了所有等待执行的任务。工作者线程(Work Thread)的任务很简单：从工作队列中获取一个任务，执行任务，返回线程池并等待下一个任务。
        1. 重用现有的线程，而不是创建新线程，可以在处理多个请求时分摊在线程创建和销毁过程中产生的巨大开销。 
        2. 当请求到达时，工作线程通常已经存在，因此不会由于等待创建线程而延迟任务的执行，提高了响应性。
        3. 通过适当调整线程池的大小，可以创建足够多的线程使得处理器保持忙碌状态，同时防止过多线程相互竞争使得应用程序耗尽内存或失败。
        
   * Executor的生命周期 （ExecutorService接口）
   > Executor的实现通常会创建线程来执行任务，但JVM只有在所有（非守护）线程全部终止后才会退出。因此，如果无法正确关闭Executor，JVM将无法结束。
   
   ```java
// 提供生命周期的支持， shutdown，shutdownNow
public interface ExecutorService extends Executor {
    void shutdown();
    List<Runnable> shutdownNow();
    boolean isShutdown();
    boolean isTerminated();
    boolean awaitTermination(long timeout, TimeUnit unit)
        throws InterruptedException;
    <T> Future<T> submit(Callable<T> task);
    <T> Future<T> submit(Runnable task, T result);
    Future<?> submit(Runnable task);
    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
        throws InterruptedException;
    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,
                                  long timeout, TimeUnit unit)
        throws InterruptedException;
    <T> T invokeAny(Collection<? extends Callable<T>> tasks)
        throws InterruptedException, ExecutionException;
    <T> T invokeAny(Collection<? extends Callable<T>> tasks,
                    long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
```
* ExecutorService的生命周期有三种状态： 运行，关闭 和 已终止。
    * ExecutorService在初始创建时处于运行状态
    * shutdown方法将执行平缓的关闭过程：不再接受新的任务，同时等待已经提交的任务完成--包括哪些还未开始执行的任务。
    * shutdownNow方法将执行粗暴的关闭过程：它将尝试取消所有运行中的任务，并且不再启动队列中尚未开始执行的任务。
    * 在ExecutorService关闭后提交的任务将由“拒绝执行处理器(Rejected Execution Handler)”来处理。它会抛弃任务，或者使得execute方法抛出一个未检查的RejectedExecutionExecption。等待所有任务完成后，ExecutorService将会转到终止状态。
    * 可以调用 awaitTermination 来等待ExecutorService到达中止状态，或者调用 isTerminated 来轮询 ExecutorService 是否已经终止。
    * 通常在调用awaitTermination之后会立即调用 shutdown，从而产生同步的关闭ExecutorService的效果
    
 * 延迟任务与周期任务
    * Timer类负责管理延迟任务 以及 周期任务，但存在一些缺陷。（Timer类执行基于绝对时间而不是相对时间的调度机制。）
        * Timer类在执行所有定时任务时只会创建一个线程。如果某个任务的执行时间过长，那么将破坏其他TimerTask的定时精确性。（固定速率/固定延时）
        * 如果TimerTask抛出了一个未检查异常时会终止定时线程。这种情况下，Timer不会恢复线程的执行，而是会错误的任务整个Timer都被取消了
    * 考虑使用 ScheduledThreadPoolExecutor来代替。（只支持基于相对时间的调度）
    * 构建自己的调度服务，可以使用DelayQueue，它实现了BlockingQueue。DelayQueue管理一组Delayed对象，在DelayQueue中，只有某个元素逾期后，才能从DelayQueue中执行take操作，从DelayQueue中返回的对象将根据他们的延迟时间进行排序。
    
   ####3. 找出可利用的并行性
  1. 串行的页面渲染器：
```java
public class SingleThreadRenderer {
    void renderPage(CharSequence source) {
        renderText(source);
        List<ImageData> imageData = new ArrayList<>();
        for(ImageInfo info : scanForImageInfo(source)) {
            imageData.add(info.downloadImage());
        }
        for(ImageData data : imageData) {
            renderImage(data);
        }
    }
}
```
    缺点：图片下载过程的大部分时间都是IO操作，这期间CPU几乎不工作，所以没有充分的利用CPU
    
2. 携带结果的任务Callable 和 Future

```java
@FunctionalInterface
public interface Callable<V> {
    V call() throws Exception;
}
public interface Future<V> {
    boolean cancel(boolean mayInterruptIfRunning);
    boolean isCancelled();
    boolean isDone();
    V get() throws InterruptedException, ExecutionException;
    V get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
```
* Callable: 主入口点将返回一个值，并可能抛出一个异常。在Executor中包含一些辅助方法能将其他类型的任务封装成一个Callable。例如 Runnable 和 java.security.PrivilegedAction
* Future表示一个任务的生命周期，并提供了响应的方法来判断是否已经完成或取消，以及获取任务的结果和取消任务等。
* get方法的行为取决于任务的状态（尚未开始，正在运行，已完成）。 如果任务已完成，get会立即返回或抛出一个异常。 如果没有完成，get将阻塞并直到任务完成。如果任务抛出了异常，get会将异常封装成ExecutionException重新抛出，可以通过getCause来获取被封装的初始异常。如果任务被取消，get将抛出CancellationException。
* 创建一个Future 来描述任务的方法： 
    1. ExecutorService#submit(callable/runnable)会返回一个Future，可以通过Future来获取任务执行结果或取消任务
    2. 显示的将指定的Runnable或Callable实例化为一个FutureTask
```java
public class FutureTask<V> implements RunnableFuture<V> {
}
public interface RunnableFuture<V> extends Runnable, Future<V> {
    void run();
}
public class ThreadPoolExecutor extends AbstractExecutorService {
}
public abstract class AbstractExecutorService implements ExecutorService {
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return new FutureTask<T>(runnable, value);
    }
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new FutureTask<T>(callable);
    }
}
```
改进renderPage，创建一个Callable来下载所有的图像，并将其提交到一个ExecutorService中。会返回一个Future，当主任务需要图像时通过get来获取结果。
使得渲染文本与下载图像数据的任务并发执行
```java
/**
* 使用 Future 等待图像下载
*/
public class FutureRenderer {
    private final ExecutorService exec = Executors.newFixedThreadPool(20);
    
    void renderPage(CharSequence source) {
        // 获取图片信息
        final List<ImageInfo> imageInfos = scanForImageInfo(source);
        // 封装成一个Callable的任务
        Callable<List<ImageData>> task = () -> {
            List<ImageData> result = new ArrayList<>();
            for (ImageInfo imageInfo : imageInfos) {
                // 下载图片
                result.add(imageInfo.downloadImage());
            }
            return result;
        };
        // 提交任务，返回一个Future
        Future<List<ImageData>> future = exec.submit(task);
        renderText(source);
        try{
            List<ImageData> imageDataList = future.get();
            for (ImageData imageData : imageDataList) {
                renderImage(data);
            }
        } catch (InterruptedException e) {
            // 重新设置线程的中断状态
            Thread.currentThread().interrupt();
            // 由于不需要结果，因此取消任务
            future.cancel(ture);
         
        } catch (ExecutionException e) {
             //抛出异常
        }
       
    } 
}
```
> get方法拥有“状态依赖”的内在特性，调用者不需要知道任务的状态，此外在任务提交和获取结果中包含安全发布属性也确保了这个方法是线程安全的。
> 只有当大量相互独立且同构的任务可以并发进行处理时，才能体现出将程序的工作负载分配到多个任务中带来的真正性能提升。

我们可以不用等到全部图片下载完再显示，而希望每下载一张图片就显示一张图片出来。

* CompletionService: 将Executor和BlockingQueue的功能融合在一起。将Callable/Runnable任务提交给它执行，然后使用类似队列操作的take和poll等方法获得已完成的结果，这些结果会被封装为Future。
* 实现类：ExecutorCompletionService。
    * 在构造函数中创建一个BlockingQueue来保存计算完成的结果。 
    * 当计算完成时调用 FutureTask中的done方法 `FutureTask#run() -> set() -> finishCompletion() -> done()`
    * 当提交任务时，先将任务包装成一个QueueingFuture，这是一个FutureTask的子类，然后改写子类的done方法，并将结果放到BlockingQueue中
    * take,poll方法委托给BlockingQueue，这些方法会在得出结果之前阻塞
```java
public interface CompletionService<V> {

    Future<V> submit(Callable<V> task);

    Future<V> submit(Runnable task, V result);

    Future<V> take() throws InterruptedException;

    Future<V> poll();

    Future<V> poll(long timeout, TimeUnit unit) throws InterruptedException;
}
// 部分代码
public class ExecutorCompletionService<V> implements CompletionService<V> {
    private final Executor executor;
    private final AbstractExecutorService aes;
    private final BlockingQueue<Future<V>> completionQueue;
    private class QueueingFuture extends FutureTask<Void> {
            QueueingFuture(RunnableFuture<V> task) {
                super(task, null);
                this.task = task;
            }
            protected void done() { completionQueue.add(task); }
            private final Future<V> task;
    }
    public ExecutorCompletionService(Executor executor) {
            if (executor == null)
                throw new NullPointerException();
            this.executor = executor;
            this.aes = (executor instanceof AbstractExecutorService) ?
                (AbstractExecutorService) executor : null;
            this.completionQueue = new LinkedBlockingQueue<Future<V>>();
    }
    public Future<V> submit(Runnable task, V result) {
            if (task == null) throw new NullPointerException();
            RunnableFuture<V> f = newTaskFor(task, result);
            executor.execute(new QueueingFuture(f));
            return f;
    }
}
```
```java
/**
* 使用CompletionService实现页面渲染
*/
public class Renderer {
    private final ExecutorService executorService;
    
    Renderer(ExecutorService executorService) {this.executorService = executorService;}
    
    void renderPage(CharSequence source) {
        // 获取图片信息
        final List<ImageInfo> imageInfos = scanForImageInfo(source);
        CompletionService<ImageData> completionService = new ExecutorCompletionService<ImageData>(executorService);
        for (final ImageInfo imageInfo : imageInfos) {
            completionService.submit(new Callable<ImageData>(){
                public ImageData call() {
                    return imageInfo.downloadImage();
                }
            });
        }
        renderText(source);
        try{
            for (int t = 0, n = imageInfos.size(); t < n; t++) {
                Future<ImageData> f = completionService.take();
                ImageData imageData = f.get();
                renderImage(imageData);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            //抛出异常
        }
    }
}
```
* 为单个任务设置时限 
`
Future # V get(long timeout, TimeUnit unit)
         throws InterruptedException, ExecutionException, TimeoutException;`
         
 > 场景：某个Web应用程序从外部的广告服务器上获取广告信息，如果该应用程序在两秒内得不到响应，那么将显示一个默认的广告。
 ```java
public class Renderer {
    
    private static final long TIME_BUDGET = 2 * 1000 * 1000 * 1000;
    private final ExecutorService exec = Executors.newFixedThreadPool(20); 
    
    Page renderPageWithAd() throws InterruptedException {
        long endNanos = System.nanoTime() + TIME_BUDGET;
        Future<Ad> future = exec.submit(new FetchAdTask());
        Ad ad;
        try{
            long waitTime = endNanos - System.nanoTime();
            ad = future.get(waitTime,TimeUnit.NANOSECONDS);
        } catch (TimeoutException e) {
            ad = DEFAULT_AD;
            future.cancel(true);
        } catch (ExecutionException e) {
            ad = DEFAULT_AD;
        }
        page.set(ad);
        reture page;
    }
}
```
> 场景：一个旅行预定门户网站，用户输入要求，门户网站获取并显示来自多条航线，旅馆或汽车租赁公司的报价。在这个过程中可能会调用Web服务，访问数据库等等，不宜让页面的响应时间受限于最慢的响应时间，而应该显示在指定时间内收到的信息。
> 从每个公司获取报价的过程相互之间没有影响，可以将获取报价的过程当成一个任务。创建n个任务，提交到一个线程池，保留n个future，使用限时的get方法串行获取每个结果。另一个更简单的方法是 
* 为一组任务设置时限 
`ExecutorService # <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,
                                                     long timeout, TimeUnit unit)
                           throws InterruptedException;`

> invokeAll: 将多个任务提交到一个ExecutorService并获取一组Future。invokeAll 按照任务集合中迭代器的顺序将所有的Future添加到返回的集合中，使调用者能将各个Future与Callable关联起来。 当所有任务都执行完毕，或调用线程被中断，或超过指定时限时，invokeAll将返回。当超过指定时限后，任何还未完成的任务都会被取消。当invokeAll返回时，每个任务的执行任务只有正常完成或取消两种，可以通过get或isCancelled来判断         
```java
public class TravelWeb {
    
    private final ExecutorService exec = Executors.newFixedThreadPool(20); 
    
    /**
    * Quote 报价
    * 获取报价的任务
    */
    private class QuoteTask implements Callable<TravelQuote> {
        
        /** 报价公司 */
        private final TravelCompany travelCompany;
        /** 用户的要求 */
        private final TraveInfo travelInfo;
        
        /** 构造函数 */
        QuoteTask(TravelCompany travelCompany,TraveInfo travelInfo) {
            this.travelCompany = travelCompany;
            this.travelInfo = travelInfo;
        }
        
        /** 获取报价信息 */
        public TravelQuote call() throws Exception {
            return travelCompany.solicitQuote(travelInfo);
        }
    }
    
    /**
    * 获取排序后的报价信息
    * @param travelInfo 用户输入的旅行信息
    * @param companies 获取报价的公司列表
    * @param ranking 比较器
    * @param time 超时时间
    * @param timeUnit 超时时间单位
    * @return 报价信息列表
    */
    public List<TravelQuote> getRankedTravelQuote(
            TraveInfo travelInfo,Set<TravelCompany> companies,
            Comparator<TravelQuote> ranking, long time, TimeUnit timeUnit
    ) {
        List<QuoteTask> tasks = new ArrayList<>();
        for (TravelCompany company : companies) {
            tasks.add(new QuoteTask(company,travelInfo));
        }
        List<Future<TravelQuote>> futures = exec.invokeAll(tasks,time,timeUnit);
        List<TravelQuote> result = new ArrayList<TravelQuote>(tasks.size());
        Iterator<QuoteTask> taskIterator = tasks.iterator();
        for (Future<TravelQuote> f : futures) {
            QuoteTask task = taskIterator.next();
            try{
                result.add(f.get());
            } catch (ExecutionException e) {
                result.add(task.getFailureQuote(e.getCause()));
            } catch (CancellationException e) {
                result.add(task.getTimeoutQuote(e));
            }
        }
        Collections.sort(result,ranking);
        return result;
    }
}
```














