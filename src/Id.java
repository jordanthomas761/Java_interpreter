
public class Id implements UnaryExpression
{

	private char ch;

	/**
	 * @param ch
	 */
	public Id(char ch)
	{
		this.ch = Character.toLowerCase(ch);
	}
	
	/* (non-Javadoc)
	 * @see Expression#evaluate()
	 */
	public int evaluate ()
	{
		return Memory.fetch (ch);
	}

	public char getCh()
	{
		return ch;
	}
	
	public void setValue (int value)
	{
		Memory.store(ch, value);
	}
	
}
