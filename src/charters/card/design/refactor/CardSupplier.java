package charters.card.design.refactor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.customProperties.HyperSchemaFactoryWrapper;

import charters.card.design.card.Card;
import charters.card.design.card.CharacterCard;
import charters.card.design.card.ImprovementCard;
import charters.card.design.card.ItemCard;

/**
 * A class that manages the project folder system to allow inreading of various card-related data
 */
public abstract class CardSupplier
{
	//==========PUBLIC CONSTANTS==========

	public static final String DESIGN_BASE_FOLDER = "resource/design/card";
		public static final String SCHEMA_REL_FOLDER = "schema";
		public static final String SCHEMA_EXT = ".schema.json";

	public static final String TEMPLATE_REL_FOLDER = "template";
	public static final String VISUAL_BASE_FOLDER = "resource/visual/card";
    	public static final String VECTOR_REL_FOLDER = "vector";
    			public static final String TEMPLATE_VECTOR_EXT = "-template.svg";
    	public static final String RASTER_REL_FOLDER = "raster";
    	public static final String ART_REL_FOLDER = "art";
	
	public static final String TEMPLATE_ART_FOLDER = VISUAL_BASE_FOLDER + "/" + ART_REL_FOLDER + "/" + TEMPLATE_REL_FOLDER;
	
	//==========PUBLIC ENUM INSTANCES==========
	
	public static final ItemSupplier ITEM_SUPPLIER = new ItemSupplier();
	public static final CharacterSupplier CHARACTER_SUPPLIER = new CharacterSupplier();
	public static final ImprovementSupplier IMPROVEMENT_SUPPLIER = new ImprovementSupplier();
	
	//==========PRIVATE MEMBERS==========//
	
	//==========SUBCLASSES==========//
	
	public static class ItemSupplier extends CardSupplier
	{
		protected ItemSupplier()
		{
			super(ItemCard.SIMPLE_NAME);
		}
		
		@Override
		public ItemCard get(File designFile)
		{
			return new ItemCard(designFile, vectorFolder, vectorTemplateFile, rasterFolder, autoArtFolder, new File(TEMPLATE_ART_FOLDER));
		}

		@Override
		protected Class<ItemDesign> getDesignType() {return ItemDesign.class;}
	}
	
	public static class CharacterSupplier extends CardSupplier
	{
		protected CharacterSupplier()
		{
			super(CharacterCard.SIMPLE_NAME);
		}
		
		@Override
		public CharacterCard get(File designFile)
		{
			return new CharacterCard(designFile, vectorFolder, vectorTemplateFile, rasterFolder, autoArtFolder, new File(TEMPLATE_ART_FOLDER));
		}

		@Override
		protected Class<CharacterDesign> getDesignType() {return CharacterDesign.class;}
	}
	
	public static class ImprovementSupplier extends CardSupplier
	{
		protected ImprovementSupplier()
		{
			super(ImprovementCard.SIMPLE_NAME);
		}
		
		@Override
		public ImprovementCard get(File designFile)
		{
			return new ImprovementCard(designFile, vectorFolder, vectorTemplateFile, rasterFolder, autoArtFolder, new File(TEMPLATE_ART_FOLDER));
		}

		@Override
		protected Class<ImprovementDesign> getDesignType() {return ImprovementDesign.class;}
	}
	
	//==========CONSTRUCTOR==========//
	
	public final File designFolder;
	public final File vectorFolder;
	public final File rasterFolder;
	public final File autoArtFolder;
	public final File vectorTemplateFile;
	public final File designSchemaFile;
	
	protected CardSupplier
	(
		String designFolderPath,
		String vectorFolderPath,
		String rasterFolderPath,
		String autoArtFolderPath,
		String schemaPath,
		String templatePath
	)
	{
		designFolder = new File(designFolderPath);
		vectorFolder = new File(vectorFolderPath);
		rasterFolder = new File(rasterFolderPath);
		autoArtFolder = new File(autoArtFolderPath);
		this.vectorTemplateFile = new File(templatePath);
		this.designSchemaFile = new File(schemaPath);
	}
	
	protected CardSupplier(String simpleTypeName)
	{
		this
		(
			DESIGN_BASE_FOLDER + "/" + simpleTypeName,
			VISUAL_BASE_FOLDER + "/" + VECTOR_REL_FOLDER + "/" + simpleTypeName,
			VISUAL_BASE_FOLDER + "/" + RASTER_REL_FOLDER + "/" + simpleTypeName,
			VISUAL_BASE_FOLDER + "/" + ART_REL_FOLDER + "/" + simpleTypeName,
			VISUAL_BASE_FOLDER + "/" + VECTOR_REL_FOLDER + "/" + TEMPLATE_REL_FOLDER + "/" + simpleTypeName + TEMPLATE_VECTOR_EXT,
			DESIGN_BASE_FOLDER + "/" + SCHEMA_REL_FOLDER + "/" + simpleTypeName + SCHEMA_EXT
		);
	}
	
	//==========PUBLIC INTERFACE==========//
	
	/**
	 * @param designFile - The file that contains a card design
	 * @return - The Card object for that file
	 */
	public abstract Card get(File designFile);

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
	
	/**
	 * Update the schema for this supplier's type and write it to disk
	 * @return - A file pointing to the schema
	 */
	public void generateSchema()
	{
		print("Generating the Charter Schema for " + getDesignType().getSimpleName());
		HyperSchemaFactoryWrapper visitor = new HyperSchemaFactoryWrapper();
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writerWithDefaultPrettyPrinter();
		try 
		{
			mapper.acceptJsonFormatVisitor(getDesignType(), visitor);
		} 
		catch (JsonMappingException e) 
		{	
			e.printStackTrace();
		}
		JsonSchema improvementSchema = visitor.finalSchema();
		try 
		{
			print("Working Directory: " + new File(".").getAbsolutePath());
			print("Schema Directory: " + designSchemaFile.getAbsolutePath());
			String schemaString = mapper.writeValueAsString(improvementSchema);
			FileWriter outputFile = new FileWriter(designSchemaFile);
			outputFile.write(schemaString);
			print("...\n" + schemaString);
			outputFile.close();
		} 
		catch (JsonProcessingException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	//==========PRIVATE HELPERS==========//
	
	/** Fetch the design type that this supplier manages. Used to generate schema */
	protected abstract Class<? extends CardDesign> getDesignType();
	
	protected static void print(String s)
	{
		System.out.println("[CardSupplier]: " + s);
	}
}
