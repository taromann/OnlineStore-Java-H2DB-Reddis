package com.github.assemblathe1.core.repositories.specifications;

import com.github.assemblathe1.core.entities.Category;
import com.github.assemblathe1.core.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;

public class ProductsSpecifications {
    public static Specification<Product> priceGreaterOrEqualsThan(Integer price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> priceLessThanOrEqualsThan(Integer price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> titleLike(String titlePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));
    }

    public static Specification<Product> belongToCategory(String category) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Subquery<Category> subquery = criteriaQuery.subquery(Category.class);
                Root<Category> categoryRoot = subquery.from(Category.class);
                subquery.select(categoryRoot)
                        .distinct(true)
                        .where(criteriaBuilder.like(categoryRoot.get("title"), String.format("%%%s%%", category)));
                System.out.println(subquery);
                return criteriaBuilder.in(root.get("category")).value(subquery);
            }
        };
    }
}
