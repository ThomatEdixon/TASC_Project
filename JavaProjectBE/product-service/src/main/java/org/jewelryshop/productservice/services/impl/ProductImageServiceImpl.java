package org.jewelryshop.productservice.services.impl;

import org.jewelryshop.productservice.DAO.impl.ProductImageDAOImpl;
import org.jewelryshop.productservice.dto.request.ProductImageRequest;
import org.jewelryshop.productservice.entities.ProductImage;
import org.jewelryshop.productservice.mappers.ProductImageMapper;
import org.jewelryshop.productservice.services.ProductImageService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        List<ProductImageRequest> productImagesRequest = convertToProductImages(productId,files);
        List<ProductImage> productImages = new ArrayList<>();
        for(ProductImageRequest productImage : productImagesRequest){
            productImageDAO.save(productImage);
            productImages.add(productImageMapper.toProductImage(productImage));
        }
        return productImages;
    }

    @Override
    public void update(String imageId, MultipartFile[] files) throws IOException {
        ProductImage existingImage = productImageDAO.getById(imageId);
        if (existingImage == null) {
            throw new NoSuchElementException("Không tìm thấy ảnh với ID: " + imageId);
        }
        List<ProductImageRequest> productImagesRequest = convertToProductImages(existingImage.getProductId(), files);
        for(ProductImageRequest productImage : productImagesRequest){
            productImageDAO.update(imageId,productImageMapper.toProductImage(productImage));
        }
    }

    @Override
    public ProductImage getById(String imageId) {
        return null;
    }

    @Override
    public void delete(String imageId) {
    productImageDAO.delete(imageId);
    }

    public String uploadToImgur(MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Client-ID " + IMGUR_CLIENT_ID);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
        body.add("image", base64Image);
        body.add("type", "base64");

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
    public List<ProductImageRequest> convertToProductImages(String productId, MultipartFile[] files) throws IOException {
        if (files.length > MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new IllegalArgumentException("Chỉ được phép tải tối đa 5 ảnh");
        }

        List<ProductImageRequest> productImages = new ArrayList<>();
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

            productImages.add(productImageRequest);
        }
        return productImages;
    }
}
