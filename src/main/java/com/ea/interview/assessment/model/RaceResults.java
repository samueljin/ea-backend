package com.ea.interview.assessment.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RaceResults {
    private String number;
    private String position;
    private String positionText;
    private String points;

    @JsonProperty("Driver")
    private Driver driver;

    @JsonProperty("Constructor")
    private Constructor constructors;

    private String grid;
    private String laps;
    private String status;

    @JsonProperty("Time")
    private Time time;

    @JsonProperty("FastestLap")
    private FastestLap fastestLap;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class FastestLap {
        private String rank;
        private String lap;

        @JsonProperty("Time")
        private Time time;

        @JsonProperty("AverageSpeed")
        private AverageSpeed averageSpeed;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public class AverageSpeed {
            private String units;
            private String speed;
        }
    }
}
