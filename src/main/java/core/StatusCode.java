package core;

public enum StatusCode {
    SUCCESS(200,"Request is success"),
    CREATED(201,"request is created"),
    BAD_REQUEST(400,"missing something in the request body"),
    UN_AUTHORIZED(401,"you don't permission"),
    FORBIDDEN(402,"you don't have authorized to perform this action"),
    NOT_FOUND(404,"Not found"),
    NO_CONTENT(204,"No content");

    public final int code;
    public final String message;

    StatusCode(int code, String message){
        this.code = code;
        this.message = message;
    }


}
