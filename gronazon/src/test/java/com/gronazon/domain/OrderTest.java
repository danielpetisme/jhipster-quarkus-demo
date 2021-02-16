package com.gronazon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gronazon.TestUtil;
import org.junit.jupiter.api.Test;


public class OrderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Order.class);
        Order order1 = new Order();
        order1.id = 1L;
        Order order2 = new Order();
        order2.id = order1.id;
        assertThat(order1).isEqualTo(order2);
        order2.id = 2L;
        assertThat(order1).isNotEqualTo(order2);
        order1.id = null;
        assertThat(order1).isNotEqualTo(order2);
    }
}
