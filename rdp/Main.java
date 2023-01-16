/*
 * Main is the main! ;-)
 *
 * The only thing you have to change here is
 * to make it instantiate YOUR parser class.
 */

public class Main {
    public static void main(String[] args) {
	// robust command line processing
	String input = usage(args);

	System.out.println("Here we go a-parsin'...");
	ParentRDP myParser = new YOUR_RDP(new Mar04_Lexer(input));
	myParser.parse();
	System.out.println("...aaannnddd we're done!");
    }

    private static String usage(String[] args) {
	String input = null;

	System.out.println();
	if (args == null || args.length == 0) {
	    input = "myvar=2*(10+55/(7-2));print(myvar);";
	    System.out.println("No command line args provided, using default.");
	}
	else if (args.length == 1) {
	    input = args[0];
	}
	else { // numArgs > 1
	    input = args[0];
	    System.out.println("Too many command line args provided.");
	}
	
	System.out.println("Using input = '" + input + "'.");
	return input;
    }
}
