
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class LexicalAnalyzer
{
	private List<Token> tokens;
	
	public LexicalAnalyzer(String fileName) throws FileNotFoundException, LexException
	{
		if (fileName == null)
			throw new IllegalArgumentException ("null string argument");
		tokens = new LinkedList<Token>();
		Scanner input = new Scanner (new File (fileName));
		int lineNum = 0;
		while (input.hasNext())
		{
			String line = input.nextLine();
			lineNum++;
			processLine (line, lineNum);
		}
		input.close();
	}

	private void processLine(String line, int rowNum) throws LexException
	{
		assert line != null;
		assert rowNum > 0;
		int index = 0;
		boolean done = false;
		do
		{
			index = skipWhiteSpace (line, index);
			if (index == line.length())
				done = true;
			else
			{
				int columnNum = index + 1;
				String lexeme;
				TokenType tok;
				if (Character.isLetter(line.charAt(index)))
				{
					int i = index;
					while (i < line.length() && (Character.isLetter(line.charAt(i))))
						i++;
					lexeme = line.substring(index, i);
					index = i;
					if(lexeme.equals("feature"))
						tok=TokenType.FEATURE;
					else if(lexeme.equals("is"))
						tok=TokenType.IS;
					else if(lexeme.equals("do"))
						tok=TokenType.DO;
					else if(lexeme.equals("end"))
						tok=TokenType.END;
					else if(lexeme.equals("if"))
						tok=TokenType.IF;
					else if(lexeme.equals("then"))
						tok=TokenType.THEN;
					else if(lexeme.equals("else"))
						tok=TokenType.ELSE;
					else if(lexeme.equals("print"))
						tok=TokenType.PRINT;
					else if(lexeme.equals("from"))
						tok=TokenType.FROM;
					else if(lexeme.equals("until"))
						tok=TokenType.UNTIL;
					else if(lexeme.equals("loop"))
						tok=TokenType.LOOP;
					else if(lexeme.length()==1)
						tok=TokenType.IDENT;
					else
						throw new LexException ("invalid lexeme", rowNum, columnNum);
								
					

					
				}
				else if (Character.isDigit(line.charAt(index)))
				{
					int i = index;
					while (i < line.length() && Character.isDigit(line.charAt(i)))
						i++;
					lexeme = line.substring(index, i);
					index = i;
					tok = TokenType.INT_LIT;
				}
				else
				{
					int i = index;
					while (i < line.length() && !Character.isLetterOrDigit(line.charAt(i)) &&  !Character.isSpaceChar(line.charAt(i)))
						i++;
					lexeme = line.substring(index, i);
					index = i;
					if(lexeme.equals(":="))
						tok=TokenType.ASSIGNMENT_OPERATOR;
					else if(lexeme.equals("<="))
						tok=TokenType.LE_OPERATOR;
					else if(lexeme.equals("<"))
						tok=TokenType.LT_OPERATOR;
					else if(lexeme.equals(">="))
						tok=TokenType.GE_OPERATOR;
					else if(lexeme.equals(">"))
						tok=TokenType.GT_OPERATOR;
					else if(lexeme.equals("=="))
						tok=TokenType.EQ_OPERATOR;
					else if(lexeme.equals("/="))
						tok=TokenType.NE_OPERATOR;
					else if(lexeme.equals("+"))
						tok=TokenType.ADD_OPERATOR;
					else if(lexeme.equals("-"))
						tok=TokenType.SUB_OPERATOR;
					else if(lexeme.equals("*"))
						tok=TokenType.MUL_OPERATOR;
					else if(lexeme.equals("/"))
						tok=TokenType.DIV_OPERATOR;
					else if(lexeme.equals("("))
						tok = TokenType.LEFT_PAREN;
					else if(lexeme.equals(")"))
						tok=TokenType.RIGHT_PAREN;
					else
						throw new LexException ("invalid lexeme", rowNum, columnNum);
				}
				Token t = new Token (tok, lexeme, rowNum, columnNum);
				tokens.add(t);
			}
		}while(!done);
	}

	private int skipWhiteSpace(String line, int index)
	{
		assert line != null;
		assert index >= 0;
		while (index < line.length() && Character.isWhitespace(line.charAt(index)))
			index++;
		return index;
	}
	
	public Token getNextToken()
	{
		if (tokens.isEmpty())
			throw new RuntimeException ("no more tokens");
		return tokens.remove(0);
	}
	
	public Token getLookaheadToken()
	{
		if (tokens.isEmpty())
			throw new RuntimeException ("no more tokens");
		return tokens.get(0);
	}
	
	public boolean moreTokens ()
	{
		return !tokens.isEmpty();
	}
}
