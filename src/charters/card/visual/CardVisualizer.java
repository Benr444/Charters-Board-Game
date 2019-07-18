package charters.card.visual;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import charters.card.design.Card;
import charters.card.design.Character;
import charters.card.design.Improvement;
import charters.card.design.Item;

public abstract class CardVisualizer
{
	//TODO move these constants to the proper factory, etc.
	
	public static final String VISUALS_FOLDER = "resource/visual/";
	
	public static final String ART_VISUALS_FOLDER = VISUALS_FOLDER + "art/";
	public static final String CARD_VISUALS_FOLDER = VISUALS_FOLDER + "card/";
	public static final String CARD_VISUALS_TEMPLATE_FOLDER = CARD_VISUALS_FOLDER + "template/";
	public static final String TEMPLATE_EXTENSION = "-template.svg";
	
	public static void main(String... args)
	{	
		try
		{
			doVisualize(new Improvement().readDesigns());
			doVisualize(new Character().readDesigns());
			doVisualize(new Item().readDesigns());
			print("Finished visualization process.");
		} 
		catch (InstantiationException | IllegalAccessException e) {e.printStackTrace();}
	}
	
	/**
	 * Draw in as many design files as possible generate a svg for each of them
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void doVisualize(LinkedList<? extends Card.Design> cardDesigns) throws InstantiationException, IllegalAccessException
	{
		print("=== BEGINNING CARD VISUALIZATION PROCESS... ===");
		//Read the card design files into a java object
		
		//Iterate over each card design
		for (Card.Design cardDesign : cardDesigns)
		{
			try
			{
				//Create the new SVG document as a copy of the template, then store that doc
				Document newDoc = getDocument(getTemplatePath(cardDesign));
				
				//Get all the elements out of the new SVG document
				LinkedList<Element> elements = getElements(newDoc);
				
				//Turn the design doc into a list of edits to be done on the SVG document
				print("CardDesign factory is " + cardDesign.getFactory());
				SVGEdits edits = cardDesign.getFactory().getEdits(cardDesign);
				
				//Pass the elements through to be edited
				edits.doEdits(elements);

				print("Moving to save " + cardDesign.name + " to " + getOutputPath(cardDesign));
				
				//Save the document by overwriting the newly created copy
				DOMSource docSource = new DOMSource(newDoc);
				StreamResult result = new StreamResult(new File(getOutputPath(cardDesign)));
				TransformerFactory.newInstance().newTransformer().transform(docSource, result);
			} 
			catch (IOException | TransformerException | TransformerFactoryConfigurationError | SAXException | ParserConfigurationException e) {e.printStackTrace();}
		}
	}
	
	public static String getOutputPath(Card.Design card)
	{
		return CARD_VISUALS_FOLDER + card.getDesignTypeName() + "/" + card.getReducedName() + ".svg";
	}
	
	public static String getTemplatePath(Card.Design card)
	{
		return CARD_VISUALS_TEMPLATE_FOLDER + card.getDesignTypeName() + TEMPLATE_EXTENSION;
	}
	
	public static Document getDocument(String docPath) throws SAXException, IOException, ParserConfigurationException
	{
		return DocumentBuilderFactory.newInstance()
				   .newDocumentBuilder()
				   .parse(new File(docPath));
	}
	
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
	
	private static void print(String s)
	{
		System.out.println("[Card Visualizer]: " + s);
	}
}
