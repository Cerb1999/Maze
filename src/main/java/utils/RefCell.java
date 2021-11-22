package utils;

public class RefCell<T> {
    public T inner;

    public RefCell(T val) {
        this.inner = val;
    }
    public RefCell() {
        this(null);
    }
}
