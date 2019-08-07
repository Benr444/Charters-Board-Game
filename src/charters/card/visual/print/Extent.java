package charters.card.visual.print;

public class Extent
{
	public double width;
	public double height;
	

	public Extent()
	{
		this(0, 0);
	}
	
	public Extent(double width, double height)
	{
		this.width = width;
		this.height = height;
	}
	
	public void Zero()
	{
		this.width = 0;
		this.height = 0;
	}
	
	public String toString()
	{
		return "(" + width + ", " + height + ")";
	}
}
