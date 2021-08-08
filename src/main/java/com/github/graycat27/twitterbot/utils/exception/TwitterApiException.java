package com.github.graycat27.twitterbot.utils.exception;

public class TwitterApiException extends RuntimeException {

    public TwitterApiException(){
        super();
    }

    public TwitterApiException(String message){
        super(message);
    }

    public TwitterApiException(String message, Throwable cause){
        super(message, cause);
    }

    public TwitterApiException(Throwable cause){
        super(cause);
    }

}
