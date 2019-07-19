package charters.card.visual;

import java.awt.Color;
import java.util.LinkedList;

import org.w3c.dom.Element;

import javafx.util.Pair;

/**
 * Class that translates design files into actual changes to be made on the svg file
 * DO NOT combine this functionality with the CardDesign class.
 */
public final class SVGEdits
{
	public SVGEdits()
	{
		this.attributeEdits = new LinkedList<Pair<String, Pair<String, String>>>();
		this.textEdits = new LinkedList<Pair<String, String>>();
		//this.colorEdits = new LinkedList<Pair<String, Color>>();
	}
	
	/**
	 * Maps the following together
	 * 1. id and/or class value to search for. E.g. id="meme"
	 * 2. value to edit
	 * 3. value to set
	 */
	protected final LinkedList<Pair<String, Pair<String, String>>> attributeEdits;
	
	/**
	 * Maps the following together
	 * 1. id and/or class value to search for. E.g. id="meme"
	 * 2. value to set the text element. E.g. <t> mytext </t>
	 */
	protected final LinkedList<Pair<String, String>> textEdits;
	
	/**
	 * Maps the following together
	 * 1. id and/or class value to search for. E.g. id="meme"
	 * 2. value of color to set
	 */
	//protected final LinkedList<Pair<String, Color>> colorEdits;
	
	public void addAttributeEdit(Pair<String, Pair<String, String>> edit)
	{
		attributeEdits.push(edit);
	}
	
	public void addTextEdit(Pair<String, String> edit)
	{
		textEdits.push(edit);
	}
	
	/**
	 * Apply all the edits in this object to the passed element list
	 * @param elements - List of elements to trawl over to apply edits
	 */
	public void doEdits(LinkedList<Element> elements)
	{
		print("Implementing edits");
		
		for (Element e : elements)
		{
			//BEGIN editing attributes
			for (Pair<String, Pair<String, String>> edit : attributeEdits)
			{
				//Short-circuit logic
				//If the current element id matches a edit's watching-for id
				//print("Does this element have a class? " + e.hasAttribute("class"));
				if 
				(
					(e.hasAttribute("class") && (e.getAttribute("class").equals(edit.getKey()))) ||
					(e.hasAttribute("id") && (e.getAttribute("id").equals(edit.getKey())))
				)
				{
					//If that element has the attribute which is desired to be edited
					if (e.hasAttribute(edit.getValue().getKey()))
					{
						//Set that element's attribute to that value
						e.setAttribute(edit.getValue().getKey(), edit.getValue().getValue());
						print("Did attribute edit: " + edit.getKey() + " => " + edit.getValue().getKey() + "=" + edit.getValue().getValue());
					}
				}
			}
			
			//BEGIN editing text elements
			for (Pair<String, String> textEdit : textEdits)
			{
				//If the ID matches the watching-for edit id
				if 
				(
					(e.hasAttribute("id") && e.getAttribute("id").equals(textEdit.getKey())) ||
					(e.hasAttribute("class") && e.getAttribute("class").equals(textEdit.getKey()))
				)
				{
					//Set the inner text content to be the value
					e.setTextContent(textEdit.getValue());
					print("Did text edit: " + textEdit.getKey() + " => " + textEdit.getValue());
				}
			}
		}
	}
	
	public String formatColor(Color color)
	{
		return "";
	}
	
	private static void print(String s)
	{
		System.out.println("[SVG Edits]: " + s);
	}
}
