package com.piglatin.piglatin.domain.ast.nodes.expression;

import com.piglatin.piglatin.domain.ast.nodes.declaration.NodeVariableDeclaration;
import com.piglatin.piglatin.domain.ast.nodes.instruction.NodeBlock;
import com.piglatin.zetariano.domain.ast.principal.ASTNode;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class NodeFunctionDeclaration extends ASTNode {
        private final String identifier;
        private final String returnType;
        private final List<NodeVariableDeclaration> parameters;
        private final NodeBlock body;

        public NodeFunctionDeclaration(String identifier, String returnType, List<NodeVariableDeclaration> parameters, NodeBlock body, int line, int column) {
            super(line, column);
            this.identifier = identifier;
            this.returnType = returnType;
            this.parameters = parameters != null ? parameters : new ArrayList<>();
            this.body = body;
        }

        public String getIdentifier() {
            return identifier;
        }

        public String getReturnType() {
            return returnType;
        }

        public List<NodeVariableDeclaration> getParameters() {
            return parameters;
        }

        public NodeBlock getBody() {
            return body;
        }

        @Override
        public String toString() {
            return "NodeFunctionDeclaration{" +
                    "identifier='" + identifier + '\'' +
                    ", returnType='" + returnType + '\'' +
                    ", parameters=" + parameters +
                    ", body=" + body +
                    '}';
        }

    @Override
    public String getTipoNodo() {
        return "FunctionDeclaration";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return null;
    }
}
