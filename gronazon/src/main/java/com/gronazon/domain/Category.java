package com.gronazon.domain;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import javax.json.bind.annotation.JsonbTransient;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@Cacheable
@RegisterForReflection
public class Category extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    public String title;

    @NotNull
    @Column(name = "description", nullable = false)
    public String description;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "category_product",
               joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    @JsonbTransient
    public Set<Product> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            "}";
    }

    public Category update() {
        return update(this);
    }

    public Category persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static Category update(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("category can't be null");
        }
        var entity = Category.<Category>findById(category.id);
        if (entity != null) {
            entity.title = category.title;
            entity.description = category.description;
            entity.products = category.products;
        }
        return entity;
    }

    public static Category persistOrUpdate(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("category can't be null");
        }
        if (category.id == null) {
            persist(category);
            return category;
        } else {
            return update(category);
        }
    }

    public static PanacheQuery<Category> findAllWithEagerRelationships() {
        return find("select distinct category from Category category left join fetch category.products");
    }

    public static Optional<Category> findOneWithEagerRelationships(Long id) {
        return find("select category from Category category left join fetch category.products where category.id =?1", id).firstResultOptional();
    }

}
