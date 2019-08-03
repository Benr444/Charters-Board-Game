package charters.card.design.diag;

import charters.card.design.card.PlayableCard;
import charters.card.design.group.CardGroup;

/**
 * Splits by HP
 * 
 * ex: a group will split into a HP=0, HP=1, HP=2, HP=3 group as necessary (filled with respective cards)
 */
public class HPSplitter implements GroupSplitter<PlayableCard>
{
	@Override
	public boolean check(CardGroup group, PlayableCard card)
	{
		return group.name.contains(qualityString(card));
	}

	@Override
	public String qualityString(PlayableCard card)
	{
		return "HP=" + card.getDesign().HP;
	}

}
