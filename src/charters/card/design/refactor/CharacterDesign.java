package charters.card.design.refactor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import charters.card.design.card.CharacterCard;

public class CharacterDesign extends PlayableDesign
{
	//===DEFAULT-VALUES===//
	public static final int DEFAULT_AK = -1;
	public static final int DEFAULT_MS = -1;
	public static final int DEFAULT_RG = -1;
	
	public CharacterDesign()
	{
		this.AK = DEFAULT_AK;
		this.MS = DEFAULT_MS;
		this.RG = DEFAULT_RG;
	}

    @JsonProperty(required = true)
	@JsonPropertyDescription("The printed AK of the card. Defaults to -1. | On template, mark with id=" + CharacterCard.AK_ID)
	public int AK;

    @JsonProperty(required = true)
	@JsonPropertyDescription("The printed MS of the card. Defaults to -1. | On template, mark with id=" + CharacterCard.AK_ID)
	public int MS;

    @JsonProperty(required = true)
	@JsonPropertyDescription("The printed RG of the card. Defaults to -1. | On template, mark with id=" + CharacterCard.AK_ID)
	public int RG;
}
