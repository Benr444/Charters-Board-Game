package charters.card.design;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import charters.card.visual.SVGEdits;
import charters.card.visual.SVGEdits.TextEdit;
import javafx.util.Pair;


public abstract class Playable extends Card
{
    /**
     * Common superclass for Improvement and Character cards
     */
    public static abstract class Design extends Card.Design
    {
    	public static class Ability
    	{
    		private static final String DEFAULT_NAME = "Nameless";
    		private static final String DEFAULT_TEXT = "No effect";
    		@JsonProperty(required = true)
    		@JsonPropertyDescription("Printed name of the ability.")
    		public final String name;
    		@JsonProperty(required = true)
    		@JsonPropertyDescription("Printed cost/effect of the ability.")
    		public final String text;
    		
    		private Ability()
    		{
    			this.name = Ability.DEFAULT_NAME;
    			this.text = Ability.DEFAULT_TEXT;
    		}
    		
    		public String get()
    		{
    			return this.name + ": " + this.text;
    		}
    	}
    	
    	//===DEFAULT-VALUES===//
    	public static final int DEFAULT_HP = -1;
    	public static final String DEFAULT_COST = "[0]";
    	public static final Ability[] DEFAULT_ABILITIES = {new Ability(), new Ability(), new Ability()};
    	
    	public Design()
    	{
    		this.HP = DEFAULT_HP;
    		this.cost = DEFAULT_COST;
    		this.abilities = DEFAULT_ABILITIES;
    	}
    	
    	@JsonProperty(required = true)
    	@JsonPropertyDescription("The printed HP of the card. Defaults to " + DEFAULT_HP + ". | In template, mark with id=" + HP_ID)
    	public final int HP;
        
        @JsonProperty(required = false)
    	@JsonPropertyDescription("The directly printed cost of the card. Please follow current cost-formatting guidelines. Defaults to " + DEFAULT_COST + ". | In template, mark with id=" + COST_ID)
    	public final String cost;
        
        @JsonProperty(required = false)
    	@JsonPropertyDescription("The directly printed abilities of the cards. Please format them carefully. | In template, mark with id=" + ABILITY_ID + "0, 1, 2, etc")
    	public final Ability[] abilities;
        
        @JsonIgnore
        public String getCombinedAbilities() 
        {
        	if (abilities.length == 0) {return "";}
        	if (abilities.length == 1) {return abilities[0].get();}
        	else
        	{
        		//All this to avoid extra spaces
        		String returnVal = "";
        		for (int i = 0; i < abilities.length - 1; i++)
        		{returnVal = returnVal + abilities[i].get() + " | ";}
        		return returnVal + abilities[abilities.length - 1];
        	}
        }
    }
    
	//id-values. These are the ID's searched for for various design variables
	public static final String COST_ID = "cost";
	public static final String HP_ID = "hp";
	public static final String ABILITY_ID = "ability-";
	
	//Template-control constants. Used for filling the template
	/** Number of slots for abilities present on template */
	public static final int NUM_TEMPLATE_ABILITIES = 3;
	
    @Override
    public SVGEdits getEdits(Card.Design design)
	{
    	SVGEdits edits = super.getEdits(design);
    	
    	//print("getEdits: design class is " + design.getClass());
    	if (getDesignType().isAssignableFrom(design.getClass()))
        {
			Playable.Design playableDesign = (Playable.Design)design;
    		//Create cost edit
    		edits.addTextEdit
    		(
    			new SVGEdits.TextEdit("id", COST_ID, playableDesign.cost)
    		);
    		
    		//Create HP edit
    		edits.addTextEdit
    		(
    			new SVGEdits.TextEdit("id", HP_ID, playableDesign.HP + "HP")
    		);
    		
    		//Create abilities edits
    		for (int n = 0; n < playableDesign.abilities.length; n++)
    		{
        		edits.addTextEdit
        		(
        			new SVGEdits.TextEdit("id", ABILITY_ID + n, playableDesign.abilities[n].get())
        		);
    		}
    		
    		//Blank any empty ability fields from the template
    		for (int m = playableDesign.abilities.length - 1; m < NUM_TEMPLATE_ABILITIES; m++)
    		{
        		edits.addTextEdit
        		(
        			new SVGEdits.TextEdit("id", ABILITY_ID + m, "") //Blank that field
        		);
    		}
    	}
    	
    	return edits;
	}
}