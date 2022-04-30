package data;

public class StringChecker {

	public static boolean isEmpty(String input) {
		return input == null || input.isEmpty() || input.isBlank();
	}
}
