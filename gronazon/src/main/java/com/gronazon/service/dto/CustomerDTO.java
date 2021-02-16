package com.gronazon.service.dto;


import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.gronazon.domain.Customer} entity.
 */
@RegisterForReflection
public class CustomerDTO implements Serializable {
    
    public Long id;

    public String firstName;

    public String lastName;

    public String email;

    @Size(max = 10)
    public String telephone;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerDTO)) {
            return false;
        }

        return id != null && id.equals(((CustomerDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", email='" + email + "'" +
            ", telephone='" + telephone + "'" +
            "}";
    }
}
