public class Example_Lexer implements Example_Tokens, Lexer {
    private char[] input;
    private char currCh;
    private int currIdx;
    private int state;

    public Example_Lexer(String input) {

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

	if (currCh == 'a') { ++currIdx; return Example_Tokens.a; }
	if (currCh == 'b') { ++currIdx; return Example_Tokens.b; }
	if (currCh == 'c') { ++currIdx; return Example_Tokens.c; }
	if (currCh == 'd') { ++currIdx; return Example_Tokens.d; }
	if (currCh == 'e') { ++currIdx; return Example_Tokens.e; }


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
