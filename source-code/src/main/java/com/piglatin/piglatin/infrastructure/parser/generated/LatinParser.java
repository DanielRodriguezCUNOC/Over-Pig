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
		NULL=40, SERIES=41, FINIS=42, SI=43, ALITER=44, DUM=45, FACERE=46, PER=47, 
		PERGE=48, INTERRUMPE=49, VARIABILES=50, MAIOR=51, IMPORT=52, NOVUS=53, 
		LEERE=54, IMPREMERE=55, ID=56, DECIMAL=57, INTEGER=58, STRING=59, CHAR=60, 
		WS=61;
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
			"'bool'", "'littera'", "'verum'", "'falsus'", "'null'", "'series'", "'finis'", 
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
			"DECIMALIS", "BOOL", "LITTERA", "VERUM", "FALSUS", "NULL", "SERIES", 
			"FINIS", "SI", "ALITER", "DUM", "FACERE", "PER", "PERGE", "INTERRUMPE", 
			"VARIABILES", "MAIOR", "IMPORT", "NOVUS", "LEERE", "IMPREMERE", "ID", 
			"DECIMAL", "INTEGER", "STRING", "CHAR", "WS"
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
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2297939450396147736L) != 0)) {
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
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
	 
		public DeclarationContext() { }
		public void copyFrom(DeclarationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DeclStandardContext extends DeclarationContext {
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
		public DeclStandardContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterDeclStandard(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitDeclStandard(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitDeclStandard(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DeclExplicitTypeContext extends DeclarationContext {
		public TerminalNode ESTO() { return getToken(LatinParser.ESTO, 0); }
		public TerminalNode ID() { return getToken(LatinParser.ID, 0); }
		public List<TerminalNode> COLON() { return getTokens(LatinParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(LatinParser.COLON, i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(LatinParser.SEMICOLON, 0); }
		public DeclExplicitTypeContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterDeclExplicitType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitDeclExplicitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitDeclExplicitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_declaration);
		int _la;
		try {
			setState(179);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				_localctx = new DeclStandardContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(160);
				match(ESTO);
				setState(161);
				match(ID);
				setState(162);
				match(COLON);
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
				setState(168);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEMICOLON) {
					{
					setState(167);
					match(SEMICOLON);
					}
				}

				}
				break;
			case 2:
				_localctx = new DeclExplicitTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(170);
				match(ESTO);
				setState(171);
				match(ID);
				setState(172);
				match(COLON);
				setState(173);
				expression();
				setState(174);
				match(COLON);
				setState(175);
				type();
				setState(177);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEMICOLON) {
					{
					setState(176);
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
			setState(181);
			match(SERIES);
			setState(182);
			match(ID);
			setState(183);
			match(LEFT_CLASP);
			setState(184);
			expression();
			setState(185);
			match(RIGHT_CLASP);
			setState(186);
			match(COLON);
			setState(187);
			type();
			setState(189);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				{
				setState(188);
				arrayLiteral();
				}
				break;
			}
			setState(192);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(191);
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
			setState(194);
			match(LEFT_BRACE);
			setState(195);
			expression();
			setState(200);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(196);
				match(COMMA);
				setState(197);
				expression();
				}
				}
				setState(202);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(203);
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
			setState(205);
			match(LEFT_BRACE);
			setState(209);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2297939450396147736L) != 0)) {
				{
				{
				setState(206);
				instruction();
				}
				}
				setState(211);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(212);
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
			setState(214);
			match(SI);
			setState(215);
			match(LEFT_PAREN);
			setState(216);
			booleanExpression();
			setState(217);
			match(RIGHT_PAREN);
			setState(218);
			block();
			setState(222);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(219);
					elseIfClause();
					}
					} 
				}
				setState(224);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			}
			setState(226);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ALITER) {
				{
				setState(225);
				elseClause();
				}
			}

			setState(228);
			match(FINIS);
			setState(230);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(229);
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
			setState(232);
			match(ALITER);
			setState(233);
			match(LEFT_PAREN);
			setState(234);
			booleanExpression();
			setState(235);
			match(RIGHT_PAREN);
			setState(236);
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
			setState(238);
			match(ALITER);
			setState(239);
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
			setState(241);
			match(DUM);
			setState(242);
			match(LEFT_PAREN);
			setState(243);
			booleanExpression();
			setState(244);
			match(RIGHT_PAREN);
			setState(245);
			block();
			setState(246);
			match(FINIS);
			setState(248);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(247);
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
			setState(250);
			match(FACERE);
			setState(251);
			block();
			setState(252);
			match(DUM);
			setState(253);
			match(LEFT_PAREN);
			setState(254);
			booleanExpression();
			setState(255);
			match(RIGHT_PAREN);
			setState(257);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(256);
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
			setState(259);
			match(PER);
			setState(260);
			match(LEFT_PAREN);
			setState(261);
			forInit();
			setState(262);
			match(SEMICOLON);
			setState(263);
			booleanExpression();
			setState(264);
			match(SEMICOLON);
			setState(266);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2242794539921702936L) != 0)) {
				{
				setState(265);
				forUpdate();
				}
			}

			setState(268);
			match(RIGHT_PAREN);
			setState(269);
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
			setState(280);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ESTO:
				_localctx = new ForInitDeclarationContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(271);
				match(ESTO);
				setState(272);
				match(ID);
				setState(273);
				match(COLON);
				setState(274);
				type();
				setState(276);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2242794539921702936L) != 0)) {
					{
					setState(275);
					expression();
					}
				}

				}
				break;
			case ID:
				_localctx = new ForInitAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(278);
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
			setState(284);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				_localctx = new ForUpdateAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(282);
				assignment();
				}
				break;
			case 2:
				_localctx = new ForUpdateExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(283);
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
			setState(294);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PERGE:
				_localctx = new JumpContinueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(286);
				match(PERGE);
				setState(288);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEMICOLON) {
					{
					setState(287);
					match(SEMICOLON);
					}
				}

				}
				break;
			case INTERRUMPE:
				_localctx = new JumpBreakContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(290);
				match(INTERRUMPE);
				setState(292);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEMICOLON) {
					{
					setState(291);
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
		public TerminalNode COLON() { return getToken(LatinParser.COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
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
			setState(296);
			match(NOVUS);
			setState(297);
			match(ID);
			setState(298);
			match(LEFT_PAREN);
			setState(300);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2242794539921702936L) != 0)) {
				{
				setState(299);
				argumentList();
				}
			}

			setState(302);
			match(RIGHT_PAREN);
			setState(305);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				{
				setState(303);
				match(COLON);
				setState(304);
				type();
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
			setState(316);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LEERE:
				_localctx = new ReadSimpleContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(307);
				match(LEERE);
				setState(309);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEMICOLON) {
					{
					setState(308);
					match(SEMICOLON);
					}
				}

				}
				break;
			case ID:
				_localctx = new ReadLvalueContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(311);
				lvalue();
				setState(312);
				match(LEERE);
				setState(314);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEMICOLON) {
					{
					setState(313);
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
			setState(318);
			match(IMPREMERE);
			setState(319);
			printItem();
			setState(324);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(320);
					match(IMPREMERE);
					setState(321);
					printItem();
					}
					} 
				}
				setState(326);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
			}
			setState(328);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(327);
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
			setState(332);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				_localctx = new PrintStringContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(330);
				match(STRING);
				}
				break;
			case 2:
				_localctx = new PrintExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(331);
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
			setState(334);
			match(ID);
			setState(338);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT || _la==LEFT_CLASP) {
				{
				{
				setState(335);
				lvalueSuffix();
				}
				}
				setState(340);
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
			setState(347);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DOT:
				_localctx = new FieldAccessContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(341);
				match(DOT);
				setState(342);
				match(ID);
				}
				break;
			case LEFT_CLASP:
				_localctx = new IndexAccessContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(343);
				match(LEFT_CLASP);
				setState(344);
				expression();
				setState(345);
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
			setState(349);
			lvalue();
			setState(350);
			match(ASSIGN);
			setState(351);
			expression();
			setState(353);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
			case 1:
				{
				setState(352);
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
			setState(361);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NUMERUS:
				_localctx = new TypeNumerusContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(355);
				match(NUMERUS);
				}
				break;
			case TEXTUM:
				_localctx = new TypeTextumContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(356);
				match(TEXTUM);
				}
				break;
			case DECIMALIS:
				_localctx = new TypeDecimalisContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(357);
				match(DECIMALIS);
				}
				break;
			case LITTERA:
				_localctx = new TypeLitteraContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(358);
				match(LITTERA);
				}
				break;
			case BOOL:
				_localctx = new TypeBoolContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(359);
				match(BOOL);
				}
				break;
			case ID:
				_localctx = new TypeIDContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(360);
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
			setState(363);
			expression();
			setState(368);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(364);
				match(COMMA);
				setState(365);
				expression();
				}
				}
				setState(370);
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
	public static class ExprNullContext extends ExpressionContext {
		public TerminalNode NULL() { return getToken(LatinParser.NULL, 0); }
		public ExprNullContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).enterExprNull(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LatinParserListener ) ((LatinParserListener)listener).exitExprNull(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LatinParserVisitor ) return ((LatinParserVisitor<? extends T>)visitor).visitExprNull(this);
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
			setState(377);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,44,_ctx) ) {
			case 1:
				_localctx = new ExprNullContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(371);
				match(NULL);
				}
				break;
			case 2:
				_localctx = new ExprBooleanContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(372);
				booleanExpression();
				}
				break;
			case 3:
				_localctx = new ExprNumericContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(373);
				numericExpression();
				}
				break;
			case 4:
				_localctx = new ExprStringContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(374);
				stringExpression();
				}
				break;
			case 5:
				_localctx = new ExprArrayLiteralContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(375);
				arrayLiteral();
				}
				break;
			case 6:
				_localctx = new ExprNewInstanceContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(376);
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
			setState(379);
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
			setState(381);
			booleanAndExpression();
			setState(386);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(382);
				match(OR);
				setState(383);
				booleanAndExpression();
				}
				}
				setState(388);
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
			setState(389);
			comparisonExpression();
			setState(394);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(390);
				match(AND);
				setState(391);
				comparisonExpression();
				}
				}
				setState(396);
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
			setState(409);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				_localctx = new CompNotContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(397);
				match(NOT);
				setState(398);
				comparisonExpression();
				}
				break;
			case 2:
				_localctx = new CompCompareContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(399);
				comparisonOperand();
				setState(403);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 64512L) != 0)) {
					{
					setState(400);
					relationalOp();
					setState(401);
					comparisonOperand();
					}
				}

				}
				break;
			case 3:
				_localctx = new CompParenContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(405);
				match(LEFT_PAREN);
				setState(406);
				booleanExpression();
				setState(407);
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
			setState(414);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
			case 1:
				_localctx = new CompNumericContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(411);
				numericExpression();
				}
				break;
			case 2:
				_localctx = new CompBooleanContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(412);
				booleanLiteral();
				}
				break;
			case 3:
				_localctx = new CompStringContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(413);
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
			setState(416);
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
			setState(418);
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
			setState(420);
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
			setState(422);
			multiplicativeExpression();
			setState(427);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,50,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(423);
					_la = _input.LA(1);
					if ( !(_la==PLUS || _la==MINUS) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(424);
					multiplicativeExpression();
					}
					} 
				}
				setState(429);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,50,_ctx);
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
			setState(430);
			unaryExpression();
			setState(435);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MULT || _la==SPLIT) {
				{
				{
				setState(431);
				_la = _input.LA(1);
				if ( !(_la==MULT || _la==SPLIT) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(432);
				unaryExpression();
				}
				}
				setState(437);
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
			setState(441);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PLUS:
			case MINUS:
			case ADD:
			case SUB:
				_localctx = new UnaryPrefixContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(438);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 3145752L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(439);
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
				setState(440);
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
			setState(458);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DECIMAL:
			case INTEGER:
				_localctx = new PrimaryNumLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(443);
				numericLiteral();
				}
				break;
			case ID:
				_localctx = new PrimaryNumIdContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(444);
				match(ID);
				setState(448);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,53,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(445);
						attributeAccess();
						}
						} 
					}
					setState(450);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,53,_ctx);
				}
				setState(452);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
				case 1:
					{
					setState(451);
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
				setState(454);
				match(LEFT_PAREN);
				setState(455);
				numericExpression();
				setState(456);
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
			setState(460);
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
			setState(462);
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
			setState(464);
			stringAdditiveItem();
			setState(469);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,56,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(465);
					match(PLUS);
					setState(466);
					stringAdditiveItem();
					}
					} 
				}
				setState(471);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,56,_ctx);
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
			setState(475);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,57,_ctx) ) {
			case 1:
				_localctx = new StringItemPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(472);
				stringPrimary();
				}
				break;
			case 2:
				_localctx = new StringItemNumericContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(473);
				numericExpression();
				}
				break;
			case 3:
				_localctx = new StringItemBooleanContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(474);
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
			setState(486);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING:
				_localctx = new StringPrimStringContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(477);
				match(STRING);
				}
				break;
			case CHAR:
				_localctx = new StringPrimCharContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(478);
				match(CHAR);
				}
				break;
			case ID:
				_localctx = new StringPrimIdContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(479);
				match(ID);
				setState(483);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,58,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(480);
						attributeAccess();
						}
						} 
					}
					setState(485);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,58,_ctx);
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
			setState(499);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DOT:
				_localctx = new AttrFieldContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(488);
				match(DOT);
				setState(489);
				match(ID);
				}
				break;
			case LEFT_CLASP:
				_localctx = new AttrIndexContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(490);
				match(LEFT_CLASP);
				setState(491);
				expression();
				setState(492);
				match(RIGHT_CLASP);
				}
				break;
			case LEFT_PAREN:
				_localctx = new AttrCallContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(494);
				match(LEFT_PAREN);
				setState(496);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2242794539921702936L) != 0)) {
					{
					setState(495);
					argumentList();
					}
				}

				setState(498);
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
		"\u0004\u0001=\u01f6\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
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
		"\u0001\u0007\u0003\u0007\u00a9\b\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u00b2\b\u0007"+
		"\u0003\u0007\u00b4\b\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0003\b\u00be\b\b\u0001\b\u0003\b\u00c1\b\b\u0001\t"+
		"\u0001\t\u0001\t\u0001\t\u0005\t\u00c7\b\t\n\t\f\t\u00ca\t\t\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0005\n\u00d0\b\n\n\n\f\n\u00d3\t\n\u0001\n\u0001\n"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0005\u000b\u00dd\b\u000b\n\u000b\f\u000b\u00e0\t\u000b\u0001\u000b\u0003"+
		"\u000b\u00e3\b\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u00e7\b\u000b"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001"+
		"\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0003\u000e\u00f9\b\u000e\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u0102\b\u000f"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0003\u0010\u010b\b\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0003\u0011"+
		"\u0115\b\u0011\u0001\u0011\u0001\u0011\u0003\u0011\u0119\b\u0011\u0001"+
		"\u0012\u0001\u0012\u0003\u0012\u011d\b\u0012\u0001\u0013\u0001\u0013\u0003"+
		"\u0013\u0121\b\u0013\u0001\u0013\u0001\u0013\u0003\u0013\u0125\b\u0013"+
		"\u0003\u0013\u0127\b\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0003\u0014\u012d\b\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0003\u0014"+
		"\u0132\b\u0014\u0001\u0015\u0001\u0015\u0003\u0015\u0136\b\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0003\u0015\u013b\b\u0015\u0003\u0015\u013d"+
		"\b\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0005\u0016\u0143"+
		"\b\u0016\n\u0016\f\u0016\u0146\t\u0016\u0001\u0016\u0003\u0016\u0149\b"+
		"\u0016\u0001\u0017\u0001\u0017\u0003\u0017\u014d\b\u0017\u0001\u0018\u0001"+
		"\u0018\u0005\u0018\u0151\b\u0018\n\u0018\f\u0018\u0154\t\u0018\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0003\u0019"+
		"\u015c\b\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0003\u001a"+
		"\u0162\b\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0001\u001b\u0003\u001b\u016a\b\u001b\u0001\u001c\u0001\u001c\u0001\u001c"+
		"\u0005\u001c\u016f\b\u001c\n\u001c\f\u001c\u0172\t\u001c\u0001\u001d\u0001"+
		"\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0003\u001d\u017a"+
		"\b\u001d\u0001\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0001\u001f\u0005"+
		"\u001f\u0181\b\u001f\n\u001f\f\u001f\u0184\t\u001f\u0001 \u0001 \u0001"+
		" \u0005 \u0189\b \n \f \u018c\t \u0001!\u0001!\u0001!\u0001!\u0001!\u0001"+
		"!\u0003!\u0194\b!\u0001!\u0001!\u0001!\u0001!\u0003!\u019a\b!\u0001\""+
		"\u0001\"\u0001\"\u0003\"\u019f\b\"\u0001#\u0001#\u0001$\u0001$\u0001%"+
		"\u0001%\u0001&\u0001&\u0001&\u0005&\u01aa\b&\n&\f&\u01ad\t&\u0001\'\u0001"+
		"\'\u0001\'\u0005\'\u01b2\b\'\n\'\f\'\u01b5\t\'\u0001(\u0001(\u0001(\u0003"+
		"(\u01ba\b(\u0001)\u0001)\u0001)\u0005)\u01bf\b)\n)\f)\u01c2\t)\u0001)"+
		"\u0003)\u01c5\b)\u0001)\u0001)\u0001)\u0001)\u0003)\u01cb\b)\u0001*\u0001"+
		"*\u0001+\u0001+\u0001,\u0001,\u0001,\u0005,\u01d4\b,\n,\f,\u01d7\t,\u0001"+
		"-\u0001-\u0001-\u0003-\u01dc\b-\u0001.\u0001.\u0001.\u0001.\u0005.\u01e2"+
		"\b.\n.\f.\u01e5\t.\u0003.\u01e7\b.\u0001/\u0001/\u0001/\u0001/\u0001/"+
		"\u0001/\u0001/\u0001/\u0003/\u01f1\b/\u0001/\u0003/\u01f4\b/\u0001/\u0000"+
		"\u00000\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018"+
		"\u001a\u001c\u001e \"$&(*,.02468:<>@BDFHJLNPRTVXZ\\^\u0000\b\u0002\u0000"+
		"\t\t**\u0001\u0000\n\u000f\u0001\u0000&\'\u0001\u0000\u0003\u0004\u0001"+
		"\u0000\u0005\u0006\u0002\u0000\u0003\u0004\u0014\u0015\u0001\u0000\u0014"+
		"\u0015\u0001\u00009:\u021b\u0000a\u0001\u0000\u0000\u0000\u0002p\u0001"+
		"\u0000\u0000\u0000\u0004t\u0001\u0000\u0000\u0000\u0006\u0083\u0001\u0000"+
		"\u0000\u0000\b\u0088\u0001\u0000\u0000\u0000\n\u008d\u0001\u0000\u0000"+
		"\u0000\f\u009e\u0001\u0000\u0000\u0000\u000e\u00b3\u0001\u0000\u0000\u0000"+
		"\u0010\u00b5\u0001\u0000\u0000\u0000\u0012\u00c2\u0001\u0000\u0000\u0000"+
		"\u0014\u00cd\u0001\u0000\u0000\u0000\u0016\u00d6\u0001\u0000\u0000\u0000"+
		"\u0018\u00e8\u0001\u0000\u0000\u0000\u001a\u00ee\u0001\u0000\u0000\u0000"+
		"\u001c\u00f1\u0001\u0000\u0000\u0000\u001e\u00fa\u0001\u0000\u0000\u0000"+
		" \u0103\u0001\u0000\u0000\u0000\"\u0118\u0001\u0000\u0000\u0000$\u011c"+
		"\u0001\u0000\u0000\u0000&\u0126\u0001\u0000\u0000\u0000(\u0128\u0001\u0000"+
		"\u0000\u0000*\u013c\u0001\u0000\u0000\u0000,\u013e\u0001\u0000\u0000\u0000"+
		".\u014c\u0001\u0000\u0000\u00000\u014e\u0001\u0000\u0000\u00002\u015b"+
		"\u0001\u0000\u0000\u00004\u015d\u0001\u0000\u0000\u00006\u0169\u0001\u0000"+
		"\u0000\u00008\u016b\u0001\u0000\u0000\u0000:\u0179\u0001\u0000\u0000\u0000"+
		"<\u017b\u0001\u0000\u0000\u0000>\u017d\u0001\u0000\u0000\u0000@\u0185"+
		"\u0001\u0000\u0000\u0000B\u0199\u0001\u0000\u0000\u0000D\u019e\u0001\u0000"+
		"\u0000\u0000F\u01a0\u0001\u0000\u0000\u0000H\u01a2\u0001\u0000\u0000\u0000"+
		"J\u01a4\u0001\u0000\u0000\u0000L\u01a6\u0001\u0000\u0000\u0000N\u01ae"+
		"\u0001\u0000\u0000\u0000P\u01b9\u0001\u0000\u0000\u0000R\u01ca\u0001\u0000"+
		"\u0000\u0000T\u01cc\u0001\u0000\u0000\u0000V\u01ce\u0001\u0000\u0000\u0000"+
		"X\u01d0\u0001\u0000\u0000\u0000Z\u01db\u0001\u0000\u0000\u0000\\\u01e6"+
		"\u0001\u0000\u0000\u0000^\u01f3\u0001\u0000\u0000\u0000`b\u0003\u0002"+
		"\u0001\u0000a`\u0001\u0000\u0000\u0000ab\u0001\u0000\u0000\u0000be\u0001"+
		"\u0000\u0000\u0000cd\u0005\u0007\u0000\u0000df\u0003\u0006\u0003\u0000"+
		"ec\u0001\u0000\u0000\u0000ef\u0001\u0000\u0000\u0000fg\u0001\u0000\u0000"+
		"\u0000gh\u0005\b\u0000\u0000hi\u0003\n\u0005\u0000ik\u0007\u0000\u0000"+
		"\u0000jl\u0005\u0017\u0000\u0000kj\u0001\u0000\u0000\u0000kl\u0001\u0000"+
		"\u0000\u0000lm\u0001\u0000\u0000\u0000mn\u0005\u0000\u0000\u0001n\u0001"+
		"\u0001\u0000\u0000\u0000oq\u0003\u0004\u0002\u0000po\u0001\u0000\u0000"+
		"\u0000qr\u0001\u0000\u0000\u0000rp\u0001\u0000\u0000\u0000rs\u0001\u0000"+
		"\u0000\u0000s\u0003\u0001\u0000\u0000\u0000tu\u00054\u0000\u0000uz\u0005"+
		"8\u0000\u0000vw\u0005\u0019\u0000\u0000wy\u00058\u0000\u0000xv\u0001\u0000"+
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
		"\u00a1\u0005 \u0000\u0000\u00a1\u00a2\u00058\u0000\u0000\u00a2\u00a3\u0005"+
		"\u0016\u0000\u0000\u00a3\u00a5\u00036\u001b\u0000\u00a4\u00a6\u0003:\u001d"+
		"\u0000\u00a5\u00a4\u0001\u0000\u0000\u0000\u00a5\u00a6\u0001\u0000\u0000"+
		"\u0000\u00a6\u00a8\u0001\u0000\u0000\u0000\u00a7\u00a9\u0005\u0017\u0000"+
		"\u0000\u00a8\u00a7\u0001\u0000\u0000\u0000\u00a8\u00a9\u0001\u0000\u0000"+
		"\u0000\u00a9\u00b4\u0001\u0000\u0000\u0000\u00aa\u00ab\u0005 \u0000\u0000"+
		"\u00ab\u00ac\u00058\u0000\u0000\u00ac\u00ad\u0005\u0016\u0000\u0000\u00ad"+
		"\u00ae\u0003:\u001d\u0000\u00ae\u00af\u0005\u0016\u0000\u0000\u00af\u00b1"+
		"\u00036\u001b\u0000\u00b0\u00b2\u0005\u0017\u0000\u0000\u00b1\u00b0\u0001"+
		"\u0000\u0000\u0000\u00b1\u00b2\u0001\u0000\u0000\u0000\u00b2\u00b4\u0001"+
		"\u0000\u0000\u0000\u00b3\u00a0\u0001\u0000\u0000\u0000\u00b3\u00aa\u0001"+
		"\u0000\u0000\u0000\u00b4\u000f\u0001\u0000\u0000\u0000\u00b5\u00b6\u0005"+
		")\u0000\u0000\u00b6\u00b7\u00058\u0000\u0000\u00b7\u00b8\u0005\u001a\u0000"+
		"\u0000\u00b8\u00b9\u0003:\u001d\u0000\u00b9\u00ba\u0005\u001b\u0000\u0000"+
		"\u00ba\u00bb\u0005\u0016\u0000\u0000\u00bb\u00bd\u00036\u001b\u0000\u00bc"+
		"\u00be\u0003\u0012\t\u0000\u00bd\u00bc\u0001\u0000\u0000\u0000\u00bd\u00be"+
		"\u0001\u0000\u0000\u0000\u00be\u00c0\u0001\u0000\u0000\u0000\u00bf\u00c1"+
		"\u0005\u0017\u0000\u0000\u00c0\u00bf\u0001\u0000\u0000\u0000\u00c0\u00c1"+
		"\u0001\u0000\u0000\u0000\u00c1\u0011\u0001\u0000\u0000\u0000\u00c2\u00c3"+
		"\u0005\u001c\u0000\u0000\u00c3\u00c8\u0003:\u001d\u0000\u00c4\u00c5\u0005"+
		"\u0018\u0000\u0000\u00c5\u00c7\u0003:\u001d\u0000\u00c6\u00c4\u0001\u0000"+
		"\u0000\u0000\u00c7\u00ca\u0001\u0000\u0000\u0000\u00c8\u00c6\u0001\u0000"+
		"\u0000\u0000\u00c8\u00c9\u0001\u0000\u0000\u0000\u00c9\u00cb\u0001\u0000"+
		"\u0000\u0000\u00ca\u00c8\u0001\u0000\u0000\u0000\u00cb\u00cc\u0005\u001d"+
		"\u0000\u0000\u00cc\u0013\u0001\u0000\u0000\u0000\u00cd\u00d1\u0005\u001c"+
		"\u0000\u0000\u00ce\u00d0\u0003\f\u0006\u0000\u00cf\u00ce\u0001\u0000\u0000"+
		"\u0000\u00d0\u00d3\u0001\u0000\u0000\u0000\u00d1\u00cf\u0001\u0000\u0000"+
		"\u0000\u00d1\u00d2\u0001\u0000\u0000\u0000\u00d2\u00d4\u0001\u0000\u0000"+
		"\u0000\u00d3\u00d1\u0001\u0000\u0000\u0000\u00d4\u00d5\u0005\u001d\u0000"+
		"\u0000\u00d5\u0015\u0001\u0000\u0000\u0000\u00d6\u00d7\u0005+\u0000\u0000"+
		"\u00d7\u00d8\u0005\u001e\u0000\u0000\u00d8\u00d9\u0003<\u001e\u0000\u00d9"+
		"\u00da\u0005\u001f\u0000\u0000\u00da\u00de\u0003\u0014\n\u0000\u00db\u00dd"+
		"\u0003\u0018\f\u0000\u00dc\u00db\u0001\u0000\u0000\u0000\u00dd\u00e0\u0001"+
		"\u0000\u0000\u0000\u00de\u00dc\u0001\u0000\u0000\u0000\u00de\u00df\u0001"+
		"\u0000\u0000\u0000\u00df\u00e2\u0001\u0000\u0000\u0000\u00e0\u00de\u0001"+
		"\u0000\u0000\u0000\u00e1\u00e3\u0003\u001a\r\u0000\u00e2\u00e1\u0001\u0000"+
		"\u0000\u0000\u00e2\u00e3\u0001\u0000\u0000\u0000\u00e3\u00e4\u0001\u0000"+
		"\u0000\u0000\u00e4\u00e6\u0005*\u0000\u0000\u00e5\u00e7\u0005\u0017\u0000"+
		"\u0000\u00e6\u00e5\u0001\u0000\u0000\u0000\u00e6\u00e7\u0001\u0000\u0000"+
		"\u0000\u00e7\u0017\u0001\u0000\u0000\u0000\u00e8\u00e9\u0005,\u0000\u0000"+
		"\u00e9\u00ea\u0005\u001e\u0000\u0000\u00ea\u00eb\u0003<\u001e\u0000\u00eb"+
		"\u00ec\u0005\u001f\u0000\u0000\u00ec\u00ed\u0003\u0014\n\u0000\u00ed\u0019"+
		"\u0001\u0000\u0000\u0000\u00ee\u00ef\u0005,\u0000\u0000\u00ef\u00f0\u0003"+
		"\u0014\n\u0000\u00f0\u001b\u0001\u0000\u0000\u0000\u00f1\u00f2\u0005-"+
		"\u0000\u0000\u00f2\u00f3\u0005\u001e\u0000\u0000\u00f3\u00f4\u0003<\u001e"+
		"\u0000\u00f4\u00f5\u0005\u001f\u0000\u0000\u00f5\u00f6\u0003\u0014\n\u0000"+
		"\u00f6\u00f8\u0005*\u0000\u0000\u00f7\u00f9\u0005\u0017\u0000\u0000\u00f8"+
		"\u00f7\u0001\u0000\u0000\u0000\u00f8\u00f9\u0001\u0000\u0000\u0000\u00f9"+
		"\u001d\u0001\u0000\u0000\u0000\u00fa\u00fb\u0005.\u0000\u0000\u00fb\u00fc"+
		"\u0003\u0014\n\u0000\u00fc\u00fd\u0005-\u0000\u0000\u00fd\u00fe\u0005"+
		"\u001e\u0000\u0000\u00fe\u00ff\u0003<\u001e\u0000\u00ff\u0101\u0005\u001f"+
		"\u0000\u0000\u0100\u0102\u0005\u0017\u0000\u0000\u0101\u0100\u0001\u0000"+
		"\u0000\u0000\u0101\u0102\u0001\u0000\u0000\u0000\u0102\u001f\u0001\u0000"+
		"\u0000\u0000\u0103\u0104\u0005/\u0000\u0000\u0104\u0105\u0005\u001e\u0000"+
		"\u0000\u0105\u0106\u0003\"\u0011\u0000\u0106\u0107\u0005\u0017\u0000\u0000"+
		"\u0107\u0108\u0003<\u001e\u0000\u0108\u010a\u0005\u0017\u0000\u0000\u0109"+
		"\u010b\u0003$\u0012\u0000\u010a\u0109\u0001\u0000\u0000\u0000\u010a\u010b"+
		"\u0001\u0000\u0000\u0000\u010b\u010c\u0001\u0000\u0000\u0000\u010c\u010d"+
		"\u0005\u001f\u0000\u0000\u010d\u010e\u0003\u0014\n\u0000\u010e!\u0001"+
		"\u0000\u0000\u0000\u010f\u0110\u0005 \u0000\u0000\u0110\u0111\u00058\u0000"+
		"\u0000\u0111\u0112\u0005\u0016\u0000\u0000\u0112\u0114\u00036\u001b\u0000"+
		"\u0113\u0115\u0003:\u001d\u0000\u0114\u0113\u0001\u0000\u0000\u0000\u0114"+
		"\u0115\u0001\u0000\u0000\u0000\u0115\u0119\u0001\u0000\u0000\u0000\u0116"+
		"\u0119\u00034\u001a\u0000\u0117\u0119\u0001\u0000\u0000\u0000\u0118\u010f"+
		"\u0001\u0000\u0000\u0000\u0118\u0116\u0001\u0000\u0000\u0000\u0118\u0117"+
		"\u0001\u0000\u0000\u0000\u0119#\u0001\u0000\u0000\u0000\u011a\u011d\u0003"+
		"4\u001a\u0000\u011b\u011d\u0003:\u001d\u0000\u011c\u011a\u0001\u0000\u0000"+
		"\u0000\u011c\u011b\u0001\u0000\u0000\u0000\u011d%\u0001\u0000\u0000\u0000"+
		"\u011e\u0120\u00050\u0000\u0000\u011f\u0121\u0005\u0017\u0000\u0000\u0120"+
		"\u011f\u0001\u0000\u0000\u0000\u0120\u0121\u0001\u0000\u0000\u0000\u0121"+
		"\u0127\u0001\u0000\u0000\u0000\u0122\u0124\u00051\u0000\u0000\u0123\u0125"+
		"\u0005\u0017\u0000\u0000\u0124\u0123\u0001\u0000\u0000\u0000\u0124\u0125"+
		"\u0001\u0000\u0000\u0000\u0125\u0127\u0001\u0000\u0000\u0000\u0126\u011e"+
		"\u0001\u0000\u0000\u0000\u0126\u0122\u0001\u0000\u0000\u0000\u0127\'\u0001"+
		"\u0000\u0000\u0000\u0128\u0129\u00055\u0000\u0000\u0129\u012a\u00058\u0000"+
		"\u0000\u012a\u012c\u0005\u001e\u0000\u0000\u012b\u012d\u00038\u001c\u0000"+
		"\u012c\u012b\u0001\u0000\u0000\u0000\u012c\u012d\u0001\u0000\u0000\u0000"+
		"\u012d\u012e\u0001\u0000\u0000\u0000\u012e\u0131\u0005\u001f\u0000\u0000"+
		"\u012f\u0130\u0005\u0016\u0000\u0000\u0130\u0132\u00036\u001b\u0000\u0131"+
		"\u012f\u0001\u0000\u0000\u0000\u0131\u0132\u0001\u0000\u0000\u0000\u0132"+
		")\u0001\u0000\u0000\u0000\u0133\u0135\u00056\u0000\u0000\u0134\u0136\u0005"+
		"\u0017\u0000\u0000\u0135\u0134\u0001\u0000\u0000\u0000\u0135\u0136\u0001"+
		"\u0000\u0000\u0000\u0136\u013d\u0001\u0000\u0000\u0000\u0137\u0138\u0003"+
		"0\u0018\u0000\u0138\u013a\u00056\u0000\u0000\u0139\u013b\u0005\u0017\u0000"+
		"\u0000\u013a\u0139\u0001\u0000\u0000\u0000\u013a\u013b\u0001\u0000\u0000"+
		"\u0000\u013b\u013d\u0001\u0000\u0000\u0000\u013c\u0133\u0001\u0000\u0000"+
		"\u0000\u013c\u0137\u0001\u0000\u0000\u0000\u013d+\u0001\u0000\u0000\u0000"+
		"\u013e\u013f\u00057\u0000\u0000\u013f\u0144\u0003.\u0017\u0000\u0140\u0141"+
		"\u00057\u0000\u0000\u0141\u0143\u0003.\u0017\u0000\u0142\u0140\u0001\u0000"+
		"\u0000\u0000\u0143\u0146\u0001\u0000\u0000\u0000\u0144\u0142\u0001\u0000"+
		"\u0000\u0000\u0144\u0145\u0001\u0000\u0000\u0000\u0145\u0148\u0001\u0000"+
		"\u0000\u0000\u0146\u0144\u0001\u0000\u0000\u0000\u0147\u0149\u0005\u0017"+
		"\u0000\u0000\u0148\u0147\u0001\u0000\u0000\u0000\u0148\u0149\u0001\u0000"+
		"\u0000\u0000\u0149-\u0001\u0000\u0000\u0000\u014a\u014d\u0005;\u0000\u0000"+
		"\u014b\u014d\u0003:\u001d\u0000\u014c\u014a\u0001\u0000\u0000\u0000\u014c"+
		"\u014b\u0001\u0000\u0000\u0000\u014d/\u0001\u0000\u0000\u0000\u014e\u0152"+
		"\u00058\u0000\u0000\u014f\u0151\u00032\u0019\u0000\u0150\u014f\u0001\u0000"+
		"\u0000\u0000\u0151\u0154\u0001\u0000\u0000\u0000\u0152\u0150\u0001\u0000"+
		"\u0000\u0000\u0152\u0153\u0001\u0000\u0000\u0000\u01531\u0001\u0000\u0000"+
		"\u0000\u0154\u0152\u0001\u0000\u0000\u0000\u0155\u0156\u0005\u0019\u0000"+
		"\u0000\u0156\u015c\u00058\u0000\u0000\u0157\u0158\u0005\u001a\u0000\u0000"+
		"\u0158\u0159\u0003:\u001d\u0000\u0159\u015a\u0005\u001b\u0000\u0000\u015a"+
		"\u015c\u0001\u0000\u0000\u0000\u015b\u0155\u0001\u0000\u0000\u0000\u015b"+
		"\u0157\u0001\u0000\u0000\u0000\u015c3\u0001\u0000\u0000\u0000\u015d\u015e"+
		"\u00030\u0018\u0000\u015e\u015f\u0005\u0010\u0000\u0000\u015f\u0161\u0003"+
		":\u001d\u0000\u0160\u0162\u0005\u0017\u0000\u0000\u0161\u0160\u0001\u0000"+
		"\u0000\u0000\u0161\u0162\u0001\u0000\u0000\u0000\u01625\u0001\u0000\u0000"+
		"\u0000\u0163\u016a\u0005!\u0000\u0000\u0164\u016a\u0005\"\u0000\u0000"+
		"\u0165\u016a\u0005#\u0000\u0000\u0166\u016a\u0005%\u0000\u0000\u0167\u016a"+
		"\u0005$\u0000\u0000\u0168\u016a\u00058\u0000\u0000\u0169\u0163\u0001\u0000"+
		"\u0000\u0000\u0169\u0164\u0001\u0000\u0000\u0000\u0169\u0165\u0001\u0000"+
		"\u0000\u0000\u0169\u0166\u0001\u0000\u0000\u0000\u0169\u0167\u0001\u0000"+
		"\u0000\u0000\u0169\u0168\u0001\u0000\u0000\u0000\u016a7\u0001\u0000\u0000"+
		"\u0000\u016b\u0170\u0003:\u001d\u0000\u016c\u016d\u0005\u0018\u0000\u0000"+
		"\u016d\u016f\u0003:\u001d\u0000\u016e\u016c\u0001\u0000\u0000\u0000\u016f"+
		"\u0172\u0001\u0000\u0000\u0000\u0170\u016e\u0001\u0000\u0000\u0000\u0170"+
		"\u0171\u0001\u0000\u0000\u0000\u01719\u0001\u0000\u0000\u0000\u0172\u0170"+
		"\u0001\u0000\u0000\u0000\u0173\u017a\u0005(\u0000\u0000\u0174\u017a\u0003"+
		"<\u001e\u0000\u0175\u017a\u0003J%\u0000\u0176\u017a\u0003V+\u0000\u0177"+
		"\u017a\u0003\u0012\t\u0000\u0178\u017a\u0003(\u0014\u0000\u0179\u0173"+
		"\u0001\u0000\u0000\u0000\u0179\u0174\u0001\u0000\u0000\u0000\u0179\u0175"+
		"\u0001\u0000\u0000\u0000\u0179\u0176\u0001\u0000\u0000\u0000\u0179\u0177"+
		"\u0001\u0000\u0000\u0000\u0179\u0178\u0001\u0000\u0000\u0000\u017a;\u0001"+
		"\u0000\u0000\u0000\u017b\u017c\u0003>\u001f\u0000\u017c=\u0001\u0000\u0000"+
		"\u0000\u017d\u0182\u0003@ \u0000\u017e\u017f\u0005\u0012\u0000\u0000\u017f"+
		"\u0181\u0003@ \u0000\u0180\u017e\u0001\u0000\u0000\u0000\u0181\u0184\u0001"+
		"\u0000\u0000\u0000\u0182\u0180\u0001\u0000\u0000\u0000\u0182\u0183\u0001"+
		"\u0000\u0000\u0000\u0183?\u0001\u0000\u0000\u0000\u0184\u0182\u0001\u0000"+
		"\u0000\u0000\u0185\u018a\u0003B!\u0000\u0186\u0187\u0005\u0011\u0000\u0000"+
		"\u0187\u0189\u0003B!\u0000\u0188\u0186\u0001\u0000\u0000\u0000\u0189\u018c"+
		"\u0001\u0000\u0000\u0000\u018a\u0188\u0001\u0000\u0000\u0000\u018a\u018b"+
		"\u0001\u0000\u0000\u0000\u018bA\u0001\u0000\u0000\u0000\u018c\u018a\u0001"+
		"\u0000\u0000\u0000\u018d\u018e\u0005\u0013\u0000\u0000\u018e\u019a\u0003"+
		"B!\u0000\u018f\u0193\u0003D\"\u0000\u0190\u0191\u0003F#\u0000\u0191\u0192"+
		"\u0003D\"\u0000\u0192\u0194\u0001\u0000\u0000\u0000\u0193\u0190\u0001"+
		"\u0000\u0000\u0000\u0193\u0194\u0001\u0000\u0000\u0000\u0194\u019a\u0001"+
		"\u0000\u0000\u0000\u0195\u0196\u0005\u001e\u0000\u0000\u0196\u0197\u0003"+
		"<\u001e\u0000\u0197\u0198\u0005\u001f\u0000\u0000\u0198\u019a\u0001\u0000"+
		"\u0000\u0000\u0199\u018d\u0001\u0000\u0000\u0000\u0199\u018f\u0001\u0000"+
		"\u0000\u0000\u0199\u0195\u0001\u0000\u0000\u0000\u019aC\u0001\u0000\u0000"+
		"\u0000\u019b\u019f\u0003J%\u0000\u019c\u019f\u0003H$\u0000\u019d\u019f"+
		"\u0003V+\u0000\u019e\u019b\u0001\u0000\u0000\u0000\u019e\u019c\u0001\u0000"+
		"\u0000\u0000\u019e\u019d\u0001\u0000\u0000\u0000\u019fE\u0001\u0000\u0000"+
		"\u0000\u01a0\u01a1\u0007\u0001\u0000\u0000\u01a1G\u0001\u0000\u0000\u0000"+
		"\u01a2\u01a3\u0007\u0002\u0000\u0000\u01a3I\u0001\u0000\u0000\u0000\u01a4"+
		"\u01a5\u0003L&\u0000\u01a5K\u0001\u0000\u0000\u0000\u01a6\u01ab\u0003"+
		"N\'\u0000\u01a7\u01a8\u0007\u0003\u0000\u0000\u01a8\u01aa\u0003N\'\u0000"+
		"\u01a9\u01a7\u0001\u0000\u0000\u0000\u01aa\u01ad\u0001\u0000\u0000\u0000"+
		"\u01ab\u01a9\u0001\u0000\u0000\u0000\u01ab\u01ac\u0001\u0000\u0000\u0000"+
		"\u01acM\u0001\u0000\u0000\u0000\u01ad\u01ab\u0001\u0000\u0000\u0000\u01ae"+
		"\u01b3\u0003P(\u0000\u01af\u01b0\u0007\u0004\u0000\u0000\u01b0\u01b2\u0003"+
		"P(\u0000\u01b1\u01af\u0001\u0000\u0000\u0000\u01b2\u01b5\u0001\u0000\u0000"+
		"\u0000\u01b3\u01b1\u0001\u0000\u0000\u0000\u01b3\u01b4\u0001\u0000\u0000"+
		"\u0000\u01b4O\u0001\u0000\u0000\u0000\u01b5\u01b3\u0001\u0000\u0000\u0000"+
		"\u01b6\u01b7\u0007\u0005\u0000\u0000\u01b7\u01ba\u0003P(\u0000\u01b8\u01ba"+
		"\u0003R)\u0000\u01b9\u01b6\u0001\u0000\u0000\u0000\u01b9\u01b8\u0001\u0000"+
		"\u0000\u0000\u01baQ\u0001\u0000\u0000\u0000\u01bb\u01cb\u0003T*\u0000"+
		"\u01bc\u01c0\u00058\u0000\u0000\u01bd\u01bf\u0003^/\u0000\u01be\u01bd"+
		"\u0001\u0000\u0000\u0000\u01bf\u01c2\u0001\u0000\u0000\u0000\u01c0\u01be"+
		"\u0001\u0000\u0000\u0000\u01c0\u01c1\u0001\u0000\u0000\u0000\u01c1\u01c4"+
		"\u0001\u0000\u0000\u0000\u01c2\u01c0\u0001\u0000\u0000\u0000\u01c3\u01c5"+
		"\u0007\u0006\u0000\u0000\u01c4\u01c3\u0001\u0000\u0000\u0000\u01c4\u01c5"+
		"\u0001\u0000\u0000\u0000\u01c5\u01cb\u0001\u0000\u0000\u0000\u01c6\u01c7"+
		"\u0005\u001e\u0000\u0000\u01c7\u01c8\u0003J%\u0000\u01c8\u01c9\u0005\u001f"+
		"\u0000\u0000\u01c9\u01cb\u0001\u0000\u0000\u0000\u01ca\u01bb\u0001\u0000"+
		"\u0000\u0000\u01ca\u01bc\u0001\u0000\u0000\u0000\u01ca\u01c6\u0001\u0000"+
		"\u0000\u0000\u01cbS\u0001\u0000\u0000\u0000\u01cc\u01cd\u0007\u0007\u0000"+
		"\u0000\u01cdU\u0001\u0000\u0000\u0000\u01ce\u01cf\u0003X,\u0000\u01cf"+
		"W\u0001\u0000\u0000\u0000\u01d0\u01d5\u0003Z-\u0000\u01d1\u01d2\u0005"+
		"\u0003\u0000\u0000\u01d2\u01d4\u0003Z-\u0000\u01d3\u01d1\u0001\u0000\u0000"+
		"\u0000\u01d4\u01d7\u0001\u0000\u0000\u0000\u01d5\u01d3\u0001\u0000\u0000"+
		"\u0000\u01d5\u01d6\u0001\u0000\u0000\u0000\u01d6Y\u0001\u0000\u0000\u0000"+
		"\u01d7\u01d5\u0001\u0000\u0000\u0000\u01d8\u01dc\u0003\\.\u0000\u01d9"+
		"\u01dc\u0003J%\u0000\u01da\u01dc\u0003H$\u0000\u01db\u01d8\u0001\u0000"+
		"\u0000\u0000\u01db\u01d9\u0001\u0000\u0000\u0000\u01db\u01da\u0001\u0000"+
		"\u0000\u0000\u01dc[\u0001\u0000\u0000\u0000\u01dd\u01e7\u0005;\u0000\u0000"+
		"\u01de\u01e7\u0005<\u0000\u0000\u01df\u01e3\u00058\u0000\u0000\u01e0\u01e2"+
		"\u0003^/\u0000\u01e1\u01e0\u0001\u0000\u0000\u0000\u01e2\u01e5\u0001\u0000"+
		"\u0000\u0000\u01e3\u01e1\u0001\u0000\u0000\u0000\u01e3\u01e4\u0001\u0000"+
		"\u0000\u0000\u01e4\u01e7\u0001\u0000\u0000\u0000\u01e5\u01e3\u0001\u0000"+
		"\u0000\u0000\u01e6\u01dd\u0001\u0000\u0000\u0000\u01e6\u01de\u0001\u0000"+
		"\u0000\u0000\u01e6\u01df\u0001\u0000\u0000\u0000\u01e7]\u0001\u0000\u0000"+
		"\u0000\u01e8\u01e9\u0005\u0019\u0000\u0000\u01e9\u01f4\u00058\u0000\u0000"+
		"\u01ea\u01eb\u0005\u001a\u0000\u0000\u01eb\u01ec\u0003:\u001d\u0000\u01ec"+
		"\u01ed\u0005\u001b\u0000\u0000\u01ed\u01f4\u0001\u0000\u0000\u0000\u01ee"+
		"\u01f0\u0005\u001e\u0000\u0000\u01ef\u01f1\u00038\u001c\u0000\u01f0\u01ef"+
		"\u0001\u0000\u0000\u0000\u01f0\u01f1\u0001\u0000\u0000\u0000\u01f1\u01f2"+
		"\u0001\u0000\u0000\u0000\u01f2\u01f4\u0005\u001f\u0000\u0000\u01f3\u01e8"+
		"\u0001\u0000\u0000\u0000\u01f3\u01ea\u0001\u0000\u0000\u0000\u01f3\u01ee"+
		"\u0001\u0000\u0000\u0000\u01f4_\u0001\u0000\u0000\u0000>aekrz~\u0083\u0088"+
		"\u008d\u009c\u009e\u00a5\u00a8\u00b1\u00b3\u00bd\u00c0\u00c8\u00d1\u00de"+
		"\u00e2\u00e6\u00f8\u0101\u010a\u0114\u0118\u011c\u0120\u0124\u0126\u012c"+
		"\u0131\u0135\u013a\u013c\u0144\u0148\u014c\u0152\u015b\u0161\u0169\u0170"+
		"\u0179\u0182\u018a\u0193\u0199\u019e\u01ab\u01b3\u01b9\u01c0\u01c4\u01ca"+
		"\u01d5\u01db\u01e3\u01e6\u01f0\u01f3";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}