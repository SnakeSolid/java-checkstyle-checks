package ru.snake.tools.checkstyle.checks;

import java.io.File;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.checks.header.AbstractHeaderCheck;
import com.puppycrawl.tools.checkstyle.checks.header.RegexpHeaderCheck;

import ru.snake.tools.checkstyle.pattern.MatchResult;
import ru.snake.tools.checkstyle.pattern.Patterns;
import ru.snake.tools.checkstyle.pattern.PattersBuilder;

/**
 * Checked similar to {@link RegexpHeaderCheck}, but support file name check and
 * date time validation.
 *
 * @author snake
 *
 */
public class MultiPatternHeaderCheck extends AbstractHeaderCheck {

	private static final String MSG_HEADER_MISSING = "File header missing";

	private static final String MSG_HEADER_MISMATCH = "Missmatch file header: {0}";

	private Patterns patterns;

	@Override
	protected void processFiltered(File file, FileText fileText) throws CheckstyleException {
		int headerSize = patterns.size();
		int fileSize = fileText.size();

		if (headerSize > fileSize) {
			log(1, MSG_HEADER_MISSING);
		} else {
			this.patterns.setParameter("file_name", file.getName());

			for (int index = 0; index < headerSize; index += 1) {
				String line = fileText.get(index);
				MatchResult match = this.patterns.match(line, index);

				if (!match.isSuccess) {
					log(index + 1, match.errorIndex, MSG_HEADER_MISMATCH, match.errorMessage);

					break;
				}
			}
		}
	}

	@Override
	protected void postProcessHeaderLines() {
		final List<String> headerLines = getHeaderLines();

		try {
			this.patterns = new PattersBuilder(headerLines).build();
		} catch (final PatternSyntaxException ex) {
			throw new IllegalArgumentException(ex);
		}
	}

}
