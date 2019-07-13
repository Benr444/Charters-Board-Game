package charters.card.script;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import charters.card.design.CardDesign;

public class DesignReader<T extends CardDesign>
{
	public static final boolean DO_PRINTOUT = false;
	
	public final Class<T> designType;
	
	public DesignReader(Class<T> designType)
	{
		this.designType = designType;
	}
	
	public LinkedList<T> readDesigns(String folderPath)
	{
		LinkedList<T> returnValues = new LinkedList<T>();
		File designsFolder = new File(folderPath);
		File[] designFiles = designsFolder.listFiles();
		if (designFiles != null)
		{
			for (File f : designFiles)
			{
				if (f.isFile())
				{
					print("Found candidate design file: " + f.getName());
					try
					{
						String jsonContents = new String(Files.readAllBytes(f.toPath()));
						ObjectMapper deserializer = new ObjectMapper();
						T currentDesign = deserializer.readValue(jsonContents, this.designType);
						print("\tFound card design: " + currentDesign.name + " (" + currentDesign.getDesignTypeName() + ")");
						returnValues.add(currentDesign);
					} 
					catch (IOException e) {e.printStackTrace();}
				}
			}
		}
		return returnValues;
	}

	private static void print(String s)
	{
		if (DO_PRINTOUT) System.out.println("[Design Reader]: " + s);
	}
}
