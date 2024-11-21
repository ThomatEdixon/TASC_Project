import { Component, OnInit } from '@angular/core';
import { Material, ProductResponse } from '../../../../models/product';
import { ProductService } from '../../../../services/product.service';
import { Router } from '@angular/router';
import { CategoryService } from '../../../../services/category.service';
import { CategoryResponse } from '../../../../models/category';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Brand, BrandResponse } from '../../../../models/brand';
import { BrandService } from '../../../../services/brand.service';
import { MaterialService } from '../../../../services/material.service';
interface Filter {
  label: string;
  value: any;
}
@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  products: ProductResponse[] = [];
  categories: CategoryResponse[] = [];
  brands: Brand[] = [];
  materials: Material[] = [];

  isSearchFormVisible = false;
  selectedFilters: Filter[] = [];
  searchForm!: FormGroup;

  dropdowns = {
    material: false,
    category: false,
    brand: false
  };
  minPrice !: string;
  maxPrice !: string;

  currentPage = 1;
  itemsPerPage = 9;
  visiblePages = [1, 2, 3];
  totalPage = Math.ceil(this.products.length / this.itemsPerPage);
  constructor(
    private productService: ProductService,
    private router: Router,
    private categoryService: CategoryService,
    private fb: FormBuilder,
    private brandService: BrandService,
    private materialService: MaterialService
  ) { }

  ngOnInit() {
    this.initForm();
    this.getAll();
    this.getAllCategory();
    this.getAllBrand();
    this.getAllMaterial();
  }
  initForm() {
    this.searchForm = this.fb.group({
      keyword: [''],
      minPrice: [0],
      maxPrice: [5000],
      material: [''],
      brand: ['']
    });
  }
  toggleDropdown(type: 'material' | 'category' | 'brand') {
    this.dropdowns[type] = !this.dropdowns[type];
  }

  onFilterChange(filterName: string, value: string) {
    console.log(filterName);

    if (!this.isSearchFormVisible) {
      this.isSearchFormVisible = true;
    }

    this.searchForm.patchValue({ [filterName]: value });

    this.selectedFilters = this.selectedFilters.filter(f => f.label !== filterName);
    if (value) {
      this.selectedFilters.push({ label: filterName, value });
    }
  }

  removeFilter(filter: any) {
    this.selectedFilters = this.selectedFilters.filter(f => f !== filter);
    this.searchForm.patchValue({ [filter.label]: '' });
  }
  onChangePage(page: number) {
    this.currentPage = page;
    this.getAll();
  }

  getAll() {
    this.productService.getProducts(this.currentPage, this.itemsPerPage).pipe().subscribe((res) => {
      this.products = res.data.content;
      this.totalPage = res.data.totalPages; 
    });
  }

  updateVisiblePages() {
    this.visiblePages = Array.from({ length: this.totalPage }, (_, i) => i + 1)
      .slice(Math.max(this.currentPage - 1, 0), this.currentPage + 2);
  }
  getAllCategory() {
    this.categoryService.getAll().pipe().subscribe((res) => {
      if (res.errorCode == 100) {
        this.categories = res.data;
      }
    });
  }
  getAllMaterial() {
    this.materialService.getAll().pipe().subscribe((res) => {
      if (res.errorCode == 100) {
        this.materials = res.data;
      }
    });
  }
  getAllBrand() {
    this.brandService.getAll().pipe().subscribe((res) => {
      if (res.errorCode == 100) {
        this.brands = res.data;
      }
    });
  }
  searchProducts() {
    // Logic tìm kiếm sản phẩm theo keyword và category
  }
  get paginatedProducts() {
    const start = this.currentPage * this.itemsPerPage;
    return this.products.slice(start, start + this.itemsPerPage);
  }
}
