package utils.functional;

import java.util.Objects;

/**
 * A very simple record with 2 members.
 *
 * @param <A> the type of the first member
 * @param <B> the type of the second member
 */
public class Tuple2<A, B> {
    /**
     * The first member of the record.
     */
    public final A fst;
    /**
     * The second member of the record.
     */
    public final B snd;

    /**
     * Constructs a new 2-membered tuples from the two values it will hold.
     *
     * @param a the value for the first member
     * @param b the value for the second member
     */
    public Tuple2(A a, B b) {
        this.fst = a;
        this.snd = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple2<?, ?> tuple2 = (Tuple2<?, ?>) o;
        return Objects.equals(fst, tuple2.fst) && Objects.equals(snd, tuple2.snd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fst, snd);
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", this.fst, this.snd);
    }
}
