package utils.functional;

public class Tuple3<A, B, C> {
    public final A fst;
    public final B snd;
    public final C thd;

    public Tuple3(A a, B b, C c) {
        this.fst = a;
        this.snd = b;
        this.thd = c;
    }
}
