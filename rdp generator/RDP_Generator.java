
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class RDP_Generator {
    private Scanner gFile;
    private String gFilename;
    private String gName;
    private HashMap<String,Terminal> terminals;
    private HashMap<String,Nonterminal> nonterminals;
    private Nonterminal startSym;
    private FileWriter writer;

    public RDP_Generator() {
	terminals = new HashMap<String, Terminal>();
	nonterminals = new HashMap<String, Nonterminal>();
	startSym = null;
    }
    public static void main(String[] args) {
	RDP_Generator rdpg = new RDP_Generator();

	rdpg.processCommandLine(args);
	rdpg.emitGrammarIndependentParts();

	rdpg.readGrammarFile();
	rdpg.emitMain();
	rdpg.emitConcreteTokens();
	rdpg.emitConcreteParser();
	
	//	rdpg.verifyConcreteLexerExists();
    }

    private void emitMain() {
    }

    private void readGrammarFile() {
	// stuff before??
	try {
            gFile = new Scanner(new File(gFilename));
        }
	catch (FileNotFoundException e) {
	    System.err.println("Grammar file " + gFilename + "not found.");
	    System.exit(1);
        }

	// read the 4 sections of the input grammar spec file
	// each section ends with a dedicated %% line
	readTerminals();
	readNonterminals();
	readProductions();
	readMisc();

	// stuff after??
	gFile.close();
    }
    private void readTerminals() {
	terminals.put("ERR_TOK", new Terminal("ERR_TOK"));
	terminals.put("EOF_TOK", new Terminal("EOF_TOK"));
        while (gFile.hasNextLine()) {
	    String line = gFile.nextLine();
	    String terminalName = line.trim();
            if (terminalName.startsWith("%%")) return;

	    terminals.put(terminalName, new Terminal(terminalName));
	}
    }
    private void readNonterminals() {
	// TO-DO: C project
	// Hint: Follow structure of readTerminals
	while (gFile.hasNextLine()) {
		String line = gFile.nextLine();
		String nonTerminalName = line.trim();
		if(nonTerminalName.startsWith("%%")) return;
		
		nonterminals.put(nonTerminalName, new Nonterminal(nonTerminalName));
	}
    }
    private void readProductions() {
        while (gFile.hasNextLine()) {
	    String line = gFile.nextLine();
	    line = line.trim();  // trim any before/after whitespace
            if (line.startsWith("%%")) return;

	    // fomrate of production is
	    // head-symbol : sequence of space-separate body symbols
	    String[] prodParts = line.split(":");
	    if (prodParts.length != 2) {
	        System.err.println("production malformed in " + gFilename);
	        System.exit(1);
	    }
	    String headString = prodParts[0].trim();
	    Nonterminal headNonterm = nonterminals.get(headString);
	    if (headNonterm == null) {
	        System.err.println("unknown production head " + headString);
	        System.exit(1);
	    }

	    String bodyString = prodParts[1].trim();

	    // using a new 'helper' method here
	    Production p = buildProduction(headNonterm, bodyString);
	    headNonterm.add(p);
	}
    }
    private Production buildProduction(Nonterminal nt, String body) {
	Production p = new Production(nt);
        String[] bodyParts = body.split(" ");
	for (String element : bodyParts) {
	    element = element.trim();
	    GrammarSymbol symbol = terminals.get(element);
	    if (symbol == null) {
	        symbol = nonterminals.get(element);
	        if (symbol == null) {
   	            System.err.println("unknown production symbol " + symbol.getName());
	            System.exit(1);
		}
	    }
            p.add(symbol);
	}
	return p;
    }

    private void readMisc() {
        while (gFile.hasNextLine()) {
	    String line = gFile.nextLine();
	    line = line.trim();  // trim any before/after whitespace
            if (line.startsWith("%%")) return;

	    // form of misc section
	    // nt-symbol : space separated terminals for first : follow : yes-no for epsilon production
	    String[] miscParts = line.split(":");
	    if (miscParts.length != 4) {
	        System.err.println("misc line malformed in " + gFilename);
	        System.exit(1);
	    }

	    // 0th part is the nonterm
	    String ntString = prodParts[0].trim();
	    Nonterminal nonterm = nonterminals.get(ntString);
	    if (nonterm == null) {
	        System.err.println("unknown nonterm in misc " + ntString);
	        System.exit(1);
	    }

	    // 1st part is first set, but it can be empty
	    if (miscParts[1] != null && miscParts[1].length > 0) {
		String firstString = miscParts[1].trim();
		// using a new 'helper' method here that YOU have to write (see below)
		addToSet(firstString, nonterm.getFIRST());
	    }

	    // 2nd part is follow set, but it can be empty
	    // TO-DO: C project
	    // Hint, just like first part but index is 2 and use FOLLOW in call to addToSet
		if (miscParts[2] != null && miscParts[2].length > 0) {
			String followString = miscParts[2].trim();
			addToSet(followString, nonterm.getFOLLOW());
		}


	    // 3rd part is nullable indictor for epsilon production generation
	    String nullable = miscParts[3].trim();
	    if ("yes".equalsIgnoreCase(nullable)) {
		nonterm.setNullable();
	    }
	}
    }
    private void addToSet(String termStrings, ArrayList<Terminals> set) {
	// TO-DO: C project
	// Hint: Split on the termStrings to separate them
	//       Then loop through resulting String[]
	//       For each item that is not null with length > 0
	//             trim() space before/after the item
	//             if length still > 0 then lookup in the terminals and add to set passed in
	
	String[] termSplit = termStrings.split(" ");
	String chara;
	for(int i = 0; i < termSplit.length; i++)
	{
		chara = termSplit[i];
		if(chara != null && chara.length > 0)
		{
			chara = chara.trim();
			if(chara.length > 0)
			{	
				
				set.add(new Terminal(chara));
			}
		}
	}
    }

    private void emitConcreteTokens() {
	int tokenNum = 501;
	System.out.print("emitting " + gName + "_Tokens.java .....");
	openWriter(gName + "_Tokens.java");

	emit("public interface " + gName + "_Tokens extends Tokens {\n");

	// Emit the concrete tokens for our grammar
	// TO-DO: C project
	// Hint: grab the terminals keyset and loop
	//       don't 'reinsert' the EOF_TOK nor ERR_TOK tokens
	for(String key : terminal.keySet())
	{
		String key  = terminal.getKey();
		if( key != "EOF_TOK" && key != "ERR_TOK")
		{
			emit("public static final int " + key + " = " + tokenNum + ";\n"); 
			tokenNum += 1;
		} 
	}
	emit("};\n");

	closeWriter();
	System.out.println("..done");
    }


    // "Borrowing" this open/close writer code from RDPG_GI class.
    // Futuer design question: How could we rewrite to avoid this 
    //  duplication??
    private void openWriter(String filename) {
	try {
	    writer = new FileWriter(filename);
	}
	catch (IOException e) {
	    System.err.println("trying to create " + filename);
	    e.printStackTrace();
	}
    }
    private void closeWriter() {
	try {
	    writer.close();
	}
	catch (IOException e) {
	    System.err.println("trying to close file");
	    e.printStackTrace();
	}
    }
    // Also "borrowing" this emit code from RDPG_GI class.
    // Futuer design question: How could we rewrite to avoid this 
    //  duplication??
    private void emit(String[] code) {
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
    // overloaded version of emit (only 1 string not array)
    private void emit(String code) {
	try {
	    writer.write(code);
	}
	catch (IOException e) {
	    System.err.println("trying to emit line");
	    e.printStackTrace();
	}
    }

    private void emitConcreteParser() {
	System.out.print("emitting " + gName + "_RDP.java .....");
	openWriter(gName + "_RDP.java");

	emit("public class " + gName + "_RDP\n");
	emit("   extends ParentRDP implements " + gName + "_Tokens\n");
	emit("{\n");
	emit("    public " + gName + "_RDP(Lexer lexer) {\n");
	emit("        super(lexer);\n");
	emit("    }\n");
	emit("    public void startSymbol() {\n");
	emit("        " + startSym.getName() + "();\n");
	emit("    }\n");

	Set<String> nonterminalNames = nonterminals.keySet();
	for (String nonterm : nonterminalNames) {
	   emitNontermMethod(nonterm);
        }

	emit("}\n");
	closeWriter();
	System.out.println("..done");
    }

    private void emitNontermMethod(String ntName) {
	// Look up nonterminal from ntName
	Nonterminal nt = nonterminals.get(ntName);
	if (nt == null) {
	    System.out.println("Unknown ntName " + ntName);
	    System.exit(1);
	}

	// emit method 'signature'
	// TO-DO: C project
	// HINT: Look at pub/3490/rdp/Fenwick_RDP.java for the 'S' production
	//       Here the method name is coming from nt.getName()
	emit("private void " + ntName + "() {\n");
	
	// use helper methods to emit "if-blocks" for each production
	ArrayList<Productions> ntProds = nt.getProductionList();
	for (Production p : ntProds) {
	    emitNontermMethodIfBlock(p);
	}
	emit("}\n");
	// emit "bottom" of method
	// TO-DO: C project
    }
    private void emitNontermMethodIfBlock(Production p) {
	// Emit start of if
	emit ("      if (\n");
	
	// emit comparisons of token with first set
	emitNontermMethodIfBlockCondition(p);

	// emit end of if
	emit("         ) {\n");

	// emitd body of if-block for this production
	emitNontermMethodIfBlockBody(p);

	// emit end of entire if block for this production
	emit("      }\n");
    }
    private void emitNontermMethodIfBlockCondition(Production p) {
	emit("token == " + p.head + "\n");
    }
    private void emitNontermMethodIfBlockBody(Production p) {
	// TO-DO: C project
	// HINT: Loop through body of production
	//          For each symbol look up in terminals
	//              if a terminal then emit match(terminal symbol)
	//              if not then lookup up in nonterminals
	//                   if a nonterm then emit symbol function call
	//                   if not then error
	ArrayList<GrammarSymbol> bodysym = p.getBody();
	
	for(int i = 0; i > bodysym.size(); i++)
	{
		if(terminal.getName(bodysym.get(i)) != null)
		{
			emit("match(" + bodysym.get(i) + ");\n");
			
		}
		if(nonterminal.getName(bodysym.get(i)) != null)
		{
			emit(bodysym.get(i)+"();\n");
		}
		emit("return;\n");
	}
	emit("parserError('Inside "+ p.getBody + "() token=' + token);\n");

    }
    private void emitNontermEpsilonProduction(Nonterminal nt) {
	// called by emitNontermMethod()
	if (! nt.isNullable()) return; // no epsilon prod needed

	// indenting 4 spaces per 'level'
	// emit if condition using helper function
	emit("        if (\n");
	emitIfComparisons(nt.getFOLLOW());
	emit("           )\n");

	// emit if body
        emit("        {\n");
        emit("            return; // epsilon production\n");
        emit("        }\n");
    }
    private void emitIfComparisons(ArrayList<Terminal> terms) {
	boolean first = true; // first line does not have || logical or
	for (Terminal term : terms) {
	    if (! first) emit("            || \n");
	    first = false;

	    emit("            token == " 
		 + gName + "_Tokens." + term.getName() + "\n");
	}
    }

    private void emitGrammarIndependentParts() {
	RDPG_GI.emitLexer();
	RDPG_GI.emitParentRDP();
	RDPG_GI.emitTokens();
    }

    // Cool use of Java reflection to verify existence assumption.
    // But does introduce "chicken-egg" problem so okay to not call for now...
    private void verifyConcreteLexerExists() {
	Class c = null;
	try {
	    c = Class.forName(gName+"_Lexer");
	    System.out.println("Concrete lexer " + gName+"_Lexer was found.");
	}
	catch (ClassNotFoundException e) {
	    System.err.println("Class " + gName + "_Lexer was not found.");
	    System.err.println("But, we need to generate ***_Tokens.java first"
	                       + " anyway, so just try it a second time...");
	    System.exit(1);
	}
	catch (Exception e) {
	    System.err.println("Verifying " + gName + "_Lexer class");
	    e.printStackTrace();
	}
    }

    private void processCommandLine(String[] args) {
	if (args.length != 1) usage();
	gFilename = args[0];
	gName = gFilename.substring(0,gFilename.lastIndexOf(".g"));

	Scanner input = null;
	try {
	    gFile = new Scanner(new File(gFilename));
	}
	catch (FileNotFoundException e) {
	    System.err.println("File " + gFilename + " unable to be opened.");
	    System.exit(1);
	}
    }

    private void usage() {
	System.err.println("usage: java RDP_Generator grammarFile.g");
	System.exit(1);
    }


}
