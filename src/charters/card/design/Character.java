package charters.card.design;

import java.util.LinkedList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import charters.card.visual.SVGEdits;
import javafx.util.Pair;

public class Character extends Playable
{
	//id-values. These are the ID's searched for for various design variables
	public static final String AK_ID = "ak";
	public static final String MS_ID = "ms";
	public static final String RG_ID = "rg";
	
    /**
     * All data needed to describe a character card
     */
    public static class Design extends Playable.Design
    {
    	//===DEFAULT-VALUES===//
    	public static final int DEFAULT_AK = -1;
    	public static final int DEFAULT_MS = -1;
    	public static final int DEFAULT_RG = -1;
    	
    	public Design()
    	{
    		this.AK = DEFAULT_AK;
    		this.MS = DEFAULT_MS;
    		this.RG = DEFAULT_RG;
    	}
    
        @JsonProperty(required = true)
    	@JsonPropertyDescription("The printed AK of the card. Defaults to -1. | On template, mark with id=" + AK_ID)
    	public int AK;
    
        @JsonProperty(required = true)
    	@JsonPropertyDescription("The printed MS of the card. Defaults to -1. | On template, mark with id=" + AK_ID)
    	public int MS;
    
        @JsonProperty(required = true)
    	@JsonPropertyDescription("The printed RG of the card. Defaults to -1. | On template, mark with id=" + AK_ID)
    	public int RG;

		@Override
		public Card getFactory() {return new Character();}
    }
    
	@Override
	public String getDesignTypeName() {return "character";}

	@Override
	public Class<Character.Design> getDesignType() {return Character.Design.class;}
	
	@Override public LinkedList<Character.Design> readDesigns()
	{
		return (LinkedList<Character.Design>)super.readDesigns();
	}

    @Override
    public SVGEdits getEdits(Card.Design design)
	{
    	SVGEdits edits = super.getEdits(design);

    	if (getDesignType().isAssignableFrom(design.getClass()))
    	{
			Character.Design characterDesign = (Character.Design)design;
			
			//Create AK edit
			edits.addTextEdit
			(
				new SVGEdits.TextEdit("id", AK_ID, characterDesign.AK + "AK")
			);

			//Create MS edit
			edits.addTextEdit
			(
				new SVGEdits.TextEdit("id", MS_ID, characterDesign.MS + "MS")
			);

			//Create RG edit
			edits.addTextEdit
			(
				new SVGEdits.TextEdit("id", RG_ID, characterDesign.RG + "RG")
			);
    	}
    	
    	return edits;
	}
}