package compiler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Options {
    public static final boolean DEBUG;

    static {
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
