package exceptions;

public final class Exn {
    public static <T> T error(String msg) {
        throw new RuntimeException(String.format("error: %s", msg));
    }

    public static <T> T undefined() {
        return error("Exn.undefined()");
    }

    public static <T> T todo() {
        return error("not yet implemented");
    }
    public static <T> T todo(String msg) {
        return error(String.format("TODO: %s", msg));
    }
}
