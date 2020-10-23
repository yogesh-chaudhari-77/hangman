package test;

import solver.DictAwareSolver;
import solver.HangmanSolver;
import solver.TwoWordHangmanGuessSolver;
import trace.HangmanGameTracer;

import java.io.*;
import java.util.*;

/**
 * Tester class implementation for running random test cases for twoword hangman
 * It requires the dictionary, it draws combination of 2 words from the dictionary.
 */
public class TwoWordTester {

    public static void main(String [] args) throws IOException {

        Random randomUtil = new Random();

        // solver type
        String solverType = "twoword";

        // dictionary filename
        String dictionaryFilename = System.getProperty("user.dir")+"/src/ausDict.txt";

        int maxIncorrectGuesses = 7;

        // (optional) game trace filename
        String gameTraceFilename = null;


        Set<String> dictionary = loadDictionary(dictionaryFilename);

        String [] dictionaryArr = new String[dictionary.size()];
        dictionary.toArray(dictionaryArr);

        FileWriter csvWriter = new FileWriter(System.getProperty("user.dir")+"/src/test/results/twoWordTestResults_7Guesses12k_Words_2.csv");
        csvWriter.append("Sr,Word,GuesseAllowed,Result,WordsLeftInSampleSet,SequenceOfGuessedLetters\n");

        int z = 1;
        // Perform test for each word
        // for(String firstWord : dictionary){
        for(int i = 0; i < 25000; i++){

            //for(String secondWord : dictionary){
            //for(int j = i+1; j < dictionaryArr.length; j++)
            {

                PrintWriter outWriter = null;
                PrintWriter traceWriter = null;

                // construct in and output streams/writers/readers
                outWriter = new PrintWriter(System.out, true);

                // Form a random word
                int firstWordIndex = randomUtil.nextInt((dictionaryArr.length - 10));
                int lastWordIndex = randomUtil.nextInt((dictionaryArr.length - 10));

                // string ot guess (must be specified by double quotes "" on command line)
                String toGuess = dictionaryArr[firstWordIndex]+" "+dictionaryArr[lastWordIndex];

                // check if words are in dictionary
                String[] wordsToGuess = toGuess.split(" ");

                // tracer/logger
                // open file first

                if (gameTraceFilename != null) {
                    File traceFile = new File(gameTraceFilename);
                    // append mode and auto-flush
                    traceWriter = new PrintWriter(new FileWriter(traceFile, true), true);
                }
                // create tracer
                HangmanGameTracer tracer = new HangmanGameTracer(traceWriter);

                HangmanGameTester game = new HangmanGameTester(outWriter, tracer);;

                HangmanSolver solver = null;
                solver = new TwoWordHangmanGuessSolver(dictionary);

                boolean result = game.runGame(wordsToGuess, maxIncorrectGuesses, solver);
                {
                    String guessSequence = "";
                    for(Character c : ((TwoWordHangmanGuessSolver) solver).getGuessedChars()){
                        guessSequence += ""+c+" | ";
                    }
                    // Removing the last |
                    guessSequence = guessSequence.substring(0, guessSequence.length()-1);

                    csvWriter.append(z+","+toGuess+","+maxIncorrectGuesses+","+result+","+((TwoWordHangmanGuessSolver) solver).getAllWords().get(0).getKnownWords().size()+","+((TwoWordHangmanGuessSolver) solver).getAllWords().get(1).getKnownWords().size()+","+guessSequence+"\n");
                }

                z++;
            }

        }
    }



    /**
     * Load dictionary.
     *
     * @param dictionaryFilename Filename of dictionary to load.
     *
     * @throws FileNotFoundException If filename not found.
     */
    public static Set<String> loadDictionary(String dictionaryFilename)
            throws FileNotFoundException
    {
        //
        // Construct dictionary
        //
        Set<String> dictionary = new HashSet<String>();

        Scanner scanner = null;
        try {
            File dictFile = new File(dictionaryFilename);
            scanner = new Scanner(dictFile);

            while (scanner.hasNextLine()) {
                String sLine = scanner.nextLine().trim();
                dictionary.add(sLine);
            }
        }
        finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return dictionary;
    } // end of loadDictionary()

}
