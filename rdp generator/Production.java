import java.util.ArrayList;

public class Production {
    private Nonterminal head;
    private ArrayList<GrammarSymbol> body;

    public Production(Nonterminal head) {
        this.head = head;
        body = new ArrayList<GrammarSymbol>();
    }
    public void add(GrammarSymbol symbol) {
	body.add(symbol);
    }
    public ArrayList<GrammarSymbol> getBody() {
	return body;
    }
}
