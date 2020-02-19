import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import static java.nio.file.FileVisitResult.*;
import java.io.IOException;

public class LoopChecker extends SimpleFileVisitor<Path> {
	private Pattern acceptedCustomers = null;
	public LoopChecker(String customerFilter) {
		super();
		createCustomerFilter(customerFilter);
	}
	public LoopChecker() {
		super();
	}
	
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
		if(Files.isRegularFile(file)) {
			//System.out.println(file.toString());
			//FileInfo f = new FileInfo(file.toString());
			//DatabaseIO.populateDocument(f);
		}
		return CONTINUE;
	}
	public void createCustomerFilter(String c) {
		acceptedCustomers = Pattern.compile("S:\\\\Customer Files\\\\"+c+".*",Pattern.CASE_INSENSITIVE);
		
	}
	
	public boolean allowFolder(Path dir) {
		if(acceptedCustomers==null) {
			return true;
		}else {
			Matcher m = acceptedCustomers.matcher(dir.toString());
			return m.matches()||dir.toString().contentEquals("S:\\Customer Files");
		}
	}
	
	@Override
	public FileVisitResult preVisitDirectory(Path dir,
	         BasicFileAttributes attrs) {
		if(!allowFolder(dir)) {
			System.out.println("Skipping customer "+dir.getFileName().toString());
			return SKIP_SUBTREE;
		}else {
			System.out.println("Visiting directory: "+dir.toString());
		}
		return CONTINUE;
	}
	
	@Override
	public FileVisitResult visitFileFailed(Path file,
            IOException exc) throws IOException {
			System.err.println(exc);
			throw exc;
}
	
}
