package com.techelevator.dao;

import com.techelevator.model.Category;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
public class JdbcCategoryDaoTests extends BaseDaoTests{
    protected static final Category CATEGORY1 = new Category(1, "American");
    protected static final Category CATEGORY2 = new Category(2, "Chinese");
    protected static final Category CATEGORY3 = new Category(2, "Italian");

    private JdbcCategoryDao sut;

    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcCategoryDao(jdbcTemplate);
    }

    @Test
    public void getCategories_returns_categories(){
        List<Category> expected = new ArrayList<>();
        expected.add(CATEGORY1);
        expected.add(CATEGORY2);

        List<Category> actual = sut.getCategories();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getCategory_givenValidId_returnsCategory(){
        Category expected = CATEGORY1;

        Category actual = sut.getCategory(1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getCategory_givenInvalidId_returnsNull(){
        Category expected = null;

        Category actual = sut.getCategory(3);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addCategory_createsCategory(){
        Category newCategory = new Category(3, "Italian");
        boolean isCategoryCreated = sut.addCategory(newCategory);
        Assert.assertTrue(isCategoryCreated);

        Category actualCategory = sut.getCategory(newCategory.getCategoryId());
        Assert.assertEquals(newCategory, actualCategory);
    }
}
