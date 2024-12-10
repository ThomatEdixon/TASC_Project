import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../../../../services/product.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Category, CategoryResponse } from '../../../../../models/category';
import { Brand, BrandResponse } from '../../../../../models/brand';
import { Material } from '../../../../../models/product';
import { Router } from '@angular/router';
import { BrandService } from '../../../../../services/brand.service';
import { CategoryService } from '../../../../../services/category.service';
import { MaterialService } from '../../../../../services/material.service';
import { fi } from 'date-fns/locale';

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.css']
})
export class ProductCreateComponent implements OnInit {
  formProduct!: FormGroup;
  formProductImage!: FormGroup;
  formMaterial!: FormGroup;

  categories : CategoryResponse[]  =[];
  brands : BrandResponse[] = [];
  materials: Material[] =[];
  selectedFiles: File[] =[];

  productCreated = false;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private brandService: BrandService,
    private categoryService:CategoryService,
    private materialService:MaterialService,
    private router:Router
  ) {}

  ngOnInit(): void {
    this.formProduct = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      originalPrice: ['', [Validators.required, Validators.min(0)]],
      categoryId: ['', Validators.required],
      brandId: ['', Validators.required]
    });

    this.formProductImage = this.fb.group({
      imageUrl: ['', Validators.required]
    });

    this.formMaterial = this.fb.group({
      materialId: ['', Validators.required]
    });
    this.getAllBrand();
    this.getAllCategory();
    this.getAllMaterial();
  }

  async onCreateProduct() {
    try {
      if (this.formProduct.invalid || this.formProductImage.invalid || this.formMaterial.invalid) {
        return;
      }
      const productData = this.formProduct.value;
      console.log(productData);
      const productResponse = await this.productService.createProduct(productData).toPromise();
      console.log(productResponse);
      const productId = productResponse?.data.productId;

      // Thêm product image
      if (this.selectedFiles.length > 0) {
        await this.productService.addProductImage(productId, this.selectedFiles).toPromise();
      } else {
        console.error('No image files selected');
      }

      // Thêm material
      const materialData = this.formMaterial.value;
      await this.productService.addMaterial(productId, materialData).toPromise();

      console.log('Product created successfully');
      this.resetForms();
    } catch (error) {
      console.error('Error during product creation:', error);
    }
  }
  onFileChange(event: any) {
    if (event.target.files && event.target.files.length > 0) {
      this.selectedFiles = Array.from(event.target.files); 
    }
  }
  

  resetForms() {
    this.formProduct.reset();
    this.formProductImage.reset();
    this.formMaterial.reset();
    this.productCreated = false;
  }
  getAllMaterial(){
    this.materialService.getAll().pipe().subscribe((res)=>{
      if(res.errorCode ==  200){
        this.materials = res.data;
        console.log(res)
      }
    });
  }
  getAllBrand(){
    this.brandService.getAll().pipe().subscribe((res)=>{
      if(res.errorCode ==  200){
        this.brands = res.data;
      }
    });
  }
  getAllCategory(){
    this.categoryService.getAll().pipe().subscribe((res)=>{
      if(res.errorCode ==  200){
        this.categories = res.data;
      }
    });
  }
}

