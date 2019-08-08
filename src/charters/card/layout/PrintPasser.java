package charters.card.layout;

import charters.card.card.Card;
import charters.card.group.CardAnalyzer;

public class PrintPasser implements CardAnalyzer<Card>
{
	public final CardLayout printout;
	
	public PrintPasser(CardLayout printout)
	{
		this.printout = printout;
	}
	
	@Override
	public void accept(Card c)
	{
		System.out.println("[PrintPasser]: passing card " + c.getDesign().name);
		printout.add(c);
	}
}
