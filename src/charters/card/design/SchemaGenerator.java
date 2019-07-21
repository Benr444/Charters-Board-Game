package charters.card.design;

public class SchemaGenerator
{
	public static void main(String...strings)
	{
		new Item().generateSchema();
		new Improvement().generateSchema();
		new Character().generateSchema();
	}
}
