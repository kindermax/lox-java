package dev.mkind.lox;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AstPrinterTest {

    @Test
    void testAstPrinterWorks() {
        Expr expression = new Expr.Binary(
                new Expr.Unary(
                        new Token(TokenType.MINUS, "-", null, 1),
                        new Expr.Literal(123)),
                new Token(TokenType.STAR, "*", null, 1),
                new Expr.Grouping(
                        new Expr.Literal(45.67)));
        var expect = "(* (- 123) (group 45.67))";
        var printed = new AstPrinter().print(expression);
        assertEquals(expect, printed);
    }
}