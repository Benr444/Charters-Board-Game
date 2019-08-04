package charters.card.design.card;

import java.io.File;

import charters.card.design.design.ItemDesign;
import charters.card.design.diag.splitter.GroupSplitter;
import charters.card.design.group.CardGroup;
import charters.card.visual.SVGEdits;

public class ItemCard extends Card
{
	//==========PUBLIC CONSTANTS==========//

	public static final String DESIGN_TYPE_NAME = "item";

	//id-values. These are the ID's searched for for various design variables
	public static final String INFLUENCE_BONUS_ID = "influence-bonus";
	public static final String INFLUENCE_BONUS_TEXT = "+1 Influence";
	
	//==========CONSTRUCTOR==========//
	
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
	
	//==========PUBLIC INTERFACE==========//

	@Override
	public ItemDesign getDesign()
	{
		return (ItemDesign)super.getDesign();
	}
	
	@Override
	public void addToGroup(CardGroup group) {group.add(this);}
	
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
	public String getDesignTypeName()
	{
		return DESIGN_TYPE_NAME;
	}

	@Override
	protected Class<ItemDesign> getDesignType()
	{
		return ItemDesign.class;
	}
}
