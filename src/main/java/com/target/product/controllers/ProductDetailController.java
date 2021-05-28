package com.target.product.controllers;

import com.target.product.exceptionhandler.BadRequestException;
import com.target.product.model.ProductPrice;
import com.target.product.model.Response;
import com.target.product.service.ProductSearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class ProductDetailController {

    @Autowired
    private ProductSearchService productSearchService;

    @GetMapping(value = "products/")
    public Response getProductsByProductId(@RequestParam String productId) throws BadRequestException {

        if (StringUtils.isEmpty(productId)) {
            throw new BadRequestException("productId is blank");
        }
        Response response = new Response();
        response.setCurrentPrice(productSearchService.getProductPrice(productId));
        response.setId(productId);
        response.setName("ProductName");

        return response;
    }

    @GetMapping(value = "getAllProductPrice")
    public List<ProductPrice> getAllProductsPrice() {

        return productSearchService.getAllProductsPrice();
    }

    @PostMapping(value = "addProduct")
    public ResponseEntity<String> exportEAData(@RequestBody Response request) {
        productSearchService.saveProductPrice(request.getCurrentPrice());
        return ResponseEntity.ok().body("success");
    }
}
