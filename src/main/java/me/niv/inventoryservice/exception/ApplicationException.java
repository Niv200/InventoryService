package me.niv.inventoryservice.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException{

    private HttpStatus status;

    public ApplicationException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }

    public ApplicationException(String message, Throwable cause, HttpStatus status){
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus(){
        return this.status;
    }

}
