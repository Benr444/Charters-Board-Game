package charters.card.design;

import java.util.LinkedList;

import charters.card.visual.SVGEdits;

public class Improvement extends Card
{
	
	/**
	 * All the data needed to describe an Improvement Card
	 */
	public static class Design extends Playable.Design
	{
		@Override
		public Card getFactory() {return new Improvement();}
	}

	@Override
	public String getDesignTypeName() {return "improvement";}

	@Override
	public Class<Improvement.Design> getDesignType() {return Improvement.Design.class;}
	
	@Override public LinkedList<Improvement.Design> readDesigns()
	{
		return (LinkedList<Improvement.Design>)super.readDesigns();
	}

	@Override
	public SVGEdits getEdits(Card.Design design) 
	{
		return super.getEdits(design);
	}
}