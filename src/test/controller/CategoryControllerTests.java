package com.techelevator.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.Category;
import com.techelevator.security.JwtAccessDeniedHandler;
import com.techelevator.security.JwtAuthenticationEntryPoint;
import com.techelevator.security.UserModelDetailsService;
import com.techelevator.security.jwt.TokenProvider;
import com.techelevator.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTests {
    @Autowired
    MockMvc mvc;
    @MockBean
    CategoryService categoryService;

    @MockBean
    TokenProvider mockTokenProvider;
    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    JwtAccessDeniedHandler jwtAccessDeniedHandler;
    @MockBean
    UserModelDetailsService userModelDetailsService;

    ObjectMapper objectMapper = new ObjectMapper();
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

    @Test
    void getCategories_ShouldHaveCorrectRequestAndResponseMapping() throws Exception{
        List<Category> categoryList = Arrays.asList(getCategory1(), getCategory2());
        when(categoryService.getCategories()).thenReturn(categoryList);
        ResultActions result = mvc.perform(get("/category").accept(MediaType.APPLICATION_JSON));
        verify(categoryService, times(1)).getCategories();
        result.andExpect(
                status().isOk()
        );
        result.andExpect(
                content().contentType(MediaType.APPLICATION_JSON)
        );
        result.andExpect(
                content().json(objectMapper.writeValueAsString(categoryList))
        );
    }

    @Test
    void getCategory_givenValidId_ShouldHaveCorrectRequestAndResponseMapping() throws Exception{
        Category category = getCategory1();
        int id = getCategory1().getCategoryId();
        when(categoryService.getCategory(id)).thenReturn(category);
        ResultActions result = mvc.perform(get("/category/" + id));
        verify(categoryService, times(1)).getCategory(id);
        result.andExpect(
                status().isOk()
        );
        result.andExpect(
                content().contentType(MediaType.APPLICATION_JSON)
        );
        result.andExpect(
                content().json(objectMapper.writeValueAsString(category))
        );
    }
    @Test
    void addCategory_ShouldHaveCorrectRequestAndResponseMapping() throws Exception{
        Category category = getCategory1();
        boolean createdOne = true;
        when(categoryService.addCategory(category)).thenReturn(createdOne);
        ResultActions result = mvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)));
        result.andExpect(
                status().isCreated()
        );
        result.andExpect(
                content().string("true")
        );
    }
}
