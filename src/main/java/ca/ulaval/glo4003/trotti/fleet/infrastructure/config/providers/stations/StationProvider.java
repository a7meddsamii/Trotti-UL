package ca.ulaval.glo4003.trotti.fleet.infrastructure.config.providers.stations;

import ca.ulaval.glo4003.trotti.config.json.CustomJsonProvider;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class StationProvider {

    private static List<StationDataRecord> stationDataRecords;
    private static StationProvider instance;

    private StationProvider(Path path) {
        ObjectMapper objectMapper = CustomJsonProvider.getMapper();
        try (InputStream input = Files.newInputStream(path)) {
            stationDataRecords = objectMapper.readValue(input, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to load stations file at startup", e);
        }
    }

    public static void initialize(Path path) {
        instance = new StationProvider(path);
    }

    public static StationProvider getInstance() {
        if (instance == null) {
            throw new IllegalStateException(
                    "StationProvider not initialized. Call initialize() first.");
        }
        return instance;
    }

    public List<StationDataRecord> getStationDataRecords() {
        return stationDataRecords;
    }
}
