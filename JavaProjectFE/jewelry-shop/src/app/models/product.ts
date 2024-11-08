import { SafeUrl } from "@angular/platform-browser";

export interface ProductImage {
    id: number;
    imageUrl: string;
    altText: string;
  }
  
export interface Product {
    name: string;
    description: string;
    price: number;
    stock_quantity: number;
    categoryName: string;
    brandName: string;
    productImages: ProductImage[];
    imageUrl?: SafeUrl;
  }