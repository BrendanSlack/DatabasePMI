import java.nio.file.Path;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.regex.Pattern;

public class DatabaseIO {
	
	//private static Pattern productPattern = Pattern.compile("(\\w*?)-");
	private static Connection conn = initializeConnection();
	private static Statement stmt = initializeStatement();
	private static ResultSet rs = null;
	private static PreparedStatement findDocument = initializeFindDocument();
	private static PreparedStatement findProduct = initializeFindProduct();
	private static PreparedStatement addDocument = initializeAddDocument();
	
	
	private static PreparedStatement initializeFindProduct() {
		PreparedStatement findProduct = null;
		try {
			findProduct = conn.prepareStatement("SELECT product_id FROM product WHERE product_name = ?");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return findProduct;
	}
	private static PreparedStatement initializeAddDocument() {
		PreparedStatement addDocument = null;
		try {
			addDocument = conn.prepareStatement("INSERT INTO productdocument "
					+ "(path, file_name, customer, folder, product_ID)  "
					+ "VALUES (?,?,?,?,?)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return addDocument;
	}
	public static void populateDocument(Path file) {
		//System.out.println("Adding "+ f.getFilePath()+ " to database");
		if(findDocument(file)!=-1) {
			//System.out.println("Document already exists");
			return;
		}
			FileInfo f = new FileInfo(file.toString());
		    int productID=-1;
		    //System.out.println("Visiting: "+path);
		    try {
		    	findProduct.setString(1, f.getProduct());
		    	rs = findProduct.executeQuery();
		    	//rs = stmt.executeQuery("SELECT product_id FROM product WHERE product_name = \""+product_name+"\"");
				if(rs.first()) {
					productID = rs.getInt(1);
				}else {
					productID = addProduct(f.getProduct());
				}
				addDocument.setString(1, f.getFilePath());
				addDocument.setString(2, f.getFileName());
				addDocument.setString(3, f.getCustomer());
				addDocument.setString(4, f.getDocType());
				addDocument.setInt(5, productID);
				addDocument.execute();
			} catch (SQLException e) {
				System.out.println("Error reading/writing to database");
				e.printStackTrace();
			}
		return;
	}
	public static String escapeSlashes(String path) {
		if(path==null)return null;
		String escapedPath = path.replace("\\","\\\\");
	    //System.out.println("Escaped: "+escapedPath);
		return escapedPath;
	}
	private static PreparedStatement initializeFindDocument() {
		PreparedStatement findDocument=null;
		try {
			findDocument = conn.prepareStatement("SELECT ProductDocument_id "
					+ "FROM productDocument "
					+ "WHERE path =?");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return findDocument;
	}
	public static int findDocument(Path file) {
	    int documentID=-1;
	    try {
	    	findDocument.setString(1, file.toString());
	    	rs = findDocument.executeQuery();
			if(rs.first()) {
				documentID = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return documentID;
	}
	public static int addProduct(String product_name) {
		int productID = -1;
		try {
			stmt.execute("INSERT INTO product (product_name) "
					+ "VALUES (\""+product_name+"\")");
			rs = stmt.executeQuery("SELECT product_id FROM product WHERE product_name = \""+product_name+"\"");
			if(rs.first()) productID = rs.getInt(1);
		} catch (SQLException e) {
			System.out.println("SQL error adding product in function addProduct");
			e.printStackTrace();
		}
		return productID;
	}
	public static void printProductDoucments(String product_name) {
		int productID = -1;
		try {
			rs = stmt.executeQuery("SELECT product_id FROM product WHERE product_name = \""+product_name+"\"");
			if(rs.first()) {productID = rs.getInt(1);}
			rs = stmt.executeQuery("SELECT file_name, customer, folder FROM ProductDocument WHERE product_id = "+productID);
			while(rs.next()){
		    	System.out.println("File Name: "+rs.getString(1) 
				+" | Customer: "+ rs.getString(2) 
				+" | Folder: "+rs.getString(3)+"\n");
		    }
		} catch (SQLException e) {
			System.out.println("Couldn't find specified Document for function printProductDocuments");
			e.printStackTrace();
		}
		
		
	}
	public static boolean customerExists(String customer) {
		int count =0;
	    try {
			rs = stmt.executeQuery("SELECT count(*) FROM productdocument WHERE customer = \""+customer+"\"");
			if(rs.first()) {count = rs.getInt(1);}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count>0;
	}
	
	private static Connection initializeConnection() {
		Connection temp =null;
		try {
			temp =
			   DriverManager.getConnection("jdbc:mysql://localhost/mydb?" +
			                               "user=root&password=RascalFlat1942");
		} catch (SQLException e) {
			System.out.println("Failed to Connect to database");
			e.printStackTrace();
		}
		return temp;
	}
	private static Statement initializeStatement() {
		Statement temp = null;
		try {
			temp = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}

}
