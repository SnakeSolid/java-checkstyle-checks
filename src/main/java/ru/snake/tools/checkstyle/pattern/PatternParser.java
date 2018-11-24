package ru.snake.tools.checkstyle.pattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PatternParser {

	private final List<Pattern> patterns;

	public PatternParser() {
		this.patterns = new ArrayList<>(4);
	}

	public List<Pattern> getPatterns() {
		return Collections.unmodifiableList(this.patterns);
	}

	public void parse(String line) {
		int index = 0;
		State state = State.TEXT;
		StringBuilder token = new StringBuilder();

		while (index < line.length()) {
			char currCh = charAtOrDefault(line, index, '\uFFFD');
			char nextCh = charAtOrDefault(line, index + 1, '\uFFFD');

			index += 1;

			if (state == State.TEXT && currCh == '{' && nextCh == '{') {
				token.append(currCh);
				index += 1;
			} else if (state == State.TEXT && currCh == '{') {
				pushText(token.toString());
				state = State.PATTERN;
				token.setLength(0);
			} else if (state == State.PATTERN && currCh == '}' && nextCh == '}') {
				token.append(currCh);
				index += 1;
			} else if (state == State.PATTERN && currCh == '}') {
				pushPattern(token.toString());
				state = State.TEXT;
				token.setLength(0);
			} else {
				token.append(currCh);
			}
		}

		switch (state) {
		case TEXT:
			pushText(token.toString());
			break;

		default:
			throw new IllegalStateException("Illegal parser state");
		}
	}

	private void pushPattern(String string) {
		if (string.isEmpty()) {
			throw new IllegalStateException("Pattern is empty");
		}

		int paramIndex = string.indexOf(':');
		boolean hasParam = paramIndex != -1;
		Pattern pattern;

		if (hasParam) {
			String patterName = string.substring(0, paramIndex);
			String patternParam = string.substring(paramIndex + 1);

			switch (patterName) {
			case "one_of":
				pattern = new OneOfPattern(patternParam);
				break;

			case "text":
				pattern = new TextPattern(patternParam);
				break;

			case "date":
				pattern = new DatePattern(patternParam);
				break;

			case "parameter":
				pattern = new ParameterPattern(patternParam);
				break;

			default:
				throw new IllegalStateException("Illegal parser state");
			}
		} else {
			throw new IllegalStateException("Illegal parser state");
		}

		this.patterns.add(pattern);
	}

	private void pushText(String string) {
		if (!string.isEmpty()) {
			this.patterns.add(new TextPattern(string));
		}
	}

	/**
	 * Returns character at given index. If index out of line bounds will return
	 * default character.
	 *
	 * @param line
	 *            line
	 * @param index
	 *            index
	 * @param defaultCh
	 *            default character
	 * @return character at index
	 */
	private char charAtOrDefault(String line, int index, char defaultCh) {
		if ((index < 0) || (index >= line.length())) {
			return defaultCh;
		}

		return line.charAt(index);
	}

	@Override
	public String toString() {
		return "PatternParser [patterns=" + patterns + "]";
	}

	/**
	 * Internal pattern parser state.
	 *
	 * @author snake
	 *
	 */
	private static enum State {
		TEXT, PATTERN,
	}
}
