import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class FileInfo {
	private String filePath;
	private String product;
	private String docType;
	private String customer;
	private String revision;
	private String fileName;
	
	private String lastModified;
	private String productCategory;
	private static Pattern filePathPattern = Pattern.compile("S:\\\\Customer Files\\\\(.*?)\\\\(.*?)\\\\(.*?)\\\\(.*?)");
	private Matcher filePathMatcher;
	FileInfo(String filePath){
		this.setFilePath(filePath);
		this.filePathMatcher = filePathPattern.matcher(this.filePath.toString());
		if(this.filePathMatcher.matches()) {
			this.customer = filePathMatcher.group(1);
			this.product = filePathMatcher.group(2);
			this.docType = filePathMatcher.group(3);
			this.fileName =filePathMatcher.group(4);
		}else {
			this.customer = "Error";
			this.product = "Error";
			this.docType = "Error";
			this.fileName ="Error";
		}
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getCustomer() {
		return this.customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}
	public String getLastModified() {
		return lastModified;
	}
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String category) {
		this.productCategory = category;
	}
	public void print() {
		System.out.println("Customer is: "+this.getCustomer());
		System.out.println("For Product: "+this.getProduct());
		System.out.println("Doc type: "+this.getDocType());
		System.out.println("Filename: "+this.getFileName());
	}
}
