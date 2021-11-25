package utils;

/**
 * A very simple reference holder that allows reassigning when it is {@code final}.
 * <p>
 * This class is <i>not</i> thread-safe on its own.
 * For a thread-safe alternative, see {@link java.util.concurrent.atomic.AtomicReference}.
 *
 * @param <T> the type of values held in the cell
 */
public class RefCell<T> {
    /**
     * The reference held in the cell, in free access.
     */
    public T inner;

    /**
     * Creates a new reference cell from an initial value.
     *
     * @param val the reference to put in the cell
     */
    public RefCell(T val) {
        this.inner = val;
    }

    /**
     * Creates a {@code null}-initialized reference cell.
     */
    public RefCell() {
        this(null);
    }
}
