package charters.card.visual.data;

import charters.card.design.PlayableDesign;
import javafx.util.Pair;

public abstract class PlayableSVGEdits extends SVGEdits
{
	//id-values. These are the ID's searched for for various design variables
	public static final String COST_ID = "cost";
	public static final String HP_ID = "hp";
	public static final String ABILITIES_ID = "abilities";
	
	public PlayableSVGEdits(PlayableDesign design)
	{
		super(design);
		
		//Create cost edit
		this.textEdits.push
		(
			new Pair<String, String>(COST_ID, design.cost)
		);
		
		//Create HP edit
		this.textEdits.push
		(
			new Pair<String, String>(HP_ID, design.HP + "HP")
		);
		
		//Create abilities edit. TODO: allow multiple ability fields
		this.textEdits.push
		(
			new Pair<String, String>(ABILITIES_ID, design.getCombinedAbilities())
		);
	}
}
