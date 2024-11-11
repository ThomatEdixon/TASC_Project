package org.jewelryshop.productservice.services.impl;

import org.jewelryshop.productservice.DAO.impl.ProductImageDAOImpl;
import org.jewelryshop.productservice.dto.request.ProductImageRequest;
import org.jewelryshop.productservice.entities.ProductImage;
import org.jewelryshop.productservice.mappers.ProductImageMapper;
import org.jewelryshop.productservice.services.interfaces.ProductImageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductImageDAOImpl productImageDAO;
    private final ProductImageMapper productImageMapper;
    private static final int MAXIMUM_IMAGES_PER_PRODUCT = 5;
    private static final String IMGUR_API_URL = "https://api.imgur.com/3/upload";
    private static final String IMGUR_CLIENT_ID = "c3b6a0b440cd305";

    public ProductImageServiceImpl(ProductImageDAOImpl productImageDAO, ProductImageMapper productImageMapper) {
        this.productImageDAO = productImageDAO;
        this.productImageMapper = productImageMapper;
    }
    @Override
    public List<ProductImage> uploadImages(String productId, MultipartFile[] files) throws IOException {
        if (files.length > MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new IllegalArgumentException("Chỉ được phép tải tối đa 5 ảnh");
        }

        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty() || !file.getContentType().startsWith("image/")) continue;

            if (file.getSize() > 10 * 1024 * 1024) { // >10MB
                throw new IllegalArgumentException("Dung lượng ảnh tối đa là 10MB");
            }

            String imageUrl = uploadToImgur(file); // Gửi ảnh đến Imgur và lấy URL
            ProductImageRequest productImageRequest = ProductImageRequest.builder()
                    .productId(productId)
                    .imageUrl(imageUrl)
                    .build();
            productImageDAO.save(productImageRequest);

            productImages.add(productImageMapper.toProductImage(productImageRequest));
        }
        return productImages;
    }

    @Override
    public Resource loadImageResponse(String imageName) throws MalformedURLException {
        Path imagePath = Paths.get("uploads/" + imageName);
        UrlResource resource = new UrlResource(imagePath.toUri());

        if (resource.exists()) {
            return resource;
        } else {
            return new UrlResource(Paths.get("uploads/notfound.jpeg").toUri());
        }
    }

    public String uploadToImgur(MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Client-ID " + IMGUR_CLIENT_ID);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", file.getBytes());
        body.add("type", "file");

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(IMGUR_API_URL, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
            return (String) data.get("link");
        } else {
            throw new IOException("Không thể upload ảnh lên Imgur. Lỗi: " + response.getBody());
        }
    }
}
