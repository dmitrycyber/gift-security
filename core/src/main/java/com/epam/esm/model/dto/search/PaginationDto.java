package com.epam.esm.model.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDto implements Serializable {
    @Min(value = 1)
    @NotNull
    private Integer pageNumber;

    @Min(value = 1)
    @NotNull
    private Integer pageSize;
}
