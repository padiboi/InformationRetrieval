package InvertedIndex;

import java.util.LinkedList;

public class QueryHandler {
	public static boolean convertInfixToPostfix(String query) {
		// returns true if user entered a valid query, false otherwise

		LinkedList<Character> operands = new LinkedList<>();
		LinkedList<Character> operators = new LinkedList<>();

		for (int i = 0; i < query.length(); ++i) {
			char ch = query.charAt(i);
			if (Character.isLetter(ch)) {
				operands.add(ch);		
			} else if (ch == '&' || ch == '|') {
				operators.add(ch);
			} else if (ch == '!') {
				char top = operators.peek();
				if (top != '!') {
					operators.push(ch);
				}
			} else {
				// invalid character
				return false;
			}
		}
		return true;
	}
}