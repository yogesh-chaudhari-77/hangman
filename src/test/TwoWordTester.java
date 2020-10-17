package test;

import solver.DictAwareSolver;
import solver.HangmanSolver;
import solver.TwoWordHangmanGuessSolver;
import trace.HangmanGameTracer;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class TwoWordTester {

    public static void main(String [] args) throws IOException {

        // solver type
        String solverType = "twoword";

        // dictionary filename
        String dictionaryFilename = System.getProperty("user.dir")+"/src/ausDict.txt";

        int maxIncorrectGuesses = 3;

        // (optional) game trace filename
        String gameTraceFilename = null;


        Set<String> dictionary = loadDictionary(dictionaryFilename);

        String [] dictionaryArr = new String[dictionary.size()];
        dictionary.toArray(dictionaryArr);

        FileWriter csvWriter = new FileWriter(System.getProperty("user.dir")+"/src/test/results/twoWordTestResults.csv");
        csvWriter.append("Word,GuesseAllowed,Result,WordsLeftInSampleSet_word_1,WordsLeftInSampleSet_word_2,SequenceOfGuessedLetters\n");

        // Perform test for each word
        // for(String firstWord : dictionary){
        for(int i = 0; i < dictionaryArr.length; i++){

            //for(String secondWord : dictionary){
            for(int j = i+1; j < dictionaryArr.length; j++){

                PrintWriter outWriter = null;
                PrintWriter traceWriter = null;

                // construct in and output streams/writers/readers
                outWriter = new PrintWriter(System.out, true);

                // string ot guess (must be specified by double quotes "" on command line)
                String toGuess = dictionaryArr[i]+" "+dictionaryArr[j];

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

                if(!game.runGame(wordsToGuess, maxIncorrectGuesses, solver)){

                    String guessSequence = "";
                    for(Character c : ((TwoWordHangmanGuessSolver) solver).getGuessedChars()){
                        guessSequence += ""+c+" | ";
                    }

                    csvWriter.append(toGuess+","+maxIncorrectGuesses+","+"false"+","+((TwoWordHangmanGuessSolver) solver).getAllWords().get(0).getKnownWords().size()+","+((TwoWordHangmanGuessSolver) solver).getAllWords().get(1).getKnownWords().size()+","+guessSequence+"\n");
                }

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
