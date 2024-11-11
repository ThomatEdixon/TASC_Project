import { SafeUrl } from "@angular/platform-browser";

export interface ProductImage {
    id: number;
    imageUrl: string;
    altText: string;
  }
  
export interface ProductResponse {
    name: string;
    description: string;
    price: number;
    stock_quantity: number;
    categoryName: string;
    brandName: string;
    productImages: ProductImage[];
    imageUrl?: SafeUrl;
  }
export interface ProductRequest {
  name: string;
  description: string;
  price: number;
  originalPrice: number;
  stockQuantity: number;
  categoryId: string;
  brandId: string;
}

export interface Material {
  name: string;
  description: string;
}
