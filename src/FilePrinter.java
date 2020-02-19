import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class FilePrinter {
	public static void displayFile(String fileName) {
		File file = new File(fileName);
		try {
		PDDocument document = PDDocument.load(file);        
	    PDFTextStripper pdfStripper;
		
			pdfStripper = new PDFTextStripper();
		//System.out.println(pdfStripper.getText(document));
	    pdfStripper.setParagraphStart("/t");
	    pdfStripper.setSortByPosition(true);
	    
	    for (String line: pdfStripper.getText(document).split(pdfStripper.getParagraphStart()))
        {
            System.out.println(line);
            System.out.println("********************************************************************");
        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
