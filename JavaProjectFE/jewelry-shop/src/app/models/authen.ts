export interface AuthenResponse {
    code:number,
    message:string,
    data:any
}

export interface AuthenRequest {
    username: string,
    password: string
}
export interface AuthenToken{
    token:any
}