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
	public CardDesign()
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
	
	public CardDesign
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
    @JsonPropertyDescription("Printed card name. Defaults to empty. | In template, mark with id=" + SVGEdits.NAME_ID)
    public final String name;
    
    @JsonProperty(required = false)
    @JsonPropertyDescription("The printed types of the card. Defaults to empty. | In template, mark with id=" + SVGEdits.TYPES_ID)
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
    @JsonPropertyDescription("The name of the art for this card. Do not include the file extension: it must be .png. It will be searched for in the relevant project folder(s). Defaults to searching for \"card-name-art.png\" in the proper art folder, but if this fails, it will keep the template art. | In template, mark with id=" + SVGEdits.ART_ID)
    public final String art;

    @JsonProperty(required = false)
    @JsonPropertyDescription("The delta-X value of the art positioning on this card. Defaults to default to 0 (no displacement.)")
    public final int artX;

    @JsonProperty(required = false)
    @JsonPropertyDescription("The delta-Y value of the art positioning on this card. Defaults to default to 0 (no displacement.)")
    public final int artY;
}
