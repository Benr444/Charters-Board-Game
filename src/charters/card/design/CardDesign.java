package charters.card.design;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import charters.card.visual.data.SVGEdits;

/**
 * Class containing all data that describes a single card's designs
 */
abstract public class CardDesign 
{
	@JsonIgnore
	public static final String DESIGN_FOLDER_PATH = "resource/design/";
	
	@JsonIgnore
	public static final String SCHEMA_FOLDER_PATH = DESIGN_FOLDER_PATH + "schema/";
	
	@JsonIgnore
	public static final String SCHEMA_EXTENSION = ".schema.json";
	
	@JsonIgnore
	public static final String DESIGN_EXTENSION = ".json";

	/**
	 * @return - The path to the file where this object's respective json schema file should be stored
	 */
	@JsonIgnore
	public String getSchemaFilePath() {return SCHEMA_FOLDER_PATH + getDesignTypeName().toLowerCase() + SCHEMA_EXTENSION;}
	
	/**
	 * @return - The path to the file where this object's respective json file should be stored
	 */
	@JsonIgnore
	public String getDesignFolderPath() {return DESIGN_FOLDER_PATH + getDesignTypeName().toLowerCase() + "/";}
	
	/**
	 * @return - The name of this card, edited like so: "The Moving Castle" -> "the-moving-castle"
	 */
	@JsonIgnore
	public String getReducedName() {return this.name.toLowerCase().replace(' ', '-');}
	
	/**
	 * @return - The correlating SVG edits from this design file
	 */
	@JsonIgnore
	public abstract SVGEdits getEdits();
	
	/**
	 * @return - The name of this design type
	 */
	@JsonIgnore
	public abstract String getDesignTypeName();
	
    public enum Color
    {
    	RED, ORANGE, YELLOW, GREEN, BLUE, VIOLET, BLACK, WHITE, COLORLESS, UNSET
    }
    
    public enum Rarity
    {
    	COMMON, UNCOMMON, RARE, UNSET
    }
    
    //===CARD-PROPERTIES===//
    
    @JsonProperty(required = true)
    @JsonPropertyDescription("Printed card name. Defaults to empty. | In template, mark with id=" + SVGEdits.NAME_ID)
    public String name = "";
    
    @JsonProperty(required = false)
    @JsonPropertyDescription("The printed types of the card. Defaults to empty. | In template, mark with id=" + SVGEdits.TYPES_ID)
    public String[] types = {};
    
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
    
    @JsonProperty(required = false)
    @JsonPropertyDescription("Italicized, bonus story text on a card. Defaults to blank.")
    public String flavorText = "";
    
    @JsonProperty(required = false)
    @JsonPropertyDescription("Used to identify the set of a card, which is the batch of designs it belongs to. Defaults to BASE")
    public String set = "BASE";

    @JsonProperty(required = true)
    @JsonPropertyDescription("Used to identify the set of a card, which is the batch of designs it belongs to.")
    public Rarity rarity = Rarity.UNSET;

    @JsonProperty(required = true)
    @JsonPropertyDescription("The color of the card, mechanically. This will be reflected in the frame, etc. Should match any card costs.")
    public Color color = Color.UNSET;

    @JsonProperty(required = false)
    @JsonPropertyDescription("The name of the art for this card. Do not include the file extension: it must be .png. It will be searched for in the relevant project folder(s). Defaults to searching for \"card-name-art.png\" in the proper art folder, but if this fails, it will keep the template art. | In template, mark with id=" + SVGEdits.ART_ID)
    public String art = null;

    @JsonProperty(required = false)
    @JsonPropertyDescription("The delta-X value of the art positioning on this card. Defaults to default to 0 (no displacement.)")
    public int artX = 0;

    @JsonProperty(required = false)
    @JsonPropertyDescription("The delta-Y value of the art positioning on this card. Defaults to default to 0 (no displacement.)")
    public int artY = 0;
}
