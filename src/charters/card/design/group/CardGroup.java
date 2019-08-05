package charters.card.design.group;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import charters.card.design.card.Card;
import charters.card.design.card.CharacterCard;
import charters.card.design.card.ImprovementCard;
import charters.card.design.card.ItemCard;
import charters.card.design.design.Color;
import charters.card.design.diag.CardAnalyzer;
import charters.card.design.diag.splitter.ColorSplitter;
import charters.card.design.diag.splitter.DesignTypeSplitter;
import charters.card.design.diag.splitter.GroupSplitter;
import charters.card.design.diag.splitter.HPSplitter;
import charters.card.design.diag.splitter.SetSplitter;

/**
 * A HIGHLY COUPLED class which deals with actually instantiable cards
 * This is highly tied to the current card types in the game: characters, items and improvements
 */
public class CardGroup
{
	//==========PUBLIC CONSTANTS==========//
	
	public static final String DEFAULT_GROUP_NAME = "UNNAMED";
	/** When creating creating subgroups, this gets appended to the old name */
	public static final String SUB_GROUP_SEP = "::";
	
	//==========PRIVATE MEMBER==========//
	
	//---'SUBSECTIONS'---//
	/** Cumulation of all cards */
	private final LinkedList<ItemCard> items;
	private final LinkedList<CharacterCard> characters;
	private final LinkedList<ImprovementCard> improvements;
	private final Map<String, LinkedList<? extends Card>> allCards;
	
	//==========CONSTRUCTOR==========//

	/** @param name - Used for various printouts to identify this set */
	public CardGroup(String name)
	{
		items = new LinkedList<ItemCard>();
		characters = new LinkedList<CharacterCard>();
		improvements = new LinkedList<ImprovementCard>();
		
		allCards = new HashMap<String, LinkedList<? extends Card>>();
		allCards.put("items", items);
		allCards.put("characters", characters);
		allCards.put("improvements", improvements); 
		
		this.name = name;
	}
	
	/** Uses the default cardGroup name */
	public CardGroup()
	{
		this(DEFAULT_GROUP_NAME);
	}
	

	/** Names a new cardgroup after an existing one */
	public CardGroup(CardGroup originator, String name)
	{
		this(originator.name + SUB_GROUP_SEP + name);
	}
	
	//==========PUBLIC INTERFACE==========//
	
	public final String name;
	
	public void add(Collection<? extends Card>... cardSets)
	{
		for (Collection<? extends Card> set : cardSets)
		{
			for (Card c : set)
			{
				c.addToGroup(this);
			}
		}
	}
	
	public void add(CardGroup... cardGroups)
	{
		for (CardGroup group : cardGroups)
		{
			for (LinkedList<? extends Card> subsection : group.allCards.values())
			{
				for (Card c : subsection)
				{
					c.addToGroup(this);
				}
			}
		}
	}
	
	public void add(Collection<CardGroup> cardGroups)
	{
		add((CardGroup[])(cardGroups.toArray()));
	}

	public void add(ImprovementCard card)
	{
		this.improvements.add(card);
	}

	public void add(CharacterCard card)
	{
		this.characters.add(card);
	}

	public void add(ItemCard card)
	{
		this.items.add(card);
	}
	
	public LinkedList<CardGroup> splitCards(GroupSplitter<Card> splitter)
	{
		//Sets to return
		LinkedList<CardGroup> returnValues = new LinkedList<CardGroup>();
		
		//Iterate through each type sub set (characters, items, improvements)
		for (LinkedList<? extends Card> subsection : allCards.values())
		{	
			//Iterate over each card in that set
    		for (Card c : subsection)
    		{
    			//Assume a set group exists for this card
    			boolean respectiveSetExists = false;
    			
    			//Check over each possible card set
    			for (CardGroup setGroup : returnValues)
    			{
    				//If this card matches any of the existing sets
    				if (splitter.check(setGroup, c))
        			{
    					//Add this card to that set
    					c.addToGroup(setGroup);
    					respectiveSetExists = true;
    					break; //For now a card can only go in one set
    				}
    				else
    				{
    					//If it matches no group, mark that
    					respectiveSetExists = false;
    				}
    			}
    			
    			//If no fitting set exists
    			if (respectiveSetExists == false)
    			{
        			//It should be created
    				CardGroup newGroup = new CardGroup(this, splitter.qualityString(c));
        			c.addToGroup(newGroup);
    				returnValues.add(newGroup);
    			}
    		}
		}
		
		//Return the list of all the sets
		return returnValues;
	}
	
	public LinkedList<CardGroup> splitItems(GroupSplitter<ItemCard> splitter)
	{
		//Sets to return
		LinkedList<CardGroup> returnValues = new LinkedList<CardGroup>();

		//Iterate over each card in that set
		for (ItemCard c : items)
		{
			//Assume a set group exists for this card
			boolean respectiveSetExists = true;
			
			//Check over each possible card set
			for (CardGroup setGroup : returnValues)
			{
				//If this card matches any of the existing sets
				if (splitter.check(setGroup, c))
    			{
					//Add this card to that set
					c.addToGroup(setGroup);
					break; //For now a card can only go in one set
				}
				else
				{
					//If it matches no group, mark that
					respectiveSetExists = false;
				}
			}
			
			//If no fitting set exists
			if (respectiveSetExists == false)
			{
    			//It should be created
				CardGroup newGroup = new CardGroup(this, splitter.qualityString(c));
    			c.addToGroup(newGroup);
				returnValues.add(newGroup);
			}
		}
		
		//Return the list of all the sets
		return returnValues;
	}
	
	public LinkedList<CardGroup> splitCharacters(GroupSplitter<CharacterCard> splitter)
	{
		//Sets to return
		LinkedList<CardGroup> returnValues = new LinkedList<CardGroup>();

		//Iterate over each card in that set
		for (CharacterCard c : characters)
		{
			//Assume a set group exists for this card
			boolean respectiveSetExists = true;
			
			//Check over each possible card set
			for (CardGroup setGroup : returnValues)
			{
				//If this card matches any of the existing sets
				if (splitter.check(setGroup, c))
    			{
					//Add this card to that set
					c.addToGroup(setGroup);
					break; //For now a card can only go in one set
				}
				else
				{
					//If it matches no group, mark that
					respectiveSetExists = false;
				}
			}
			
			//If no fitting set exists
			if (respectiveSetExists == false)
			{
    			//It should be created
				CardGroup newGroup = new CardGroup(this, splitter.qualityString(c));
    			c.addToGroup(newGroup);
				returnValues.add(newGroup);
			}
		}
		
		//Return the list of all the sets
		return returnValues;
	}
	
	public LinkedList<CardGroup> splitImprovements(GroupSplitter<ImprovementCard> splitter)
	{
		//Sets to return
		LinkedList<CardGroup> returnValues = new LinkedList<CardGroup>();

		//Iterate over each card in that set
		for (ImprovementCard c : improvements)
		{
			//Assume a set group exists for this card
			boolean respectiveSetExists = true;
			
			//Check over each possible card set
			for (CardGroup setGroup : returnValues)
			{
				//If this card matches any of the existing sets
				if (splitter.check(setGroup, c))
    			{
					//Add this card to that set
					c.addToGroup(setGroup);
					break; //For now a card can only go in one set
				}
				else
				{
					//If it matches no group, mark that
					respectiveSetExists = false;
				}
			}
			
			//If no fitting set exists
			if (respectiveSetExists == false)
			{
    			//It should be created
    			returnValues.add(new CardGroup(this, splitter.qualityString(c)));
			}
		}
		
		//Return the list of all the sets
		return returnValues;
	}
	
	/**
	 * @return - Some new CardGroups containing the cards for each DESIGN-MARKED set inside this this. Subsequent calls do essentially nothing
	 */
	public LinkedList<CardGroup> setSplit()
	{
		return splitCards(new SetSplitter());
	}
	
	/** @return - A card group for each type in this set (character, item, etc.) */
	public LinkedList<CardGroup> designTypeSplit()
	{
		return splitCards(new DesignTypeSplitter());
	}
	
	/** @return - A card group for each different color in this group */
	public LinkedList<CardGroup> colorSplit()
	{
		return splitCards(new ColorSplitter());
	}
	
	/** @return - A card group for each different HP in this group *
	public LinkedList<CardGroup> hpSplit()
	{
		return splitCharacters((GroupSplitter<CharacterCard>)new HPSplitter());
	}*/
	
	/** @return - The number of cards in this set */
	public int size()
	{
		int count = 0;
		
		for (LinkedList<? extends Card> subsection : allCards.values())
		{
			count += subsection.size();
		}
		
		return count;
	}
	
	/** @param analyzer - Performs the consume function on every card in this group */
	public void forEveryCard(CardAnalyzer<Card> analyzer)
	{
		for (LinkedList<? extends Card> subsection : allCards.values())
		{
    		for (Card c : subsection)
    		{
    			analyzer.accept(c);
    		}
		}
	}
	
//	public void forEveryItem(CardAnalyzer<ImprovementCard> analyzer)
//	{
//		
//	}
	
	/** Goes through each card in this group and adds/removes from this group to match its rarity */
	//TODO: CURRENTLY DOES NOT REDUCE FOR RARITY
	public void adjustForRarity()
	{
		Map<Card, Integer> currentCounts = new HashMap<Card, Integer>();
		//Iterate through each type sub set (characters, items, improvements)
		for (LinkedList<? extends Card> subsection : allCards.values())
		{	
			//Iterate over each card in that set
    		for (Card c : subsection)
    		{
    			//NOTE: do not exist the collections while in these loops. Java does not like that.
    			
    			//Set to true once the card exists in the entries map
    			boolean cardExistsInCounts = false;
    			
    			for (Entry<Card, Integer> entry : currentCounts.entrySet())
    			{
    				if (entry.getKey() == c)
    				{
    					cardExistsInCounts = true;
    					entry.setValue(entry.getValue() + 1);
    					break;
    				}
    			}
    			
    			if (cardExistsInCounts == false)
    			{
    				//Add this card to the counts (at one)
    				print("[Rarity Adjuster] Added " + c.getDesign().name);
    				currentCounts.put(c, 1);
    			}
    		}
		}
		
		//Add enough card copies in to match the rarity system
		for (Entry<Card, Integer> entry : currentCounts.entrySet())
		{
			for (int i = entry.getValue(); i < entry.getKey().getDesign().rarity.count; i++)
			{
				print("[Rarity Adjuster] Re-Added " + entry.getKey().getDesign().name);
				entry.getKey().addToGroup(this);
			}
		}
	}
	
	//==========PRIVATE HELPERS==========//
	
	protected void print(String s)
	{
		System.out.println("[CardGroup]: " + s);
	}
}
