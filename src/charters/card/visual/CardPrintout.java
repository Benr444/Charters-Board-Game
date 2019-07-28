package charters.card.visual;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 * Creates a pdf that contains card iamges for a design set 
 */
public class CardPrintout
{
	private final PDDocument pdf;
	private final LinkedList<PDPage> pages;
	
	public CardPrintout()
	{
		pdf = new PDDocument();
		pages = new LinkedList<PDPage>();
	}
	
	public boolean save()
	{
		for (PDPage p : pages)
		{
			pdf.addPage(p);
		}
		try
		{
			pdf.close();
			return true;
		} 
		catch (IOException e) {e.printStackTrace(); return false;}
	}

	/**
	 * @param imgPath - Path to image to add
	 * @return - True if the image can fit and was added, false if it cant fit and wasn't added
	 */
	public static boolean addImage(String imgPath)
	{
		return false;
	}
}
