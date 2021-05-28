package com.target.product.service;

import com.target.product.dao.ProductSearchDao;
import com.target.product.model.ProductPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSearchService {

    @Autowired
    ProductSearchDao productSearchDao;


    public ProductPrice getProductPrice(String productId) {

        return productSearchDao.getProductDetails(productId);
    }

    public List<ProductPrice> getAllProductsPrice() {

        return productSearchDao.getAllProductsDetails();
    }

    public void saveProductPrice(ProductPrice productPrice) {
        productSearchDao.saveProductPrice(productPrice);
    }

}
