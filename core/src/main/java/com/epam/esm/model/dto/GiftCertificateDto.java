package com.epam.esm.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> implements Serializable {
    @Null
    private Long id;

    @Size(min = 2, max = 45)
    @NotNull(groups = {CreatingDto.class})
    private String name;

    @Size(min = 3, max = 255)
    private String description;

    @Min(value = 1)
    @NotNull(groups = {CreatingDto.class})
    private Integer price;

    @Min(value = 1)
    @NotNull(groups = {CreatingDto.class})
    private Integer duration;

    @Null
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createDate;

    @Null
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;

    @Valid
    private Set<TagDto> tags;
}
