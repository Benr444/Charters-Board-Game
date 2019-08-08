package charters.card.layout;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import charters.card.card.Card;
import charters.card.group.CardGroup;

/**
 * Creates a pdf that contains card iamges for a design set 
 */
public class CardLayout
{
	//========== PUBLIC STATICS ==========//

	public static final String LAYOUT_FOLDER = "resource/visual/card/print/";
	public static final String LAYOUT_EXT = ".pdf";
	
	//========== PRIVATE MEMBERS ==========//
	
	private final String name;
	private final PDDocument pdf;
	private final LinkedList<LayoutPage> layouts;
	
	//========== CONSTRUCTOR ==========//
	
	public CardLayout(String inputName)
	{
		name = inputName;
		pdf = new PDDocument();
		layouts = new LinkedList<LayoutPage>();
	}
	
	//========== PUBLIC INTERFACE ==========//
	
	public PDDocument getDocument()
	{
		return pdf;
	}
	
	public void add(Card c)
	{
		boolean addedToExisting = false;
		
		for (LayoutPage layout : layouts)
		{
			if (layout.add(c) == true) //If card is added successfully
			{
				addedToExisting = true;
			}
		}
		
		if (addedToExisting == false)
		{
			LayoutPage newLayout = new LayoutPage(getDocument());
			newLayout.add(c);
			layouts.add(newLayout);
		}
	}
	
	public boolean export()
	{
		for (LayoutPage layout : layouts)
		{
			pdf.addPage(layout.getPage());
		}
		try
		{
			pdf.save(new File(LAYOUT_FOLDER + this.name + LAYOUT_EXT));
			pdf.close();
			print("Saved printout " + this.name);
			return true;
		} 
		catch (IOException e) {e.printStackTrace(); return false;}
	}
	
	//========== PRIVATE HELPERS ==========//
	
	protected static void print(String s)
	{
		System.out.println("[CardPrintout]: " + s);
	}
}
