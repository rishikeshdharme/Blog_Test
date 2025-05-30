package com.example.Blog_test.exception;

public class ResourceNotFoundException extends RuntimeException{

    private  String resourceName;
    private String fieldName;
    private Integer fieldValue;

    public ResourceNotFoundException(String resourceName,String fieldName,Integer fieldValue){
        super(String.format("%s is not found with %s : %s",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue  = fieldValue;

    }
}
