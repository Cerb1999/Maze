package utils.functional;

import java.util.IdentityHashMap;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A lazy wrapper for a value of type <code>T</code>.
 *
 * @param <T> the type of values initialized by this lazy holder
 * @see <a href="https://stackoverflow.com/a/36280375/6718698">this StackOverflow post</a> for most of this interface.
 */
@FunctionalInterface
public interface Lazy<T> extends Supplier<T> {
    /**
     * Get the lazily generated value from this lazy holder.
     * <p>
     * Depending on the object generated, the first call to this method may be very slow.
     *
     * @return the object held in this lazy holder
     */
    @Override
    default T get() {
        return Cache.getInstance(this, this::init);
    }

    /**
     * Initializes the lazy value.
     * Because this interface is a functional interface, using a lambda of type <code>Lazy&lt;T&gt;</code> involves
     * calling this function internally.
     *
     * @return a value lazily computed
     */
    T init();

    abstract class Cache<T> {
        /**
         * @implNote
         * It is mandatory to use <code>Object</code> here instead of <code>T</code> as the value of the
         * map, because <code>T</code> cannot be referenced from a static context.
         */
        private static final IdentityHashMap<Object, Object> values = new IdentityHashMap<>();

        /**
         * Creates a new cached instance of a <code>T</code> only if none were already created from the
         * same lazy holder, else retrieves the one already created and returns it.
         * <p>
         * This function is thread-safe. Multiple instances of the same lazy holder will not create multiple
         * times the same object in different threads.
         *
         * @param lazyHolder the lazy holder to create instances for
         * @param creator    the supplier used to create new instances of objects of type <code>T</code>
         * @param <T>        the type of objects to retrieve from the cache
         * @return the cached value if one has already been created, else a new one generated (and cached) from
         * th supplier.
         */
        public static synchronized <T> T getInstance(Lazy<T> lazyHolder, Supplier<T> creator) {
            Object instance = values.get(lazyHolder);
            if (Objects.isNull(instance)) {
                // NOTE: this is automatically upcasted to Object
                instance = creator.get();

                values.put(lazyHolder, instance);
            }

            // NOTE: it is unfortunately impossible to check if <code>instance</code> is really an instance of
            // a <code>T</code> (because <code>T</code> is an erased generic parameter).
            // So we simply put this comment to disable the warning generated.

            //noinspection unchecked
            return (T) instance;
        }
    }
}