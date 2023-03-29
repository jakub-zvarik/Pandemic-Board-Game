package chatbot;


import java.util.Scanner;

/*
Chatbot enables players to choose action which they want to do for their turn. This component
takes in users input, evaluates in and returns corresponding value. The system then calls
corresponding method to fulfil player's intentions. These values returned by the chatbot
are evaluated inside the Gameplay class.
*/
public class Chatbot {

    private final Scanner scanner = new Scanner(System.in);

    // Arrays of possible inputs from users for different situations
    private final String[] agreement = {"yes", "that's it", "positive", "agree", "true"};
    private final String[] disagreement = {"no", "false", "negative", "nope"};
    private final String[] whatToDo = {"ask what to do", "what can i do?", "what to do?", "what to do now?", "help",
            "actions", "commands", "h"};
    private final String[] showInfected = {"show infected cities", "which cities are infected", "infected cities",
            "show infections"};
    private final String[] moveToAdjacent = {"move to adjacent city", "move to adjacent", "drive", "move", "adjacent",
            "connected", "move to connected city", "I want to go to adjacent city", "take me"};
    private final String[] fly = {"fly", "fly to city", "move to other city"};
    private final String[] cure = {"cure", "cure disease", "delete disease cube"};
    private final String[] findCure = {"find cure", "eradicate"};

    // Arrays above are added into one array of arrays. Intention is evaluated based on the index returned.
    private final String[][] possibleQueries = new String[][] {agreement, disagreement, whatToDo,
            showInfected, moveToAdjacent, fly, cure, findCure};

    /*
    For every player's turn, this chatbot's method is called to evaluate what intention
    the player has. This is done by comparing the player's input against arrays with possible
    inputs. Chatbot have three states - sure of what suer wants, unsure if it understood correctly,
    and sure it does not know.
    */
    public int chatTurn() {
        int indexCounter = -1;
        String userInput = this.scanner.nextLine();
        // Iterates through every array in the possibleQueries array
        for (String[] array : this.possibleQueries) {
            // Counts index of currently iterated array
            indexCounter++;
            // Iterates through every string in the array
            for (String word : array) {
                // Sure about intention
                if (word.contains(userInput)) {
                    return indexCounter;
                }
            }
            // If there is only part of the word in the array, chatbot will ask if that's what player means
            for (String word : array) {
                // Unsure about intention
                if (word.contains(userInput.substring(0, Math.min(userInput.length(), 3)))) {
                    return doYouMean(indexCounter);
                }
            }
        }
        // Returns -1 if chatbot does not know what user wants
        return -1;
    }

    /*
    If chatbot finds only part of the user's input in the arrays with possible actions,
    it asks the user if that is what they mean. It uses the first word in the possible
    array for the question.
    */
    private int doYouMean(int indexCounter) {
        // Asks the question
        System.out.println("Sorry, I did not catch that. Do you mean to " + possibleQueries[indexCounter][0] + " ?");
        // Call chatbot again to get answer
        int CHATBOT_VERDICT = chatTurn();
        // If yes, returns the index of array that was in question
        if (CHATBOT_VERDICT == 0) {
            System.out.println("Ok, let's see...");
            return indexCounter;
        } else {
            System.out.println("Oops, I am sorry then. Let's try this again.");
            return -1;
        }

    }
}
