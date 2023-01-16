/*
 * This class provides all the "infrastructure" that all RDPs need.
 *
 * Your job is to write a subclass named YourUsername_RDP 
 * so for me it would be Fenwickjb_RDP
 * that extends this ParentRDP and adds all the grammar-specific
 * functions.
 *
 * Because this ParentRDP does not know anything about your grammar,
 * it doesn't know your start symbol. Therefore, it forces you to
 * implement the startSymbol() function. Just simply call the function 
 * for the start symbol of your grammar in your implementation of the
 * startSymbol() function.
 *
 * It also does not know what lexer your grammar will require.
 * So you must provide that to the ParentRDP parser. Your RDP constructor
 * should pass it to our constructor here using super(lexer) function call.
 * While we don't know what precise class it is, we DO REQUIRE that it
 * implements the Lexer interface and so that is what we expect.
 *
 * Infrastructure you get:
 * int token - use to predict
 * boolean parse() - entry method to do parse
 * void match(int expectedToken) - shifts input token if matches expectation
 * void parserError(String message) - prints message and exits
 */

public abstract class ParentRDP implements Tokens {

    // Subclass should have something like: 
    // public void startSymbol() { S(); }
    // where S is actually THEIR grammar's starting non-terminal
    public abstract void startSymbol();

    // Subclass will need to check token values in if-blocks...
    protected int token;
    
    // This is the primary method provided to do the parse!
    public boolean parse() {
	// Get first token!
	token = lexer.yylex();

	// begin top-down derivation by calling start symbol
	startSymbol();

	// okay THEIR derivation is done successfully, 
	// make sure that there is no more input.
	if (token != EOF_TOK) {
	    // oops, there is input remaining?
	    parserError("Extraneous input...");
	}
	return true;
    }

    public void match(int expectedToken) {
	if (token == expectedToken) {
	    token = lexer.yylex();
	}
	else {
	    parserError("Expected " + expectedToken + " but read " + token);
	}
    }
    
    public void parserError(String message) {
	System.err.println();
	System.err.println("Parser error: " + message);
	System.err.println();
	System.exit(1);
    }

    public ParentRDP(Lexer lexer) {
	// This comes from the subclass constructor
	this.lexer = lexer;
    }

    private Lexer lexer;
}
