/*
 * Just an example to get you started.
 *
 * My grammar is simply S -> ;
 *
 * Of course, YOUR grammar is more complicated...
 */

public class Fenwickjb_RDP 
    extends ParentRDP 
    implements Mar04_Tokens 
{
    public Fenwickjb_RDP(Lexer lexer) {
	super(lexer);
    }

    public void startSymbol() {
	// My Fenwickjb start symbol is S.
	// Yours may be different.
	S();
    }

    private void S() {
	if (token == SEMI_TOK) {
	    match(SEMI_TOK);
	    return;
	}
	parserError("Inside S() token=" + token);
    }
}
