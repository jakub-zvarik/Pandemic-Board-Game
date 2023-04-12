<b>In this version of the game (with default settings) …</b>
This work aimed to create a program that would simulate the popular board game Pandemic. This Pandemic game has a board with interconnected cities. These cities have four different colours (blue, yellow, black, and red). There are also four different diseases with these four colours. Two different card decks are used in the game, the player’s deck, and the disease deck. The player’s deck contains 48 city cards (with names of cities and their colours) and 4 epidemics cards. The disease deck has 48 city cards (with names of their cities and their colours). Every turn players play their 4 moves and draw two cards from the player’s deck. A player can move around the map, decrease infections in cities, or find cures for diseases. The maximum number of cards a player can hold is 7. Every turn cities are infected – a card drawn from the disease deck decides which city will be infected. Infecting a city means adding 1 disease cube. But if the city already has 3 disease cubes, an outbreak occurs – every adjacent city gets a disease cube. If a player draws an epidemic card from the player’s deck, the epidemic mechanic occurs. This means that the next city drawn from the disease deck will receive 3 disease cubes if has 0 disease cubes. If it has more than 0 disease cubes, it will automatically receive 3 disease cubes and trigger an outbreak. Outbreaks are also triggered whenever the city has 3 infection cubes and is chosen to be infected again. An outbreak can only occur 7 times through the game, if it comes up 8th time, the game is over, and players lost. Also, if there are no players’ cards left in their deck, the game is over, and players lost. The only way to win the game is to find all cures for all diseases. When players manage to find a cure for a disease, cities with that colour will no longer be infected and if players cure the disease there, infection cubes will be set to 0, no matter how many infection cubes there are in the city.


<h3>How to play</h3>
Players start in Atlanta. Two players play the game. It’s turn-based. 

The player is prompted to enter what they want to do. There are multiple actions that players can perform:
1.	ask for help – type “help” or “what to do”.
2.	show infected cities: type “infections”.
3.	move to adjacent cities: type “move” or “adjacent” and after another prompt, type the name of the city.
4.	fly to another city (if the player has a card with the destination city name): type “fly” and after another prompt, type the name of the destination.
5.	cure infection cube (to decrease the number of infection cubes in the current city) – type (“cure”)
6.	find a cure (the player needs to have 5 cards of the colour of the disease they want to find a cure for) and they have to be in the city of that colour.

Please note: there are more keywords that the chatbot knows, the shortest ones were written in this manual. If you want to try more words, sometimes the chatbot won’t be sure what you meant and will try to figure it out – at this point, it will ask you if it is correct. Answer “yes” or “no”.


<h3>Overview of implemented functionality</h3>

•	Map with interconnected cities (can be edited via CSV file)

•	Card decks (player’s deck, disease deck, discard piles)

•	Chatbot evaluates the player’s input and decides what intentions the player has.

•	Edit Distance algorithm for spelling mistakes implemented (for city names, explained in the ‘Chosen architecture’ part)

•	Players can travel around the world (adjacent cities and flights), cure infection cubes in cities and research cures.

•	The gameplay agent is controlling events such as infections, epidemics, and outbreaks.

•	The game can be won (by finding all four cures) or lost (by reaching a high number of outbreaks or depleting players’ decks).

•	The gameplay agent is making sure the game is played according to rules and checks game states.



<h3>Chosen architecture</h3>
The architecture chosen for this program is built upon the premise of object-oriented programming, where almost every element in the original board game is represented as an object in the code. This leads to several classes, every one of which is designed to create real-life game objects and their properties. The result leads to the following architecture:

<hr>

<i>(file) cities.csv</i> – CSV file containing all the cities in the game, their colours, and adjacent cities. By editing this file, cities can be completely edited, added, or deleted from the game. The design of the mechanism loading cities into the game is made with scalability in mind. This means that the users can add as many cities as they like – also with custom colour which will be automatically taken into the game as a new disease and users will need to find a cure for it. Since the same file is used to create cards, cards for these newly added cities will be also automatically generated. If you want custom cities with custom colours (and therefore custom disease), please remember to add enough of these cities so you can comfortably find a cure for their colour of disease (by default 5 cards of the same colour are needed to find the cure, but it’s advisable to add more to have a fighting chance).


<i>(package) support:</i>

• MyHashMap – Classic HashMap but coded from scratch. It creates a list of objects that are composed of keys and values. The value created and inserted into the MyHashMap object is paired with a key and can be found and manipulated via this key. MyHashMap 
is mutable and there is no need to specify the initial size of the user. Apart from basic functions such as adding new entries or getting the value via key, it also keeps track of its size, if this information is needed.
 
•	Filepaths – interface containing important file paths. This approach was chosen in case the same file paths must be used across multiple classes and to give users one interface where they can edit file paths, which will consequently make changes everywhere, where it is needed.
 
•	EditDistance – Edit distance employs Levenshtein’s algorithm to compare two strings and calculate the minimal number of edits (insertion, substitution, or deletion) needed to transform one string into another.


<i>(package) cities:</i>

•	City – This class is used to create a City object with multiple properties. Every City object has its name (String) and colour (String), an array of adjacent cities (City []), a number of infection cubes (int) and a value indicating if the cure of the same colour as the colour of the City object was already researched (Boolean).


<i>(package) map:</i>

•	Map – Map class is designed to create a map of the world composed of City objects and make them interconnected. First, the MyHashMap object is created and filled with City objects. These are created by reading the cities.csv file and taking the values needed to create a City object (name and colour of the city). Filepaths interface is implemented to get access to this file. Once the object is created, it is added to the MyHashMap object. As a key, the name of the city is used. To make cities connected, adjacent cities are added to every City object inside the MyHashMap. This is done by reading the cities.csv file (to get the name of a city and the names of its adjacent cities). City object is looked up by key in the MyHashMap. Every adjacent city is then also looked up in the MyHashMap via its keys and City objects associated with these keys are then added to the City object’s array of adjacent cities.





<i>(package) cards:</i>

•	Card – Used to create objects of Card. Every Card object has a name and colour since in this version of the game are not any special cards (yet). Cards have names and colours the same as their City object counterparts. The only exemptions are Epidemic cards, which have the name “EPIDEMIC” and the colour “Poison green”.



<i>(package) carddecks:</i>

•	Deck – abstract class laying down a “blueprint” for every Deck in the game. The deck contains ArrayLists of Card objects. ArrayList was chosen because of the fast-changing nature of Decks in the game – Card objects are always moved between decks, changing their size rapidly. As said above, Deck objects are composed of Card objects. There are two decks in the real game – the players’ deck and the disease deck. Both contain cards with cities’ names and colours. Therefore, Card objects are created from the CVS file with cities and placed into the Deck’s ArrayList. Deck implements support class Filepaths to get access to the cities.csv. Apart from that Deck has methods to shuffle the deck, discard a Card object and move it to another Deck object and remove the card from the game.
 
•	PlayersDeck – extends the Deck abstract class. The player’s deck apart from the Card objects with cities’ names and colours, also has epidemic cards. There are a few special steps involved in creating PlayersDeck, therefore there is a method for implementing these steps. First, it creates city Card objects from the cities.csv file and adds them to an ArrayList. This ArrayList is then shuffled and divided into 4 piles. One epidemic card per pile is added (so 4 of them in total). Every pile is shuffled and then these piles are put back to form one deck – the final Player’s Deck object.

•	DiseaseDeck – this deck only contains cards with cities’ names and colours. It takes advantage of the method from its parent class to create Card objects and initialise the deck itself by reading from the CSV file.

•	DiscardPile – this extends the Deck abstract class too. No cards are read in during its initialisation, this pile is designed to store all the drawn cards. There are two of these DiscardPile objects, one for the Players’ Deck, and the other one for the Disease Deck.


<i>(package) players:</i>

•	Player – abstract class for Player objects. Every Player object has the player’s colour (String), which is required by the constructor. It is also tracking the current player’s position by setting the variable of the City object to a city where the player currently is. There is also an ArrayList of Card objects, which is the player’s hand. It can hold a maximum of 7 Card objects by default, which is set by a constant in the Player class and can be modified here. The number of cards needed to find a cure is here and by default, it is set to 5. A number of starting cards is also here, set to 4 and can be modified. There are multiple methods taking care of multiple tasks - drawing starting cards from the players’ deck, according to the number of starting cards set in class variables, cure city (decrease the number of disease cubes by one or set them to 0 if a cure has been found) and find a cure for the disease (if the player has enough cards of the same colour). Method to check how many cards can player draw at the end of their turn (depends on how many). 
 
•	HumanPlayer – extends abstract class Player. Apart from inheriting from its parent class Player, this class also implements basic actions that players can do. In this version of the game, HumanPlayer can move to the adjacent city, and fly to the city (if they hold the card with the destination name). It implements Levenshtein’s algorithm (edit distance) to tolerate spelling mistakes (to some extent) while writing down names of cities where players want to travel. 
 
•	ArtificialPlayer – extends abstract class Player. For now, it does not do anything noticeable, this is one of the subjects of future work.


<i>(package) chatbot:</i>

•	Chatbot – chatbot is by the gameplay agent to recognise the intentions of the player and call the right action. The first component of Chatbot is String arrays with possible user input. These possible inputs are divided into multiple arrays, according to what intention they represent. The arrays are named to represent the intention. All arrays are then stored in a 2-dimensional array of String arrays, which is used for further evaluation. The user’s input is compared against Strings in the arrays, looking for the best match. If the match is found, the index of the array which contains the match is returned, which is interpreted by the gameplay agent. If Chatbot finds only part of the user’s input in the intention arrays, it will ask the user, if that is what they mean. The user is prompted to confirm or decline (yes, no, that’s it, negative etc.). If the answer is positive, it will return the index of the array it was asking about to the gameplay agent. The first version of the chatbot also employed Levenshtein’s algorithm, but this didn’t work out very well – it usually jumped to the wrong conclusion at the first probable answer. Not even a combination of searching for a keyword and Levenshtein’s algorithm worked well because many intentions have similar characters and lengths.


<i>(package) gameplay:</i>

•	Gameplay – this is where all the other classes are initialised, and where the gameplay loop and the gameplay agent are. The gameplay agent takes care of checking for the states of the game (if the game is lost or won) and making sure players play by the rules. Here, many changes can be made to gameplay – the initial city for the player can be changed, actions per turn increased or decreased, infection rates added or deleted, and a maximum number of outbreaks edited. The turning method invoked the chatbot to evaluate the player’s intentions. Depending on the chatbot’s verdict, appropriate action is called. Cards are drawn at the end of every turn. The gameplay agent performs an epidemic check to see if any epidemic cards were drawn. If yes, the gameplay agent starts the epidemic process. Every time infections happen; the gameplay agent is checking if there are any outbreaks in the affected cities and makes sure that one city does not get more than one outbreak in one turn. There are also methods used by players to check the current state of the game or show the manual for the game.


PandemicGame – Main class with game description in the comments is outlined architecture. The game must run from here. To run the game, the Gameplay object is constructed here.

<hr>

<h3>Future work</h3>
Implementation of more game mechanics such as research centres, multiple ways of travelling and player roles is planned and will be eventually implemented to make the gameplay more interesting. Implementation of a player agent and adding the option to play with up to four players are also important features to make the gameplay better.
