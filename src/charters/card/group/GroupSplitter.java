package charters.card.group;

import charters.card.card.Card;

/** Functional interface used to split groups of cards into other groups */
public interface GroupSplitter<T extends Card>
{
	/**
	 * @param group - Group to check membership to
	 * @param card - Card in question
	 * @return - True if card should go in group, false if it should not
	 */
	default public boolean check(CardGroup group, T card)
	{
		return group.name.contains(qualityString(card));
	}
	
	/**
	 * @return - The id string used to name new sets for the passed card. E.g. if you are splitting based on color, then it should return the cards color
	 */
	public String qualityString(T card);
}
