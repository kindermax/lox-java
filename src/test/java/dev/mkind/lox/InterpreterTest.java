package dev.mkind.lox;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterTest {

    @Test
    void testInterpreterWorks() {
        var scanner = new Scanner("(1 + 1) > 1");
        var parser = new Parser(scanner.scanTokens());
        var expr = parser.parse();
        var interpreter = new Interpreter();

        var result = interpreter.evaluate(expr);

        assertTrue((boolean) result);
    }
}