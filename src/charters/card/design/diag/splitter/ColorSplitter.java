package charters.card.design.diag.splitter;

import charters.card.design.card.Card;
import charters.card.design.group.CardGroup;

public class ColorSplitter implements GroupSplitter<Card>
{
	@Override
	public String qualityString(Card card)
	{
		return card.getDesign().color.name();
	}
}
