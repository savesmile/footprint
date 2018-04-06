package com.f_lin.utils.function;

/**
 * JustDo
 */
@FunctionalInterface
public interface JustDo {
    /**
     * 运行并跑出异常的方法
     *
     * @throws Exception
     */
    void justDoIt() throws Exception;

    /**
     * @return
     */
    default Runnable toRunnable() {
        return () -> {
            try {
                JustDo.this.justDoIt();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

}
