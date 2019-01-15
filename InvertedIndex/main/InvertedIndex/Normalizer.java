package InvertedIndex;

public class Normalizer {
	public static String normalize(String term) {
		StopWords stopWords = new StopWords();
		String normalizedTerm = "";
		for (int i = 0; i < term.length(); ++i) {
			char ch = term.charAt(i);
			if (!Character.isLetterOrDigit(ch)) {
				continue;
			}
			normalizedTerm += Character.toLowerCase(ch);
		}
		if (stopWords.shouldInclude(normalizedTerm)) {
			return normalizedTerm;
		}
		return null;
	}
}