package charters.card.script;

import charters.card.card.CardSupplier;

public class CardSchemizer
{
	public static void main(String...strings)
	{
		CardSupplier.CHARACTER_SUPPLIER.generateSchema();
		CardSupplier.IMPROVEMENT_SUPPLIER.generateSchema();
		CardSupplier.ITEM_SUPPLIER.generateSchema();
	}
}
