import java.io.FileInputStream;
import java.io.PrintWriter;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class sliceRunner {
    public static void main(String[] args) throws Exception {
        //Please specify the input filename as an argument when running this file
        FileInputStream fis = new FileInputStream(args[0]);
        ANTLRInputStream input = new ANTLRInputStream(fis);

        SliceLexer lexer = new SliceLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        SliceParser parser = new SliceParser(tokens);
        ParserRuleContext tree = parser.start();
        // print LISP-style tree in file parseTree.pt
        try {
            PrintWriter writer = new PrintWriter("parseTree.pt", "UTF-8");
            writer.println(tree);
            writer.println(tree.toStringTree(parser));
            writer.close();
        } catch (Exception e) {
            System.out.println("Cannot write to the file \n\n\n\n"
                    + e.toString());
        }
        ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker
        sliceBaseListener extractor = new sliceBaseListener();
        walker.walk(extractor, tree); // initiate walk of tree with listener
        // print the output in the file output.bjav
        try {
            PrintWriter writer = new PrintWriter("intermediate.pyj", "UTF-8");
            for (int i = 1; i < extractor.op.size(); i++) {
                writer.println(extractor.op.get(i));
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Cannot write to the file \n\n\n\n"
                    + e.toString());
        }
    }
}