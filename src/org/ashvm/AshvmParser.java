// $ANTLR 3.3 Nov 30, 2010 12:45:30 D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g 2011-07-01 01:24:32

package org.ashvm;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.DFA;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

public class AshvmParser extends Parser
{
	public static final String[] tokenNames = new String[]
	{
			"<invalid>", "<EOR>", "<DOWN>", "<UP>", "COLON", "NOP", "PINT", "PCHR", "PUSH", "ADD",
			"MUL", "SUB", "DIV", "CMP", "JMP", "JZ", "CALL", "RET", "PEEK", "POKE", "PICK", "ROLL",
			"DROP", "END", "DUP", "INC", "DEC", "STR", "INT", "IDENTIFIER", "STRING", "INTEGER",
			"NEWLINE", "COMMENT", "ESC", "WS"
	};
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
	public static final int INC = 25;
	public static final int DEC = 26;
	public static final int STR = 27;
	public static final int INT = 28;
	public static final int IDENTIFIER = 29;
	public static final int STRING = 30;
	public static final int INTEGER = 31;
	public static final int NEWLINE = 32;
	public static final int COMMENT = 33;
	public static final int ESC = 34;
	public static final int WS = 35;

	// delegates
	// delegators

	public AshvmParser(final TokenStream input)
	{
		this(input, new RecognizerSharedState());
	}

	public AshvmParser(final TokenStream input, final RecognizerSharedState state)
	{
		super(input, state);

	}

	@Override
	public String[] getTokenNames()
	{
		return AshvmParser.tokenNames;
	}

	@Override
	public String getGrammarFileName()
	{
		return "D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g";
	}

	PrintStream codeOut = System.out;
	PrintStream memOut = System.err;

	List<Character> code = new ArrayList<Character>();
	List<Integer> memory = new ArrayList<Integer>();

	HashMap<String, Integer> labels = new HashMap<String, Integer>();
	HashMap<String, Integer> variables = new HashMap<String, Integer>();

	String producePositiveInteger(final int integer)
	{
		if (integer >= 0 && integer <= 9)
		{
			return String.valueOf(integer);
		}

		if (integer >= 10 && integer <= 18)
		{
			return "9" + (integer - 9) + "+";
		}

		final StringBuilder sb = new StringBuilder();
		final String intS = String.valueOf(integer);
		int pos = intS.length() - 1;

		sb.append(intS.charAt(pos--));
		do
		{

			sb.append("91+");
			for (int i = intS.length() - pos - 1; i > 1; i--)
			{
				sb.append("91+*");
			}

			sb.append(intS.charAt(pos));
			sb.append("*+");
		}
		while (pos-- > 0);

		return sb.toString();
	}

	String produceNegativeInteger(final int integer)
	{
		if (integer >= -9 && integer <= 0)
		{
			return "0" + Math.abs(integer) + "-";
		}

		if (integer >= -18 && integer <= -10)
		{
			return "09-" + Math.abs(integer + 9) + "-";
		}

		final StringBuilder sb = new StringBuilder();
		final String intS = String.valueOf(integer);
		int pos = intS.length() - 1;

		sb.append('0');
		if (intS.charAt(pos) != '0')
		{
			sb.append(intS.charAt(pos));
			sb.append('-');
		}
		pos--;

		do
		{
			sb.append("91+");
			for (int i = intS.length() - pos - 1; i > 1; i--)
			{
				sb.append("91+*");
			}
			sb.append(intS.charAt(pos));
			sb.append("*-");
		}
		while (pos-- > 1);

		return sb.toString();
	}

	String produceInteger(final int integer)
	{
		return (integer >= 0 ? this.producePositiveInteger(integer) : this
				.produceNegativeInteger(integer));
	}

	int calcAddressDiff(final int current, final int aim)
	{
		if (current > aim)
		{
			return aim - current;
		}
		else if (current < aim)
		{
			return Math.abs(aim - current);
		}
		else
		{
			return 0;
		}
	}

	void appendToCode(final char c)
	{
		this.code.add(c);
	}

	void appendToCode(final String s)
	{
		for (final char c : s.toCharArray())
		{
			this.code.add(c);
		}
	}

	void writeToCode(final char c, final int pos)
	{
		this.code.add(pos, c);
	}

	void writeToCode(final String s, int pos)
	{
		for (final char c : s.toCharArray())
		{
			this.code.add(pos++, c);
		}
	}

	void writeToMemory(final int val)
	{
		this.memory.add(val);
	}

	void writeToMemory(final String s)
	{
		for (final char c : s.toCharArray())
		{
			this.memory.add((int) c);
		}
	}

	int getCurrentCodePosition()
	{
		return this.code.size();
	}

	String generateReferenceCode(final int codePos, final int pointer, final char op)
	{
		String oldCode = " ";
		String newCode = " ";
		int diff;
		String addCode;

		for (;;)
		{
			diff = this.calcAddressDiff(codePos + oldCode.length(), pointer);
			addCode = this.produceInteger(diff);
			newCode = addCode + op;
			if (oldCode.length() == newCode.length())
			{
				break;
			}

			oldCode = newCode;
		}
		return newCode;
	}

	void addReference(final int codePos, final int pointer, final char op)
	{
		final String refCode = this.generateReferenceCode(codePos, pointer, op);
		this.writeToCode(refCode, codePos);
	}

	void printMemory()
	{
		if (this.memory.size() == 0)
		{
			return;
		}

		this.memOut.print(this.memory.get(0));
		for (int i = 1; i < this.memory.size(); i++)
		{
			this.memOut.print(',');
			this.memOut.print(this.memory.get(i));
		}
	}

	void printCode()
	{
		for (final char c : this.code)
		{
			this.codeOut.print(c);
		}
	}

	String stripApostrophe(final String str)
	{
		return str.substring(1, str.length() - 1);
	}

	// $ANTLR start "prog"
	// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:211:1: prog : (
	// instruction )* ;
	public final void prog() throws RecognitionException
	{
		try
		{
			// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:211:6: ( (
			// instruction )* )
			// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:211:8: (
			// instruction )*
			{
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:211:8: (
				// instruction )*
				loop1:
				do
				{
					int alt1 = 2;
					final int LA1_0 = this.input.LA(1);

					if (((LA1_0 >= AshvmParser.COLON && LA1_0 <= AshvmParser.COMMENT)))
					{
						alt1 = 1;
					}

					switch (alt1)
					{
						case 1:
						// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:211:8:
						// instruction
						{
							this.pushFollow(AshvmParser.FOLLOW_instruction_in_prog47);
							this.instruction();

							this.state._fsp--;

						}
							break;

						default:
							break loop1;
					}
				}
				while (true);

			}

		}
		catch (final RecognitionException re)
		{
			this.reportError(re);
			this.recover(this.input, re);
		}
		finally
		{
		}
		return;
	}

	// $ANTLR end "prog"

	// $ANTLR start "instruction"
	// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:214:1: instruction :
	// ( identifier COLON | NOP instruction_end | PINT instruction_end | PINT
	// integer instruction_end | PCHR instruction_end | PCHR integer
	// instruction_end | PCHR string instruction_end | PUSH identifier
	// instruction_end | PUSH integer instruction_end | PUSH string
	// instruction_end | ADD instruction_end | ADD integer instruction_end | MUL
	// instruction_end | MUL integer instruction_end | SUB instruction_end | SUB
	// integer instruction_end | DIV instruction_end | DIV integer
	// instruction_end | CMP instruction_end | JMP instruction_end | JMP integer
	// instruction_end | JMP identifier instruction_end | JZ instruction_end |
	// JZ integer instruction_end | JZ identifier instruction_end | CALL
	// instruction_end | CALL integer instruction_end | CALL identifier
	// instruction_end | RET instruction_end | PEEK instruction_end | POKE
	// instruction_end | PICK instruction_end | ROLL instruction_end | DROP
	// instruction_end | END instruction_end | DUP instruction_end | INC
	// instruction_end | DEC instruction_end | STR identifier string
	// instruction_end | INT identifier integer instruction_end |
	// instruction_end );
	public final void instruction() throws RecognitionException
	{
		String identifier1 = null;

		int integer2 = 0;

		int integer3 = 0;

		String string4 = null;

		String identifier5 = null;

		int integer6 = 0;

		String string7 = null;

		int integer8 = 0;

		int integer9 = 0;

		int integer10 = 0;

		int integer11 = 0;

		int integer12 = 0;

		String identifier13 = null;

		int integer14 = 0;

		String identifier15 = null;

		int integer16 = 0;

		String identifier17 = null;

		String string18 = null;

		String identifier19 = null;

		String identifier20 = null;

		int integer21 = 0;

		try
		{
			// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:215:2: (
			// identifier COLON | NOP instruction_end | PINT instruction_end |
			// PINT integer instruction_end | PCHR instruction_end | PCHR
			// integer instruction_end | PCHR string instruction_end | PUSH
			// identifier instruction_end | PUSH integer instruction_end | PUSH
			// string instruction_end | ADD instruction_end | ADD integer
			// instruction_end | MUL instruction_end | MUL integer
			// instruction_end | SUB instruction_end | SUB integer
			// instruction_end | DIV instruction_end | DIV integer
			// instruction_end | CMP instruction_end | JMP instruction_end | JMP
			// integer instruction_end | JMP identifier instruction_end | JZ
			// instruction_end | JZ integer instruction_end | JZ identifier
			// instruction_end | CALL instruction_end | CALL integer
			// instruction_end | CALL identifier instruction_end | RET
			// instruction_end | PEEK instruction_end | POKE instruction_end |
			// PICK instruction_end | ROLL instruction_end | DROP
			// instruction_end | END instruction_end | DUP instruction_end | INC
			// instruction_end | DEC instruction_end | STR identifier string
			// instruction_end | INT identifier integer instruction_end |
			// instruction_end )
			int alt2 = 41;
			alt2 = this.dfa2.predict(this.input);
			switch (alt2)
			{
				case 1:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:215:4:
				// identifier COLON
				{
					this.pushFollow(AshvmParser.FOLLOW_identifier_in_instruction60);
					identifier1 = this.identifier();

					this.state._fsp--;

					this.match(this.input, AshvmParser.COLON,
							AshvmParser.FOLLOW_COLON_in_instruction62);

					if (this.labels.get(identifier1) != null)
					{
						System.err.println("-> " + identifier1 + " redeclared");
					}
					else
					{
						this.labels.put(identifier1, this.getCurrentCodePosition());
					}

				}
					break;
				case 2:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:225:4:
				// NOP instruction_end
				{
					this.match(this.input, AshvmParser.NOP, AshvmParser.FOLLOW_NOP_in_instruction74);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction76);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode(' ');

				}
					break;
				case 3:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:230:4:
				// PINT instruction_end
				{
					this.match(this.input, AshvmParser.PINT,
							AshvmParser.FOLLOW_PINT_in_instruction88);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction90);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode('p');

				}
					break;
				case 4:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:234:4:
				// PINT integer instruction_end
				{
					this.match(this.input, AshvmParser.PINT,
							AshvmParser.FOLLOW_PINT_in_instruction99);
					this.pushFollow(AshvmParser.FOLLOW_integer_in_instruction101);
					integer2 = this.integer();

					this.state._fsp--;

					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction103);
					this.instruction_end();

					this.state._fsp--;

					final String valPush = this.produceInteger(integer2);
					this.appendToCode(valPush);
					this.appendToCode('p');

				}
					break;
				case 5:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:241:4:
				// PCHR instruction_end
				{
					this.match(this.input, AshvmParser.PCHR,
							AshvmParser.FOLLOW_PCHR_in_instruction115);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction117);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode('P');

				}
					break;
				case 6:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:245:4:
				// PCHR integer instruction_end
				{
					this.match(this.input, AshvmParser.PCHR,
							AshvmParser.FOLLOW_PCHR_in_instruction126);
					this.pushFollow(AshvmParser.FOLLOW_integer_in_instruction128);
					integer3 = this.integer();

					this.state._fsp--;

					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction130);
					this.instruction_end();

					this.state._fsp--;

					final String valPush = this.produceInteger(integer3);
					this.appendToCode(valPush);
					this.appendToCode('P');

				}
					break;
				case 7:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:251:4:
				// PCHR string instruction_end
				{
					this.match(this.input, AshvmParser.PCHR,
							AshvmParser.FOLLOW_PCHR_in_instruction139);
					this.pushFollow(AshvmParser.FOLLOW_string_in_instruction141);
					string4 = this.string();

					this.state._fsp--;

					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction143);
					this.instruction_end();

					this.state._fsp--;

					final String str = this.stripApostrophe(string4);
					if (str.length() == 0)
					{
						System.err.println("-> string is smaller than 1");
					}
					else
					{
						final int fAscii = str.codePointAt(0);
						final String valPush = this.produceInteger(fAscii);
						this.appendToCode(valPush);
						this.appendToCode('P');
					}

				}
					break;
				case 8:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:265:4:
				// PUSH identifier instruction_end
				{
					this.match(this.input, AshvmParser.PUSH,
							AshvmParser.FOLLOW_PUSH_in_instruction155);
					this.pushFollow(AshvmParser.FOLLOW_identifier_in_instruction157);
					identifier5 = this.identifier();

					this.state._fsp--;

					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction159);
					this.instruction_end();

					this.state._fsp--;

					final Integer val = this.variables.get(identifier5);
					if (val == null)
					{
						System.err.println("-> variable '" + identifier5 + "' is not declared");
					}
					else
					{
						final String valPush = this.produceInteger(val.intValue());
						this.appendToCode(valPush);
					}

				}
					break;
				case 9:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:276:4:
				// PUSH integer instruction_end
				{
					this.match(this.input, AshvmParser.PUSH,
							AshvmParser.FOLLOW_PUSH_in_instruction168);
					this.pushFollow(AshvmParser.FOLLOW_integer_in_instruction170);
					integer6 = this.integer();

					this.state._fsp--;

					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction172);
					this.instruction_end();

					this.state._fsp--;

					final String valPush = this.produceInteger(integer6);
					this.appendToCode(valPush);

				}
					break;
				case 10:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:281:4:
				// PUSH string instruction_end
				{
					this.match(this.input, AshvmParser.PUSH,
							AshvmParser.FOLLOW_PUSH_in_instruction181);
					this.pushFollow(AshvmParser.FOLLOW_string_in_instruction183);
					string7 = this.string();

					this.state._fsp--;

					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction185);
					this.instruction_end();

					this.state._fsp--;

					final String str = this.stripApostrophe(string7);
					if (str.length() == 0)
					{
						System.err.println("-> string is smaller than 1");
					}
					else
					{
						final int fAscii = str.codePointAt(0);
						final String valPush = this.produceInteger(fAscii);
						this.appendToCode(valPush);
					}

				}
					break;
				case 11:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:294:4:
				// ADD instruction_end
				{
					this.match(this.input, AshvmParser.ADD,
							AshvmParser.FOLLOW_ADD_in_instruction197);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction199);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode('+');

				}
					break;
				case 12:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:298:4:
				// ADD integer instruction_end
				{
					this.match(this.input, AshvmParser.ADD,
							AshvmParser.FOLLOW_ADD_in_instruction208);
					this.pushFollow(AshvmParser.FOLLOW_integer_in_instruction210);
					integer8 = this.integer();

					this.state._fsp--;

					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction212);
					this.instruction_end();

					this.state._fsp--;

					final String valPush = this.produceInteger(integer8);
					this.appendToCode(valPush);
					this.appendToCode('+');

				}
					break;
				case 13:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:305:4:
				// MUL instruction_end
				{
					this.match(this.input, AshvmParser.MUL,
							AshvmParser.FOLLOW_MUL_in_instruction224);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction226);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode('*');

				}
					break;
				case 14:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:309:4:
				// MUL integer instruction_end
				{
					this.match(this.input, AshvmParser.MUL,
							AshvmParser.FOLLOW_MUL_in_instruction235);
					this.pushFollow(AshvmParser.FOLLOW_integer_in_instruction237);
					integer9 = this.integer();

					this.state._fsp--;

					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction239);
					this.instruction_end();

					this.state._fsp--;

					final String valPush = this.produceInteger(integer9);
					this.appendToCode(valPush);
					this.appendToCode('*');

				}
					break;
				case 15:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:316:4:
				// SUB instruction_end
				{
					this.match(this.input, AshvmParser.SUB,
							AshvmParser.FOLLOW_SUB_in_instruction251);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction253);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode('-');

				}
					break;
				case 16:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:320:4:
				// SUB integer instruction_end
				{
					this.match(this.input, AshvmParser.SUB,
							AshvmParser.FOLLOW_SUB_in_instruction262);
					this.pushFollow(AshvmParser.FOLLOW_integer_in_instruction264);
					integer10 = this.integer();

					this.state._fsp--;

					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction266);
					this.instruction_end();

					this.state._fsp--;

					final String valPush = this.produceInteger(integer10);
					this.appendToCode(valPush);
					this.appendToCode('-');

				}
					break;
				case 17:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:327:4:
				// DIV instruction_end
				{
					this.match(this.input, AshvmParser.DIV,
							AshvmParser.FOLLOW_DIV_in_instruction278);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction280);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode('/');

				}
					break;
				case 18:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:331:4:
				// DIV integer instruction_end
				{
					this.match(this.input, AshvmParser.DIV,
							AshvmParser.FOLLOW_DIV_in_instruction289);
					this.pushFollow(AshvmParser.FOLLOW_integer_in_instruction291);
					integer11 = this.integer();

					this.state._fsp--;

					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction293);
					this.instruction_end();

					this.state._fsp--;

					final String valPush = this.produceInteger(integer11);
					this.appendToCode(valPush);
					this.appendToCode('/');

				}
					break;
				case 19:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:338:4:
				// CMP instruction_end
				{
					this.match(this.input, AshvmParser.CMP,
							AshvmParser.FOLLOW_CMP_in_instruction305);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction307);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode(':');

				}
					break;
				case 20:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:343:4:
				// JMP instruction_end
				{
					this.match(this.input, AshvmParser.JMP,
							AshvmParser.FOLLOW_JMP_in_instruction319);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction321);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode('g');

				}
					break;
				case 21:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:347:4:
				// JMP integer instruction_end
				{
					this.match(this.input, AshvmParser.JMP,
							AshvmParser.FOLLOW_JMP_in_instruction330);
					this.pushFollow(AshvmParser.FOLLOW_integer_in_instruction332);
					integer12 = this.integer();

					this.state._fsp--;

					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction334);
					this.instruction_end();

					this.state._fsp--;

					final String valPush = this.produceInteger(integer12);
					this.appendToCode(valPush);
					this.appendToCode('g');

				}
					break;
				case 22:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:353:4:
				// JMP identifier instruction_end
				{
					this.match(this.input, AshvmParser.JMP,
							AshvmParser.FOLLOW_JMP_in_instruction343);
					this.pushFollow(AshvmParser.FOLLOW_identifier_in_instruction345);
					identifier13 = this.identifier();

					this.state._fsp--;

					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction347);
					this.instruction_end();

					this.state._fsp--;

					final Integer val = this.labels.get(identifier13);
					if (val == null)
					{
						System.err.println("-> label '" + identifier13 + "' is not declared");
					}
					else
					{
						this.addReference(this.getCurrentCodePosition(), val.intValue(), 'g');
					}

				}
					break;
				case 23:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:364:4:
				// JZ instruction_end
				{
					this.match(this.input, AshvmParser.JZ, AshvmParser.FOLLOW_JZ_in_instruction359);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction361);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode('?');

				}
					break;
				case 24:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:368:4:
				// JZ integer instruction_end
				{
					this.match(this.input, AshvmParser.JZ, AshvmParser.FOLLOW_JZ_in_instruction370);
					this.pushFollow(AshvmParser.FOLLOW_integer_in_instruction372);
					integer14 = this.integer();

					this.state._fsp--;

					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction374);
					this.instruction_end();

					this.state._fsp--;

					final String valPush = this.produceInteger(integer14);
					this.appendToCode(valPush);
					this.appendToCode('?');

				}
					break;
				case 25:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:374:4:
				// JZ identifier instruction_end
				{
					this.match(this.input, AshvmParser.JZ, AshvmParser.FOLLOW_JZ_in_instruction383);
					this.pushFollow(AshvmParser.FOLLOW_identifier_in_instruction385);
					identifier15 = this.identifier();

					this.state._fsp--;

					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction387);
					this.instruction_end();

					this.state._fsp--;

					final Integer val = this.labels.get(identifier15);
					if (val == null)
					{
						System.err.println("-> label '" + identifier15 + "' is not declared");
					}
					else
					{
						this.addReference(this.getCurrentCodePosition(), val.intValue(), '?');
					}

				}
					break;
				case 26:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:385:4:
				// CALL instruction_end
				{
					this.match(this.input, AshvmParser.CALL,
							AshvmParser.FOLLOW_CALL_in_instruction399);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction401);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode('c');

				}
					break;
				case 27:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:389:4:
				// CALL integer instruction_end
				{
					this.match(this.input, AshvmParser.CALL,
							AshvmParser.FOLLOW_CALL_in_instruction410);
					this.pushFollow(AshvmParser.FOLLOW_integer_in_instruction412);
					integer16 = this.integer();

					this.state._fsp--;

					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction414);
					this.instruction_end();

					this.state._fsp--;

					final String valPush = this.produceInteger(integer16);
					this.appendToCode(valPush);
					this.appendToCode('c');

				}
					break;
				case 28:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:395:4:
				// CALL identifier instruction_end
				{
					this.match(this.input, AshvmParser.CALL,
							AshvmParser.FOLLOW_CALL_in_instruction423);
					this.pushFollow(AshvmParser.FOLLOW_identifier_in_instruction425);
					identifier17 = this.identifier();

					this.state._fsp--;

					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction427);
					this.instruction_end();

					this.state._fsp--;

					final Integer val = this.labels.get(identifier17);
					if (val == null)
					{
						System.err.println("-> label '" + identifier17 + "' is not declared");
					}
					else
					{
						this.addReference(this.getCurrentCodePosition(), val.intValue(), 'c');
					}

				}
					break;
				case 29:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:406:4:
				// RET instruction_end
				{
					this.match(this.input, AshvmParser.RET,
							AshvmParser.FOLLOW_RET_in_instruction439);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction441);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode('$');

				}
					break;
				case 30:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:411:4:
				// PEEK instruction_end
				{
					this.match(this.input, AshvmParser.PEEK,
							AshvmParser.FOLLOW_PEEK_in_instruction453);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction455);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode('<');

				}
					break;
				case 31:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:416:4:
				// POKE instruction_end
				{
					this.match(this.input, AshvmParser.POKE,
							AshvmParser.FOLLOW_POKE_in_instruction467);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction469);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode('>');

				}
					break;
				case 32:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:421:4:
				// PICK instruction_end
				{
					this.match(this.input, AshvmParser.PICK,
							AshvmParser.FOLLOW_PICK_in_instruction481);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction483);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode('^');

				}
					break;
				case 33:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:426:4:
				// ROLL instruction_end
				{
					this.match(this.input, AshvmParser.ROLL,
							AshvmParser.FOLLOW_ROLL_in_instruction495);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction497);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode('v');

				}
					break;
				case 34:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:431:4:
				// DROP instruction_end
				{
					this.match(this.input, AshvmParser.DROP,
							AshvmParser.FOLLOW_DROP_in_instruction509);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction511);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode('d');

				}
					break;
				case 35:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:436:4:
				// END instruction_end
				{
					this.match(this.input, AshvmParser.END,
							AshvmParser.FOLLOW_END_in_instruction523);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction525);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode('!');

				}
					break;
				case 36:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:441:4:
				// DUP instruction_end
				{
					this.match(this.input, AshvmParser.DUP,
							AshvmParser.FOLLOW_DUP_in_instruction537);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction539);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode("0^");

				}
					break;
				case 37:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:446:4:
				// INC instruction_end
				{
					this.match(this.input, AshvmParser.INC,
							AshvmParser.FOLLOW_INC_in_instruction551);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction553);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode("1+");

				}
					break;
				case 38:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:451:4:
				// DEC instruction_end
				{
					this.match(this.input, AshvmParser.DEC,
							AshvmParser.FOLLOW_DEC_in_instruction565);
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction567);
					this.instruction_end();

					this.state._fsp--;

					this.appendToCode("1-");

				}
					break;
				case 39:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:456:4:
				// STR identifier string instruction_end
				{
					this.match(this.input, AshvmParser.STR,
							AshvmParser.FOLLOW_STR_in_instruction579);
					this.pushFollow(AshvmParser.FOLLOW_identifier_in_instruction581);
					identifier19 = this.identifier();

					this.state._fsp--;

					this.pushFollow(AshvmParser.FOLLOW_string_in_instruction583);
					string18 = this.string();

					this.state._fsp--;

					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction585);
					this.instruction_end();

					this.state._fsp--;

					final String str = this.stripApostrophe(string18);
					this.variables.put(identifier19, this.memory.size());
					this.writeToMemory(str);

				}
					break;
				case 40:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:463:4:
				// INT identifier integer instruction_end
				{
					this.match(this.input, AshvmParser.INT,
							AshvmParser.FOLLOW_INT_in_instruction597);
					this.pushFollow(AshvmParser.FOLLOW_identifier_in_instruction599);
					identifier20 = this.identifier();

					this.state._fsp--;

					this.pushFollow(AshvmParser.FOLLOW_integer_in_instruction601);
					integer21 = this.integer();

					this.state._fsp--;

					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction603);
					this.instruction_end();

					this.state._fsp--;

					this.variables.put(identifier20, this.memory.size());
					this.writeToMemory(integer21);

				}
					break;
				case 41:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:469:4:
				// instruction_end
				{
					this.pushFollow(AshvmParser.FOLLOW_instruction_end_in_instruction615);
					this.instruction_end();

					this.state._fsp--;

				}
					break;

			}
		}
		catch (final RecognitionException re)
		{
			this.reportError(re);
			this.recover(this.input, re);
		}
		finally
		{
		}
		return;
	}

	// $ANTLR end "instruction"

	// $ANTLR start "identifier"
	// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:473:1: identifier
	// returns [String value] : ( IDENTIFIER | );
	public final String identifier() throws RecognitionException
	{
		String value = null;

		Token IDENTIFIER22 = null;

		try
		{
			// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:474:2: (
			// IDENTIFIER | )
			int alt3 = 2;
			final int LA3_0 = this.input.LA(1);

			if ((LA3_0 == AshvmParser.IDENTIFIER))
			{
				alt3 = 1;
			}
			else if ((LA3_0 == AshvmParser.COLON || (LA3_0 >= AshvmParser.STRING && LA3_0 <= AshvmParser.COMMENT)))
			{
				alt3 = 2;
			}
			else
			{
				final NoViableAltException nvae = new NoViableAltException("", 3, 0, this.input);

				throw nvae;
			}
			switch (alt3)
			{
				case 1:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:474:4:
				// IDENTIFIER
				{
					IDENTIFIER22 = (Token) this.match(this.input, AshvmParser.IDENTIFIER,
							AshvmParser.FOLLOW_IDENTIFIER_in_identifier642);
					value = (IDENTIFIER22 != null ? IDENTIFIER22.getText() : null);

				}
					break;
				case 2:
				// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:477:2:
				{
				}
					break;

			}
		}
		catch (final RecognitionException re)
		{
			this.reportError(re);
			this.recover(this.input, re);
		}
		finally
		{
		}
		return value;
	}

	// $ANTLR end "identifier"

	// $ANTLR start "string"
	// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:479:1: string
	// returns [String value] : STRING ;
	public final String string() throws RecognitionException
	{
		String value = null;

		Token STRING23 = null;

		try
		{
			// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:480:2: (
			// STRING )
			// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:480:4:
			// STRING
			{
				STRING23 = (Token) this.match(this.input, AshvmParser.STRING,
						AshvmParser.FOLLOW_STRING_in_string665);
				value = (STRING23 != null ? STRING23.getText() : null);

			}

		}
		catch (final RecognitionException re)
		{
			this.reportError(re);
			this.recover(this.input, re);
		}
		finally
		{
		}
		return value;
	}

	// $ANTLR end "string"

	// $ANTLR start "integer"
	// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:484:1: integer
	// returns [int value] : INTEGER ;
	public final int integer() throws RecognitionException
	{
		int value = 0;

		Token INTEGER24 = null;

		try
		{
			// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:485:2: (
			// INTEGER )
			// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:485:4:
			// INTEGER
			{
				INTEGER24 = (Token) this.match(this.input, AshvmParser.INTEGER,
						AshvmParser.FOLLOW_INTEGER_in_integer685);
				value = Integer.parseInt((INTEGER24 != null ? INTEGER24.getText() : null));

			}

		}
		catch (final RecognitionException re)
		{
			this.reportError(re);
			this.recover(this.input, re);
		}
		finally
		{
		}
		return value;
	}

	// $ANTLR end "integer"

	// $ANTLR start "instruction_end"
	// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:489:1:
	// instruction_end : ( NEWLINE | COMMENT );
	public final void instruction_end() throws RecognitionException
	{
		try
		{
			// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:490:2: (
			// NEWLINE | COMMENT )
			// D:\\Programmieren\\projects\\ashvm\\schrott\\Ashvm.g:
			{
				if ((this.input.LA(1) >= AshvmParser.NEWLINE && this.input.LA(1) <= AshvmParser.COMMENT))
				{
					this.input.consume();
					this.state.errorRecovery = false;
				}
				else
				{
					final MismatchedSetException mse = new MismatchedSetException(null, this.input);
					throw mse;
				}

			}

		}
		catch (final RecognitionException re)
		{
			this.reportError(re);
			this.recover(this.input, re);
		}
		finally
		{
		}
		return;
	}

	// $ANTLR end "instruction_end"

	// Delegated rules

	protected DFA2 dfa2 = new DFA2(this);
	static final String DFA2_eotS = "\64\uffff";
	static final String DFA2_eofS = "\64\uffff";
	static final String DFA2_minS = "\1\4\2\uffff\1\37\1\36\1\35\4\37\1\uffff\3\35\46\uffff";
	static final String DFA2_maxS = "\1\41\2\uffff\7\41\1\uffff\3\41\46\uffff";
	static final String DFA2_acceptS = "\1\uffff\1\1\1\2\7\uffff\1\23\3\uffff\1\35\1\36\1\37\1\40\1\41"
			+ "\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\3\1\4\1\5\1\6\1\7\1\10"
			+ "\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\24\1\25\1\26"
			+ "\1\27\1\30\1\31\1\32\1\33\1\34";
	static final String DFA2_specialS = "\64\uffff}>";
	static final String[] DFA2_transitionS =
	{
			"\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15"
					+ "\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31"
					+ "\1\1\2\uffff\2\32", "", "", "\1\34\2\33", "\1\37\1\36\2\35",
			"\1\40\1\42\1\41\2\40", "\1\44\2\43", "\1\46\2\45", "\1\50\2\47", "\1\52\2\51", "",
			"\1\55\1\uffff\1\54\2\53", "\1\60\1\uffff\1\57\2\56", "\1\63\1\uffff\1\62\2\61", "",
			"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
			"", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
	};

	static final short[] DFA2_eot = DFA.unpackEncodedString(AshvmParser.DFA2_eotS);
	static final short[] DFA2_eof = DFA.unpackEncodedString(AshvmParser.DFA2_eofS);
	static final char[] DFA2_min = DFA.unpackEncodedStringToUnsignedChars(AshvmParser.DFA2_minS);
	static final char[] DFA2_max = DFA.unpackEncodedStringToUnsignedChars(AshvmParser.DFA2_maxS);
	static final short[] DFA2_accept = DFA.unpackEncodedString(AshvmParser.DFA2_acceptS);
	static final short[] DFA2_special = DFA.unpackEncodedString(AshvmParser.DFA2_specialS);
	static final short[][] DFA2_transition;

	static
	{
		final int numStates = AshvmParser.DFA2_transitionS.length;
		DFA2_transition = new short[numStates][];
		for (int i = 0; i < numStates; i++)
		{
			AshvmParser.DFA2_transition[i] = DFA
					.unpackEncodedString(AshvmParser.DFA2_transitionS[i]);
		}
	}

	class DFA2 extends DFA
	{

		public DFA2(final BaseRecognizer recognizer)
		{
			this.recognizer = recognizer;
			this.decisionNumber = 2;
			this.eot = AshvmParser.DFA2_eot;
			this.eof = AshvmParser.DFA2_eof;
			this.min = AshvmParser.DFA2_min;
			this.max = AshvmParser.DFA2_max;
			this.accept = AshvmParser.DFA2_accept;
			this.special = AshvmParser.DFA2_special;
			this.transition = AshvmParser.DFA2_transition;
		}

		@Override
		public String getDescription()
		{
			return "214:1: instruction : ( identifier COLON | NOP instruction_end | PINT instruction_end | PINT integer instruction_end | PCHR instruction_end | PCHR integer instruction_end | PCHR string instruction_end | PUSH identifier instruction_end | PUSH integer instruction_end | PUSH string instruction_end | ADD instruction_end | ADD integer instruction_end | MUL instruction_end | MUL integer instruction_end | SUB instruction_end | SUB integer instruction_end | DIV instruction_end | DIV integer instruction_end | CMP instruction_end | JMP instruction_end | JMP integer instruction_end | JMP identifier instruction_end | JZ instruction_end | JZ integer instruction_end | JZ identifier instruction_end | CALL instruction_end | CALL integer instruction_end | CALL identifier instruction_end | RET instruction_end | PEEK instruction_end | POKE instruction_end | PICK instruction_end | ROLL instruction_end | DROP instruction_end | END instruction_end | DUP instruction_end | INC instruction_end | DEC instruction_end | STR identifier string instruction_end | INT identifier integer instruction_end | instruction_end );";
		}
	}

	public static final BitSet FOLLOW_instruction_in_prog47 = new BitSet(new long[]
	{
		0x000000033FFFFFF2L
	});
	public static final BitSet FOLLOW_identifier_in_instruction60 = new BitSet(new long[]
	{
		0x0000000000000010L
	});
	public static final BitSet FOLLOW_COLON_in_instruction62 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_NOP_in_instruction74 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction76 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_PINT_in_instruction88 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction90 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_PINT_in_instruction99 = new BitSet(new long[]
	{
		0x0000000080000000L
	});
	public static final BitSet FOLLOW_integer_in_instruction101 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction103 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_PCHR_in_instruction115 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction117 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_PCHR_in_instruction126 = new BitSet(new long[]
	{
		0x0000000080000000L
	});
	public static final BitSet FOLLOW_integer_in_instruction128 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction130 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_PCHR_in_instruction139 = new BitSet(new long[]
	{
		0x0000000040000000L
	});
	public static final BitSet FOLLOW_string_in_instruction141 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction143 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_PUSH_in_instruction155 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_identifier_in_instruction157 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction159 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_PUSH_in_instruction168 = new BitSet(new long[]
	{
		0x0000000080000000L
	});
	public static final BitSet FOLLOW_integer_in_instruction170 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction172 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_PUSH_in_instruction181 = new BitSet(new long[]
	{
		0x0000000040000000L
	});
	public static final BitSet FOLLOW_string_in_instruction183 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction185 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_ADD_in_instruction197 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction199 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_ADD_in_instruction208 = new BitSet(new long[]
	{
		0x0000000080000000L
	});
	public static final BitSet FOLLOW_integer_in_instruction210 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction212 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_MUL_in_instruction224 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction226 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_MUL_in_instruction235 = new BitSet(new long[]
	{
		0x0000000080000000L
	});
	public static final BitSet FOLLOW_integer_in_instruction237 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction239 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_SUB_in_instruction251 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction253 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_SUB_in_instruction262 = new BitSet(new long[]
	{
		0x0000000080000000L
	});
	public static final BitSet FOLLOW_integer_in_instruction264 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction266 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_DIV_in_instruction278 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction280 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_DIV_in_instruction289 = new BitSet(new long[]
	{
		0x0000000080000000L
	});
	public static final BitSet FOLLOW_integer_in_instruction291 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction293 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_CMP_in_instruction305 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction307 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_JMP_in_instruction319 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction321 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_JMP_in_instruction330 = new BitSet(new long[]
	{
		0x0000000080000000L
	});
	public static final BitSet FOLLOW_integer_in_instruction332 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction334 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_JMP_in_instruction343 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_identifier_in_instruction345 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction347 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_JZ_in_instruction359 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction361 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_JZ_in_instruction370 = new BitSet(new long[]
	{
		0x0000000080000000L
	});
	public static final BitSet FOLLOW_integer_in_instruction372 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction374 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_JZ_in_instruction383 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_identifier_in_instruction385 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction387 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_CALL_in_instruction399 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction401 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_CALL_in_instruction410 = new BitSet(new long[]
	{
		0x0000000080000000L
	});
	public static final BitSet FOLLOW_integer_in_instruction412 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction414 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_CALL_in_instruction423 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_identifier_in_instruction425 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction427 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_RET_in_instruction439 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction441 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_PEEK_in_instruction453 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction455 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_POKE_in_instruction467 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction469 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_PICK_in_instruction481 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction483 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_ROLL_in_instruction495 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction497 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_DROP_in_instruction509 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction511 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_END_in_instruction523 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction525 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_DUP_in_instruction537 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction539 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_INC_in_instruction551 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction553 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_DEC_in_instruction565 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction567 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_STR_in_instruction579 = new BitSet(new long[]
	{
		0x0000000060000000L
	});
	public static final BitSet FOLLOW_identifier_in_instruction581 = new BitSet(new long[]
	{
		0x0000000040000000L
	});
	public static final BitSet FOLLOW_string_in_instruction583 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction585 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_INT_in_instruction597 = new BitSet(new long[]
	{
		0x00000000A0000000L
	});
	public static final BitSet FOLLOW_identifier_in_instruction599 = new BitSet(new long[]
	{
		0x0000000080000000L
	});
	public static final BitSet FOLLOW_integer_in_instruction601 = new BitSet(new long[]
	{
		0x000000033FFFFFF0L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction603 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_instruction_end_in_instruction615 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_IDENTIFIER_in_identifier642 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_STRING_in_string665 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_INTEGER_in_integer685 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_set_in_instruction_end0 = new BitSet(new long[]
	{
		0x0000000000000002L
	});

}