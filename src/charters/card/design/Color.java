package charters.card.design;

public enum Color
{	
	
	RED("charters-red"),
	ORANGE("charters-orange"),
	YELLOW("charters-yellow"),
	GREEN("charters-green"),
	BLUE("charters-blue"),
	VIOLET("charters-violet"),
	BLACK("charters-black"),
	WHITE("charters-white"),
	COLORLESS("charters-colorless"),
	UNSET("charters-unset");

	public final String alias;
	
	Color(String alias)
	{
		this.alias = alias;
	}
	
	public String getFillValue()
	{
		return "url(#" + this.alias + ")";
	}
}
