package ru.snake.tools.checkstyle.pattern;

/**
 * Match result. Contains result of matching some string with {@link Pattern}
 * expression.
 *
 * If of {@link Match#isSuccess} is true - the string is contains pattern. Field
 * {@link Match#length} will contain length of matched line part.
 *
 * If of {@link Match#isSuccess} is false - the string is not contains pattern.
 * Field {@link Match#length} will contain -1. Field {@link Match#message} will
 * contain error message.
 *
 * @author snake
 *
 */
public class Match {

	public final boolean isSuccess;

	public final int length;

	public final String message;

	/**
	 * Create new instance of {@link Match} with success result.
	 *
	 * @param length
	 *            pattern length
	 */
	public static Match success(int length) {
		return new Match(true, length, null);
	}

	/**
	 * Create new instance of {@link Match} with fail result.
	 *
	 * @param message
	 *            error message
	 */
	public static Match fail(String message) {
		return new Match(false, -1, message);
	}

	/**
	 * Create new initialize instance of {@link Match}.
	 *
	 * @param isMatch
	 *            is pattern matches
	 * @param length
	 *            pattern length
	 * @param message
	 *            error message
	 */
	private Match(boolean isMatch, int length, String message) {
		this.isSuccess = isMatch;
		this.length = length;
		this.message = message;
	}

	@Override
	public String toString() {
		return "Match [isMatch=" + isSuccess + ", length=" + length + ", message=" + message + "]";
	}

}
