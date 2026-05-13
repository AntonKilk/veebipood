package ee.anton.veebipood.controller;

import ee.anton.veebipood.dto.CategoryDto;
import ee.anton.veebipood.entity.Category;
import ee.anton.veebipood.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping("categories")
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @PostMapping("categories")
    public Category save(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PutMapping("categories/{id}")
    public Category update(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(categoryDto.name());
        return categoryRepository.save(category);
    }

    @DeleteMapping("categories/{id}")
    public void delete(@PathVariable Long id) {
        categoryRepository.deleteById(id);
    }
}
