package charters.card.design.refactor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import charters.card.design.card.ItemCard;

public class ItemDesign extends CardDesign
{
	//===DEFAULT_VALUES===//
	public static final boolean DEFAULT_IS_INFLUENCE_BONUS = false;
	
	public ItemDesign()
	{
		isInfluenceBonus = DEFAULT_IS_INFLUENCE_BONUS;
	}
    
    @JsonProperty(required = false)
    @JsonPropertyDescription("Set to true if this item can be revealed for +1 influence. Defaults to false. | In template, mark with id=" + ItemCard.INFLUENCE_BONUS_ID)
    public boolean isInfluenceBonus;
}
