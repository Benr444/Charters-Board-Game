package charters.card.group;

import java.util.LinkedList;

import charters.card.card.CardSupplier;

/**
 * A class used to compose deck lists for cards
 * IT IS NOT A DECK SIMULATOR CLASS
 */
public final class DeckLists
{
	//=========STATIC CONSTANTS=========//
	
	public CardGroup basePlayableDeck;
	
	//=========PUBLIC INTERFACE=========//
	
	public static DeckLists get()
	{
		if (instance == null)
		{
			instance = new DeckLists();
		}
		return instance;
	}
			
	//=========PRIVATE=========//
	
	private static DeckLists instance;
	
	private DeckLists()
	{
		//Initialize decks
		basePlayableDeck = new CardGroup("BasePlayableDeck");
		
		//Get all cards
		CardGroup allCards = new CardGroup();
		allCards.add(CardSupplier.CHARACTER_SUPPLIER.getAll());
		allCards.add(CardSupplier.IMPROVEMENT_SUPPLIER.getAll());
		LinkedList<CardGroup> sets = allCards.setSplit();
		for (CardGroup set : sets)
		{
			if (set.name.contains("BASE"))
			{
				basePlayableDeck.add(set);
			}
		}
		basePlayableDeck = basePlayableDeck.getRarityAdjusted();
	};
}
