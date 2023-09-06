package dev.mkind.lox;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterTest {

    @Test
    void testInterpreterWorks() {
        var scanner = new Scanner("""
            var a = 1;
            var b = 2;
            print a + b;
        """);
        var parser = new Parser(scanner.scanTokens());
        var stmts = parser.parse();
        var interpreter = new Interpreter();

        interpreter.interpret(stmts);
    }
}