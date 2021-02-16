package com.gronazon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gronazon.TestUtil;
import org.junit.jupiter.api.Test;


public class CategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Category.class);
        Category category1 = new Category();
        category1.id = 1L;
        Category category2 = new Category();
        category2.id = category1.id;
        assertThat(category1).isEqualTo(category2);
        category2.id = 2L;
        assertThat(category1).isNotEqualTo(category2);
        category1.id = null;
        assertThat(category1).isNotEqualTo(category2);
    }
}
