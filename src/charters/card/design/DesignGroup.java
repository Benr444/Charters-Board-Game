package charters.card.design;

import java.util.LinkedList;

public class DesignGroup
{
	public LinkedList<Item.Design> items;
	public LinkedList<Improvement.Design> improvements;
	public LinkedList<Character.Design> characters;
	
	public DesignGroup()
	{
		this.items = new LinkedList<Item.Design>();
		this.improvements = new LinkedList<Improvement.Design>();
		this.characters = new LinkedList<Character.Design>();
	}
	
	/**
	 * Initializer constructor
	 */
	public DesignGroup
	(
		LinkedList<Item.Design> items, LinkedList<Improvement.Design> improvements, LinkedList<Character.Design> characters
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
	public void add(Card.Design design)
	{
		if (design.getClass() == Item.Design.class)
		{
			items.push((Item.Design)design);
		}
		else if (design.getClass() == Improvement.Design.class)
		{
			improvements.push((Improvement.Design)design);
		}
		else if (design.getClass() == Character.Design.class)
		{
			characters.push((Character.Design)design);
		}
	}
	
	public LinkedList<Card.Design> total()
	{
		LinkedList<Card.Design> returnValue = new LinkedList<Card.Design>();
		returnValue.addAll(items);
		returnValue.addAll(improvements);
		returnValue.addAll(characters);
		return returnValue;
	}
}
