package charters.card.design.design;

public enum Rarity
{
	COMMON(3, "#FFFFFF"),
	UNCOMMON(2, "#cccccc"),
	RARE(1, "#ccffff"),
	UNSET(0, "#1fcd3d");

	public final String[] colorCodes;
	public final int count;
	
	private Rarity(int count, String... colorCodes)
	{
		this.count = count;
		this.colorCodes = colorCodes;
	}
	
	public String getFillValue(int index)
	{
		return this.colorCodes[index];
	}
}