package ru.snake.tools.checkstyle.checks;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Check all components of package name. Every name part must be valid of
 * Windows directory name. Every unsupported name part will be marker with
 * corresponding error.
 *
 * Windows does not support directory following names: con, prn, aux, nul, com1,
 * com2, com3, com4, com5, com6, com7, com8, com9, lpt1, lpt2, lpt3, lpt4, lpt5,
 * lpt6, lpt7, lpt8 and lpt9.
 *
 * @author snake
 *
 */
public class WindowsSafePackageNameCheck extends AbstractCheck {

	private static final String MSG_KEY = "Package contains name `{0}` which unsupported in Windows";

	private Set<String> unsafeDirectories = Stream
			.of("con", "prn", "aux", "nul", "com1", "com2", "com3", "com4", "com5", "com6", "com7", "com8", "com9",
					"lpt1", "lpt2", "lpt3", "lpt4", "lpt5", "lpt6", "lpt7", "lpt8", "lpt9")
			.collect(Collectors.toSet());

	@Override
	public int[] getDefaultTokens() {
		return getRequiredTokens();
	}

	@Override
	public int[] getAcceptableTokens() {
		return getRequiredTokens();
	}

	@Override
	public int[] getRequiredTokens() {
		return new int[] { TokenTypes.PACKAGE_DEF };
	}

	@Override
	public void visitToken(DetailAST ast) {
		DetailAST nameAst = ast.getLastChild().getPreviousSibling();

		validatePackage(nameAst);
	}

	/**
	 * Validate every part of package name. Show errors for every name part
	 * which contains unsupported in Windows name.
	 *
	 * @param ast
	 *            ast node
	 */
	private void validatePackage(DetailAST ast) {
		if (ast != null) {
			if (ast.getType() == TokenTypes.DOT) {
				validatePackage(ast.getFirstChild());
				validatePackage(ast.getFirstChild().getNextSibling());
			} else {
				validateName(ast);
			}
		}
	}

	/**
	 * Validate AST node. Show error if node name is one of unsupported in
	 * Windows directory names.
	 *
	 * @param ast
	 *            ast node
	 */
	private void validateName(DetailAST ast) {
		String name = ast.getText().toLowerCase(Locale.ENGLISH);

		if (unsafeDirectories.contains(name)) {
			log(ast.getLineNo(), ast.getColumnNo(), MSG_KEY, name);
		}
	}

}
