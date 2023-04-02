import gameplay.Gameplay;

import java.io.FileNotFoundException;

/*
PANDEMIC GAME
Code written by Jakub Zvarik
This program simulates popular board game Pandemic. Two players can play the game. Players can move between cities,
cure disease cubes and research cures for diseases. Be aware of outbreaks and epidemics! Players can win by finding
cures for all 4 diseases. Players can lose by depleting the players' deck or reaching certain number of outbreaks
(8 by default). To find a cure for disease, player needs to get certain number of cards of the same color (5 by default)
to research cure for disease of the same color. Player has to be in city of this color to be able to find cure.

How to play:

to get help type: help
to view infected cities type: infections
to move to adjacent cities type: move (or adjacent) and choose city from the list (type city name)
to fly to other city type: fly and choose from the list of cities (depends on what cards you have)
to cure disease cube in your current position type: cure
to find cure for disease type: find cure or eradicate

Architecture (packages and classes):

- support
  |- Filepaths -------- (contains path to the cities CSV file shared between Deck and Map classes)
  |- EditDistance ----- (Levenshtein's distance algorithm for spellchecking)
  |- MyHashMap -------- (creates HashMap Object)
- cities
  |- City ------------- (City object for every City in game)
- map
  |- Map -------------- (uses City objects to create interconnected Map)
- cards
  |- Card -------------|
  |- CityCard ---------|-(Card objects for every Card in game)
  |- EpidemicCards ----|
- carddecks
  |- Deck -------------|
  |- PlayersDeck ------|-(using Card objects to construct Deck objects)
  |- DiseaseDeck ------|
  |- DiscardPile ------|
- players
  |- Player -----------|
  |- HumanPlayer ------|-(Player objects with all the player's abilities)
  |- ArtificialPlayer -|
- chatbot
  |- Chatbot ----------|-(tool for communication with players and to recognise their intentions)
- gameplay
  |- Gameplay ---------|-(utilises all the classes above and contains actual rules and gameplay)


This is the main class of the game. In here, instance of Gameplay class is created, which contains the actual gameplay
agent and the gameplay loop.
*/

public class PandemicGame {

    public static void main(String[] args) throws FileNotFoundException {
        new Gameplay();
    }
}