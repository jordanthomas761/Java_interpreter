
public class IfStatement implements Statement{
	private BooleanExpression boo;
	private Compound compound1;
	private Compound compound2;
	
	public IfStatement(BooleanExpression b, Compound cp1,Compound cp2)
	{
		this.boo=b;
		this.compound1=cp1;
		this.compound2=cp2;
	}
	
	public void execute()
	{
		if(boo.evaluate())
		{
			compound1.execute();;
		}
		else
		{
			compound2.execute();
		}
	}
	
	

}
