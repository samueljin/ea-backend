package com.ea.interview.assessment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Constructor {
    private String constructorId;
    private String url;
    private String name;
    private String nationality;
}