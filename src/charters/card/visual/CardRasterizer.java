package charters.card.visual;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.fasterxml.jackson.databind.ObjectMapper;

import charters.card.design.Card;
import charters.card.design.Card.Design;
import charters.card.design.Item;

/**
 * Turns card svg's into raster png's, then saves them in the output folder
 * 
 * Requires INKSCAPE to be installed and be on the path to function
 */
public class CardRasterizer
{
	public static final int DPI = 200;
	
	public static void main(String...strings)
	{
		print("=== BEGINNING RASTERIZATION ===");
		rasterize(new Item());
	}
	
	/**
	 * @return - File name with no extension
	 */
	public static String noExtension(String file)
	{
		return file.split("\\.")[0];
	}
	
	public static void rasterize(Card factory)
	{
		print(factory.getCardFolderPath());
		File svgFolder = new File(factory.getCardFolderPath());
		File[] svgFiles = svgFolder.listFiles();
		if (svgFiles != null)
		{
			for (File f : svgFiles)
			{
				if (f.isFile())
				{
					String simpleName = noExtension(f.getName());
					print("Found candidate svg file: " + simpleName);
					String cmd = "inkscape " +
								 noExtension(f.getAbsolutePath()) + ".svg" + 
								 " -e " +
								 factory.getRasterFolderPath() + simpleName + ".png" +
								 " - d " + DPI;
					print("The command!: " + cmd);
					try
					{
						Runtime.getRuntime().exec(cmd);
					} 
					catch (IOException e) {e.printStackTrace();}
				}
			}
		}
	}
	
	public static void print(String s)
	{
		System.out.println("[CardRasterizer]: " + s);
	}
}