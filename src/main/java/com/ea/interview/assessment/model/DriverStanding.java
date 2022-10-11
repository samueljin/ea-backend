package com.ea.interview.assessment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DriverStanding {
    private String position;
    private String positionText;
    private String points;
    private String wins;

    @JsonProperty("Driver")
    private Driver driver;

    @JsonProperty("Constructors")
    private List<Constructor> constructors;
}
