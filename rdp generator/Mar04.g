NUM_TOK
ID_TOK
PRINT_TOK
LPAR_TOK
RPAR_TOK
PLUS_TOK
MINUS_TOK
MUL_TOK
DIV_TOK
EQ_TOK
SEMI_TOK
%%
stmts
stmts_prime
stmt
assign
print
expr
expr_prime
term
term_prime
factor
%%
stmts : stmt stmts_prime
stmts_prime : stmt stmts_prime
stmt : assign
stmt : print
assign : ID_TOK EQ_TOK expr SEMI_TOK
print : PRINT_TOK LPAR_TOK ID_TOK RPAR_TOK SEMI_TOK
expr : term expr_prime
expr_prime : PLUS_TOK term expr_prime
expr_prime : MINUS_TOK term expr_prime
term : factor term_prime
term_prime : MUL_TOK factor term_prime
term_prime : DIV_TOK factor term_prime
factor : ID_TOK
factor : NUM_TOK
factor : LPAR_TOK expr RPAR_TOK
%%
stmts : ID_TOK PRINT_TOK : EOF_TOK : no
stmts_prime : ID_TOK PRINT_TOK : EOF_TOK : yes
stmt : ID_TOK PRINT_TOK : ID_TOK PRINT_TOK EOF_TOK : no
assign : ID_TOK : ID_TOK PRINT_TOK EOF_TOK : no
print : PRINT_TOK : ID_TOK PRINT_TOK EOF_TOK : no
expr : ID_TOK NUM_TOK LPAR_TOK : SEMI_TOK RPAR_TOK : no
expr_prime : PLUS_TOK MINUS_TOK : SEMI_TOK RPAR_TOK : yes
term : ID_TOK LPAR_TOK NUM_TOK : PLUS_TOK MINUS_TOK SEMI_TOK RPAR_TOK : no
term_prime : MUL_TOK DIV_TOK : PLUS_TOK MINUS_TOK SEMI_TOK RPAR_TOK : yes
factor : ID_TOK LPAR_TOK NUM_TOK : MUL_TOK DIV_TOK PLUS_TOK MINUS_TOK SEMI_TOK RPAR_TOK : no
%%
