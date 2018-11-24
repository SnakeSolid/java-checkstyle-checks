package ru.snake.tools.checkstyle.pattern;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author snake
 *
 */
public class ParameterPatternTest {

	private Map<String, String> parameters;

	@BeforeEach
	public void init() {
		this.parameters = new HashMap<>();
		this.parameters.put("param", "Text");
	}

	@Test
	public void shouldMatchString() {
		Pattern pattern = new ParameterPattern("param");
		Match match = pattern.match("Text", 0, this.parameters);

		Assertions.assertNotNull(match);
		Assertions.assertEquals(true, match.isSuccess);
		Assertions.assertEquals(4, match.length);
	}

	@Test
	public void shouldNotMatchString() {
		Pattern pattern = new ParameterPattern("param");
		Match match = pattern.match("Different", 0, this.parameters);

		Assertions.assertNotNull(match);
		Assertions.assertEquals(false, match.isSuccess);
		Assertions.assertEquals(-1, match.length);
	}

	@Test
	public void shouldMatchStringFromIntex() {
		Pattern pattern = new ParameterPattern("param");
		Match match = pattern.match("    Text", 4, this.parameters);

		Assertions.assertNotNull(match);
		Assertions.assertEquals(true, match.isSuccess);
		Assertions.assertEquals(4, match.length);
	}

	@Test
	public void shouldNotMatchStringFromIntex() {
		Pattern pattern = new ParameterPattern("param");
		Match match = pattern.match("    Different", 4, this.parameters);

		Assertions.assertNotNull(match);
		Assertions.assertEquals(false, match.isSuccess);
		Assertions.assertEquals(-1, match.length);
	}

}
