package ru.snake.tools.checkstyle.pattern;

import java.util.Map;

public class TextPattern implements Pattern {

	private final String text;

	public TextPattern(String text) {
		this.text = text;
	}

	@Override
	public Match match(String line, int startIndex, Map<String, String> parameters) {
		if (startIndex < line.length()) {
			if (line.substring(startIndex).startsWith(this.text)) {
				return Match.success(this.text.length());
			}
		}

		return Match.fail("Expected " + this.text);
	}

	@Override
	public String toString() {
		return "TextPattern [text=" + text + "]";
	}

}
