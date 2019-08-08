package charters.card.group;

import charters.card.card.Card;

public class ColorSplitter implements GroupSplitter<Card>
{
	@Override
	public String qualityString(Card card)
	{
		return card.getDesign().color.name();
	}
}
