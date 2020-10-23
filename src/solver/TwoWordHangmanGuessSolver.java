package solver;

import java.util.*;

/**
 * Guessing strategy for two word Hangman. (task C)
 * You'll need to complete the implementation of this.
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class TwoWordHangmanGuessSolver extends HangmanSolver
{
    // Dictionary that has been fed initially.
    Set<String> originalDictionary = null;

    // All words are trying to guess
    ArrayList<DictAwareSolver> allWords = null;

    // All words index that we have guessed completely so far
    ArrayList<Integer> solvedWordsIndex = new ArrayList<Integer>();

    // We are going big work first strategy. Stores the current word under execution
    int bigWordIndex = -1;

    // All guessed chars
    Set<Character> guessedChars = null;

    /**
     * Constructor.
     *
     * @param dictionary Dictionary of words that the guessed words are drawn from.
     */
    public TwoWordHangmanGuessSolver(Set<String> dictionary) {

        this.originalDictionary = dictionary;
        this.guessedChars = new HashSet<>();

    } // end of TwoWordHangmanGuessSolver()


    @Override
    public void newGame(int[] wordLengths, int maxIncorrectGuesses) {

        allWords = new ArrayList<DictAwareSolver>();

        // Init DictAware solver for first word
        for(int i = 0; i < wordLengths.length; i++){
            allWords.add(new DictAwareSolver(this.originalDictionary));
        }

        // Start their game
        for(int i = 0; i < wordLengths.length; i++){
            allWords.get(i).newGame(new int[] {wordLengths[i]}, maxIncorrectGuesses);
        }

        // Start with the biggest word here.
        this.findBiggestWord();

    } // end of newGame()


    @Override
    public char makeGuess() {

        int bestNext = findUnsolvedWordWithOneWordLeft();
        // unrequired condition - && !( "aeiou".contains( String.valueOf(this.allWords.get(bigWordIndex).getSortedFreqMap().keySet().toArray()[0]) )  )
        if(bestNext != -1){
            System.out.println("bestNext switch Occured : "+bestNext);
            bigWordIndex = bestNext;
        }

        // Guessing from frequency distribution
        // Note that, sample size of all words have been reduced greatly at this point
        char possibleChar = this.allWords.get(bigWordIndex).makeGuess();

        // Mark it visited already
        this.guessedChars.add(possibleChar);

        return possibleChar;
    } // end of makeGuess()


    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList< ArrayList<Integer> > lPositions)
    {
        // Iterate over feedback of all words
        for(int i = 0; i < this.allWords.size(); i++){

            // Create a feedback arraylist suitable for Single word solver
            ArrayList<ArrayList<Integer>> lpos = new ArrayList<ArrayList<Integer>>();
            lpos.add(lPositions.get(i));

            // Null means, guesses char did not appear for this word
            if(lPositions.get(i) == null){
                allWords.get(i).guessFeedback(c, false, lpos );
            }else{
                // It appeared for this word
                allWords.get(i).guessFeedback(c, true, lpos );
            }
        }

        markSolvedWordsIfAny();

        // Check if the first biggest word has been solved
        if(this.allWords.get(bigWordIndex).getKnownWords().size() == 1) {

            // This should be one word here
            for (String word : this.allWords.get(bigWordIndex).getKnownWords()) {

                // Check if, the word has been solved completely
                // If completely solved, the guesses chars will have all characters from reduced set. that is 1 one the sample set reduced to
                if (this.guessedChars.containsAll(Set.of(Arrays.stream(word.split("")).distinct().map(x -> x.charAt(0)).toArray())) ) {

                    // Mark it as solved
                    solvedWordsIndex.add(bigWordIndex);

                    // recalculate biggestWordIndex
                    this.findBiggestWord();

                    System.out.println("Word Switched");

                }
            }
        }

    } // end of guessFeedback()


    /**
     * Strategy is to do biggest word first.
     * This will greatly reduce the sample size of smaller words
     * Finds the index of biggest word that has not been completely guessed it.
     */
    public void findBiggestWord(){

        int max = -1;

        System.out.println("All Words Size : "+this.allWords.size());

        // Iterate over all words
        for(int i = 0; i < this.allWords.size(); i++){

            // Max logic, also check that this word has not been solved so far.
            if(this.allWords.get(i).getWordLength() > max && !solvedWordsIndex.contains(i)){
                System.out.println(i+"th word size : "+this.allWords.get(i).getWordLength());
                max = this.allWords.get(i).getWordLength();

                // word at this index will be solved now.
                this.bigWordIndex = i;
            }
        }

        System.out.println("BigWordIndex : " + this.bigWordIndex);
        //System.exit(0);
    }


    /**
     * From a set of words, find an unsolved word, that has only one word left in sample set
     */
    public int findUnsolvedWordWithOneWordLeft(){

        int bestNext = -1;

        // Iterate over all words
        for(int i = 0; i < this.allWords.size(); i++){

            // Max logic, also check that this word has not been solved so far.
            if(this.allWords.get(i).getKnownWords().size() == 1 && !solvedWordsIndex.contains(i)){

                // word at this index will be solved now.
                bestNext = i;
            }
        }

        return bestNext;
    }


    public void markSolvedWordsIfAny(){

        // Iterate over all words
        for(int i = 0; i < this.allWords.size(); i++){

            // Reduced sample size must be one
            if(this.allWords.get(i).getKnownWords().size() == 1){

                // Get that one remaining word
                for(String remainedWord : this.allWords.get(i).getKnownWords()){

                    // If guessedChars contains all chars in remainedWord, that word has already been guessed and should be skipped
                    if (this.guessedChars.containsAll(Set.of(Arrays.stream(remainedWord.split("")).distinct().map(x -> x.charAt(0)).toArray())) ) {

                        // Marking this word as solved
                        this.solvedWordsIndex.add(i);
                    }
                }
            }
        }
    }

    // Getter-Setters Added Here

    public Set<Character> getGuessedChars() {
        return guessedChars;
    }

    public void setGuessedChars(Set<Character> guessedChars) {
        this.guessedChars = guessedChars;
    }

    public ArrayList<DictAwareSolver> getAllWords() {
        return allWords;
    }

    public void setAllWords(ArrayList<DictAwareSolver> allWords) {
        this.allWords = allWords;
    }

// Getter Setters Ends Here

} // end of class TwoWordHangmanGuessSolver
