package ru.snake.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class EmptySpaceBetweenBlocksCheck extends AbstractCheck {

	public static final String MSG_EMPTY_LINES_AFTER = "Number of empty lines after '{' greater than {0}";

	public static final String MSG_EMPTY_LINES_BEFORE = "Number of empty lines before '}' greater than {0}";

	private int numLines;

	public void setNumLines(int numLines) {
		if (numLines < 0) {
			throw new IllegalArgumentException("Parameter numLines must be greater than zero");
		}

		this.numLines = numLines;
	}

	@Override
	public int[] getDefaultTokens() {
		return getAcceptableTokens();
	}

	@Override
	public int[] getAcceptableTokens() {
		return new int[] { TokenTypes.LCURLY, TokenTypes.SLIST, TokenTypes.RCURLY, };
	}

	@Override
	public int[] getRequiredTokens() {
		return new int[] {};
	}

	@Override
	public void visitToken(DetailAST ast) {
		DetailAST currentToken = ast;
		DetailAST otherToken = null;

		switch (ast.getType()) {
		case TokenTypes.LCURLY:
			otherToken = getNextNode(ast.getNextSibling());
			break;

		case TokenTypes.SLIST:
			otherToken = getNextNode(ast.getFirstChild());
			break;

		case TokenTypes.RCURLY:
			otherToken = getPreviousNode(ast.getPreviousSibling());
			break;

		default:
			otherToken = null;
			break;
		}

		if (otherToken != null) {
			int currentLine = currentToken.getLineNo();
			int otherLine = otherToken.getLineNo();

			if (otherLine - currentLine > this.numLines + 1) {
				log(currentLine + this.numLines + 1, MSG_EMPTY_LINES_AFTER, this.numLines);
			} else if (currentLine - otherLine > this.numLines + 1) {
				log(otherLine + this.numLines + 1, MSG_EMPTY_LINES_BEFORE, this.numLines);
			}
		}
	}

	/**
	 * Returns previous nearest node in AST tree.
	 *
	 * @param ast
	 *            ast
	 * @return previous node
	 */
	private DetailAST getPreviousNode(DetailAST ast) {
		DetailAST result = ast;

		while (result != null && result.getChildCount() > 0) {
			result = result.getLastChild();
		}

		return result;
	}

	/**
	 * Returns next nearest node in AST tree.
	 *
	 * @param ast
	 *            ast
	 * @return next node
	 */
	private DetailAST getNextNode(DetailAST ast) {
		DetailAST result = ast;

		while (result != null && result.getChildCount() > 0) {
			result = result.getFirstChild();
		}

		return result;
	}

}
