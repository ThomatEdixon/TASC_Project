export interface AuthenResponse {
    errorCode:number,
    message:string,
    data:any
}

export interface AuthenRequest {
    username: string,
    password: string
}
export interface AuthenToken{
    token:string
}