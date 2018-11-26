package ru.snake.tools.checkstyle.pattern;

import java.text.ParsePosition;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

import ru.snake.tools.checkstyle.checks.MultiPatternHeaderCheck;

/**
 * Date format pattern. This class internally used in
 * {@link MultiPatternHeaderCheck} to check fields marked as date.
 *
 * @author snake
 *
 */
public class DatePattern implements Pattern {

	private final String pattern;

	private final DateTimeFormatter format;

	/**
	 * Create new date pattern using given date format.
	 *
	 * @param pattern
	 *            date format pattern
	 */
	public DatePattern(String pattern) {
		this.pattern = pattern;
		this.format = DateTimeFormatter.ofPattern(pattern);
	}

	@Override
	public Match match(String line, int startIndex, Map<String, String> parameters) {
		if (startIndex < line.length()) {
			ParsePosition position = new ParsePosition(startIndex);

			try {
				this.format.parse(line, position);

				if (position.getErrorIndex() == -1) {
					return Match.success(position.getIndex() - startIndex);
				}
			} catch (DateTimeParseException e) {
				return Match.fail("Expected date in format - " + this.pattern);
			}
		}

		return Match.fail("Expected date in format - " + this.pattern);
	}

	@Override
	public String toString() {
		return "DatePattern [pattern=" + pattern + ", format=" + format + "]";
	}

}
