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

    void interpret(String src) {
        var scanner = new Scanner(src);
        var parser = new Parser(scanner.scanTokens());
        var stmts = parser.parse();
        var interpreter = new Interpreter();
        Resolver resolver = new Resolver(interpreter);
        resolver.resolve(stmts);
        interpreter.interpret(stmts);
    }

    @Test
    void testInterpreterWorks() {
        interpret("""
                    var a = 1;
                    var b = 2;
                    print a + b;
                """);
        assertEquals("3\n", outContent.toString());
    }

    @Test
    void testScopingWorks() {
        interpret("""
                                var a = 1;
                                {
                var a = 5;
                print a;
                                }
                                print a;
                            """);
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
        interpret("""
                    print "hi" or 2;
                    print nil or "yes";
                """);
        assertEquals("hi\nyes\n", outContent.toString());
    }

    @Test
    void testWhileStatementWorks() {
        interpret("""
                            var i = 0;
                            while (i < 1) {
                print "hi";
                i = i + 1;
                            }
                            """);
        assertEquals("hi\n", outContent.toString());
    }

    @Test
    void testForStatementWorks() {
        interpret("""
                            for (var i = 0; i < 1; i = i + 1) {
                print "hi";
                            }
                            """);
        assertEquals("hi\n", outContent.toString());
    }

    @Test
    void testFunctionStmtExecutionWorks() {
        interpret("""
                            fun hi() {
                print "hi";
                            }
                            hi();
                            """);
        assertEquals("hi\n", outContent.toString());
    }

    @Test
    void testFunctionStmtReturnWorks() {
        interpret("""
                            fun hi(name) {
                return "hi " + name;
                            }
                            print hi("Max");
                            """);
        assertEquals("hi Max\n", outContent.toString());
    }

    @Test
    void testClosuresWorks() {
        interpret("""
                        fun makeCounter() {
                var i = 0;
                fun count() {
                    i = i + 1;
                    print i;
                }

                return count;
                        }

                        var counter = makeCounter();
                        counter(); // "1".
                        counter(); // "2".
                            """);
        assertEquals("1\n2\n", outContent.toString());
    }

    @Test
    void testClosuresScopeImmutableWorks() {
        interpret("""
                        var a = "hi";
                        {
                fun greet() {
                    print a;
                }

                greet();
                var a = "hello";
                greet();
                        }
                            """);
        assertEquals("hi\nhi\n", outContent.toString());
    }
}
