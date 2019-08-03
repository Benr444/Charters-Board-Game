package charters.card.design.card;

import java.io.File;

import charters.card.design.design.CardDesign;
import charters.card.design.design.ImprovementDesign;
import charters.card.design.group.CardGroup;
import charters.card.visual.SVGEdits;

public class ImprovementCard extends PlayableCard
{
	//==========PUBLIC CONSTANTS==========//
	
	public static final String DESIGN_TYPE_NAME = "improvement";

	//==========CONSTRUCTOR==========//
	
	public ImprovementCard
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
	public void addToGroup(CardGroup group) {group.add(this);};

	@Override
	public String getDesignTypeName()
	{
		return DESIGN_TYPE_NAME;
	}

	//==========PRIVATE HELPER METHODS==========//

	@Override
	protected Class<ImprovementDesign> getDesignType()
	{
		return ImprovementDesign.class;
	}
}
