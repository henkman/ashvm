package org.ashvm;

import java.io.File;
import java.io.PrintStream;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.TokenRewriteStream;
import org.antlr.runtime.TokenStream;

public class Ashvm
{
	public static void main(final String[] args) throws Exception
	{
		if (args.length != 1)
		{
			System.out.println("usage: java -jar Ashvm.jar [sourcefile]");
			System.exit(0);
		}

		final File sourceFile = new File(args[0]);
		if (!sourceFile.exists())
		{
			System.err.println("file does not exist");
			System.exit(1);
		}

		final CharStream input = new ANTLRFileStream(sourceFile.getAbsolutePath());
		final AshvmLexer lex = new AshvmLexer(input);
		final TokenStream tokens = new TokenRewriteStream(lex);
		final AshvmParser parser = new AshvmParser(tokens);

		File dir = sourceFile.getParentFile();
		if (dir == null)
		{
			dir = new File(".");
		}

		String fileName = sourceFile.getName();
		final int dotPos = fileName.indexOf('.');
		if (dotPos != -1)
		{
			fileName = fileName.substring(0, dotPos);
		}

		final String fileWoutExt = dir.getAbsolutePath() + File.separatorChar + fileName;

		parser.memOut = new PrintStream(fileWoutExt + ".mem");
		parser.codeOut = new PrintStream(fileWoutExt + ".hvm");
		parser.prog();
		parser.generateReferenceCodes();
		parser.writeReferenceCodes();
		parser.printCode();
		parser.printMemory();
	}
}
