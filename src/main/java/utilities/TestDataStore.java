package utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class TestDataStore {

    private static final String STORE_FILE =
            "target/test-data.properties";

    private static final Properties PROPERTIES =
            new Properties();

    private static boolean loaded = false;

    private TestDataStore() {
        // Utility class
    }

    // =========================================================
    // Get
    // =========================================================

    public static synchronized String get(String key) {

        loadIfNeeded();

        return PROPERTIES.getProperty(key);
    }

    // =========================================================
    // Set
    // =========================================================

    public static synchronized void set(
            String key,
            String value) {

        loadIfNeeded();

        PROPERTIES.setProperty(key, value);

        save();
    }

    // =========================================================
    // Remove
    // =========================================================

    public static synchronized void remove(String key) {

        loadIfNeeded();

        PROPERTIES.remove(key);

        save();
    }

    // =========================================================
    // Clear
    // =========================================================

    public static synchronized void clear() {

        PROPERTIES.clear();

        save();
    }

    // =========================================================
    // Load
    // =========================================================

    private static void loadIfNeeded() {

        if (loaded) {
            return;
        }

        Path path =
                Paths.get(STORE_FILE);

        if (Files.exists(path)) {

            try (InputStream input =
                         Files.newInputStream(path)) {

                PROPERTIES.load(input);

            } catch (IOException e) {

                throw new RuntimeException(
                        "Failed to load test data store",
                        e
                );
            }
        }

        loaded = true;
    }

    // =========================================================
    // Save
    // =========================================================

    private static void save() {

        Path path =
                Paths.get(STORE_FILE);

        try {

            Files.createDirectories(
                    path.getParent()
            );

            try (OutputStream output =
                         Files.newOutputStream(path)) {

                PROPERTIES.store(
                        output,
                        "Generated Test Data"
                );
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to save test data store",
                    e
            );
        }
    }
}