
public class LiteralInteger implements UnaryExpression 
{

	private int value;

	public LiteralInteger(int value)
	{
		this.value = value;
	}

	public int evaluate ()
	{
		return value;
	}

}
