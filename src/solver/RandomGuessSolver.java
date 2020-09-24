package solver;

import java.util.*;
import java.lang.System;

/**
 * Random guessing strategy for Hangman. (task A)
 * You'll need to complete the implementation of this.
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class RandomGuessSolver extends HangmanSolver
{


    /**
     * Constructor.
     *
     * @param dictionary Dictionary of words that the guessed words are drawn from.
     */
    public RandomGuessSolver(Set<String> dictionary) {
        // Implement me!
    } // end of RandomGuessSolver()


    @Override
    public void newGame(int[] wordLengths, int maxIncorrectGuesses) {
        // Implement me!
    } // end of newGame()


    @Override
    public char makeGuess() {
        // Implement me!

        // TODO: This is a placeholder, replace with appropriate return value.
        return '\0';
    } // end of makeGuess()


    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList< ArrayList<Integer> > lPositions)
    {
        // Implement me!
    } // end of guessFeedback()

} // end of class RandomGuessSolver
