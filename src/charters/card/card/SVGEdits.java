package charters.card.card;

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
	public static class AttributeEdit
	{
		protected final String searchAttribute; 
		protected final String searchValue;
		protected final String editAttribute;
		protected final String editValue;
		
		public AttributeEdit(String searchAttribute, String searchValue, String editAttribute, String editValue)
		{
			this.searchAttribute = searchAttribute; 
			this.searchValue = searchValue;
			this.editAttribute = editAttribute;
			this.editValue = editValue;
		}
	}
	
	public static class TextEdit
	{
		protected final String searchAttribute;
		protected final String searchValue;
		protected final String editText;
		
		public TextEdit(String searchAttribute, String searchValue, String editText)
		{
			this.searchAttribute = searchAttribute;
			this.searchValue = searchValue;
			this.editText = editText;
		}
		
		public TextEdit(String searchValue, String editText)
		{
			this("id", searchValue, editText);
		}
	}
	
	public SVGEdits()
	{
		this.attributeEdits = new LinkedList<AttributeEdit>();
		this.textEdits = new LinkedList<TextEdit>();
	}
	
	/**
	 * Maps the following together
	 * 1. id and/or class value to search for. E.g. id="meme"
	 * 2. value to edit
	 * 3. value to set
	 */
	protected final LinkedList<AttributeEdit> attributeEdits;
	
	/**
	 * Maps the following together
	 * 1. id and/or class value to search for. E.g. id="meme"
	 * 2. value to set the text element. E.g. <t> mytext </t>
	 */
	protected final LinkedList<TextEdit> textEdits;
	
	public void addAttributeEdit(AttributeEdit edit)
	{
		attributeEdits.push(edit);
	}
	
	public void addTextEdit(TextEdit edit)
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
			if (e.hasAttribute("charters-color"))
			{
				print("Found charters-color property...");
			}
			//BEGIN editing attributes
			for (AttributeEdit edit : attributeEdits)
			{
				//Short-circuit logic
				//If the current element id matches a edit's watching-for id
				//print("Does this element have a class? " + e.hasAttribute("class"));
				if 
				(
					(e.hasAttribute(edit.searchAttribute) && (e.getAttribute(edit.searchAttribute).equals(edit.searchValue)))
				)
				{
					//If that element has the attribute which is desired to be edited
					if (e.hasAttribute(edit.editAttribute))
					{
						//Set that element's attribute to that value
						e.setAttribute(edit.editAttribute, edit.editValue);
						print("Did attribute edit: " + edit.searchAttribute + "=" + edit.searchValue + " => " + edit.editAttribute + "=" + edit.editValue);
					}
				}
			}
			
			//BEGIN editing text elements
			for (TextEdit textEdit : textEdits)
			{
				//If the ID matches the watching-for edit id
				if 
				(
					(e.hasAttribute(textEdit.searchAttribute) && e.getAttribute(textEdit.searchAttribute).equals(textEdit.searchValue))
				)
				{
					//Set the inner text content to be the value
					e.setTextContent(textEdit.editText);
					print("Did text edit: " + textEdit.searchAttribute + "=" + textEdit.searchValue + " => text=" + textEdit.editText);
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
