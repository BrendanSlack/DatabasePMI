import java.util.ArrayList;
import java.util.List;

public class ProductFilesBatcher {
	private String productName;
	private String productID;
	private List<String> paths;
	public ProductFilesBatcher() {
		paths = new ArrayList<String>();
	}
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public void executeBatch() {
		return;
	}

}
