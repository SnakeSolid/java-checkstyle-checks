package ru.snake.tools.checkstyle.pattern;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Patterns {

	private final List<List<Pattern>> patterns;

	private final Map<String, String> parameters;

	public Patterns(List<List<Pattern>> petterns) {
		this.parameters = new HashMap<>();
		this.patterns = petterns;
	}

	public void setParameter(String key, String value) {
		this.parameters.put(key, value);
	}

	/**
	 * Returns number of line convered with patterns.
	 *
	 * @return number of patterns
	 */
	public int size() {
		return this.patterns.size();
	}

	public MatchResult match(String line, int patternIndex) {
		int index = 0;

		for (Pattern pattern : this.patterns.get(patternIndex)) {
			Match match = pattern.match(line, index, this.parameters);

			if (match.isSuccess) {
				index += match.length;
			} else {
				return MatchResult.fail(index, match.message);
			}
		}

		if (index < line.length()) {
			return MatchResult.fail(index, "Expected end of line");
		}

		return MatchResult.success();
	}

	@Override
	public String toString() {
		return "Patterns [patterns=" + patterns + ", parameters=" + parameters + "]";
	}

}
