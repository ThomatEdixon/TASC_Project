export interface PaymentRequest{
    orderId:any;
    paymentMethod:string
}
export interface PaymentResponse{
    paymentId: string ;
    orderId : string ;
    paymentMethod: string ;
    paymentStatus: string ;
    orderCode: number ;
    createdAt: string ;
    checkoutUrl: string ;
}