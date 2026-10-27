package com.piglatin.zetariano.domain.symboltable;

import com.piglatin.zetariano.domain.ast.enums.AccessModifier;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public abstract class Symbol {

    protected final String name;
    protected AccessModifier accessModifier;
    protected final int line, column;

    protected Symbol(String name, AccessModifier accessModifier, int line, int column) {
        this.name = name;
        this.accessModifier = accessModifier;
        this.line = line;
        this.column = column;
    }

    protected Symbol(String name, int line, int column) {
        this(name, null, line, column);
    }
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