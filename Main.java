import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, SAXException {
    		    	
    	Scanner scanner = new Scanner(System.in);
    	System.out.print("\u001B[36m" + "please type the name of the input file or type \"exit\" to exit: " + "\u001B[0m");
		String inputFileName;

    	SAXParserFactory factory  = SAXParserFactory.newInstance();
    	SAXParser parser = factory.newSAXParser();
    	MyHandler handler = new MyHandler();

    	while (true) {
			System.out.print("\u001B[33m");
	    	inputFileName = scanner.nextLine();
	    	System.out.print("\u001B[0m");

	    	System.out.println();
	    	if ("exit".equalsIgnoreCase(inputFileName)) {
	    		System.out.println("Goodbye."); 
	    		System.exit(0);
	    	}
	    	File inputFile = new File(inputFileName);

			try {
				parser.parse(inputFile, handler);
				break;
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.out.print("\u001B[36m" + "File not found. Try agin or type \"exit\" to exit: " + "\u001B[0m");
			}
			catch (SAXParseException c){
				System.out.println("\u001B[36m" + "The file type you attempted to enter is not allowed. Try agin or type \"exit\" to exit: " + "\u001B[0m");
			}
		}

		handler.getPC().play(scanner);
    }
}
