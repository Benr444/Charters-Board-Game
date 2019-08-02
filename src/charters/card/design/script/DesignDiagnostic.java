package charters.card.design.script;

import java.util.LinkedList;

import charters.card.design.Card;
import charters.card.design.Character;
import charters.card.design.Improvement;
import charters.card.design.Item;
import charters.card.design.Card.Design;
import javafx.util.Pair;

/**
 * A class which can be used to see an overview of the current card designs,
 * broken down by various categories
 */
public class DesignDiagnostic
{
	public static void main(String... args)
	{
		DesignDiagnostic diag = new DesignDiagnostic();
		diag.diagnose();
	}
	
	private DesignGroup allDesigns;
	private LinkedList<DesignGroup> sets;
	
	public DesignDiagnostic()
	{
		this.allDesigns = new DesignGroup
		(
			"All Found Cards", new Item().readDesigns(), new Improvement().readDesigns(), new Character().readDesigns()
		);
		this.sets = new LinkedList<DesignGroup>();
	}
	
	/**
	 * Prints a bunch of diagnostic information about the current designs in file
	 * Just keep adding stuff to this function honestly
	 */
	public void diagnose()
	{
		//Sort all designs into sets
		setSort(allDesigns.items);
		setSort(allDesigns.improvements);
		setSort(allDesigns.characters);
		
		//Iterate for each set: various diagnostics
		for (DesignGroup set : sets)
		{
			set.printBigCount();
			set.getCharacterSubset().printBigCount();
			set.getItemSubset().printBigCount();
			set.getImprovementSubset().printBigCount();
		}
	}
	
	/**
	 * Sorts designs into sets
	 * @param subset - what part of the set to sort into the sets
	 */
	private void setSort(LinkedList<? extends Card.Design> subset)
	{
		//Create the list of sets
		for (Card.Design design : subset)
		{
			//Assume set does not exist
			boolean setExists = false;
			for (DesignGroup set : sets)
			{
				if (set.name.equals(design.set)) //If a set with that name exists 
				{
					setExists = true;
					set.add(design); //Add it to it
				}
			}
			if (setExists == false) //Otherwise
			{
				DesignGroup newSet = new DesignGroup(design.set); //Create a new set
				newSet.add(design); //And add it to that
				sets.add(newSet);
			}
		}
	}
	
	public static void print(String s)
	{
		System.out.println("[Design Diagnostic]: " + s);
	}
	
	public static void print(String set, String s)
	{
		System.out.println("[Design Diagnostic][" + set + "]: " + s);
	}
}
