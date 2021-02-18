package com.smallyuan.labs.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {

    private final FutureTask<ProductInfo> future = new FutureTask<ProductInfo>(this::loadProductInfo);

    private final Thread thread = new Thread(future);

    /**
     * 在构造函数 或 静态初始化方法中启动线程不是个好方法， 提供一个start方法来启动线程
     */
    public void start() {
        thread.start();
    }

    /**
     * 当需要 产品信息的时候，通过get方法来获取
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public ProductInfo get() throws InterruptedException, ExecutionException {
            return future.get();
    }

    /**
     * 加载产品信息，可以从数据库获取
     * @return
     */
    private ProductInfo loadProductInfo() {
        return new ProductInfo();
    }

    class ProductInfo {}
}
