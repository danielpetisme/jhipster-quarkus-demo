package com.gronazon.service;

import io.quarkus.panache.common.Page;
import com.gronazon.domain.Product;
import com.gronazon.service.dto.ProductDTO;
import com.gronazon.service.mapper.ProductMapper;
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
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Inject
    ProductMapper productMapper;

    @Transactional
    public ProductDTO persistOrUpdate(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        var product = productMapper.toEntity(productDTO);
        product = Product.persistOrUpdate(product);
        return productMapper.toDto(product);
    }

    /**
     * Delete the Product by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        Product.findByIdOptional(id).ifPresent(product -> {
            product.delete();
        });
    }

    /**
     * Get one product by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ProductDTO> findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return Product.findByIdOptional(id)
            .map(product -> productMapper.toDto((Product) product)); 
    }

    /**
     * Get all the products.
     * @param page the pagination information.
     * @return the list of entities.
     */
    public Paged<ProductDTO> findAll(Page page) {
        log.debug("Request to get all Products");
        return new Paged<>(Product.findAll().page(page))
            .map(product -> productMapper.toDto((Product) product));
    }



}
