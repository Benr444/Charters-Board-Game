package charters.card.design.script;

import java.util.LinkedList;

import charters.card.design.Card;
import charters.card.design.Character;
import charters.card.design.Improvement;
import charters.card.design.Item;
import charters.card.design.Item.Design;
import charters.card.design.refactor.Color;

public class DesignGroup
{
	public final String name;
	public LinkedList<Item.Design> items;
	public LinkedList<Improvement.Design> improvements;
	public LinkedList<Character.Design> characters;
	
	public DesignGroup(String name)
	{
		this.name = name;
		this.items = new LinkedList<Item.Design>();
		this.improvements = new LinkedList<Improvement.Design>();
		this.characters = new LinkedList<Character.Design>();
	}
	
	/**
	 * Initializer constructor
	 */
	public DesignGroup
	(
		String name,
		LinkedList<Item.Design> items, LinkedList<Improvement.Design> improvements, LinkedList<Character.Design> characters
	)
	{
		this(name);
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
	
	public DesignGroup getItemSubset()
	{
		return new DesignGroup(this.name + ".Items", this.items, new LinkedList<Improvement.Design>(), new LinkedList<Character.Design>());
	}
	
	public DesignGroup getImprovementSubset()
	{
		return new DesignGroup(this.name + ".Improvements", new LinkedList<Item.Design>(), this.improvements, new LinkedList<Character.Design>());
	}
	
	public DesignGroup getCharacterSubset()
	{
		return new DesignGroup(this.name + ".Characters", new LinkedList<Item.Design>(), new LinkedList<Improvement.Design>(), this.characters);
	}
	
	public int totalCount()
	{
		return this.total().size();
	}
	
	public int countColor(Color color)
	{
		int returnValue = 0;
		for (Card.Design design : this.total())
		{
			if (design.color.equals(color)) {returnValue++;}
		}
		return returnValue;
	}

	public void printBigCount()
	{
		double setSize = new Double(totalCount()); //Double to simplify casting
		print("Set Count (Total Size): " + setSize);
		int itemCount = this.items.size();
		print("Item #: " + itemCount);
		print("Item %: " + itemCount / setSize);
		int characterCount = this.characters.size();
		print("Character #: " + characterCount);
		print("Character %: " + characterCount / setSize);
		int improvementCount = this.improvements.size();
		print("Improvement #: " + improvementCount);
		print("Improvement %: " + improvementCount / setSize);
		for (Color color : Color.values())
		{
			int colorCount = this.countColor(color);
			print(color.toString() + " #: " + colorCount);
			print(color.toString() + " %: " + colorCount / setSize);
		}
	}
	
	public void print(String s)
	{
		System.out.println("[DesignGroup:\"" + this.name + "\"]:" + s);
	}
}
