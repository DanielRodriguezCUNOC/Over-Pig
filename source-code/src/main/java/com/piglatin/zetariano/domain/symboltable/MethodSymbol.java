package com.piglatin.zetariano.domain.symboltable;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MethodSymbol extends Symbol{

    private final String returnType;
    private final List<String> parameterTypes;

    public MethodSymbol(String name, String returnType, List<String> parameterTypes, int line, int column) {
        super(name, line, column);
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
    }
}
