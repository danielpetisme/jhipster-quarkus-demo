package com.gronazon.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gronazon.TestUtil;

public class CategoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryDTO.class);
        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.id = 1L;
        CategoryDTO categoryDTO2 = new CategoryDTO();
        assertThat(categoryDTO1).isNotEqualTo(categoryDTO2);
        categoryDTO2.id = categoryDTO1.id;
        assertThat(categoryDTO1).isEqualTo(categoryDTO2);
        categoryDTO2.id = 2L;
        assertThat(categoryDTO1).isNotEqualTo(categoryDTO2);
        categoryDTO1.id = null;
        assertThat(categoryDTO1).isNotEqualTo(categoryDTO2);
    }
}
