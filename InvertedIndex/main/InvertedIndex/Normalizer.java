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

	public static String normalizeQuery(String term) {
		StopWords stopWords = new StopWords();
		String normalizedTerm = "";
		for (int i = 0; i < term.length(); ++i) {
			char ch = term.charAt(i);
			// assumes that query does not contain operator character within operand token
			if (!Character.isLetterOrDigit(ch) && ch != '&' && ch != '!' && ch != '|') {
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