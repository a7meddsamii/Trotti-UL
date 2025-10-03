package ca.ulaval.glo4003.trotti.infrastructure.csv;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EmployeeIdulCsvReader {

    public List<Idul> readFromClasspathOrDie(String resourcePath) {
        try {
            var in = EmployeeIdulCsvReader.class.getClassLoader().getResourceAsStream(resourcePath);
            if (in == null) throw new IllegalStateException("Resource not found: " + resourcePath);
            try (var br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                return parse(br);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read: " + resourcePath, e);
        }
    }
    
    private List<Idul> parse(BufferedReader br) throws IOException {
        List<Idul> iduls = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            String s = line.trim();
            if (!s.isEmpty()) iduls.add(Idul.from(s));
        }
        return iduls;
    }
}