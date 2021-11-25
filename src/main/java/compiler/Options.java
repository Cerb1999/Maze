package compiler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Options {
    /**
     * Should debug be activated in the application?
     *
     * @implNote This value is set from a bundled {@code options.properties} in a {@code static}
     * block.
     * This allows specifying whether debugging is activated or not at compile-time (or even modified
     * directly in the built .jar file).
     */
    public static final boolean DEBUG;

    static {
        // this is needed so that we don't initialize {@link #DEBUG} twice (which would be
        // disallowed by Java anyway).
        boolean debug = false;

        try (InputStream is = Options.class.getClassLoader().getResourceAsStream("options.properties")) {
            Properties props = new Properties();
            props.load(is);

            // add more properties if needed
            debug = Boolean.parseBoolean(props.getProperty("DEBUG", "false"));
        } catch (IOException ignored) {
            // do nothing here
        }

        // initialize every property here
        DEBUG = debug;
    }
}
