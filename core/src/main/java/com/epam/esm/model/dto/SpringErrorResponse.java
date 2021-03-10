package com.epam.esm.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpringErrorResponse implements Serializable {
    private String error;

    @JsonProperty("error_description")
    private String errorDescription;
}
