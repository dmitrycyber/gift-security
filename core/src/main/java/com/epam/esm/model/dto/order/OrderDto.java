package com.epam.esm.model.dto.order;

import com.epam.esm.model.dto.CreatingDto;
import com.epam.esm.model.dto.TagDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends RepresentationModel<OrderDto> implements Serializable {
    @Null
    private Long id;

    @Null
    private Integer cost;

    @Null
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime purchaseDate;

    @NotNull
    private Long giftId;

    @NotNull
    private Long userId;
}
