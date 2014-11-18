
public class Compound {
	private Statement state;
	private Compound compund;
	
	public Compound(Statement s)
	{
		this.state=s;
	}

	public Compound(Statement s, Compound sl)
	{
		this.state=s;
		this.compund=sl;
	}
	
	public Statement getStatement()
	{
		return state;
	}
	
	public Compound getCompound()
	{
		return compund;
	}
	
	public void execute()
	{
		if(compund==null)
			state.execute();
		else
		{
			state.execute();
			compund.execute();
		}
	}

}
