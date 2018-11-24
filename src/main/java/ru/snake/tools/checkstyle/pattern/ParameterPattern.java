package ru.snake.tools.checkstyle.pattern;

import java.util.Map;

public class ParameterPattern implements Pattern {

	private final String parameter;

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
