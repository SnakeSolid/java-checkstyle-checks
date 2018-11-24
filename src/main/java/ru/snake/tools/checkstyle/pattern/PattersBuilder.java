package ru.snake.tools.checkstyle.pattern;

import java.util.ArrayList;
import java.util.List;

public class PattersBuilder {

	private final List<String> lines;

	public PattersBuilder(List<String> lines) {
		this.lines = lines;
	}

	public Patterns build() {
		List<List<Pattern>> patterns = new ArrayList<>(this.lines.size());

		for (String line : this.lines) {
			PatternParser parser = new PatternParser();
			parser.parse(line);

			List<Pattern> linePatterns = parser.getPatterns();

			patterns.add(linePatterns);
		}

		return new Patterns(patterns);
	}

	@Override
	public String toString() {
		return "PattersBuilder [lines=" + lines + "]";
	}

}
