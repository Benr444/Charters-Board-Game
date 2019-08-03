package charters.card.design.script;

public class SchemaGenerator
{
	public static void main(String...strings)
	{
		CardSupplier.CHARACTER_SUPPLIER.generateSchema();
		CardSupplier.IMPROVEMENT_SUPPLIER.generateSchema();
		CardSupplier.ITEM_SUPPLIER.generateSchema();
	}
}
