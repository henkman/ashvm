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
	
	List<Reference> references = new ArrayList<Reference>();
	
	class Reference
	{
		public String label;
		public int codePos;
		public int pointer;
		public char op;
		public String code;
		public boolean isBackwardReference;
		public int shift;

		public Reference(final String label, final int codePos, final int pointer, final char op)
		{
			this.label = label;
			this.codePos = codePos;
			this.pointer = pointer;
			this.op = op;
			this.code = "";
			this.isBackwardReference = (pointer != -1);
			this.shift = 0;
		}
	}

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

	void generateBackJumpReferenceCode(final Reference reference)
	{
		String yeOldeCode = "im old";
		String oldCode = reference.code;
		String newCode = reference.code + " ";
		int diff;
		String addCode;

		for (;;)
		{
			diff = this.calcAddressDiff(reference.codePos + oldCode.length() + reference.shift,
					reference.pointer);
			addCode = this.produceInteger(diff);
			newCode = addCode + reference.op;

			// we got into an endless loop
			if (yeOldeCode.length() == newCode.length())
			{
				final int d = newCode.length() - oldCode.length();
				final StringBuilder sb = new StringBuilder();
				String code;
				if (d > 0) // newcode is longer
				{
					code = oldCode;
				}
				else
				// oldcode is longer
				{
					code = newCode;
				}

				for (int i = 0; i < d; i++)
				{
					sb.append(' '); // appending nops
				}
				sb.append(code);

				reference.code = sb.toString();
				return;
			}

			if (oldCode.length() == newCode.length())
			{
				break;
			}

			yeOldeCode = oldCode;
			oldCode = newCode;
		}

		reference.code = newCode;
	}

	void generateForwardJumpReferenceCode(final Reference reference)
	{
		final int diff = this.calcAddressDiff(reference.codePos, reference.pointer
				+ reference.shift);
		final String addCode = this.produceInteger(diff);
		reference.code = addCode + reference.op;
	}

	void resolveBackwardReference(final String label, final int labelPos)
	{
		for (final Reference reference : this.references)
		{
			if (reference.label.equals(label))
			{
				reference.pointer = labelPos;
			}
		}
	}

	void addReference(final String label, final int codePos, final int pointer, final char op)
	{
		this.references.add(new Reference(label, codePos, pointer, op));
	}

	int sumCodeSizeOfReferencesInRange(final Reference targReference)
	{
		int sum = 0;
		for (final Reference reference : this.references)
		{
			if (targReference.op == 'c')
			{
				if (reference.codePos <= targReference.pointer)
				{
					sum += reference.code.length();
				}
			}
			else
			{
				if (reference == targReference)
				{
					continue;
				}

				if (reference.codePos >= targReference.codePos
						&& reference.codePos <= targReference.pointer
						|| reference.codePos >= targReference.pointer
						&& reference.codePos <= targReference.codePos)
				{
					sum += reference.code.length();
				}
			}
		}

		return sum;
	}

	void generateCallCode(final Reference reference)
	{
		final String addCode = this.produceInteger(reference.pointer + reference.shift);
		reference.code = addCode + reference.op;
	}

	void generateReferenceCode(final Reference reference)
	{
		if (reference.op == 'c')
		{
			this.generateCallCode(reference);
		}
		else
		{
			if (reference.isBackwardReference)
			{
				this.generateBackJumpReferenceCode(reference);
			}
			else
			{
				this.generateForwardJumpReferenceCode(reference);
			}
		}
	}

	void generateReferenceCodes()
	{
		// 1. Generate Codes ignoring other references
		for (final Reference reference : this.references)
		{
			this.generateReferenceCode(reference);
		}

		// 2. fix conflicts between references by changing own jump size (until
		// no reference code changes anymore)
		boolean allFixed;
		do
		{
			allFixed = true;

			for (final Reference reference : this.references)
			{
				final int sum = this.sumCodeSizeOfReferencesInRange(reference);
				if (reference.shift != sum)
				{
					allFixed = false;
				}

				reference.shift = sum;
				this.generateReferenceCode(reference);
			}
		}
		while (!allFixed);

		// 3. shift the codePos of all references according the previous
		// references
		int sum = 0;
		for (final Reference reference : this.references)
		{
			reference.codePos += sum;
			sum += reference.code.length();
		}

	}

	void writeReferenceCodes()
	{
		for (final Reference reference : this.references)
		{
			this.writeToCode(reference.code, reference.codePos);
		}
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
				resolveBackwardReference($identifier.value, getCurrentCodePosition());
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
				addReference($identifier.value, getCurrentCodePosition(), -1, 'g');
			}
			else {
				addReference($identifier.value, getCurrentCodePosition(), val.intValue(), 'g');
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
				addReference($identifier.value, getCurrentCodePosition(), -1, '?');
			}
			else {
				addReference($identifier.value, getCurrentCodePosition(), val.intValue(), '?');
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
				addReference($identifier.value, getCurrentCodePosition(), -1, 'c');
			}
			else {
				addReference($identifier.value, getCurrentCodePosition(), val.intValue(), 'c');
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
		
	|	SWAP instruction_end
		{
			appendToCode("1v");
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
	
SWAP	:	'swap'
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
	