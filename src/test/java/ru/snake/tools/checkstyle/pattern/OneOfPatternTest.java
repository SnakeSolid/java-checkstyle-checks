package ru.snake.tools.checkstyle.pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author snake
 *
 */
public class OneOfPatternTest {

	@Test
	public void shouldMatchVeryLongString() {
		Pattern pattern = new OneOfPattern("Text|TextSmall|TextVeryLong");
		Match match = pattern.match("TextVeryLong", 0, null);

		Assertions.assertNotNull(match);
		Assertions.assertEquals(true, match.isSuccess);
		Assertions.assertEquals(12, match.length);
	}

	@Test
	public void shouldMatchSmallString() {
		Pattern pattern = new OneOfPattern("Text|TextSmall|TextVeryLong");
		Match match = pattern.match("TextSmall", 0, null);

		Assertions.assertNotNull(match);
		Assertions.assertEquals(true, match.isSuccess);
		Assertions.assertEquals(9, match.length);
	}

	@Test
	public void shouldMatchTextString() {
		Pattern pattern = new OneOfPattern("Text|TextSmall|TextVeryLong");
		Match match = pattern.match("Text", 0, null);

		Assertions.assertNotNull(match);
		Assertions.assertEquals(true, match.isSuccess);
		Assertions.assertEquals(4, match.length);
	}

	@Test
	public void shouldNotMatchAnyString() {
		Pattern pattern = new OneOfPattern("Text|TextSmall|TextVeryLong");
		Match match = pattern.match("    Line", 4, null);

		Assertions.assertNotNull(match);
		Assertions.assertEquals(false, match.isSuccess);
		Assertions.assertEquals(-1, match.length);
	}

	@Test
	public void shouldMatchVeryLongStringFromIndex() {
		Pattern pattern = new OneOfPattern("Text|TextSmall|TextVeryLong");
		Match match = pattern.match("    TextVeryLong", 4, null);

		Assertions.assertNotNull(match);
		Assertions.assertEquals(true, match.isSuccess);
		Assertions.assertEquals(12, match.length);
	}

	@Test
	public void shouldMatchSmallStringFromIndex() {
		Pattern pattern = new OneOfPattern("Text|TextSmall|TextVeryLong");
		Match match = pattern.match("    TextSmall", 4, null);

		Assertions.assertNotNull(match);
		Assertions.assertEquals(true, match.isSuccess);
		Assertions.assertEquals(9, match.length);
	}

	@Test
	public void shouldMatchTextStringFromIndex() {
		Pattern pattern = new OneOfPattern("Text|TextSmall|TextVeryLong");
		Match match = pattern.match("    Text", 4, null);

		Assertions.assertNotNull(match);
		Assertions.assertEquals(true, match.isSuccess);
		Assertions.assertEquals(4, match.length);
	}

}
