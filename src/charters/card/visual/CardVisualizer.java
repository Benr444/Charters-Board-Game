package charters.card.visual;

import charters.card.design.card.Card;
import charters.card.design.group.CardGroup;
import charters.card.design.script.CardSupplier;

public abstract class CardVisualizer
{
	public static void main(String... args)
	{	
		CardGroup allCards = new CardGroup();
		allCards.add(CardSupplier.CHARACTER_SUPPLIER.getAll());
		
		allCards.forEveryCard
		(
			(Card c) -> 
			{
				c.vectorize(); 
				print("Vectorized " + c.getDesign().name + " and output to " + c.getVectorFile().getAbsolutePath());
			}
		);
	}
	
	private static void print(String s)
	{
		System.out.println("[Card Visualizer]: " + s);
	}
}
