package charters.card.design.refactor;

import java.util.Iterator;
import java.util.LinkedList;

import charters.card.design.card.Card;
import charters.card.design.card.ItemCard;

public class CardGroup implements Iterable<Card>
{
	//==========NESTED CLASS==========//
	
	protected static class CardGroupIterator implements Iterator<Card>
	{
		Card current;
		
		protected CardGroupIterator(CardGroup group)
		{
			current = group.getHead();
		}
		
		@Override
		public boolean hasNext()
		{
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Card next()
		{
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	//==========PRIVATE MEMBER==========//
	
	LinkedList<ItemCard> itemList;
	
	//==========CONSTRUCTOR==========//
	
	public CardGroup(String name)
	{
		this.name = name;
	}
	
	public CardGroup()
	{
		this("unnamed");
	}
	
	//==========PUBLIC INTERFACE==========//
	
	public final String name;
	
	@Deprecated
	public void add(Iterable<? extends Card> cards)
	{
		
	}
	
	@Deprecated
	public void add(Card card)
	{
		
	}

	@Override
	public Iterator<Card> iterator()
	{
		return new CardGroupIterator(this);
	}
	
	//==========PRIVATE HELPER==========//
	
	protected Card getHead()
	{
		return itemList.getFirst();
	}
}
