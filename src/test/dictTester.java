package test;

import game.HangmanGame;
import solver.DictAwareSolver;
import solver.HangmanSolver;
import trace.HangmanGameTracer;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class dictTester {

    public static void main(String [] args) throws IOException {

        // solver type
        String solverType = "dict";

        // dictionary filename
        String dictionaryFilename = System.getProperty("user.dir")+"/src/ausDict.txt";

        int maxIncorrectGuesses = 4;

        Set<String> dictionary = loadDictionary(dictionaryFilename);

        FileWriter csvWriter = new FileWriter(System.getProperty("user.dir")+"/src/test/results/dictTestResults.csv");
        csvWriter.append("Word,GuesseAllowed,Result,WordsLeftInSampleSet,SequenceOfGuessedLetters\n");
        // Perform test for each word


        //for(String word : dictionary){
        for(String word : specialSet()) {

            PrintWriter outWriter = null;
            PrintWriter traceWriter = null;

            // construct in and output streams/writers/readers
            outWriter = new PrintWriter(System.out, true);

            // string ot guess (must be specified by double quotes "" on command line)
            String toGuess = word;

            // check if words are in dictionary
            String[] wordsToGuess = toGuess.split(" ");

            String gameTraceFilename = "NachiketWords.txt";
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
            solver = new DictAwareSolver(dictionary);

            if(!game.runGame(wordsToGuess, maxIncorrectGuesses, solver)){

                String guessSequence = "";
                for(Character c : ((DictAwareSolver) solver).getGuessedChars()){
                    guessSequence += ""+c+" | ";
                }

                csvWriter.append(word+","+maxIncorrectGuesses+","+"false"+","+((DictAwareSolver) solver).getKnownWords().size()+","+guessSequence+"\n");
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


    public static String [] specialSet(){
        //String [] failedWord = new String[] {"mynah's", "outhumouring", "gour", "behove", "tabourer", "trets", "polymerised", "arvos", "billabong", "lyricising", "cottonise", "laevo", "poetling", "harbourful", "grey", "hure", "vaporising", "touret", "appetise", "haematin", "sanger", "coetus", "roumania", "carking", "paviour", "ploughboys", "womanisers", "appetising", "humanisations", "armoured", "yoghurt's", "mainour", "louvres", "galahs", "synonymises", "vialling", "solutise", "monetise", "sheikh", "symphonised", "routinising", "yodeller's", "policiser", "humanisation's", "sapour", "briar's", "humaniser's", "baptising", "armour's", "fervour", "decolourises", "decolourised", "totaller", "gemmology's", "humaniser", "tumbril's", "succourer", "tabouring", "pyritised", "pyritises", "polonise", "frivolling", "sabre", "gybe", "mise", "quatres", "soultre", "underprising", "howzat", "bushie", "optimise", "mechanise", "plough", "pelletising", "pandoura", "roughy", "colourant", "wobbegong's", "carolled", "anaesthyl", "westie", "vapourish", "vaporise", "aphorise", "louvre", "optimising", "hominised", "phoney", "tinselled", "pedalling", "magnetises", "woollen's", "vapourific", "mandore", "moult", "venalise", "metalling", "ochre", "taegu", "ochrey", "syntonise", "sombre", "gelofre", "typhaemia", "nounise", "miaowing", "sulphides", "ute", "fuelling", "womanised", "threap", "nought", "woop", "coralled", "clamouring", "appetises", "centred", "centrer", "jacobinised", "jacobinises", "papulae", "paedology", "rhythmises", "totalised", "totaliser", "totalises", "tantalises", "pythonise", "wantonise", "hypnotises", "coelom's", "soury", "jackaroo", "vires", "mylonitise", "mums", "tinnies", "uraemias", "arvo", "peroxidise", "parlourmaid", "blowy's", "hypogaeum", "hydrolise", "croc's", "summarises", "cuvae", "favouring", "looed", "deadrise", "totalise", "initialling", "skilful", "zoogloea", "vacuumise", "waer", "gre", "chocolatey", "tantalise", "sulphur", "ire", "sulphurising", "pyrolysing", "gynae", "saeculums", "lupins", "woollenise", "hirselled", "napaea", "regulise", "among", "vaagmaer", "muddie's", "faradising", "synonymise", "vapouring", "preapprising", "macarising", "poe", "taes", "briars", "cancelled", "cymae", "melodises", "soe", "kylie's", "ochres", "bandanna's", "tumoured", "deputises", "bogans", "victualling", "yabby's", "totalising", "dualise", "appetised", "tachypnoea", "hanky", "papalising", "haemin", "nuggety", "sodomises", "sandshoe", "tartarise", "mechanised", "mechanises", "vampirising", "faggoting", "sabbatise", "chequing", "marmarises", "colouring", "tittupped", "tabouret", "taboured", "deputise", "jacobinise", "fulfilment", "pummelling", "arabicises", "cairns", "photolyse", "pummelled", "communises", "sororising", "becolour", "pulverising", "dunnies", "oecoid", "loury", "sabbatising", "chaetopod", "phaeophyl", "cocainises", "goura", "cupeller's", "cosy", "pupilise", "hoveller", "mynahes", "weftwise", "palaeology", "savagise", "bren", "subdure", "hepatise", "fayre", "maccabaean", "pyramidise", "pommelled", "barramundi", "matt", "rivalled", "draughtily", "tittupping", "candour", "rorts", "behoving", "autoecisms", "jazz", "cupelled", "mynah", "muddie", "morulae", "saki", "malodour", "gimballing", "cupelling", "skite", "youse", "sulphide", "gruelled", "botanised", "faggoted", "miaowed", "cephalisation's", "hypogaea", "hooning", "drongo's", "tyre", "bonderise", "patinaed", "okaying", "pommelling", "maximising", "puppetise", "humanises", "aerical", "gemmology", "hydraemia", "zoaea", "cephalisations", "whyalla", "vigour", "titbit's", "chilli's", "yabby", "vire", "notogaea", "zac's", "vigours", "redback", "zooea", "summariser", "summarised", "gambolled", "amoebous", "humanise", "gimballed", "gluttonise", "neologise", "fecundise", "autoecism", "cocainise", "mythising", "ptyalised", "machinise", "cyclising", "bundy's", "communise", "crozier", "tinny's", "woollen", "whizz's", "lupin's", "colourised", "cithren", "compoer", "updraught", "canalling", "trialling", "fulfils", "inquiring", "kaftans", "pyjamas", "curarising", "gutfuls", "titbits", "guttae", "ptyalise", "ising", "dunny", "spilt", "drily", "pilly", "mum's", "whizz", "lupin", "whirr", "lilly", "chilli", "fulfil", "brumby", "gutful", "furphy", "wilful", "kaftan", "pyjama", "titbit", "bunyip", "dinkum", "wilfully", "bingling", "bicolour", "bundying", "moulding", "tittuppy", "rhythmising", "anonymised", "communised"};
        String [] failedWord = new String[] {"among", "chequing", "labour", "organise", "organises", "saki", "cancelled", "cancelling", "colouring", "cosy", "dialling", "fibre", "fulfil", "fulfils", "honouring", "inquiring", "maximise", "modelling", "mould", "optimise", "optimising", "summarised", "summarises", "summarising", "tyre", "oking", "aeon's", "aftie's", "afties", "agonise", "aluminium", "ambo", "ambo's", "ambos", "arbour", "arbour's", "arbours", "bandanna's", "baptising", "baulk's", "baulking", "biffo", "biffo's", "biffos", "billabong", "bingled", "bingles", "bingling", "blowy's", "bluey", "bogan", "bogans", "bonza", "brumby", "bundy", "bundying", "bunyip", "bunyips", "bushie", "candour", "cark", "carking", "carks", "carolled", "chilli", "chilli's", "clamour", "clamouring", "colonise", "colonised", "colonises", "colonising", "coolabah's", "cossie", "croc", "deodorising", "dinkum", "dobber", "dobbers", "drily", "duelling", "dunnies", "dunny", "fogey", "fogey's", "fulfilment", "funnelled", "furphy", "galah", "galah's", "galahs", "gutful", "gutfuls", "gybed", "gybing", "harbour", "hippy's", "hoon", "hooning", "howzat", "humanise", "humanised", "humanises", "humoured", "initialled", "initialling", "jackaroo", "jumbuck", "kanga's", "kelpie", "kerbing", "kylie", "labouring", "labours", "legalises", "lilly", "lupin", "lupin's", "lupins", "matt", "matts", "maximised", "maximises", "maximising", "mechanise", "mechanised", "mechanises", "memorised", "miaowing", "mobilising", "moulding", "moulds", "mouldy", "moulting", "muddie", "muddie's", "mum's", "mums", "nought", "nulla", "okaying", "pedalling", "perilled", "pillies", "pilly", "plough", "ploughs", "pokie", "pollie", "pulverising", "pummelled", "pummelling", "pyjamas", "rivalling", "rort's", "rorts", "sabre", "sheikh", "shrivelled", "skite", "sombre", "spilt", "succouring", "symbolised", "tantalise", "tantalised", "tinny's", "titbit", "titbit's", "titbits", "totalling", "trialling", "ute", "utes", "vigour", "vigour's", "whirr", "whirr's", "whirrs", "whizz", "whizz's", "wilful", "wilfully", "woollen", "woollen's", "yabbied", "yabby", "yabbying", "yodelling", "youse", "zac's", "zack", "zacks", "adaptors", "bevelling", "hanky's", "behove", "behoving", "briar", "briar's", "briars", "burqa's", "catechise", "cavilling", "crozier", "dehumanises", "demobilise", "deputise", "dowelling", "gambolled", "gambolling", "goitre", "hybridised", "hybridises", "hybridising", "kaftan", "kaftans", "monetise", "mynahes", "ochre", "pommelled", "pommelling", "rowelling", "updraught", "verbalises", "victualling", "womanise", "womanised", "womanises", "carbonise", "manilla", "modeller's", "modellers", "moggie", "pyjama", "sodomising", "barbarise", "barbarised", "barbarises", "barbarising", "canceller", "canceller's", "cancellers", "downdraught", "faggoting", "fayre", "gemmology", "gemmology's", "recolour", "tunneller", "bael", "bicolour", "bise", "bistre", "caestus", "cephalisation", "cocainise", "cocainises", "cocainising", "coeliac", "coelom", "colourado", "communised", "cymae", "doura", "fibred", "flavoury", "frivolling", "geologise", "glutaeus", "goey", "gre", "gynaecoid", "haematic", "indraught", "loury", "maculae", "maximiser", "melodises", "neologise", "ochres", "paedology", "palaeolith", "pandoura", "parlourmaid", "ruralise", "sakis", "summariser", "symphonise", "synonymise", "tabouret", "totalising", "vapourific", "vapourish", "vire", "zoogloea", "agenise", "agnised", "agnises", "althaeas", "anonymised", "anvilling", "apogaeic", "arboured", "bourd", "bowelled", "bowelling", "bren", "canaller", "canalling", "cantonise", "cantonised", "cantonises", "channeller", "cithren", "coelom's", "colourman", "compoer", "cottae", "cudgellers", "cupelling", "cyanise", "cyanised", "cyanises", "cyanising", "cyclise", "cyclised", "cyclises", "cyclising", "deaminise", "dognapped", "dognapping", "drek", "dualise", "dualises", "eunuchising", "fae", "faggoted", "formulises", "fouldre", "gaed", "galvanisers", "gargarise", "gargarises", "gaufres", "genialise", "gimballed", "gimballing", "glycerines", "gruelled", "guttae", "haed", "haematozoic", "haviour", "hebraised", "hepatise", "hepatised", "hepatises", "herborising", "hydraemia", "hypogaea", "hypopnoea", "looed", "maaed", "mainour", "medalling", "moellon", "morselling", "morulae", "mythising", "nodalise", "nodalises", "nominalise", "ochring", "odourful", "oecophobia", "ourology", "patinaed", "peptonising", "photolyse", "photolysed", "pigmaean", "polliniser", "polonising", "powellising", "protocolling", "ptyalised", "ptyalises", "ptyalising", "pyjama's", "regulise", "rhythmise", "rhythmises", "rhythmising", "ruralises", "ruralising", "sabbatise", "sabbatised", "sabbatises", "sabbatising", "sabring", "saeculums", "synonymises", "taboured", "tabourer", "tabouring", "tachypnoea", "taegu", "taes", "taffetised", "tittupped", "tittupping", "tittuppy", "totalise", "totalised", "unlabouring", "vampirised", "vampirises", "vampirising", "vigours", "weftwise", "zaffres", "zooecium", "academise", "aemuled", "aemuling", "aerical", "agoniser", "amoebian", "archaei", "balladised", "becolour", "bittour", "bonderise", "bourds", "brisa", "caffre", "chromicise", "coaliser", "coe", "coeloma", "coenoby", "colourin", "commonise", "countour", "cre", "cudgeller's", "cuvae", "deadrise", "dialling's", "fecundise", "fribourg", "gelofre", "goddise", "goura", "haematoidin", "haemopod", "hamletise", "hydrolise", "hypogaeic", "indraught's", "invigour", "ising", "machinise", "mandore", "meagrer", "modalise", "moeck", "nakedise", "napaea", "oecoid", "oogloea", "outlabour", "palaeology", "palaeozoic", "phaeophyl", "phloeum", "poetling", "pommae", "poudre", "poudres", "pupilise", "puppetise", "pyramidise", "quae", "savagise", "soultre", "symphonised", "templise", "totaller", "unidolised", "unsombre", "vaagmaer", "vacuumise", "woollenise", "zonaesthesia", "cairns", "whyalla", "maccabaean", "neogaea", "notogaea", "arabicises", "idaea", "idumaean", "jacobinise", "jacobinised", "jacobinises", "jacobinising", "phocaean", "sanforise", "goes" };
        return failedWord;
    }
}
