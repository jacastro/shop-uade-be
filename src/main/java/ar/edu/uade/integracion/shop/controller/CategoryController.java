package ar.edu.uade.integracion.shop.controller;

import ar.edu.uade.integracion.shop.entity.Category;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "categories")
public class CategoryController {

    @GetMapping("/")
    public List<Category> getCategories() {
        return Arrays.asList(Category.values());
    }
}
