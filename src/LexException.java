@SuppressWarnings("serial")
public class LexException extends Exception
{
	private int rowNumber;
	private int columnNumber;
	
	/**
	 * preconditions: message is not null, rowNumber > 0, columnNumber > 0
	 * @param message
	 * @param rowNumber
	 * @param columnNumber
	 * @throws IllegalArgumentException if message is null, rowNumber <= 0, or columnNumber <= 0
	 */
	public LexException(String message, int rowNumber, int columnNumber)
	{
		super (message);
		if (message == null)
			throw new IllegalArgumentException ("null string argument");
		if (rowNumber <= 0)
			throw new IllegalArgumentException ("invalid row number argument");
		if (columnNumber <= 0)
			throw new IllegalArgumentException ("invalid column number argument");
		this.rowNumber = rowNumber;
		this.columnNumber = columnNumber;
	}

	public int getRowNumber()
	{
		return rowNumber;
	}

	public int getColumnNumber()
	{
		return columnNumber;
	}

	
}
