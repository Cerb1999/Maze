package utils.functional;

import java.util.Objects;

/**
 * A simple product type with 3 members.
 *
 * @param <A> the type of the first member
 * @param <B> the type of the second member
 * @param <C> the type of the third member
 */
public class Tuple3<A, B, C> {
    /**
     * The first element of the product
     */
    public final A fst;
    /**
     * The second element of the product
     */
    public final B snd;
    /**
     * The third element of the product
     */
    public final C thd;

    /**
     * Creates a new 3-way product from the 3 members it will hold
     *
     * @param a the first member
     * @param b the second member
     * @param c the third member
     */
    public Tuple3(A a, B b, C c) {
        this.fst = a;
        this.snd = b;
        this.thd = c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple3<?, ?, ?> tuple3 = (Tuple3<?, ?, ?>) o;
        return Objects.equals(fst, tuple3.fst) && Objects.equals(snd, tuple3.snd) && Objects.equals(thd, tuple3.thd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fst, snd, thd);
    }
}
