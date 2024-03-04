package services;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.LevenshteinDistance;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class FilterService {
    private static final List<String> profaneWords = Arrays.asList("bitch", "hitler", "fuck");

    private static final Soundex soundex = new Soundex();
    private static final LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

    public static String filterProfanity(String text) {
        String[] words = text.split("\\s+");
        StringBuilder filteredText = new StringBuilder();

        for (String word : words) {
<<<<<<< Updated upstream
<<<<<<< Updated upstream
            boolean isProfane = false;
            for (String profaneWord : profaneWords) {
                if (isSimilar(word, profaneWord)) {
                    isProfane = true;
                    break;
                }
            }
            if (isProfane) {
=======
            if (isProfane(word)) {
>>>>>>> Stashed changes
=======
            if (isProfane(word)) {
>>>>>>> Stashed changes
                filteredText.append("*".repeat(word.length())).append(" ");
            } else {
                filteredText.append(word).append(" ");
            }
        }

        return filteredText.toString().trim();
    }

<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
=======
>>>>>>> Stashed changes
    private static boolean isProfane(String word) {
        for (String profaneWord : profaneWords) {
            if (isSimilar(word, profaneWord)) {
                return true;
            }
        }
        return false;
    }

<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
    private static boolean isSimilar(String word1, String word2) {
        return soundex.encode(word1).equals(soundex.encode(word2))
                || levenshteinDistance.apply(word1, word2) <= 2; // Adjust the threshold as needed
    }


    public static boolean containsBadWord(String text) {
        if (text.contains("*")){
            return true;
        }
        return false;
    }

}
