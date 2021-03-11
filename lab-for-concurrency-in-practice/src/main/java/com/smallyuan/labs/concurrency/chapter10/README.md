## 避免活跃性危险
 ### 死锁
 最简单的锁顺序死锁
 > 如果所有线程以固定的顺序来获得锁，那么程序中就不会出现锁顺序死锁的问题。要验证锁顺序的一致性，需要全局的分析程序中的加锁行为。
 ```java
 /**
 * 当一个线程调用 leftRight，另一个线程调用rightLeft，并且这两个线程的操作是交错执行，就可能会发生死锁
*/
public class LeftRightDeadLock {
    private final Object left = new Object();
    private final Object right = new Object();
    
    public void leftRight(){
        synchronized (left) {
            synchronized (right) {
                doSomething();
            }
        }
    }
    
    public void rightLeft(){
        synchronized (right){
            synchronized (left) {
                doSomethingElse();
            }
        }
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
 ```java
public class RemoveDeadLock {
    private static final Object tieLock = new Object();
    
    /** 将资金从A账户，转移到B账户 */
    public void transferMoney(Account fromAccount,Account toAccount,DollarAoumt amount) {
        int fromHash = System.identityHashCode(fromAccount);
        int toHash = System.identityHashCode(toAccount);
        if (fromHash > toHash) {
            synchronized (toAccount){
                synchronized (fromAccount){
                    // ***
                }
            }
        } else {
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
 ### 死锁的避免与诊断
 
 ### 其他活跃性危险









