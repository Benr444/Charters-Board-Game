package charters.card.group;

import charters.card.card.Card;

public class DesignTypeSplitter implements GroupSplitter<Card>
{
	@Override
	public String qualityString(Card card)
	{
		return card.getDesignTypeName();
	}
}
