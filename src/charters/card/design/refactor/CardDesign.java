package charters.card.design.refactor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import charters.card.design.card.Card;

/**
 * POJO output from jackson goes purely into this class
 * Purely data and defaults, no interpretation
 */
abstract public class CardDesign 
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
	protected CardDesign()
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
	
    //===CARD-PROPERTIES===//
    
    @JsonProperty(required = true)
    @JsonPropertyDescription("Printed card name. Defaults to empty. | In template, mark with id=" + Card.NAME_ID)
    public final String name;
    
    @JsonProperty(required = false)
    @JsonPropertyDescription("The printed types of the card. Defaults to empty. | In template, mark with id=" + Card.TYPES_ID + ", or mark each type with " + Card.TYPE_ID + "0, " + Card.TYPE_ID + "1, etc.")
    public final String[] types;
    
    @JsonProperty(required = false)
    @JsonPropertyDescription("Italicized, bonus story text on a card. Defaults to blank.")
    public final String flavorText;
    
    @JsonProperty(required = false)
    @JsonPropertyDescription("Used to identify the set of a card, which is the batch of designs it belongs to. Defaults to BASE")
    public final String set;

    @JsonProperty(required = true)
    @JsonPropertyDescription("Used to identify the set of a card, which is the batch of designs it belongs to. | In template, set " + Card.RARITY_COLOR_ATTRIBUTE + "=0, =1, etc. The main color for the cards rarity will go in 0, the secondary in 1, etc.")
    public final Rarity rarity;

    @JsonProperty(required = true)
    @JsonPropertyDescription("The color of the card, mechanically. This will be reflected in the frame, etc. Should match any card costs. | In template, mark with " + Card.COLOR_ATTRIBUTE + "=0 or =1, etc. The primary color code will fill in 0, the secondary color code will fill 1, etc.")
    public final Color color;

    @JsonProperty(required = false)
    @JsonPropertyDescription("The name of the art for this card. Do not include the file extension: it must be .png. It will be searched for in the relevant project folder(s). Defaults to searching for \"card-name-art.png\" in the proper art folder, but if this fails, it will keep the template art. | In template, mark with id=" + Card.ART_ID)
    public final String art;

    @JsonProperty(required = false)
    @JsonPropertyDescription("The delta-X value of the art positioning on this card. Defaults to default to 0 (no displacement.)")
    public final int artX;

    @JsonProperty(required = false)
    @JsonPropertyDescription("The delta-Y value of the art positioning on this card. Defaults to default to 0 (no displacement.)")
    public final int artY;
}