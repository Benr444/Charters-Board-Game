package charters.card.visual.data;

import charters.card.design.CharacterDesign;
import javafx.util.Pair;

public class CharacterSVGEdits extends PlayableSVGEdits
{
	//id-values. These are the ID's searched for for various design variables
	public static final String AK_ID = "ak";
	public static final String MS_ID = "ms";
	public static final String RG_ID = "rg";

	public CharacterSVGEdits(CharacterDesign design)
	{
		super(design);
		
		//Create AK edit
		this.textEdits.push
		(
			new Pair<String, String>(AK_ID, design.AK + "AK")
		);

		//Create MS edit
		this.textEdits.push
		(
			new Pair<String, String>(MS_ID, design.MS + "MS")
		);

		//Create RG edit
		this.textEdits.push
		(
			new Pair<String, String>(RG_ID, design.RG + "RG")
		);
	}

}
