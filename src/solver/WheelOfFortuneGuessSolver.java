package solver;

import java.util.*;


/**
 * Guessing strategy for Wheel of Fortune Hangman variant. (task D)
 * You'll need to complete the implementation of this.
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class WheelOfFortuneGuessSolver extends HangmanSolver
{

    // Dictionary that has been fed initially.
    private Set<String> originalDictionary = null;

    // All words are trying to guess
    private ArrayList<DictAwareSolver> allWords = null;

    // All words index that we have guessed completely so far
    private ArrayList<Integer> solvedWordsIndex = new ArrayList<Integer>();

    // We are going big work first strategy. Stores the current word under execution
    private int bigWordIndex = -1;

    // All guessed chars
    private Set<Character> guessedChars = null;


    /**
     * Constructor.
     *
     * @param dictionary Dictionary of words that the guessed words are drawn from.
     */
    public WheelOfFortuneGuessSolver(Set<String> dictionary) {

        this.originalDictionary = dictionary;
        this.guessedChars = new HashSet<>();

    } // end of WheelOfFortuneGuessSolver()


    @Override
    public void newGame(int[] wordLengths, int maxTries) {

        allWords = new ArrayList<DictAwareSolver>();

        // Init DictAware solver for first word
        for(int i = 0; i < wordLengths.length; i++){
            allWords.add(new DictAwareSolver(this.originalDictionary));
        }

        // Start their game
        for(int i = 0; i < wordLengths.length; i++){
            allWords.get(i).newGame(new int[] {wordLengths[i]}, maxTries);
        }

        // Start with the biggest word here.
        this.findBiggestWord();
    } // end of setWordLength()


    @Override
    public char makeGuess() {

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

        // Check if the first biggest word has been solved
        if(this.allWords.get(bigWordIndex).getKnownWords().size() == 1) {

            // This should be one word here
            for (String word : this.allWords.get(bigWordIndex).getKnownWords()) {

                // Check if, the word has been solved completely
                // If completely solved, the guesses chars will have all characters from reduced set. that is 1 one the sample set reduced to
                if (this.guessedChars.containsAll(Set.of(Arrays.stream(word.split("")).distinct().map(x -> x.charAt(0)).toArray())) ) {

                    // Mark it as solved
                    solvedWordsIndex.add(bigWordIndex);

                    // Find next biggest word, recalculate biggestWordIndex
                    this.findBiggestWord();

                    //System.out.println("Word Switched");

                }
            }
        }

        markSolvedWordsIfAny();

        // Maximising the guessing chances. Since sample size is reduced to one, every guess made, would be hit, and will help us reduce sample size of other words
        int bestNext = findUnsolvedWordWithOneWordLeft();

        // We have bestNext, Try that
        if(bestNext != -1){
            //System.out.println("Switched Biggest Word Index with Best Next ===> "+bestNext);
            this.bigWordIndex = bestNext;
        }

    } // end of guessFeedback()


    /**
     * Strategy is to do biggest word first.
     * This will greatly reduce the sample size of smaller words
     * Finds the index of biggest word that has not been completely guessed it.
     */
    public void findBiggestWord(){

        int max = -1;

        // Iterate over all words
        for(int i = 0; i < this.allWords.size(); i++){

            // Max logic, also check that this word has not been solved so far.
            if(this.allWords.get(i).getWordLength() > max && !solvedWordsIndex.contains(i)){
                max = this.allWords.get(i).getWordLength();

                // word at this index will be solved now.
                this.bigWordIndex = i;
            }
        }
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

    /**
     * Marks the words, that have been solved.
     * This happens at each feedback.
     * This is because, smaller words, might accidently, get guessed, while solving bigger words.
     * or vice versa, bigger words, might be solved, solving 2 smaller words
     */
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


    // 18-10-2020 - Yogeshwar Chaudhari - Needed for Testing
    public ArrayList<DictAwareSolver> getAllWords() {
        return allWords;
    }

    public void setAllWords(ArrayList<DictAwareSolver> allWords) {
        this.allWords = allWords;
    }

    public ArrayList<Integer> getSolvedWordsIndex() {
        return solvedWordsIndex;
    }

    public void setSolvedWordsIndex(ArrayList<Integer> solvedWordsIndex) {
        this.solvedWordsIndex = solvedWordsIndex;
    }

    public Set<Character> getGuessedChars() {
        return guessedChars;
    }

    public void setGuessedChars(Set<Character> guessedChars) {
        this.guessedChars = guessedChars;
    }

    // 18-10-2020 - Getter setter ends here

} // end of class WheelOfFortuneGuessSolver
