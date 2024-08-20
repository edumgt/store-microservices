package com.praveenukkoji.productservice.service;

import com.praveenukkoji.productservice.dto.request.category.CreateCategoryRequest;
import com.praveenukkoji.productservice.dto.request.category.UpdateCategoryRequest;
import com.praveenukkoji.productservice.dto.response.category.CategoryResponse;
import com.praveenukkoji.productservice.exception.category.CategoryCreateException;
import com.praveenukkoji.productservice.exception.category.CategoryNotFoundException;
import com.praveenukkoji.productservice.exception.category.CategoryUpdateException;
import com.praveenukkoji.productservice.model.Category;
import com.praveenukkoji.productservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public UUID createCategory(CreateCategoryRequest createCategoryRequest)
            throws CategoryCreateException {

        Category newCategory = Category.builder()
                .name(createCategoryRequest.getName())
                .build();

        try {
            return categoryRepository.save(newCategory).getId();
        } catch (Exception e) {
            throw new CategoryCreateException();
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

            try {
                Category updatedCategory = category.get();
                updatedCategory.setName(updateCategoryRequest.getName());

                return categoryRepository.save(updatedCategory).getId();
            } catch (Exception e) {
                throw new CategoryUpdateException();
            }
        }

        throw new CategoryNotFoundException();
    }

    public void deleteCategory(UUID categoryId)
            throws CategoryNotFoundException {

        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isPresent()) {
            categoryRepository.deleteById(categoryId);
            return;
        }

        throw new CategoryNotFoundException("category with id = " + categoryId + " not found");
    }
}


































