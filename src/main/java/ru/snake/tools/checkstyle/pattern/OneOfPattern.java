package ru.snake.tools.checkstyle.pattern;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ru.snake.tools.checkstyle.checks.MultiPatternHeaderCheck;

/**
 * On of pattern. This class internally used in {@link MultiPatternHeaderCheck}
 * to match fields with one of expected values.
 *
 * @author snake
 *
 */
public class OneOfPattern implements Pattern {

	private final String[] values;

	/**
	 * Create new pattern using list of values from parameter. Values are
	 * separated by | character.
	 *
	 * @param param
	 *            allowed values separated by |
	 */
	public OneOfPattern(String param) {
		this.values = param.split("\\|");

		Arrays.sort(this.values, (String a, String b) -> Integer.compare(b.length(), a.length()));
	}

	@Override
	public Match match(String line, int startIndex, Map<String, String> parameters) {
		if (startIndex < line.length()) {
			for (String value : this.values) {
				if (line.substring(startIndex).startsWith(value)) {
					return Match.success(value.length());
				}
			}
		}

		return Match.fail("Expected one of: " + Stream.of(this.values).collect(Collectors.joining(", ")));
	}

	@Override
	public String toString() {
		return "OneOfPattern [values=" + Arrays.toString(values) + "]";
	}

}
