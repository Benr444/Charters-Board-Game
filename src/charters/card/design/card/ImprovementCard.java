package charters.card.design.card;

import java.io.File;

import charters.card.design.refactor.CardDesign;
import charters.card.design.refactor.ImprovementDesign;
import charters.card.visual.SVGEdits;

public class ImprovementCard extends PlayableCard
{
	//==========PUBLIC CONSTANTS==========//
	
	public static final String SIMPLE_NAME = "improvement";

	//==========PUBLIC INTERFACE==========//
	
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
	
	//==========PRIVATE HELPER METHODS==========//

	@Override
	protected String getSimpleName()
	{
		return SIMPLE_NAME;
	}

	@Override
	protected Class<ImprovementDesign> getDesignType()
	{
		return ImprovementDesign.class;
	}
}
