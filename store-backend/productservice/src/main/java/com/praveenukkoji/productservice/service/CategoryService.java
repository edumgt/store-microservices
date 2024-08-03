package com.praveenukkoji.productservice.service;

import com.praveenukkoji.productservice.dto.request.category.CreateCategoryRequest;
import com.praveenukkoji.productservice.dto.request.category.UpdateCategoryRequest;
import com.praveenukkoji.productservice.dto.response.category.CategoryResponse;
import com.praveenukkoji.productservice.exception.category.CategoryNotFoundException;
import com.praveenukkoji.productservice.exception.category.CategoryUpdateException;
import com.praveenukkoji.productservice.exception.category.CreateCategoryException;
import com.praveenukkoji.productservice.model.Category;
import com.praveenukkoji.productservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public UUID createCategory(CreateCategoryRequest createCategoryRequest)
            throws CreateCategoryException {

        Category newCategory = Category.builder()
                .name(createCategoryRequest.getName())
                .build();

        try {
            return categoryRepository.save(newCategory).getId();
        } catch (Exception e) {
            throw new CreateCategoryException();
        }
    }

    public List<CategoryResponse> getAllCategory() {

        List<Category> categoryList = categoryRepository.findAll();

        return categoryList.stream()
                .map(category -> {
                    return CategoryResponse.builder()
                            .id(category.getId())
                            .name(category.getName())
                            .build();
                })
                .toList();
    }

    @Transactional
    public UUID updateCategory(
            UUID categoryId, UpdateCategoryRequest updateCategoryRequest
    ) throws CategoryNotFoundException, CategoryUpdateException {

        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isPresent()) {

            String categoryName = updateCategoryRequest.getName();

            if (!Objects.equals(categoryName, "")) {
                category.get().setName(categoryName);
            }

            try {
                return categoryRepository.save(category.get()).getId();
            } catch (Exception e) {
                throw new CategoryUpdateException();
            }
        }

        throw new CategoryNotFoundException();
    }

    public UUID deleteCategory(UUID categoryId)
            throws CategoryNotFoundException {

        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isPresent()) {
            categoryRepository.deleteById(categoryId);

            return categoryId;
        }

        throw new CategoryNotFoundException();
    }
}


































