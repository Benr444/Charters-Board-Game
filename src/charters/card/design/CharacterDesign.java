package charters.card.design;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import charters.card.visual.data.CharacterSVGEdits;
import charters.card.visual.data.ImprovementSVGEdits;
import charters.card.visual.data.SVGEdits;

/**
 * All data needed to describe a character card
 */
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
	
	@JsonIgnore
	@Override
	public String getDesignTypeName() {return "character";}

	@Override
	public CharacterSVGEdits getEdits() {return new CharacterSVGEdits(this);}

    @JsonProperty(required = true)
	@JsonPropertyDescription("The printed AK of the card. Defaults to -1. | On template, mark with id=" + CharacterSVGEdits.AK_ID)
	public int AK;

    @JsonProperty(required = true)
	@JsonPropertyDescription("The printed MS of the card. Defaults to -1. | On template, mark with id=" + CharacterSVGEdits.AK_ID)
	public int MS;

    @JsonProperty(required = true)
	@JsonPropertyDescription("The printed RG of the card. Defaults to -1. | On template, mark with id=" + CharacterSVGEdits.AK_ID)
	public int RG;
}
