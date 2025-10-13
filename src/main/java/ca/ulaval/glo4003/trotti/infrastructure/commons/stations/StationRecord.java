package ca.ulaval.glo4003.trotti.infrastructure.commons.stations;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StationRecord(
        @JsonProperty("location") String location,
        @JsonProperty("name") String name,
        @JsonProperty("capacity") int capacity
) {
}
