package com.github.assemblathe1.core.repositories;

import com.github.assemblathe1.core.entities.ProductCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnalyticsRepository {
    private final EntityManager entityManager;

    public List<ProductCount> getMostPopularProductsPerMonth(Integer productQuantity) {
        System.out.println(productQuantity.intValue());
        return entityManager.createQuery(
                "SELECT new com.github.assemblathe1.core.entities.ProductCount(o.product, SUM(o.quantity))" +
                        "FROM OrderItem AS o " +
                        "GROUP BY o.product  " +
                        "ORDER BY SUM(o.quantity) DESC")
                .setMaxResults(productQuantity.intValue())
                .getResultList();
    };

//    @Query("SELECT new com.geekbrains.spring.web.core.analytics.ProductCount(o.product, SUM(o.quantity))" +
//            "FROM OrderItem AS o GROUP BY o.product  ORDER BY SUM(o.quantity)")
//    ArrayList<ProductCount> getMostPopularProductsPerMonth();
}
