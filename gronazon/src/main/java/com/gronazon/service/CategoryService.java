package com.gronazon.service;

import io.quarkus.panache.common.Page;
import com.gronazon.domain.Category;
import com.gronazon.service.dto.CategoryDTO;
import com.gronazon.service.mapper.CategoryMapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryService.class);

    @Inject
    CategoryMapper categoryMapper;

    @Transactional
    public CategoryDTO persistOrUpdate(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        var category = categoryMapper.toEntity(categoryDTO);
        category = Category.persistOrUpdate(category);
        return categoryMapper.toDto(category);
    }

    /**
     * Delete the Category by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        Category.findByIdOptional(id).ifPresent(category -> {
            category.delete();
        });
    }

    /**
     * Get one category by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<CategoryDTO> findOne(Long id) {
        log.debug("Request to get Category : {}", id);
        return Category.findOneWithEagerRelationships(id)
            .map(category -> categoryMapper.toDto((Category) category)); 
    }

    /**
     * Get all the categories.
     * @param page the pagination information.
     * @return the list of entities.
     */
    public Paged<CategoryDTO> findAll(Page page) {
        log.debug("Request to get all Categories");
        return new Paged<>(Category.findAll().page(page))
            .map(category -> categoryMapper.toDto((Category) category));
    }


    /**
     * Get all the categories with eager load of many-to-many relationships.
     * @param page the pagination information.
     * @return the list of entities.
     */
    public Paged<CategoryDTO> findAllWithEagerRelationships(Page page) {
        var categories = Category.findAllWithEagerRelationships().page(page).list();
        var totalCount = Category.findAll().count();
        var pageCount = Category.findAll().page(page).pageCount();
        return new Paged<>(page.index, page.size, totalCount, pageCount, categories)
            .map(category -> categoryMapper.toDto((Category) category));
    }


}
