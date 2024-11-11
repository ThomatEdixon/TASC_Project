package org.jewelryshop.productservice.controllers;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.productservice.dto.response.ApiResponse;
import org.jewelryshop.productservice.entities.ProductImage;
import org.jewelryshop.productservice.services.impl.ProductImageServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping(value = "/upload")
@RequiredArgsConstructor
public class ProductImageController {
    private final ProductImageServiceImpl productImageService;
    @PostMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<List<ProductImage>> uploadImages(
            @PathVariable("id") String productId,
            @ModelAttribute("files") MultipartFile[] files) {

        try {
            return ApiResponse.<List<ProductImage>>builder()
                    .data(productImageService.uploadImages(productId,files))
                    .build();
        } catch (IllegalArgumentException e) {
            return ApiResponse.<List<ProductImage>>builder()
                    .message(e.getMessage())
                    .build();
        } catch (IOException e) {
            return ApiResponse.<List<ProductImage>>builder()
                    .message(e.getMessage())
                    .build();
        }
    }
//    @GetMapping("/{imageName}")
//    public ResponseEntity<Resource> viewImage(@PathVariable String imageName) {
//        try {
//            Resource image = productImageService.loadImageResponse(imageName);
//            MediaType mediaType = MediaType.IMAGE_JPEG;
//
//            return ResponseEntity.ok()
//                    .contentType(mediaType)
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imageName + "\"")
//                    .body(image);
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
