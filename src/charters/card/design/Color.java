package charters.card.design;

public enum Color
{	
	
	RED("#fc0000"),
	ORANGE("#fe7900"),
	YELLOW("#ece700"),
	GREEN("#00ae06"),
	BLUE("#0019fe"),
	VIOLET("#8900c8"),
	BLACK("#000000"),
	WHITE("#ffffff"),
	COLORLESS("#cccccc"),
	UNSET("#1fcd3d");

	public final String[] colorCodes;
	
	Color(String... colorCodes)
	{
		this.colorCodes = colorCodes;
	}
	
	public String getFillValue(int index)
	{
		return this.colorCodes[index];
	}
}
