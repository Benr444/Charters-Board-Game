package charters.card.design.card;

import java.io.File;

import charters.card.design.Item;
import charters.card.design.refactor.ItemDesign;
import charters.card.visual.SVGEdits;

public class ItemCard extends Card
{
	//==========PUBLIC CONSTANTS==========//

	public static final String SIMPLE_NAME = "item";

	//id-values. These are the ID's searched for for various design variables
	public static final String INFLUENCE_BONUS_ID = "influence-bonus";
	public static final String INFLUENCE_BONUS_TEXT = "+1 Influence";
	
	//==========PUBLIC INTERFACE==========//
	
	public ItemCard
	(
		File designFile,
		File vectorFolder,
		File vectorTemplateFile,
		File rasterFolder,
		File autoArtFolder,
		File templateArtFolder)
	{
		super(designFile, vectorFolder, vectorTemplateFile, rasterFolder, autoArtFolder, templateArtFolder);
	}

	//==========PRIVATE HELPER METHODS==========//
	
	@Override
	protected SVGEdits getEdits()
	{
		SVGEdits edits = super.getEdits();
		
		//Add influence bonus edit
		if (getDesign().isInfluenceBonus) //Conditional on the item actually being flagged for bonus influence
		{
			edits.addTextEdit
			(
				new SVGEdits.TextEdit
				(
					"id",
					INFLUENCE_BONUS_ID,
					INFLUENCE_BONUS_TEXT
				)
			);
		}
    	
		return edits;
	}
	
	@Override
	protected ItemDesign getDesign()
	{
		return (ItemDesign)super.getDesign();
	}

	@Override
	protected String getSimpleName()
	{
		return SIMPLE_NAME;
	}

	@Override
	protected Class<ItemDesign> getDesignType()
	{
		return ItemDesign.class;
	}
}
