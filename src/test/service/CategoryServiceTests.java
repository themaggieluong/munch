package com.techelevator.service;

import com.techelevator.dao.CategoryDao;
import com.techelevator.dao.JdbcCategoryDao;
import com.techelevator.model.Category;
import com.techelevator.services.CategoryService;
import com.techelevator.services.CategoryServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {
    CategoryDao mockCategoryDao = mock(JdbcCategoryDao.class);
    CategoryService sut = new CategoryServiceImpl(mockCategoryDao);
    Category getCategory1(){
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("American");
        return category;
    }

    Category getCategory2(){
        Category category = new Category();
        category.setCategoryId(2);
        category.setCategoryName("Chinese");
        return category;
    }

    Category getCategory3(){
        Category category = new Category();
        category.setCategoryId(3);
        category.setCategoryName("Italian");
        return category;
    }
    @BeforeEach
    void setup(){
        Category category1 = getCategory1();
        Category category2 = getCategory2();
        mockCategoryDao.addCategory(category1);
        mockCategoryDao.addCategory(category2);
    }

    @Test
    void getCategories_shouldGetCategories(){
        List<Category> expected = mockCategoryDao.getCategories();
        when(mockCategoryDao.getCategories()).thenReturn(expected);
        List<Category> actual = sut.getCategories();
        Assert.assertEquals(expected, actual);
    }

    @Test
    void getCategory_shouldGetCategory(){
        Category expected = getCategory1();
        when(mockCategoryDao.getCategory(getCategory1().getCategoryId())).thenReturn(expected);
        Category actual = sut.getCategory(getCategory1().getCategoryId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    void addCategory_shouldAddCategory(){
        boolean expected = true;
        when(mockCategoryDao.addCategory(getCategory3())).thenReturn(expected);
        boolean actual = sut.addCategory(getCategory3());
        Assert.assertEquals(expected, actual);
    }
}
