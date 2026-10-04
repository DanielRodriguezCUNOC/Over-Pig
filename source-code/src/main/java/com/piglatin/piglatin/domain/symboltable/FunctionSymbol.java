package com.piglatin.piglatin.domain.symboltable;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a function declared with 'ACTIO' or 'RATIO'
 * storage the return type and an ordered list of params
 */

@Getter
@Setter
public class FunctionSymbol extends Symbol{

    /** Indicate the type value that function returns
     * If return is 'ACTIO' returnType is null
     */
    private String returnType;

    //* Indicate de list of parameters that functions requiered
    private final List<VariableSymbol> parameters;

    public FunctionSymbol(String name, String type, int line, int column, String returnType) {
        super(name, type, line, column);
        this.returnType = returnType;
        this.parameters = new ArrayList<>();
    }

    public void addParameter(ParameterSymbol parameter) {
        this.parameters.add(parameter);
    }

    //* This method is util for error messages
    public String getSignature(){
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("(");

        for (int i = 0; i < parameters.size(); i++) {
            sb.append(parameters.get(i).getType());

            if (i < parameters.size() - 1) sb.append(", ");
        }
        sb.append(")");

        if (returnType != null) sb.append(" -> ").append(returnType);
        return sb.toString();
    }

    @Override
    public String getCategory() {
        return "function";
    }

    @Override
    public String toString() {
        return getCategory() + " " + getSignature() +
                " (line " + line + ", column " + column + ")";
    }
}
