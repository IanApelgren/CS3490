public class YOUR_RDP extends ParentRDP implements Mar04_Tokens
{
	public YOUR_RDP(Lexar lexar)
	{
		super(lexar);
	}
	public void startSymbol()
	{
		STMTS();
	}
	
	private void STMTS()
	{
		if (token == ID_TOK)
		{
			STMT();
			STMTS_PRIME();
			return;
		}
		if (token == PRINT_TOK)
		{
			STMT();
			STMTS_PRIME();
			return;

		}
		parserError("Inside STMTS() token=" + token);
	}

	private void STMTS_PRIME()
	{
		if (token == ID_TOK)
		{
			STMT();
			STMTS_PRIME();
			return;
		}
		if (token == PRINT_TOK)
		{
			STMT();
			STMTS_PRIME();
			return;

		}
		if (token == EOF_TOK)
		{
			match(EOF_TOK);
		}

		parserError("Inside STMTS_PRIME() token=" + token);

	}
	private void STMT()
	{
		if (token == ID_TOK)
		{
			ASSIGN();
			return;
		}
		if (token == PRINT_TOK)
		{
			PRINT();
			return;
		}
		parserError("Inside STMT() token=" + token);

	}
	//id = expr
	private void ASSIGN()
	{
		if (token == ID_TOK)
		{
			match(ID_TOK);
			match(EQ_TOK);
			EXPR();
			match(SEMI_TOK);
			return;
		}
		parserError("Inside ASSIGN() token=" + token);

	}
	//print(id)
	private void PRINT()
	{
		if (token == PRINT_TOK)
		{
			match(PRINT_TOK);
			match(LPAR_TOK);
			match(ID_TOK);
			match(RPAR_TOK);
			match(SEMI_TOK);
			return;
		}
		parserError("Inside PRINT() token=" + token);

	}
	private void EXPR()
	{
		if (token == ID_TOK)
		{
			TERM();
			EXPR_PRIME();
			return;
			
		}
		if (token == NUM_TOK)
		{
			TERM();
			EXPR_PRIME();
			return;

		}
		if (token == LPAR_TOK)
		{
			TERM();
			EXPR_PRIME();
			return;

		}

		parserError("Inside EXPR() token=" + token);

	}
	private void EXPR_PRIME()
	{
		if (token == PLUS_TOK)
		{
			match(PLUS_TOK);
			TERM();
			EXPR_PRIME();
			return;
		}
		if (token == MINUS_TOK)
		{
			match(MINUS_TOK);
			TERM();
			EXPR_PRIME();
			return;

		}
		if (token == SEMI_TOK)
		{
			return;
		}
		if (token == RPAR_TOK)
		{
			return;
		}

		parserError("Inside EXPR_PRIME() token=" + token);

	}
	private void TERM()
	{
		if (token == ID_TOK)
		{
			FACTOR();
			TERM_PRIME();
			return;
			
		}
		if (token == NUM_TOK)
		{
			FACTOR();
			TERM_PRIME();
			return;

		}
		if (token == LPAR_TOK)
		{
			FACTOR();
			TERM_PRIME();
			return;

		}
		parserError("Inside TERM() token=" + token);

	}
	private void TERM_PRIME()
	{
		if (token == MUL_TOK)
		{
			match(MUL_TOK);
			FACTOR();
			TERM_PRIME();
			return;
		}

		if (token == DIV_TOK)
		{
			match(DIV_TOK);
			FACTOR();
			TERM_PRIME();
			return;

		}
		if (token == PLUS_TOK)
		{
			return;
		}
		if (token == MINUS_TOK)
		{
			return;
		}
		if (token == SEMI_TOK)
		{
			return;
		}
		if (token == RPAR_TOK)
		{
			return;
		}

		
		parserError("Inside TERM_PRIME() token=" + token);

	}
	private void FACTOR()
	{
		if (token == ID_TOK)
		{
			match(ID_TOK);
			return;
		}
		if (token == NUM_TOK)
		{
			match(NUM_TOK);
			return;
		}
		if (token == LPAR_TOK)
		{
			match(LPAR_TOK);
			EXPR();
			match(RPAR_TOK);
			return;
		}
		parserError("Inside FACTOR() token=" + token);

	}



}