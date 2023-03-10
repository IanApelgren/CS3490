/**
 * This is a string set data structure, that is implemented as a Hash Table. 
 * This data structure supports operations insert, find and print - that insert a new 
 * String, finds a String key and prints the contents of the data structure resp.
 */
public class StringSet {

  StringNode [] table;	// Hash table - collisions resolved through chaining.
  int numelements;	// Number of elements actually stored in the structure.
  int size;					// Allocated memory (size of the hash table).

  /** 
   * Constructur: initilaizes numelements, size and initial table size.
   */
  public StringSet() {
    numelements = 0;
    size = 100;
    table = new StringNode[size];
  }

  /*
   * inserts a new key into the set. Inserts it at the head of the linked list given by its hash value.
   */
  public void insert(String key) {

    if (numelements == size) {
      //TODO: need to expand the hash table and rehash its contents. 
        size = (size*2)+1;
        StringNode[] copyTable = new StringNode[size];
        
        for(int i = 0; i < size; i++)
        {
            //StringNode current = table[i];
            for(StringNode current = table[i]; current != null; current = current.getNext())
            {
                int Hkey = hash(key);
                copyTable[Hkey] = new StringNode(key, table[Hkey]);
            }
        }
        
      
    }
    //TODO: Code for actual insert.
    int Hkey = hash(key);
    table[Hkey] = new StringNode(key, table[Hkey]);
  }

  /*
   * finds if a String key is present in the data structure. Returns true if found, else false.
   */
  public boolean find(String key) {
    // TODO:
    StringNode current = table[0];
    //int Hkey = hash(key);
    while(current != null)
    {
        if (key.equals(current.getKey()))
        {
            return true;
        }
        current = current.getNext();
    }
    return false;
  }

  /*
   * Prints the contents of the hash table.
   */
  public void print() {
    // TODO:
      for(int i = 0; i < size; i++)
      {
          //StringNode current = table[i];
          for(StringNode current = table[i]; current != null; current = current.getNext())
          {
              System.out.println(current.getKey());
          }
      }
  }

  /*
   * The hash function that returns the index into the hash table for a string k.
   */
  private int hash(String k) {
    int h = 0;
    // TODO: Compute a polynomial hash function for the String k. Make sure that the hash value h returned is a proper index 
    // in the hash table, ie. in the range [0...capacity-1].
    for (int i = 0; i < k.length(); i++) 
    {
        h = (h + (int) k.charAt(i)) % size;
    }
    return h;
  }

}
