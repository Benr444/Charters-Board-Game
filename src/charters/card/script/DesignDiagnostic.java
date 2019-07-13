package charters.card.script;

import java.util.LinkedList;

import charters.card.design.CardDesign;
import charters.card.design.CharacterDesign;
import charters.card.design.DesignGroup;
import charters.card.design.ImprovementDesign;
import charters.card.design.ItemDesign;
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
	private LinkedList<Pair<String, DesignGroup>> sets;
	
	public DesignDiagnostic()
	{
		this.allDesigns = new DesignGroup
		(
			new DesignReader<ItemDesign>(ItemDesign.class)
			.readDesigns(new ItemDesign().getDesignFolderPath()),
			new DesignReader<ImprovementDesign>(ImprovementDesign.class)
			.readDesigns(new ImprovementDesign().getDesignFolderPath()),
			new DesignReader<CharacterDesign>(CharacterDesign.class)
			.readDesigns(new CharacterDesign().getDesignFolderPath())
		);
		this.sets = new LinkedList<Pair<String, DesignGroup>>();
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
		
		//Iterate for each set:
		for (Pair<String, DesignGroup> set : sets)
		{
			double setSize = new Double(count(set.getValue())); //Double to simplify casting
			print(set.getKey(), "Set Count (Total Size): " + setSize);
			int itemCount = set.getValue().items.size();
			print(set.getKey(), "Item Count: " + itemCount);
			print(set.getKey(), "Item Fraction: " + itemCount / setSize);
			int characterCount = set.getValue().characters.size();
			print(set.getKey(), "Character Count: " + characterCount);
			print(set.getKey(), "Character Fraction: " + characterCount / setSize);
			int improvementCount = set.getValue().improvements.size();
			print(set.getKey(), "Improvement Count: " + improvementCount);
			print(set.getKey(), "Improvement Fraction: " + improvementCount / setSize);
			for (CardDesign.Color color : CardDesign.Color.values())
			{
				int colorCount = countColor(set.getValue(), color);
				print(set.getKey(), color.toString() + " Count: " + colorCount);
				print(set.getKey(), color.toString() + " Fraction: " + colorCount / setSize);
			}
		}
	}
	
	/**
	 * Sorts designs into sets
	 * @param subset - what part of the set to sort into the sets
	 */
	private void setSort(LinkedList<? extends CardDesign> subset)
	{
		//Create the list of sets
		for (CardDesign design : subset)
		{
			//Assume set does not exist
			boolean setExists = false;
			for (Pair<String, DesignGroup> set : sets)
			{
				if (set.getKey().equals(design.set)) //If a set with that name exists 
				{
					setExists = true;
					set.getValue().add(design); //Add it to it
				}
			}
			if (setExists == false) //Otherwise
			{
				DesignGroup newSet = new DesignGroup(); //Create a new set
				newSet.add(design); //And add it to that
				sets.add(new Pair<String, DesignGroup>(design.set, newSet));
			}
		}
	}
	
	public static int count(DesignGroup group)
	{
		return group.total().size();
	}
	
	public static int countColor(DesignGroup group, CardDesign.Color color)
	{
		int returnValue = 0;
		for (CardDesign design : group.total())
		{
			if (design.color.equals(color)) {returnValue++;}
		}
		return returnValue;
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
