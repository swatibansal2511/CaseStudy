package com.target.product.dao;

import com.target.product.model.ProductPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductSearchDao {


    @Autowired
    MongoTemplate mongoTemplate;

    public ProductPrice getProductDetails(String productId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("productId").is(productId));
        List<ProductPrice> result = mongoTemplate.find(query, ProductPrice.class);
        return result.get(0);

    }

    public List<ProductPrice> getAllProductsDetails() {
        return mongoTemplate.find(new Query(), ProductPrice.class);

    }

    public void saveProductPrice(ProductPrice price) {
        mongoTemplate.save(price,"productPrice");
    }

}
