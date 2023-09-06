package dev.mkind.lox;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testInterpreterWorks() {
        var scanner = new Scanner("""
            var a = 1;
            var b = 2;
            b = 3;
            print a + b;
        """);
        var parser = new Parser(scanner.scanTokens());
        var stmts = parser.parse();
        var interpreter = new Interpreter();

        interpreter.interpret(stmts);
        assertEquals("4", outContent.toString().trim());
    }
}