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
	@JsonIgnore
	@Override
	public String getDesignTypeName()
	{
		return "character";
	}

	@Override
	public CharacterSVGEdits getEdits() {return new CharacterSVGEdits(this);}

    @JsonProperty(required = true)
	@JsonPropertyDescription("The printed AK of the card. Defaults to -1. | On template, mark with id=" + CharacterSVGEdits.AK_ID)
	public int AK = -1;

    @JsonProperty(required = true)
	@JsonPropertyDescription("The printed MS of the card. Defaults to -1. | On template, mark with id=" + CharacterSVGEdits.AK_ID)
	public int MS = -1;

    @JsonProperty(required = true)
	@JsonPropertyDescription("The printed RG of the card. Defaults to -1. | On template, mark with id=" + CharacterSVGEdits.AK_ID)
	public int RG = -1;
}
