package charters.card.group;

import java.util.function.Consumer;

import charters.card.card.Card;

public interface CardAnalyzer<T extends Card> extends Consumer<T>
{
	
}
