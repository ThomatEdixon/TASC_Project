export interface OrderRequest{
    userId : any ;

    orderDate : string ;

    status: string ;

    orderDetails : OrderDetailRequest[] ;
}
export interface OrderDetailRequest{
    orderId :string ;
    productId: string ;
    quantity: number ;
    pricePerUnit: number ;
    totalPrice: number ;
}
export interface OrderResponse{
    orderId :string ;

    userId : string ;

    orderDate : string ;

    status: string ;

    totalAmount: number ;

    orderDetails : OrderDetailRequest[] ;

    paymentMethod: string
}
