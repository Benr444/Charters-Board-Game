package charters.card.design;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import charters.card.visual.data.ItemSVGEdits;

/**
 * All the data needed for an item design.
 */
public class ItemDesign extends CardDesign
{
	//===DEFAULT_VALUES===//
	public static final boolean DEFAULT_IS_INFLUENCE_BONUS = false;
	
	public ItemDesign()
	{
		isInfluenceBonus = DEFAULT_IS_INFLUENCE_BONUS;
	}
	
	@JsonIgnore
	@Override
	public String getDesignTypeName()
	{
		return "item";
	}

	@Override
	public ItemSVGEdits getEdits() {return new ItemSVGEdits(this);}
    
    @JsonProperty(required = false)
    @JsonPropertyDescription("Set to true if this item can be revealed for +1 influence. Defaults to false. | In template, mark with id=" + ItemSVGEdits.INFLUENCE_BONUS_ID)
    public boolean isInfluenceBonus;
}
