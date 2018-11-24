package ru.snake.tools.checkstyle.pattern;

import java.util.Map;

public interface Pattern {

	/**
	 * Returns length of matched with patter part of the line. If line string
	 * does not contain this pattern starting from startIndex returns -1. If
	 * startIndex greater then line length returns -1.
	 *
	 * @param line
	 *            string to check
	 * @param startIndex
	 *            start index
	 * @param parameters
	 *            parameters map
	 * @return length of matched line part
	 */
	public Match match(String line, int startIndex, Map<String, String> parameters);

}
