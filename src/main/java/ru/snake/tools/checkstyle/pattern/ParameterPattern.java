package ru.snake.tools.checkstyle.pattern;

import java.util.Map;

import ru.snake.tools.checkstyle.checks.MultiPatternHeaderCheck;

/**
 * Parameter pattern. This class internally used in
 * {@link MultiPatternHeaderCheck} to match field with file name.
 *
 * @author snake
 *
 */
public class ParameterPattern implements Pattern {

	private final String parameter;

	/**
	 * Create new instance of parameter pattern matcher.
	 *
	 * @param parameter
	 *            parameter name to use
	 */
	public ParameterPattern(String parameter) {
		this.parameter = parameter;
	}

	@Override
	public Match match(String line, int startIndex, Map<String, String> parameters) {
		String value = parameters.get(this.parameter);

		if (value == null) {
			return Match.fail("Parameter " + this.parameter + " not found");
		}

		if (startIndex < line.length()) {
			if (line.substring(startIndex).startsWith(value)) {
				return Match.success(value.length());
			}
		}

		return Match.fail("Expected " + value);
	}

	@Override
	public String toString() {
		return "ParameterPattern [parameter=" + parameter + "]";
	}

}
