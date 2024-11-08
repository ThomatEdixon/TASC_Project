package org.jewelryshop.productservice.controllers;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.productservice.services.impl.FakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/fake-data")
@RequiredArgsConstructor
public class FakeController {
    @Autowired
    private final FakeService fakeService;
    @GetMapping("/generate")
    public ResponseEntity<String> generateFakeData() {
        try {
            fakeService.initFakeData();
            return ResponseEntity.ok("Fake data generated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to generate fake data");
        }
    }
    @GetMapping("/generate/product-material")
    public ResponseEntity<String> generateFakeDataProductMaterial() {
        try {
            fakeService.initFakeDataProductMaterial();
            return ResponseEntity.ok("Fake data generated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to generate fake data");
        }
    }
}
