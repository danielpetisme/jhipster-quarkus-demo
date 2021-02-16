package com.gronazon.service.mapper;


import com.gronazon.domain.*;
import com.gronazon.service.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "cdi", uses = {ProductMapper.class})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {



    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.id = id;
        return category;
    }
}
