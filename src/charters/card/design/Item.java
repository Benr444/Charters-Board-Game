package charters.card.design;

import java.util.LinkedList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import charters.card.visual.SVGEdits;
import javafx.util.Pair;

public class Item extends Card
{
	//id-values. These are the ID's searched for for various design variables
	public static final String INFLUENCE_BONUS_ID = "influence-bonus";
	public static final String INFLUENCE_BONUS_TEXT = "+1 Influence";
	
	/**
	 * All the data needed for an item design.
	 */
	public static class Design extends Card.Design
	{
		//===DEFAULT_VALUES===//
		public static final boolean DEFAULT_IS_INFLUENCE_BONUS = false;
		
		public Design()
		{
			isInfluenceBonus = DEFAULT_IS_INFLUENCE_BONUS;
		}
	    
	    @JsonProperty(required = false)
	    @JsonPropertyDescription("Set to true if this item can be revealed for +1 influence. Defaults to false. | In template, mark with id=" + INFLUENCE_BONUS_ID)
	    public boolean isInfluenceBonus;

		@Override
		public Card getFactory() {return new Item();}
	}

	@Override
	public String getDesignTypeName() {return "item";}

	@Override
	public Class<? extends Card.Design> getDesignType() {return Item.Design.class;}
	
	@Override public LinkedList<Item.Design> readDesigns()
	{
		return (LinkedList<Item.Design>)super.readDesigns();
	}

	@Override
	public SVGEdits getEdits(Card.Design design) 
	{
		SVGEdits edits = super.getEdits(design);
		
		//Add influence bonus edit
		if (design.getClass().equals(getDesignType())) //TODO: Not my favorite thing, but if I had to pick ONE type of downcasting...
		{
			Item.Design itemDesign = (Item.Design)design;
			if (itemDesign.isInfluenceBonus) //Conditional on the item actually being flagged for bonus influence
			{
				edits.addTextEdit
				(
					new Pair<String, String>
					(
						INFLUENCE_BONUS_ID,
						INFLUENCE_BONUS_TEXT
					)
				);
			}
		}
		return edits;
	}
}