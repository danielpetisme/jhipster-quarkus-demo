package com.gronazon.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gronazon.TestUtil;

public class ProductDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDTO.class);
        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.id = 1L;
        ProductDTO productDTO2 = new ProductDTO();
        assertThat(productDTO1).isNotEqualTo(productDTO2);
        productDTO2.id = productDTO1.id;
        assertThat(productDTO1).isEqualTo(productDTO2);
        productDTO2.id = 2L;
        assertThat(productDTO1).isNotEqualTo(productDTO2);
        productDTO1.id = null;
        assertThat(productDTO1).isNotEqualTo(productDTO2);
    }
}
