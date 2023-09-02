package dev.mkind.lox;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScannerTest {

    @Test
    void testScannerWorks() {
        var scanner = new Scanner("1 + 2");
        var tokens = scanner.scanTokens();

        var expect = List.of(
                new Token(TokenType.NUMBER, "1", 1.0, 1),
                new Token(TokenType.PLUS, "+", null, 1),
                new Token(TokenType.NUMBER, "2", 2.0, 1),
                new Token(TokenType.EOF, "", null, 1)
        );

        assertEquals(expect, tokens);
    }
}