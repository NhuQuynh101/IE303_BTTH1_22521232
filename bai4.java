import java.io.*;
import java.util.*;

public class bai4 {
    private static final Map<String, Integer> wordCounts = new HashMap<>();
    private static final Map<String, Map<String, Integer>> bigramCounts = new HashMap<>();
    private static int totalWords = 0;  
    private static int totalFilteredWords = 0; 

    public static void main(String[] args) throws IOException {
        String filePath = "UIT-ViOCD.txt";
        loadCorpus(filePath);

        System.out.println("Tổng số từ trong tập dữ liệu (bao gồm trùng lặp): " + totalWords);
        System.out.println("Tổng số từ sau khi loại bỏ các từ xuất hiện dưới 5 lần: " + totalFilteredWords);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Nhập từ bắt đầu: ");
            String startWord = scanner.nextLine().toLowerCase();
            startWord = cleanWord(startWord);

            if (!wordCounts.containsKey(startWord)) {
                System.out.println("Từ này không có trong tập từ vựng! Hãy nhập từ khác.");
                return;
            }

            List<String> generatedSentence = generateSentence(startWord, 5);
            System.out.println("Câu tạo sinh: " + String.join(" ", generatedSentence));
        }
    }

    private static void loadCorpus(String filePath) throws IOException {
        Map<String, Integer> tempWordCounts = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.toLowerCase().split("\\s+");
                for (String word : words) {
                    word = cleanWord(word);
                    if (!word.isEmpty()) {
                        tempWordCounts.put(word, tempWordCounts.getOrDefault(word, 0) + 1);
                        totalWords++; 
                    }
                }
            }
        }

        for (Map.Entry<String, Integer> entry : tempWordCounts.entrySet()) {
            if (entry.getValue() >= 5) {
                wordCounts.put(entry.getKey(), entry.getValue());
                totalFilteredWords += entry.getValue(); 
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.toLowerCase().split("\\s+");
                for (int i = 0; i < words.length - 1; i++) {
                    String prevWord = cleanWord(words[i]);
                    String word = cleanWord(words[i + 1]);

                    if (!wordCounts.containsKey(prevWord) || !wordCounts.containsKey(word)) continue;

                    bigramCounts.putIfAbsent(prevWord, new HashMap<>());
                    bigramCounts.get(prevWord).put(word, bigramCounts.get(prevWord).getOrDefault(word, 0) + 1);
                }
            }
        }
    }

    private static String cleanWord(String word) {
        return word.replaceAll("[^a-zA-Zàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ]", "");
    }

    private static List<String> generateSentence(String startWord, int maxWords) {
        List<String> sentence = new ArrayList<>();
        sentence.add(startWord);

        String currentWord = startWord;
        for (int i = 1; i < maxWords; i++) {
            if (!bigramCounts.containsKey(currentWord) || bigramCounts.get(currentWord).isEmpty()) {
                break;
            }
            String nextWord = getNextWord(currentWord);
            if (nextWord == null) break;
            sentence.add(nextWord);
            currentWord = nextWord;
        }

        return sentence;
    }

    private static String getNextWord(String word) {
        if (!bigramCounts.containsKey(word)) return null;

        Map<String, Integer> nextWords = bigramCounts.get(word);
        int wordCount = wordCounts.getOrDefault(word, 1);

        double maxProbability = -1.0;
        String bestWord = null;

        for (Map.Entry<String, Integer> entry : nextWords.entrySet()) {
            double probability = (double) entry.getValue() / wordCount;
            if (probability > maxProbability) {
                maxProbability = probability;
                bestWord = entry.getKey();
            }
        }
        return bestWord;
    }
}
