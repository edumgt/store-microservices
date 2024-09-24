package com.praveenukkoji.productservice.repository;

import com.praveenukkoji.productservice.model.Category;
import com.praveenukkoji.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    // get by category
    @Query("""
            SELECT p FROM Product p WHERE p.category = ?1
            """)
    List<Product> findAllByCategoryName(Category category);
}
