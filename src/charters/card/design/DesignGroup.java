package charters.card.design;

import java.util.LinkedList;

public class DesignGroup
{
	public LinkedList<ItemDesign> items;
	public LinkedList<ImprovementDesign> improvements;
	public LinkedList<CharacterDesign> characters;
	
	public DesignGroup()
	{
		this.items = new LinkedList<ItemDesign>();
		this.improvements = new LinkedList<ImprovementDesign>();
		this.characters = new LinkedList<CharacterDesign>();
	}
	
	/**
	 * Initializer constructor
	 */
	public DesignGroup
	(
		LinkedList<ItemDesign> items, LinkedList<ImprovementDesign> improvements, LinkedList<CharacterDesign> characters
	)
	{
		this.items = items;
		this.improvements = improvements;
		this.characters = characters;
	}
	
	/**
	 * This is awful, I hate it
	 * @param design
	 */
	public void add(CardDesign design)
	{
		if (design.getClass() == ItemDesign.class)
		{
			items.push((ItemDesign)design);
		}
		else if (design.getClass() == ImprovementDesign.class)
		{
			improvements.push((ImprovementDesign)design);
		}
		else if (design.getClass() == CharacterDesign.class)
		{
			characters.push((CharacterDesign)design);
		}
	}
	
	public LinkedList<CardDesign> total()
	{
		LinkedList<CardDesign> returnValue = new LinkedList<CardDesign>();
		returnValue.addAll(items);
		returnValue.addAll(improvements);
		returnValue.addAll(characters);
		return returnValue;
	}
}
