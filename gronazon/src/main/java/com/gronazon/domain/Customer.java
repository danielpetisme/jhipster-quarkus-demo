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
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cacheable
@RegisterForReflection
public class Customer extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "first_name")
    public String firstName;

    @Column(name = "last_name")
    public String lastName;

    @Column(name = "email")
    public String email;

    @Size(max = 10)
    @Column(name = "telephone", length = 10)
    public String telephone;

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public Set<Order> orders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", email='" + email + "'" +
            ", telephone='" + telephone + "'" +
            "}";
    }

    public Customer update() {
        return update(this);
    }

    public Customer persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static Customer update(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("customer can't be null");
        }
        var entity = Customer.<Customer>findById(customer.id);
        if (entity != null) {
            entity.firstName = customer.firstName;
            entity.lastName = customer.lastName;
            entity.email = customer.email;
            entity.telephone = customer.telephone;
            entity.orders = customer.orders;
        }
        return entity;
    }

    public static Customer persistOrUpdate(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("customer can't be null");
        }
        if (customer.id == null) {
            persist(customer);
            return customer;
        } else {
            return update(customer);
        }
    }


}
