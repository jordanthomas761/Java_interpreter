
public class AssignmentStatement implements Statement{
	private Id id;
	private Expression expr;
	
	public AssignmentStatement(Id i, Expression e)
	{
		this.id=i;
		this.expr=e;
		Memory.store(id.getCh(), expr.evaluate());
	}
	
	@Override
	public void execute() 
	{
		Memory.store(id.getCh(), expr.evaluate());
	}
	

}
