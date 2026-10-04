package com.piglatin.zetariano.domain.symboltable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassSymbol extends Symbol {

    public ClassSymbol(String className, int line, int column) {
        super(className, line, column);
    }
}
