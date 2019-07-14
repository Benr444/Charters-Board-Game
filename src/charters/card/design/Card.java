package charters.card.design;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.customProperties.HyperSchemaFactoryWrapper;

import charters.card.visual.script.SVGEdits;
import javafx.util.Pair;

/**
 * Factory that reads design files and handles static information regarding CardDesign that needs to be overridden
 * in subclasses
 */
public abstract class Card
{
	public static final String DESIGN_FOLDER_PATH = "resource/design/";
	public static final String SCHEMA_FOLDER_PATH = DESIGN_FOLDER_PATH + "schema/";
	public static final String SCHEMA_EXTENSION = ".schema.json";
	public static final String DESIGN_EXTENSION = ".json";
	
	/** Folder where static background art images can be found. Relative path from SVG file */
	public static final String ART_FOLDER = "../../art/";
	
	//id-values. These are the ID's searched for for various design variables
	public static final String NAME_ID = "name";
	public static final String TYPES_ID = "types";
	public static final String ART_ID = "art";
	
	//Attribute names. These are the names of attributes edited by certain attributeEdits
	public static final String ART_ATTRIBUTE_NAME = "xlink:href";
	public static final String ART_X_ATTRIBUTE_NAME = "x";
	public static final String ART_Y_ATTRIBUTE_NAME = "y";
	
    /**
     * Class containing all data that describes a single card's designs
     */
    abstract static public class Design 
    {
    	//===DEFAULT-VALUES===//
    	
    	public static final String DEFAULT_NAME = "ERR_NAME_NOT_FOUND";
    	public static final String[] DEFAULT_TYPES = {};
    	public static final String DEFAULT_FLAVOR_TEXT = null;
    	public static final String DEFAULT_SET = "BASE";
    	public static final Rarity DEFAULT_RARITY = Rarity.UNSET;
    	public static final Color DEFAULT_COLOR = Color.UNSET;
    	public static final int DEFAULT_ART_X = 0;
    	public static final int DEFAULT_ART_Y = 0;
    	
    	public enum Color
        {
        	RED, ORANGE, YELLOW, GREEN, BLUE, VIOLET, BLACK, WHITE, COLORLESS, UNSET
        }
        
        public enum Rarity
        {
        	COMMON, UNCOMMON, RARE, UNSET
        }
        
        //===UTILITY-MEMBERS===//
    
        /**
         * Place default values in this constructor
         */
    	protected Design()
    	{
    		this.name = DEFAULT_NAME;
    		this.types = DEFAULT_TYPES;
    		this.flavorText = DEFAULT_FLAVOR_TEXT;
    		this.set = DEFAULT_SET;
    		this.rarity = DEFAULT_RARITY;
    		this.color = DEFAULT_COLOR;
    		this.art = this.getReducedName();
    		this.artX = DEFAULT_ART_X;
    		this.artY = DEFAULT_ART_Y;
    	}
    	
    	protected Design
    	(
    		String name,
    		String[] types,
    		String flavorText,
    		String set,
    		Rarity rarity,
    		Color color,
    		String art,
    		int artX,
    		int artY
    	)
    	{
    		this.name = name;
    		this.types = types;
    		this.flavorText = flavorText;
    		this.set = set;
    		this.rarity = rarity;
    		this.color = color;
    		this.art = art;
    		this.artX = artX;
    		this.artY = artY;
    	}
    	
    	@JsonIgnore 
    	public abstract Card getFactory();
    	
    	public String getDesignTypeName() {return getFactory().getDesignTypeName();}
    	
    	/**
    	 * @return - The name of this card, edited like so: "The Moving Castle" -> "the-moving-castle"
    	 */
    	@JsonIgnore
    	public String getReducedName() {return this.name.toLowerCase().replace(' ', '-');}
    	
        @JsonIgnore
        public String getCombinedTypes() 
        {
        	if (types.length == 0) {return "";}
        	if (types.length == 1) {return types[0];}
        	else
        	{
        		//All this to avoid extra spaces
        		String returnVal = "";
        		for (int i = 0; i < types.length - 1; i++)
        		{returnVal = returnVal + types[0] + " ";}
        		return returnVal + types[types.length - 1];
        	}
        }
        
        //===CARD-PROPERTIES===//
        
        @JsonProperty(required = true)
        @JsonPropertyDescription("Printed card name. Defaults to empty. | In template, mark with id=" + NAME_ID)
        public final String name;
        
        @JsonProperty(required = false)
        @JsonPropertyDescription("The printed types of the card. Defaults to empty. | In template, mark with id=" + TYPES_ID)
        public final String[] types;
        
        @JsonProperty(required = false)
        @JsonPropertyDescription("Italicized, bonus story text on a card. Defaults to blank.")
        public final String flavorText;
        
        @JsonProperty(required = false)
        @JsonPropertyDescription("Used to identify the set of a card, which is the batch of designs it belongs to. Defaults to BASE")
        public final String set;
    
        @JsonProperty(required = true)
        @JsonPropertyDescription("Used to identify the set of a card, which is the batch of designs it belongs to.")
        public final Rarity rarity;
    
        @JsonProperty(required = true)
        @JsonPropertyDescription("The color of the card, mechanically. This will be reflected in the frame, etc. Should match any card costs.")
        public final Color color;
    
        @JsonProperty(required = false)
        @JsonPropertyDescription("The name of the art for this card. Do not include the file extension: it must be .png. It will be searched for in the relevant project folder(s). Defaults to searching for \"card-name-art.png\" in the proper art folder, but if this fails, it will keep the template art. | In template, mark with id=" + ART_ID)
        public final String art;
    
        @JsonProperty(required = false)
        @JsonPropertyDescription("The delta-X value of the art positioning on this card. Defaults to default to 0 (no displacement.)")
        public final int artX;
    
        @JsonProperty(required = false)
        @JsonPropertyDescription("The delta-Y value of the art positioning on this card. Defaults to default to 0 (no displacement.)")
        public final int artY;
    }
	
	/**
	 * @return - The name of this design type
	 */
	@JsonIgnore
	public abstract String getDesignTypeName();
	
	/**
	 * @return - The path to the file where this object's respective json schema file should be stored
	 */
	public String getSchemaFilePath() {return SCHEMA_FOLDER_PATH + getDesignTypeName().toLowerCase() + SCHEMA_EXTENSION;}
	
	/**
	 * @return - The path to the file where this object's respective json file should be stored
	 */
	public String getDesignFolderPath() {return DESIGN_FOLDER_PATH + getDesignTypeName().toLowerCase() + "/";}
	
	/**
	 * @return - The design type this factory encapsulates
	 */
	public abstract Class<? extends Design> getDesignType();
	
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
			String schemaString = mapper.writeValueAsString(improvementSchema);
			FileWriter outputFile = new FileWriter(new File(getSchemaFilePath()));
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
	/**
	 * Used to read json into POJOs
	 * @return - FACTORY-SPECIFIC-TYPE list of found POJOs: e.g. ItemDesignFactory will return item designs
	 */
	public LinkedList<? extends Design> readDesigns()
	{
		LinkedList<Design> returnValues = new LinkedList<Design>();
		File designsFolder = new File(getDesignFolderPath());
		File[] designFiles = designsFolder.listFiles();
		if (designFiles != null)
		{
			for (File f : designFiles)
			{
				if (f.isFile())
				{
					print("Found candidate design file: " + f.getName());
					try
					{
						String jsonContents = new String(Files.readAllBytes(f.toPath()));
						ObjectMapper deserializer = new ObjectMapper();
						Design currentDesign = deserializer.readValue(jsonContents, getDesignType());
						print("\tFound card design: " + currentDesign.name + " (" + getDesignTypeName() + ")");
						returnValues.add(currentDesign);
					} 
					catch (IOException e) {e.printStackTrace();}
				}
			}
		}
		return returnValues;
	}
	
	/**
	 * 
	 * @param design - Design to get edits for
	 * @return - Edits for that design
	 */
	public SVGEdits getEdits(Design design)
	{
		SVGEdits edits = new SVGEdits();

		//Create name edit
		edits.addTextEdit(new Pair<String, String>(NAME_ID, design.name));
		
		//Create type edit
		edits.addTextEdit(new Pair<String, String>(TYPES_ID, design.getCombinedTypes()));
		
		//Create art edit
		edits.addAttributeEdit
		(
			new Pair<String, Pair<String, String>>
			(
				ART_ID, 
				new Pair<String, String>
				(
					ART_ATTRIBUTE_NAME, 
					ART_FOLDER + design.getDesignTypeName() + "/" + design.art + ".png"
				)
			)
		);
		
		//Create art position edit
		edits.addAttributeEdit
		(
			new Pair<String, Pair<String, String>>
			(ART_ID, new Pair<String, String>(ART_X_ATTRIBUTE_NAME, "" + design.artX))
		);
		edits.addAttributeEdit
		(
			new Pair<String, Pair<String, String>>
			(ART_ID, new Pair<String, String>(ART_Y_ATTRIBUTE_NAME, "" + design.artY))
		);
		
		return edits;
	}
	
	public static void print(String s)
	{
		System.out.println("[Design Factory]: " + s);
	}
}