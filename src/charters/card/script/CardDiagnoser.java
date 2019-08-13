package charters.card.script;

import java.util.Collection;
import java.util.LinkedList;

import charters.card.card.Card;
import charters.card.card.CardSupplier;
import charters.card.design.Color;
import charters.card.group.CardAnalyzer;
import charters.card.group.CardGroup;
import charters.card.group.ColorCounter;
import charters.card.group.DeckLists;

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
 * 	In designgroup: breakdown to reds, breakdown to improvements, count
 */
public abstract class CardDiagnoser
{
	public static void main(String... args)
	{
		CardGroup allCards = new CardGroup("All");
		//allCards.add(CardSupplier.CHARACTER_SUPPLIER.getAll());
		//allCards.add(CardSupplier.ITEM_SUPPLIER.getAll());
		allCards.add(CardSupplier.IMPROVEMENT_SUPPLIER.getAll());
		
		print("Beginning Diagnosis...");
		
		LinkedList<CardGroup> diags = getDiagnosables(allCards);
		diagnose(diags);
		
		print("Reprinting each card in this deck for each occurence.");
		CardGroup baseGroup = DeckLists.get().basePlayableDeck;
		baseGroup.forEveryCard
		(
			new CardAnalyzer<Card>()
			{
				@Override
				public void accept(Card t)
				{
					print("(" + t.getDesign().color + ") Reprinting: " + t.getDesign().name + " @ " + t.getDesign().rarity);
				}
			}
		);
		diagnose(getDiagnosables(baseGroup));
	}
	
	/** Split the passed group into commonly analyzed subsets */
	public static LinkedList<CardGroup> getDiagnosables(CardGroup group)
	{
		//To be returned
		LinkedList<CardGroup> diagnosables = new LinkedList<CardGroup>();
		
		//Add each set as a separate diagnosable
		LinkedList<CardGroup> sets = group.setSplit();
		diagnosables.addAll(sets);
		
		for (CardGroup setGroup : sets)
		{
			diagnosables.addAll(setGroup.designTypeSplit());
			diagnosables.addAll(setGroup.colorSplit());
		}
		
		print("Found " + diagnosables.size() + " subgroups to analyze from all present cards.");
		
		return diagnosables;
	}

	/** @param groups - All the groups to be diagnosed */
	public static void diagnose(Collection<CardGroup> groups)
	{
		for (CardGroup group : groups) {diagnose(group);}
	}
	
	/**
	 * Prints a bunch of diagnostic information about the current designs in file
	 * Just keep adding stuff to this function honestly
	 */
	public static void diagnose(CardGroup group)
	{
		//Analyze colors
		for (Color color : Color.values())
		{
			ColorCounter counter = new ColorCounter(color);
			group.forEveryCard(counter);
			print(group, color.name() + " Color", counter.getCount(), group.size());
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
	
	public static void print(CardGroup group, String quality, int numerator, int denominator)
	{
		print(group, quality + ": " + numerator + "/" + denominator + " (" + (numerator * 100) / denominator + "%)");
	}
}
