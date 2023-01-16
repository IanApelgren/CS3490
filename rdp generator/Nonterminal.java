import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

public class Nonterminal extends GrammarSymbol{
    private boolean _isNullable;
    private ArrayList<Terminal> firstSet;
    private ArrayList<Terminal> followSet;
    private ArrayList<Production> productions;
    public Nonterminal(String name) {
	super(name);
	_isNullable = false;
	firstSet = new ArrayList<Terminal>();
	followSet = new ArrayList<Terminal>();
	productions = new ArrayList<Production>();
    }
    public void setNullable() { _isNullable = true; }
    public void addToFIRST(Terminal t) { firstSet.add(t); }
    public void addToFOLLOW(Terminal t) { followSet.add(t); }
    public ArrayList<Terminal> getFIRST() { return firstSet; }
    public ArrayList<Terminal> getFOLLOW() { return followSet; }
    public boolean isNullable() { return _isNullable == true; }
    public void add(Production p) { productions.add(p); }
    public ArrayList<Production> getProductionList() {
	return productions;
    }
}
