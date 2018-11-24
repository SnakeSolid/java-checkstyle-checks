package ru.snake.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class MethodLimitCheck extends AbstractCheck {
	private static final int DEFAULT_MAX = 3;

	private int max = DEFAULT_MAX;

	@Override
	public int[] getDefaultTokens() {
		return new int[] { TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF };
	}

	@Override
	public void visitToken(DetailAST ast) {
		DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
		int methodDefs = objBlock.getChildCount(TokenTypes.METHOD_DEF);

		if (methodDefs > this.max) {
			String message = "too many methods, only " + this.max + " are allowed";

			log(ast.getLineNo(), message);
		}
	}

	public void setMax(int max) {
		this.max = max;
	}

	@Override
	public int[] getAcceptableTokens() {
		return getDefaultTokens();
	}

	@Override
	public int[] getRequiredTokens() {
		return getDefaultTokens();
	}
}
