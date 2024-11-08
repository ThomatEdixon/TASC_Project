package org.jewelryshop.productservice.services.impl;

import org.jewelryshop.productservice.DAO.ProductImageDAO;
import org.jewelryshop.productservice.dto.request.ProductImageRequest;
import org.jewelryshop.productservice.entities.ProductImage;
import org.jewelryshop.productservice.mappers.ProductImageMapper;
import org.jewelryshop.productservice.services.interfaces.IProductImageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductImageService implements IProductImageService {
    private final ProductImageDAO productImageDAO;
    private static final int MAXIMUM_IMAGES_PER_PRODUCT = 5;

    public ProductImageService(ProductImageDAO productImageDAO) {
        this.productImageDAO = productImageDAO;
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

            String filename = storeFile(file);
            ProductImageRequest productImageRequest = ProductImageRequest.builder()
                    .productId(productId)
                    .imageUrl(filename)
                    .build();
            productImageDAO.save(productImageRequest);

            productImages.add(ProductImageMapper.toProductImage(productImageRequest));
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

    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        Path uploadDir = Paths.get("uploads");

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
}
