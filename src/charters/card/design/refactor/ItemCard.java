package charters.card.design.refactor;

import java.io.File;

import charters.card.visual.SVGEdits;

public class ItemCard extends Card
{
	//==========PUBLIC CONSTANTS==========//

	public static final String SIMPLE_NAME = "item";
	
	//==========PUBLIC INTERFACE==========//
	
	public ItemCard(File designFile, File vectorFolder, File rasterFolder, Class<? extends CardDesign> cardType)
	{
		super(designFile, vectorFolder, rasterFolder, cardType);
	}

	@Override
	protected SVGEdits getEdits()
	{
		SVGEdits edits = super.getEdits();
		//Add some edits
		return edits;
	}
}
