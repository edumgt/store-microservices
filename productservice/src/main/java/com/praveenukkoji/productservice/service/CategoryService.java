package com.praveenukkoji.productservice.service;

import com.praveenukkoji.productservice.dto.category.request.CreateCategoryRequest;
import com.praveenukkoji.productservice.dto.category.request.UpdateCategoryRequest;
import com.praveenukkoji.productservice.dto.category.response.CategoryResponse;
import com.praveenukkoji.productservice.exception.category.CategoryCreateException;
import com.praveenukkoji.productservice.exception.category.CategoryDeleteException;
import com.praveenukkoji.productservice.exception.category.CategoryNotFoundException;
import com.praveenukkoji.productservice.exception.category.CategoryUpdateException;
import com.praveenukkoji.productservice.exception.error.ValidationException;
import com.praveenukkoji.productservice.model.Category;
import com.praveenukkoji.productservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // create
    public UUID createCategory(CreateCategoryRequest createCategoryRequest)
            throws CategoryCreateException, ValidationException {

        String categoryName = createCategoryRequest.getName();

        log.info("creating new category = {}", categoryName);

        if(Objects.equals(categoryName, "")) {
            throw new ValidationException("name", "category name cannot be empty");
        }

        try {
            Category newCategory = Category.builder()
                    .name(categoryName)
                    .build();

            return categoryRepository.save(newCategory).getId();
        } catch (DataIntegrityViolationException e) {
            throw new CategoryCreateException(e.getMostSpecificCause().getMessage());
        } catch (Exception e) {
            throw new CategoryCreateException(e.getMessage());
        }
    }

    // get
    public CategoryResponse getCategory(String id)
            throws CategoryNotFoundException, ValidationException {

        log.info("fetching category having id = {}", id);

        if(Objects.equals(id, "")) {
            throw new ValidationException("categoryId", "category id cannot be empty");
        }

        UUID categoryId = UUID.fromString(id);

        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isPresent()) {
            return CategoryResponse.builder()
                    .id(category.get().getId())
                    .name(category.get().getName())
                    .build();
        }

        throw new CategoryNotFoundException("category with id = " + id + " not found");

    }

    // update
    public void updateCategory(UpdateCategoryRequest updateCategoryRequest)
            throws CategoryNotFoundException, CategoryUpdateException, ValidationException {

        String id = updateCategoryRequest.getCategoryId();
        String name = updateCategoryRequest.getName();

        log.info("updating category having id = {}", id);

        if(Objects.equals(id, "")) {
            throw new ValidationException("categoryId", "category id cannot be empty");
        }

        if(Objects.equals(name, "")) {
            throw new ValidationException("name", "category name cannot be empty");
        }

        try {
            UUID categoryId = UUID.fromString(id);
            Optional<Category> category = categoryRepository.findById(categoryId);

            if (category.isPresent()) {
                Category updatedCategory = category.get();
                updatedCategory.setName(name);

                categoryRepository.save(updatedCategory);
            }
            else {
                throw new CategoryNotFoundException("category with id = " + id + " not found");
            }
        }
        catch (CategoryNotFoundException e) {
            throw new CategoryNotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new CategoryUpdateException(e.getMessage());
        }
    }

    // delete
    public void deleteCategory(String id)
            throws CategoryNotFoundException, CategoryDeleteException, ValidationException {

        log.info("deleting category having id = {}", id);

        if (Objects.equals(id, "")) {
            throw new ValidationException("categoryId", "category id cannot be empty");
        }

        try {
            UUID categoryId = UUID.fromString(id);
            Optional<Category> category = categoryRepository.findById(categoryId);

            if (category.isPresent()) {
                categoryRepository.deleteById(categoryId);
            }
            else {
                throw new CategoryNotFoundException("category with id = " + id + " not found");
            }
        }
        catch(CategoryNotFoundException e){
            throw new CategoryNotFoundException(e.getMessage());
        }
        catch(Exception e){
            throw new CategoryDeleteException(e.getMessage());
        }
    }

    // get all
    public List<CategoryResponse> getAllCategory() {

        log.info("fetching all categories");

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


































