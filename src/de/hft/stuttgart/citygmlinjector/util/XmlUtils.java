package de.hft.stuttgart.citygmlinjector.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlUtils {

	private XmlUtils() {
	}

	public static Node getFirstOccurence(Node parentNode, String nodeName) {
		Node result = null;
		NodeList list = parentNode.getChildNodes();
		for (int i = 0, leni = list.getLength(); i < leni; i++) {
			Node n = list.item(i);

			if (n.getNodeName() == nodeName) {
				result = n;
				break;
			}

		}
		return result;

	}

	public static void removeChildren(Node node) {
		while (node.hasChildNodes())
			node.removeChild(node.getFirstChild());
	}
	
	public static void printChildren(Node node) {
		
		NodeList list = node.getChildNodes();
		
		for (int i = 0, leni = list.getLength(); i < leni; i++) {
			Node n = list.item(i);
			System.out.println(n.getNodeName());

		}
		
	}
	
	public static void trimWhitespace(Node node)
	{
	    NodeList children = node.getChildNodes();
	    for(int i = 0; i < children.getLength(); ++i) {
	        Node child = children.item(i);
	        if(child.getNodeType() == Node.TEXT_NODE) {
	            child.setTextContent(child.getTextContent().trim());
	        }
	        trimWhitespace(child);
	    }
	}

}
