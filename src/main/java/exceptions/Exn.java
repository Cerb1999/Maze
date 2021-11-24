package exceptions;

public final class Exn {
    private static class ErrorCallException extends RuntimeException {
        public ErrorCallException(String msg) {
            super(msg);
        }
    }

    /**
     * Reports a general error with the given message.
     *
     * @implNote This is implemented by throwing an internal {@link java.lang.RuntimeException} which
     * should not be caught in a {@code try}-{@code catch} block.
     * There are other ways to return the {@code ⊥} value (e.g. return a {@link java.lang.Void}) but
     * throwing an exception allows early failing and being used in almost any context.
     *
     * @param msg the error message
     * @param <T> semantically equivalent to {@code any} or {@code Object}
     * @return the unforgeable {@code ⊥} value
     */
    public static <T> T error(String msg) {
        throw new ErrorCallException(String.format("error: %s", msg));
    }

    /**
     * Specifies that the return value is undefined/unknown at this point (for example because it has
     * not yet been implemented or it is unreachable).
     *
     * This is equivalent to calling {@code Exn.error("Exn.undefined()")}.
     *
     * @param <T> semantically equivalent to {@code any} or {@code Object}
     * @return the unforgeable {@code ⊥} value
     */
    public static <T> T undefined() {
        return error("Exn.undefined()");
    }

    /**
     * Indicates that a particular point is yet to be done.
     *
     * @param <T> semantically equivalent to {@code any} or {@code Object}
     * @return the unforgeable {@code ⊥} value
     */
    public static <T> T todo() {
        return todo("not yet implemented");
    }

    /**
     * Indicates that a particular point is yet to be done, but with a custom message.
     *
     * @param msg the message to print when this function is called
     * @param <T> semantically equivalent to {@code any} or {@code Object}
     * @return the unforgeable {@code ⊥} value
     */
    public static <T> T todo(String msg) {
        return error(String.format("TODO: %s", msg));
    }
}
