package com.piglatin.zetariano.domain.symboltable;

import com.piglatin.zetariano.domain.ast.enums.AccessModifier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class VariableSymbol extends Symbol{

    private final String type;
    private Object constantValue;
    private AccessModifier modifier;

    public VariableSymbol(AccessModifier modifier, String name,  String type, int line, int column) {
        super(name, line, column);
        this.type = type;
        this.constantValue = null;
        this.modifier = modifier;
    }

    public VariableSymbol(String name, String type, int line, int column) {
        super(name, line, column);
        this.type = type;
        this.constantValue = constantValue;
    }

    public boolean isConstant() {
        return constantValue != null;
    }
}
