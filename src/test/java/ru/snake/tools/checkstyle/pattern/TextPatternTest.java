package ru.snake.tools.checkstyle.pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TextPatternTest {

	@Test
	public void shouldMatchString() {
		Pattern pattern = new TextPattern("Text");
		Match match = pattern.match("Text", 0, null);

		Assertions.assertNotNull(match);
		Assertions.assertEquals(true, match.isSuccess);
		Assertions.assertEquals(4, match.length);
	}

	@Test
	public void shouldNotMatchString() {
		Pattern pattern = new TextPattern("Text");
		Match match = pattern.match("Different", 0, null);

		Assertions.assertNotNull(match);
		Assertions.assertEquals(false, match.isSuccess);
		Assertions.assertEquals(-1, match.length);
	}

	@Test
	public void shouldMatchStringFromIntex() {
		Pattern pattern = new TextPattern("Text");
		Match match = pattern.match("    Text", 4, null);

		Assertions.assertNotNull(match);
		Assertions.assertEquals(true, match.isSuccess);
		Assertions.assertEquals(4, match.length);
	}

	@Test
	public void shouldNotMatchStringFromIntex() {
		Pattern pattern = new TextPattern("Text");
		Match match = pattern.match("    Different", 4, null);

		Assertions.assertNotNull(match);
		Assertions.assertEquals(false, match.isSuccess);
		Assertions.assertEquals(-1, match.length);
	}

}
