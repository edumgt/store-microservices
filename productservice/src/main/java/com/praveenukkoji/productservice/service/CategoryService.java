package com.praveenukkoji.productservice.service;

import com.praveenukkoji.productservice.dto.Response;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CreateCategoryRequest createCategoryRequest)
            throws CreateCategoryException {

        Category newCategory = Category.builder()
                .categoryName(createCategoryRequest.getCategoryName())
                .createdBy(createCategoryRequest.getCreatedBy())
                .createdOn(LocalDateTime.now())
                .build();

        try {
            Category category = categoryRepository.saveAndFlush(newCategory);

            CategoryResponse response = CategoryResponse.builder()
                    .categoryId(category.getCategoryId())
                    .categoryName(category.getCategoryName())
                    .build();

            return response;
        } catch (Exception e) {
            throw new CreateCategoryException();
        }
    }

    public List<CategoryResponse> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();

        List<CategoryResponse> categoryResponseList = categoryList.stream()
                .map(category -> {
                    return CategoryResponse.builder()
                            .categoryId(category.getCategoryId())
                            .categoryName(category.getCategoryName())
                            .build();
                })
                .toList();

        return categoryResponseList;
    }

    @Transactional
    public CategoryResponse updateCategory(UUID categoryId, UpdateCategoryRequest updateCategoryRequest)
            throws CategoryNotFoundException, CategoryUpdateException {
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isPresent()) {
            String categoryName = updateCategoryRequest.getCategoryName();
            UUID modifiedBy = updateCategoryRequest.getModifiedBy();

            try {
                if (!Objects.equals(categoryName, "") && modifiedBy != null) {
                    category.get().setCategoryName(categoryName);
                    category.get().setModifiedBy(modifiedBy);
                    category.get().setModifiedOn(LocalDateTime.now());

                    Category updatedCategory = categoryRepository.saveAndFlush(category.get());

                    CategoryResponse response = CategoryResponse.builder()
                            .categoryId(updatedCategory.getCategoryId())
                            .categoryName(updatedCategory.getCategoryName())
                            .build();

                    return response;
                }

                throw new CategoryUpdateException();
            } catch (Exception e) {
                throw new CategoryUpdateException();
            }
        }

        throw new CategoryNotFoundException();
    }

    public Response deleteCategory(UUID categoryId)
            throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isPresent()) {
            categoryRepository.deleteById(categoryId);

            Response response = Response.builder()
                    .message("category deleted with categoryId = " + categoryId)
                    .build();

            return response;
        }

        throw new CategoryNotFoundException();
    }
}


































