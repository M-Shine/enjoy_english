package com.example.enjoy_english.aop;

public enum  OperationType {
    INSERT("insert"),

    UPDATE("update"),

    DELETE("delete");

    private String operationType;

    OperationType(String operationType){
        this.operationType = operationType;
    }
}
