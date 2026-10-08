package com.piglatin.zetariano.domain.symboltable;

import com.piglatin.zetariano.domain.ast.enums.AccessModifier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class Symbol {

    protected final String name;
    protected AccessModifier accessModifier;
    protected final int line, column;
    public String getId() {
        return name;
    }

    public String getType() {
        return "UNKNOWN";
    }

    public String getScope() {
        return "global";
    }

}
