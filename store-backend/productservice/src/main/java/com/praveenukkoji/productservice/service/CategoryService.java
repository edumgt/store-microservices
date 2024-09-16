package com.praveenukkoji.productservice.service;

import com.praveenukkoji.productservice.dto.request.category.CreateCategoryRequest;
import com.praveenukkoji.productservice.dto.request.category.UpdateCategoryRequest;
import com.praveenukkoji.productservice.dto.response.category.CategoryResponse;
import com.praveenukkoji.productservice.exception.category.CategoryCreateException;
import com.praveenukkoji.productservice.exception.category.CategoryDeleteException;
import com.praveenukkoji.productservice.exception.category.CategoryNotFoundException;
import com.praveenukkoji.productservice.exception.category.CategoryUpdateException;
import com.praveenukkoji.productservice.model.Category;
import com.praveenukkoji.productservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // create
    public UUID createCategory(CreateCategoryRequest createCategoryRequest) throws CategoryCreateException {

        Category newCategory = Category.builder()
                .name(createCategoryRequest.getName())
                .build();

        try {
            return categoryRepository.save(newCategory).getId();
        } catch (Exception e) {
            throw new CategoryCreateException(e.getMessage());
        }
    }

    // retrieve
    public CategoryResponse getCategory(UUID categoryId) throws CategoryNotFoundException {

        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isPresent()) {
            return CategoryResponse.builder()
                    .id(category.get().getId())
                    .name(category.get().getName())
                    .build();
        }

        throw new CategoryNotFoundException("category with id = " + categoryId + " not found");

    }

    // update
    @Transactional
    public UUID updateCategory(UUID categoryId, UpdateCategoryRequest updateCategoryRequest)
            throws CategoryNotFoundException, CategoryUpdateException {

        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isPresent()) {
            Category updatedCategory = category.get();

            try {
                updatedCategory.setName(updateCategoryRequest.getName());
                return categoryRepository.save(updatedCategory).getId();
            } catch (Exception e) {
                throw new CategoryUpdateException(e.getMessage());
            }
        }

        throw new CategoryNotFoundException("category with id = " + categoryId + " not found");
    }

    // delete
    public void deleteCategory(UUID categoryId) throws CategoryNotFoundException, CategoryDeleteException {

        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isPresent()) {
            try {
                categoryRepository.deleteById(categoryId);
                return;
            } catch (Exception e) {
                throw new CategoryDeleteException(e.getMessage());
            }
        }

        throw new CategoryNotFoundException("category with id = " + categoryId + " not found");
    }

    // get all
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
}


































