package ru.snake.tools.checkstyle.pattern;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OneOfPattern implements Pattern {

	private final String[] values;

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
