package com.gronazon.service.mapper;


import com.gronazon.domain.*;
import com.gronazon.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "cdi", uses = {})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {


    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.id = id;
        return product;
    }
}
