package InvertedIndex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class QueryInterface {
	public static void main(String[] args) throws IOException {
		System.out.println("Enter query: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String query = br.readLine();
		query = query.trim();
		// QueryHandler.scheduleOperations(query);
	}
}