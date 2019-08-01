package charters.card.design.refactor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.fasterxml.jackson.databind.ObjectMapper;

import charters.card.visual.SVGEdits;

/**
 * Active representation of a card
 * Should be agnostic to project structure
 */
public class Card
{
	//==========CONSTANTS==========//
	
	public static final String VECTOR_EXT = ".svg";
	public static final String RASTER_EXT = ".png";

	public static final int RASTER_DPI = 200;
	
	//==========PRIVATE FIELDS==========//
	
	//private final CardSupplier supplier;
	
	/** The file that contains all the design data */
	protected final File designFile;
	
	/** The file that contains all the vector data */
	protected final File vectorFile;
	
	/** The file that contains all the raster data */
	protected final File rasterFile;
	
	/** All the data directly in-read from the .json design file */
	protected final CardDesign design;
	
	/** The class that corresponds to this card design's type. Must be known ahead of time for deserialization */
	protected final Class<? extends CardDesign> cardType;
	
	//==========CONSTRUCTORS==========//
	
	public Card(File designFile, File vectorFolder, File rasterFolder, Class<? extends CardDesign> cardType)
	{
		this.designFile = designFile;
		this.cardType = cardType;
		this.design = this.readDesign();
		this.vectorFile = new File(vectorFolder.getPath() + this.design.name + VECTOR_EXT);
		this.rasterFile = new File(rasterFolder.getPath() + this.design.name + RASTER_EXT);
	}
	
	//==========PUBLIC INTERFACE==========//
	
	/** @return - The file that represents the .json design file for this card */
	public File getDesignFile()
	{
		return this.designFile;
	};
	
	/** @return - The file that represents the .svg visual for this card */
	public File getVectorFile()
	{
		return this.vectorFile;
	};
	
	/** @return - The file that represents the .(raster) visual for this card */
	public File getRasterFile()
	{
		return this.rasterFile;
	};
	
	/** 
	 * <b>YOU MUST HAVE INKSCAPE INSTALLED AND ON YOUR ENVIROMENTAL PATH FOR THIS TO RUN!</b>
	 * Overwrites the raster image for the card with an updated version.
	 * @return - The raster file
	 */
	public File rasterize()
	{
		String cmd = "inkscape " + vectorFile.getAbsolutePath() + " -e " +
					 rasterFile.getAbsolutePath() + " -d " + RASTER_DPI;
    	try
    	{
    		Runtime.getRuntime().exec(cmd);
    		print("Executing command: " + cmd);
    	} 
    	catch (IOException e) {e.printStackTrace();}
		return getRasterFile();
	}
	
	/** 
	 * Overwrites the vector image for the card with an updated version.
	 * @return - The vector file
	 */
	public File vectorize()
	{
		//Get all SVGEdits
		SVGEdits edits = getEdits();
		//Apply the SVGEdits to the template
		//Save the template
		return getVectorFile();
	}
	
	//==========PRIVATE HELPERS==========//
	
	/** Reads the design file into the design object */
	private CardDesign readDesign()
	{
		String jsonContents;
		try {jsonContents = new String(Files.readAllBytes(designFile.toPath()));}
		catch (IOException e)
		{
			e.printStackTrace();
			jsonContents = "";
		}
		ObjectMapper deserializer = new ObjectMapper();
		CardDesign currentDesign;
		try {currentDesign = deserializer.readValue(jsonContents, cardType);} 
		catch (IOException e)
		{
			e.printStackTrace();
			currentDesign = null;
		}
		print("\tFound card design: " + currentDesign.name + " (" + currentDesign.getClass().getCanonicalName() + ")");
		return currentDesign;
	}
	
	protected SVGEdits getEdits()
	{
		SVGEdits edits = new SVGEdits();
		return edits;
	}
	
	private static void print(String s)
	{
		System.out.println("[Card]: " + s);
	}
}
