package charters.card.design;

import com.fasterxml.jackson.annotation.JsonIgnore;

import charters.card.visual.data.ImprovementSVGEdits;

/**
 * All the data needed to describe an Improvement Card
 */
public class ImprovementDesign extends PlayableDesign
{
	@JsonIgnore
	@Override
	public String getDesignTypeName()
	{
		return "improvement";
	}

	@Override
	public ImprovementSVGEdits getEdits() {return new ImprovementSVGEdits(this);}
}
