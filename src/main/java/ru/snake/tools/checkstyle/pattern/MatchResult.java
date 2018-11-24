package ru.snake.tools.checkstyle.pattern;

/**
 * Result of matching single line with {@link Patterns}. If line matched with
 * success field {@link MatchResult#isSuccess} will be true.
 *
 * If match failed field {@link MatchResult#isSuccess} fill be false. Field
 * {@link MatchResult#errorIndex} will be set to first failed character
 * position. Field {@link MatchResult#errorMessage} will be set to error
 * description.
 *
 * @author snake
 *
 */
public class MatchResult {

	public final boolean isSuccess;

	public final int errorIndex;

	public final String errorMessage;

	/**
	 * Create new success match result object.
	 *
	 * @return new match result
	 */
	public static MatchResult success() {
		return new MatchResult(true, -1, null);
	}

	/**
	 * Create new fail match object. Fields {@link MatchResult#errorIndex} and
	 * {@link MatchResult#errorMessage} will be set to corresponding parameters.
	 *
	 * @param errorIndex
	 *            error index
	 * @param errorMessage
	 *            error message
	 * @return
	 */
	public static MatchResult fail(int errorIndex, String errorMessage) {
		return new MatchResult(false, errorIndex, errorMessage);
	}

	public MatchResult(boolean isSuccess, int errorIndex, String errorMessage) {
		this.isSuccess = isSuccess;
		this.errorIndex = errorIndex;
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "MatchResult [isSuccess=" + isSuccess + ", errorIndex=" + errorIndex + ", errorMessage=" + errorMessage
				+ "]";
	}

}
