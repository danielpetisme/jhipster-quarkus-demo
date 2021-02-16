package com.gronazon.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerMapperTest {

    private CustomerMapper customerMapper;

    @BeforeEach
    public void setUp() {
        customerMapper = new CustomerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(customerMapper.fromId(id).id).isEqualTo(id);
        assertThat(customerMapper.fromId(null)).isNull();
    }
}
