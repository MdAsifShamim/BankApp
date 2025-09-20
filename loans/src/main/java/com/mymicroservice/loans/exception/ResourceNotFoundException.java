package com.mymicroservice.loans.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String resourceName,String fieldName,String filedValue ) {

        super(String.format("%s not found with the given input data %s : %S",resourceName,fieldName,filedValue));
    }


}
