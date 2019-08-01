package charters.card.design.card;

import java.io.File;

import charters.card.design.refactor.CharacterDesign;
import charters.card.visual.SVGEdits;

public class CharacterCard extends PlayableCard
{
	//==========PUBLIC CONSTANTS==========//
	
	public static final String SIMPLE_NAME = "character";

	//id-values. These are the ID's searched for for various design variables
	public static final String AK_ID = "ak";
	public static final String MS_ID = "ms";
	public static final String RG_ID = "rg";

	//==========PUBLIC INTERFACE==========//
	
	public CharacterCard
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
	protected CharacterDesign getDesign()
	{
		return (CharacterDesign)super.getDesign();
	}
	
	@Override
	protected String getSimpleName()
	{
		return SIMPLE_NAME;
	}

	@Override
	protected Class<CharacterDesign> getDesignType()
	{
		return CharacterDesign.class;
	}

	@Override
	protected SVGEdits getEdits()
	{
		SVGEdits edits = super.getEdits();

		//Create AK edit
		edits.addTextEdit
		(
			new SVGEdits.TextEdit("id", AK_ID, getDesign().AK + "AK")
		);

		//Create MS edit
		edits.addTextEdit
		(
			new SVGEdits.TextEdit("id", MS_ID, getDesign().MS + "MS")
		);

		//Create RG edit
		edits.addTextEdit
		(
			new SVGEdits.TextEdit("id", RG_ID, getDesign().RG + "RG")
		);
		
		return edits;
	}
}