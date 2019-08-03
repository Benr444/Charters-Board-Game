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

import charters.card.design.card.Card;
import charters.card.design.group.CardGroup;
import charters.card.design.script.CardSupplier;

public abstract class CardVisualizer
{
	public static void main(String... args)
	{	
		CardGroup allCards = new CardGroup();
		allCards.add(CardSupplier.CHARACTER_SUPPLIER.getAll());
		for (Card c : allCards)
		{
			c.vectorize();
			print("Vectorized " + c.getDesign().name + " and output to " + c.getVectorFile().getAbsolutePath());
		}
	}
	
	private static void print(String s)
	{
		System.out.println("[Card Visualizer]: " + s);
	}
}
