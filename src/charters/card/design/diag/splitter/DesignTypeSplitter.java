package charters.card.design.diag.splitter;

import charters.card.design.card.Card;

public class DesignTypeSplitter implements GroupSplitter<Card>
{
	@Override
	public String qualityString(Card card)
	{
		return card.getDesignTypeName();
	}
}
