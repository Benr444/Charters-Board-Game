package charters.card.visual;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import charters.card.design.script.CardSupplier;

public class CardPDFPrinter
{
	public static final String TEST_IMAGE = "resource/visual/raster/item/gear.png";
	public static final String TEST_FOLDER = "resource/visual/print/";
	public static final String TEST_NAME = "test.pdf";
	public static final String TEST_PATH = TEST_FOLDER + TEST_NAME;
	
	public static void main(String...strings)
	{
		CardSupplier.ITEM_SUPPLIER.getAll();
		makeDocument();
	}
	
	
	public static void makeDocument()
	{
		PDDocument pdf = new PDDocument();
		PDPage firstPage = new PDPage();
		try
		{
			pdf.addPage(firstPage);
			PDImageXObject img = PDImageXObject.createFromFile(TEST_IMAGE, pdf);
			double aspectRatio = (double)img.getHeight() / (double)img.getWidth();
			print("Aspect Ratio: " + aspectRatio);
			PDPageContentStream stream = new PDPageContentStream(pdf, firstPage);
			int newWidth = pxWidth(firstPage, 0.3333333);
			int newHeight = (int)(newWidth * aspectRatio);
			stream.drawImage
			(
				img, 00, 00, newWidth, newHeight
			);
			stream.close();
			pdf.save(TEST_PATH);
			pdf.close();
		} 
		catch (IOException e) {e.printStackTrace();}
	}
	
	public static int pxWidth(PDPage page, double percent)
	{
		double pageWidth = page.getCropBox().getWidth();
		print("Page Width: " + pageWidth);
		return (int)(pageWidth * percent);
	}
	
	public static void print(String s)
	{
		System.out.println("[CardPDFPrinter]: " + s);
	}
}
