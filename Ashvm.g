grammar Ashvm;

options 
{
    language = Java;
}

@header {
	package org.ashvm;
	import java.util.HashMap;
	import java.util.ArrayList;
	import java.util.List;
	import java.io.PrintStream;
}

@lexer::header {
	package org.ashvm;
}

@members {
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
	
	void appendToCode(char c) {
		code.add(c);
	}
	
	void appendToCode(String s) {
		for(char c : s.toCharArray()) {
			code.add(c);
		}
	}
	
	void writeToCode(char c, int pos) {
		code.add(pos, c);
	}
	
	void writeToCode(String s, int pos) {
		for(char c : s.toCharArray()) {
			code.add(pos++, c);
		}
	}
	
	void writeToMemory(int val) {
		memory.add(val);
	}
	
	void writeToMemory(String s) {
		for(char c : s.toCharArray()) {
			memory.add((int) c);
		}
	}
	
	int getCurrentCodePosition() {
		return code.size();
	}
	
	String generateReferenceCode(int codePos, int pointer, char op) {
		String oldCode = " ";
		String newCode = " ";
		int diff;
		String addCode;
		
		for(;;) {
			diff = calcAddressDiff(codePos + oldCode.length(), pointer);
			addCode = produceInteger(diff);
			newCode = addCode + op;
			if(oldCode.length() == newCode.length()) {
				break;
			}
			
			oldCode = newCode;
		}
		return newCode;
	}
	
	void addReference(int codePos, int pointer, char op) {
		String refCode = generateReferenceCode(codePos, pointer, op);
		writeToCode(refCode, codePos);
	}
	
	void printMemory()
	{
		if (memory.size() == 0)
		{
			return;
		}

		memOut.print(memory.get(0));
		for (int i = 1; i < memory.size(); i++)
		{
			memOut.print(',');
			memOut.print(memory.get(i));
		}
	}

	void printCode()
	{
		for (final char c : code)
		{
			codeOut.print(c);
		}
	}
	
	String stripApostrophe(String str) {
		return str.substring(1, str.length() - 1);
	}
}

prog	:	instruction*
	;
	
instruction
	:	identifier COLON
		{
			if(labels.get($identifier.value) != null) {
				System.err.println("-> " + $identifier.value + " redeclared");
			}
			else {
				labels.put($identifier.value, getCurrentCodePosition());
			}
		}
		
	|	NOP instruction_end
		{
			appendToCode(' ');
		}
		
	|	PINT instruction_end
		{
			appendToCode('p');
		}
	|	PINT integer instruction_end
		{
			String valPush = produceInteger($integer.value);
			appendToCode(valPush);
			appendToCode('p');
		}
		
	|	PCHR instruction_end
		{
			appendToCode('P');
		}
	|	PCHR integer instruction_end
		{
			String valPush = produceInteger($integer.value);
			appendToCode(valPush);
			appendToCode('P');
		}
	|	PCHR string instruction_end
		{
			String str = stripApostrophe($string.value);
			if(str.length() == 0) {
				System.err.println("-> string is smaller than 1");
			}
			else {
				int fAscii = str.codePointAt(0);
				String valPush = produceInteger(fAscii);
				appendToCode(valPush);
				appendToCode('P');
			}
		}
		
	|	PUSH identifier instruction_end
		{
			Integer val = variables.get($identifier.value);
			if(val == null) {
				System.err.println("-> variable '" + $identifier.value + "' is not declared");
			}
			else {
				String valPush = produceInteger(val.intValue());
				appendToCode(valPush);
			}
		}
	|	PUSH integer instruction_end
		{
			String valPush = produceInteger($integer.value);
			appendToCode(valPush);
		}
	|	PUSH string instruction_end
		{
			String str = stripApostrophe($string.value);
			if(str.length() == 0) {
				System.err.println("-> string is smaller than 1");
			}
			else {
				int fAscii = str.codePointAt(0);
				String valPush = produceInteger(fAscii);
				appendToCode(valPush);
			}
		}
		
	|	ADD instruction_end
		{
			appendToCode('+');
		}
	|	ADD integer instruction_end
		{
			String valPush = produceInteger($integer.value);
			appendToCode(valPush);
			appendToCode('+');
		}
		
	|	MUL instruction_end
		{
			appendToCode('*');
		}
	|	MUL integer instruction_end
		{
			String valPush = produceInteger($integer.value);
			appendToCode(valPush);
			appendToCode('*');
		}
		
	|	SUB instruction_end
		{
			appendToCode('-');
		}
	|	SUB integer instruction_end
		{
			String valPush = produceInteger($integer.value);
			appendToCode(valPush);
			appendToCode('-');
		}
		
	|	DIV instruction_end
		{
			appendToCode('/');
		}
	|	DIV integer instruction_end
		{
			String valPush = produceInteger($integer.value);
			appendToCode(valPush);
			appendToCode('/');
		}
		
	|	CMP instruction_end
		{
			appendToCode(':');
		}
		
	|	JMP instruction_end
		{
			appendToCode('g');
		}
	|	JMP integer instruction_end
		{
			String valPush = produceInteger($integer.value);
			appendToCode(valPush);
			appendToCode('g');
		}
	|	JMP identifier instruction_end
		{
			Integer val = labels.get($identifier.value);
			if(val == null) {
				System.err.println("-> label '" + $identifier.value + "' is not declared");
			}
			else {
				addReference(getCurrentCodePosition(), val.intValue(), 'g');
			}
		}
		
	|	JZ instruction_end
		{
			appendToCode('?');
		}
	|	JZ integer instruction_end
		{
			String valPush = produceInteger($integer.value);
			appendToCode(valPush);
			appendToCode('?');
		}
	|	JZ identifier instruction_end
		{
			Integer val = labels.get($identifier.value);
			if(val == null) {
				System.err.println("-> label '" + $identifier.value + "' is not declared");
			}
			else {
				addReference(getCurrentCodePosition(), val.intValue(), '?');
			}
		}
		
	|	CALL instruction_end
		{
			appendToCode('c');
		}
	|	CALL integer instruction_end
		{
			String valPush = produceInteger($integer.value);
			appendToCode(valPush);
			appendToCode('c');
		}
	|	CALL identifier instruction_end
		{
			Integer val = labels.get($identifier.value);
			if(val == null) {
				System.err.println("-> label '" + $identifier.value + "' is not declared");
			}
			else {
				addReference(getCurrentCodePosition(), val.intValue(), 'c');
			}
		}
		
	|	RET instruction_end
		{
			appendToCode('$');
		}
		
	|	PEEK instruction_end
		{
			appendToCode('<');
		}
		
	|	POKE instruction_end
		{
			appendToCode('>');
		}
		
	|	PICK instruction_end
		{
			appendToCode('^');
		}
		
	|	ROLL instruction_end
		{
			appendToCode('v');
		}
		
	|	DROP instruction_end
		{
			appendToCode('d');
		}
		
	|	END instruction_end
		{
			appendToCode('!');
		}
		
	|	DUP instruction_end
		{
			appendToCode("0^");
		}
		
	|	INC instruction_end
		{
			appendToCode("1+");
		}
		
	|	DEC instruction_end
		{
			appendToCode("1-");
		}
		
	|	STR identifier string instruction_end
		{
			String str = stripApostrophe($string.value);
			variables.put($identifier.value, memory.size());
			writeToMemory(str);
		}
		
	|	INT identifier integer instruction_end
		{
			variables.put($identifier.value, memory.size());
			writeToMemory($integer.value);
		}
		
	|	instruction_end
		{}
    ;
    
identifier returns [String value] 
	:	IDENTIFIER
		{$value = $IDENTIFIER.text;}
	|	
	;

string returns [String value]
	:	STRING
		{$value = $STRING.text;}
	;
	
integer returns [int value]
	:	INTEGER
		{$value = Integer.parseInt($INTEGER.text);}
	;
	
instruction_end 
	:	NEWLINE
	|	COMMENT
	;

NOP	:	'nop'
	;
    
PINT	:	'pint'
	;
	
PCHR	:	'pchr'
	;
	
PUSH	:	'push' 
	;

ADD	:	'add' 
	;
	
MUL	:	'mul'
	;
	
SUB	:	'sub'
	;
	
DIV	:	'div' 
	;
	
CMP	:	'cmp'
	;
	
JMP	:	'jmp' 
	;
	
JZ	:	'jz'
	;
	
CALL	:	'call'
	;
	
RET	:	'ret'
	;

PEEK	:	'peek'
	;
	
POKE	:	'poke'
	;
	
PICK	:	'pick'
	;
	
ROLL	:	'roll'
	;
	
DROP	:	'drop'
	;
	
END	:	'end'
	;
	
DUP	:	'dup'
	;
	
INC	:	'inc'
	;
	
DEC	:	'dec'
	;
	
STR	:	'str'
	;
	
INT	:	'int'
	;
	
COLON	:	':'
	;

INTEGER	:	('-')? ('0'..'9')+	
	;
    
STRING	:	'"' (ESC|~('\\'|'\n'|'"'))* '"'	
	;
	
fragment
ESC
	:	'\\' .
	;

NEWLINE
    :   (('\r')? '\n' )
    ;
	
COMMENT
    :   ';' .* '\n'
    ;
    
IDENTIFIER	:	('.')? ('a'..'z'|'A'..'Z')+	
		; 
    
WS	:	(' '|'\t')+
		{skip();}
	;
	