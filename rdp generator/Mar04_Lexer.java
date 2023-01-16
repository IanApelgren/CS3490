/*
 * An "explicit, manually-coded lexical analyzer for
 * the RDP program.
 * ID, NUM, LPAR, RPAR, PLUS, MINUS, MUL, DIV, EQ, SEMI, PRINT
 *
 * Sample usage:
 * Lexer lexer = new Mar04_Lexer("a=99;print(a);");
 * int token = lexer.yylex();
 *
 * "Poor man's" unit testing:
 * javac Mar04_Lexer.java
 * java Mar04_Lexer "print(printer);prin=99+3-abc*b/c;"
 */

public class Mar04_Lexer implements Lexer, Mar04_Tokens {

    public static void main(String[] args) {
	if (args.length != 1) {
	    System.err.println("input string argument missing");
	    System.exit(1);
	}
	
	Lexer lexer = new Mar04_Lexer(args[0]);
	int currTok;
	while ( (currTok=lexer.yylex()) != EOF_TOK) {
	    switch (currTok) {
	    case NUM_TOK:
		System.out.println("Found token: NUM"); break;
	    case ID_TOK:
		System.out.println("Found token: ID"); break;
	    case PRINT_TOK:
		System.out.println("Found token: PRINT"); break;
	    case LPAR_TOK:
		System.out.println("Found token: LPAR"); break;
	    case RPAR_TOK:
		System.out.println("Found token: RPAR"); break;
	    case PLUS_TOK:
		System.out.println("Found token: PLUS"); break;
	    case MINUS_TOK:
		System.out.println("Found token: MINUS"); break;
	    case MUL_TOK:
		System.out.println("Found token: MUL"); break;
	    case DIV_TOK:
		System.out.println("Found token: DIV"); break;
	    case EQ_TOK:
		System.out.println("Found token: EQ"); break;
	    case SEMI_TOK:
		System.out.println("Found token: SEMI"); break;
	    default: 
		System.out.println("Found unknown token: "+currTok); break;
	    }
	}
    }

    // ------------------------------------------------------------------

    private char[] input;
    private char currCh;
    private int currIdx;
    private int state;

    public Mar04_Lexer(String input) {

	/* We will need to know when we are at the end of the input.
	   We could check currIdx against length of the input array.
	   But we opt instead to append a "special symbol" not in Sigma
	   that will serve as a marker.
	*/
	input += "$$";

	// Using String class charAt() is way more inefficient than
	//  char array access with an index.
	this.input = input.toCharArray();
	currIdx = 0;
	state = 0;
    }

    public int yylex() {
	// initialize this request from "parser" for a 
	int lastToken = EOF_TOK;
	state = 0;

	// get the first input character
	// trim off any leading whitespace
	currCh = input[currIdx];
	while (currCh == ' ' || currCh == '\t' || currCh == '\n') {
	    currCh = input[++currIdx];
	}
	    
	if (currCh == '$') { ++currIdx; return EOF_TOK; }

	if (currCh == '+') { ++currIdx; return PLUS_TOK; }
	if (currCh == '-') { ++currIdx; return MINUS_TOK; }
	if (currCh == '*') { ++currIdx; return MUL_TOK; }
	if (currCh == '/') { ++currIdx; return DIV_TOK; }
	if (currCh == '(') { ++currIdx; return LPAR_TOK; }
	if (currCh == ')') { ++currIdx; return RPAR_TOK; }
	if (currCh == ';') { ++currIdx; return SEMI_TOK; }
	if (currCh == '=') { ++currIdx; return EQ_TOK; }

	if ('0' <= currCh && currCh <= '9') {
	    // have a NUM_TOK, go get rest of digits
	    currCh = input[++currIdx];
	    while ('0' <= currCh && currCh <= '9') {
		currCh = input[++currIdx];
	    }
	    return NUM_TOK;
	}

	if ('a' <= currCh && currCh <= 'z') {
	    // have an ID_TOK or a PRINT_TOK
	    // brute force, short-circuited check for PRINT_TOK
	    if (currCh == 'p' && 'r' == input[++currIdx] && 'i' == input[++currIdx]
		&& 'n' == input[++currIdx] && 't' == input[++currIdx]) {
		
		// Definitely have all of 'print' are there more letters?
		currCh = input[++currIdx];
		if ('a' <= currCh && currCh <= 'z') {
		    // There are MORE letters
		    // NOT a PRINT_TOK, go get rest of letters
		    while ('a' <= currCh && currCh <= 'z') {
			currCh = input[++currIdx];
		    }
		    return ID_TOK;
		}
		else {
		    // Next char is NOT a letter so it was print and 
		    return PRINT_TOK;
		}
	    }
	    // NOT a PRINT_TOK, go get rest of letters
	    currCh = input[currIdx];
	    while ('a' <= currCh && currCh <= 'z') {
		currCh = input[++currIdx];
	    }
	    return ID_TOK;
	}

	lexError(currIdx, currCh);
	return EOF_TOK;
    }
    private void lexError(int index, char ch) {
	System.err.println("Error with input '" + input +
			   "'\nat index " + index
			   + " on char '" + ch + "'.");
	//System.exit(1);
    }
}
