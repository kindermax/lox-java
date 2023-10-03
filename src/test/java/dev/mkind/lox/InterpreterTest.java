package dev.mkind.lox;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
                    print a + b;
                """);
        var parser = new Parser(scanner.scanTokens());
        var stmts = parser.parse();
        var interpreter = new Interpreter();

        interpreter.interpret(stmts);
        assertEquals("3\n", outContent.toString());
  }

  @Test
  void testScopingWorks() {
        var scanner = new Scanner("""
                    var a = 1;
                    {
      var a = 5;
      print a;
                    }
                    print a;
                """);
        var parser = new Parser(scanner.scanTokens());
        var stmts = parser.parse();
        var interpreter = new Interpreter();

        interpreter.interpret(stmts);
        assertEquals("5\n1\n", outContent.toString());
  }

  @Test
  void testIfConditionWorks() {
        var scanner = new Scanner("""
                    if (1 + 1 == 2)
                        print "two";
                """);
        var parser = new Parser(scanner.scanTokens());
        var stmts = parser.parse();
        var interpreter = new Interpreter();

        interpreter.interpret(stmts);
        assertEquals("two\n", outContent.toString());
  }

  @Test
  void testIfConditionWithOperatorsWorks() {
        var scanner = new Scanner("""
                    print "hi" or 2;
                    print nil or "yes";
                """);
        var parser = new Parser(scanner.scanTokens());
        var stmts = parser.parse();
        var interpreter = new Interpreter();

        interpreter.interpret(stmts);
        assertEquals("hi\nyes\n", outContent.toString());
  }

  @Test
  void testWhileStatementWorks() {
        var scanner = new Scanner("""
                    var i = 0;
                    while (i < 1) {
      print "hi";
      i = i + 1;
                    }
                """);
        var parser = new Parser(scanner.scanTokens());
        var stmts = parser.parse();
        var interpreter = new Interpreter();

        interpreter.interpret(stmts);
        assertEquals("hi\n", outContent.toString());
  }

  @Test
  void testForStatementWorks() {
        var scanner = new Scanner("""
                    for (var i = 0; i < 1; i = i + 1) {
      print "hi";
                    }
                """);
        var parser = new Parser(scanner.scanTokens());
        var stmts = parser.parse();
        var interpreter = new Interpreter();

        interpreter.interpret(stmts);
        assertEquals("hi\n", outContent.toString());
  }

  @Test
  void testFunctionStmtExecutionWorks() {
        var scanner = new Scanner("""
                    fun hi() {
      print "hi";
                    }
                    hi();
                """);
        var parser = new Parser(scanner.scanTokens());
        var stmts = parser.parse();
        var interpreter = new Interpreter();

        interpreter.interpret(stmts);
        assertEquals("hi\n", outContent.toString());
  }
}
