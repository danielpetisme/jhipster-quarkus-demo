package com.gronazon.service.dto;


import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

import com.gronazon.domain.enumeration.OrderStatus;

/**
 * A DTO for the {@link com.gronazon.domain.Order} entity.
 */
@RegisterForReflection
public class OrderDTO implements Serializable {
    
    public Long id;

    public OrderStatus status;

    public Instant dateAdded;

    public Instant dateModified;

    public Long customerId;
    public String customerEmail;
    public Long productId;
    public String productTitle;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDTO)) {
            return false;
        }

        return id != null && id.equals(((OrderDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", dateAdded='" + dateAdded + "'" +
            ", dateModified='" + dateModified + "'" +
            ", customerId=" + customerId +
            ", customerEmail='" + customerEmail + "'" +
            ", productId=" + productId +
            ", productTitle='" + productTitle + "'" +
            "}";
    }
}
