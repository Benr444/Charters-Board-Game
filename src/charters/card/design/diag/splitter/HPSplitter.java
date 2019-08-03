package charters.card.design.diag.splitter;

import charters.card.design.card.PlayableCard;

/**
 * Splits by HP
 * 
 * ex: a group will split into a HP=0, HP=1, HP=2, HP=3 group as necessary (filled with respective cards)
 */
public class HPSplitter implements GroupSplitter<PlayableCard>
{
	@Override
	public String qualityString(PlayableCard card)
	{
		return "HP=" + card.getDesign().HP;
	}
}
