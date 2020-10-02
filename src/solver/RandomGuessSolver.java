package solver;

import java.lang.reflect.Array;
import java.util.*;
import java.lang.System;

import static java.util.Arrays.*;

/**
 * Random guessing strategy for Hangman. (task A)
 * You'll need to complete the implementation of this.
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class RandomGuessSolver extends HangmanSolver
{

    private static List<String> charsSet = null;

    private List<String> guessedChars;
    private Random randomUtil;

    /**
     * Constructor.
     *
     * @param dictionary Dictionary of words that the guessed words are drawn from.
     */
    public RandomGuessSolver(Set<String> dictionary) {

        this.randomUtil = new Random();
        this.guessedChars = new ArrayList<String>();
        this.charsSet = new ArrayList<String>();
        charsSet = asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
                "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
                "y", "z");

    } // end of RandomGuessSolver()


    @Override
    public void newGame(int[] wordLengths, int maxIncorrectGuesses) {

        // For every new game, clear the previouly stored guessChars
        this.guessedChars = new ArrayList<String>();

    } // end of newGame()


    @Override
    public char makeGuess() {

        // Guess a random characters from known set of characters a - z
        String currGuess = RandomGuessSolver.charsSet.get(this.randomUtil.nextInt(26));

        // If the character has already been guessed earlier then guess again.
        while(guessedChars.contains(currGuess)){
            currGuess = RandomGuessSolver.charsSet.get(this.randomUtil.nextInt(26));
        }

        // Unguessed character found, record it
        guessedChars.add(currGuess);

        return currGuess.toCharArray()[0];
    } // end of makeGuess()


    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList< ArrayList<Integer> > lPositions)
    {

    } // end of guessFeedback()

} // end of class RandomGuessSolver
