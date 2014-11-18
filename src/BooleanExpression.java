
public class BooleanExpression {
	private RelationalOperator op;
	
	private Expression expr1;
	
	private Expression expr2;

	public BooleanExpression(RelationalOperator op, Expression expr1, Expression expr2)
	{
		if (expr1 == null || expr2 == null)
			throw new IllegalArgumentException ("null expression argument");
		this.op = op;
		this.expr1 = expr1;
		this.expr2 = expr2;
	}
	
	public boolean evaluate()
	{
		boolean value = true;
		switch (op)
		{
			case LE_OPERATOR:
				value = expr1.evaluate() <= expr2.evaluate();
				break;
			case LT_OPERATOR:
				value = expr1.evaluate() < expr2.evaluate();
				break;
			case GE_OPERATOR:
				value = expr1.evaluate() >= expr2.evaluate();
				break;
			case GT_OPERATOR:
				value = expr1.evaluate() > expr2.evaluate();
				break;
			case EQ_OPERATOR:
				value = expr1.evaluate() == expr2.evaluate();
				break;
			case NE_OPERATOR:
				value = expr1.evaluate() != expr2.evaluate();
				break;
		}
		return value;
	}
}
