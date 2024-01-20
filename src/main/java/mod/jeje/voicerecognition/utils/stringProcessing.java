package mod.jeje.voicerecognition.utils;

import mod.jeje.voicerecognition.voskTest.voskTest;
import mod.jeje.voicerecognition.wordCounter.WordCounter;
import net.minecraft.server.MinecraftServer;

import java.util.*;

import static mod.jeje.voicerecognition.constants.N_MOST_WORDS;

public class stringProcessing {

    private static Random random = new Random();

    public static WordCounter wordPool = new WordCounter();

    static String[] wordsToOmit = {
            // Artículos
            "el", "la", "los", "las", "un", "una", "unos", "unas",
            // Pronombres
            "yo", "tÃº", "Ã©l", "ella", "nosotros", "vosotros", "ellos", "ellas",
            "me", "te", "se", "nos", "os", "los", "las", "mÃ\u00AD", "ti", "consigo", "conmigo", "contigo", "le",
            // Preposiciones
            "a", "de", "en", "con", "por", "para", "sin", "sobre", "entre", "hacia",
            // Conjunciones
            "y", "o", "pero", "aunque", "si", "porque", "cuando", "mientras", "como", "sino",
            // Palabras imprescindibles (limitadas a 2, no sé si agregar más)
            "sÃ\u00AD", "no"
    };
    static List<String> omittedWords = new ArrayList<>(List.of(wordsToOmit));

    public static void addWordsFromString(String recString){
        for (String str : recString.split(" ")){
            if (!omittedWords.contains(str) && !voskTest.bannedWords.contains(str)) {
                wordPool.addWord(str);
            }
        }
    }

    public static void banWord(String word){
        wordPool.removeWord(word);
        voskTest.bannedWords.add(word);
    }

    public static String getRandomWord(List<Map.Entry<String, Integer>> lst){
        //This function returns a random word from a list with the n-most used words.
        if (lst.isEmpty()){return null;}
        int rndIndex = random.nextInt(lst.size());
        String value = (lst != null && !lst.isEmpty()) ? lst.get(rndIndex).getKey() : null;

        return value;
    }

    public static void banRandomWord(){
        String word = getRandomWord(wordPool.getMostUsedWords(N_MOST_WORDS));
        if (word != null){banWord(word);}
    }
}
