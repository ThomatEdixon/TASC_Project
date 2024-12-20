export interface UserRequest{
    username:string,
    password:string,
    email:string,
    phoneNumber:string,
    dob:string,
    address:string
}
export interface UserResponse{
    username:string,
    email:string,
    phoneNumber:string,
    dob:string,
    address:string,
    role:any,
}
export interface ForgotPassWordRequest{
    username:string;
    email:string
}
export interface ChangePasswordRequest{
    newPassword:string;
}
export interface VerifyRequest{
    otp:string
}