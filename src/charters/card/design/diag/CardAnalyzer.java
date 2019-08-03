package charters.card.design.diag;

import java.util.function.Consumer;

import charters.card.design.card.Card;

public interface CardAnalyzer<T extends Card> extends Consumer<T>
{
	
}
