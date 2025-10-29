package ca.ulaval.glo4003.trotti.commons.stations;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StationDataRecord(
        @JsonProperty("location") String location,
        @JsonProperty("name") String name,
        @JsonProperty("capacity") int capacity
) {
}
