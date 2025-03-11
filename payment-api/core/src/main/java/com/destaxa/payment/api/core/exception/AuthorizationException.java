package com.destaxa.payment.api.core.exception;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException(String message){
        super(message);
    }

    public AuthorizationException(){
        super();
    }

}
