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
import charters.card.design.refactor.CardGroup;
import charters.card.design.refactor.CardSupplier;

public abstract class CardVisualizer
{
	public static void main(String... args)
	{	
		CardGroup allCards = new CardGroup();
		allCards.add(CardSupplier.CHARACTER_SUPPLIER.getAll());
		for (Card c : allCards)
		{
			
		}
	}
	
	private static void print(String s)
	{
		System.out.println("[Card Visualizer]: " + s);
	}
}
