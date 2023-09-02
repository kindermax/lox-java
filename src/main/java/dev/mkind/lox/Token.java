package dev.mkind.lox;

import java.util.Objects;

public class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    Token(
            TokenType type,
            String lexeme,
            Object literal,
            int line
    ) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    public String toString() {
        return type + " " + lexeme + " " + literal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, lexeme, literal, line);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Token token) {
            return type == token.type && Objects.equals(lexeme, token.lexeme)
                    && Objects.equals(literal, token.literal) && this.line == token.line;
        }

        return false;
    }
}
