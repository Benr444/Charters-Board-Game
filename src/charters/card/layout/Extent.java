package charters.card.layout;

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
	
	public void add(Extent e)
	{
		this.width += e.width;
		this.height += e.height;
	}
	
	//Ensures that this extent is at least as large as the passed one
	public void expandTo(Extent e)
	{
		if (this.width < e.width)
		{
			this.width = e.width;
		}
		if (this.height < e.height)
		{
			this.height = e.height;
		}
	}
	
	public String toString()
	{
		return "(" + width + ", " + height + ")";
	}
}
