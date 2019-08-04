package charters.card.design.card;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.ObjectMapper;

import charters.card.design.design.CardDesign;
import charters.card.design.group.CardGroup;
import charters.card.visual.SVGEdits;

/**
 * Active representation of a card
 * Should be agnostic to project structure
 */
public abstract class Card
{
	//==========CONSTANTS==========//
	
	public static final String VECTOR_EXT = ".svg";
	public static final String RASTER_EXT = ".png";
	public static final String ART_EXT = ".png";

	public static final int RASTER_DPI = 200;
	
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
	
	//Template properties
	/** Number of fields the template has for types present. Excess will be blanked */
	public static final int NUM_TEMPLATE_TYPES = 3;
	
	//==========PRIVATE FIELDS==========//
	
	//private final CardSupplier supplier;
	
	/** The file that contains all the design data */
	protected final File designFile;
	
	/** The file that contains all the vector data */
	protected final File vectorFile;
	
	/** The file that acts as the vector template */
	protected final File vectorTemplateFile;
	
	/** The file that contains all the raster data */
	protected final File rasterFile;

	/** Image file for the artwork to be used on this card */
	protected final File artFile;
	
	/** All the data directly in-read from the .json design file */
	private final CardDesign design;
	
	//==========CONSTRUCTORS==========//
	
	public Card
	(
		File designFile,
		File vectorFolder,
		File vectorTemplateFile,
		File rasterFolder,
		File autoArtFolder,
		File templateArt
	)
	{
		this.designFile = designFile;
		this.design = this.readDesign();
		this.artFile = this.findArt(autoArtFolder, templateArt);
		print("Art has been assigned as " + this.artFile.getAbsolutePath());
		this.vectorFile = new File(vectorFolder.getPath() + "/" + this.getReducedName() + VECTOR_EXT);
		this.vectorTemplateFile = vectorTemplateFile;
		this.rasterFile = new File(rasterFolder.getPath() + "/" + this.getReducedName() + RASTER_EXT);
	}
	
	//==========PUBLIC INTERFACE==========//
	
	/** @return - A design type name like "characters" */
	public abstract String getDesignTypeName();
	
	/** @param group - The group to add this card to. Polymorphism allows for it to figure out how to sort itself */
	public abstract void addToGroup(CardGroup group);
	
	/**
	 * @return - The name of this card, edited like so: "The Moving Castle" -> "the-moving-castle"
	 */
	public String getReducedName() 
	{
		if (getDesign().name != null)
		{
			return getDesign().name.toLowerCase().replace(' ', '-').replace("\'", "");
		}
		else
		{
			return null;
		}
	}
	
    /**
     * @return - The types of this design, concatenated into a unified string
     */
    public String getCombinedTypes() 
    {
    	String[] ts = getDesign().types;
    	if (ts.length == 0) {return "";}
    	if (ts.length == 1) {return ts[0];}
    	else
    	{
    		//All this to avoid extra spaces
    		String returnVal = "";
    		for (int i = 0; i < ts.length - 1; i++)
    		{returnVal = returnVal + ts[0] + " ";}
    		return returnVal + ts[ts.length - 1];
    	}
    }
	
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
		
		try
		{
			//Create the new SVG document as a copy of the template, then store that doc
			Document newDoc = getDocument(vectorTemplateFile);
			
			//Get all the elements out of the new SVG document
			LinkedList<Element> elements = getElements(newDoc);
			
			//Pass the elements through to be edited
			edits.doEdits(elements);
			
			//Save the document by overwriting the newly created copy
			DOMSource docSource = new DOMSource(newDoc);
			StreamResult result = new StreamResult(vectorFile);
			TransformerFactory.newInstance().newTransformer().transform(docSource, result);

			print("Saving " + getDesign().name + " to " + vectorFile.getAbsolutePath());
		} 
		catch (IOException | TransformerException | TransformerFactoryConfigurationError | SAXException | ParserConfigurationException e) {e.printStackTrace();}
	
		
		//Save the template
		return getVectorFile();
	}
	
	//==========PRIVATE HELPERS==========//

	protected abstract Class<? extends CardDesign> getDesignType();
	
	/**
	 * @return - The design object for this card. Subclasses must override this with a cast to their type of design
	 */
	public CardDesign getDesign()
	{
		return this.design;
	}
	
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
		try {currentDesign = deserializer.readValue(jsonContents, getDesignType());} 
		catch (IOException e)
		{
			e.printStackTrace();
			currentDesign = null;
		}
		print("\tFound card design: " + currentDesign.name + " (" + currentDesign.getClass().getCanonicalName() + ")");
		return currentDesign;
	}
	
	protected File findArt(File autoArtFolder, File templateArt)
	{
		//print("Template Art File Candidate: " + templateArt.getAbsolutePath());
		if (getDesign().art == null) //No art specified: look for auto art and template art
		{
			File autoArtFile = new File(autoArtFolder.getAbsolutePath() + "/" + getReducedName() + ART_EXT);
			//print("Auto Art File Candidate: " + autoArtFile.getAbsolutePath());
			if (autoArtFile.exists()) //If the auto art exists
			{
				return autoArtFile;
			}
			else
			{
				return templateArt; //Return the template art
			}
		}
		else
		{
			return new File(getDesign().art);
		}
	}
	
	protected SVGEdits getEdits()
	{
		SVGEdits edits = new SVGEdits();
		
		//Create name edit
		edits.addTextEdit(new SVGEdits.TextEdit("id", NAME_ID, design.name));
		
		//Create type edit
		edits.addTextEdit(new SVGEdits.TextEdit("id", TYPES_ID, getCombinedTypes()));

		//Add multi-element-types edits
		for (int c = 0; c < design.types.length; c++)
		{
			edits.addTextEdit(new SVGEdits.TextEdit("id", TYPE_ID + c, design.types[c]));
		}

		//Blank any excess type text fields
		for (int g = design.types.length - 1; g < NUM_TEMPLATE_TYPES; g++)
		{
			edits.addTextEdit(new SVGEdits.TextEdit("id", TYPE_ID + g, ""));
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
		edits.addAttributeEdit
		(
			new SVGEdits.AttributeEdit
			(
				"id",
				ART_ID,
				ART_ATTRIBUTE_NAME, 
				this.artFile.getAbsolutePath() //TODO: Switch this to a relative path
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
	
	/**
	 * Used during the visualization process to fetch the elements from a document
	 * @param doc - Doc to deconstruct
	 * @return - List of elements
	 */
	public static LinkedList<Element> getElements(Document doc)
	{
		LinkedList<Node> nodes = deconstructDocument(doc);
		LinkedList<Element> elements = new LinkedList<Element>();
		for (Node n : nodes)
		{
			if (n.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (org.w3c.dom.Element)n;
				elements.add(element);
			}
		}
		return elements;
	}
	
	/**
	 * @return - The document that corresponds to that file
	 */
	public static Document getDocument(File docFile) throws SAXException, IOException, ParserConfigurationException
	{
		return DocumentBuilderFactory.newInstance()
				   .newDocumentBuilder()
				   .parse(new File(docFile.getAbsolutePath()));
	}
	
	/**
	 * Reduces the passed document to a series of un-nested nodes
	 * @return - a collection of un-ordered and un-nested nodes
	 */
	public static LinkedList<Node> deconstructDocument(Document doc)
	{
		return recursiveDeconstructDocument(doc.getDocumentElement());
	}

	/**
	 * CALL THIS FUNCTION VIA DECONSTRUCTDOCUMENT()
	 * Recursively return a list of all nodes below the passed INCLUDING the passed
	 * @param top - the top-level node to begin at. This node will be in the list
	 * @return - A list of all node under or in "top"
	 */
	private static LinkedList<Node> recursiveDeconstructDocument(Node top)
	{
		LinkedList<Node> returnList = new LinkedList<Node>();
		if (top != null)
		{
			returnList.add(top);
		}
		if (top.hasChildNodes())
		{
			NodeList childs = top.getChildNodes();
			for (int i = 0; i < childs.getLength(); i++)
			{
				returnList.addAll(recursiveDeconstructDocument(childs.item(i)));
			}
		}
		return returnList;
	}
	
	protected static void print(String s)
	{
		System.out.println("[Card]: " + s);
	}
}
