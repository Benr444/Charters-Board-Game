package charters.card.visual.print;

import charters.card.design.card.Card;
import charters.card.design.diag.CardAnalyzer;

public class PrintPasser implements CardAnalyzer<Card>
{
	public final CardPrintout printout;
	
	public PrintPasser(CardPrintout printout)
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
