package charters.card.group;

import charters.card.card.Card;

public class SetSplitter implements GroupSplitter<Card>
{
	@Override
	public String qualityString(Card card)
	{
		return card.getDesign().set;
	}
}
