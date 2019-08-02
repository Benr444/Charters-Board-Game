package charters.card.design.script;

import charters.card.design.refactor.CardSupplier;

public class SchemaGenerator
{
	public static void main(String...strings)
	{
		CardSupplier.CHARACTER_SUPPLIER.generateSchema();
		CardSupplier.IMPROVEMENT_SUPPLIER.generateSchema();
		CardSupplier.ITEM_SUPPLIER.generateSchema();
	}
}
