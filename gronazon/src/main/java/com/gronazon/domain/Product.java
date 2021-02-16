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

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cacheable
@RegisterForReflection
public class Product extends PanacheEntityBase implements Serializable {

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

    @Column(name = "price")
    public Double price;

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public Set<Order> orders = new HashSet<>();

    @ManyToMany(mappedBy = "products")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonbTransient
    public Set<Category> categories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", price=" + price +
            "}";
    }

    public Product update() {
        return update(this);
    }

    public Product persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static Product update(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product can't be null");
        }
        var entity = Product.<Product>findById(product.id);
        if (entity != null) {
            entity.title = product.title;
            entity.description = product.description;
            entity.price = product.price;
            entity.orders = product.orders;
            entity.categories = product.categories;
        }
        return entity;
    }

    public static Product persistOrUpdate(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product can't be null");
        }
        if (product.id == null) {
            persist(product);
            return product;
        } else {
            return update(product);
        }
    }


}
