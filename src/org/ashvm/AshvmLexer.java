// $ANTLR 3.3 Nov 30, 2010 12:45:30 D:\\Programmieren\\projects\\ashvm\\Ashvm.g 2011-07-12 17:40:33

package org.ashvm;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class AshvmLexer extends Lexer
{
	public static final int EOF = -1;
	public static final int COLON = 4;
	public static final int NOP = 5;
	public static final int PINT = 6;
	public static final int PCHR = 7;
	public static final int PUSH = 8;
	public static final int ADD = 9;
	public static final int MUL = 10;
	public static final int SUB = 11;
	public static final int DIV = 12;
	public static final int CMP = 13;
	public static final int JMP = 14;
	public static final int JZ = 15;
	public static final int CALL = 16;
	public static final int RET = 17;
	public static final int PEEK = 18;
	public static final int POKE = 19;
	public static final int PICK = 20;
	public static final int ROLL = 21;
	public static final int DROP = 22;
	public static final int END = 23;
	public static final int DUP = 24;
	public static final int SWAP = 25;
	public static final int INC = 26;
	public static final int DEC = 27;
	public static final int STR = 28;
	public static final int INT = 29;
	public static final int IDENTIFIER = 30;
	public static final int STRING = 31;
	public static final int INTEGER = 32;
	public static final int NEWLINE = 33;
	public static final int COMMENT = 34;
	public static final int ESC = 35;
	public static final int WS = 36;

	// delegates
	// delegators

	public AshvmLexer()
	{
		;
	}

	public AshvmLexer(final CharStream input)
	{
		this(input, new RecognizerSharedState());
	}

	public AshvmLexer(final CharStream input, final RecognizerSharedState state)
	{
		super(input, state);

	}

	@Override
	public String getGrammarFileName()
	{
		return "D:\\Programmieren\\projects\\ashvm\\Ashvm.g";
	}

	// $ANTLR start "NOP"
	public final void mNOP() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.NOP;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:693:5: ( 'nop' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:693:7: 'nop'
			{
				this.match("nop");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "NOP"

	// $ANTLR start "PINT"
	public final void mPINT() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.PINT;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:696:6: ( 'pint' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:696:8: 'pint'
			{
				this.match("pint");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "PINT"

	// $ANTLR start "PCHR"
	public final void mPCHR() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.PCHR;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:699:6: ( 'pchr' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:699:8: 'pchr'
			{
				this.match("pchr");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "PCHR"

	// $ANTLR start "PUSH"
	public final void mPUSH() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.PUSH;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:702:6: ( 'push' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:702:8: 'push'
			{
				this.match("push");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "PUSH"

	// $ANTLR start "ADD"
	public final void mADD() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.ADD;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:705:5: ( 'add' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:705:7: 'add'
			{
				this.match("add");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "ADD"

	// $ANTLR start "MUL"
	public final void mMUL() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.MUL;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:708:5: ( 'mul' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:708:7: 'mul'
			{
				this.match("mul");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "MUL"

	// $ANTLR start "SUB"
	public final void mSUB() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.SUB;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:711:5: ( 'sub' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:711:7: 'sub'
			{
				this.match("sub");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "SUB"

	// $ANTLR start "DIV"
	public final void mDIV() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.DIV;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:714:5: ( 'div' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:714:7: 'div'
			{
				this.match("div");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "DIV"

	// $ANTLR start "CMP"
	public final void mCMP() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.CMP;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:717:5: ( 'cmp' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:717:7: 'cmp'
			{
				this.match("cmp");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "CMP"

	// $ANTLR start "JMP"
	public final void mJMP() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.JMP;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:720:5: ( 'jmp' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:720:7: 'jmp'
			{
				this.match("jmp");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "JMP"

	// $ANTLR start "JZ"
	public final void mJZ() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.JZ;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:723:4: ( 'jz' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:723:6: 'jz'
			{
				this.match("jz");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "JZ"

	// $ANTLR start "CALL"
	public final void mCALL() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.CALL;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:726:6: ( 'call' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:726:8: 'call'
			{
				this.match("call");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "CALL"

	// $ANTLR start "RET"
	public final void mRET() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.RET;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:729:5: ( 'ret' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:729:7: 'ret'
			{
				this.match("ret");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "RET"

	// $ANTLR start "PEEK"
	public final void mPEEK() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.PEEK;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:732:6: ( 'peek' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:732:8: 'peek'
			{
				this.match("peek");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "PEEK"

	// $ANTLR start "POKE"
	public final void mPOKE() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.POKE;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:735:6: ( 'poke' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:735:8: 'poke'
			{
				this.match("poke");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "POKE"

	// $ANTLR start "PICK"
	public final void mPICK() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.PICK;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:738:6: ( 'pick' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:738:8: 'pick'
			{
				this.match("pick");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "PICK"

	// $ANTLR start "ROLL"
	public final void mROLL() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.ROLL;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:741:6: ( 'roll' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:741:8: 'roll'
			{
				this.match("roll");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "ROLL"

	// $ANTLR start "DROP"
	public final void mDROP() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.DROP;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:744:6: ( 'drop' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:744:8: 'drop'
			{
				this.match("drop");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "DROP"

	// $ANTLR start "END"
	public final void mEND() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.END;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:747:5: ( 'end' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:747:7: 'end'
			{
				this.match("end");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "END"

	// $ANTLR start "DUP"
	public final void mDUP() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.DUP;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:750:5: ( 'dup' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:750:7: 'dup'
			{
				this.match("dup");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "DUP"

	// $ANTLR start "SWAP"
	public final void mSWAP() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.SWAP;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:753:6: ( 'swap' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:753:8: 'swap'
			{
				this.match("swap");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "SWAP"

	// $ANTLR start "INC"
	public final void mINC() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.INC;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:756:5: ( 'inc' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:756:7: 'inc'
			{
				this.match("inc");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "INC"

	// $ANTLR start "DEC"
	public final void mDEC() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.DEC;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:759:5: ( 'dec' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:759:7: 'dec'
			{
				this.match("dec");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "DEC"

	// $ANTLR start "STR"
	public final void mSTR() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.STR;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:762:5: ( 'str' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:762:7: 'str'
			{
				this.match("str");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "STR"

	// $ANTLR start "INT"
	public final void mINT() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.INT;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:765:5: ( 'int' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:765:7: 'int'
			{
				this.match("int");

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "INT"

	// $ANTLR start "COLON"
	public final void mCOLON() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.COLON;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:768:7: ( ':' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:768:9: ':'
			{
				this.match(':');

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "COLON"

	// $ANTLR start "INTEGER"
	public final void mINTEGER() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.INTEGER;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:771:9: ( ( '-' )? (
			// '0' .. '9' )+ )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:771:11: ( '-' )? (
			// '0' .. '9' )+
			{
				// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:771:11: ( '-' )?
				int alt1 = 2;
				final int LA1_0 = this.input.LA(1);

				if ((LA1_0 == '-'))
				{
					alt1 = 1;
				}
				switch (alt1)
				{
					case 1:
					// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:771:12: '-'
					{
						this.match('-');

					}
						break;

				}

				// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:771:18: ( '0' ..
				// '9' )+
				int cnt2 = 0;
				loop2:
				do
				{
					int alt2 = 2;
					final int LA2_0 = this.input.LA(1);

					if (((LA2_0 >= '0' && LA2_0 <= '9')))
					{
						alt2 = 1;
					}

					switch (alt2)
					{
						case 1:
						// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:771:19:
						// '0' .. '9'
						{
							this.matchRange('0', '9');

						}
							break;

						default:
							if (cnt2 >= 1)
							{
								break loop2;
							}
							final EarlyExitException eee = new EarlyExitException(2, this.input);
							throw eee;
					}
					cnt2++;
				}
				while (true);

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "INTEGER"

	// $ANTLR start "STRING"
	public final void mSTRING() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.STRING;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:774:8: ( '\"' ( ESC |
			// ~ ( '\\\\' | '\\n' | '\"' ) )* '\"' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:774:10: '\"' ( ESC |
			// ~ ( '\\\\' | '\\n' | '\"' ) )* '\"'
			{
				this.match('\"');
				// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:774:14: ( ESC | ~
				// ( '\\\\' | '\\n' | '\"' ) )*
				loop3:
				do
				{
					int alt3 = 3;
					final int LA3_0 = this.input.LA(1);

					if ((LA3_0 == '\\'))
					{
						alt3 = 1;
					}
					else if (((LA3_0 >= '\u0000' && LA3_0 <= '\t')
							|| (LA3_0 >= '\u000B' && LA3_0 <= '!')
							|| (LA3_0 >= '#' && LA3_0 <= '[') || (LA3_0 >= ']' && LA3_0 <= '\uFFFF')))
					{
						alt3 = 2;
					}

					switch (alt3)
					{
						case 1:
						// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:774:15:
						// ESC
						{
							this.mESC();

						}
							break;
						case 2:
						// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:774:19: ~
						// ( '\\\\' | '\\n' | '\"' )
						{
							if ((this.input.LA(1) >= '\u0000' && this.input.LA(1) <= '\t')
									|| (this.input.LA(1) >= '\u000B' && this.input.LA(1) <= '!')
									|| (this.input.LA(1) >= '#' && this.input.LA(1) <= '[')
									|| (this.input.LA(1) >= ']' && this.input.LA(1) <= '\uFFFF'))
							{
								this.input.consume();

							}
							else
							{
								final MismatchedSetException mse = new MismatchedSetException(null,
										this.input);
								this.recover(mse);
								throw mse;
							}

						}
							break;

						default:
							break loop3;
					}
				}
				while (true);

				this.match('\"');

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "STRING"

	// $ANTLR start "ESC"
	public final void mESC() throws RecognitionException
	{
		try
		{
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:779:2: ( '\\\\' . )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:779:4: '\\\\' .
			{
				this.match('\\');
				this.matchAny();

			}

		}
		finally
		{
		}
	}

	// $ANTLR end "ESC"

	// $ANTLR start "NEWLINE"
	public final void mNEWLINE() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.NEWLINE;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:783:5: ( ( ( '\\r' )?
			// '\\n' ) )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:783:9: ( ( '\\r' )?
			// '\\n' )
			{
				// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:783:9: ( ( '\\r'
				// )? '\\n' )
				// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:783:10: ( '\\r'
				// )? '\\n'
				{
					// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:783:10: (
					// '\\r' )?
					int alt4 = 2;
					final int LA4_0 = this.input.LA(1);

					if ((LA4_0 == '\r'))
					{
						alt4 = 1;
					}
					switch (alt4)
					{
						case 1:
						// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:783:11:
						// '\\r'
						{
							this.match('\r');

						}
							break;

					}

					this.match('\n');

				}

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "NEWLINE"

	// $ANTLR start "COMMENT"
	public final void mCOMMENT() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.COMMENT;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:787:5: ( ';' ( . )*
			// '\\n' )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:787:9: ';' ( . )*
			// '\\n'
			{
				this.match(';');
				// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:787:13: ( . )*
				loop5:
				do
				{
					int alt5 = 2;
					final int LA5_0 = this.input.LA(1);

					if ((LA5_0 == '\n'))
					{
						alt5 = 2;
					}
					else if (((LA5_0 >= '\u0000' && LA5_0 <= '\t') || (LA5_0 >= '\u000B' && LA5_0 <= '\uFFFF')))
					{
						alt5 = 1;
					}

					switch (alt5)
					{
						case 1:
						// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:787:13: .
						{
							this.matchAny();

						}
							break;

						default:
							break loop5;
					}
				}
				while (true);

				this.match('\n');

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "COMMENT"

	// $ANTLR start "IDENTIFIER"
	public final void mIDENTIFIER() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.IDENTIFIER;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:790:12: ( ( '.' )? (
			// 'a' .. 'z' | 'A' .. 'Z' )+ )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:790:14: ( '.' )? (
			// 'a' .. 'z' | 'A' .. 'Z' )+
			{
				// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:790:14: ( '.' )?
				int alt6 = 2;
				final int LA6_0 = this.input.LA(1);

				if ((LA6_0 == '.'))
				{
					alt6 = 1;
				}
				switch (alt6)
				{
					case 1:
					// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:790:15: '.'
					{
						this.match('.');

					}
						break;

				}

				// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:790:21: ( 'a' ..
				// 'z' | 'A' .. 'Z' )+
				int cnt7 = 0;
				loop7:
				do
				{
					int alt7 = 2;
					final int LA7_0 = this.input.LA(1);

					if (((LA7_0 >= 'A' && LA7_0 <= 'Z') || (LA7_0 >= 'a' && LA7_0 <= 'z')))
					{
						alt7 = 1;
					}

					switch (alt7)
					{
						case 1:
						// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:
						{
							if ((this.input.LA(1) >= 'A' && this.input.LA(1) <= 'Z')
									|| (this.input.LA(1) >= 'a' && this.input.LA(1) <= 'z'))
							{
								this.input.consume();

							}
							else
							{
								final MismatchedSetException mse = new MismatchedSetException(null,
										this.input);
								this.recover(mse);
								throw mse;
							}

						}
							break;

						default:
							if (cnt7 >= 1)
							{
								break loop7;
							}
							final EarlyExitException eee = new EarlyExitException(7, this.input);
							throw eee;
					}
					cnt7++;
				}
				while (true);

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "IDENTIFIER"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException
	{
		try
		{
			final int _type = AshvmLexer.WS;
			final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:793:4: ( ( ' ' |
			// '\\t' )+ )
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:793:6: ( ' ' | '\\t'
			// )+
			{
				// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:793:6: ( ' ' |
				// '\\t' )+
				int cnt8 = 0;
				loop8:
				do
				{
					int alt8 = 2;
					final int LA8_0 = this.input.LA(1);

					if ((LA8_0 == '\t' || LA8_0 == ' '))
					{
						alt8 = 1;
					}

					switch (alt8)
					{
						case 1:
						// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:
						{
							if (this.input.LA(1) == '\t' || this.input.LA(1) == ' ')
							{
								this.input.consume();

							}
							else
							{
								final MismatchedSetException mse = new MismatchedSetException(null,
										this.input);
								this.recover(mse);
								throw mse;
							}

						}
							break;

						default:
							if (cnt8 >= 1)
							{
								break loop8;
							}
							final EarlyExitException eee = new EarlyExitException(8, this.input);
							throw eee;
					}
					cnt8++;
				}
				while (true);

				this.skip();

			}

			this.state.type = _type;
			this.state.channel = _channel;
		}
		finally
		{
		}
	}

	// $ANTLR end "WS"

	@Override
	public void mTokens() throws RecognitionException
	{
		// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:8: ( NOP | PINT | PCHR
		// | PUSH | ADD | MUL | SUB | DIV | CMP | JMP | JZ | CALL | RET | PEEK |
		// POKE | PICK | ROLL | DROP | END | DUP | SWAP | INC | DEC | STR | INT
		// | COLON | INTEGER | STRING | NEWLINE | COMMENT | IDENTIFIER | WS )
		int alt9 = 32;
		alt9 = this.dfa9.predict(this.input);
		switch (alt9)
		{
			case 1:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:10: NOP
			{
				this.mNOP();

			}
				break;
			case 2:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:14: PINT
			{
				this.mPINT();

			}
				break;
			case 3:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:19: PCHR
			{
				this.mPCHR();

			}
				break;
			case 4:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:24: PUSH
			{
				this.mPUSH();

			}
				break;
			case 5:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:29: ADD
			{
				this.mADD();

			}
				break;
			case 6:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:33: MUL
			{
				this.mMUL();

			}
				break;
			case 7:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:37: SUB
			{
				this.mSUB();

			}
				break;
			case 8:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:41: DIV
			{
				this.mDIV();

			}
				break;
			case 9:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:45: CMP
			{
				this.mCMP();

			}
				break;
			case 10:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:49: JMP
			{
				this.mJMP();

			}
				break;
			case 11:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:53: JZ
			{
				this.mJZ();

			}
				break;
			case 12:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:56: CALL
			{
				this.mCALL();

			}
				break;
			case 13:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:61: RET
			{
				this.mRET();

			}
				break;
			case 14:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:65: PEEK
			{
				this.mPEEK();

			}
				break;
			case 15:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:70: POKE
			{
				this.mPOKE();

			}
				break;
			case 16:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:75: PICK
			{
				this.mPICK();

			}
				break;
			case 17:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:80: ROLL
			{
				this.mROLL();

			}
				break;
			case 18:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:85: DROP
			{
				this.mDROP();

			}
				break;
			case 19:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:90: END
			{
				this.mEND();

			}
				break;
			case 20:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:94: DUP
			{
				this.mDUP();

			}
				break;
			case 21:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:98: SWAP
			{
				this.mSWAP();

			}
				break;
			case 22:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:103: INC
			{
				this.mINC();

			}
				break;
			case 23:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:107: DEC
			{
				this.mDEC();

			}
				break;
			case 24:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:111: STR
			{
				this.mSTR();

			}
				break;
			case 25:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:115: INT
			{
				this.mINT();

			}
				break;
			case 26:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:119: COLON
			{
				this.mCOLON();

			}
				break;
			case 27:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:125: INTEGER
			{
				this.mINTEGER();

			}
				break;
			case 28:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:133: STRING
			{
				this.mSTRING();

			}
				break;
			case 29:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:140: NEWLINE
			{
				this.mNEWLINE();

			}
				break;
			case 30:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:148: COMMENT
			{
				this.mCOMMENT();

			}
				break;
			case 31:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:156: IDENTIFIER
			{
				this.mIDENTIFIER();

			}
				break;
			case 32:
			// D:\\Programmieren\\projects\\ashvm\\Ashvm.g:1:167: WS
			{
				this.mWS();

			}
				break;

		}

	}

	protected DFA9 dfa9 = new DFA9(this);
	static final String DFA9_eotS = "\1\uffff\13\21\7\uffff\22\21\1\75\4\21\1\103\6\21\1\112\1\113\1"
			+ "\114\1\21\1\116\1\117\1\21\1\121\1\122\1\123\1\21\1\125\1\uffff"
			+ "\1\126\1\21\1\130\1\131\1\132\1\uffff\1\133\1\134\1\135\1\136\1"
			+ "\137\1\140\3\uffff\1\141\2\uffff\1\142\3\uffff\1\143\2\uffff\1\144" + "\15\uffff";
	static final String DFA9_eofS = "\145\uffff";
	static final String DFA9_minS = "\1\11\1\157\1\143\1\144\1\165\1\164\1\145\1\141\1\155\1\145\2\156"
			+ "\7\uffff\1\160\1\143\1\150\1\163\1\145\1\153\1\144\1\154\1\142\1"
			+ "\141\1\162\1\166\1\157\1\160\1\143\1\160\1\154\1\160\1\101\1\164"
			+ "\1\154\1\144\1\143\1\101\1\164\1\153\1\162\1\150\1\153\1\145\3\101"
			+ "\1\160\2\101\1\160\3\101\1\154\1\101\1\uffff\1\101\1\154\3\101\1"
			+ "\uffff\6\101\3\uffff\1\101\2\uffff\1\101\3\uffff\1\101\2\uffff\1" + "\101\15\uffff";
	static final String DFA9_maxS = "\1\172\1\157\1\165\1\144\1\165\1\167\1\165\1\155\1\172\1\157\2"
			+ "\156\7\uffff\1\160\1\156\1\150\1\163\1\145\1\153\1\144\1\154\1\142"
			+ "\1\141\1\162\1\166\1\157\1\160\1\143\1\160\1\154\1\160\1\172\1\164"
			+ "\1\154\1\144\1\164\1\172\1\164\1\153\1\162\1\150\1\153\1\145\3\172"
			+ "\1\160\2\172\1\160\3\172\1\154\1\172\1\uffff\1\172\1\154\3\172\1"
			+ "\uffff\6\172\3\uffff\1\172\2\uffff\1\172\3\uffff\1\172\2\uffff\1" + "\172\15\uffff";
	static final String DFA9_acceptS = "\14\uffff\1\32\1\33\1\34\1\35\1\36\1\37\1\40\52\uffff\1\13\5\uffff"
			+ "\1\1\6\uffff\1\5\1\6\1\7\1\uffff\1\30\1\10\1\uffff\1\24\1\27\1\11"
			+ "\1\uffff\1\12\1\15\1\uffff\1\23\1\26\1\31\1\2\1\20\1\3\1\4\1\16"
			+ "\1\17\1\25\1\22\1\14\1\21";
	static final String DFA9_specialS = "\145\uffff}>";
	static final String[] DFA9_transitionS =
	{
			"\1\22\1\17\2\uffff\1\17\22\uffff\1\22\1\uffff\1\16\12\uffff"
					+ "\1\15\1\21\1\uffff\12\15\1\14\1\20\5\uffff\32\21\6\uffff\1\3"
					+ "\1\21\1\7\1\6\1\12\3\21\1\13\1\10\2\21\1\4\1\1\1\21\1\2\1\21"
					+ "\1\11\1\5\7\21", "\1\23",
			"\1\25\1\uffff\1\27\3\uffff\1\24\5\uffff\1\30\5\uffff\1\26", "\1\31", "\1\32",
			"\1\35\1\33\1\uffff\1\34", "\1\41\3\uffff\1\36\10\uffff\1\37\2\uffff\1\40",
			"\1\43\13\uffff\1\42", "\1\44\14\uffff\1\45", "\1\46\11\uffff\1\47", "\1\50", "\1\51",
			"", "", "", "", "", "", "", "\1\52", "\1\54\12\uffff\1\53", "\1\55", "\1\56", "\1\57",
			"\1\60", "\1\61", "\1\62", "\1\63", "\1\64", "\1\65", "\1\66", "\1\67", "\1\70",
			"\1\71", "\1\72", "\1\73", "\1\74", "\32\21\6\uffff\32\21", "\1\76", "\1\77", "\1\100",
			"\1\101\20\uffff\1\102", "\32\21\6\uffff\32\21", "\1\104", "\1\105", "\1\106",
			"\1\107", "\1\110", "\1\111", "\32\21\6\uffff\32\21", "\32\21\6\uffff\32\21",
			"\32\21\6\uffff\32\21", "\1\115", "\32\21\6\uffff\32\21", "\32\21\6\uffff\32\21",
			"\1\120", "\32\21\6\uffff\32\21", "\32\21\6\uffff\32\21", "\32\21\6\uffff\32\21",
			"\1\124", "\32\21\6\uffff\32\21", "", "\32\21\6\uffff\32\21", "\1\127",
			"\32\21\6\uffff\32\21", "\32\21\6\uffff\32\21", "\32\21\6\uffff\32\21", "",
			"\32\21\6\uffff\32\21", "\32\21\6\uffff\32\21", "\32\21\6\uffff\32\21",
			"\32\21\6\uffff\32\21", "\32\21\6\uffff\32\21", "\32\21\6\uffff\32\21", "", "", "",
			"\32\21\6\uffff\32\21", "", "", "\32\21\6\uffff\32\21", "", "", "",
			"\32\21\6\uffff\32\21", "", "", "\32\21\6\uffff\32\21", "", "", "", "", "", "", "", "",
			"", "", "", "", ""
	};

	static final short[] DFA9_eot = DFA.unpackEncodedString(AshvmLexer.DFA9_eotS);
	static final short[] DFA9_eof = DFA.unpackEncodedString(AshvmLexer.DFA9_eofS);
	static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(AshvmLexer.DFA9_minS);
	static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(AshvmLexer.DFA9_maxS);
	static final short[] DFA9_accept = DFA.unpackEncodedString(AshvmLexer.DFA9_acceptS);
	static final short[] DFA9_special = DFA.unpackEncodedString(AshvmLexer.DFA9_specialS);
	static final short[][] DFA9_transition;

	static
	{
		final int numStates = AshvmLexer.DFA9_transitionS.length;
		DFA9_transition = new short[numStates][];
		for (int i = 0; i < numStates; i++)
		{
			AshvmLexer.DFA9_transition[i] = DFA.unpackEncodedString(AshvmLexer.DFA9_transitionS[i]);
		}
	}

	class DFA9 extends DFA
	{

		public DFA9(final BaseRecognizer recognizer)
		{
			this.recognizer = recognizer;
			this.decisionNumber = 9;
			this.eot = AshvmLexer.DFA9_eot;
			this.eof = AshvmLexer.DFA9_eof;
			this.min = AshvmLexer.DFA9_min;
			this.max = AshvmLexer.DFA9_max;
			this.accept = AshvmLexer.DFA9_accept;
			this.special = AshvmLexer.DFA9_special;
			this.transition = AshvmLexer.DFA9_transition;
		}

		@Override
		public String getDescription()
		{
			return "1:1: Tokens : ( NOP | PINT | PCHR | PUSH | ADD | MUL | SUB | DIV | CMP | JMP | JZ | CALL | RET | PEEK | POKE | PICK | ROLL | DROP | END | DUP | SWAP | INC | DEC | STR | INT | COLON | INTEGER | STRING | NEWLINE | COMMENT | IDENTIFIER | WS );";
		}
	}

}