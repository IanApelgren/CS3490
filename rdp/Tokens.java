/*
 * These are grammar INDEPENDENT tokens.
 * As such they are reserved for all RDPs to use
 * regardless of their grammar specific tokens.
 *
 * Your grammar specific tokens can go into a 
 * separate interface that extends this one.
 * Example: public interface MyG_Tokens extends Tokens 
 *
 * Then your grammar specific RDP class will
 * implement your grammar specific Tokens.
 * Example: 
 * public class MyG_RDP extends RDP implements MyG_Tokens {
 */

public interface Tokens {
    public static int ERR_TOK = -1; // error
    public static int EOF_TOK = 0;  // normal end of file/input
}
