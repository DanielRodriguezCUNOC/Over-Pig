package com.piglatin.y.domain.ast.statements;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NodeChoose extends ASTNode {

    private ASTNode expression;
    private List<NodeChooseCase> cases;
    private NodeChooseCase defaultCase;

    public NodeChoose() {
        this(null, new ArrayList<>(), null, 0, 0);
    }

    public NodeChoose(int line, int column) {
        this(null, new ArrayList<>(), null, line, column);
    }

    public NodeChoose(ASTNode expression, List<NodeChooseCase> cases, NodeChooseCase defaultCase, int line, int column) {
        super(line, column);
        this.expression = expression;
        this.cases = cases != null ? cases : new ArrayList<>();
        this.defaultCase = defaultCase;
    }

    public void addCase(NodeChooseCase caseNode) {
        if (cases == null) {
            this.cases = new ArrayList<>();
        }
        this.cases.add(caseNode);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("elegir(").append(expression.toString()).append("):\n");
        for (NodeChooseCase c : cases) {
            sb.append(c.toString());
        }
        if (defaultCase != null) {
            sb.append(defaultCase.toString());
        }
        return sb.toString();
    }

    @Override
    public String getTipoNodo() {
        return "Choose";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitChoose(this);
    }
}
