
public class Feature {
	private Id id;
	private Compound compound;
	
	public Feature(Id i, Compound cp)
	{
		this.id=i;
		this.compound=cp;
	}
	
	public Id getId()
	{
		return id;
	}
	
	public Compound getStatementList()
	{
		return compound;
	}
	
	public void execute()
	{
		compound.execute();
	}

}
