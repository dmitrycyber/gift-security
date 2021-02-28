package com.epam.esm.model.dto.user;

import com.epam.esm.model.dto.CreatingDto;
import com.epam.esm.model.dto.order.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends RepresentationModel<UserDto> implements Serializable {
    @Null
    private Long id;

    @Size(min = 2, max = 45)
    @NotNull(groups = {CreatingDto.class})
    private String firstName;

    @Size(min = 2, max = 45)
    @NotNull(groups = {CreatingDto.class})
    private String lastName;

    @Size(min = 2, max = 45)
    @Email
    @NotNull(groups = {CreatingDto.class})
    private String email;

    @Size(min = 13, max = 13)
    @NotNull(groups = {CreatingDto.class})
    private String phoneNumber;

    @Size(min = 8, max = 8)
    @NotNull
    private String password;

    @Valid
    @Null(groups = {CreatingDto.class})
    private Set<OrderDto> orders;
}
