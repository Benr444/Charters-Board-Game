package charters.card.layout;

import java.awt.Point;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import charters.card.card.Card;
import charters.card.card.ItemCard;

public class LayoutPage
{
	//========== PUBLIC STATICS ==========//

	/**
	 * @param points - unit of graphical measure
	 * @return - equiv. inches
	 */
	public static double convertToInches(double points)
	{
		return points / 72;
	}
	
	/**
	 * @param inches
	 * @return - equiv. points
	 */
	public static double convertToPoints(double inches)
	{
		return inches * 72;
	}
	
	public static double getWidthInches(PDImageXObject cardImage)
	{return (double)cardImage.getWidth() / Card.RASTER_DPI;}

	public static double getHeightInches(PDImageXObject cardImage)
	{return (double)cardImage.getHeight() / Card.RASTER_DPI;}
	
	protected PDDocument doc;
	protected PDPage page;
	/** In inches */
	protected Extent currentAdvance;
	protected Extent maximumExtent;
	/** Since all cards must be the same size, this is that size */
	protected Extent cardSize;
	protected LinkedList<PDImageXObject> images;
	
	LayoutPage(PDDocument doc)
	{
		print("Created LayoutPage");
		this.doc = doc;
		page = new PDPage();
		currentAdvance = new Extent();
		maximumExtent = new Extent();
		images = new LinkedList<PDImageXObject>();
		print("Layout Width: " + getWidthInches());
		print("Layout Height: " + getHeightInches());
	}
	
	public double getWidthInches()
	{return convertToInches(this.page.getCropBox().getWidth());}
	
	public double getHeightInches()
	{return convertToInches(this.page.getCropBox().getHeight());}
	
	/*
	 * RECURSION ALERT!
	 * Attempts to add the passed card to this page
	 * @return - false if the image cannot fit
	 */
	public boolean add(Card i)
	{
		try
		{
			PDImageXObject img = PDImageXObject.createFromFile(i.getRasterFile().getAbsolutePath(), doc);

			if (cardSize == null)
			{
				cardSize = new Extent();
				cardSize.width = getWidthInches(img);
				cardSize.height = getHeightInches(img);
			}
			
			print("==========");
			print("Current Card Count: " + images.size());
			print("Current Advance: " + currentAdvance + " in");
			print("Maximum Extent: " + maximumExtent + " in");
			print("Image Dimensions: (" + getWidthInches(img) + ", " + getHeightInches(img) + ") in");
			print("Current Advance after addition (?): (" + (currentAdvance.width + getWidthInches(img)) + ", " 
				+ (currentAdvance.height + getHeightInches(img)) + ") in");
			print("Page Dimensions: (" + getWidthInches() + ", " + getHeightInches() + ") in");
			
			//The new image must fit inside the remaining vertical space
			if (currentAdvance.height + getHeightInches(img) < getHeightInches())
			{
				//and also horizontal space
				if (currentAdvance.width + getWidthInches(img) < getWidthInches())
				{
					//print("Adding " + i.getDesign().name + " to layout");
					//If this is the first image on this row
					if (currentAdvance.width == 0)
					{
						maximumExtent.height += getHeightInches(img);
					}
					currentAdvance.width += getWidthInches(img); 
					maximumExtent.expandTo(currentAdvance);
					print("Card accepted!");
					images.add(img);
					return true;
				}
				else
				{
					//If it doesn't fit in the remaining horizontal space
					currentAdvance.width = 0; //Reset the advance to the start
					currentAdvance.height += getHeightInches(img); //Go up a row (all img must be same size)
					maximumExtent.expandTo(currentAdvance);
					print("Reattempting to fit card on next row.");
					return add(i); //Attempt to re-add this card
				}
			}
			else
			{
				//If it doesn't fit in the remaining vertical space, there's nothing to be done
				print("Card rejected. Page is full.");
				return false;
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public PDPage getPage()
	{
		this.paint();
		return this.page;
	}
	
	//========== PRIVATE HELPER METHODS ==========//
	
	/**
	 * Paints all the images added to this layout onto the page
	 */
	protected void paint()
	{
		print("==========");
		Extent startPt = new Extent();
		Extent printPt = new Extent();
		
		//Center the drawing process
		print("PDF Page: (" + getWidthInches() + ", " + getHeightInches() + ")");
		print("Maximum Extent: " + maximumExtent);
		
		startPt.width = (getWidthInches() - maximumExtent.width) / 2;
		startPt.height = (getHeightInches() - maximumExtent.height) / 2;
		printPt.width = startPt.width;
		printPt.height = startPt.height;
		
		print("startPt: " + startPt);
		print("Starting printPt: " + printPt);
		
		print(images.size() + " images to print on this layout.");
		
		try
		{
			PDPageContentStream stream = new PDPageContentStream(doc, page);
			for (PDImageXObject image : images)
			{
				stream.drawImage
				(
					image,
					(float)convertToPoints(printPt.width),
					(float)convertToPoints(printPt.height),
					(float)convertToPoints(getWidthInches(image)),
					(float)convertToPoints(getHeightInches(image))
				);
				//Again, assumes equal image sizes
				printPt.width += getWidthInches(image);
				if (printPt.width + getWidthInches(image) > getWidthInches())
				{
					printPt.height += cardSize.height;
					printPt.width = startPt.width;
				}
			}
			stream.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	protected static void print(String s)
	{
		System.out.println("[LayoutPage]: " + s);
	}
}