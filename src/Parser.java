import java.io.FileNotFoundException;


public class Parser
{

	private LexicalAnalyzer lex;
	
	/**
	 * precondition: fileName is not null
	 * @param fileName
	 * @throws FileNotFoundException
	 * @throws LexException 
	 * @throws IllegalArgumentException if fileName is null
	 */
	public Parser (String fileName) throws FileNotFoundException, LexException
	{
		if (fileName == null)
			throw new IllegalArgumentException ("null string argument");
		lex = new LexicalAnalyzer (fileName);
	}
	
	public Feature parsep () throws ParserException
	{
		
		Compound cp;
		Id id;
		Token tok = lex.getNextToken();
		if(tok.getTokenType()==TokenType.FEATURE)
		{
			tok = lex.getLookaheadToken();
			if(tok.getTokenType()==TokenType.IDENT)
			{
				id=getId();
				tok=lex.getNextToken();
				if(tok.getTokenType()==TokenType.IS)
				{
					tok=lex.getNextToken();
					if(tok.getTokenType()==TokenType.DO)
					{
						cp= getCompound();
						tok=lex.getLookaheadToken();
						if(tok.getTokenType()==TokenType.END)
						{
							
						}
						else
						{
							throw new ParserException ("Expected end at: "
									+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());
						}
				
					}
					else
					{
						throw new ParserException ("Expected do syntax at: "
								+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());
						
					}
				}
				else
				{
					throw new ParserException ("Expected is syntax at: "
							+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());
					
				}
			}
			else
			{
				throw new ParserException ("Expected id at: "
						+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());
				
			}
		}
		else
		{
			throw new ParserException ("Expected feature syntax at: "
					+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());
		}
		return new Feature(id,cp);
	
	}
	
	private Compound getCompound() throws ParserException
	{
		Token tok = lex.getLookaheadToken();
		if(tok.getTokenType()==TokenType.IF || tok.getTokenType()==TokenType.FROM || tok.getTokenType()==TokenType.IDENT || tok.getTokenType()==TokenType.PRINT)
		{
			Statement state =getStatement();
			Compound compound=getCompound();
			if(compound==null)
				return new Compound(state);
			else
				return new Compound(state, compound);
		}
		else
		{ 
			return null;	
		}
	}
	
	private Statement getStatement()throws ParserException
	{
		Token tok = lex.getLookaheadToken();
		if(tok.getTokenType()==TokenType.IF)
			return getIfStatement();
		else if(tok.getTokenType()==TokenType.FROM)
			return getLoopStatement();
		else if(tok.getTokenType()==TokenType.IDENT)
			return getAssignmentStatement();
		else if(tok.getTokenType()==TokenType.PRINT)
			return getPrintStatement();
		else
			throw new ParserException ("Statment expected at line number: "
					+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());
	}
	
	private IfStatement getIfStatement()throws ParserException
	{
		BooleanExpression boo;
		Compound cp1;
		Compound cp2;
		
		Token tok = lex.getNextToken();
		boo=getBooleanExpression();
		tok = lex.getNextToken();
		if(tok.getTokenType()==TokenType.THEN)
		{ 
			cp1=getCompound();
			tok = lex.getNextToken();
			if(tok.getTokenType()==TokenType.ELSE)
			{
				cp2=getCompound();
				tok = lex.getNextToken();
				if(tok.getTokenType()==TokenType.END)
				{
				}
				else
				{
					throw new ParserException ("end expected at line number: "
							+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());
				}
			}
			else
			{
				throw new ParserException ("else expected at line number: "
						+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());
			}
		}
		else
		{
			throw new ParserException ("then expected at line number: "
					+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());
		}
		
		return new IfStatement(boo, cp1, cp2);
	}
	
	private LoopStatement getLoopStatement()throws ParserException
	{
		Id id;
		Compound cp;
		Statement assign;
		BooleanExpression bool;
		id = getId();
		assign = getAssignmentStatement();
		Token tok =lex.getNextToken();
		if(tok.getTokenType()==TokenType.UNTIL)
		{
			bool = getBooleanExpression();
			tok = lex.getNextToken();
			if(tok.getTokenType()==TokenType.LOOP)
			{
				cp =getCompound();
				tok = lex.getNextToken();
				if(tok.getTokenType()==TokenType.END)
				{	
				}
				else
				{
					throw new ParserException ("end expected at line number: "
							+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());
					
				}
			}
			else
			{
				throw new ParserException ("loop expected at line number: "
						+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());
				
			}	
			
		}
		else
		{
			throw new ParserException ("until expected at line number: "
					+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());
			
		}
		assign.execute();
		return new LoopStatement(id, bool, cp);
		
	}
	
	private AssignmentStatement getAssignmentStatement()throws ParserException
	{
		Id id=getId();
		Token tok= lex.getNextToken();
		if(tok.getTokenType()==TokenType.ASSIGNMENT_OPERATOR)
		{
			return new AssignmentStatement(id,getExpression());
		}
		else
		{
			throw new ParserException ("assignment operator expected at line number: "
					+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());
		}
	}
	
	private PrintStatement getPrintStatement() throws ParserException
	{
		lex.getNextToken();
		Token tok = lex.getNextToken();
		Expression expr;
		if(tok.getTokenType()==TokenType.LEFT_PAREN)
		{
			expr=getExpression();
			tok=lex.getNextToken();
			if(tok.getTokenType()==TokenType.RIGHT_PAREN)
			{
			}
			else
			{
				throw new ParserException ("right parentheses expected at line number: "
						+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());
			}
		}
		else
		{
			throw new ParserException ("left parentheses expected at line number: "
					+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());
		}
		
		return new PrintStatement(expr);
	}
	private BooleanExpression getBooleanExpression()throws ParserException
	{
		return new BooleanExpression(getRelationalOperator(), getExpression(), getExpression());
	}

	private Expression getExpression() throws ParserException
	{
		Expression expr;
		Token tok = lex.getLookaheadToken();
		if (tok.getTokenType() == TokenType.IDENT || tok.getTokenType() == TokenType.INT_LIT)
			expr = getUnaryExpression();
		else
		{
			expr = getBinaryExpression();
		}
		return expr;
	}

	private Expression getBinaryExpression() throws ParserException
	{
		ArithmeticOperator op = getArithmeticOperator();
		Expression expr1 = getExpression();
		Expression expr2 = getExpression();
		return new BinaryExpression (op, expr1, expr2);
	}

	private UnaryExpression getUnaryExpression() throws ParserException
	{
		UnaryExpression expr;
		Token tok = lex.getLookaheadToken();
		if (tok.getTokenType() == TokenType.IDENT)
			expr = getId();
		else if (tok.getTokenType() == TokenType.INT_LIT)
			expr = getLiteralInteger();
		else
			throw new ParserException ("identifier or literal integer expected at line number: "
					+ tok.getLineNumber() + " column number: " + tok.getColumnNumber());

		return expr;
	}

	private ArithmeticOperator getArithmeticOperator() throws ParserException
	{
		ArithmeticOperator op;
		Token tok = lex.getNextToken();
		if (tok.getTokenType() == TokenType.ADD_OPERATOR)
			op = ArithmeticOperator.ADD;
		else if (tok.getTokenType() == TokenType.SUB_OPERATOR)
			op = ArithmeticOperator.SUB;
		else if (tok.getTokenType() == TokenType.MUL_OPERATOR)
			op = ArithmeticOperator.MUL;
		else if (tok.getTokenType() == TokenType.DIV_OPERATOR)
			op = ArithmeticOperator.DIV;
		else
			throw new ParserException ("arithmetic operator expected at line number: " + tok.getLineNumber() + 
				" column number: " + tok.getColumnNumber());
		return op;
	}

	private RelationalOperator getRelationalOperator() throws ParserException
	{
		RelationalOperator op;
		Token tok = lex.getNextToken();
		if (tok.getTokenType() == TokenType.LE_OPERATOR)
			op = RelationalOperator.LE_OPERATOR;
		else if (tok.getTokenType() == TokenType.LT_OPERATOR)
			op = RelationalOperator.LT_OPERATOR;
		else if (tok.getTokenType() == TokenType.GE_OPERATOR)
			op = RelationalOperator.GE_OPERATOR;
		else if (tok.getTokenType() == TokenType.GT_OPERATOR)
			op = RelationalOperator.GT_OPERATOR;
		else if (tok.getTokenType() == TokenType.EQ_OPERATOR)
			op = RelationalOperator.EQ_OPERATOR;
		else if (tok.getTokenType() == TokenType.NE_OPERATOR)
			op = RelationalOperator.NE_OPERATOR;
		else
			throw new ParserException ("relational operator expected at line number: " + tok.getLineNumber() + 
				" column number: " + tok.getColumnNumber());
		return op;
	}

	private LiteralInteger getLiteralInteger() throws ParserException
	{
		Token tok = lex.getNextToken();
		int value = 0;
		try
		{
			value = Integer.parseInt(tok.getLexeme());
		}
		catch (NumberFormatException e)
		{
			throw new ParserException ("literal integer expected at line number: " + tok.getLineNumber() + 
				" column number: " + tok.getColumnNumber());
		}
		return new LiteralInteger (value);
	}

	private Id getId() throws ParserException
	{
		Token tok = lex.getNextToken();
		return new Id (tok.getLexeme().charAt(0));
	}
	
}