package pl.edu.pk.iti.copperAnt;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class Configuration {
	private static Configuration m_instance = null;
	public static String ROOT_NAME = "element";
	
	public static Configuration getInstance() {
		  if(m_instance == null) {
		         m_instance = new Configuration();
		  }
		      return m_instance;
		   
	}
		
	public void init(String fileName) {
		try {
		
			File file = new File(fileName);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nodeLst = doc.getElementsByTagName(Configuration.ROOT_NAME);
			for (int s = 0; s < nodeLst.getLength(); ++s) {

			    Node fstNode = nodeLst.item(s);
			    
			    if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
			  
			           Element fstElmnt = (Element) fstNode;
			      NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("type");
			      Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
			      NodeList fstNm = fstNmElmnt.getChildNodes();
			      System.out.println("First Name : "  + ((Node) fstNm.item(0)).getNodeValue());
			      NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("lastname");
			      Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
			      NodeList lstNm = lstNmElmnt.getChildNodes();
			      System.out.println("Last Name : " + ((Node) lstNm.item(0)).getNodeValue());
			    }

			  }
			  } catch (Exception e) {
			    e.printStackTrace();
			  }
			 
		}
	
	
		 
}
