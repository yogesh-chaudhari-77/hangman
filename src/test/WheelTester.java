package test;

import solver.DictAwareSolver;
import solver.HangmanSolver;
import solver.WheelOfFortuneGuessSolver;
import trace.HangmanGameTracer;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Tester class implementation for running wheel of fortune test case running.
 * It requires the list of phrases to be present in src folder
 */
public class WheelTester {
    public static void main(String [] args) throws IOException {

        // solver type
        String solverType = "wheel";

        // dictionary filename
        //String dictionaryFilename = System.getProperty("user.dir")+"/src/ausDict.txt";
        String dictionaryFilename = System.getProperty("user.dir")+"/src/phrase_words_dict.txt";
        String phrasesFilename = System.getProperty("user.dir")+"/src/phrases_dataset.txt";

        int maxIncorrectGuesses = 7;

        Set<String> dictionary = loadDictionary(dictionaryFilename);
        Set<String> phrases = loadDictionary(phrasesFilename);

        FileWriter csvWriter = new FileWriter(System.getProperty("user.dir")+"/src/test/results/WheelTestResults_7Guesses_8kwords10kPhrases.csv");
        csvWriter.append("Sr,Phrase,GuesseAllowed,Result,SequenceOfGuessedLetters,SolvedWordIndexes,RemaingWordsAfterFiltering#LengthOfWord\n");
        // Perform test for each word

        int z = 0;

        for(String phrase : phrases){
            //for(String word : specialSet()) {

            PrintWriter outWriter = null;
            PrintWriter traceWriter = null;

            // construct in and output streams/writers/readers
            outWriter = new PrintWriter(System.out, true);

            // string ot guess (must be specified by double quotes "" on command line)
            String toGuess = phrase;

            // check if words are in dictionary
            String[] wordsToGuess = toGuess.split(" ");

            String gameTraceFilename = "JeffTestWords.txt";
            // tracer/logger
            // open file first

            if (gameTraceFilename != null) {
                File traceFile = new File(gameTraceFilename);
                // append mode and auto-flush
                traceWriter = new PrintWriter(new FileWriter(traceFile, true), true);
            }
            // create tracer
            HangmanGameTracer tracer = new HangmanGameTracer(traceWriter);

            HangmanGameTester game = new HangmanGameTester(outWriter, tracer);

            HangmanSolver solver = null;
            solver = new WheelOfFortuneGuessSolver(dictionary);

            boolean result = game.runGame(wordsToGuess, maxIncorrectGuesses, solver);
            {
                csvWriter.append(z + "," + phrase + "," + maxIncorrectGuesses + "," + result + ","+Arrays.asList(((WheelOfFortuneGuessSolver) solver).getGuessedChars())+",");
                csvWriter.append(Arrays.asList(((WheelOfFortuneGuessSolver) solver).getSolvedWordsIndex())+",");

                // Log Reduced sample set size
                for( DictAwareSolver s : ((WheelOfFortuneGuessSolver) solver).getAllWords() ){
                    csvWriter.append(s.getKnownWords().size()+"#"+s.getWordLength()+",");
                }
                csvWriter.append("\n");
            }

            z++;
            System.err.println("<====="+z+"=====>");
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
        String [] failedWord = new String[] {"bolshevised", "luteinises", "goloe", "lyophilising", "elegising", "chaptalisation", "bussed", "prelatise", "summariser's", "immobilisation's", "dekalitre", "hucksterise", "heroisation", "discolourments", "categorised", "volatilise", "mythologising", "trivialises", "catalogue's", "gynaecomastias", "hybridised", "metallisation", "arcticise", "hypercholesterolaemia", "baronise", "autotoxaemias", "favourable", "amenorrhoeal", "disorganising", "jackaroo's", "snorkelling", "ruggedises", "rancour's", "opalise", "unsummarisable", "oestriols", "rephosphorisation", "dichotomises", "enthronisation's", "haematins", "polymerising", "sooks", "computerisations", "sanforises", "dechristianises", "dekametre's", "ovalisation", "heroinise", "unsocialised", "vitalisation's", "antagonises", "tuberculisation", "patronisers", "remould", "ileocaecal", "linenise", "suberised", "panelled", "photosensitises", "cogniser's", "eupnoeic", "dentalised", "perdicinae", "unfavourableness", "manilla's", "visard", "jovialise", "sectarianise", "existentialise", "parasitises", "ritualises", "prelatises", "carnalise", "resynchronised", "preve", "laconised", "universalisation's", "glacialise", "vapouring", "unspecialised", "magnetise", "canalisations", "dealcoholise", "sunbake", "flyer's", "bastardisation", "sulphide", "anoestri", "palaetiology", "crenellated", "palaeoclimatologist", "lustreless", "unsympathisability", "rusticising", "polemicises", "centremost", "semiorganised", "cryptanalyses", "finalise", "cognise", "cicatrix", "barbarising", "formulise", "democratiser", "stercoraemia", "myrialitre", "crenellation's", "signalised", "semicarbonise", "sonantised", "synergised", "praedial", "stylised", "jasperise", "naturalises", "epilogised", "metropolitanise", "zoaea", "lustrewares", "recanalisation", "cancellous", "glamorisations", "normaliser", "yabber's", "neutraliser's", "neopaganise", "mesmerisable", "artificialising", "supernaturalises", "circularise", "geologising", "adverbialise", "focalises", "inquires", "dehumanisations", "blaise", "unpoetised", "demoralised", "pressuriser", "adulterises", "nesslerise", "misbaptise", "renormalisations", "generaliser", "governmentalised", "zeroise", "septicaemia's", "polymerise", "vernalisation's", "contemporised", "unpolarised", "hansardised", "revalorisation's", "caroller's", "clamour's", "religionises", "moult", "reauthorises", "organising", "memorialise", "trivialise", "townsville", "sclerotisation", "lavoltaed", "deoxygenises", "unneighbourlinesses", "recolouring", "coeloma", "obelised", "favouress", "geelong's", "attitudinises", "columnising", "epiloguises", "unstabilised", "demonetisations", "uneulogised", "psychologised", "demineraliser", "turdinae", "leukaemias", "baconise", "spanaemic", "machinise", "lyophilisations", "becudgelling", "spirochaetal", "harbourer's", "apogaeic", "anaesthesis", "journalised", "rationaliser's", "appetises", "diarised", "canonisers", "retinise", "sentinelling", "telaesthetic", "ionisation's", "manoeuvred", "inquiry's", "computerises", "expertised", "idumaean", "haematinics", "aec", "myxoedema", "favouritism's", "ghebre's", "pauperisation", "detribalisation's", "itemised", "canaller", "biffo's", "desulphurisers", "dragonise", "tyre", "mortalise", "armourers", "anarchise", "rematerialises", "sweepstake's", "sunbaker's", "legitimisations", "nodalise", "billabong", "nonfulfilment's", "policiser", "colouristically", "overgeneralising", "haemophile's", "owreword", "jacobaean", "rort", "lustreware", "unstoicise", "rigouristic", "nanisation", "quatres", "dogmatisers", "misrealise", "bluey's", "furore", "prelatised", "ozaena", "adulterise", "saltires", "focalisations", "paganiser's", "spectre's", "equalised", "formularisers", "unvitalised", "unilateralise", "glycerinise", "fossilled", "counsellor's", "recolouration", "rejuvenising", "uncaramelised", "epitomisation's", "recarbonise", "subcentre", "crenellations", "dunnies", "cortinae", "disauthorised", "fibrescopes", "cinchonisations", "unfeminising", "puppetise", "spermatorrhoeas", "prises", "sabred", "plasmolysed", "melithaemia", "frisette", "spheroidise", "enterocoele", "secularising", "disharmonised", "inquired", "phonemicisation", "militarises", "moulding's", "desulphuriser", "odourful", "vires", "lyophilises", "militarised", "eulogising", "indianise", "siliquae", "comprehensivising", "disharmonising", "labiatae", "trillionise", "galvaniser", "memorialisation", "palaeobotanically", "anagrammatises", "metagrabolise", "splenisations", "radicalise", "phaenologies", "steriliser's", "sapientise", "numbskull", "coenesthesia", "martyrises", "euhemerise", "epicoele", "republicaniser", "corinthianising", "legitimise", "shonk", "dramatisation", "unsensualising", "derecognising", "laevorotation", "reinitialising", "phagocytise", "autolyse", "unnationalised", "haemathermous", "hatchelled", "shrivelling", "masculinisations", "chiselling", "labouress", "sherardising", "tunnelled", "practised", "barramundi's", "phoney's", "syphilisations", "digitising", "dekametre", "snuffcoloured", "autotomise", "colloquised", "eudaemonic", "fragmentise", "neogaean", "autotomised", "spinulae", "harmonises", "canalise", "superspecialise", "preterites", "cyanising", "praescutum", "cudgellings", "harbourer", "synopsised", "absinth's", "bartisans", "disorganised", "mercerised", "lacklustre", "optimalisation", "kaftan's", "proindustrialisation", "mise", "mislabours", "commercialise", "dieselisation", "kindergarteners", "vulcanisation", "unmechanised", "palaeoanthropological", "granitises", "polariser", "academise", "eternisation", "sympathised", "inquiring", "disorganiser's", "westie's", "surgerise", "reroyalise", "endamoebic", "formalisations", "unsuccoured", "respectabilises", "titbit", "unantagonisable", "laevogyre", "legalises", "ornamentalise", "palletises", "gutturalises", "forejudgement's", "catalysing", "laicised", "digitalisations", "defenceman", "haematocyst's", "satiriser", "praetaxation", "preutilise", "gastraeas", "exorcises", "realising", "tinselled", "colourate", "automatised", "capitalising", "scandalisation", "communisations", "greenuk", "becudgelled", "gothicises", "succour's", "monasticise", "ferritisation", "tsouris", "hygienisation", "chloroformisation", "quantisations", "dieselise", "yoghurt", "deaconise", "botanises", "agnising", "epigrammatiser", "trichotomising", "reseiser", "anglicisations", "geologises", "axiomatisation's", "nominalised", "cadmiumise", "apotheosise", "incentres", "bonza", "objectivised", "succourful", "lemmatising", "neutralisation's", "modernises", "unitisations", "theatricalisations", "aromatitae", "harmonisations", "radialise", "haematologist's", "publicises", "cutisation", "parchmentised", "romanticise", "gourmandising", "metamerisation", "catholicising", "coenesthesias", "martialise", "molochise", "haemocoels", "praeanal", "pattae", "historicised", "terrae", "casualisations", "pictorialisation's", "martyrisations", "immortalisers", "experimentalised", "hypophysectomise", "transistorised", "aramaean", "woomera's", "pretences", "fantasising", "blowie's", "dehypnotisations", "aggrandisement's", "colouring's", "galvanisation", "bastinadoes", "cruelise", "immobiliser", "mitreworts", "paedophiliacs", "dialysation's", "sovietised", "tabularisations", "palaestra", "discolourated", "organiser's", "dimerises", "chequerboard's", "peise", "palletisation's", "athetises", "prioritisation's", "vapourlike", "remilitarisation", "uncivilised", "decolonisations", "equalisations", "tartarise", "draughtsmanships", "internalisations", "uncriticisably", "armouried", "easternise", "democratisation's", "protoarchaeology", "europeanised", "accoutrement", "amongst", "bingled", "lethargised", "reaexportation", "eunuchise", "initialisations", "reconnoitrer's", "detribalised", "privatiser", "coolibah", "apophthegmatise", "unpatronised", "liberalisations", "vowelisations", "oestrone's", "traitorise", "defeminisation", "civilisedness", "phoneticise", "isochronising", "oophorectomising", "enzedders", "symbolise", "germanisations", "ambo's", "schillerising", "idolises", "ebonised", "theatricise", "patrialisation", "diarrhoeas", "hasmonaean", "transistorises", "parrotise", "overhonour", "endeavour", "animalised", "emulsionised", "mitrer", "synchronisable", "revitalise", "subjectivisations", "fibreboard's", "italicisation", "gynaecomastia", "updraught", "areology", "launceston", "unhonourably", "suburbanisations", "optimisation", "preacute", "unionisation", "decolours", "carbonising", "enquiring", "technicising", "accoutred", "elaeoptene", "wentworth", "motorises", "civilianisation", "tetanise", "refertilisation", "idolise", "scrutinise", "poeticise", "ratitae", "diagonalising", "fictionalises", "cosiness's", "optimiser's", "hygienise", "trichinising", "tumours", "potentise", "decarburises", "resolemnise", "defence's", "grangeriser", "securitisations", "denaturalisation's", "dissyllabise", "bickie's", "libellants", "meagrest", "reoxygenise", "vapourers", "unauthorisedly", "journalisation", "circumcentres", "bolshevising", "araeometric", "rumourers", "nephrotomise", "haemocytes", "diabolise", "psalmodising", "enamellings", "phlyctaena", "cuckoldised", "allegorisation", "meningorrhoea", "haemangioma", "micromillimetre", "overcentralises", "emotionalisation", "breathalyser's", "paralysations", "trema", "radicalisations", "cryaesthesia", "fluidisers", "scrutinises", "nakedise", "dedramatises", "deionised", "galvaniser's", "repatronise", "cataloguised", "palaeoatavistic", "internalise", "pictorialises", "italicise", "tailorise", "fraena", "laicise", "empyreumatise", "sonnetisation", "sacerdotalising", "fossilisation's", "symmetrises", "capsulised", "newspaperised", "malodour", "haematocrit's", "watercolour", "unionised", "cottonise", "aeonian", "colourmen", "atomising", "mouldy", "crystallisers", "quarreller", "recolonisation", "bundies", "centring's", "particularisations", "infamise", "anoestrum", "parameterise", "uninitialised", "forejudgement", "larrikinisms", "herborised", "keratinises", "phenomenalises", "caramelisations", "zoogloea", "ghebres", "towelled", "araeosystyle", "decametre", "socialisation's", "empathises", "hypnoidises", "bacteraemia's", "sexualisations", "ambience's", "unpoeticised", "demagnetisers", "gynaecology", "denationalising", "voltise", "gormandised", "reutilises", "jillarooed", "annualises", "volatilised", "acre", "desacralise", "tyrannising", "hydrorrhoea", "exorcising", "underutilising", "notarisations", "labellers", "perichaete", "behaviourist's", "sulphurisation", "materialiser's", "profanise", "marmarised", "poetise", "tabourers", "dehydrogeniser", "valorising", "zack", "palaeoencephalon", "cryptaesthetic", "trowelled", "hypothetising", "bowdlerise", "categoriser", "calibre's", "hemihypoesthesia", "rapturising", "solecising", "hypnoidising", "epimerised", "bastinadoed", "nitroglycerine", "bussings", "thraldom", "manoeuvrable", "hypostatisations", "conveyorises", "anthoecology", "puritanised", "rhythmises", "uraemias", "derecognise", "orthogonalise", "miaows", "noncoloured", "propraetorian", "oestradiol", "reauthorise", "underemphasising", "pyrolyser", "synchronise", "russianises", "haemogram", "maternalising", "reprivatisations", "asafoetida", "overrapturise", "compartmentalising", "haematoblasts", "fibred", "suggestionises", "conchae", "paedogenic", "mesmerisation's", "mercurise", "unformalised", "gutturalised", "reutilise", "mythopoetise", "amoebocytes", "praelectorship", "reconnoitred", "macarise", "haemins", "generalised", "sulfatise", "newsagency", "liquidising", "apologise", "undemagnetisable", "methodisations", "devitalisations", "eudaemonisms", "oxygenising", "palaeobotany", "archaised", "ageings", "spires", "premixture", "apothegmatises", "decarbonisation's", "judgement's", "unicolour", "pedestrianisation's", "ruggedised", "glycogenise", "republicanisations", "abolitionising", "prologised", "ergotising", "telaesthesia", "anagrammatise", "dogmatises", "phenomenalising", "antisepticises", "dependants", "demagnetised", "polemises", "judgements", "preorally", "varicoloured", "marbleised", "legalisations", "sluggardised", "deoxygenised", "goe", "desensitisation", "skeletonisation", "enthrals", "vectorisations", "arabicises", "creolisation", "rumoured", "lethargising", "foutres", "rorted", "unvulgarised", "creatin", "lourings", "haemad", "areic", "fluoridised", "lignitise", "mylonitisations", "palaeocyclic", "colourer's", "laurelled", "finalises", "doura", "tabularise", "gorgonised", "diploae's", "delocalising", "frivolling", "internationalisation's", "phonemicise", "fulfil", "christianiser's", "phonemicises", "apologises", "centrepieces", "serialisation", "dorised", "vapourable", "oecophobia", "posturising", "paganiser", "neighbourhoods", "callisthenics", "papalises", "semihumanised", "cancellated", "oenolic", "rhoeadine", "peptisation", "unvulgarising", "digitised", "phosphatisation", "celestialise", "photolyses", "praetorship", "aggrandisers", "palaeopathology", "nonlocalised", "politicisation", "cholophaein", "coaliser", "microminiaturisations", "decarburising", "vapourer", "legalise", "haemometer", "sceptic's", "anthracitisation", "palaestrian", "labourer", "preapprise", "judaising", "squamulae", "frise", "eternalised", "disauthorising", "dialysed", "misdemeanour", "soliloquiser's", "hyphenising", "apnoeal", "metagrabolised", "aetiotropic", "pauperise", "overlabour", "internalising", "comprehensivises", "nationalises", "woollen's", "generalising", "theorising", "fractionalises", "honourableness's", "minimises", "disseisor", "unionising", "penalises", "temporalise", "vigours", "autocatalysing", "particulariser's", "litre", "shonky's", "laconise", "praecornu", "matronises", "vernalise", "winterises", "annualising", "oophorectomises", "precompound", "gelatinises", "anatomiser", "animalisation", "classicise", "monochordise", "chequers's", "realisations", "neopaganised", "coeliacs", "decentralises", "localiser's", "araeostyles", "westernisations", "palaeoencephalon's", "honouree's", "centrings", "palatalises", "haematozoa", "mechanisers", "preauthorise", "quantisers", "iridectomised", "desalinising", "philosophiser", "palaeoclimatologic", "illegalise", "jacobinised", "haemorrhage", "secularisers", "skilfulness", "nought", "favouringly", "haemoflagellate's", "jewelled", "trichinised", "uncolourable", "federalises", "harmonisable", "resymbolise", "boloed", "sceptic", "battologised", "graphitisation", "gorgonising", "jillaroo", "quininise", "villagisation", "dialysability", "monarchise", "conventionalises", "preconisation", "unnormalised", "alloxuraemia", "praetorians", "historicise", "carbonatisation", "develled", "unclericalise", "hebraisation", "philtres", "scripturalise", "hydroxylisation", "centrifugalise", "sermonise", "kerbs", "blowies", "reacclimatising", "snivelling", "ute's", "intraorganisation", "haed", "generalisers", "haemocyte's", "eroticised", "despiritualisation", "unsystematised", "palaeopathological", "dramatisable", "scenarising", "preact", "pseudoembryo", "metathesising"};
        return failedWord;
    }
}
