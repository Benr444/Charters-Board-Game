package charters.card.visual.data;

import charters.card.design.ItemDesign;
import javafx.util.Pair;

public class ItemSVGEdits extends SVGEdits
{
	//id-values. These are the ID's searched for for various design variables
	public static final String INFLUENCE_BONUS_ID = "influence-bonus";
	public static final String INFLUENCE_BONUS_TEXT = "+1 Influence";
	
	public ItemSVGEdits(ItemDesign design)
	{
		super(design);
		
		//Add influence bonus edit
		if (design.isInfluenceBonus) //Conditional on the item actually being flagged for bonus influence
		{
			this.textEdits.push
			(
				new Pair<String, String>
				(
					INFLUENCE_BONUS_ID,
					INFLUENCE_BONUS_TEXT
				)
			);
		}
	}

}
