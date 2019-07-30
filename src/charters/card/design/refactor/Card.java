package charters.card.design.refactor;

import java.io.File;

/**
 * Active representation of a card
 * Should be agnostic to project structure
 */
public abstract class Card
{
	//==========CONSTANTS==========//
	
	//==========PRIVATE FIELDS==========//
	
	/** The file that contains all the design data */
	private File designFile;
	
	/** All the data directly in-read from the .json design file */
	private CardDesign design;
	
	/** Reference to factory singleton instance */
	private static final CardFactory factory = CardFactory.getInstance();
	
	//==========CONSTRUCTORS==========//
	
	public Card(File designFile)
	{
		this.designFile = designFile;
		this.readDesign();
	}
	
	//==========PUBLIC INTERFACE==========//
	
	/** @return - The file that represents the .json design file for this card */
	public abstract File getDesignFile();
	
	/** @return - The file that represents the .svg visual for this card */
	public abstract File getVectorFile();
	
	/** @return - The file that represents the .(raster) visual for this card */
	public abstract File getRasterFile();
	
	//==========PRIVATE HELPERS==========//
	/** Reads the design file into the design object */
	private void readDesign()
	{
		
	}
}
