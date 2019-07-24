package charters.card.design.script;

import charters.card.design.Character;
import charters.card.design.Improvement;
import charters.card.design.Item;

public class SchemaGenerator
{
	public static void main(String...strings)
	{
		new Item().generateSchema();
		new Improvement().generateSchema();
		new Character().generateSchema();
	}
}
