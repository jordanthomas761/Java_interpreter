
public class LoopStatement implements Statement{
	private Id id;
	private BooleanExpression bool;
	private Compound compound;
	
	public LoopStatement(Id i, BooleanExpression b, Compound cp)
	{
		this.id=i;
		this.bool=b;
		this.compound=cp;
	}
	
	public void execute()
	{
		while(!bool.evaluate())
		{
			compound.execute();
			id.setValue(id.evaluate() + 1);
		}
	}

}
