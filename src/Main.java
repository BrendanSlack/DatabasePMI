import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.EnumSet;


public class Main {

	public static void main(String[] args) {
		//String file = "S:\\Customer Files\\Altaix\\P6T-2G18G-55-R-512-SFF\\DRAWINGS\\26913457 Driver Board Assembly (REV A3)";
		//FileInfo f = new FileInfo(file);
		//DatabaseIO.populateDocument(f);
		//String path ="S:\\Customer Files";
		//startDump('m');
		startDump2('s','m');
		//DatabaseIO.printProductDoucments("APD-4-400M3G-17-SFF-75W");
		
	}
	public static void startDump(char start) {
		String path ="S:\\Customer Files\\"; 
		Path startingDir = Paths.get(path);
		char index = start;
		try {
			for(char c = start;c<='z';c++) {
				DumpFiles visitor = new DumpFiles(""+c);
				//LoopChecker visitor = new LoopChecker(c+"");
				Files.walkFileTree(startingDir,visitor);
				System.out.println("Finshed with index: "+index);
				index++;
			}
		} catch (IOException e) {
			e.printStackTrace();
			
			System.out.println("Waiting for one minute..");
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Restarting file Dump at index: "+index);
			startDump(index);
		}
	}
	public static void startDump2(char start1, char start2) {
		String path ="S:\\Customer Files\\"; 
		Path startingDir = Paths.get(path);
		char index1 = start1;
		char index2 = start2;
		try {
			for(char c = start1;c<='z';c++) {
				index1 = c;
				index2='a';
				for(char h = start2;h<='z';h++) {
					index2= h;
					System.out.println("creating filter "+c+h);
					DumpFiles visitor = new DumpFiles(""+c+h);
					//LoopChecker visitor = new LoopChecker(c+"");
					Files.walkFileTree(startingDir,visitor);
					System.out.println("Finshed with index: "+index1+index2);
				}
				start2='a';
				
			}
		} catch (IOException e) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			System.out.println("Encountered error at "+sdf.format(timestamp));
			e.printStackTrace();
			System.out.println("Waiting for one minute..");
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Restarting file Dump at index: "+index1+index2);
			startDump2(index1,index2);
		}
	}

}