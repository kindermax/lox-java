package dev.mkind.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      System.err.println("Usage: generate_ast <output directory>");
      System.exit(64);
    }
    String outputDir = args[0];
    defineAst(
        outputDir, "Expr",
        Arrays.asList("Assign   : Token name, Expr value",
            "Binary   : Expr left, Token operator, Expr right",
            "Call : Expr callee, Token paren, List<Expr> arguments",
            "Grouping : Expr expression", "Literal  : Object value",
            "Logical  : Expr left, Token operator, Expr right",
            "Unary    : Token operator, Expr right",
            "Variable : Token name"));

    defineAst(
        outputDir, "Stmt",
        Arrays.asList(
            "Block      : List<Stmt> statements",
            "Expression : Expr expression",
            "Function   : Token name, List<Token> params, List<Stmt> body",
            "If         : Expr condition, Stmt thenBranch, Stmt elseBranch",
            "Print      : Expr expression",
            "Return     : Token keyword, Expr value",
            "Var        : Token name, Expr initializer",
            "While      : Expr condition, Stmt body"));
  }

  private static void defineAst(String outputDir, String baseName,
      List<String> types) throws IOException {
    String path = outputDir + "/" + baseName + ".java";
    PrintWriter writer = new PrintWriter(path, "UTF-8");

    writer.println("package dev.mkind.lox;");
    writer.println();
    writer.println("import java.util.List;");
    writer.println();
    writer.println("abstract class " + baseName + " {");

    defineVisitor(writer, baseName, types);

    // The AST classes
    for (String type : types) {
      String[] parts = type.split(":");
      String className = parts[0].trim();
      String fields = parts[1].trim();
      defineType(writer, baseName, className, fields);
    }

    // The base accept method;
    writer.println();
    writer.println("  abstract <R> R accept(Visitor<R> visitor);");

    writer.println("}");
    writer.close();
  }

  private static void defineType(PrintWriter writer, String baseName,
      String className, String fieldList) {
    writer.println(
        String.format("  static class %s extends %s {", className, baseName));
    // Constructor
    writer.println(String.format("    %s(%s) {", className, fieldList));

    // Store params in fields
    String[] fields = fieldList.split(", ");
    for (String field : fields) {
      String name = field.split(" ")[1].trim();
      writer.println(String.format("        this.%1$s = %1$s;", name));
    }

    writer.println("    }");

    // Visitor pattern
    writer.println();
    writer.println("    @Override");
    writer.println("    <R> R accept(Visitor<R> visitor) {");
    writer.println(String.format("      return visitor.visit%s%s(this);",
        className, baseName));
    writer.println("    }");

    // Fields
    writer.println();
    for (String field : fields) {
      writer.println(String.format("    final %s;", field));
    }

    writer.println("  }");
  }

  private static void defineVisitor(PrintWriter writer, String baseName,
      List<String> types) {
    writer.println("  interface Visitor<R> {");

    for (String type : types) {
      String typeName = type.split(":")[0].trim();
      writer.println(String.format("    R visit%s%s(%s %s);", typeName,
          baseName, typeName, baseName.toLowerCase()));
    }

    writer.println("  }");
  }
}
