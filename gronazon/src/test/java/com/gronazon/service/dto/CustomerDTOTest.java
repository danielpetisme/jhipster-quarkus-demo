package com.gronazon.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gronazon.TestUtil;

public class CustomerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerDTO.class);
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.id = 1L;
        CustomerDTO customerDTO2 = new CustomerDTO();
        assertThat(customerDTO1).isNotEqualTo(customerDTO2);
        customerDTO2.id = customerDTO1.id;
        assertThat(customerDTO1).isEqualTo(customerDTO2);
        customerDTO2.id = 2L;
        assertThat(customerDTO1).isNotEqualTo(customerDTO2);
        customerDTO1.id = null;
        assertThat(customerDTO1).isNotEqualTo(customerDTO2);
    }
}
