package charters.card.design.card;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonIgnore;

import charters.card.design.design.PlayableDesign;
import charters.card.design.design.PlayableDesign.Ability;
import charters.card.visual.SVGEdits;

public abstract class PlayableCard extends Card
{
	//==========PUBLIC CONSTANTS==========//
	
	//id-values. These are the ID's searched for for various design variables
	public static final String COST_ID = "cost";
	public static final String HP_ID = "hp";
	public static final String ABILITY_ID = "ability-";
	
	//Template-control constants. Used for filling the template
	/** Number of slots for abilities present on template */
	public static final int NUM_TEMPLATE_ABILITIES = 3;
	
	//==========CONSTRUCTOR==========//
	
	public PlayableCard
	(
		File designFile,
		File vectorFolder,
		File vectorTemplateFile,
		File rasterFolder,
		File autoArtFolder,
		File templateArtFolder
	)
	{
		super(designFile, vectorFolder, vectorTemplateFile, rasterFolder, autoArtFolder, templateArtFolder);
	}
	
	//==========PUBLIC INTERFACE==========//
	
	@Override
	public PlayableDesign getDesign()
	{
		return (PlayableDesign)super.getDesign();
	}
	
	//==========PRIVATE HELPERS==========//
	
	@Override
	protected SVGEdits getEdits()
	{
		SVGEdits edits = super.getEdits();

		//Create cost edit
		edits.addTextEdit
		(
			new SVGEdits.TextEdit("id", COST_ID, getDesign().cost)
		);
		
		//Create HP edit
		edits.addTextEdit
		(
			new SVGEdits.TextEdit("id", HP_ID, getDesign().HP + "HP")
		);
		
		//Create abilities edits
		for (int n = 0; n < getDesign().abilities.length; n++)
		{
    		edits.addTextEdit
    		(
    			new SVGEdits.TextEdit("id", ABILITY_ID + n, getDesign().abilities[n].get())
    		);
		}
		
		//Blank any empty ability fields from the template
		for (int m = getDesign().abilities.length - 1; m < NUM_TEMPLATE_ABILITIES; m++)
		{
    		edits.addTextEdit
    		(
    			new SVGEdits.TextEdit("id", ABILITY_ID + m, "") //Blank that field
    		);
		}
		
		return edits;
	}
	
    @JsonIgnore
    protected String getCombinedAbilities() 
    {
    	Ability[] designAbils = getDesign().abilities;
    	if (designAbils.length == 0) {return "";}
    	if (designAbils.length == 1) {return designAbils[0].get();}
    	else
    	{
    		//All this to avoid extra spaces
    		String returnVal = "";
    		for (int i = 0; i < designAbils.length - 1; i++)
    		{returnVal = returnVal + designAbils[i].get() + " | ";}
    		return returnVal + designAbils[designAbils.length - 1];
    	}
    }
}
