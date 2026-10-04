package com.piglatin.zetariano.domain.symboltable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class VariableSymbol extends Symbol{

    private final String type;
    private Object constantValue;

    public VariableSymbol(String name,  String type, int line, int column) {
        super(name, line, column);
        this.type = type;
        this.constantValue = null;
    }

    public boolean isConstant() {
        return constantValue != null;
    }
}
