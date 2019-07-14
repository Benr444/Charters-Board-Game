package charters.card.design;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import charters.card.design.CardDesign.Color;
import charters.card.design.CardDesign.Rarity;
import charters.card.visual.data.PlayableSVGEdits;
import charters.card.visual.data.SVGEdits;

/**
 * Common superclass for Improvement and Character cards
 */
public abstract class PlayableDesign extends CardDesign
{
	//===DEFAULT-VALUES===//
	public static final int DEFAULT_HP = -1;
	public static final String DEFAULT_COST = "[0]";
	public static final String[] DEFAULT_ABILITIES = {};
	
	public PlayableDesign()
	{
		this.HP = DEFAULT_HP;
		this.cost = DEFAULT_COST;
		this.abilities = DEFAULT_ABILITIES;
	}
	
	@JsonProperty(required = true)
	@JsonPropertyDescription("The printed HP of the card. Defaults to " + DEFAULT_HP + ". | In template, mark with id=" + PlayableSVGEdits.HP_ID)
	public final int HP;
    
    @JsonProperty(required = false)
	@JsonPropertyDescription("The directly printed cost of the card. Please follow current cost-formatting guidelines. Defaults to " + DEFAULT_COST + ". | In template, mark with id=" + PlayableSVGEdits.COST_ID)
	public final String cost;
    
    @JsonProperty(required = false)
	@JsonPropertyDescription("The directly printed abilities of the cards. Please format them as follows: AbilityName: Cost/Trigger=>Effect. Defaults to empty. | In template, mark with id=" + PlayableSVGEdits.ABILITIES_ID)
	public final String[] abilities;
    
    @JsonIgnore
    public String getCombinedAbilities() 
    {
    	if (abilities.length == 0) {return "";}
    	if (abilities.length == 1) {return abilities[0];}
    	else
    	{
    		//All this to avoid extra spaces
    		String returnVal = "";
    		for (int i = 0; i < abilities.length - 1; i++)
    		{returnVal = returnVal + abilities[0] + " ";}
    		return returnVal + abilities[abilities.length - 1];
    	}
    }
}
