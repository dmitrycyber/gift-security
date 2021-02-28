package com.epam.esm.jpa.criteria;

import com.epam.esm.jpa.exception.PaginationException;

import javax.persistence.TypedQuery;
import javax.validation.constraints.Min;

public class PaginationBuilder {

    public static void addPagination(@Min(value = 1) Integer pageNumber, @Min(value = 1) Integer pageSize, TypedQuery query) {
        if (pageNumber != null && pageSize != null) {
            if (pageNumber < 1 || pageSize < 1) {
                throw new PaginationException("check page number or page size");
            }
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
    }
}
