package charters.card.design.diag;

import charters.card.design.card.Card;
import charters.card.design.group.CardGroup;

public class SetSplitter implements GroupSplitter<Card>
{
	@Override
	public boolean check(CardGroup group, Card card)
	{
		return group.name.contains(qualityString(card));
	}

	@Override
	public String qualityString(Card card)
	{
		return card.getDesign().set;
	}
}
