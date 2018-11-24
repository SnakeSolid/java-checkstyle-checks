package ru.snake.tools.checkstyle.pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author snake
 *
 */
public class DatePatternTest {

	@Test
	public void shouldMatchDateString() {
		Pattern pattern = new DatePattern("yyyy-MM-dd");
		Match match = pattern.match("2018-11-24", 0, null);

		Assertions.assertNotNull(match);
		Assertions.assertEquals(true, match.isSuccess);
		Assertions.assertEquals(10, match.length);
	}

	@Test
	public void shouldMatchDateStringFromINdex() {
		Pattern pattern = new DatePattern("yyyy-MM-dd");
		Match match = pattern.match("    2018-11-24", 4, null);

		Assertions.assertNotNull(match);
		Assertions.assertEquals(true, match.isSuccess);
		Assertions.assertEquals(10, match.length);
	}

	@Test
	public void shouldNotMatchDateString() {
		Pattern pattern = new DatePattern("yyyy-MM-dd");
		Match match = pattern.match("2018-21-24", 0, null);

		Assertions.assertNotNull(match);
		Assertions.assertEquals(false, match.isSuccess);
		Assertions.assertEquals(-1, match.length);
	}

	@Test
	public void shouldNotMatchDateStringFromINdex() {
		Pattern pattern = new DatePattern("yyyy-MM-dd");
		Match match = pattern.match("    2018-21-24", 4, null);

		Assertions.assertNotNull(match);
		Assertions.assertEquals(false, match.isSuccess);
		Assertions.assertEquals(-1, match.length);
	}

}
