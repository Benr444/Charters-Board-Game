package charters.card.design.refactor;

import java.util.Iterator;

import charters.card.design.card.Card;

public class CardGroup implements Iterable<Card>
{
	protected static class CardGroupIterator implements Iterator<Card>
	{

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
		return new CardGroupIterator();
	}
}
