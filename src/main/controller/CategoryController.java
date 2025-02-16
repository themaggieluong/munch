package com.techelevator.controller;

import com.techelevator.model.Category;
import com.techelevator.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value="/category", method= RequestMethod.GET)
    public List<Category> getCategories(){
        return categoryService.getCategories();
    }

    @RequestMapping(value="/category/{id}", method= RequestMethod.GET)
    public Category getCategories(@PathVariable int id){
        return categoryService.getCategory(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value="/category", method= RequestMethod.POST)
    public boolean addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }
}
