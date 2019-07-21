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

import charters.card.visual.SVGEdits;
import charters.card.visual.SVGEdits.AttributeEdit;
import javafx.util.Pair;

/**
 * Factory that reads design files and handles static information regarding CardDesign that needs to be overridden
 * in subclasses
 */
public abstract class Card
{
	public static final String VISUAL_FOLDER_PATH = "resource/visual/";
	public static final String CARD_FOLDER_PATH = VISUAL_FOLDER_PATH + "card/";
	public static final String RASTER_FOLDER_PATH = VISUAL_FOLDER_PATH + "raster/";
	public static final String DESIGN_FOLDER_PATH = "resource/design/";
	public static final String SCHEMA_FOLDER_PATH = DESIGN_FOLDER_PATH + "schema/";
	public static final String SCHEMA_EXTENSION = ".schema.json";
	public static final String DESIGN_EXTENSION = ".json";
	public static final String ART_EXTENSION = ".png";
	
	/** Folder where static background art images can be found. Relative path from SVG file */
	public static final String SVG_RELATIVE_ART_FOLDER = "../../art/";
	public static final String ART_FOLDER = VISUAL_FOLDER_PATH + "art/";
	public static final String TEMPLATE_ART = SVG_RELATIVE_ART_FOLDER + "template/template-art.png";
	
	//id/class-values. These are the ID/Classes's searched for for various design variables
	public static final String NAME_ID = "name";
	public static final String TYPES_ID = "types";
	public static final String TYPE_ID = "type-";
	public static final String ART_ID = "art";
	public static final String COLOR_ATTRIBUTE = "charters-color";
	public static final String RARITY_COLOR_ATTRIBUTE = "charters-rarity-color";
	
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
    	
    	public static final String DEFAULT_NAME = null;
    	public static final String[] DEFAULT_TYPES = {"", "", ""};
    	public static final String DEFAULT_FLAVOR_TEXT = null;
    	public static final String DEFAULT_SET = "BASE";
    	public static final Rarity DEFAULT_RARITY = Rarity.UNSET;
    	public static final Color DEFAULT_COLOR = Color.UNSET;
    	public static final int DEFAULT_ART_X = 0;
    	public static final int DEFAULT_ART_Y = 0;
        
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
    		this.art = null;
    		this.artX = DEFAULT_ART_X;
    		this.artY = DEFAULT_ART_Y;
    	}
    	
    	/*
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
    		print("Constructing design: art:" + art);
    		if (art == null)
    		{
    			this.art = getReducedName() + DEFAULT_ART_EXTENSION;
    		}
    		else
    		{
        		this.art = art;
    		}
    		this.artX = artX;
    		this.artY = artY;
    	}*/
    	
    	@JsonIgnore 
    	public abstract Card getFactory();
    	
    	public String getDesignTypeName() {return getFactory().getDesignTypeName();}
    	
    	/**
    	 * @return - The name of this card, edited like so: "The Moving Castle" -> "the-moving-castle"
    	 */
    	@JsonIgnore
    	public String getReducedName() 
    	{
    		if (name != null)
    		{
    			return this.name.toLowerCase().replace(' ', '-').replace("\'", "");
    		}
    		else
    		{
    			return null;
    		}
    	}
    	
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
        @JsonPropertyDescription("The printed types of the card. Defaults to empty. | In template, mark with id=" + TYPES_ID + ", or mark each type with " + TYPE_ID + "0, " + TYPE_ID + "1, etc.")
        public final String[] types;
        
        @JsonProperty(required = false)
        @JsonPropertyDescription("Italicized, bonus story text on a card. Defaults to blank.")
        public final String flavorText;
        
        @JsonProperty(required = false)
        @JsonPropertyDescription("Used to identify the set of a card, which is the batch of designs it belongs to. Defaults to BASE")
        public final String set;
    
        @JsonProperty(required = true)
        @JsonPropertyDescription("Used to identify the set of a card, which is the batch of designs it belongs to. | In template, set " + RARITY_COLOR_ATTRIBUTE + "=0, =1, etc. The main color for the cards rarity will go in 0, the secondary in 1, etc.")
        public final Rarity rarity;
    
        @JsonProperty(required = true)
        @JsonPropertyDescription("The color of the card, mechanically. This will be reflected in the frame, etc. Should match any card costs. | In template, mark with " + COLOR_ATTRIBUTE + "=0 or =1, etc. The primary color code will fill in 0, the secondary color code will fill 1, etc.")
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
	public abstract String getDesignTypeName();
	
	/**
	 * @return - The path to the file where this object's respective json schema file should be stored
	 */
	public String getSchemaFilePath() {return SCHEMA_FOLDER_PATH + getDesignTypeName().toLowerCase() + SCHEMA_EXTENSION;}
	
	/**
	 * @return - The path to the folder where this object's respective json file should be stored
	 */
	public String getDesignFolderPath() {return DESIGN_FOLDER_PATH + getDesignTypeName().toLowerCase() + "/";}

	/**
	 * @return - The path to the folder where this object's respective svg file should be stored
	 */
	public String getCardFolderPath() {return CARD_FOLDER_PATH + getDesignTypeName().toLowerCase() + "/";}

	/**
	 * @return - The path to the folder where this object's respective raster file should be stored
	 */
	public String getRasterFolderPath() {return RASTER_FOLDER_PATH + getDesignTypeName().toLowerCase() + "/";}

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
		edits.addTextEdit(new SVGEdits.TextEdit("id", NAME_ID, design.name));
		
		//Create type edit
		edits.addTextEdit(new SVGEdits.TextEdit("id", TYPES_ID, design.getCombinedTypes()));

		
		//Add multi-element-types edits
		for (int c = 0; c < design.types.length; c++)
		{
			edits.addTextEdit(new SVGEdits.TextEdit("id", TYPE_ID + c, design.types[c]));
		}
		
		//For each of this card's defined color codes
		for (int d = 0; d < design.color.colorCodes.length; d++)
		{
			//Add a color edit
			edits.addAttributeEdit(new SVGEdits.AttributeEdit(COLOR_ATTRIBUTE, "" + d, "fill", design.color.getFillValue(d)));
		}
		
		//For each of this card's defined rarity color codes
		for (int z = 0; z < design.rarity.colorCodes.length; z++)
		{
			//Add a rarity color edit
			edits.addAttributeEdit(new SVGEdits.AttributeEdit(RARITY_COLOR_ATTRIBUTE, "" + z, "fill", design.rarity.getFillValue(z)));
		}
		
		//Create art edit
		String artString;
		if (design.art == null)
		{
			String nameDefaultArt = SVG_RELATIVE_ART_FOLDER + design.getDesignTypeName() + "/" + design.getReducedName() + ART_EXTENSION;
			String nameDefaultArtAbsolute = ART_FOLDER + design.getDesignTypeName() + "/" + design.getReducedName() + ART_EXTENSION;
			if (new File(nameDefaultArtAbsolute).exists())
			{
				print("\tName-default art exists.");
				artString = nameDefaultArt;
			}
			else
			{
				print("\tName-default art does not exist. Defaulting to template art.");
				artString = TEMPLATE_ART;
			}
		}
		else
		{
			artString = design.art;
		}
		edits.addAttributeEdit
		(
			new SVGEdits.AttributeEdit
			(
				"id",
				ART_ID,
				ART_ATTRIBUTE_NAME, 
				artString
			)
		);
		
		//Create art position edit
		edits.addAttributeEdit
		(
			new SVGEdits.AttributeEdit("id", ART_ID, ART_X_ATTRIBUTE_NAME, "" + design.artX)
		);
		edits.addAttributeEdit
		(
			new SVGEdits.AttributeEdit("id", ART_ID, ART_Y_ATTRIBUTE_NAME, "" + design.artY)
		);
		
		return edits;
	}
	
	public static void print(String s)
	{
		System.out.println("[Design Factory]: " + s);
	}
}