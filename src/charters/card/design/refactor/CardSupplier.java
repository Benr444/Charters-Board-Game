package charters.card.design.refactor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;

import com.fasterxml.jackson.databind.ObjectMapper;

import charters.card.design.Card.Design;

/**
 * A class that manages the project folder system to allow inreading of various card-related data
 */
public class CardSupplier
{
	//==========PUBLIC CONSTANTS==========

	public static final String DESIGN_BASE_FOLDER = "resouce/design/card";
		public static final String SCHEMA_REL_FOLDER = "schema";
		public static final String SCHEMA_EXT = ".schema.json";
	public static final String VECTOR_BASE_FOLDER = "resouce/visual/card/vector";
		public static final String TEMPLATE_REL_FOLDER = "template";
		public static final String TEMPLATE_EXT = "-template.svg";
	public static final String RASTER_BASE_FOLDER = "resouce/visual/card/raster";
	
	public static final String SIMPLE_CHARACTER_TYPE_NAME = "character";
	public static final String SIMPLE_IMPROVEMENT_TYPE_NAME = "improvement";
	
	//==========PUBLIC ENUM INSTANCES==========
	
	public static final CardSupplier ITEM_SUPPLIER = new CardSupplier
	(
		ItemDesign.class,
		ItemCard.SIMPLE_NAME,
		DESIGN_BASE_FOLDER + "/" + ItemCard.SIMPLE_NAME,
		VECTOR_BASE_FOLDER + "/" + ItemCard.SIMPLE_NAME,
		RASTER_BASE_FOLDER + "/" + ItemCard.SIMPLE_NAME,
		VECTOR_BASE_FOLDER + "/" + TEMPLATE_REL_FOLDER + ItemCard.SIMPLE_NAME + TEMPLATE_EXT,
		DESIGN_BASE_FOLDER + "/" + SCHEMA_REL_FOLDER + "/" + ItemCard.SIMPLE_NAME + SCHEMA_EXT
	);
	public static final CardSupplier CHARACTER_SUPPLIER = new CardSupplier
	(
		SIMPLE_ITEM_TYPE_NAME,
		DESIGN_BASE_FOLDER + "/" + SIMPLE_CHARACTER_TYPE_NAME,
		VECTOR_BASE_FOLDER + "/" + SIMPLE_CHARACTER_TYPE_NAME,
		RASTER_BASE_FOLDER + "/" + SIMPLE_CHARACTER_TYPE_NAME,
		VECTOR_BASE_FOLDER + "/" + TEMPLATE_REL_FOLDER + SIMPLE_CHARACTER_TYPE_NAME + TEMPLATE_EXT,
		DESIGN_BASE_FOLDER + "/" + SCHEMA_REL_FOLDER + "/" + SIMPLE_CHARACTER_TYPE_NAME + SCHEMA_EXT
	);
	public static final CardSupplier IMPROVEMENT_SUPPLIER = new CardSupplier
	(
		SIMPLE_ITEM_TYPE_NAME,
		DESIGN_BASE_FOLDER + "/" + SIMPLE_IMPROVEMENT_TYPE_NAME,
		VECTOR_BASE_FOLDER + "/" + SIMPLE_IMPROVEMENT_TYPE_NAME,
		RASTER_BASE_FOLDER + "/" + SIMPLE_IMPROVEMENT_TYPE_NAME,
		VECTOR_BASE_FOLDER + "/" + TEMPLATE_REL_FOLDER + SIMPLE_IMPROVEMENT_TYPE_NAME + TEMPLATE_EXT,
		DESIGN_BASE_FOLDER + "/" + SCHEMA_REL_FOLDER + "/" + SIMPLE_IMPROVEMENT_TYPE_NAME + SCHEMA_EXT
	);
	
	//==========PRIVATE MEMBERS==========//
	
	
	//==========PUBLIC INTERFACE==========//
	
	public final Class<? extends CardDesign> designType;
	public final String simpleDesignTypeName;
	
	public final File designFolder;
	public final File vectorFolder;
	public final File rasterFolder;
	public final File template;
	public final File schema;
	
	private CardSupplier
	(
		Class<? extends CardDesign> designType,
		String simgpleDesignTypeName,
		String designFolderPath,
		String vectorFolderPath,
		String rasterFolderPath,
		String schemaPath,
		String templatePath
	)
	{
		this.designType = designType;
		this.simpleDesignTypeName = simgpleDesignTypeName;
		designFolder = new File(designFolderPath);
		vectorFolder = new File(vectorFolderPath);
		rasterFolder = new File(rasterFolderPath);
		this.template = new File(templatePath);
		this.schema = new File(schemaPath);
	}
	
	/**
	 * @param designFile - The file that contains a card design
	 * @return - The Card object for that file
	 */
	public Card get(File designFile)
	{
		try
		{
			return new Card(designFile, vectorFolder, rasterFolder, designType);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return - A CardGroup consisting of all the cards of the designs this supplier manages
	 */
	public CardGroup getAll()
	{
		CardGroup returnGroup = new CardGroup();
		File[] designFiles = designFolder.listFiles();
		if (designFiles != null)
		{
			for (File f : designFiles)
			{
				if (f.isFile())
				{
					print("Found candidate design file: " + f.getName());
					returnGroup.add(get(f));
				}
			}
		}
		return returnGroup;
	}
	
	public static void print(String s)
	{
		System.out.println("[CardSupplier]: " + s);
	}
}
