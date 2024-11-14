import { SafeUrl } from "@angular/platform-browser";

export interface ProductImage {
    imageId: string;
    productId: string;
    imageUrl: string;
    altText: string;
  }
  
export interface ProductResponse {
    productId:string
    name: string;
    description: string;
    price: number;
    stockQuantity: number;
    categoryName: string;
    brandName: string;
    productImages: ProductImage[];
    imageUrl?: SafeUrl;
    materials : Material[];
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
