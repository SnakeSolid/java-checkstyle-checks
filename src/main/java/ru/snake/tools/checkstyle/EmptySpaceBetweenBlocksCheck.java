package ru.snake.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Validate number of empty line between open curly brace and following code or
 * closing curly brace and previous code. Number of empty line can be defined
 * using {@link EmptySpaceBetweenBlocksCheck#numLines} parameter. By default
 * number of empty line is zero.
 *
 * @author snake
 *
 */
public class EmptySpaceBetweenBlocksCheck extends AbstractCheck {

	public static final String MSG_EMPTY_LINES_AFTER = "Number of empty lines after '{' greater than {0}";

	public static final String MSG_EMPTY_LINES_BEFORE = "Number of empty lines before '}' greater than {0}";

	private int numLines;

	/**
	 * Set number of lines parameter.
	 *
	 * @param numLines
	 *            number of lines
	 */
	public void setNumLines(int numLines) {
		if (numLines < 0) {
			throw new IllegalArgumentException("Parameter numLines must be greater than zero");
		}

		this.numLines = numLines;
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
			switch (result.getType()) {
			case TokenTypes.CTOR_DEF:
			case TokenTypes.METHOD_DEF:
			case TokenTypes.VARIABLE_DEF:
				result = scanComment(result);
				break;

			default:
				result = result.getFirstChild();
				break;
			}
		}

		return result;
	}

	/**
	 * Scan for nearest comment for method or variable definition. Used to skip
	 * empty modifiers node when modifiers not defined. Returns first node with
	 * children or first child node.
	 *
	 * @param ast
	 *            ast
	 * @return node with comment or first child
	 */
	private DetailAST scanComment(DetailAST ast) {
		DetailAST result = ast.getFirstChild();
		int resultLineNo = result.getLineNo();
		DetailAST current = result;

		while (current != null) {
			if (current.getLineNo() > resultLineNo) {
				break;
			}

			if (current.getChildCount() > 0) {
				return current.getFirstChild();
			}

			current = current.getNextSibling();
		}

		return result;
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
	public boolean isCommentNodesRequired() {
		return true;
	}

}
