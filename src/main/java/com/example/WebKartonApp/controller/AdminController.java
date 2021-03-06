package com.example.WebKartonApp.controller;


import com.example.WebKartonApp.model.News;

import com.example.WebKartonApp.model.Product;

import com.example.WebKartonApp.repo.NewsRepository;
import com.example.WebKartonApp.repo.ProductRepository;

import com.example.WebKartonApp.service.NewsService;

import com.example.WebKartonApp.service.ProductService;
import com.example.WebKartonApp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/api/v1/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    @Value("${upload.path}")
    private String uploadPath;

    private final NewsService newsService;
    private final UserService userService;
    private final ProductService productService;
    private final ProductRepository productRepository;

    private final NewsRepository newsRepository;

//    private final CategoryService categoryService;

//    private final CategoryRepository categoryRepository;

//    private final OrderService orderService;

//    @PostMapping("/add_category")
//    public ResponseEntity<?> addCategory(@RequestBody Category category){
//       categoryService.save(category);
//       return new ResponseEntity<>(category,HttpStatus.CREATED);
//    }
//
//    @DeleteMapping("/delete_category")
//    public void deleteCategory(@RequestBody Category category) {
//        categoryRepository.delete(category);
//    }

    @PostMapping("/add_prod")
    public ResponseEntity<?> addProduct(
            @RequestBody Product product,
        BindingResult bindingResult
//        @RequestPart(name = "file", required = false) MultipartFile file
    ) throws IOException {
            if (bindingResult.hasErrors()) {
                Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

                return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
            } else {
//                saveFile(product, file);

                Product savedProduct = productService.save(product);

                log.debug("ADMIN added product to DB: id={}, product={}, category={}",
                        product.getId(), product.getProductName(), product.getProductCategory());

                return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
            }
        }



    @PutMapping("/update_prod")
    public ResponseEntity<?> updateProduct(
            @RequestBody Product product,
            BindingResult bindingResult
//            @RequestPart(name = "file", required = false) MultipartFile file
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        } else {
//            saveFile(product, file);

            productService.saveProductInfoById(product.getProductName(),
                    product.getProductCategory(),
                    product.getSubCategory(),
                    product.getProductColor(),
                    product.getProductDescription(),
                    product.getPrice(),
                    product.getFilename(),
                    product.getQuantity(),
                    product.getId());

            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete_prod")
    public void deleteProduct(@RequestBody Product product) {
        productRepository.delete(product);
    }
//    @GetMapping("/orders")
//    public ResponseEntity<?> getAllOrders() {
//        log.info("got getAllOrders request");
//        List<Order> orders = orderService.findAll();
//
//        return new ResponseEntity<>(orders, HttpStatus.OK);
//    }
//
//    @GetMapping("/user/{id}")
//    public ResponseEntity<?> getUser(@PathVariable("id") Long userId) {
//        User user = userService.getOne(userId);
//
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }
//
//    @GetMapping("/user/all")
//    public ResponseEntity<?> getAllUsers() {
//        List<User> users = userService.findAll();
//
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }

    @PostMapping("/add_news")
    public ResponseEntity<?> addNews(
            @RequestBody News news,
            BindingResult bindingResult
//            @RequestPart(name = "file1", required = false) MultipartFile file1
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        } else {
//            saveFileTwo(news, file1);

            News savedNews = newsService.save(news);

            return new ResponseEntity<>(savedNews, HttpStatus.CREATED);
        }
    }

    @PutMapping("/update_news")
    public ResponseEntity<?> updateNews(
            @RequestBody News news,
            BindingResult bindingResult
//            @RequestPart(name = "file1", required = false) MultipartFile file1
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        } else {
//            saveFileTwo(news, file1);

            newsService.saveNewsInfoById(news.getTitle(),
                    news.getInformation(),
                    news.getFilename(),
                    news.getId());

            return new ResponseEntity<>(HttpStatus.OK);
        }

    }

    @DeleteMapping("/delete_news")
    public void deleteNews(@RequestBody News news) {
        newsRepository.delete(news);
    }


//    public static String decode (String str){
//        Base64.Decoder  decoder = Base64.getDecoder();
//        byte[] decoded = decoder.decode(str);
//
//    }

//    private void saveFile(Product product, @RequestParam("file") MultipartFile file) throws IOException {
////        Base64.Decoder decoder = Base64.getDecoder();
////        byte[] bytes = decoder.decode(encodedString);
//        if (file != null && !file.getOriginalFilename().isEmpty()) {
//            product.setFilename("");
//            File uploadDir = new File(uploadPath);
//
//            if (!uploadDir.exists()) {
//                uploadDir.mkdir();
//            }
//            String uuidFile = UUID.randomUUID().toString();
//            String resultFilename = uuidFile + "." + file.getOriginalFilename();
//
//            file.transferTo(new File(uploadPath + "/" + resultFilename));
//            product.setFilename(resultFilename);
//        }
//    }

//    public ResponseEntity<?> saveFile(Product product, @RequestParam("file") MultipartFile file)
//    {

//    private void saveFileTwo(News news, @RequestParam("file1") MultipartFile file1) throws IOException {
//        if (file1 == null) {
//            news.setFilename("empty.jpg");
//        } else {
//            File uploadDir = new File(uploadPath);
//
//            if (!uploadDir.exists()) {
//                uploadDir.mkdir();
//            }
//            String uuidFile = UUID.randomUUID().toString();
//            String resultFilename = uuidFile + "." + file1.getOriginalFilename();
//
//            file1.transferTo(new File(uploadPath + "/" + resultFilename));
//            news.setFilename(resultFilename);
//        }
//    }
}

