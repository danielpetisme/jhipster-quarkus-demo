package com.gronazon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gronazon.TestUtil;
import org.junit.jupiter.api.Test;


public class ProductTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = new Product();
        product1.id = 1L;
        Product product2 = new Product();
        product2.id = product1.id;
        assertThat(product1).isEqualTo(product2);
        product2.id = 2L;
        assertThat(product1).isNotEqualTo(product2);
        product1.id = null;
        assertThat(product1).isNotEqualTo(product2);
    }
}
