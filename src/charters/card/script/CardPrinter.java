package charters.card.script;

import java.util.LinkedList;

import charters.card.card.CardSupplier;
import charters.card.group.CardGroup;
import charters.card.layout.CardLayout;
import charters.card.layout.PrintPasser;

public class CardPrinter
{
	public static void main(String... args)
	{
		CardGroup allCards = new CardGroup("All");
		allCards.add(CardSupplier.CHARACTER_SUPPLIER.getAll());
		allCards.add(CardSupplier.IMPROVEMENT_SUPPLIER.getAll());
		allCards.add(CardSupplier.ITEM_SUPPLIER.getAll());
		LinkedList<CardGroup> sets = allCards.setSplit();
		for (CardGroup set : sets)
		{
			LinkedList<CardGroup> typeSubsets = set.designTypeSplit();
			for (CardGroup subset : typeSubsets)
			{
				//Construct a printout (becomes pdf file)
				//CardPrintout printout = new CardPrintout(set.name);
				CardLayout printout = new CardLayout(subset.name);
				
				//Pass every card in the set to the printout one at a time
				subset.forEveryCard(new PrintPasser(printout));
				
				printout.export();
			}
		}
	}
}
