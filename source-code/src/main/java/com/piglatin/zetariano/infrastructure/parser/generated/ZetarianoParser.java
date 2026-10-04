// Generated from ZetarianoParser.g4 by ANTLR 4.13.2
package com.piglatin.zetariano.infrastructure.parser.generated;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class ZetarianoParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PUBLIC=1, CLASS=2, INT=3, DOUBLE=4, CHAR_TYPE=5, BOOLEAN=6, STRING_TYPE=7, 
		VOID=8, NEW=9, NULL=10, IF=11, ELSE=12, SWITCH=13, CASE=14, DEFAULT=15, 
		BREAK=16, CONTINUE=17, RETURN=18, FOR=19, WHILE=20, DO=21, PRINTLN=22, 
		PRINT=23, READLN=24, TRUE=25, FALSE=26, PLUS=27, MINUS=28, MULT=29, DIV=30, 
		MOD=31, INC=32, DEC=33, EQUAL=34, NOTEQUAL=35, LESS=36, GREATER=37, LESSEQUAL=38, 
		GREATEREQUAL=39, AND=40, OR=41, NOT=42, ASSIGN=43, ADD_ASSIGN=44, SUB_ASSIGN=45, 
		MULT_ASSIGN=46, QUESTION=47, COLON=48, SEMICOLON=49, COMMA=50, DOT=51, 
		LEFT_PAREN=52, RIGHT_PAREN=53, LEFT_BRACE=54, RIGHT_BRACE=55, LEFT_BRACKET=56, 
		RIGHT_BRACKET=57, INTEGER=58, DECIMAL=59, CHAR=60, STRING=61, ID=62, LINE_COMMENT=63, 
		BLOCK_COMMENT=64, WS=65;
	public static final int
		RULE_program = 0, RULE_classDefinition = 1, RULE_globalDeclarations = 2, 
		RULE_globalDeclaration = 3, RULE_fieldDeclaration = 4, RULE_methodDeclaration = 5, 
		RULE_constructorDeclaration = 6, RULE_parameterList = 7, RULE_parameter = 8, 
		RULE_block = 9, RULE_mainInstructions = 10, RULE_instruction = 11, RULE_variableDeclaration = 12, 
		RULE_arrayDeclaration = 13, RULE_arrayInitializer = 14, RULE_assignment = 15, 
		RULE_readStatement = 16, RULE_printStatement = 17, RULE_ifStatement = 18, 
		RULE_switchStatement = 19, RULE_whileStatement = 20, RULE_doWhileStatement = 21, 
		RULE_forStatement = 22, RULE_jumpStatement = 23, RULE_lvalue = 24, RULE_type = 25, 
		RULE_expression = 26, RULE_primary = 27, RULE_literal = 28, RULE_argumentList = 29;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "classDefinition", "globalDeclarations", "globalDeclaration", 
			"fieldDeclaration", "methodDeclaration", "constructorDeclaration", "parameterList", 
			"parameter", "block", "mainInstructions", "instruction", "variableDeclaration", 
			"arrayDeclaration", "arrayInitializer", "assignment", "readStatement", 
			"printStatement", "ifStatement", "switchStatement", "whileStatement", 
			"doWhileStatement", "forStatement", "jumpStatement", "lvalue", "type", 
			"expression", "primary", "literal", "argumentList"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'public'", "'class'", "'int'", "'double'", "'char'", "'boolean'", 
			"'String'", "'void'", "'new'", "'null'", "'if'", "'else'", "'switch'", 
			"'case'", "'default'", "'break'", "'continue'", "'return'", "'for'", 
			"'while'", "'do'", "'println'", "'print'", "'readln'", "'true'", "'false'", 
			"'+'", "'-'", "'*'", "'/'", "'%'", "'++'", "'--'", "'=='", "'!='", "'<'", 
			"'>'", "'<='", "'>='", "'&&'", "'||'", "'!'", "'='", "'+='", "'-='", 
			"'*='", "'?'", "':'", "';'", "','", "'.'", "'('", "')'", "'{'", "'}'", 
			"'['", "']'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "PUBLIC", "CLASS", "INT", "DOUBLE", "CHAR_TYPE", "BOOLEAN", "STRING_TYPE", 
			"VOID", "NEW", "NULL", "IF", "ELSE", "SWITCH", "CASE", "DEFAULT", "BREAK", 
			"CONTINUE", "RETURN", "FOR", "WHILE", "DO", "PRINTLN", "PRINT", "READLN", 
			"TRUE", "FALSE", "PLUS", "MINUS", "MULT", "DIV", "MOD", "INC", "DEC", 
			"EQUAL", "NOTEQUAL", "LESS", "GREATER", "LESSEQUAL", "GREATEREQUAL", 
			"AND", "OR", "NOT", "ASSIGN", "ADD_ASSIGN", "SUB_ASSIGN", "MULT_ASSIGN", 
			"QUESTION", "COLON", "SEMICOLON", "COMMA", "DOT", "LEFT_PAREN", "RIGHT_PAREN", 
			"LEFT_BRACE", "RIGHT_BRACE", "LEFT_BRACKET", "RIGHT_BRACKET", "INTEGER", 
			"DECIMAL", "CHAR", "STRING", "ID", "LINE_COMMENT", "BLOCK_COMMENT", "WS"
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
	public String getGrammarFileName() { return "ZetarianoParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ZetarianoParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public ClassDefinitionContext classDefinition() {
			return getRuleContext(ClassDefinitionContext.class,0);
		}
		public TerminalNode EOF() { return getToken(ZetarianoParser.EOF, 0); }
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			classDefinition();
			setState(61);
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
	public static class ClassDefinitionContext extends ParserRuleContext {
		public TerminalNode CLASS() { return getToken(ZetarianoParser.CLASS, 0); }
		public TerminalNode ID() { return getToken(ZetarianoParser.ID, 0); }
		public TerminalNode LEFT_BRACE() { return getToken(ZetarianoParser.LEFT_BRACE, 0); }
		public GlobalDeclarationsContext globalDeclarations() {
			return getRuleContext(GlobalDeclarationsContext.class,0);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(ZetarianoParser.RIGHT_BRACE, 0); }
		public TerminalNode PUBLIC() { return getToken(ZetarianoParser.PUBLIC, 0); }
		public ClassDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterClassDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitClassDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitClassDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDefinitionContext classDefinition() throws RecognitionException {
		ClassDefinitionContext _localctx = new ClassDefinitionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_classDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PUBLIC) {
				{
				setState(63);
				match(PUBLIC);
				}
			}

			setState(66);
			match(CLASS);
			setState(67);
			match(ID);
			setState(68);
			match(LEFT_BRACE);
			setState(69);
			globalDeclarations();
			setState(70);
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
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterGlobalDeclarations(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitGlobalDeclarations(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitGlobalDeclarations(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GlobalDeclarationsContext globalDeclarations() throws RecognitionException {
		GlobalDeclarationsContext _localctx = new GlobalDeclarationsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_globalDeclarations);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4611686018427388410L) != 0)) {
				{
				{
				setState(72);
				globalDeclaration();
				}
				}
				setState(77);
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
	public static class GlobalConstructorContext extends GlobalDeclarationContext {
		public ConstructorDeclarationContext constructorDeclaration() {
			return getRuleContext(ConstructorDeclarationContext.class,0);
		}
		public GlobalConstructorContext(GlobalDeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterGlobalConstructor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitGlobalConstructor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitGlobalConstructor(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GlobalFieldContext extends GlobalDeclarationContext {
		public FieldDeclarationContext fieldDeclaration() {
			return getRuleContext(FieldDeclarationContext.class,0);
		}
		public GlobalFieldContext(GlobalDeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterGlobalField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitGlobalField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitGlobalField(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GlobalMethodContext extends GlobalDeclarationContext {
		public MethodDeclarationContext methodDeclaration() {
			return getRuleContext(MethodDeclarationContext.class,0);
		}
		public GlobalMethodContext(GlobalDeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterGlobalMethod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitGlobalMethod(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitGlobalMethod(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GlobalDeclarationContext globalDeclaration() throws RecognitionException {
		GlobalDeclarationContext _localctx = new GlobalDeclarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_globalDeclaration);
		try {
			setState(81);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				_localctx = new GlobalFieldContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(78);
				fieldDeclaration();
				}
				break;
			case 2:
				_localctx = new GlobalMethodContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(79);
				methodDeclaration();
				}
				break;
			case 3:
				_localctx = new GlobalConstructorContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(80);
				constructorDeclaration();
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
	public static class FieldDeclarationContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(ZetarianoParser.ID, 0); }
		public TerminalNode SEMICOLON() { return getToken(ZetarianoParser.SEMICOLON, 0); }
		public List<TerminalNode> LEFT_BRACKET() { return getTokens(ZetarianoParser.LEFT_BRACKET); }
		public TerminalNode LEFT_BRACKET(int i) {
			return getToken(ZetarianoParser.LEFT_BRACKET, i);
		}
		public List<TerminalNode> RIGHT_BRACKET() { return getTokens(ZetarianoParser.RIGHT_BRACKET); }
		public TerminalNode RIGHT_BRACKET(int i) {
			return getToken(ZetarianoParser.RIGHT_BRACKET, i);
		}
		public TerminalNode ASSIGN() { return getToken(ZetarianoParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ArrayInitializerContext arrayInitializer() {
			return getRuleContext(ArrayInitializerContext.class,0);
		}
		public FieldDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterFieldDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitFieldDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitFieldDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldDeclarationContext fieldDeclaration() throws RecognitionException {
		FieldDeclarationContext _localctx = new FieldDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_fieldDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			type();
			setState(88);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LEFT_BRACKET) {
				{
				{
				setState(84);
				match(LEFT_BRACKET);
				setState(85);
				match(RIGHT_BRACKET);
				}
				}
				setState(90);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(91);
			match(ID);
			setState(97);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(92);
				match(ASSIGN);
				setState(95);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case NEW:
				case NULL:
				case TRUE:
				case FALSE:
				case PLUS:
				case MINUS:
				case INC:
				case DEC:
				case NOT:
				case LEFT_PAREN:
				case INTEGER:
				case DECIMAL:
				case CHAR:
				case STRING:
				case ID:
					{
					setState(93);
					expression(0);
					}
					break;
				case LEFT_BRACE:
					{
					setState(94);
					arrayInitializer();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
			}

			setState(99);
			match(SEMICOLON);
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
	public static class MethodDeclarationContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ZetarianoParser.ID, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(ZetarianoParser.LEFT_PAREN, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(ZetarianoParser.RIGHT_PAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode VOID() { return getToken(ZetarianoParser.VOID, 0); }
		public TerminalNode PUBLIC() { return getToken(ZetarianoParser.PUBLIC, 0); }
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public MethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterMethodDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitMethodDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitMethodDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodDeclarationContext methodDeclaration() throws RecognitionException {
		MethodDeclarationContext _localctx = new MethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_methodDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PUBLIC) {
				{
				setState(101);
				match(PUBLIC);
				}
			}

			setState(106);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
			case DOUBLE:
			case CHAR_TYPE:
			case BOOLEAN:
			case STRING_TYPE:
			case ID:
				{
				setState(104);
				type();
				}
				break;
			case VOID:
				{
				setState(105);
				match(VOID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(108);
			match(ID);
			setState(109);
			match(LEFT_PAREN);
			setState(111);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4611686018427388152L) != 0)) {
				{
				setState(110);
				parameterList();
				}
			}

			setState(113);
			match(RIGHT_PAREN);
			setState(114);
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
	public static class ConstructorDeclarationContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ZetarianoParser.ID, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(ZetarianoParser.LEFT_PAREN, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(ZetarianoParser.RIGHT_PAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode PUBLIC() { return getToken(ZetarianoParser.PUBLIC, 0); }
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public ConstructorDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterConstructorDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitConstructorDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitConstructorDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorDeclarationContext constructorDeclaration() throws RecognitionException {
		ConstructorDeclarationContext _localctx = new ConstructorDeclarationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_constructorDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PUBLIC) {
				{
				setState(116);
				match(PUBLIC);
				}
			}

			setState(119);
			match(ID);
			setState(120);
			match(LEFT_PAREN);
			setState(122);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4611686018427388152L) != 0)) {
				{
				setState(121);
				parameterList();
				}
			}

			setState(124);
			match(RIGHT_PAREN);
			setState(125);
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
	public static class ParameterListContext extends ParserRuleContext {
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ZetarianoParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ZetarianoParser.COMMA, i);
		}
		public ParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterParameterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitParameterList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterListContext parameterList() throws RecognitionException {
		ParameterListContext _localctx = new ParameterListContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_parameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
			parameter();
			setState(132);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(128);
				match(COMMA);
				setState(129);
				parameter();
				}
				}
				setState(134);
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
	public static class ParameterContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(ZetarianoParser.ID, 0); }
		public List<TerminalNode> LEFT_BRACKET() { return getTokens(ZetarianoParser.LEFT_BRACKET); }
		public TerminalNode LEFT_BRACKET(int i) {
			return getToken(ZetarianoParser.LEFT_BRACKET, i);
		}
		public List<TerminalNode> RIGHT_BRACKET() { return getTokens(ZetarianoParser.RIGHT_BRACKET); }
		public TerminalNode RIGHT_BRACKET(int i) {
			return getToken(ZetarianoParser.RIGHT_BRACKET, i);
		}
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_parameter);
		int _la;
		try {
			setState(148);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(135);
				type();
				setState(136);
				match(ID);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(138);
				type();
				setState(143);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==LEFT_BRACKET) {
					{
					{
					setState(139);
					match(LEFT_BRACKET);
					setState(140);
					match(RIGHT_BRACKET);
					}
					}
					setState(145);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(146);
				match(ID);
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
	public static class BlockContext extends ParserRuleContext {
		public TerminalNode LEFT_BRACE() { return getToken(ZetarianoParser.LEFT_BRACE, 0); }
		public MainInstructionsContext mainInstructions() {
			return getRuleContext(MainInstructionsContext.class,0);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(ZetarianoParser.RIGHT_BRACE, 0); }
		public InstructionContext instruction() {
			return getRuleContext(InstructionContext.class,0);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_block);
		try {
			setState(155);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LEFT_BRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(150);
				match(LEFT_BRACE);
				setState(151);
				mainInstructions();
				setState(152);
				match(RIGHT_BRACE);
				}
				break;
			case INT:
			case DOUBLE:
			case CHAR_TYPE:
			case BOOLEAN:
			case STRING_TYPE:
			case NEW:
			case NULL:
			case IF:
			case SWITCH:
			case BREAK:
			case CONTINUE:
			case RETURN:
			case FOR:
			case WHILE:
			case DO:
			case PRINTLN:
			case PRINT:
			case READLN:
			case TRUE:
			case FALSE:
			case PLUS:
			case MINUS:
			case INC:
			case DEC:
			case NOT:
			case LEFT_PAREN:
			case INTEGER:
			case DECIMAL:
			case CHAR:
			case STRING:
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(154);
				instruction();
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
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterMainInstructions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitMainInstructions(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitMainInstructions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MainInstructionsContext mainInstructions() throws RecognitionException {
		MainInstructionsContext _localctx = new MainInstructionsContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_mainInstructions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8939649671798664952L) != 0)) {
				{
				{
				setState(157);
				instruction();
				}
				}
				setState(162);
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
		public TerminalNode SEMICOLON() { return getToken(ZetarianoParser.SEMICOLON, 0); }
		public InstructionAssignmentContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterInstructionAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitInstructionAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitInstructionAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstructionSwitchContext extends InstructionContext {
		public SwitchStatementContext switchStatement() {
			return getRuleContext(SwitchStatementContext.class,0);
		}
		public InstructionSwitchContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterInstructionSwitch(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitInstructionSwitch(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitInstructionSwitch(this);
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
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterInstructionFor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitInstructionFor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitInstructionFor(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstructionDeclarationContext extends InstructionContext {
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ZetarianoParser.SEMICOLON, 0); }
		public InstructionDeclarationContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterInstructionDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitInstructionDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitInstructionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstructionPrintContext extends InstructionContext {
		public PrintStatementContext printStatement() {
			return getRuleContext(PrintStatementContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ZetarianoParser.SEMICOLON, 0); }
		public InstructionPrintContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterInstructionPrint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitInstructionPrint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitInstructionPrint(this);
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
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterInstructionIf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitInstructionIf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitInstructionIf(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstructionDoWhileContext extends InstructionContext {
		public DoWhileStatementContext doWhileStatement() {
			return getRuleContext(DoWhileStatementContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ZetarianoParser.SEMICOLON, 0); }
		public InstructionDoWhileContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterInstructionDoWhile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitInstructionDoWhile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitInstructionDoWhile(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstructionJumpContext extends InstructionContext {
		public JumpStatementContext jumpStatement() {
			return getRuleContext(JumpStatementContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ZetarianoParser.SEMICOLON, 0); }
		public InstructionJumpContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterInstructionJump(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitInstructionJump(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitInstructionJump(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstructionArrayDeclarationContext extends InstructionContext {
		public ArrayDeclarationContext arrayDeclaration() {
			return getRuleContext(ArrayDeclarationContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ZetarianoParser.SEMICOLON, 0); }
		public InstructionArrayDeclarationContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterInstructionArrayDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitInstructionArrayDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitInstructionArrayDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstructionReadContext extends InstructionContext {
		public ReadStatementContext readStatement() {
			return getRuleContext(ReadStatementContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ZetarianoParser.SEMICOLON, 0); }
		public InstructionReadContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterInstructionRead(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitInstructionRead(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitInstructionRead(this);
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
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterInstructionWhile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitInstructionWhile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitInstructionWhile(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstructionExpressionContext extends InstructionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ZetarianoParser.SEMICOLON, 0); }
		public InstructionExpressionContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterInstructionExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitInstructionExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitInstructionExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_instruction);
		try {
			setState(191);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				_localctx = new InstructionAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(163);
				assignment();
				setState(164);
				match(SEMICOLON);
				}
				break;
			case 2:
				_localctx = new InstructionReadContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(166);
				readStatement();
				setState(167);
				match(SEMICOLON);
				}
				break;
			case 3:
				_localctx = new InstructionPrintContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(169);
				printStatement();
				setState(170);
				match(SEMICOLON);
				}
				break;
			case 4:
				_localctx = new InstructionIfContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(172);
				ifStatement();
				}
				break;
			case 5:
				_localctx = new InstructionSwitchContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(173);
				switchStatement();
				}
				break;
			case 6:
				_localctx = new InstructionWhileContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(174);
				whileStatement();
				}
				break;
			case 7:
				_localctx = new InstructionDoWhileContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(175);
				doWhileStatement();
				setState(176);
				match(SEMICOLON);
				}
				break;
			case 8:
				_localctx = new InstructionForContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(178);
				forStatement();
				}
				break;
			case 9:
				_localctx = new InstructionJumpContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(179);
				jumpStatement();
				setState(180);
				match(SEMICOLON);
				}
				break;
			case 10:
				_localctx = new InstructionDeclarationContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(182);
				variableDeclaration();
				setState(183);
				match(SEMICOLON);
				}
				break;
			case 11:
				_localctx = new InstructionArrayDeclarationContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(185);
				arrayDeclaration();
				setState(186);
				match(SEMICOLON);
				}
				break;
			case 12:
				_localctx = new InstructionExpressionContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(188);
				expression(0);
				setState(189);
				match(SEMICOLON);
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
	public static class VariableDeclarationContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(ZetarianoParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(ZetarianoParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterVariableDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitVariableDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_variableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			type();
			setState(194);
			match(ID);
			setState(197);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(195);
				match(ASSIGN);
				setState(196);
				expression(0);
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
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(ZetarianoParser.ID, 0); }
		public List<TerminalNode> LEFT_BRACKET() { return getTokens(ZetarianoParser.LEFT_BRACKET); }
		public TerminalNode LEFT_BRACKET(int i) {
			return getToken(ZetarianoParser.LEFT_BRACKET, i);
		}
		public List<TerminalNode> RIGHT_BRACKET() { return getTokens(ZetarianoParser.RIGHT_BRACKET); }
		public TerminalNode RIGHT_BRACKET(int i) {
			return getToken(ZetarianoParser.RIGHT_BRACKET, i);
		}
		public TerminalNode ASSIGN() { return getToken(ZetarianoParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ArrayInitializerContext arrayInitializer() {
			return getRuleContext(ArrayInitializerContext.class,0);
		}
		public ArrayDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterArrayDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitArrayDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitArrayDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayDeclarationContext arrayDeclaration() throws RecognitionException {
		ArrayDeclarationContext _localctx = new ArrayDeclarationContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_arrayDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(199);
			type();
			setState(202); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(200);
				match(LEFT_BRACKET);
				setState(201);
				match(RIGHT_BRACKET);
				}
				}
				setState(204); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==LEFT_BRACKET );
			setState(206);
			match(ID);
			setState(212);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(207);
				match(ASSIGN);
				setState(210);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case NEW:
				case NULL:
				case TRUE:
				case FALSE:
				case PLUS:
				case MINUS:
				case INC:
				case DEC:
				case NOT:
				case LEFT_PAREN:
				case INTEGER:
				case DECIMAL:
				case CHAR:
				case STRING:
				case ID:
					{
					setState(208);
					expression(0);
					}
					break;
				case LEFT_BRACE:
					{
					setState(209);
					arrayInitializer();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
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
	public static class ArrayInitializerContext extends ParserRuleContext {
		public TerminalNode LEFT_BRACE() { return getToken(ZetarianoParser.LEFT_BRACE, 0); }
		public TerminalNode RIGHT_BRACE() { return getToken(ZetarianoParser.RIGHT_BRACE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ArrayInitializerContext> arrayInitializer() {
			return getRuleContexts(ArrayInitializerContext.class);
		}
		public ArrayInitializerContext arrayInitializer(int i) {
			return getRuleContext(ArrayInitializerContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ZetarianoParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ZetarianoParser.COMMA, i);
		}
		public ArrayInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayInitializer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterArrayInitializer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitArrayInitializer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitArrayInitializer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayInitializerContext arrayInitializer() throws RecognitionException {
		ArrayInitializerContext _localctx = new ArrayInitializerContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_arrayInitializer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(214);
			match(LEFT_BRACE);
			setState(217);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NEW:
			case NULL:
			case TRUE:
			case FALSE:
			case PLUS:
			case MINUS:
			case INC:
			case DEC:
			case NOT:
			case LEFT_PAREN:
			case INTEGER:
			case DECIMAL:
			case CHAR:
			case STRING:
			case ID:
				{
				setState(215);
				expression(0);
				}
				break;
			case LEFT_BRACE:
				{
				setState(216);
				arrayInitializer();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(226);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(219);
				match(COMMA);
				setState(222);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case NEW:
				case NULL:
				case TRUE:
				case FALSE:
				case PLUS:
				case MINUS:
				case INC:
				case DEC:
				case NOT:
				case LEFT_PAREN:
				case INTEGER:
				case DECIMAL:
				case CHAR:
				case STRING:
				case ID:
					{
					setState(220);
					expression(0);
					}
					break;
				case LEFT_BRACE:
					{
					setState(221);
					arrayInitializer();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				}
				setState(228);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(229);
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
	public static class AssignmentContext extends ParserRuleContext {
		public LvalueContext lvalue() {
			return getRuleContext(LvalueContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(ZetarianoParser.ASSIGN, 0); }
		public TerminalNode ADD_ASSIGN() { return getToken(ZetarianoParser.ADD_ASSIGN, 0); }
		public TerminalNode SUB_ASSIGN() { return getToken(ZetarianoParser.SUB_ASSIGN, 0); }
		public TerminalNode MULT_ASSIGN() { return getToken(ZetarianoParser.MULT_ASSIGN, 0); }
		public TerminalNode INC() { return getToken(ZetarianoParser.INC, 0); }
		public TerminalNode DEC() { return getToken(ZetarianoParser.DEC, 0); }
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_assignment);
		int _la;
		try {
			setState(238);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(231);
				lvalue(0);
				setState(232);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 131941395333120L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(233);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(235);
				lvalue(0);
				setState(236);
				_la = _input.LA(1);
				if ( !(_la==INC || _la==DEC) ) {
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
		public TerminalNode READLN() { return getToken(ZetarianoParser.READLN, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(ZetarianoParser.LEFT_PAREN, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(ZetarianoParser.RIGHT_PAREN, 0); }
		public ReadStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_readStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterReadStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitReadStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitReadStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReadStatementContext readStatement() throws RecognitionException {
		ReadStatementContext _localctx = new ReadStatementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_readStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(240);
			match(READLN);
			setState(241);
			match(LEFT_PAREN);
			setState(242);
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
	public static class PrintStatementContext extends ParserRuleContext {
		public TerminalNode LEFT_PAREN() { return getToken(ZetarianoParser.LEFT_PAREN, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(ZetarianoParser.RIGHT_PAREN, 0); }
		public TerminalNode PRINT() { return getToken(ZetarianoParser.PRINT, 0); }
		public TerminalNode PRINTLN() { return getToken(ZetarianoParser.PRINTLN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PrintStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_printStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterPrintStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitPrintStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitPrintStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrintStatementContext printStatement() throws RecognitionException {
		PrintStatementContext _localctx = new PrintStatementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_printStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244);
			_la = _input.LA(1);
			if ( !(_la==PRINTLN || _la==PRINT) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(245);
			match(LEFT_PAREN);
			setState(247);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8939649671765165568L) != 0)) {
				{
				setState(246);
				expression(0);
				}
			}

			setState(249);
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
	public static class IfStatementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(ZetarianoParser.IF, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(ZetarianoParser.LEFT_PAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(ZetarianoParser.RIGHT_PAREN, 0); }
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(ZetarianoParser.ELSE, 0); }
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(251);
			match(IF);
			setState(252);
			match(LEFT_PAREN);
			setState(253);
			expression(0);
			setState(254);
			match(RIGHT_PAREN);
			setState(255);
			block();
			setState(258);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				{
				setState(256);
				match(ELSE);
				setState(257);
				block();
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
	public static class SwitchStatementContext extends ParserRuleContext {
		public TerminalNode SWITCH() { return getToken(ZetarianoParser.SWITCH, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(ZetarianoParser.LEFT_PAREN, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(ZetarianoParser.RIGHT_PAREN, 0); }
		public TerminalNode LEFT_BRACE() { return getToken(ZetarianoParser.LEFT_BRACE, 0); }
		public TerminalNode RIGHT_BRACE() { return getToken(ZetarianoParser.RIGHT_BRACE, 0); }
		public List<TerminalNode> CASE() { return getTokens(ZetarianoParser.CASE); }
		public TerminalNode CASE(int i) {
			return getToken(ZetarianoParser.CASE, i);
		}
		public List<TerminalNode> COLON() { return getTokens(ZetarianoParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(ZetarianoParser.COLON, i);
		}
		public List<MainInstructionsContext> mainInstructions() {
			return getRuleContexts(MainInstructionsContext.class);
		}
		public MainInstructionsContext mainInstructions(int i) {
			return getRuleContext(MainInstructionsContext.class,i);
		}
		public TerminalNode DEFAULT() { return getToken(ZetarianoParser.DEFAULT, 0); }
		public SwitchStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterSwitchStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitSwitchStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitSwitchStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchStatementContext switchStatement() throws RecognitionException {
		SwitchStatementContext _localctx = new SwitchStatementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_switchStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(260);
			match(SWITCH);
			setState(261);
			match(LEFT_PAREN);
			setState(262);
			expression(0);
			setState(263);
			match(RIGHT_PAREN);
			setState(264);
			match(LEFT_BRACE);
			setState(272);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CASE) {
				{
				{
				setState(265);
				match(CASE);
				setState(266);
				expression(0);
				setState(267);
				match(COLON);
				setState(268);
				mainInstructions();
				}
				}
				setState(274);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(278);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEFAULT) {
				{
				setState(275);
				match(DEFAULT);
				setState(276);
				match(COLON);
				setState(277);
				mainInstructions();
				}
			}

			setState(280);
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
	public static class WhileStatementContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(ZetarianoParser.WHILE, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(ZetarianoParser.LEFT_PAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(ZetarianoParser.RIGHT_PAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(282);
			match(WHILE);
			setState(283);
			match(LEFT_PAREN);
			setState(284);
			expression(0);
			setState(285);
			match(RIGHT_PAREN);
			setState(286);
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
	public static class DoWhileStatementContext extends ParserRuleContext {
		public TerminalNode DO() { return getToken(ZetarianoParser.DO, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode WHILE() { return getToken(ZetarianoParser.WHILE, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(ZetarianoParser.LEFT_PAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(ZetarianoParser.RIGHT_PAREN, 0); }
		public DoWhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doWhileStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterDoWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitDoWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitDoWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DoWhileStatementContext doWhileStatement() throws RecognitionException {
		DoWhileStatementContext _localctx = new DoWhileStatementContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_doWhileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(288);
			match(DO);
			setState(289);
			block();
			setState(290);
			match(WHILE);
			setState(291);
			match(LEFT_PAREN);
			setState(292);
			expression(0);
			setState(293);
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
	public static class ForStatementContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(ZetarianoParser.FOR, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(ZetarianoParser.LEFT_PAREN, 0); }
		public List<TerminalNode> SEMICOLON() { return getTokens(ZetarianoParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(ZetarianoParser.SEMICOLON, i);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(ZetarianoParser.RIGHT_PAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public List<AssignmentContext> assignment() {
			return getRuleContexts(AssignmentContext.class);
		}
		public AssignmentContext assignment(int i) {
			return getRuleContext(AssignmentContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterForStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitForStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_forStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(295);
			match(FOR);
			setState(296);
			match(LEFT_PAREN);
			setState(299);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				{
				setState(297);
				variableDeclaration();
				}
				break;
			case 2:
				{
				setState(298);
				assignment();
				}
				break;
			}
			setState(301);
			match(SEMICOLON);
			setState(303);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8939649671765165568L) != 0)) {
				{
				setState(302);
				expression(0);
				}
			}

			setState(305);
			match(SEMICOLON);
			setState(308);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				{
				setState(306);
				assignment();
				}
				break;
			case 2:
				{
				setState(307);
				expression(0);
				}
				break;
			}
			setState(310);
			match(RIGHT_PAREN);
			setState(311);
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
	public static class JumpReturnContext extends JumpStatementContext {
		public TerminalNode RETURN() { return getToken(ZetarianoParser.RETURN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public JumpReturnContext(JumpStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterJumpReturn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitJumpReturn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitJumpReturn(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JumpContinueContext extends JumpStatementContext {
		public TerminalNode CONTINUE() { return getToken(ZetarianoParser.CONTINUE, 0); }
		public JumpContinueContext(JumpStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterJumpContinue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitJumpContinue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitJumpContinue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JumpBreakContext extends JumpStatementContext {
		public TerminalNode BREAK() { return getToken(ZetarianoParser.BREAK, 0); }
		public JumpBreakContext(JumpStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterJumpBreak(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitJumpBreak(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitJumpBreak(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JumpStatementContext jumpStatement() throws RecognitionException {
		JumpStatementContext _localctx = new JumpStatementContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_jumpStatement);
		int _la;
		try {
			setState(319);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BREAK:
				_localctx = new JumpBreakContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(313);
				match(BREAK);
				}
				break;
			case CONTINUE:
				_localctx = new JumpContinueContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(314);
				match(CONTINUE);
				}
				break;
			case RETURN:
				_localctx = new JumpReturnContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(315);
				match(RETURN);
				setState(317);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8939649671765165568L) != 0)) {
					{
					setState(316);
					expression(0);
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
	public static class LvalueContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ZetarianoParser.ID, 0); }
		public LvalueContext lvalue() {
			return getRuleContext(LvalueContext.class,0);
		}
		public TerminalNode DOT() { return getToken(ZetarianoParser.DOT, 0); }
		public TerminalNode LEFT_BRACKET() { return getToken(ZetarianoParser.LEFT_BRACKET, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RIGHT_BRACKET() { return getToken(ZetarianoParser.RIGHT_BRACKET, 0); }
		public LvalueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lvalue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterLvalue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitLvalue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitLvalue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LvalueContext lvalue() throws RecognitionException {
		return lvalue(0);
	}

	private LvalueContext lvalue(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		LvalueContext _localctx = new LvalueContext(_ctx, _parentState);
		LvalueContext _prevctx = _localctx;
		int _startState = 48;
		enterRecursionRule(_localctx, 48, RULE_lvalue, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(322);
			match(ID);
			}
			_ctx.stop = _input.LT(-1);
			setState(334);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(332);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
					case 1:
						{
						_localctx = new LvalueContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_lvalue);
						setState(324);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(325);
						match(DOT);
						setState(326);
						match(ID);
						}
						break;
					case 2:
						{
						_localctx = new LvalueContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_lvalue);
						setState(327);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(328);
						match(LEFT_BRACKET);
						setState(329);
						expression(0);
						setState(330);
						match(RIGHT_BRACKET);
						}
						break;
					}
					} 
				}
				setState(336);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(ZetarianoParser.INT, 0); }
		public TerminalNode DOUBLE() { return getToken(ZetarianoParser.DOUBLE, 0); }
		public TerminalNode CHAR_TYPE() { return getToken(ZetarianoParser.CHAR_TYPE, 0); }
		public TerminalNode BOOLEAN() { return getToken(ZetarianoParser.BOOLEAN, 0); }
		public TerminalNode STRING_TYPE() { return getToken(ZetarianoParser.STRING_TYPE, 0); }
		public TerminalNode ID() { return getToken(ZetarianoParser.ID, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(337);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 4611686018427388152L) != 0)) ) {
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
	public static class ExprPrimaryContext extends ExpressionContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public ExprPrimaryContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterExprPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitExprPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitExprPrimary(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprAndContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode AND() { return getToken(ZetarianoParser.AND, 0); }
		public ExprAndContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterExprAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitExprAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitExprAnd(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprTernaryContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode QUESTION() { return getToken(ZetarianoParser.QUESTION, 0); }
		public TerminalNode COLON() { return getToken(ZetarianoParser.COLON, 0); }
		public ExprTernaryContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterExprTernary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitExprTernary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitExprTernary(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprParenContext extends ExpressionContext {
		public TerminalNode LEFT_PAREN() { return getToken(ZetarianoParser.LEFT_PAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(ZetarianoParser.RIGHT_PAREN, 0); }
		public ExprParenContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterExprParen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitExprParen(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitExprParen(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprOrContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode OR() { return getToken(ZetarianoParser.OR, 0); }
		public ExprOrContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterExprOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitExprOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitExprOr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprMultiplicativeContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode MULT() { return getToken(ZetarianoParser.MULT, 0); }
		public TerminalNode DIV() { return getToken(ZetarianoParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(ZetarianoParser.MOD, 0); }
		public ExprMultiplicativeContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterExprMultiplicative(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitExprMultiplicative(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitExprMultiplicative(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprUnaryContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode PLUS() { return getToken(ZetarianoParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(ZetarianoParser.MINUS, 0); }
		public TerminalNode NOT() { return getToken(ZetarianoParser.NOT, 0); }
		public TerminalNode INC() { return getToken(ZetarianoParser.INC, 0); }
		public TerminalNode DEC() { return getToken(ZetarianoParser.DEC, 0); }
		public ExprUnaryContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterExprUnary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitExprUnary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitExprUnary(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprRelationalContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode LESS() { return getToken(ZetarianoParser.LESS, 0); }
		public TerminalNode GREATER() { return getToken(ZetarianoParser.GREATER, 0); }
		public TerminalNode LESSEQUAL() { return getToken(ZetarianoParser.LESSEQUAL, 0); }
		public TerminalNode GREATEREQUAL() { return getToken(ZetarianoParser.GREATEREQUAL, 0); }
		public ExprRelationalContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterExprRelational(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitExprRelational(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitExprRelational(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprEqualityContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode EQUAL() { return getToken(ZetarianoParser.EQUAL, 0); }
		public TerminalNode NOTEQUAL() { return getToken(ZetarianoParser.NOTEQUAL, 0); }
		public ExprEqualityContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterExprEquality(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitExprEquality(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitExprEquality(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprAdditiveContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(ZetarianoParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(ZetarianoParser.MINUS, 0); }
		public ExprAdditiveContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterExprAdditive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitExprAdditive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitExprAdditive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 52;
		enterRecursionRule(_localctx, 52, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(347);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LEFT_PAREN:
				{
				_localctx = new ExprParenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(340);
				match(LEFT_PAREN);
				setState(341);
				expression(0);
				setState(342);
				match(RIGHT_PAREN);
				}
				break;
			case PLUS:
			case MINUS:
			case INC:
			case DEC:
			case NOT:
				{
				_localctx = new ExprUnaryContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(344);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 4411334066176L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(345);
				expression(9);
				}
				break;
			case NEW:
			case NULL:
			case TRUE:
			case FALSE:
			case INTEGER:
			case DECIMAL:
			case CHAR:
			case STRING:
			case ID:
				{
				_localctx = new ExprPrimaryContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(346);
				primary();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(375);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(373);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
					case 1:
						{
						_localctx = new ExprMultiplicativeContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(349);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(350);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 3758096384L) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(351);
						expression(9);
						}
						break;
					case 2:
						{
						_localctx = new ExprAdditiveContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(352);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(353);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(354);
						expression(8);
						}
						break;
					case 3:
						{
						_localctx = new ExprRelationalContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(355);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(356);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1030792151040L) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(357);
						expression(7);
						}
						break;
					case 4:
						{
						_localctx = new ExprEqualityContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(358);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(359);
						_la = _input.LA(1);
						if ( !(_la==EQUAL || _la==NOTEQUAL) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(360);
						expression(6);
						}
						break;
					case 5:
						{
						_localctx = new ExprAndContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(361);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(362);
						match(AND);
						setState(363);
						expression(5);
						}
						break;
					case 6:
						{
						_localctx = new ExprOrContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(364);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(365);
						match(OR);
						setState(366);
						expression(4);
						}
						break;
					case 7:
						{
						_localctx = new ExprTernaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(367);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(368);
						match(QUESTION);
						setState(369);
						expression(0);
						setState(370);
						match(COLON);
						setState(371);
						expression(3);
						}
						break;
					}
					} 
				}
				setState(377);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryContext extends ParserRuleContext {
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
	 
		public PrimaryContext() { }
		public void copyFrom(PrimaryContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryLiteralContext extends PrimaryContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public PrimaryLiteralContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterPrimaryLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitPrimaryLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitPrimaryLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryNewObjectContext extends PrimaryContext {
		public TerminalNode NEW() { return getToken(ZetarianoParser.NEW, 0); }
		public TerminalNode ID() { return getToken(ZetarianoParser.ID, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(ZetarianoParser.LEFT_PAREN, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(ZetarianoParser.RIGHT_PAREN, 0); }
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public PrimaryNewObjectContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterPrimaryNewObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitPrimaryNewObject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitPrimaryNewObject(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryLvalueOrCallContext extends PrimaryContext {
		public LvalueContext lvalue() {
			return getRuleContext(LvalueContext.class,0);
		}
		public TerminalNode LEFT_PAREN() { return getToken(ZetarianoParser.LEFT_PAREN, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(ZetarianoParser.RIGHT_PAREN, 0); }
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public PrimaryLvalueOrCallContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterPrimaryLvalueOrCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitPrimaryLvalueOrCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitPrimaryLvalueOrCall(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryNewArrayContext extends PrimaryContext {
		public TerminalNode NEW() { return getToken(ZetarianoParser.NEW, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<TerminalNode> LEFT_BRACKET() { return getTokens(ZetarianoParser.LEFT_BRACKET); }
		public TerminalNode LEFT_BRACKET(int i) {
			return getToken(ZetarianoParser.LEFT_BRACKET, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> RIGHT_BRACKET() { return getTokens(ZetarianoParser.RIGHT_BRACKET); }
		public TerminalNode RIGHT_BRACKET(int i) {
			return getToken(ZetarianoParser.RIGHT_BRACKET, i);
		}
		public PrimaryNewArrayContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterPrimaryNewArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitPrimaryNewArray(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitPrimaryNewArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_primary);
		int _la;
		try {
			int _alt;
			setState(404);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
			case 1:
				_localctx = new PrimaryLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(378);
				literal();
				}
				break;
			case 2:
				_localctx = new PrimaryNewObjectContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(379);
				match(NEW);
				setState(380);
				match(ID);
				setState(381);
				match(LEFT_PAREN);
				setState(383);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8939649671765165568L) != 0)) {
					{
					setState(382);
					argumentList();
					}
				}

				setState(385);
				match(RIGHT_PAREN);
				}
				break;
			case 3:
				_localctx = new PrimaryNewArrayContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(386);
				match(NEW);
				setState(387);
				type();
				setState(392); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(388);
						match(LEFT_BRACKET);
						setState(389);
						expression(0);
						setState(390);
						match(RIGHT_BRACKET);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(394); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,40,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			case 4:
				_localctx = new PrimaryLvalueOrCallContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(396);
				lvalue(0);
				setState(402);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
				case 1:
					{
					setState(397);
					match(LEFT_PAREN);
					setState(399);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8939649671765165568L) != 0)) {
						{
						setState(398);
						argumentList();
						}
					}

					setState(401);
					match(RIGHT_PAREN);
					}
					break;
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
	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(ZetarianoParser.INTEGER, 0); }
		public TerminalNode DECIMAL() { return getToken(ZetarianoParser.DECIMAL, 0); }
		public TerminalNode CHAR() { return getToken(ZetarianoParser.CHAR, 0); }
		public TerminalNode STRING() { return getToken(ZetarianoParser.STRING, 0); }
		public TerminalNode TRUE() { return getToken(ZetarianoParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(ZetarianoParser.FALSE, 0); }
		public TerminalNode NULL() { return getToken(ZetarianoParser.NULL, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(406);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 4323455642376340480L) != 0)) ) {
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
	public static class ArgumentListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ZetarianoParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ZetarianoParser.COMMA, i);
		}
		public ArgumentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).enterArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZetarianoParserListener ) ((ZetarianoParserListener)listener).exitArgumentList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZetarianoParserVisitor ) return ((ZetarianoParserVisitor<? extends T>)visitor).visitArgumentList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentListContext argumentList() throws RecognitionException {
		ArgumentListContext _localctx = new ArgumentListContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_argumentList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(408);
			expression(0);
			setState(413);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(409);
				match(COMMA);
				setState(410);
				expression(0);
				}
				}
				setState(415);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 24:
			return lvalue_sempred((LvalueContext)_localctx, predIndex);
		case 26:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean lvalue_sempred(LvalueContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 8);
		case 3:
			return precpred(_ctx, 7);
		case 4:
			return precpred(_ctx, 6);
		case 5:
			return precpred(_ctx, 5);
		case 6:
			return precpred(_ctx, 4);
		case 7:
			return precpred(_ctx, 3);
		case 8:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001A\u01a1\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0003\u0001A\b\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0005\u0002"+
		"J\b\u0002\n\u0002\f\u0002M\t\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0003\u0003R\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004"+
		"W\b\u0004\n\u0004\f\u0004Z\t\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0003\u0004`\b\u0004\u0003\u0004b\b\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0005\u0003\u0005g\b\u0005\u0001\u0005\u0001\u0005\u0003"+
		"\u0005k\b\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005p\b\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0003\u0006v\b\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006{\b\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007"+
		"\u0083\b\u0007\n\u0007\f\u0007\u0086\t\u0007\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0005\b\u008e\b\b\n\b\f\b\u0091\t\b\u0001\b\u0001\b"+
		"\u0003\b\u0095\b\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\t\u009c"+
		"\b\t\u0001\n\u0005\n\u009f\b\n\n\n\f\n\u00a2\t\n\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0003\u000b\u00c0\b\u000b\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0003\f\u00c6\b\f\u0001\r\u0001\r\u0001\r\u0004\r\u00cb\b\r"+
		"\u000b\r\f\r\u00cc\u0001\r\u0001\r\u0001\r\u0001\r\u0003\r\u00d3\b\r\u0003"+
		"\r\u00d5\b\r\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u00da\b\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u00df\b\u000e\u0005\u000e"+
		"\u00e1\b\u000e\n\u000e\f\u000e\u00e4\t\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0003\u000f\u00ef\b\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0003\u0011\u00f8\b\u0011\u0001"+
		"\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u0103\b\u0012\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0005\u0013\u010f\b\u0013\n\u0013\f\u0013"+
		"\u0112\t\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0003\u0013\u0117\b"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0003\u0016\u012c\b\u0016\u0001\u0016\u0001\u0016\u0003"+
		"\u0016\u0130\b\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0003\u0016\u0135"+
		"\b\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001"+
		"\u0017\u0001\u0017\u0003\u0017\u013e\b\u0017\u0003\u0017\u0140\b\u0017"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0005\u0018"+
		"\u014d\b\u0018\n\u0018\f\u0018\u0150\t\u0018\u0001\u0019\u0001\u0019\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0003\u001a\u015c\b\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0005\u001a\u0176\b\u001a\n"+
		"\u001a\f\u001a\u0179\t\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0001"+
		"\u001b\u0001\u001b\u0003\u001b\u0180\b\u001b\u0001\u001b\u0001\u001b\u0001"+
		"\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0004\u001b\u0189"+
		"\b\u001b\u000b\u001b\f\u001b\u018a\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0003\u001b\u0190\b\u001b\u0001\u001b\u0003\u001b\u0193\b\u001b\u0003"+
		"\u001b\u0195\b\u001b\u0001\u001c\u0001\u001c\u0001\u001d\u0001\u001d\u0001"+
		"\u001d\u0005\u001d\u019c\b\u001d\n\u001d\f\u001d\u019f\t\u001d\u0001\u001d"+
		"\u0000\u000204\u001e\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014"+
		"\u0016\u0018\u001a\u001c\u001e \"$&(*,.02468:\u0000\n\u0001\u0000+.\u0001"+
		"\u0000 !\u0001\u0000\u0016\u0017\u0002\u0000\u0003\u0007>>\u0003\u0000"+
		"\u001b\u001c !**\u0001\u0000\u001d\u001f\u0001\u0000\u001b\u001c\u0001"+
		"\u0000$\'\u0001\u0000\"#\u0003\u0000\n\n\u0019\u001a:=\u01c5\u0000<\u0001"+
		"\u0000\u0000\u0000\u0002@\u0001\u0000\u0000\u0000\u0004K\u0001\u0000\u0000"+
		"\u0000\u0006Q\u0001\u0000\u0000\u0000\bS\u0001\u0000\u0000\u0000\nf\u0001"+
		"\u0000\u0000\u0000\fu\u0001\u0000\u0000\u0000\u000e\u007f\u0001\u0000"+
		"\u0000\u0000\u0010\u0094\u0001\u0000\u0000\u0000\u0012\u009b\u0001\u0000"+
		"\u0000\u0000\u0014\u00a0\u0001\u0000\u0000\u0000\u0016\u00bf\u0001\u0000"+
		"\u0000\u0000\u0018\u00c1\u0001\u0000\u0000\u0000\u001a\u00c7\u0001\u0000"+
		"\u0000\u0000\u001c\u00d6\u0001\u0000\u0000\u0000\u001e\u00ee\u0001\u0000"+
		"\u0000\u0000 \u00f0\u0001\u0000\u0000\u0000\"\u00f4\u0001\u0000\u0000"+
		"\u0000$\u00fb\u0001\u0000\u0000\u0000&\u0104\u0001\u0000\u0000\u0000("+
		"\u011a\u0001\u0000\u0000\u0000*\u0120\u0001\u0000\u0000\u0000,\u0127\u0001"+
		"\u0000\u0000\u0000.\u013f\u0001\u0000\u0000\u00000\u0141\u0001\u0000\u0000"+
		"\u00002\u0151\u0001\u0000\u0000\u00004\u015b\u0001\u0000\u0000\u00006"+
		"\u0194\u0001\u0000\u0000\u00008\u0196\u0001\u0000\u0000\u0000:\u0198\u0001"+
		"\u0000\u0000\u0000<=\u0003\u0002\u0001\u0000=>\u0005\u0000\u0000\u0001"+
		">\u0001\u0001\u0000\u0000\u0000?A\u0005\u0001\u0000\u0000@?\u0001\u0000"+
		"\u0000\u0000@A\u0001\u0000\u0000\u0000AB\u0001\u0000\u0000\u0000BC\u0005"+
		"\u0002\u0000\u0000CD\u0005>\u0000\u0000DE\u00056\u0000\u0000EF\u0003\u0004"+
		"\u0002\u0000FG\u00057\u0000\u0000G\u0003\u0001\u0000\u0000\u0000HJ\u0003"+
		"\u0006\u0003\u0000IH\u0001\u0000\u0000\u0000JM\u0001\u0000\u0000\u0000"+
		"KI\u0001\u0000\u0000\u0000KL\u0001\u0000\u0000\u0000L\u0005\u0001\u0000"+
		"\u0000\u0000MK\u0001\u0000\u0000\u0000NR\u0003\b\u0004\u0000OR\u0003\n"+
		"\u0005\u0000PR\u0003\f\u0006\u0000QN\u0001\u0000\u0000\u0000QO\u0001\u0000"+
		"\u0000\u0000QP\u0001\u0000\u0000\u0000R\u0007\u0001\u0000\u0000\u0000"+
		"SX\u00032\u0019\u0000TU\u00058\u0000\u0000UW\u00059\u0000\u0000VT\u0001"+
		"\u0000\u0000\u0000WZ\u0001\u0000\u0000\u0000XV\u0001\u0000\u0000\u0000"+
		"XY\u0001\u0000\u0000\u0000Y[\u0001\u0000\u0000\u0000ZX\u0001\u0000\u0000"+
		"\u0000[a\u0005>\u0000\u0000\\_\u0005+\u0000\u0000]`\u00034\u001a\u0000"+
		"^`\u0003\u001c\u000e\u0000_]\u0001\u0000\u0000\u0000_^\u0001\u0000\u0000"+
		"\u0000`b\u0001\u0000\u0000\u0000a\\\u0001\u0000\u0000\u0000ab\u0001\u0000"+
		"\u0000\u0000bc\u0001\u0000\u0000\u0000cd\u00051\u0000\u0000d\t\u0001\u0000"+
		"\u0000\u0000eg\u0005\u0001\u0000\u0000fe\u0001\u0000\u0000\u0000fg\u0001"+
		"\u0000\u0000\u0000gj\u0001\u0000\u0000\u0000hk\u00032\u0019\u0000ik\u0005"+
		"\b\u0000\u0000jh\u0001\u0000\u0000\u0000ji\u0001\u0000\u0000\u0000kl\u0001"+
		"\u0000\u0000\u0000lm\u0005>\u0000\u0000mo\u00054\u0000\u0000np\u0003\u000e"+
		"\u0007\u0000on\u0001\u0000\u0000\u0000op\u0001\u0000\u0000\u0000pq\u0001"+
		"\u0000\u0000\u0000qr\u00055\u0000\u0000rs\u0003\u0012\t\u0000s\u000b\u0001"+
		"\u0000\u0000\u0000tv\u0005\u0001\u0000\u0000ut\u0001\u0000\u0000\u0000"+
		"uv\u0001\u0000\u0000\u0000vw\u0001\u0000\u0000\u0000wx\u0005>\u0000\u0000"+
		"xz\u00054\u0000\u0000y{\u0003\u000e\u0007\u0000zy\u0001\u0000\u0000\u0000"+
		"z{\u0001\u0000\u0000\u0000{|\u0001\u0000\u0000\u0000|}\u00055\u0000\u0000"+
		"}~\u0003\u0012\t\u0000~\r\u0001\u0000\u0000\u0000\u007f\u0084\u0003\u0010"+
		"\b\u0000\u0080\u0081\u00052\u0000\u0000\u0081\u0083\u0003\u0010\b\u0000"+
		"\u0082\u0080\u0001\u0000\u0000\u0000\u0083\u0086\u0001\u0000\u0000\u0000"+
		"\u0084\u0082\u0001\u0000\u0000\u0000\u0084\u0085\u0001\u0000\u0000\u0000"+
		"\u0085\u000f\u0001\u0000\u0000\u0000\u0086\u0084\u0001\u0000\u0000\u0000"+
		"\u0087\u0088\u00032\u0019\u0000\u0088\u0089\u0005>\u0000\u0000\u0089\u0095"+
		"\u0001\u0000\u0000\u0000\u008a\u008f\u00032\u0019\u0000\u008b\u008c\u0005"+
		"8\u0000\u0000\u008c\u008e\u00059\u0000\u0000\u008d\u008b\u0001\u0000\u0000"+
		"\u0000\u008e\u0091\u0001\u0000\u0000\u0000\u008f\u008d\u0001\u0000\u0000"+
		"\u0000\u008f\u0090\u0001\u0000\u0000\u0000\u0090\u0092\u0001\u0000\u0000"+
		"\u0000\u0091\u008f\u0001\u0000\u0000\u0000\u0092\u0093\u0005>\u0000\u0000"+
		"\u0093\u0095\u0001\u0000\u0000\u0000\u0094\u0087\u0001\u0000\u0000\u0000"+
		"\u0094\u008a\u0001\u0000\u0000\u0000\u0095\u0011\u0001\u0000\u0000\u0000"+
		"\u0096\u0097\u00056\u0000\u0000\u0097\u0098\u0003\u0014\n\u0000\u0098"+
		"\u0099\u00057\u0000\u0000\u0099\u009c\u0001\u0000\u0000\u0000\u009a\u009c"+
		"\u0003\u0016\u000b\u0000\u009b\u0096\u0001\u0000\u0000\u0000\u009b\u009a"+
		"\u0001\u0000\u0000\u0000\u009c\u0013\u0001\u0000\u0000\u0000\u009d\u009f"+
		"\u0003\u0016\u000b\u0000\u009e\u009d\u0001\u0000\u0000\u0000\u009f\u00a2"+
		"\u0001\u0000\u0000\u0000\u00a0\u009e\u0001\u0000\u0000\u0000\u00a0\u00a1"+
		"\u0001\u0000\u0000\u0000\u00a1\u0015\u0001\u0000\u0000\u0000\u00a2\u00a0"+
		"\u0001\u0000\u0000\u0000\u00a3\u00a4\u0003\u001e\u000f\u0000\u00a4\u00a5"+
		"\u00051\u0000\u0000\u00a5\u00c0\u0001\u0000\u0000\u0000\u00a6\u00a7\u0003"+
		" \u0010\u0000\u00a7\u00a8\u00051\u0000\u0000\u00a8\u00c0\u0001\u0000\u0000"+
		"\u0000\u00a9\u00aa\u0003\"\u0011\u0000\u00aa\u00ab\u00051\u0000\u0000"+
		"\u00ab\u00c0\u0001\u0000\u0000\u0000\u00ac\u00c0\u0003$\u0012\u0000\u00ad"+
		"\u00c0\u0003&\u0013\u0000\u00ae\u00c0\u0003(\u0014\u0000\u00af\u00b0\u0003"+
		"*\u0015\u0000\u00b0\u00b1\u00051\u0000\u0000\u00b1\u00c0\u0001\u0000\u0000"+
		"\u0000\u00b2\u00c0\u0003,\u0016\u0000\u00b3\u00b4\u0003.\u0017\u0000\u00b4"+
		"\u00b5\u00051\u0000\u0000\u00b5\u00c0\u0001\u0000\u0000\u0000\u00b6\u00b7"+
		"\u0003\u0018\f\u0000\u00b7\u00b8\u00051\u0000\u0000\u00b8\u00c0\u0001"+
		"\u0000\u0000\u0000\u00b9\u00ba\u0003\u001a\r\u0000\u00ba\u00bb\u00051"+
		"\u0000\u0000\u00bb\u00c0\u0001\u0000\u0000\u0000\u00bc\u00bd\u00034\u001a"+
		"\u0000\u00bd\u00be\u00051\u0000\u0000\u00be\u00c0\u0001\u0000\u0000\u0000"+
		"\u00bf\u00a3\u0001\u0000\u0000\u0000\u00bf\u00a6\u0001\u0000\u0000\u0000"+
		"\u00bf\u00a9\u0001\u0000\u0000\u0000\u00bf\u00ac\u0001\u0000\u0000\u0000"+
		"\u00bf\u00ad\u0001\u0000\u0000\u0000\u00bf\u00ae\u0001\u0000\u0000\u0000"+
		"\u00bf\u00af\u0001\u0000\u0000\u0000\u00bf\u00b2\u0001\u0000\u0000\u0000"+
		"\u00bf\u00b3\u0001\u0000\u0000\u0000\u00bf\u00b6\u0001\u0000\u0000\u0000"+
		"\u00bf\u00b9\u0001\u0000\u0000\u0000\u00bf\u00bc\u0001\u0000\u0000\u0000"+
		"\u00c0\u0017\u0001\u0000\u0000\u0000\u00c1\u00c2\u00032\u0019\u0000\u00c2"+
		"\u00c5\u0005>\u0000\u0000\u00c3\u00c4\u0005+\u0000\u0000\u00c4\u00c6\u0003"+
		"4\u001a\u0000\u00c5\u00c3\u0001\u0000\u0000\u0000\u00c5\u00c6\u0001\u0000"+
		"\u0000\u0000\u00c6\u0019\u0001\u0000\u0000\u0000\u00c7\u00ca\u00032\u0019"+
		"\u0000\u00c8\u00c9\u00058\u0000\u0000\u00c9\u00cb\u00059\u0000\u0000\u00ca"+
		"\u00c8\u0001\u0000\u0000\u0000\u00cb\u00cc\u0001\u0000\u0000\u0000\u00cc"+
		"\u00ca\u0001\u0000\u0000\u0000\u00cc\u00cd\u0001\u0000\u0000\u0000\u00cd"+
		"\u00ce\u0001\u0000\u0000\u0000\u00ce\u00d4\u0005>\u0000\u0000\u00cf\u00d2"+
		"\u0005+\u0000\u0000\u00d0\u00d3\u00034\u001a\u0000\u00d1\u00d3\u0003\u001c"+
		"\u000e\u0000\u00d2\u00d0\u0001\u0000\u0000\u0000\u00d2\u00d1\u0001\u0000"+
		"\u0000\u0000\u00d3\u00d5\u0001\u0000\u0000\u0000\u00d4\u00cf\u0001\u0000"+
		"\u0000\u0000\u00d4\u00d5\u0001\u0000\u0000\u0000\u00d5\u001b\u0001\u0000"+
		"\u0000\u0000\u00d6\u00d9\u00056\u0000\u0000\u00d7\u00da\u00034\u001a\u0000"+
		"\u00d8\u00da\u0003\u001c\u000e\u0000\u00d9\u00d7\u0001\u0000\u0000\u0000"+
		"\u00d9\u00d8\u0001\u0000\u0000\u0000\u00da\u00e2\u0001\u0000\u0000\u0000"+
		"\u00db\u00de\u00052\u0000\u0000\u00dc\u00df\u00034\u001a\u0000\u00dd\u00df"+
		"\u0003\u001c\u000e\u0000\u00de\u00dc\u0001\u0000\u0000\u0000\u00de\u00dd"+
		"\u0001\u0000\u0000\u0000\u00df\u00e1\u0001\u0000\u0000\u0000\u00e0\u00db"+
		"\u0001\u0000\u0000\u0000\u00e1\u00e4\u0001\u0000\u0000\u0000\u00e2\u00e0"+
		"\u0001\u0000\u0000\u0000\u00e2\u00e3\u0001\u0000\u0000\u0000\u00e3\u00e5"+
		"\u0001\u0000\u0000\u0000\u00e4\u00e2\u0001\u0000\u0000\u0000\u00e5\u00e6"+
		"\u00057\u0000\u0000\u00e6\u001d\u0001\u0000\u0000\u0000\u00e7\u00e8\u0003"+
		"0\u0018\u0000\u00e8\u00e9\u0007\u0000\u0000\u0000\u00e9\u00ea\u00034\u001a"+
		"\u0000\u00ea\u00ef\u0001\u0000\u0000\u0000\u00eb\u00ec\u00030\u0018\u0000"+
		"\u00ec\u00ed\u0007\u0001\u0000\u0000\u00ed\u00ef\u0001\u0000\u0000\u0000"+
		"\u00ee\u00e7\u0001\u0000\u0000\u0000\u00ee\u00eb\u0001\u0000\u0000\u0000"+
		"\u00ef\u001f\u0001\u0000\u0000\u0000\u00f0\u00f1\u0005\u0018\u0000\u0000"+
		"\u00f1\u00f2\u00054\u0000\u0000\u00f2\u00f3\u00055\u0000\u0000\u00f3!"+
		"\u0001\u0000\u0000\u0000\u00f4\u00f5\u0007\u0002\u0000\u0000\u00f5\u00f7"+
		"\u00054\u0000\u0000\u00f6\u00f8\u00034\u001a\u0000\u00f7\u00f6\u0001\u0000"+
		"\u0000\u0000\u00f7\u00f8\u0001\u0000\u0000\u0000\u00f8\u00f9\u0001\u0000"+
		"\u0000\u0000\u00f9\u00fa\u00055\u0000\u0000\u00fa#\u0001\u0000\u0000\u0000"+
		"\u00fb\u00fc\u0005\u000b\u0000\u0000\u00fc\u00fd\u00054\u0000\u0000\u00fd"+
		"\u00fe\u00034\u001a\u0000\u00fe\u00ff\u00055\u0000\u0000\u00ff\u0102\u0003"+
		"\u0012\t\u0000\u0100\u0101\u0005\f\u0000\u0000\u0101\u0103\u0003\u0012"+
		"\t\u0000\u0102\u0100\u0001\u0000\u0000\u0000\u0102\u0103\u0001\u0000\u0000"+
		"\u0000\u0103%\u0001\u0000\u0000\u0000\u0104\u0105\u0005\r\u0000\u0000"+
		"\u0105\u0106\u00054\u0000\u0000\u0106\u0107\u00034\u001a\u0000\u0107\u0108"+
		"\u00055\u0000\u0000\u0108\u0110\u00056\u0000\u0000\u0109\u010a\u0005\u000e"+
		"\u0000\u0000\u010a\u010b\u00034\u001a\u0000\u010b\u010c\u00050\u0000\u0000"+
		"\u010c\u010d\u0003\u0014\n\u0000\u010d\u010f\u0001\u0000\u0000\u0000\u010e"+
		"\u0109\u0001\u0000\u0000\u0000\u010f\u0112\u0001\u0000\u0000\u0000\u0110"+
		"\u010e\u0001\u0000\u0000\u0000\u0110\u0111\u0001\u0000\u0000\u0000\u0111"+
		"\u0116\u0001\u0000\u0000\u0000\u0112\u0110\u0001\u0000\u0000\u0000\u0113"+
		"\u0114\u0005\u000f\u0000\u0000\u0114\u0115\u00050\u0000\u0000\u0115\u0117"+
		"\u0003\u0014\n\u0000\u0116\u0113\u0001\u0000\u0000\u0000\u0116\u0117\u0001"+
		"\u0000\u0000\u0000\u0117\u0118\u0001\u0000\u0000\u0000\u0118\u0119\u0005"+
		"7\u0000\u0000\u0119\'\u0001\u0000\u0000\u0000\u011a\u011b\u0005\u0014"+
		"\u0000\u0000\u011b\u011c\u00054\u0000\u0000\u011c\u011d\u00034\u001a\u0000"+
		"\u011d\u011e\u00055\u0000\u0000\u011e\u011f\u0003\u0012\t\u0000\u011f"+
		")\u0001\u0000\u0000\u0000\u0120\u0121\u0005\u0015\u0000\u0000\u0121\u0122"+
		"\u0003\u0012\t\u0000\u0122\u0123\u0005\u0014\u0000\u0000\u0123\u0124\u0005"+
		"4\u0000\u0000\u0124\u0125\u00034\u001a\u0000\u0125\u0126\u00055\u0000"+
		"\u0000\u0126+\u0001\u0000\u0000\u0000\u0127\u0128\u0005\u0013\u0000\u0000"+
		"\u0128\u012b\u00054\u0000\u0000\u0129\u012c\u0003\u0018\f\u0000\u012a"+
		"\u012c\u0003\u001e\u000f\u0000\u012b\u0129\u0001\u0000\u0000\u0000\u012b"+
		"\u012a\u0001\u0000\u0000\u0000\u012b\u012c\u0001\u0000\u0000\u0000\u012c"+
		"\u012d\u0001\u0000\u0000\u0000\u012d\u012f\u00051\u0000\u0000\u012e\u0130"+
		"\u00034\u001a\u0000\u012f\u012e\u0001\u0000\u0000\u0000\u012f\u0130\u0001"+
		"\u0000\u0000\u0000\u0130\u0131\u0001\u0000\u0000\u0000\u0131\u0134\u0005"+
		"1\u0000\u0000\u0132\u0135\u0003\u001e\u000f\u0000\u0133\u0135\u00034\u001a"+
		"\u0000\u0134\u0132\u0001\u0000\u0000\u0000\u0134\u0133\u0001\u0000\u0000"+
		"\u0000\u0134\u0135\u0001\u0000\u0000\u0000\u0135\u0136\u0001\u0000\u0000"+
		"\u0000\u0136\u0137\u00055\u0000\u0000\u0137\u0138\u0003\u0012\t\u0000"+
		"\u0138-\u0001\u0000\u0000\u0000\u0139\u0140\u0005\u0010\u0000\u0000\u013a"+
		"\u0140\u0005\u0011\u0000\u0000\u013b\u013d\u0005\u0012\u0000\u0000\u013c"+
		"\u013e\u00034\u001a\u0000\u013d\u013c\u0001\u0000\u0000\u0000\u013d\u013e"+
		"\u0001\u0000\u0000\u0000\u013e\u0140\u0001\u0000\u0000\u0000\u013f\u0139"+
		"\u0001\u0000\u0000\u0000\u013f\u013a\u0001\u0000\u0000\u0000\u013f\u013b"+
		"\u0001\u0000\u0000\u0000\u0140/\u0001\u0000\u0000\u0000\u0141\u0142\u0006"+
		"\u0018\uffff\uffff\u0000\u0142\u0143\u0005>\u0000\u0000\u0143\u014e\u0001"+
		"\u0000\u0000\u0000\u0144\u0145\n\u0002\u0000\u0000\u0145\u0146\u00053"+
		"\u0000\u0000\u0146\u014d\u0005>\u0000\u0000\u0147\u0148\n\u0001\u0000"+
		"\u0000\u0148\u0149\u00058\u0000\u0000\u0149\u014a\u00034\u001a\u0000\u014a"+
		"\u014b\u00059\u0000\u0000\u014b\u014d\u0001\u0000\u0000\u0000\u014c\u0144"+
		"\u0001\u0000\u0000\u0000\u014c\u0147\u0001\u0000\u0000\u0000\u014d\u0150"+
		"\u0001\u0000\u0000\u0000\u014e\u014c\u0001\u0000\u0000\u0000\u014e\u014f"+
		"\u0001\u0000\u0000\u0000\u014f1\u0001\u0000\u0000\u0000\u0150\u014e\u0001"+
		"\u0000\u0000\u0000\u0151\u0152\u0007\u0003\u0000\u0000\u01523\u0001\u0000"+
		"\u0000\u0000\u0153\u0154\u0006\u001a\uffff\uffff\u0000\u0154\u0155\u0005"+
		"4\u0000\u0000\u0155\u0156\u00034\u001a\u0000\u0156\u0157\u00055\u0000"+
		"\u0000\u0157\u015c\u0001\u0000\u0000\u0000\u0158\u0159\u0007\u0004\u0000"+
		"\u0000\u0159\u015c\u00034\u001a\t\u015a\u015c\u00036\u001b\u0000\u015b"+
		"\u0153\u0001\u0000\u0000\u0000\u015b\u0158\u0001\u0000\u0000\u0000\u015b"+
		"\u015a\u0001\u0000\u0000\u0000\u015c\u0177\u0001\u0000\u0000\u0000\u015d"+
		"\u015e\n\b\u0000\u0000\u015e\u015f\u0007\u0005\u0000\u0000\u015f\u0176"+
		"\u00034\u001a\t\u0160\u0161\n\u0007\u0000\u0000\u0161\u0162\u0007\u0006"+
		"\u0000\u0000\u0162\u0176\u00034\u001a\b\u0163\u0164\n\u0006\u0000\u0000"+
		"\u0164\u0165\u0007\u0007\u0000\u0000\u0165\u0176\u00034\u001a\u0007\u0166"+
		"\u0167\n\u0005\u0000\u0000\u0167\u0168\u0007\b\u0000\u0000\u0168\u0176"+
		"\u00034\u001a\u0006\u0169\u016a\n\u0004\u0000\u0000\u016a\u016b\u0005"+
		"(\u0000\u0000\u016b\u0176\u00034\u001a\u0005\u016c\u016d\n\u0003\u0000"+
		"\u0000\u016d\u016e\u0005)\u0000\u0000\u016e\u0176\u00034\u001a\u0004\u016f"+
		"\u0170\n\u0002\u0000\u0000\u0170\u0171\u0005/\u0000\u0000\u0171\u0172"+
		"\u00034\u001a\u0000\u0172\u0173\u00050\u0000\u0000\u0173\u0174\u00034"+
		"\u001a\u0003\u0174\u0176\u0001\u0000\u0000\u0000\u0175\u015d\u0001\u0000"+
		"\u0000\u0000\u0175\u0160\u0001\u0000\u0000\u0000\u0175\u0163\u0001\u0000"+
		"\u0000\u0000\u0175\u0166\u0001\u0000\u0000\u0000\u0175\u0169\u0001\u0000"+
		"\u0000\u0000\u0175\u016c\u0001\u0000\u0000\u0000\u0175\u016f\u0001\u0000"+
		"\u0000\u0000\u0176\u0179\u0001\u0000\u0000\u0000\u0177\u0175\u0001\u0000"+
		"\u0000\u0000\u0177\u0178\u0001\u0000\u0000\u0000\u01785\u0001\u0000\u0000"+
		"\u0000\u0179\u0177\u0001\u0000\u0000\u0000\u017a\u0195\u00038\u001c\u0000"+
		"\u017b\u017c\u0005\t\u0000\u0000\u017c\u017d\u0005>\u0000\u0000\u017d"+
		"\u017f\u00054\u0000\u0000\u017e\u0180\u0003:\u001d\u0000\u017f\u017e\u0001"+
		"\u0000\u0000\u0000\u017f\u0180\u0001\u0000\u0000\u0000\u0180\u0181\u0001"+
		"\u0000\u0000\u0000\u0181\u0195\u00055\u0000\u0000\u0182\u0183\u0005\t"+
		"\u0000\u0000\u0183\u0188\u00032\u0019\u0000\u0184\u0185\u00058\u0000\u0000"+
		"\u0185\u0186\u00034\u001a\u0000\u0186\u0187\u00059\u0000\u0000\u0187\u0189"+
		"\u0001\u0000\u0000\u0000\u0188\u0184\u0001\u0000\u0000\u0000\u0189\u018a"+
		"\u0001\u0000\u0000\u0000\u018a\u0188\u0001\u0000\u0000\u0000\u018a\u018b"+
		"\u0001\u0000\u0000\u0000\u018b\u0195\u0001\u0000\u0000\u0000\u018c\u0192"+
		"\u00030\u0018\u0000\u018d\u018f\u00054\u0000\u0000\u018e\u0190\u0003:"+
		"\u001d\u0000\u018f\u018e\u0001\u0000\u0000\u0000\u018f\u0190\u0001\u0000"+
		"\u0000\u0000\u0190\u0191\u0001\u0000\u0000\u0000\u0191\u0193\u00055\u0000"+
		"\u0000\u0192\u018d\u0001\u0000\u0000\u0000\u0192\u0193\u0001\u0000\u0000"+
		"\u0000\u0193\u0195\u0001\u0000\u0000\u0000\u0194\u017a\u0001\u0000\u0000"+
		"\u0000\u0194\u017b\u0001\u0000\u0000\u0000\u0194\u0182\u0001\u0000\u0000"+
		"\u0000\u0194\u018c\u0001\u0000\u0000\u0000\u01957\u0001\u0000\u0000\u0000"+
		"\u0196\u0197\u0007\t\u0000\u0000\u01979\u0001\u0000\u0000\u0000\u0198"+
		"\u019d\u00034\u001a\u0000\u0199\u019a\u00052\u0000\u0000\u019a\u019c\u0003"+
		"4\u001a\u0000\u019b\u0199\u0001\u0000\u0000\u0000\u019c\u019f\u0001\u0000"+
		"\u0000\u0000\u019d\u019b\u0001\u0000\u0000\u0000\u019d\u019e\u0001\u0000"+
		"\u0000\u0000\u019e;\u0001\u0000\u0000\u0000\u019f\u019d\u0001\u0000\u0000"+
		"\u0000-@KQX_afjouz\u0084\u008f\u0094\u009b\u00a0\u00bf\u00c5\u00cc\u00d2"+
		"\u00d4\u00d9\u00de\u00e2\u00ee\u00f7\u0102\u0110\u0116\u012b\u012f\u0134"+
		"\u013d\u013f\u014c\u014e\u015b\u0175\u0177\u017f\u018a\u018f\u0192\u0194"+
		"\u019d";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}