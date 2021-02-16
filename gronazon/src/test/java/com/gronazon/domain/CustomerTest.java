package com.gronazon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gronazon.TestUtil;
import org.junit.jupiter.api.Test;


public class CustomerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customer.class);
        Customer customer1 = new Customer();
        customer1.id = 1L;
        Customer customer2 = new Customer();
        customer2.id = customer1.id;
        assertThat(customer1).isEqualTo(customer2);
        customer2.id = 2L;
        assertThat(customer1).isNotEqualTo(customer2);
        customer1.id = null;
        assertThat(customer1).isNotEqualTo(customer2);
    }
}
