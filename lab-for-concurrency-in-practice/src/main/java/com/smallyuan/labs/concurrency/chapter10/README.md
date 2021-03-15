## 避免活跃性危险
 ### 死锁
 最简单的锁顺序死锁
 > 如果所有线程以固定的顺序来获得锁，那么程序中就不会出现锁顺序死锁的问题。要验证锁顺序的一致性，需要全局的分析程序中的加锁行为。
 ```java
 /**
 * 当一个线程调用 leftRight，另一个线程调用rightLeft，并且这两个线程的操作是交错执行，就可能会发生死锁
 * 
 * jps 查看PID
 * jstack -F PID
*/
public class LeftRightDeadLock {
    private final Object left = new Object();
    private final Object right = new Object();
    
    public void leftRight() {
        synchronized (left) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (right) {
                
            }
        }
    }
    
    public void rightLeft() {
        synchronized (right){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (left) {

            }
        }
    }

    public static void main(String[] args) {
        LeftRightDeadLock deadLock = new LeftRightDeadLock();
        Thread t1 = new Thread(() -> deadLock.leftRight());
        Thread t2= new Thread(() -> deadLock.rightLeft());
        t1.start();
        t2.start();
    }

}
```
动态的锁顺序死锁
```java
public class DynamicDeadLock {
    /** 将资金从A账户，转移到B账户 */
    public void transferMoney(Account fromAccount,Account toAccount,DollarAoumt amount) {
        synchronized (fromAccount) {
            synchronized (toAccount) {
                if (fromAccount.getBalance().compareTo(amount) < 0) {
                    throw new InsufficientFundsException();
                } else {
                    fromAccount.debit(amount);
                    toAccount.credit(amount);
                }
            }
        }
    }
}
```
 `DynamicDeadLock#transferMoney`会产生死锁的原因是，要锁的两个对象取决于外部输入。
 如果 A: transferMoney(x,y,10);
 B:transferMoney(y,x,20);这样就会产生死锁。
 
 要解决这个问题，必须定义锁的顺序，并在整个应用程序中都按照这个顺序来获取锁。 可以使用`System.identityHashCode`方法，该方法将返回由`Object#hashCode`返回的值。
 
 case1 和 case2 根据hash值的比较，保证了每次都先锁住 hash 值小的账号，以此保证加锁的顺序性。 case3 是当两个账号的hash值相等时，多一个额外的对象加锁来保证每次只有一个线程对这两个对象无序加锁，从而消除死锁发生的可能性
 
 如果在Account中包含一个 唯一的， 不可变的， 并且具有可比性的键值，那么可以直接通过键值对对象进行排序来保证加锁的顺序性。
 ```java
public class RemoveDeadLock {
    private static final Object tieLock = new Object();
    
    /** 将资金从A账户，转移到B账户 */
    public void transferMoney(Account fromAccount,Account toAccount,DollarAoumt amount) {
        int fromHash = System.identityHashCode(fromAccount);
        int toHash = System.identityHashCode(toAccount);
        
        if (fromHash > toHash) { // case 1
            synchronized (toAccount){ 
                synchronized (fromAccount){
                    // ***
                }
            }
        } else if (fromHash < toHash) { // case 2
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    // ***
                }
            }
        } else { // case 3 : fromHash == toHash
            synchronized (tieLock){
                synchronized (fromAccount){
                    synchronized (toAccount){
                        // *** 
                    }
                }
            }
        }
    }
}
```

在协作对象之间发生死锁。
* 开放调用： 在调用某个方法时不需要持有锁，那么这种调用被称为开放调用。
* 使同步代码块仅用于保护那些涉及共享状态的操作，收缩同步代码块的保护范围可以提高可伸缩性。 这也会使得某个原子操作变为非原子操作。 在许多情况下是可以接受的。

资源死锁。 有界线程池/资源池与相互依赖的任务不能一起使用
* 数据库的线程池（资源池通常采用信号量来实现，没有资源时阻塞）。 两个资源池的情况下。。。
* 线程饥饿死锁（一个单线程的Executor在任务中提交另一个任务）
 ### 死锁的避免与诊断
 * 考虑锁的顺序
 * 支持定时的锁（显式锁：Lock#tryLock）
 * 分析死锁（1、 jsp 查看PID， 2. jstack PID 查看线程堆栈信息）
 * 内置锁（synchronized）与获得他们所在的线程的栈帧是相关联的，而显式锁只与获得它的线程相关联
 ### 其他活跃性危险

* 饥饿： 对线程优先级的使用不当。 尽量不要改变线程的优先级 （Thread.sleep, Thread.yield 试图克服优先级调整问题或相应问题）
    * 引发饥饿的最常见资源就是CPU时钟周期。
    * Thread API中定义的线程优先级只是线程调度的参考。MIN_PRORITY = 1， MAX_PRORITY = 10，Thread.NORM_PRIORITY = 5(默认)。JVM根据优先级将其映射到操作系统的调度优先级（与平台相关）
* 活锁： 一个很形象的例子，  两个过于礼貌的人在半路上面对面相遇，他们彼此都让出对方的路，然后过一段时间有相遇，然后反复避让下去，两个人都无法前进。
    * 解决方法，在重试机制中引入随机性。（以太协议定义了在重复发生冲突时采用指数方式回退机制） 在并发应用程序中，通过等待随机长度的时间和回退可以有效的避免活锁的发生







