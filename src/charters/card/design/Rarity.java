package charters.card.design;

public enum Rarity
{
	COMMON("#FFFFFF"),
	UNCOMMON("#cccccc"),
	RARE("#ccffff"),
	UNSET("#1fcd3d");

	public final String[] colorCodes;
	
	private Rarity(String... colorCodes)
	{
		this.colorCodes = colorCodes;
	}
	
	public String getFillValue(int index)
	{
		return this.colorCodes[index];
	}
}