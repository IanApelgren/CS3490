
/*
 * RDPG_GI
 * Recursive Descent Parser Generator - Grammar Independent
 *
 */

import java.io.FileWriter;
import java.io.IOException;

public class RDPG_GI {
    public static void emitParentRDP() {
	System.out.print("emitting ParentRDP.java .....");
	openWriter("ParentRDP.java");
	emit(codeParentRDP);
	closeWriter();
	System.out.println("..done");
    }
    public static void emitTokens() {
	System.out.print("emitting Tokens.java .....");
	openWriter("Tokens.java");
	emit(codeTokens);
	closeWriter();
	System.out.println("..done");
    }
    public static void emitLexer() {
	System.out.print("emitting Lexer.java .....");
	openWriter("Lexer.java");
	emit(codeLexer);
	closeWriter();
	System.out.println("..done");
    }

    private static void openWriter(String filename) {
	try {
	    writer = new FileWriter(filename);
	}
	catch (IOException e) {
	    System.err.println("trying to create " + filename);
	    e.printStackTrace();
	}
    }
    private static FileWriter writer;
    private static void closeWriter() {
	try {
	    writer.close();
	}
	catch (IOException e) {
	    System.err.println("trying to close file");
	    e.printStackTrace();
	}
    }

    private static void emit(String[] code) {
	try {
	    for (int i=0; i < code.length; i++) {
		writer.write(code[i]);
	    }
	}
	catch (IOException e) {
	    System.err.println("trying to emit line");
	    e.printStackTrace();
	}
    }

    private static String[] codeLexer = {
	    "public interface Lexer {\n",
	    "   int yylex();\n",
	    "}\n"
    };

    private static String[] codeTokens = {
	    "public interface Tokens {\n",
	    "   public static int ERR_TOK = -1; // error",
	    "    public static int EOF_TOK = 0;  // normal end of file/input",
	    "}\n"
    };

    private static String[] codeParentRDP = {
	"public abstract class ParentRDP implements Tokens {\n",
	"\n",
	"    // Subclass should have something like: \n",
	"    // public void startSymbol() { S(); }\n",
	"    // where S is actually THEIR grammar's starting non-terminal\n",
	"    public abstract void startSymbol();\n",
	"\n",
	"    // Subclass will need to check token values in if-blocks...\n",
	"    protected int token;\n",
	"    \n",
	"    // This is the primary method provided to do the parse!\n",
	"    public boolean parse() {\n",
	"	// Get first token!\n",
	"	token = lexer.yylex();\n",
	"\n",
	"	// begin top-down derivation by calling start symbol\n",
	"	startSymbol();\n",
	"\n",
	"	// okay THEIR derivation is done successfully, \n",
	"	// make sure that there is no more input.\n",
	"	if (token != Tokens.EOF_TOK) {\n",
	"	    // oops, there is input remaining?\n",
	"	    parserError(\"Extraneous input...\");\n",
	"	}\n",
	"	return true;\n",
	"    }\n",
	"\n",
	"    public void match(int expectedToken) {\n",
	"	if (token == expectedToken) {\n",
	"	    token = lexer.yylex();\n",
	"	}\n",
	"	else {\n",
	"	    parserError(\"Expected \" + expectedToken + \" but read \" + token);\n",
	"	}\n",
	"    }\n",
	"    \n",
	"    public void parserError(String message) {\n",
	"	System.err.println();\n",
	"	System.err.println(\"Parser error: \" + message);\n",
	"	System.err.println();\n",
	"	System.exit(1);\n",
	"    }\n",
	"\n",
	"    public ParentRDP(Lexer lexer) {\n",
	"	// This comes from the subclass constructor\n",
	"	this.lexer = lexer;\n",
	"    }\n",
	"\n",
	"    private Lexer lexer;\n",
	"}\n"
    };
}
