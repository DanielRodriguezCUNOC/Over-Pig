// Generated from LatinParser.g4 by ANTLR 4.13.2
package com.piglatin.piglatin.infrastructure.parser.generated;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class LatinParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LINE_COMMENT=1, BLOCK_COMMENT=2, PLUS=3, MINUS=4, MULT=5, SPLIT=6, VARIABILES_INIT=7, 
		MAIOR_INIT=8, FINIS_EOF=9, IDENTIC=10, DIFF=11, MAJORTO=12, MINORTO=13, 
		MINOR=14, MAJOR=15, ASSIGN=16, AND=17, OR=18, NOT=19, ADD=20, SUB=21, 
		COLON=22, SEMICOLON=23, COMMA=24, DOT=25, LEFT_CLASP=26, RIGHT_CLASP=27, 
		LEFT_BRACE=28, RIGHT_BRACE=29, LEFT_PAREN=30, RIGHT_PAREN=31, ESTO=32, 
		NUMERUS=33, TEXTUM=34, DECIMALIS=35, BOOL=36, LITTERA=37, VERUM=38, FALSUS=39, 
		SERIES=40, FINIS=41, SI=42, ALITER=43, DUM=44, FACERE=45, PER=46, PERGE=47, 
		INTERRUMPE=48, VARIABILES=49, MAIOR=50, IMPORT=51, NOVUS=52, LEERE=53, 
		IMPREMERE=54, ID=55, DECIMAL=56, INTEGER=57, STRING=58, CHAR=59, WS=60;
	public static final int
		RULE_program = 0, RULE_importList = 1, RULE_importStatement = 2, RULE_globalDeclarations = 3, 
		RULE_globalDeclaration = 4, RULE_mainInstructions = 5, RULE_instruction = 6, 
		RULE_declaration = 7, RULE_arrayDeclaration = 8, RULE_arrayLiteral = 9, 
		RULE_block = 10, RULE_ifStatement = 11, RULE_elseIfClause = 12, RULE_elseClause = 13, 
		RULE_whileStatement = 14, RULE_doWhileStatement = 15, RULE_forStatement = 16, 
		RULE_forInit = 17, RULE_forUpdate = 18, RULE_jumpStatement = 19, RULE_newInstance = 20, 
		RULE_readStatement = 21, RULE_printStatement = 22, RULE_printItem = 23, 
		RULE_lvalue = 24, RULE_lvalueSuffix = 25, RULE_assignment = 26, RULE_type = 27, 
		RULE_argumentList = 28, RULE_expression = 29, RULE_booleanExpression = 30, 
		RULE_booleanOrExpression = 31, RULE_booleanAndExpression = 32, RULE_comparisonExpression = 33, 
		RULE_comparisonOperand = 34, RULE_relationalOp = 35, RULE_booleanLiteral = 36, 
		RULE_numericExpression = 37, RULE_additiveExpression = 38, RULE_multiplicativeExpression = 39, 
		RULE_unaryExpression = 40, RULE_primaryNumeric = 41, RULE_numericLiteral = 42, 
		RULE_stringExpression = 43, RULE_stringAdditiveExpression = 44, RULE_stringAdditiveItem = 45, 
		RULE_stringPrimary = 46, RULE_attributeAccess = 47;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "importList", "importStatement", "globalDeclarations", "globalDeclaration", 
			"mainInstructions", "instruction", "declaration", "arrayDeclaration", 
			"arrayLiteral", "block", "ifStatement", "elseIfClause", "elseClause", 
			"whileStatement", "doWhileStatement", "forStatement", "forInit", "forUpdate", 
			"jumpStatement", "newInstance", "readStatement", "printStatement", "printItem", 
			"lvalue", "lvalueSuffix", "assignment", "type", "argumentList", "expression", 
			"booleanExpression", "booleanOrExpression", "booleanAndExpression", "comparisonExpression", 
			"comparisonOperand", "relationalOp", "booleanLiteral", "numericExpression", 
			"additiveExpression", "multiplicativeExpression", "unaryExpression", 
			"primaryNumeric", "numericLiteral", "stringExpression", "stringAdditiveExpression", 
			"stringAdditiveItem", "stringPrimary", "attributeAccess"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'+'", "'-'", "'*'", "'/'", "'VARIABILES>'", "'MAIOR>'", 
			"'FINIS'", "'=='", "'!='", "'>='", "'<='", "'<'", "'>'", "'='", "'&&'", 
			"'||'", "'non'", "'++'", "'--'", "':'", "';'", "','", "'.'", "'['", "']'", 
			"'{'", "'}'", "'('", "')'", "'esto'", "'numerus'", "'textum'", "'decimalis'", 
			"'bool'", "'littera'", "'verum'", "'falsus'", "'series'", "'finis'", 
			"'si'", "'aliter'", "'dum'", "'facere'", "'per'", "'perge'", "'interrumpe'", 
			"'VARIABILES'", "'MAIOR'", "'import'", "'novus'", "'<<'", "'>>'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "LINE_COMMENT", "BLOCK_COMMENT", "PLUS", "MINUS", "MULT", "SPLIT", 
			"VARIABILES_INIT", "MAIOR_INIT", "FINIS_EOF", "IDENTIC", "DIFF", "MAJORTO", 
			"MINORTO", "MINOR", "MAJOR", "ASSIGN", "AND", "OR", "NOT", "ADD", "SUB", 
			"COLON", "SEMICOLON", "COMMA", "DOT", "LEFT_CLASP", "RIGHT_CLASP", "LEFT_BRACE", 
			"RIGHT_BRACE", "LEFT_PAREN", "RIGHT_PAREN", "ESTO", "NUMERUS", "TEXTUM", 
			"DECIMALIS", "BOOL", "LITTERA", "VERUM", "FALSUS", "SERIES", "FINIS", 
			"SI", "ALITER", "DUM", "FACERE", "PER", "PERGE", "INTERRUMPE", "VARIABILES", 
			"MAIOR", "IMPORT", "NOVUS", "LEERE", "IMPREMERE", "ID", "DECIMAL", "INTEGER", 
			"STRING", "CHAR", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "LatinParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public LatinParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode MAIOR_INIT() { return getToken(LatinParser.MAIOR_INIT, 0); }
		public MainInstructionsContext mainInstructions() {
			return getRuleContext(MainInstructionsContext.class,0);
		}
		public TerminalNode EOF() { return getToken(LatinParser.EOF, 0); }
		public TerminalNode FINIS_EOF() { return getToken(LatinParser.FINIS_EOF, 0); }
		public TerminalNode FINIS() { return getToken(LatinParser.FINIS, 0); }
		public ImportListContext importList() {
			return getRuleContext(ImportListContext.class,0);
		}
		public TerminalNode VARIABILES_INIT() { return getToken(LatinParser.VARIABILES_INIT, 0); }
		public GlobalDeclarationsContext globalDeclarations() {
			return getRuleContext(GlobalDeclarationsContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(LatinParser.SEMICOLON, 0); }
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPORT) {
				{
				setState(96);
				importList();
				}
			}

			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==VARIABILES_INIT) {
				{
				setState(99);
				match(VARIABILES_INIT);
				setState(100);
				globalDeclarations();
				}
			}

			setState(103);
			match(MAIOR_INIT);
			setState(104);
			mainInstructions();
			setState(105);
			_la = _input.LA(1);
			if ( !(_la==FINIS_EOF || _la==FINIS) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(107);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(106);
				match(SEMICOLON);
				}
			}

			setState(109);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ImportListContext extends ParserRuleContext {
		public List<ImportStatementContext> importStatement() {
			return getRuleContexts(ImportStatementContext.class);
		}
		public ImportStatementContext importStatement(int i) {
			return getRuleContext(ImportStatementContext.class,i);
		}
		public ImportListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterImportList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitImportList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitImportList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportListContext importList() throws RecognitionException {
		ImportListContext _localctx = new ImportListContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_importList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(111);
				importStatement();
				}
				}
				setState(114); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==IMPORT );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ImportStatementContext extends ParserRuleContext {
		public TerminalNode IMPORT() { return getToken(LatinParser.IMPORT, 0); }
		public List<TerminalNode> ID() { return getTokens(LatinParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(LatinParser.ID, i);
		}
		public List<TerminalNode> DOT() { return getTokens(LatinParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(LatinParser.DOT, i);
		}
		public TerminalNode SEMICOLON() { return getToken(LatinParser.SEMICOLON, 0); }
		public ImportStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterImportStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitImportStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitImportStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportStatementContext importStatement() throws RecognitionException {
		ImportStatementContext _localctx = new ImportStatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_importStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
			match(IMPORT);
			setState(117);
			match(ID);
			setState(122);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(118);
				match(DOT);
				setState(119);
				match(ID);
				}
				}
				setState(124);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(126);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(125);
				match(SEMICOLON);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GlobalDeclarationsContext extends ParserRuleContext {
		public List<GlobalDeclarationContext> globalDeclaration() {
			return getRuleContexts(GlobalDeclarationContext.class);
		}
		public GlobalDeclarationContext globalDeclaration(int i) {
			return getRuleContext(GlobalDeclarationContext.class,i);
		}
		public GlobalDeclarationsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_globalDeclarations; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterGlobalDeclarations(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitGlobalDeclarations(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitGlobalDeclarations(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GlobalDeclarationsContext globalDeclarations() throws RecognitionException {
		GlobalDeclarationsContext _localctx = new GlobalDeclarationsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_globalDeclarations);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ESTO || _la==SERIES) {
				{
				{
				setState(128);
				globalDeclaration();
				}
				}
				setState(133);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GlobalDeclarationContext extends ParserRuleContext {
		public GlobalDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_globalDeclaration; }
	 
		public GlobalDeclarationContext() { }
		public void copyFrom(GlobalDeclarationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GlobalDeclarationArrayContext extends GlobalDeclarationContext {
		public ArrayDeclarationContext arrayDeclaration() {
			return getRuleContext(ArrayDeclarationContext.class,0);
		}
		public GlobalDeclarationArrayContext(GlobalDeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterGlobalDeclarationArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitGlobalDeclarationArray(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitGlobalDeclarationArray(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GlobalDeclarationVariableContext extends GlobalDeclarationContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public GlobalDeclarationVariableContext(GlobalDeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterGlobalDeclarationVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitGlobalDeclarationVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitGlobalDeclarationVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GlobalDeclarationContext globalDeclaration() throws RecognitionException {
		GlobalDeclarationContext _localctx = new GlobalDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_globalDeclaration);
		try {
			setState(136);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ESTO:
				_localctx = new GlobalDeclarationVariableContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(134);
				declaration();
				}
				break;
			case SERIES:
				_localctx = new GlobalDeclarationArrayContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(135);
				arrayDeclaration();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MainInstructionsContext extends ParserRuleContext {
		public List<InstructionContext> instruction() {
			return getRuleContexts(InstructionContext.class);
		}
		public InstructionContext instruction(int i) {
			return getRuleContext(InstructionContext.class,i);
		}
		public MainInstructionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mainInstructions; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterMainInstructions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitMainInstructions(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitMainInstructions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MainInstructionsContext mainInstructions() throws RecognitionException {
		MainInstructionsContext _localctx = new MainInstructionsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_mainInstructions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1148969590579527704L) != 0)) {
				{
				{
				setState(138);
				instruction();
				}
				}
				setState(143);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InstructionContext extends ParserRuleContext {
		public InstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruction; }
	 
		public InstructionContext() { }
		public void copyFrom(InstructionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstructionAssignmentContext extends InstructionContext {
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public InstructionAssignmentContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterInstructionAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitInstructionAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitInstructionAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstructionForContext extends InstructionContext {
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public InstructionForContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterInstructionFor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitInstructionFor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitInstructionFor(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstructionDeclarationContext extends InstructionContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public InstructionDeclarationContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterInstructionDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitInstructionDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitInstructionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstructionPrintContext extends InstructionContext {
		public PrintStatementContext printStatement() {
			return getRuleContext(PrintStatementContext.class,0);
		}
		public InstructionPrintContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterInstructionPrint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitInstructionPrint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitInstructionPrint(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstructionIfContext extends InstructionContext {
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public InstructionIfContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterInstructionIf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitInstructionIf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitInstructionIf(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstructionDoWhileContext extends InstructionContext {
		public DoWhileStatementContext doWhileStatement() {
			return getRuleContext(DoWhileStatementContext.class,0);
		}
		public InstructionDoWhileContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterInstructionDoWhile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitInstructionDoWhile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitInstructionDoWhile(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstructionJumpContext extends InstructionContext {
		public JumpStatementContext jumpStatement() {
			return getRuleContext(JumpStatementContext.class,0);
		}
		public InstructionJumpContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterInstructionJump(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitInstructionJump(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitInstructionJump(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstructionArrayDeclarationContext extends InstructionContext {
		public ArrayDeclarationContext arrayDeclaration() {
			return getRuleContext(ArrayDeclarationContext.class,0);
		}
		public InstructionArrayDeclarationContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterInstructionArrayDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitInstructionArrayDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitInstructionArrayDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstructionReadContext extends InstructionContext {
		public ReadStatementContext readStatement() {
			return getRuleContext(ReadStatementContext.class,0);
		}
		public InstructionReadContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterInstructionRead(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitInstructionRead(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitInstructionRead(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstructionWhileContext extends InstructionContext {
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public InstructionWhileContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterInstructionWhile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitInstructionWhile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitInstructionWhile(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstructionExpressionContext extends InstructionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(LatinParser.SEMICOLON, 0); }
		public InstructionExpressionContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterInstructionExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitInstructionExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitInstructionExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_instruction);
		int _la;
		try {
			setState(158);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				_localctx = new InstructionAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(144);
				assignment();
				}
				break;
			case 2:
				_localctx = new InstructionReadContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(145);
				readStatement();
				}
				break;
			case 3:
				_localctx = new InstructionPrintContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(146);
				printStatement();
				}
				break;
			case 4:
				_localctx = new InstructionIfContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(147);
				ifStatement();
				}
				break;
			case 5:
				_localctx = new InstructionWhileContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(148);
				whileStatement();
				}
				break;
			case 6:
				_localctx = new InstructionDoWhileContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(149);
				doWhileStatement();
				}
				break;
			case 7:
				_localctx = new InstructionForContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(150);
				forStatement();
				}
				break;
			case 8:
				_localctx = new InstructionJumpContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(151);
				jumpStatement();
				}
				break;
			case 9:
				_localctx = new InstructionDeclarationContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(152);
				declaration();
				}
				break;
			case 10:
				_localctx = new InstructionArrayDeclarationContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(153);
				arrayDeclaration();
				}
				break;
			case 11:
				_localctx = new InstructionExpressionContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(154);
				expression();
				setState(156);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEMICOLON) {
					{
					setState(155);
					match(SEMICOLON);
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeclarationContext extends ParserRuleContext {
		public TerminalNode ESTO() { return getToken(LatinParser.ESTO, 0); }
		public TerminalNode ID() { return getToken(LatinParser.ID, 0); }
		public TerminalNode COLON() { return getToken(LatinParser.COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(LatinParser.SEMICOLON, 0); }
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			match(ESTO);
			setState(161);
			match(ID);
			setState(162);
			match(COLON);
			setState(168);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				setState(163);
				type();
				setState(165);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
				case 1:
					{
					setState(164);
					expression();
					}
					break;
				}
				}
				break;
			case 2:
				{
				setState(167);
				expression();
				}
				break;
			}
			setState(171);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(170);
				match(SEMICOLON);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArrayDeclarationContext extends ParserRuleContext {
		public TerminalNode SERIES() { return getToken(LatinParser.SERIES, 0); }
		public TerminalNode ID() { return getToken(LatinParser.ID, 0); }
		public TerminalNode LEFT_CLASP() { return getToken(LatinParser.LEFT_CLASP, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RIGHT_CLASP() { return getToken(LatinParser.RIGHT_CLASP, 0); }
		public TerminalNode COLON() { return getToken(LatinParser.COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ArrayLiteralContext arrayLiteral() {
			return getRuleContext(ArrayLiteralContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(LatinParser.SEMICOLON, 0); }
		public ArrayDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterArrayDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitArrayDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitArrayDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayDeclarationContext arrayDeclaration() throws RecognitionException {
		ArrayDeclarationContext _localctx = new ArrayDeclarationContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_arrayDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			match(SERIES);
			setState(174);
			match(ID);
			setState(175);
			match(LEFT_CLASP);
			setState(176);
			expression();
			setState(177);
			match(RIGHT_CLASP);
			setState(178);
			match(COLON);
			setState(179);
			type();
			setState(181);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				setState(180);
				arrayLiteral();
				}
				break;
			}
			setState(184);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(183);
				match(SEMICOLON);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArrayLiteralContext extends ParserRuleContext {
		public TerminalNode LEFT_BRACE() { return getToken(LatinParser.LEFT_BRACE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(LatinParser.RIGHT_BRACE, 0); }
		public List<TerminalNode> COMMA() { return getTokens(LatinParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(LatinParser.COMMA, i);
		}
		public ArrayLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterArrayLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitArrayLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitArrayLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayLiteralContext arrayLiteral() throws RecognitionException {
		ArrayLiteralContext _localctx = new ArrayLiteralContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_arrayLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(186);
			match(LEFT_BRACE);
			setState(187);
			expression();
			setState(192);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(188);
				match(COMMA);
				setState(189);
				expression();
				}
				}
				setState(194);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(195);
			match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public TerminalNode LEFT_BRACE() { return getToken(LatinParser.LEFT_BRACE, 0); }
		public TerminalNode RIGHT_BRACE() { return getToken(LatinParser.RIGHT_BRACE, 0); }
		public List<InstructionContext> instruction() {
			return getRuleContexts(InstructionContext.class);
		}
		public InstructionContext instruction(int i) {
			return getRuleContext(InstructionContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			match(LEFT_BRACE);
			setState(201);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1148969590579527704L) != 0)) {
				{
				{
				setState(198);
				instruction();
				}
				}
				setState(203);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(204);
			match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IfStatementContext extends ParserRuleContext {
		public TerminalNode SI() { return getToken(LatinParser.SI, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(LatinParser.LEFT_PAREN, 0); }
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(LatinParser.RIGHT_PAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode FINIS() { return getToken(LatinParser.FINIS, 0); }
		public List<ElseIfClauseContext> elseIfClause() {
			return getRuleContexts(ElseIfClauseContext.class);
		}
		public ElseIfClauseContext elseIfClause(int i) {
			return getRuleContext(ElseIfClauseContext.class,i);
		}
		public ElseClauseContext elseClause() {
			return getRuleContext(ElseClauseContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(LatinParser.SEMICOLON, 0); }
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_ifStatement);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			match(SI);
			setState(207);
			match(LEFT_PAREN);
			setState(208);
			booleanExpression();
			setState(209);
			match(RIGHT_PAREN);
			setState(210);
			block();
			setState(214);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(211);
					elseIfClause();
					}
					} 
				}
				setState(216);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			}
			setState(218);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ALITER) {
				{
				setState(217);
				elseClause();
				}
			}

			setState(220);
			match(FINIS);
			setState(222);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(221);
				match(SEMICOLON);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElseIfClauseContext extends ParserRuleContext {
		public TerminalNode ALITER() { return getToken(LatinParser.ALITER, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(LatinParser.LEFT_PAREN, 0); }
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(LatinParser.RIGHT_PAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ElseIfClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseIfClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterElseIfClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitElseIfClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitElseIfClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseIfClauseContext elseIfClause() throws RecognitionException {
		ElseIfClauseContext _localctx = new ElseIfClauseContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_elseIfClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(224);
			match(ALITER);
			setState(225);
			match(LEFT_PAREN);
			setState(226);
			booleanExpression();
			setState(227);
			match(RIGHT_PAREN);
			setState(228);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElseClauseContext extends ParserRuleContext {
		public TerminalNode ALITER() { return getToken(LatinParser.ALITER, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ElseClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterElseClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitElseClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitElseClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseClauseContext elseClause() throws RecognitionException {
		ElseClauseContext _localctx = new ElseClauseContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_elseClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(230);
			match(ALITER);
			setState(231);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhileStatementContext extends ParserRuleContext {
		public TerminalNode DUM() { return getToken(LatinParser.DUM, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(LatinParser.LEFT_PAREN, 0); }
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(LatinParser.RIGHT_PAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode FINIS() { return getToken(LatinParser.FINIS, 0); }
		public TerminalNode SEMICOLON() { return getToken(LatinParser.SEMICOLON, 0); }
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_whileStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
			match(DUM);
			setState(234);
			match(LEFT_PAREN);
			setState(235);
			booleanExpression();
			setState(236);
			match(RIGHT_PAREN);
			setState(237);
			block();
			setState(238);
			match(FINIS);
			setState(240);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(239);
				match(SEMICOLON);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DoWhileStatementContext extends ParserRuleContext {
		public TerminalNode FACERE() { return getToken(LatinParser.FACERE, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode DUM() { return getToken(LatinParser.DUM, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(LatinParser.LEFT_PAREN, 0); }
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(LatinParser.RIGHT_PAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(LatinParser.SEMICOLON, 0); }
		public DoWhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doWhileStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterDoWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitDoWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitDoWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DoWhileStatementContext doWhileStatement() throws RecognitionException {
		DoWhileStatementContext _localctx = new DoWhileStatementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_doWhileStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(242);
			match(FACERE);
			setState(243);
			block();
			setState(244);
			match(DUM);
			setState(245);
			match(LEFT_PAREN);
			setState(246);
			booleanExpression();
			setState(247);
			match(RIGHT_PAREN);
			setState(249);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(248);
				match(SEMICOLON);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForStatementContext extends ParserRuleContext {
		public TerminalNode PER() { return getToken(LatinParser.PER, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(LatinParser.LEFT_PAREN, 0); }
		public ForInitContext forInit() {
			return getRuleContext(ForInitContext.class,0);
		}
		public List<TerminalNode> SEMICOLON() { return getTokens(LatinParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(LatinParser.SEMICOLON, i);
		}
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(LatinParser.RIGHT_PAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ForUpdateContext forUpdate() {
			return getRuleContext(ForUpdateContext.class,0);
		}
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterForStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitForStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_forStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(251);
			match(PER);
			setState(252);
			match(LEFT_PAREN);
			setState(253);
			forInit();
			setState(254);
			match(SEMICOLON);
			setState(255);
			booleanExpression();
			setState(256);
			match(SEMICOLON);
			setState(258);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1121397133194821656L) != 0)) {
				{
				setState(257);
				forUpdate();
				}
			}

			setState(260);
			match(RIGHT_PAREN);
			setState(261);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForInitContext extends ParserRuleContext {
		public ForInitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forInit; }
	 
		public ForInitContext() { }
		public void copyFrom(ForInitContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ForInitEmptyContext extends ForInitContext {
		public ForInitEmptyContext(ForInitContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterForInitEmpty(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitForInitEmpty(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitForInitEmpty(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ForInitDeclarationContext extends ForInitContext {
		public TerminalNode ESTO() { return getToken(LatinParser.ESTO, 0); }
		public TerminalNode ID() { return getToken(LatinParser.ID, 0); }
		public TerminalNode COLON() { return getToken(LatinParser.COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ForInitDeclarationContext(ForInitContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterForInitDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitForInitDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitForInitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ForInitAssignmentContext extends ForInitContext {
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public ForInitAssignmentContext(ForInitContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterForInitAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitForInitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitForInitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForInitContext forInit() throws RecognitionException {
		ForInitContext _localctx = new ForInitContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_forInit);
		int _la;
		try {
			setState(272);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ESTO:
				_localctx = new ForInitDeclarationContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(263);
				match(ESTO);
				setState(264);
				match(ID);
				setState(265);
				match(COLON);
				setState(266);
				type();
				setState(268);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1121397133194821656L) != 0)) {
					{
					setState(267);
					expression();
					}
				}

				}
				break;
			case ID:
				_localctx = new ForInitAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(270);
				assignment();
				}
				break;
			case SEMICOLON:
				_localctx = new ForInitEmptyContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForUpdateContext extends ParserRuleContext {
		public ForUpdateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forUpdate; }
	 
		public ForUpdateContext() { }
		public void copyFrom(ForUpdateContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ForUpdateExpressionContext extends ForUpdateContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ForUpdateExpressionContext(ForUpdateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterForUpdateExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitForUpdateExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitForUpdateExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ForUpdateAssignmentContext extends ForUpdateContext {
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public ForUpdateAssignmentContext(ForUpdateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterForUpdateAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitForUpdateAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitForUpdateAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForUpdateContext forUpdate() throws RecognitionException {
		ForUpdateContext _localctx = new ForUpdateContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_forUpdate);
		try {
			setState(276);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				_localctx = new ForUpdateAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(274);
				assignment();
				}
				break;
			case 2:
				_localctx = new ForUpdateExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(275);
				expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JumpStatementContext extends ParserRuleContext {
		public JumpStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jumpStatement; }
	 
		public JumpStatementContext() { }
		public void copyFrom(JumpStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JumpContinueContext extends JumpStatementContext {
		public TerminalNode PERGE() { return getToken(LatinParser.PERGE, 0); }
		public TerminalNode SEMICOLON() { return getToken(LatinParser.SEMICOLON, 0); }
		public JumpContinueContext(JumpStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterJumpContinue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitJumpContinue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitJumpContinue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JumpBreakContext extends JumpStatementContext {
		public TerminalNode INTERRUMPE() { return getToken(LatinParser.INTERRUMPE, 0); }
		public TerminalNode SEMICOLON() { return getToken(LatinParser.SEMICOLON, 0); }
		public JumpBreakContext(JumpStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterJumpBreak(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitJumpBreak(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitJumpBreak(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JumpStatementContext jumpStatement() throws RecognitionException {
		JumpStatementContext _localctx = new JumpStatementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_jumpStatement);
		int _la;
		try {
			setState(286);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PERGE:
				_localctx = new JumpContinueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(278);
				match(PERGE);
				setState(280);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEMICOLON) {
					{
					setState(279);
					match(SEMICOLON);
					}
				}

				}
				break;
			case INTERRUMPE:
				_localctx = new JumpBreakContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(282);
				match(INTERRUMPE);
				setState(284);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEMICOLON) {
					{
					setState(283);
					match(SEMICOLON);
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NewInstanceContext extends ParserRuleContext {
		public TerminalNode NOVUS() { return getToken(LatinParser.NOVUS, 0); }
		public TerminalNode ID() { return getToken(LatinParser.ID, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(LatinParser.LEFT_PAREN, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(LatinParser.RIGHT_PAREN, 0); }
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public NewInstanceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newInstance; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterNewInstance(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitNewInstance(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitNewInstance(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NewInstanceContext newInstance() throws RecognitionException {
		NewInstanceContext _localctx = new NewInstanceContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_newInstance);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(288);
			match(NOVUS);
			setState(289);
			match(ID);
			setState(290);
			match(LEFT_PAREN);
			setState(292);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1121397133194821656L) != 0)) {
				{
				setState(291);
				argumentList();
				}
			}

			setState(294);
			match(RIGHT_PAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReadStatementContext extends ParserRuleContext {
		public ReadStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_readStatement; }
	 
		public ReadStatementContext() { }
		public void copyFrom(ReadStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ReadSimpleContext extends ReadStatementContext {
		public TerminalNode LEERE() { return getToken(LatinParser.LEERE, 0); }
		public TerminalNode SEMICOLON() { return getToken(LatinParser.SEMICOLON, 0); }
		public ReadSimpleContext(ReadStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterReadSimple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitReadSimple(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitReadSimple(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ReadLvalueContext extends ReadStatementContext {
		public LvalueContext lvalue() {
			return getRuleContext(LvalueContext.class,0);
		}
		public TerminalNode LEERE() { return getToken(LatinParser.LEERE, 0); }
		public TerminalNode SEMICOLON() { return getToken(LatinParser.SEMICOLON, 0); }
		public ReadLvalueContext(ReadStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterReadLvalue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitReadLvalue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitReadLvalue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReadStatementContext readStatement() throws RecognitionException {
		ReadStatementContext _localctx = new ReadStatementContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_readStatement);
		int _la;
		try {
			setState(305);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LEERE:
				_localctx = new ReadSimpleContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(296);
				match(LEERE);
				setState(298);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEMICOLON) {
					{
					setState(297);
					match(SEMICOLON);
					}
				}

				}
				break;
			case ID:
				_localctx = new ReadLvalueContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(300);
				lvalue();
				setState(301);
				match(LEERE);
				setState(303);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEMICOLON) {
					{
					setState(302);
					match(SEMICOLON);
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrintStatementContext extends ParserRuleContext {
		public List<TerminalNode> IMPREMERE() { return getTokens(LatinParser.IMPREMERE); }
		public TerminalNode IMPREMERE(int i) {
			return getToken(LatinParser.IMPREMERE, i);
		}
		public List<PrintItemContext> printItem() {
			return getRuleContexts(PrintItemContext.class);
		}
		public PrintItemContext printItem(int i) {
			return getRuleContext(PrintItemContext.class,i);
		}
		public TerminalNode SEMICOLON() { return getToken(LatinParser.SEMICOLON, 0); }
		public PrintStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_printStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterPrintStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitPrintStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitPrintStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrintStatementContext printStatement() throws RecognitionException {
		PrintStatementContext _localctx = new PrintStatementContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_printStatement);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(307);
			match(IMPREMERE);
			setState(308);
			printItem();
			setState(313);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(309);
					match(IMPREMERE);
					setState(310);
					printItem();
					}
					} 
				}
				setState(315);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
			}
			setState(317);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(316);
				match(SEMICOLON);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrintItemContext extends ParserRuleContext {
		public PrintItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_printItem; }
	 
		public PrintItemContext() { }
		public void copyFrom(PrintItemContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrintStringContext extends PrintItemContext {
		public TerminalNode STRING() { return getToken(LatinParser.STRING, 0); }
		public PrintStringContext(PrintItemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterPrintString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitPrintString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitPrintString(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrintExpressionContext extends PrintItemContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PrintExpressionContext(PrintItemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterPrintExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitPrintExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitPrintExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrintItemContext printItem() throws RecognitionException {
		PrintItemContext _localctx = new PrintItemContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_printItem);
		try {
			setState(321);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				_localctx = new PrintStringContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(319);
				match(STRING);
				}
				break;
			case 2:
				_localctx = new PrintExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(320);
				expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LvalueContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(LatinParser.ID, 0); }
		public List<LvalueSuffixContext> lvalueSuffix() {
			return getRuleContexts(LvalueSuffixContext.class);
		}
		public LvalueSuffixContext lvalueSuffix(int i) {
			return getRuleContext(LvalueSuffixContext.class,i);
		}
		public LvalueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lvalue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterLvalue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitLvalue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitLvalue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LvalueContext lvalue() throws RecognitionException {
		LvalueContext _localctx = new LvalueContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_lvalue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(323);
			match(ID);
			setState(327);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT || _la==LEFT_CLASP) {
				{
				{
				setState(324);
				lvalueSuffix();
				}
				}
				setState(329);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LvalueSuffixContext extends ParserRuleContext {
		public LvalueSuffixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lvalueSuffix; }
	 
		public LvalueSuffixContext() { }
		public void copyFrom(LvalueSuffixContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IndexAccessContext extends LvalueSuffixContext {
		public TerminalNode LEFT_CLASP() { return getToken(LatinParser.LEFT_CLASP, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RIGHT_CLASP() { return getToken(LatinParser.RIGHT_CLASP, 0); }
		public IndexAccessContext(LvalueSuffixContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterIndexAccess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitIndexAccess(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitIndexAccess(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FieldAccessContext extends LvalueSuffixContext {
		public TerminalNode DOT() { return getToken(LatinParser.DOT, 0); }
		public TerminalNode ID() { return getToken(LatinParser.ID, 0); }
		public FieldAccessContext(LvalueSuffixContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterFieldAccess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitFieldAccess(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitFieldAccess(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LvalueSuffixContext lvalueSuffix() throws RecognitionException {
		LvalueSuffixContext _localctx = new LvalueSuffixContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_lvalueSuffix);
		try {
			setState(336);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DOT:
				_localctx = new FieldAccessContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(330);
				match(DOT);
				setState(331);
				match(ID);
				}
				break;
			case LEFT_CLASP:
				_localctx = new IndexAccessContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(332);
				match(LEFT_CLASP);
				setState(333);
				expression();
				setState(334);
				match(RIGHT_CLASP);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignmentContext extends ParserRuleContext {
		public LvalueContext lvalue() {
			return getRuleContext(LvalueContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(LatinParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(LatinParser.SEMICOLON, 0); }
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(338);
			lvalue();
			setState(339);
			match(ASSIGN);
			setState(340);
			expression();
			setState(342);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
			case 1:
				{
				setState(341);
				match(SEMICOLON);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	 
		public TypeContext() { }
		public void copyFrom(TypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TypeIDContext extends TypeContext {
		public TerminalNode ID() { return getToken(LatinParser.ID, 0); }
		public TypeIDContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterTypeID(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitTypeID(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitTypeID(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TypeNumerusContext extends TypeContext {
		public TerminalNode NUMERUS() { return getToken(LatinParser.NUMERUS, 0); }
		public TypeNumerusContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterTypeNumerus(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitTypeNumerus(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitTypeNumerus(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TypeDecimalisContext extends TypeContext {
		public TerminalNode DECIMALIS() { return getToken(LatinParser.DECIMALIS, 0); }
		public TypeDecimalisContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterTypeDecimalis(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitTypeDecimalis(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitTypeDecimalis(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TypeTextumContext extends TypeContext {
		public TerminalNode TEXTUM() { return getToken(LatinParser.TEXTUM, 0); }
		public TypeTextumContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterTypeTextum(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitTypeTextum(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitTypeTextum(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TypeBoolContext extends TypeContext {
		public TerminalNode BOOL() { return getToken(LatinParser.BOOL, 0); }
		public TypeBoolContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterTypeBool(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitTypeBool(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitTypeBool(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TypeLitteraContext extends TypeContext {
		public TerminalNode LITTERA() { return getToken(LatinParser.LITTERA, 0); }
		public TypeLitteraContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterTypeLittera(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitTypeLittera(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitTypeLittera(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_type);
		try {
			setState(350);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NUMERUS:
				_localctx = new TypeNumerusContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(344);
				match(NUMERUS);
				}
				break;
			case TEXTUM:
				_localctx = new TypeTextumContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(345);
				match(TEXTUM);
				}
				break;
			case DECIMALIS:
				_localctx = new TypeDecimalisContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(346);
				match(DECIMALIS);
				}
				break;
			case LITTERA:
				_localctx = new TypeLitteraContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(347);
				match(LITTERA);
				}
				break;
			case BOOL:
				_localctx = new TypeBoolContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(348);
				match(BOOL);
				}
				break;
			case ID:
				_localctx = new TypeIDContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(349);
				match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(LatinParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(LatinParser.COMMA, i);
		}
		public ArgumentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitArgumentList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitArgumentList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentListContext argumentList() throws RecognitionException {
		ArgumentListContext _localctx = new ArgumentListContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_argumentList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(352);
			expression();
			setState(357);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(353);
				match(COMMA);
				setState(354);
				expression();
				}
				}
				setState(359);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprNewInstanceContext extends ExpressionContext {
		public NewInstanceContext newInstance() {
			return getRuleContext(NewInstanceContext.class,0);
		}
		public ExprNewInstanceContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterExprNewInstance(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitExprNewInstance(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitExprNewInstance(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprStringContext extends ExpressionContext {
		public StringExpressionContext stringExpression() {
			return getRuleContext(StringExpressionContext.class,0);
		}
		public ExprStringContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterExprString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitExprString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitExprString(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprBooleanContext extends ExpressionContext {
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public ExprBooleanContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterExprBoolean(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitExprBoolean(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitExprBoolean(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprNumericContext extends ExpressionContext {
		public NumericExpressionContext numericExpression() {
			return getRuleContext(NumericExpressionContext.class,0);
		}
		public ExprNumericContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterExprNumeric(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitExprNumeric(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitExprNumeric(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprArrayLiteralContext extends ExpressionContext {
		public ArrayLiteralContext arrayLiteral() {
			return getRuleContext(ArrayLiteralContext.class,0);
		}
		public ExprArrayLiteralContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterExprArrayLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitExprArrayLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitExprArrayLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_expression);
		try {
			setState(365);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				_localctx = new ExprBooleanContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(360);
				booleanExpression();
				}
				break;
			case 2:
				_localctx = new ExprNumericContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(361);
				numericExpression();
				}
				break;
			case 3:
				_localctx = new ExprStringContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(362);
				stringExpression();
				}
				break;
			case 4:
				_localctx = new ExprArrayLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(363);
				arrayLiteral();
				}
				break;
			case 5:
				_localctx = new ExprNewInstanceContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(364);
				newInstance();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BooleanExpressionContext extends ParserRuleContext {
		public BooleanOrExpressionContext booleanOrExpression() {
			return getRuleContext(BooleanOrExpressionContext.class,0);
		}
		public BooleanExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterBooleanExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitBooleanExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitBooleanExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanExpressionContext booleanExpression() throws RecognitionException {
		BooleanExpressionContext _localctx = new BooleanExpressionContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_booleanExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(367);
			booleanOrExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BooleanOrExpressionContext extends ParserRuleContext {
		public List<BooleanAndExpressionContext> booleanAndExpression() {
			return getRuleContexts(BooleanAndExpressionContext.class);
		}
		public BooleanAndExpressionContext booleanAndExpression(int i) {
			return getRuleContext(BooleanAndExpressionContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(LatinParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(LatinParser.OR, i);
		}
		public BooleanOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanOrExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterBooleanOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitBooleanOrExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitBooleanOrExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanOrExpressionContext booleanOrExpression() throws RecognitionException {
		BooleanOrExpressionContext _localctx = new BooleanOrExpressionContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_booleanOrExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(369);
			booleanAndExpression();
			setState(374);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(370);
				match(OR);
				setState(371);
				booleanAndExpression();
				}
				}
				setState(376);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BooleanAndExpressionContext extends ParserRuleContext {
		public List<ComparisonExpressionContext> comparisonExpression() {
			return getRuleContexts(ComparisonExpressionContext.class);
		}
		public ComparisonExpressionContext comparisonExpression(int i) {
			return getRuleContext(ComparisonExpressionContext.class,i);
		}
		public List<TerminalNode> AND() { return getTokens(LatinParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(LatinParser.AND, i);
		}
		public BooleanAndExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanAndExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterBooleanAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitBooleanAndExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitBooleanAndExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanAndExpressionContext booleanAndExpression() throws RecognitionException {
		BooleanAndExpressionContext _localctx = new BooleanAndExpressionContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_booleanAndExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(377);
			comparisonExpression();
			setState(382);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(378);
				match(AND);
				setState(379);
				comparisonExpression();
				}
				}
				setState(384);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ComparisonExpressionContext extends ParserRuleContext {
		public ComparisonExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonExpression; }
	 
		public ComparisonExpressionContext() { }
		public void copyFrom(ComparisonExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CompNotContext extends ComparisonExpressionContext {
		public TerminalNode NOT() { return getToken(LatinParser.NOT, 0); }
		public ComparisonExpressionContext comparisonExpression() {
			return getRuleContext(ComparisonExpressionContext.class,0);
		}
		public CompNotContext(ComparisonExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterCompNot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitCompNot(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitCompNot(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CompParenContext extends ComparisonExpressionContext {
		public TerminalNode LEFT_PAREN() { return getToken(LatinParser.LEFT_PAREN, 0); }
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(LatinParser.RIGHT_PAREN, 0); }
		public CompParenContext(ComparisonExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterCompParen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitCompParen(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitCompParen(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CompCompareContext extends ComparisonExpressionContext {
		public List<ComparisonOperandContext> comparisonOperand() {
			return getRuleContexts(ComparisonOperandContext.class);
		}
		public ComparisonOperandContext comparisonOperand(int i) {
			return getRuleContext(ComparisonOperandContext.class,i);
		}
		public RelationalOpContext relationalOp() {
			return getRuleContext(RelationalOpContext.class,0);
		}
		public CompCompareContext(ComparisonExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterCompCompare(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitCompCompare(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitCompCompare(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonExpressionContext comparisonExpression() throws RecognitionException {
		ComparisonExpressionContext _localctx = new ComparisonExpressionContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_comparisonExpression);
		int _la;
		try {
			setState(397);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
			case 1:
				_localctx = new CompNotContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(385);
				match(NOT);
				setState(386);
				comparisonExpression();
				}
				break;
			case 2:
				_localctx = new CompCompareContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(387);
				comparisonOperand();
				setState(391);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 64512L) != 0)) {
					{
					setState(388);
					relationalOp();
					setState(389);
					comparisonOperand();
					}
				}

				}
				break;
			case 3:
				_localctx = new CompParenContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(393);
				match(LEFT_PAREN);
				setState(394);
				booleanExpression();
				setState(395);
				match(RIGHT_PAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ComparisonOperandContext extends ParserRuleContext {
		public ComparisonOperandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonOperand; }
	 
		public ComparisonOperandContext() { }
		public void copyFrom(ComparisonOperandContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CompBooleanContext extends ComparisonOperandContext {
		public BooleanLiteralContext booleanLiteral() {
			return getRuleContext(BooleanLiteralContext.class,0);
		}
		public CompBooleanContext(ComparisonOperandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterCompBoolean(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitCompBoolean(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitCompBoolean(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CompNumericContext extends ComparisonOperandContext {
		public NumericExpressionContext numericExpression() {
			return getRuleContext(NumericExpressionContext.class,0);
		}
		public CompNumericContext(ComparisonOperandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterCompNumeric(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitCompNumeric(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitCompNumeric(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CompStringContext extends ComparisonOperandContext {
		public StringExpressionContext stringExpression() {
			return getRuleContext(StringExpressionContext.class,0);
		}
		public CompStringContext(ComparisonOperandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterCompString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitCompString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitCompString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonOperandContext comparisonOperand() throws RecognitionException {
		ComparisonOperandContext _localctx = new ComparisonOperandContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_comparisonOperand);
		try {
			setState(402);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,47,_ctx) ) {
			case 1:
				_localctx = new CompNumericContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(399);
				numericExpression();
				}
				break;
			case 2:
				_localctx = new CompBooleanContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(400);
				booleanLiteral();
				}
				break;
			case 3:
				_localctx = new CompStringContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(401);
				stringExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RelationalOpContext extends ParserRuleContext {
		public TerminalNode IDENTIC() { return getToken(LatinParser.IDENTIC, 0); }
		public TerminalNode DIFF() { return getToken(LatinParser.DIFF, 0); }
		public TerminalNode MINOR() { return getToken(LatinParser.MINOR, 0); }
		public TerminalNode MAJOR() { return getToken(LatinParser.MAJOR, 0); }
		public TerminalNode MINORTO() { return getToken(LatinParser.MINORTO, 0); }
		public TerminalNode MAJORTO() { return getToken(LatinParser.MAJORTO, 0); }
		public RelationalOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationalOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterRelationalOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitRelationalOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitRelationalOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationalOpContext relationalOp() throws RecognitionException {
		RelationalOpContext _localctx = new RelationalOpContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_relationalOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(404);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 64512L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BooleanLiteralContext extends ParserRuleContext {
		public TerminalNode VERUM() { return getToken(LatinParser.VERUM, 0); }
		public TerminalNode FALSUS() { return getToken(LatinParser.FALSUS, 0); }
		public BooleanLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterBooleanLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitBooleanLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitBooleanLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanLiteralContext booleanLiteral() throws RecognitionException {
		BooleanLiteralContext _localctx = new BooleanLiteralContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_booleanLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(406);
			_la = _input.LA(1);
			if ( !(_la==VERUM || _la==FALSUS) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NumericExpressionContext extends ParserRuleContext {
		public AdditiveExpressionContext additiveExpression() {
			return getRuleContext(AdditiveExpressionContext.class,0);
		}
		public NumericExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterNumericExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitNumericExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitNumericExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericExpressionContext numericExpression() throws RecognitionException {
		NumericExpressionContext _localctx = new NumericExpressionContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_numericExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(408);
			additiveExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AdditiveExpressionContext extends ParserRuleContext {
		public List<MultiplicativeExpressionContext> multiplicativeExpression() {
			return getRuleContexts(MultiplicativeExpressionContext.class);
		}
		public MultiplicativeExpressionContext multiplicativeExpression(int i) {
			return getRuleContext(MultiplicativeExpressionContext.class,i);
		}
		public List<TerminalNode> PLUS() { return getTokens(LatinParser.PLUS); }
		public TerminalNode PLUS(int i) {
			return getToken(LatinParser.PLUS, i);
		}
		public List<TerminalNode> MINUS() { return getTokens(LatinParser.MINUS); }
		public TerminalNode MINUS(int i) {
			return getToken(LatinParser.MINUS, i);
		}
		public AdditiveExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additiveExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterAdditiveExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitAdditiveExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitAdditiveExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdditiveExpressionContext additiveExpression() throws RecognitionException {
		AdditiveExpressionContext _localctx = new AdditiveExpressionContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_additiveExpression);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(410);
			multiplicativeExpression();
			setState(415);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(411);
					_la = _input.LA(1);
					if ( !(_la==PLUS || _la==MINUS) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(412);
					multiplicativeExpression();
					}
					} 
				}
				setState(417);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MultiplicativeExpressionContext extends ParserRuleContext {
		public List<UnaryExpressionContext> unaryExpression() {
			return getRuleContexts(UnaryExpressionContext.class);
		}
		public UnaryExpressionContext unaryExpression(int i) {
			return getRuleContext(UnaryExpressionContext.class,i);
		}
		public List<TerminalNode> MULT() { return getTokens(LatinParser.MULT); }
		public TerminalNode MULT(int i) {
			return getToken(LatinParser.MULT, i);
		}
		public List<TerminalNode> SPLIT() { return getTokens(LatinParser.SPLIT); }
		public TerminalNode SPLIT(int i) {
			return getToken(LatinParser.SPLIT, i);
		}
		public MultiplicativeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicativeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterMultiplicativeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitMultiplicativeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitMultiplicativeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplicativeExpressionContext multiplicativeExpression() throws RecognitionException {
		MultiplicativeExpressionContext _localctx = new MultiplicativeExpressionContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_multiplicativeExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(418);
			unaryExpression();
			setState(423);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MULT || _la==SPLIT) {
				{
				{
				setState(419);
				_la = _input.LA(1);
				if ( !(_la==MULT || _la==SPLIT) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(420);
				unaryExpression();
				}
				}
				setState(425);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class UnaryExpressionContext extends ParserRuleContext {
		public UnaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryExpression; }
	 
		public UnaryExpressionContext() { }
		public void copyFrom(UnaryExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnaryPrimaryContext extends UnaryExpressionContext {
		public PrimaryNumericContext primaryNumeric() {
			return getRuleContext(PrimaryNumericContext.class,0);
		}
		public UnaryPrimaryContext(UnaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterUnaryPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitUnaryPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitUnaryPrimary(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnaryPrefixContext extends UnaryExpressionContext {
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public TerminalNode PLUS() { return getToken(LatinParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(LatinParser.MINUS, 0); }
		public TerminalNode ADD() { return getToken(LatinParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(LatinParser.SUB, 0); }
		public UnaryPrefixContext(UnaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterUnaryPrefix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitUnaryPrefix(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitUnaryPrefix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryExpressionContext unaryExpression() throws RecognitionException {
		UnaryExpressionContext _localctx = new UnaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_unaryExpression);
		int _la;
		try {
			setState(429);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PLUS:
			case MINUS:
			case ADD:
			case SUB:
				_localctx = new UnaryPrefixContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(426);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 3145752L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(427);
				unaryExpression();
				}
				break;
			case LEFT_PAREN:
			case ID:
			case DECIMAL:
			case INTEGER:
				_localctx = new UnaryPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(428);
				primaryNumeric();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryNumericContext extends ParserRuleContext {
		public PrimaryNumericContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryNumeric; }
	 
		public PrimaryNumericContext() { }
		public void copyFrom(PrimaryNumericContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryNumParenContext extends PrimaryNumericContext {
		public TerminalNode LEFT_PAREN() { return getToken(LatinParser.LEFT_PAREN, 0); }
		public NumericExpressionContext numericExpression() {
			return getRuleContext(NumericExpressionContext.class,0);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(LatinParser.RIGHT_PAREN, 0); }
		public PrimaryNumParenContext(PrimaryNumericContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterPrimaryNumParen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitPrimaryNumParen(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitPrimaryNumParen(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryNumLiteralContext extends PrimaryNumericContext {
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public PrimaryNumLiteralContext(PrimaryNumericContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterPrimaryNumLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitPrimaryNumLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitPrimaryNumLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryNumIdContext extends PrimaryNumericContext {
		public TerminalNode ID() { return getToken(LatinParser.ID, 0); }
		public List<AttributeAccessContext> attributeAccess() {
			return getRuleContexts(AttributeAccessContext.class);
		}
		public AttributeAccessContext attributeAccess(int i) {
			return getRuleContext(AttributeAccessContext.class,i);
		}
		public TerminalNode ADD() { return getToken(LatinParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(LatinParser.SUB, 0); }
		public PrimaryNumIdContext(PrimaryNumericContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterPrimaryNumId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitPrimaryNumId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitPrimaryNumId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryNumericContext primaryNumeric() throws RecognitionException {
		PrimaryNumericContext _localctx = new PrimaryNumericContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_primaryNumeric);
		int _la;
		try {
			int _alt;
			setState(446);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DECIMAL:
			case INTEGER:
				_localctx = new PrimaryNumLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(431);
				numericLiteral();
				}
				break;
			case ID:
				_localctx = new PrimaryNumIdContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(432);
				match(ID);
				setState(436);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,51,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(433);
						attributeAccess();
						}
						} 
					}
					setState(438);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,51,_ctx);
				}
				setState(440);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,52,_ctx) ) {
				case 1:
					{
					setState(439);
					_la = _input.LA(1);
					if ( !(_la==ADD || _la==SUB) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				}
				}
				break;
			case LEFT_PAREN:
				_localctx = new PrimaryNumParenContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(442);
				match(LEFT_PAREN);
				setState(443);
				numericExpression();
				setState(444);
				match(RIGHT_PAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NumericLiteralContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(LatinParser.INTEGER, 0); }
		public TerminalNode DECIMAL() { return getToken(LatinParser.DECIMAL, 0); }
		public NumericLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterNumericLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitNumericLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitNumericLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericLiteralContext numericLiteral() throws RecognitionException {
		NumericLiteralContext _localctx = new NumericLiteralContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_numericLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(448);
			_la = _input.LA(1);
			if ( !(_la==DECIMAL || _la==INTEGER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StringExpressionContext extends ParserRuleContext {
		public StringAdditiveExpressionContext stringAdditiveExpression() {
			return getRuleContext(StringAdditiveExpressionContext.class,0);
		}
		public StringExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterStringExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitStringExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitStringExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringExpressionContext stringExpression() throws RecognitionException {
		StringExpressionContext _localctx = new StringExpressionContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_stringExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(450);
			stringAdditiveExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StringAdditiveExpressionContext extends ParserRuleContext {
		public List<StringAdditiveItemContext> stringAdditiveItem() {
			return getRuleContexts(StringAdditiveItemContext.class);
		}
		public StringAdditiveItemContext stringAdditiveItem(int i) {
			return getRuleContext(StringAdditiveItemContext.class,i);
		}
		public List<TerminalNode> PLUS() { return getTokens(LatinParser.PLUS); }
		public TerminalNode PLUS(int i) {
			return getToken(LatinParser.PLUS, i);
		}
		public StringAdditiveExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringAdditiveExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterStringAdditiveExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitStringAdditiveExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitStringAdditiveExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringAdditiveExpressionContext stringAdditiveExpression() throws RecognitionException {
		StringAdditiveExpressionContext _localctx = new StringAdditiveExpressionContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_stringAdditiveExpression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(452);
			stringAdditiveItem();
			setState(457);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,54,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(453);
					match(PLUS);
					setState(454);
					stringAdditiveItem();
					}
					} 
				}
				setState(459);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,54,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StringAdditiveItemContext extends ParserRuleContext {
		public StringAdditiveItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringAdditiveItem; }
	 
		public StringAdditiveItemContext() { }
		public void copyFrom(StringAdditiveItemContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringItemNumericContext extends StringAdditiveItemContext {
		public NumericExpressionContext numericExpression() {
			return getRuleContext(NumericExpressionContext.class,0);
		}
		public StringItemNumericContext(StringAdditiveItemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterStringItemNumeric(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitStringItemNumeric(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitStringItemNumeric(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringItemBooleanContext extends StringAdditiveItemContext {
		public BooleanLiteralContext booleanLiteral() {
			return getRuleContext(BooleanLiteralContext.class,0);
		}
		public StringItemBooleanContext(StringAdditiveItemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterStringItemBoolean(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitStringItemBoolean(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitStringItemBoolean(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringItemPrimaryContext extends StringAdditiveItemContext {
		public StringPrimaryContext stringPrimary() {
			return getRuleContext(StringPrimaryContext.class,0);
		}
		public StringItemPrimaryContext(StringAdditiveItemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterStringItemPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitStringItemPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitStringItemPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringAdditiveItemContext stringAdditiveItem() throws RecognitionException {
		StringAdditiveItemContext _localctx = new StringAdditiveItemContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_stringAdditiveItem);
		try {
			setState(463);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,55,_ctx) ) {
			case 1:
				_localctx = new StringItemPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(460);
				stringPrimary();
				}
				break;
			case 2:
				_localctx = new StringItemNumericContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(461);
				numericExpression();
				}
				break;
			case 3:
				_localctx = new StringItemBooleanContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(462);
				booleanLiteral();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StringPrimaryContext extends ParserRuleContext {
		public StringPrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringPrimary; }
	 
		public StringPrimaryContext() { }
		public void copyFrom(StringPrimaryContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringPrimStringContext extends StringPrimaryContext {
		public TerminalNode STRING() { return getToken(LatinParser.STRING, 0); }
		public StringPrimStringContext(StringPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterStringPrimString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitStringPrimString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitStringPrimString(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringPrimCharContext extends StringPrimaryContext {
		public TerminalNode CHAR() { return getToken(LatinParser.CHAR, 0); }
		public StringPrimCharContext(StringPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterStringPrimChar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitStringPrimChar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitStringPrimChar(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringPrimIdContext extends StringPrimaryContext {
		public TerminalNode ID() { return getToken(LatinParser.ID, 0); }
		public List<AttributeAccessContext> attributeAccess() {
			return getRuleContexts(AttributeAccessContext.class);
		}
		public AttributeAccessContext attributeAccess(int i) {
			return getRuleContext(AttributeAccessContext.class,i);
		}
		public StringPrimIdContext(StringPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterStringPrimId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitStringPrimId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitStringPrimId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringPrimaryContext stringPrimary() throws RecognitionException {
		StringPrimaryContext _localctx = new StringPrimaryContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_stringPrimary);
		try {
			int _alt;
			setState(474);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING:
				_localctx = new StringPrimStringContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(465);
				match(STRING);
				}
				break;
			case CHAR:
				_localctx = new StringPrimCharContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(466);
				match(CHAR);
				}
				break;
			case ID:
				_localctx = new StringPrimIdContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(467);
				match(ID);
				setState(471);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,56,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(468);
						attributeAccess();
						}
						} 
					}
					setState(473);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,56,_ctx);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AttributeAccessContext extends ParserRuleContext {
		public AttributeAccessContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeAccess; }
	 
		public AttributeAccessContext() { }
		public void copyFrom(AttributeAccessContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AttrFieldContext extends AttributeAccessContext {
		public TerminalNode DOT() { return getToken(LatinParser.DOT, 0); }
		public TerminalNode ID() { return getToken(LatinParser.ID, 0); }
		public AttrFieldContext(AttributeAccessContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterAttrField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitAttrField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitAttrField(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AttrCallContext extends AttributeAccessContext {
		public TerminalNode LEFT_PAREN() { return getToken(LatinParser.LEFT_PAREN, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(LatinParser.RIGHT_PAREN, 0); }
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public AttrCallContext(AttributeAccessContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterAttrCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitAttrCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitAttrCall(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AttrIndexContext extends AttributeAccessContext {
		public TerminalNode LEFT_CLASP() { return getToken(LatinParser.LEFT_CLASP, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RIGHT_CLASP() { return getToken(LatinParser.RIGHT_CLASP, 0); }
		public AttrIndexContext(AttributeAccessContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterAttrIndex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitAttrIndex(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitAttrIndex(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeAccessContext attributeAccess() throws RecognitionException {
		AttributeAccessContext _localctx = new AttributeAccessContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_attributeAccess);
		int _la;
		try {
			setState(487);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DOT:
				_localctx = new AttrFieldContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(476);
				match(DOT);
				setState(477);
				match(ID);
				}
				break;
			case LEFT_CLASP:
				_localctx = new AttrIndexContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(478);
				match(LEFT_CLASP);
				setState(479);
				expression();
				setState(480);
				match(RIGHT_CLASP);
				}
				break;
			case LEFT_PAREN:
				_localctx = new AttrCallContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(482);
				match(LEFT_PAREN);
				setState(484);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1121397133194821656L) != 0)) {
					{
					setState(483);
					argumentList();
					}
				}

				setState(486);
				match(RIGHT_PAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001<\u01ea\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002"+
		"#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007\'\u0002"+
		"(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007+\u0002,\u0007,\u0002"+
		"-\u0007-\u0002.\u0007.\u0002/\u0007/\u0001\u0000\u0003\u0000b\b\u0000"+
		"\u0001\u0000\u0001\u0000\u0003\u0000f\b\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0003\u0000l\b\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0004\u0001q\b\u0001\u000b\u0001\f\u0001r\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0005\u0002y\b\u0002\n\u0002\f\u0002|\t"+
		"\u0002\u0001\u0002\u0003\u0002\u007f\b\u0002\u0001\u0003\u0005\u0003\u0082"+
		"\b\u0003\n\u0003\f\u0003\u0085\t\u0003\u0001\u0004\u0001\u0004\u0003\u0004"+
		"\u0089\b\u0004\u0001\u0005\u0005\u0005\u008c\b\u0005\n\u0005\f\u0005\u008f"+
		"\t\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0003\u0006\u009d\b\u0006\u0003\u0006\u009f\b\u0006\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u00a6\b\u0007"+
		"\u0001\u0007\u0003\u0007\u00a9\b\u0007\u0001\u0007\u0003\u0007\u00ac\b"+
		"\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b"+
		"\u0003\b\u00b6\b\b\u0001\b\u0003\b\u00b9\b\b\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0005\t\u00bf\b\t\n\t\f\t\u00c2\t\t\u0001\t\u0001\t\u0001\n\u0001\n"+
		"\u0005\n\u00c8\b\n\n\n\f\n\u00cb\t\n\u0001\n\u0001\n\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0005\u000b\u00d5"+
		"\b\u000b\n\u000b\f\u000b\u00d8\t\u000b\u0001\u000b\u0003\u000b\u00db\b"+
		"\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u00df\b\u000b\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0003\u000e\u00f1\b\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u00fa\b\u000f\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0003\u0010\u0103\b\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0003\u0011\u010d\b\u0011"+
		"\u0001\u0011\u0001\u0011\u0003\u0011\u0111\b\u0011\u0001\u0012\u0001\u0012"+
		"\u0003\u0012\u0115\b\u0012\u0001\u0013\u0001\u0013\u0003\u0013\u0119\b"+
		"\u0013\u0001\u0013\u0001\u0013\u0003\u0013\u011d\b\u0013\u0003\u0013\u011f"+
		"\b\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0003\u0014\u0125"+
		"\b\u0014\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0003\u0015\u012b"+
		"\b\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0003\u0015\u0130\b\u0015"+
		"\u0003\u0015\u0132\b\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0005\u0016\u0138\b\u0016\n\u0016\f\u0016\u013b\t\u0016\u0001\u0016\u0003"+
		"\u0016\u013e\b\u0016\u0001\u0017\u0001\u0017\u0003\u0017\u0142\b\u0017"+
		"\u0001\u0018\u0001\u0018\u0005\u0018\u0146\b\u0018\n\u0018\f\u0018\u0149"+
		"\t\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001"+
		"\u0019\u0003\u0019\u0151\b\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0003\u001a\u0157\b\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0001"+
		"\u001b\u0001\u001b\u0001\u001b\u0003\u001b\u015f\b\u001b\u0001\u001c\u0001"+
		"\u001c\u0001\u001c\u0005\u001c\u0164\b\u001c\n\u001c\f\u001c\u0167\t\u001c"+
		"\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0003\u001d"+
		"\u016e\b\u001d\u0001\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0001\u001f"+
		"\u0005\u001f\u0175\b\u001f\n\u001f\f\u001f\u0178\t\u001f\u0001 \u0001"+
		" \u0001 \u0005 \u017d\b \n \f \u0180\t \u0001!\u0001!\u0001!\u0001!\u0001"+
		"!\u0001!\u0003!\u0188\b!\u0001!\u0001!\u0001!\u0001!\u0003!\u018e\b!\u0001"+
		"\"\u0001\"\u0001\"\u0003\"\u0193\b\"\u0001#\u0001#\u0001$\u0001$\u0001"+
		"%\u0001%\u0001&\u0001&\u0001&\u0005&\u019e\b&\n&\f&\u01a1\t&\u0001\'\u0001"+
		"\'\u0001\'\u0005\'\u01a6\b\'\n\'\f\'\u01a9\t\'\u0001(\u0001(\u0001(\u0003"+
		"(\u01ae\b(\u0001)\u0001)\u0001)\u0005)\u01b3\b)\n)\f)\u01b6\t)\u0001)"+
		"\u0003)\u01b9\b)\u0001)\u0001)\u0001)\u0001)\u0003)\u01bf\b)\u0001*\u0001"+
		"*\u0001+\u0001+\u0001,\u0001,\u0001,\u0005,\u01c8\b,\n,\f,\u01cb\t,\u0001"+
		"-\u0001-\u0001-\u0003-\u01d0\b-\u0001.\u0001.\u0001.\u0001.\u0005.\u01d6"+
		"\b.\n.\f.\u01d9\t.\u0003.\u01db\b.\u0001/\u0001/\u0001/\u0001/\u0001/"+
		"\u0001/\u0001/\u0001/\u0003/\u01e5\b/\u0001/\u0003/\u01e8\b/\u0001/\u0000"+
		"\u00000\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018"+
		"\u001a\u001c\u001e \"$&(*,.02468:<>@BDFHJLNPRTVXZ\\^\u0000\b\u0002\u0000"+
		"\t\t))\u0001\u0000\n\u000f\u0001\u0000&\'\u0001\u0000\u0003\u0004\u0001"+
		"\u0000\u0005\u0006\u0002\u0000\u0003\u0004\u0014\u0015\u0001\u0000\u0014"+
		"\u0015\u0001\u000089\u020c\u0000a\u0001\u0000\u0000\u0000\u0002p\u0001"+
		"\u0000\u0000\u0000\u0004t\u0001\u0000\u0000\u0000\u0006\u0083\u0001\u0000"+
		"\u0000\u0000\b\u0088\u0001\u0000\u0000\u0000\n\u008d\u0001\u0000\u0000"+
		"\u0000\f\u009e\u0001\u0000\u0000\u0000\u000e\u00a0\u0001\u0000\u0000\u0000"+
		"\u0010\u00ad\u0001\u0000\u0000\u0000\u0012\u00ba\u0001\u0000\u0000\u0000"+
		"\u0014\u00c5\u0001\u0000\u0000\u0000\u0016\u00ce\u0001\u0000\u0000\u0000"+
		"\u0018\u00e0\u0001\u0000\u0000\u0000\u001a\u00e6\u0001\u0000\u0000\u0000"+
		"\u001c\u00e9\u0001\u0000\u0000\u0000\u001e\u00f2\u0001\u0000\u0000\u0000"+
		" \u00fb\u0001\u0000\u0000\u0000\"\u0110\u0001\u0000\u0000\u0000$\u0114"+
		"\u0001\u0000\u0000\u0000&\u011e\u0001\u0000\u0000\u0000(\u0120\u0001\u0000"+
		"\u0000\u0000*\u0131\u0001\u0000\u0000\u0000,\u0133\u0001\u0000\u0000\u0000"+
		".\u0141\u0001\u0000\u0000\u00000\u0143\u0001\u0000\u0000\u00002\u0150"+
		"\u0001\u0000\u0000\u00004\u0152\u0001\u0000\u0000\u00006\u015e\u0001\u0000"+
		"\u0000\u00008\u0160\u0001\u0000\u0000\u0000:\u016d\u0001\u0000\u0000\u0000"+
		"<\u016f\u0001\u0000\u0000\u0000>\u0171\u0001\u0000\u0000\u0000@\u0179"+
		"\u0001\u0000\u0000\u0000B\u018d\u0001\u0000\u0000\u0000D\u0192\u0001\u0000"+
		"\u0000\u0000F\u0194\u0001\u0000\u0000\u0000H\u0196\u0001\u0000\u0000\u0000"+
		"J\u0198\u0001\u0000\u0000\u0000L\u019a\u0001\u0000\u0000\u0000N\u01a2"+
		"\u0001\u0000\u0000\u0000P\u01ad\u0001\u0000\u0000\u0000R\u01be\u0001\u0000"+
		"\u0000\u0000T\u01c0\u0001\u0000\u0000\u0000V\u01c2\u0001\u0000\u0000\u0000"+
		"X\u01c4\u0001\u0000\u0000\u0000Z\u01cf\u0001\u0000\u0000\u0000\\\u01da"+
		"\u0001\u0000\u0000\u0000^\u01e7\u0001\u0000\u0000\u0000`b\u0003\u0002"+
		"\u0001\u0000a`\u0001\u0000\u0000\u0000ab\u0001\u0000\u0000\u0000be\u0001"+
		"\u0000\u0000\u0000cd\u0005\u0007\u0000\u0000df\u0003\u0006\u0003\u0000"+
		"ec\u0001\u0000\u0000\u0000ef\u0001\u0000\u0000\u0000fg\u0001\u0000\u0000"+
		"\u0000gh\u0005\b\u0000\u0000hi\u0003\n\u0005\u0000ik\u0007\u0000\u0000"+
		"\u0000jl\u0005\u0017\u0000\u0000kj\u0001\u0000\u0000\u0000kl\u0001\u0000"+
		"\u0000\u0000lm\u0001\u0000\u0000\u0000mn\u0005\u0000\u0000\u0001n\u0001"+
		"\u0001\u0000\u0000\u0000oq\u0003\u0004\u0002\u0000po\u0001\u0000\u0000"+
		"\u0000qr\u0001\u0000\u0000\u0000rp\u0001\u0000\u0000\u0000rs\u0001\u0000"+
		"\u0000\u0000s\u0003\u0001\u0000\u0000\u0000tu\u00053\u0000\u0000uz\u0005"+
		"7\u0000\u0000vw\u0005\u0019\u0000\u0000wy\u00057\u0000\u0000xv\u0001\u0000"+
		"\u0000\u0000y|\u0001\u0000\u0000\u0000zx\u0001\u0000\u0000\u0000z{\u0001"+
		"\u0000\u0000\u0000{~\u0001\u0000\u0000\u0000|z\u0001\u0000\u0000\u0000"+
		"}\u007f\u0005\u0017\u0000\u0000~}\u0001\u0000\u0000\u0000~\u007f\u0001"+
		"\u0000\u0000\u0000\u007f\u0005\u0001\u0000\u0000\u0000\u0080\u0082\u0003"+
		"\b\u0004\u0000\u0081\u0080\u0001\u0000\u0000\u0000\u0082\u0085\u0001\u0000"+
		"\u0000\u0000\u0083\u0081\u0001\u0000\u0000\u0000\u0083\u0084\u0001\u0000"+
		"\u0000\u0000\u0084\u0007\u0001\u0000\u0000\u0000\u0085\u0083\u0001\u0000"+
		"\u0000\u0000\u0086\u0089\u0003\u000e\u0007\u0000\u0087\u0089\u0003\u0010"+
		"\b\u0000\u0088\u0086\u0001\u0000\u0000\u0000\u0088\u0087\u0001\u0000\u0000"+
		"\u0000\u0089\t\u0001\u0000\u0000\u0000\u008a\u008c\u0003\f\u0006\u0000"+
		"\u008b\u008a\u0001\u0000\u0000\u0000\u008c\u008f\u0001\u0000\u0000\u0000"+
		"\u008d\u008b\u0001\u0000\u0000\u0000\u008d\u008e\u0001\u0000\u0000\u0000"+
		"\u008e\u000b\u0001\u0000\u0000\u0000\u008f\u008d\u0001\u0000\u0000\u0000"+
		"\u0090\u009f\u00034\u001a\u0000\u0091\u009f\u0003*\u0015\u0000\u0092\u009f"+
		"\u0003,\u0016\u0000\u0093\u009f\u0003\u0016\u000b\u0000\u0094\u009f\u0003"+
		"\u001c\u000e\u0000\u0095\u009f\u0003\u001e\u000f\u0000\u0096\u009f\u0003"+
		" \u0010\u0000\u0097\u009f\u0003&\u0013\u0000\u0098\u009f\u0003\u000e\u0007"+
		"\u0000\u0099\u009f\u0003\u0010\b\u0000\u009a\u009c\u0003:\u001d\u0000"+
		"\u009b\u009d\u0005\u0017\u0000\u0000\u009c\u009b\u0001\u0000\u0000\u0000"+
		"\u009c\u009d\u0001\u0000\u0000\u0000\u009d\u009f\u0001\u0000\u0000\u0000"+
		"\u009e\u0090\u0001\u0000\u0000\u0000\u009e\u0091\u0001\u0000\u0000\u0000"+
		"\u009e\u0092\u0001\u0000\u0000\u0000\u009e\u0093\u0001\u0000\u0000\u0000"+
		"\u009e\u0094\u0001\u0000\u0000\u0000\u009e\u0095\u0001\u0000\u0000\u0000"+
		"\u009e\u0096\u0001\u0000\u0000\u0000\u009e\u0097\u0001\u0000\u0000\u0000"+
		"\u009e\u0098\u0001\u0000\u0000\u0000\u009e\u0099\u0001\u0000\u0000\u0000"+
		"\u009e\u009a\u0001\u0000\u0000\u0000\u009f\r\u0001\u0000\u0000\u0000\u00a0"+
		"\u00a1\u0005 \u0000\u0000\u00a1\u00a2\u00057\u0000\u0000\u00a2\u00a8\u0005"+
		"\u0016\u0000\u0000\u00a3\u00a5\u00036\u001b\u0000\u00a4\u00a6\u0003:\u001d"+
		"\u0000\u00a5\u00a4\u0001\u0000\u0000\u0000\u00a5\u00a6\u0001\u0000\u0000"+
		"\u0000\u00a6\u00a9\u0001\u0000\u0000\u0000\u00a7\u00a9\u0003:\u001d\u0000"+
		"\u00a8\u00a3\u0001\u0000\u0000\u0000\u00a8\u00a7\u0001\u0000\u0000\u0000"+
		"\u00a9\u00ab\u0001\u0000\u0000\u0000\u00aa\u00ac\u0005\u0017\u0000\u0000"+
		"\u00ab\u00aa\u0001\u0000\u0000\u0000\u00ab\u00ac\u0001\u0000\u0000\u0000"+
		"\u00ac\u000f\u0001\u0000\u0000\u0000\u00ad\u00ae\u0005(\u0000\u0000\u00ae"+
		"\u00af\u00057\u0000\u0000\u00af\u00b0\u0005\u001a\u0000\u0000\u00b0\u00b1"+
		"\u0003:\u001d\u0000\u00b1\u00b2\u0005\u001b\u0000\u0000\u00b2\u00b3\u0005"+
		"\u0016\u0000\u0000\u00b3\u00b5\u00036\u001b\u0000\u00b4\u00b6\u0003\u0012"+
		"\t\u0000\u00b5\u00b4\u0001\u0000\u0000\u0000\u00b5\u00b6\u0001\u0000\u0000"+
		"\u0000\u00b6\u00b8\u0001\u0000\u0000\u0000\u00b7\u00b9\u0005\u0017\u0000"+
		"\u0000\u00b8\u00b7\u0001\u0000\u0000\u0000\u00b8\u00b9\u0001\u0000\u0000"+
		"\u0000\u00b9\u0011\u0001\u0000\u0000\u0000\u00ba\u00bb\u0005\u001c\u0000"+
		"\u0000\u00bb\u00c0\u0003:\u001d\u0000\u00bc\u00bd\u0005\u0018\u0000\u0000"+
		"\u00bd\u00bf\u0003:\u001d\u0000\u00be\u00bc\u0001\u0000\u0000\u0000\u00bf"+
		"\u00c2\u0001\u0000\u0000\u0000\u00c0\u00be\u0001\u0000\u0000\u0000\u00c0"+
		"\u00c1\u0001\u0000\u0000\u0000\u00c1\u00c3\u0001\u0000\u0000\u0000\u00c2"+
		"\u00c0\u0001\u0000\u0000\u0000\u00c3\u00c4\u0005\u001d\u0000\u0000\u00c4"+
		"\u0013\u0001\u0000\u0000\u0000\u00c5\u00c9\u0005\u001c\u0000\u0000\u00c6"+
		"\u00c8\u0003\f\u0006\u0000\u00c7\u00c6\u0001\u0000\u0000\u0000\u00c8\u00cb"+
		"\u0001\u0000\u0000\u0000\u00c9\u00c7\u0001\u0000\u0000\u0000\u00c9\u00ca"+
		"\u0001\u0000\u0000\u0000\u00ca\u00cc\u0001\u0000\u0000\u0000\u00cb\u00c9"+
		"\u0001\u0000\u0000\u0000\u00cc\u00cd\u0005\u001d\u0000\u0000\u00cd\u0015"+
		"\u0001\u0000\u0000\u0000\u00ce\u00cf\u0005*\u0000\u0000\u00cf\u00d0\u0005"+
		"\u001e\u0000\u0000\u00d0\u00d1\u0003<\u001e\u0000\u00d1\u00d2\u0005\u001f"+
		"\u0000\u0000\u00d2\u00d6\u0003\u0014\n\u0000\u00d3\u00d5\u0003\u0018\f"+
		"\u0000\u00d4\u00d3\u0001\u0000\u0000\u0000\u00d5\u00d8\u0001\u0000\u0000"+
		"\u0000\u00d6\u00d4\u0001\u0000\u0000\u0000\u00d6\u00d7\u0001\u0000\u0000"+
		"\u0000\u00d7\u00da\u0001\u0000\u0000\u0000\u00d8\u00d6\u0001\u0000\u0000"+
		"\u0000\u00d9\u00db\u0003\u001a\r\u0000\u00da\u00d9\u0001\u0000\u0000\u0000"+
		"\u00da\u00db\u0001\u0000\u0000\u0000\u00db\u00dc\u0001\u0000\u0000\u0000"+
		"\u00dc\u00de\u0005)\u0000\u0000\u00dd\u00df\u0005\u0017\u0000\u0000\u00de"+
		"\u00dd\u0001\u0000\u0000\u0000\u00de\u00df\u0001\u0000\u0000\u0000\u00df"+
		"\u0017\u0001\u0000\u0000\u0000\u00e0\u00e1\u0005+\u0000\u0000\u00e1\u00e2"+
		"\u0005\u001e\u0000\u0000\u00e2\u00e3\u0003<\u001e\u0000\u00e3\u00e4\u0005"+
		"\u001f\u0000\u0000\u00e4\u00e5\u0003\u0014\n\u0000\u00e5\u0019\u0001\u0000"+
		"\u0000\u0000\u00e6\u00e7\u0005+\u0000\u0000\u00e7\u00e8\u0003\u0014\n"+
		"\u0000\u00e8\u001b\u0001\u0000\u0000\u0000\u00e9\u00ea\u0005,\u0000\u0000"+
		"\u00ea\u00eb\u0005\u001e\u0000\u0000\u00eb\u00ec\u0003<\u001e\u0000\u00ec"+
		"\u00ed\u0005\u001f\u0000\u0000\u00ed\u00ee\u0003\u0014\n\u0000\u00ee\u00f0"+
		"\u0005)\u0000\u0000\u00ef\u00f1\u0005\u0017\u0000\u0000\u00f0\u00ef\u0001"+
		"\u0000\u0000\u0000\u00f0\u00f1\u0001\u0000\u0000\u0000\u00f1\u001d\u0001"+
		"\u0000\u0000\u0000\u00f2\u00f3\u0005-\u0000\u0000\u00f3\u00f4\u0003\u0014"+
		"\n\u0000\u00f4\u00f5\u0005,\u0000\u0000\u00f5\u00f6\u0005\u001e\u0000"+
		"\u0000\u00f6\u00f7\u0003<\u001e\u0000\u00f7\u00f9\u0005\u001f\u0000\u0000"+
		"\u00f8\u00fa\u0005\u0017\u0000\u0000\u00f9\u00f8\u0001\u0000\u0000\u0000"+
		"\u00f9\u00fa\u0001\u0000\u0000\u0000\u00fa\u001f\u0001\u0000\u0000\u0000"+
		"\u00fb\u00fc\u0005.\u0000\u0000\u00fc\u00fd\u0005\u001e\u0000\u0000\u00fd"+
		"\u00fe\u0003\"\u0011\u0000\u00fe\u00ff\u0005\u0017\u0000\u0000\u00ff\u0100"+
		"\u0003<\u001e\u0000\u0100\u0102\u0005\u0017\u0000\u0000\u0101\u0103\u0003"+
		"$\u0012\u0000\u0102\u0101\u0001\u0000\u0000\u0000\u0102\u0103\u0001\u0000"+
		"\u0000\u0000\u0103\u0104\u0001\u0000\u0000\u0000\u0104\u0105\u0005\u001f"+
		"\u0000\u0000\u0105\u0106\u0003\u0014\n\u0000\u0106!\u0001\u0000\u0000"+
		"\u0000\u0107\u0108\u0005 \u0000\u0000\u0108\u0109\u00057\u0000\u0000\u0109"+
		"\u010a\u0005\u0016\u0000\u0000\u010a\u010c\u00036\u001b\u0000\u010b\u010d"+
		"\u0003:\u001d\u0000\u010c\u010b\u0001\u0000\u0000\u0000\u010c\u010d\u0001"+
		"\u0000\u0000\u0000\u010d\u0111\u0001\u0000\u0000\u0000\u010e\u0111\u0003"+
		"4\u001a\u0000\u010f\u0111\u0001\u0000\u0000\u0000\u0110\u0107\u0001\u0000"+
		"\u0000\u0000\u0110\u010e\u0001\u0000\u0000\u0000\u0110\u010f\u0001\u0000"+
		"\u0000\u0000\u0111#\u0001\u0000\u0000\u0000\u0112\u0115\u00034\u001a\u0000"+
		"\u0113\u0115\u0003:\u001d\u0000\u0114\u0112\u0001\u0000\u0000\u0000\u0114"+
		"\u0113\u0001\u0000\u0000\u0000\u0115%\u0001\u0000\u0000\u0000\u0116\u0118"+
		"\u0005/\u0000\u0000\u0117\u0119\u0005\u0017\u0000\u0000\u0118\u0117\u0001"+
		"\u0000\u0000\u0000\u0118\u0119\u0001\u0000\u0000\u0000\u0119\u011f\u0001"+
		"\u0000\u0000\u0000\u011a\u011c\u00050\u0000\u0000\u011b\u011d\u0005\u0017"+
		"\u0000\u0000\u011c\u011b\u0001\u0000\u0000\u0000\u011c\u011d\u0001\u0000"+
		"\u0000\u0000\u011d\u011f\u0001\u0000\u0000\u0000\u011e\u0116\u0001\u0000"+
		"\u0000\u0000\u011e\u011a\u0001\u0000\u0000\u0000\u011f\'\u0001\u0000\u0000"+
		"\u0000\u0120\u0121\u00054\u0000\u0000\u0121\u0122\u00057\u0000\u0000\u0122"+
		"\u0124\u0005\u001e\u0000\u0000\u0123\u0125\u00038\u001c\u0000\u0124\u0123"+
		"\u0001\u0000\u0000\u0000\u0124\u0125\u0001\u0000\u0000\u0000\u0125\u0126"+
		"\u0001\u0000\u0000\u0000\u0126\u0127\u0005\u001f\u0000\u0000\u0127)\u0001"+
		"\u0000\u0000\u0000\u0128\u012a\u00055\u0000\u0000\u0129\u012b\u0005\u0017"+
		"\u0000\u0000\u012a\u0129\u0001\u0000\u0000\u0000\u012a\u012b\u0001\u0000"+
		"\u0000\u0000\u012b\u0132\u0001\u0000\u0000\u0000\u012c\u012d\u00030\u0018"+
		"\u0000\u012d\u012f\u00055\u0000\u0000\u012e\u0130\u0005\u0017\u0000\u0000"+
		"\u012f\u012e\u0001\u0000\u0000\u0000\u012f\u0130\u0001\u0000\u0000\u0000"+
		"\u0130\u0132\u0001\u0000\u0000\u0000\u0131\u0128\u0001\u0000\u0000\u0000"+
		"\u0131\u012c\u0001\u0000\u0000\u0000\u0132+\u0001\u0000\u0000\u0000\u0133"+
		"\u0134\u00056\u0000\u0000\u0134\u0139\u0003.\u0017\u0000\u0135\u0136\u0005"+
		"6\u0000\u0000\u0136\u0138\u0003.\u0017\u0000\u0137\u0135\u0001\u0000\u0000"+
		"\u0000\u0138\u013b\u0001\u0000\u0000\u0000\u0139\u0137\u0001\u0000\u0000"+
		"\u0000\u0139\u013a\u0001\u0000\u0000\u0000\u013a\u013d\u0001\u0000\u0000"+
		"\u0000\u013b\u0139\u0001\u0000\u0000\u0000\u013c\u013e\u0005\u0017\u0000"+
		"\u0000\u013d\u013c\u0001\u0000\u0000\u0000\u013d\u013e\u0001\u0000\u0000"+
		"\u0000\u013e-\u0001\u0000\u0000\u0000\u013f\u0142\u0005:\u0000\u0000\u0140"+
		"\u0142\u0003:\u001d\u0000\u0141\u013f\u0001\u0000\u0000\u0000\u0141\u0140"+
		"\u0001\u0000\u0000\u0000\u0142/\u0001\u0000\u0000\u0000\u0143\u0147\u0005"+
		"7\u0000\u0000\u0144\u0146\u00032\u0019\u0000\u0145\u0144\u0001\u0000\u0000"+
		"\u0000\u0146\u0149\u0001\u0000\u0000\u0000\u0147\u0145\u0001\u0000\u0000"+
		"\u0000\u0147\u0148\u0001\u0000\u0000\u0000\u01481\u0001\u0000\u0000\u0000"+
		"\u0149\u0147\u0001\u0000\u0000\u0000\u014a\u014b\u0005\u0019\u0000\u0000"+
		"\u014b\u0151\u00057\u0000\u0000\u014c\u014d\u0005\u001a\u0000\u0000\u014d"+
		"\u014e\u0003:\u001d\u0000\u014e\u014f\u0005\u001b\u0000\u0000\u014f\u0151"+
		"\u0001\u0000\u0000\u0000\u0150\u014a\u0001\u0000\u0000\u0000\u0150\u014c"+
		"\u0001\u0000\u0000\u0000\u01513\u0001\u0000\u0000\u0000\u0152\u0153\u0003"+
		"0\u0018\u0000\u0153\u0154\u0005\u0010\u0000\u0000\u0154\u0156\u0003:\u001d"+
		"\u0000\u0155\u0157\u0005\u0017\u0000\u0000\u0156\u0155\u0001\u0000\u0000"+
		"\u0000\u0156\u0157\u0001\u0000\u0000\u0000\u01575\u0001\u0000\u0000\u0000"+
		"\u0158\u015f\u0005!\u0000\u0000\u0159\u015f\u0005\"\u0000\u0000\u015a"+
		"\u015f\u0005#\u0000\u0000\u015b\u015f\u0005%\u0000\u0000\u015c\u015f\u0005"+
		"$\u0000\u0000\u015d\u015f\u00057\u0000\u0000\u015e\u0158\u0001\u0000\u0000"+
		"\u0000\u015e\u0159\u0001\u0000\u0000\u0000\u015e\u015a\u0001\u0000\u0000"+
		"\u0000\u015e\u015b\u0001\u0000\u0000\u0000\u015e\u015c\u0001\u0000\u0000"+
		"\u0000\u015e\u015d\u0001\u0000\u0000\u0000\u015f7\u0001\u0000\u0000\u0000"+
		"\u0160\u0165\u0003:\u001d\u0000\u0161\u0162\u0005\u0018\u0000\u0000\u0162"+
		"\u0164\u0003:\u001d\u0000\u0163\u0161\u0001\u0000\u0000\u0000\u0164\u0167"+
		"\u0001\u0000\u0000\u0000\u0165\u0163\u0001\u0000\u0000\u0000\u0165\u0166"+
		"\u0001\u0000\u0000\u0000\u01669\u0001\u0000\u0000\u0000\u0167\u0165\u0001"+
		"\u0000\u0000\u0000\u0168\u016e\u0003<\u001e\u0000\u0169\u016e\u0003J%"+
		"\u0000\u016a\u016e\u0003V+\u0000\u016b\u016e\u0003\u0012\t\u0000\u016c"+
		"\u016e\u0003(\u0014\u0000\u016d\u0168\u0001\u0000\u0000\u0000\u016d\u0169"+
		"\u0001\u0000\u0000\u0000\u016d\u016a\u0001\u0000\u0000\u0000\u016d\u016b"+
		"\u0001\u0000\u0000\u0000\u016d\u016c\u0001\u0000\u0000\u0000\u016e;\u0001"+
		"\u0000\u0000\u0000\u016f\u0170\u0003>\u001f\u0000\u0170=\u0001\u0000\u0000"+
		"\u0000\u0171\u0176\u0003@ \u0000\u0172\u0173\u0005\u0012\u0000\u0000\u0173"+
		"\u0175\u0003@ \u0000\u0174\u0172\u0001\u0000\u0000\u0000\u0175\u0178\u0001"+
		"\u0000\u0000\u0000\u0176\u0174\u0001\u0000\u0000\u0000\u0176\u0177\u0001"+
		"\u0000\u0000\u0000\u0177?\u0001\u0000\u0000\u0000\u0178\u0176\u0001\u0000"+
		"\u0000\u0000\u0179\u017e\u0003B!\u0000\u017a\u017b\u0005\u0011\u0000\u0000"+
		"\u017b\u017d\u0003B!\u0000\u017c\u017a\u0001\u0000\u0000\u0000\u017d\u0180"+
		"\u0001\u0000\u0000\u0000\u017e\u017c\u0001\u0000\u0000\u0000\u017e\u017f"+
		"\u0001\u0000\u0000\u0000\u017fA\u0001\u0000\u0000\u0000\u0180\u017e\u0001"+
		"\u0000\u0000\u0000\u0181\u0182\u0005\u0013\u0000\u0000\u0182\u018e\u0003"+
		"B!\u0000\u0183\u0187\u0003D\"\u0000\u0184\u0185\u0003F#\u0000\u0185\u0186"+
		"\u0003D\"\u0000\u0186\u0188\u0001\u0000\u0000\u0000\u0187\u0184\u0001"+
		"\u0000\u0000\u0000\u0187\u0188\u0001\u0000\u0000\u0000\u0188\u018e\u0001"+
		"\u0000\u0000\u0000\u0189\u018a\u0005\u001e\u0000\u0000\u018a\u018b\u0003"+
		"<\u001e\u0000\u018b\u018c\u0005\u001f\u0000\u0000\u018c\u018e\u0001\u0000"+
		"\u0000\u0000\u018d\u0181\u0001\u0000\u0000\u0000\u018d\u0183\u0001\u0000"+
		"\u0000\u0000\u018d\u0189\u0001\u0000\u0000\u0000\u018eC\u0001\u0000\u0000"+
		"\u0000\u018f\u0193\u0003J%\u0000\u0190\u0193\u0003H$\u0000\u0191\u0193"+
		"\u0003V+\u0000\u0192\u018f\u0001\u0000\u0000\u0000\u0192\u0190\u0001\u0000"+
		"\u0000\u0000\u0192\u0191\u0001\u0000\u0000\u0000\u0193E\u0001\u0000\u0000"+
		"\u0000\u0194\u0195\u0007\u0001\u0000\u0000\u0195G\u0001\u0000\u0000\u0000"+
		"\u0196\u0197\u0007\u0002\u0000\u0000\u0197I\u0001\u0000\u0000\u0000\u0198"+
		"\u0199\u0003L&\u0000\u0199K\u0001\u0000\u0000\u0000\u019a\u019f\u0003"+
		"N\'\u0000\u019b\u019c\u0007\u0003\u0000\u0000\u019c\u019e\u0003N\'\u0000"+
		"\u019d\u019b\u0001\u0000\u0000\u0000\u019e\u01a1\u0001\u0000\u0000\u0000"+
		"\u019f\u019d\u0001\u0000\u0000\u0000\u019f\u01a0\u0001\u0000\u0000\u0000"+
		"\u01a0M\u0001\u0000\u0000\u0000\u01a1\u019f\u0001\u0000\u0000\u0000\u01a2"+
		"\u01a7\u0003P(\u0000\u01a3\u01a4\u0007\u0004\u0000\u0000\u01a4\u01a6\u0003"+
		"P(\u0000\u01a5\u01a3\u0001\u0000\u0000\u0000\u01a6\u01a9\u0001\u0000\u0000"+
		"\u0000\u01a7\u01a5\u0001\u0000\u0000\u0000\u01a7\u01a8\u0001\u0000\u0000"+
		"\u0000\u01a8O\u0001\u0000\u0000\u0000\u01a9\u01a7\u0001\u0000\u0000\u0000"+
		"\u01aa\u01ab\u0007\u0005\u0000\u0000\u01ab\u01ae\u0003P(\u0000\u01ac\u01ae"+
		"\u0003R)\u0000\u01ad\u01aa\u0001\u0000\u0000\u0000\u01ad\u01ac\u0001\u0000"+
		"\u0000\u0000\u01aeQ\u0001\u0000\u0000\u0000\u01af\u01bf\u0003T*\u0000"+
		"\u01b0\u01b4\u00057\u0000\u0000\u01b1\u01b3\u0003^/\u0000\u01b2\u01b1"+
		"\u0001\u0000\u0000\u0000\u01b3\u01b6\u0001\u0000\u0000\u0000\u01b4\u01b2"+
		"\u0001\u0000\u0000\u0000\u01b4\u01b5\u0001\u0000\u0000\u0000\u01b5\u01b8"+
		"\u0001\u0000\u0000\u0000\u01b6\u01b4\u0001\u0000\u0000\u0000\u01b7\u01b9"+
		"\u0007\u0006\u0000\u0000\u01b8\u01b7\u0001\u0000\u0000\u0000\u01b8\u01b9"+
		"\u0001\u0000\u0000\u0000\u01b9\u01bf\u0001\u0000\u0000\u0000\u01ba\u01bb"+
		"\u0005\u001e\u0000\u0000\u01bb\u01bc\u0003J%\u0000\u01bc\u01bd\u0005\u001f"+
		"\u0000\u0000\u01bd\u01bf\u0001\u0000\u0000\u0000\u01be\u01af\u0001\u0000"+
		"\u0000\u0000\u01be\u01b0\u0001\u0000\u0000\u0000\u01be\u01ba\u0001\u0000"+
		"\u0000\u0000\u01bfS\u0001\u0000\u0000\u0000\u01c0\u01c1\u0007\u0007\u0000"+
		"\u0000\u01c1U\u0001\u0000\u0000\u0000\u01c2\u01c3\u0003X,\u0000\u01c3"+
		"W\u0001\u0000\u0000\u0000\u01c4\u01c9\u0003Z-\u0000\u01c5\u01c6\u0005"+
		"\u0003\u0000\u0000\u01c6\u01c8\u0003Z-\u0000\u01c7\u01c5\u0001\u0000\u0000"+
		"\u0000\u01c8\u01cb\u0001\u0000\u0000\u0000\u01c9\u01c7\u0001\u0000\u0000"+
		"\u0000\u01c9\u01ca\u0001\u0000\u0000\u0000\u01caY\u0001\u0000\u0000\u0000"+
		"\u01cb\u01c9\u0001\u0000\u0000\u0000\u01cc\u01d0\u0003\\.\u0000\u01cd"+
		"\u01d0\u0003J%\u0000\u01ce\u01d0\u0003H$\u0000\u01cf\u01cc\u0001\u0000"+
		"\u0000\u0000\u01cf\u01cd\u0001\u0000\u0000\u0000\u01cf\u01ce\u0001\u0000"+
		"\u0000\u0000\u01d0[\u0001\u0000\u0000\u0000\u01d1\u01db\u0005:\u0000\u0000"+
		"\u01d2\u01db\u0005;\u0000\u0000\u01d3\u01d7\u00057\u0000\u0000\u01d4\u01d6"+
		"\u0003^/\u0000\u01d5\u01d4\u0001\u0000\u0000\u0000\u01d6\u01d9\u0001\u0000"+
		"\u0000\u0000\u01d7\u01d5\u0001\u0000\u0000\u0000\u01d7\u01d8\u0001\u0000"+
		"\u0000\u0000\u01d8\u01db\u0001\u0000\u0000\u0000\u01d9\u01d7\u0001\u0000"+
		"\u0000\u0000\u01da\u01d1\u0001\u0000\u0000\u0000\u01da\u01d2\u0001\u0000"+
		"\u0000\u0000\u01da\u01d3\u0001\u0000\u0000\u0000\u01db]\u0001\u0000\u0000"+
		"\u0000\u01dc\u01dd\u0005\u0019\u0000\u0000\u01dd\u01e8\u00057\u0000\u0000"+
		"\u01de\u01df\u0005\u001a\u0000\u0000\u01df\u01e0\u0003:\u001d\u0000\u01e0"+
		"\u01e1\u0005\u001b\u0000\u0000\u01e1\u01e8\u0001\u0000\u0000\u0000\u01e2"+
		"\u01e4\u0005\u001e\u0000\u0000\u01e3\u01e5\u00038\u001c\u0000\u01e4\u01e3"+
		"\u0001\u0000\u0000\u0000\u01e4\u01e5\u0001\u0000\u0000\u0000\u01e5\u01e6"+
		"\u0001\u0000\u0000\u0000\u01e6\u01e8\u0005\u001f\u0000\u0000\u01e7\u01dc"+
		"\u0001\u0000\u0000\u0000\u01e7\u01de\u0001\u0000\u0000\u0000\u01e7\u01e2"+
		"\u0001\u0000\u0000\u0000\u01e8_\u0001\u0000\u0000\u0000<aekrz~\u0083\u0088"+
		"\u008d\u009c\u009e\u00a5\u00a8\u00ab\u00b5\u00b8\u00c0\u00c9\u00d6\u00da"+
		"\u00de\u00f0\u00f9\u0102\u010c\u0110\u0114\u0118\u011c\u011e\u0124\u012a"+
		"\u012f\u0131\u0139\u013d\u0141\u0147\u0150\u0156\u015e\u0165\u016d\u0176"+
		"\u017e\u0187\u018d\u0192\u019f\u01a7\u01ad\u01b4\u01b8\u01be\u01c9\u01cf"+
		"\u01d7\u01da\u01e4\u01e7";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}