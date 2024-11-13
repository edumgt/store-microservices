package com.praveenukkoji.productservice.repository;

import com.praveenukkoji.productservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    // get category by name
    @Query("""
            SELECT c From Category c WHERE c.name = ?1
            """)
    Optional<Category> findByCategoryName(String categoryName);
}
