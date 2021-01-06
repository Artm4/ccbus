package ccbus.dbtool.util;

import ccbus.dbtool.intermediate.RoutineCode;

import java.io.PrintStream;

public class Printer
{
    private static final int INDENT_WIDTH = 4;
    private static final int LINE_WIDTH = 80;

    protected PrintStream ps;      // output print stream
    protected int length;          // output line length
    protected String indent;       // indent spaces
    protected String indentation;  // indentation of a line
    protected StringBuilder line;  // output line

    Printer(PrintStream ps)
    {
        this.ps = ps;
        this.length = 0;
        this.indentation = "";
        this.line = new StringBuilder();

        // The indent is INDENT_WIDTH spaces.
        this.indent = "";
        for (int i = 0; i < INDENT_WIDTH; ++i) {
            this.indent += " ";
        }
    }

    /**
     * Print the intermediate code as a parse tree.
     * @param symTabStack the symbol table stack.
     */
    public void print(RoutineCode symTabStack)
    {
        ps.println("\n===== INTERMEDIATE CODE =====");
    }

    /**
     * Append text to the output line.
     * @param text the text to append.
     */
    protected void append(String text)
    {
        int textLength = text.length();
        boolean lineBreak = false;

        // Wrap lines that are too long.
        if (length + textLength > LINE_WIDTH) {
            printLine();
            line.append(indentation);
            length = indentation.length();
            lineBreak = true;
        }

        // Append the text.
        if (!(lineBreak && text.equals(" "))) {
            line.append(text);
            length += textLength;
        }
    }

    /**
     * Print an output line.
     */
    protected void printLine()
    {
        if (length > 0) {
            ps.println(line);
            line.setLength(0);
            length = 0;
        }
    }
}
