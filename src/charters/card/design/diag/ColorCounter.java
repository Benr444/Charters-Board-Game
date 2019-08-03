package charters.card.design.diag;

import charters.card.design.card.Card;
import charters.card.design.design.Color;

public class ColorCounter implements CardAnalyzer<Card>
{
	public final Color color;
	private int count;
	
	public ColorCounter(Color color)
	{
		this.color = color;
		count = 0;
	}
	
	public int getCount() {return count;}

	@Override
	public void accept(Card c)
	{
		if (c.getDesign().color == this.color) {count++;}
	}
}
