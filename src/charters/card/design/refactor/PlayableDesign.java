package charters.card.design.refactor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import charters.card.design.Playable.Design.Ability;
import charters.card.design.card.PlayableCard;

public class PlayableDesign extends CardDesign
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
	
	public PlayableDesign()
	{
		this.HP = DEFAULT_HP;
		this.cost = DEFAULT_COST;
		this.abilities = DEFAULT_ABILITIES;
	}
	
	@JsonProperty(required = true)
	@JsonPropertyDescription("The printed HP of the card. Defaults to " + DEFAULT_HP + ". | In template, mark with id=" + PlayableCard.HP_ID)
	public final int HP;
    
    @JsonProperty(required = false)
	@JsonPropertyDescription("The directly printed cost of the card. Please follow current cost-formatting guidelines. Defaults to " + DEFAULT_COST + ". | In template, mark with id=" + PlayableCard.COST_ID)
	public final String cost;
    
    @JsonProperty(required = false)
	@JsonPropertyDescription("The directly printed abilities of the cards. Please format them carefully. | In template, mark with id=" + PlayableCard.ABILITY_ID + "0, 1, 2, etc")
	public final Ability[] abilities;
}
