package charters.card.design.refactor;

import java.io.File;

/**
 * A class that manages the project folder system to allow inreading of various card-related data
 */
public class CardSupplier
{
	//==========PUBLIC ENUM INSTANCES==========
	
	public static final CardFactory ITEM_FACTORY
	(
		
	);
	public static final CardFactory CHARACTER_FACTORY
	(
		
	);
	public static final CardFactory IMPROVEMENT_FACTORY
	(
	
	);
	
	//==========PRIVATE MEMBERS==========//
	
	
	//==========PUBLIC INTERFACE==========//
	
	public final File designFolder;
	public final File vectorFolder;
	public final File rasterFolder;
	
	private CardFactory(String designFolderPath, String vectorFolderPath, String rasterFolderPath)
	{
		designFolder = new File(designFolderPath);
		vectorFolder = new File(vectorFolderPath);
		rasterFolder = new File(rasterFolderPath);
	}
	
	/**
	 * @return - The file that represents the .schema.json schema file for this card type
	 */
	public File getSchemaFile()
	{
		
	}
	
	/**
	 * @return - The file that represents the .svg template file for this card type
	 */
	public File getTemplateFile()
	{
		
	}
}
