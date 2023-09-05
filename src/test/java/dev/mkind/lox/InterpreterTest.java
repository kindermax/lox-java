package dev.mkind.lox;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterTest {

    @Test
    void testInterpreterWorks() {
        var scanner = new Scanner("print 1;");
        var parser = new Parser(scanner.scanTokens());
        var stmts = parser.parse();
        var interpreter = new Interpreter();

        interpreter.interpret(stmts);
    }
}