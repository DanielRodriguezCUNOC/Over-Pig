package com.piglatin.zetariano.domain.symboltable;

import com.piglatin.zetariano.domain.ast.enums.AccessModifier;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MethodSymbol extends Symbol{

    private final String returnType;
    private final List<String> parameterTypes;
    private AccessModifier modifier;

    public MethodSymbol(AccessModifier modifier, String name, String returnType, List<String> parameterTypes, int line, int column) {
        super(name, line, column);
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
        this.modifier = modifier;
    }
}
