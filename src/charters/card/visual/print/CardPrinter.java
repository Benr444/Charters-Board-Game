package charters.card.visual.print;

import java.util.LinkedList;

import charters.card.design.group.CardGroup;
import charters.card.design.script.CardSupplier;

public class CardPrinter
{
	public static void main(String... args)
	{
		CardGroup allCards = new CardGroup("All");
		//allCards.add(CardSupplier.CHARACTER_SUPPLIER.getAll());
		//allCards.add(CardSupplier.IMPROVEMENT_SUPPLIER.getAll());
		allCards.add(CardSupplier.ITEM_SUPPLIER.getAll());
		LinkedList<CardGroup> sets = allCards.setSplit();
		for (CardGroup set : sets)
		{
			//Construct a printout (becomes pdf file)
			//CardPrintout printout = new CardPrintout(set.name);
			CardPrintout printout = new CardPrintout("YEESNAW");
			
			//Pass every card in the set to the printout one at a time
			set.forEveryCard(new PrintPasser(printout));
			
			printout.export();
		}
	}
}
