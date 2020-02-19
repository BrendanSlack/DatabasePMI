import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import static java.nio.file.FileVisitResult.*;
import java.io.IOException;

public class DumpFiles extends SimpleFileVisitor<Path> {
	private Pattern acceptedCustomers = null;
	private Pattern rejectBAE = null;
	private Pattern checkRoot = null;
	private Pattern reject = null;
	public DumpFiles(String customerFilter) {
		super();
		createCustomerFilter(customerFilter);
		//createBAEFilter();
		createRejectFilter();
		checkRoot = Pattern.compile("^S:\\\\Customer Files$");
		
	}
	public DumpFiles() {
		super();
		//createBAEFilter();
		//createPastFilter();
		checkRoot = Pattern.compile("^S:\\\\Customer Files$");
	}
	
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
		if(Files.isRegularFile(file)) {
			//System.out.println(file.toString());
			//FileInfo f = new FileInfo(file.toString());
			DatabaseIO.populateDocument(file);
		}
		return CONTINUE;
	}
	public void createCustomerFilter(String c) {
		//System.out.println("Creating filter: S:\\\\Customer Files\\\\"+c);
		acceptedCustomers = Pattern.compile("S:\\\\Customer Files\\\\"+c+".*",Pattern.CASE_INSENSITIVE);
		
	}
	public void createBAEFilter() {
		rejectBAE = Pattern.compile("S:\\\\Customer Files\\\\BAE \\(NH\\).*",Pattern.CASE_INSENSITIVE);
		
	}
	public void createRejectFilter() {
		reject = Pattern.compile("S:\\\\Customer Files\\\\Smile Way (Taiwan)\\\\PFS-618-CD-1\\\\En.*",Pattern.CASE_INSENSITIVE);
		
	}
	
	public boolean allowFolder(Path dir) {
		if(Files.isSymbolicLink(dir)) {
			return false;
		}
		if(acceptedCustomers==null) {
			return true;
		}else {
			Matcher m = acceptedCustomers.matcher(dir.toString());
			Matcher m2 = checkRoot.matcher(dir.toString());
			Matcher m3 = reject.matcher(dir.toString());
			if(m3.matches()) {
				return false;
			}
			return m.matches()||m2.matches();
		}
	}
	
	@Override
	public FileVisitResult preVisitDirectory(Path dir,
	         BasicFileAttributes attrs) {
		if(!allowFolder(dir)) {
			//System.out.println("Skipping customer "+dir.getFileName().toString());
			return SKIP_SUBTREE;
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
