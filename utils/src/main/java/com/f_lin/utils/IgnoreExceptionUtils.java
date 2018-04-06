package com.f_lin.utils;

import com.f_lin.utils.function.JustDo;
import com.f_lin.utils.function.SupplierCE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * IgnoreExceptionUtils
 * <p>
 * 只管执行，忽略异常，
 * 用于程序中可有可无的操作地方，避免可以规避的异常中断正常逻辑
 *
 */
public class IgnoreExceptionUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(IgnoreExceptionUtils.class);

    /**
     * 同步运行不管是否有异常
     *
     * @param supplier supplier
     * @param <T>      T
     * @return
     */
    public static final <T> Optional<T> run(SupplierCE<T> supplier) {
        T res;
        try {
            res = supplier.get();
        } catch (Exception ignore) {
            LOGGER.error("SupplierCE error: ", ignore);
            res = null;
        }
        return Optional.ofNullable(res);
    }

    /**
     * @param justDo justDo
     */
    public static final void run(JustDo justDo) {
        try {
            justDo.justDoIt();
        } catch (Exception ignore) {
            LOGGER.error("JustDo error: ", ignore);
        }
    }

    /**
     * 执行一个操作，发生异常时使用默认值
     *
     * @param supplier     supplier
     * @param defaultValue defaultValue
     * @param <T>          T
     * @return
     */
    public static final <T> T run(SupplierCE<T> supplier, T defaultValue) {
        try {
            return supplier.get();
        } catch (Exception e) {
            LOGGER.info("获取操作发生异常，使用默认值", e);
            return defaultValue;
        }
    }

    /**
     * @param justDo justDo
     */
    public static final void runAsync(JustDo justDo) {
        try {
            CompletableFuture.runAsync(justDo.toRunnable());
        } catch (Exception ignore) {
            LOGGER.error("JustDo error: ", ignore);
        }
    }

    /**
     * 异步运行不管是否有异常
     * 当没有传入timeout参数时，返回null
     * 传入timeout参数会等待timeout milliseconds 若没有返回则返回null
     *
     * @param supplier supplier
     * @param timeout  timeout
     * @param <T>      T
     * @return
     */
    public static final <T> Optional<T> runAsync(SupplierCE<T> supplier, long... timeout) {
        T res = null;
        try {
            CompletableFuture<T> future = CompletableFuture.supplyAsync(supplier.toSupplier());
            if (timeout.length > 0) {
                res = future.get(timeout[0], TimeUnit.MILLISECONDS);
            }
        } catch (Exception ignore) {
            LOGGER.error("Async SupplierCE error: ", ignore);
            res = null;
        }
        return Optional.ofNullable(res);
    }

}
