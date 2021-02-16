package com.gronazon.domain;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import javax.json.bind.annotation.JsonbTransient;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.gronazon.domain.enumeration.OrderStatus;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@Cacheable
@RegisterForReflection
public class Order extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public OrderStatus status;

    @Column(name = "date_added")
    public Instant dateAdded;

    @Column(name = "date_modified")
    public Instant dateModified;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonbTransient
    public Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonbTransient
    public Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", dateAdded='" + dateAdded + "'" +
            ", dateModified='" + dateModified + "'" +
            "}";
    }

    public Order update() {
        return update(this);
    }

    public Order persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static Order update(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("order can't be null");
        }
        var entity = Order.<Order>findById(order.id);
        if (entity != null) {
            entity.status = order.status;
            entity.dateAdded = order.dateAdded;
            entity.dateModified = order.dateModified;
            entity.customer = order.customer;
            entity.product = order.product;
        }
        return entity;
    }

    public static Order persistOrUpdate(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("order can't be null");
        }
        if (order.id == null) {
            persist(order);
            return order;
        } else {
            return update(order);
        }
    }


}
