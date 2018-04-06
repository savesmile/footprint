package com.f_lin.utils.function;

import java.util.function.Supplier;

/**
 * SupplierCE
 * <p>
 * CE is Catch Exception
 *
 * @param <T>
 */
@FunctionalInterface
public interface SupplierCE<T> {

    /**
     * Gets a result.
     *
     * @return a result
     * @throws Exception
     */
    T get() throws Exception;

    /**
     * @return
     */
    default Supplier<T> toSupplier() {
        return () -> {
            try {
                return SupplierCE.this.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

}
