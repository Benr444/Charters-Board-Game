package charters.card.script;

import java.io.IOException;

import charters.card.card.Card;
import charters.card.card.CardSupplier;
import charters.card.group.CardGroup;

public abstract class CardVisualizer
{
	public static void main(String... args)
	{	
		CardGroup allCards = new CardGroup();
		allCards.add(CardSupplier.CHARACTER_SUPPLIER.getAll());
		allCards.add(CardSupplier.ITEM_SUPPLIER.getAll());
		allCards.add(CardSupplier.IMPROVEMENT_SUPPLIER.getAll());
		
		try
		{
			print("--> Press enter to initiate vectorization.");
			System.in.read();
		} 
		catch (IOException e) {e.printStackTrace();}
		
		allCards.forEveryCard
		(
			(Card c) -> 
			{
				c.vectorize(); 
				print("Vectorized " + c.getDesign().name + " and output to " + c.getVectorFile().getAbsolutePath());
			}
		);
		
		try
		{
			print("--> Press enter to initiate rasterization. Some lag may occur.");
			print("--! Exit or stop to cancel rasterization.");
			System.in.read();
		} 
		catch (IOException e) {e.printStackTrace();}
		
		allCards.forEveryCard
		(
			(Card c) -> 
			{
				c.rasterize(); 
				print("Rasterized " + c.getDesign().name + " and output to " + c.getRasterFile().getAbsolutePath());
			}
		);
	}
	
	private static void print(String s)
	{
		System.out.println("[Card Visualizer]: " + s);
	}
}
