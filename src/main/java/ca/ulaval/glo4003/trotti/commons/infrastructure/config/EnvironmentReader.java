package ca.ulaval.glo4003.trotti.commons.infrastructure.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EnvironmentReader {

    private static EnvironmentReader instance;
    private final Map<String, String> variables;

    private EnvironmentReader() {
        this.variables = new HashMap<>();
        loadFromFile("config.env");
    }

    public static EnvironmentReader getInstance() {
        if (instance == null) {
            instance = new EnvironmentReader();
        }
        return instance;
    }

    private void loadFromFile(String filePath) {
        BufferedReader reader = createReader(filePath);
        reader.lines().forEach(this::parseLine);
    }

    private BufferedReader createReader(String filePath) {
        try {
            return new BufferedReader(new FileReader(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Impossible de charger le fichier: " + filePath, e);
        }
    }

    private void parseLine(String line) {
        line = line.trim();

        if (line.isEmpty() || line.startsWith("#")) {
            return;
        }

        int equalIndex = line.indexOf('=');
        if (equalIndex > 0) {
            String key = line.substring(0, equalIndex).trim();
            String value = line.substring(equalIndex + 1).trim();
            variables.put(key, value);
        }
    }
    public String get(String key) {
        String systemValue = System.getenv(key);
        if (systemValue != null) {
            return systemValue;
        }
        return variables.get(key);
    }

    public String get(String key, String defaultValue) {
        String value = get(key);
        return value != null ? value : defaultValue;
    }
}