import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductService } from '../../../../services/product.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MaterialService } from '../../../../services/material.service';
import { CategoryService } from '../../../../services/category.service';
import { BrandService } from '../../../../services/brand.service';
import { CategoryResponse } from '../../../../models/category';
import { BrandResponse } from '../../../../models/brand';
import { Material, ProductRequest, ProductResponse } from '../../../../models/product';

@Component({
  selector: 'app-product-edit',
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.css']
})
export class ProductEditComponent implements OnInit {

  formUpdateProduct!: FormGroup;
  formUpdateProductImage!: FormGroup;
  formUpdateMaterial!: FormGroup;

  categories: CategoryResponse[] = [];
  brands: BrandResponse[] = [];
  materials: Material[] = [];
  selectedFiles: File[] = [];
  currentId: any;
  product!: ProductResponse;
  categoryId!:String;
  brandId!:String;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private categoryService: CategoryService,
    private brandService: BrandService,
    private materialService: MaterialService,
    private router: Router,
    private activatedRouter: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.activatedRouter.queryParams.subscribe(params => {
      this.currentId = params['productId'];
    });

    this.initializeForms();
    this.loadCategoriesAndBrandsAndMaterials();
    this.loadProductData(this.currentId);
  }

  compareById(item1: any, item2: any): boolean {
    return item1 && item2 && item1 === item2;
  }
  findBrandId(name:String){
    this.brands.forEach(element => {
      if(element.name == name){
        this.brandId = element.brandId
      }
    });
  }

  findCategoryId(name:String){
    this.categories.forEach(element => {
      if(element.name == name){
        this.categoryId = element.categoryId
      }
    });
  }

  initializeForms(): void {
    this.formUpdateProduct = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', Validators.required],
      stockQuantity: ['', Validators.required],
      categoryId: ['', Validators.required],
      brandId: ['', Validators.required],
    });

    this.formUpdateProductImage = this.fb.group({
      imageUrl: [null]
    });

    this.formUpdateMaterial = this.fb.group({
      materialName: ['', Validators.required]
    });
  }

  loadCategoriesAndBrandsAndMaterials(): void {
    this.categoryService.getAll().subscribe((categories) => {
      this.categories = categories.data;
    });
    this.brandService.getAll().subscribe((brands) => {
      this.brands = brands.data;
    });
    this.materialService.getAll().subscribe((materials) => {
      this.materials = materials.data;
    });
  }

  loadProductData(productId: string): void {
    this.productService.getProductById(productId).subscribe((productResponse) => {
      this.product = productResponse.data;
      this.findBrandId(this.product.brandName);
      this.findCategoryId(this.product.categoryName);
      this.formUpdateProduct.patchValue({
        name: this.product.name,
        description: this.product.description,
        price: this.product.price,
        stockQuantity: this.product.stockQuantity,
        categoryId: this.categoryId,  
        brandId: this.brandId        
      });

      // Kiểm tra nếu có dữ liệu hình ảnh
      if (this.product.imageUrl) {
        this.formUpdateProductImage.patchValue({
          imageUrl: this.product.imageUrl
        });
      }

      // Điền thông tin material nếu có
      if (this.product.materials && this.product.materials.length > 0) {
        this.formUpdateMaterial.patchValue({
          materialName: this.product.materials[0].name  // Sử dụng tên của material đầu tiên
        });
      }
    });
  }

  async onUpdateProduct() {
    try {
      if (this.formUpdateProduct.invalid || this.formUpdateProductImage.invalid || this.formUpdateMaterial.invalid) {
        return;
      }

      const productData: ProductRequest = {
        ...this.formUpdateProduct.value,
        materials: [this.formUpdateMaterial.value.materialName]
      };

      const productId = this.currentId;

      await this.productService.updateProduct(productId, productData).toPromise();

      // Cập nhật ảnh sản phẩm nếu có file được chọn
      if (this.selectedFiles.length > 0) {
        await this.productService.updateProductImage(this.product.productImages[0].imageId, this.selectedFiles).toPromise();
      }

      console.log('Product updated successfully');
    } catch (error) {
      console.error('Error during product update:', error);
    }
  }

  onFileChange(event: any) {
    if (event.target.files && event.target.files.length > 0) {
      this.selectedFiles = Array.from(event.target.files);
    }
  }
}
