package charters.card.design.script;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;

import charters.card.design.card.Card;
import charters.card.design.design.Color;
import charters.card.design.diag.ColorCounter;
import charters.card.design.group.CardGroup;
import charters.card.design.group.DesignGroup;
import javafx.util.Pair;

/**
 * A class which can be used to see an overview of the current card designs,
 * broken down by various categories
 * 
 * This class does NOT define the methods by which information is received about a card set
 * It merely PRINTS that information to the console
 * 
 * This class DOES determine how to break down existing card designs and how to choose which ones to analyze
 * 
 * Example: Goal: Count all RED cards present in any set that are improvements
 * 	In this class: No break down needed
 */
public abstract class DesignDiagnostic
{
	public static void main(String... args)
	{
		CardGroup allCards = new CardGroup("All Found Cards");
		allCards.add(CardSupplier.CHARACTER_SUPPLIER.getAll());
		allCards.add(CardSupplier.ITEM_SUPPLIER.getAll());
		allCards.add(CardSupplier.IMPROVEMENT_SUPPLIER.getAll());
		getDiagnosables(allCards);
	}
	
	/** Split the passed group into commonly analyzed subsets */
	public static LinkedList<CardGroup> getDiagnosables(CardGroup group)
	{
		//To be returned
		LinkedList<CardGroup> diagnosables = new LinkedList<CardGroup>();
		
		//Add each set as a separate diagnosable
		diagnosables.addAll(group.setSplit());
		
		return diagnosables;
		
	}

	/** @param groups - All the groups to be diagnosed */
	public void diagnose(Collection<CardGroup> groups)
	{
		for (CardGroup group : groups) {diagnose(group);}
	}
	
	/**
	 * Prints a bunch of diagnostic information about the current designs in file
	 * Just keep adding stuff to this function honestly
	 */
	public void diagnose(CardGroup group)
	{
		
		//Analyze colors
		for (Color color : Color.values())
		{
			ColorCounter counter = new ColorCounter(color);
			group.forEveryCard(counter);
			print(group, color.name() + " Color Count: " + counter.getCount());
			print(group, color.name() + " Color Fraction: " + counter.getCount() / group.size());
		}
	}
	
	public static void print(String s)
	{
		System.out.println("[Design Diagnostic]: " + s);
	}
	
	public static void print(CardGroup group, String s)
	{
		System.out.println("[Design Diagnostic][" + group.name + "]: " + s);
	}
}
