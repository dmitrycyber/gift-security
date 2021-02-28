package com.epam.esm.model.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftSearchDto implements Serializable {
    private String namePrefix;
    private String descriptionPrefix;
    private List<String> tagNamePrefixes;
    private String sortField;
    private String sortMethod;
}
