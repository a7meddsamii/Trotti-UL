package ca.ulaval.glo4003.trotti.infrastructure.config.providers;

import ca.ulaval.glo4003.trotti.domain.trip.values.StationConfiguration;
import ca.ulaval.glo4003.trotti.infrastructure.commons.stations.StationDataRecord;
import ca.ulaval.glo4003.trotti.infrastructure.commons.stations.mappers.StationMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class StationProvider {

    private static List<StationConfiguration> stationConfigurations;
    private static StationProvider instance;

    private StationProvider(Path path, StationMapper stationMapper) {
        ObjectMapper objectMapper = CustomJsonProvider.getMapper();
        try (InputStream input = Files.newInputStream(path)) {
            List<StationDataRecord> stationDataRecords =
                    objectMapper.readValue(input, new TypeReference<>() {});

            stationConfigurations = stationDataRecords.stream().map(stationMapper::toStationConfiguration).toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load stations file at startup", e);
        }
    }

    public static void initialize(Path path, StationMapper stationMapper) {
        instance = new StationProvider(path, stationMapper);
    }

    public static StationProvider getInstance() {
        if (instance == null) {
            throw new IllegalStateException(
                    "StationProvider not initialized. Call initialize() first.");
        }
        return instance;
    }

    public List<StationConfiguration> getStationConfigurations() {
        return stationConfigurations;
    }
}
