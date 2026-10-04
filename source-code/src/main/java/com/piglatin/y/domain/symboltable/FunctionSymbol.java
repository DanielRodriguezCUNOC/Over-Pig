package com.piglatin.y.domain.symboltable;

import lombok.Getter;

import java.util.List;

@Getter
public class FunctionSymbol extends Symbol {

    private final String returnType;
    private final List<ParameterType> parameterTypes;

    public FunctionSymbol(String name, String returnType, List<ParameterType> parameterTypes, int line, int column) {
        super(name, line, column);
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
    }

}