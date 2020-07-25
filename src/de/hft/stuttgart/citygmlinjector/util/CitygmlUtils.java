package de.hft.stuttgart.citygmlinjector.util;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CitygmlUtils {
	
	private CitygmlUtils() {}
	
	public static String getLOD(Document document) {
		
		String levelOfDetail = "lod0";
		
		if (document.getElementsByTagName("bldg:lod1MultiSurface").getLength() > 0) {
			levelOfDetail = "lod1";
		}else if (document.getElementsByTagName("bldg:lod2MultiSurface").getLength() > 0) {
			levelOfDetail = "lod2";
		}else if (document.getElementsByTagName("bldg:lod3MultiSurface").getLength() > 0) {
			levelOfDetail = "lod3";
		}else if (document.getElementsByTagName("bldg:lod4MultiSurface").getLength() > 0){
			levelOfDetail = "lod4";
		}
		
		
		return levelOfDetail;
		
	}
	
	public static String getCitygmlVersion(Document document) {
		
		Element root = document.getDocumentElement();
		String[] namespaceTokens = root.getNamespaceURI().split("/");
		String citygmlVersion = namespaceTokens[namespaceTokens.length-1];
		
		return citygmlVersion;
		
	}
	
	public static String getFileExtension(File file) {
	    String name = file.getName();
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; // empty extension
	    }
	    return name.substring(lastIndexOf);
	}
	
	public static String getFileNameBase(File file) {
	    String name = file.getName();
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return name;
	    }
	    return name.substring(0, lastIndexOf);
	}

}
