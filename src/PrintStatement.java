
public class PrintStatement implements Statement{
	private Expression expr;
	
	public PrintStatement(Expression ex)
	{
		this.expr=ex;
	}
	
	public void execute()
	{
		System.out.println(expr.evaluate());
	}


}
