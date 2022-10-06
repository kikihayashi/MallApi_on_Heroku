package com.kikihayashi.springboot_mall.controller;

import com.kikihayashi.springboot_mall.constant.ProductCategory;
import com.kikihayashi.springboot_mall.dto.ProductQueryParams;
import com.kikihayashi.springboot_mall.dto.ProductRequest;
import com.kikihayashi.springboot_mall.model.Product;
import com.kikihayashi.springboot_mall.service.ProductService;
import com.kikihayashi.springboot_mall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id) {
        Product product = productService.getProductById(id);
        //需要驗證商品是否存在
        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            /**
             * 查詢條件
             * category：分類
             * search：關鍵字
             * orderBy：排序
             * sort：升序、降序
             * limit：取得幾筆參數
             * offset：跳過前幾筆資料
             * */
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "DESC") String sort,
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0)Integer limit,
            @RequestParam(defaultValue = "0") @Min(0)Integer offset
    ) {
        ProductQueryParams params = new ProductQueryParams();
        params.setCategory(category);
        params.setSearch(search);
        params.setOrderBy(orderBy);
        params.setSort(sort);
        params.setLimit(limit);
        params.setOffset(offset);

        //取得當前條件下的資料(有包括limit、offset條件)
        List<Product> productList = productService.getProducts(params);

        //取得當前條件下的總筆數(不包括limit、offset條件)
        Integer total = productService.countProduct(params);

        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);

        //對Restful的設計理念，即便沒有任何商品，products這個url資源還是存在，所以一律回傳200
        //不需要驗證所有商品的數量是否大於0
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Integer id = productService.createProduct(productRequest);

        Product product = productService.getProductById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id,
                                                 @RequestBody @Valid ProductRequest productRequest) {

        //檢查商品是否存在
        Product product = productService.getProductById(id);

        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //更新商品
        productService.updateProduct(id, productRequest);
        Product productNew = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(productNew);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        //刪除商品，不需要去管商品原先是否存在
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
