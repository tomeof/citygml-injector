package de.hft.stuttgart.citygmlinjector.action;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.hft.stuttgart.citygmlinjector.contract.IActionHandle;
import de.hft.stuttgart.citygmlinjector.state.Context;
import de.hft.stuttgart.citygmlinjector.util.CitygmlUtils;
import de.hft.stuttgart.citygmlinjector.util.XmlUtils;

public class BuildingPartHandler implements IActionHandle{
	
	@Override
	public void handle(Context context) {
	
		String citygmlVersion = CitygmlUtils.getCitygmlVersion(context.document);
		
		String levelOfDetail = CitygmlUtils.getLOD(context.document);
		
		Node lod = context.document.createElementNS("http://www.opengis.net/citygml/building/"+citygmlVersion, "bldg:"+levelOfDetail+"Solid");
		Node solid = context.document.createElementNS("http://www.opengis.net/gml", "gml:Solid");
		Node exterior = context.document.createElementNS("http://www.opengis.net/gml", "gml:exterior");
		Node CompositeSurface = context.document.createElementNS("http://www.opengis.net/gml", "gml:CompositeSurface");
		
		NodeList selectedNodes = context.document.getElementsByTagName(context.selectedElement);
		for (int i=0, leni = selectedNodes.getLength(); i<leni; i++) {
			Element currentNode = (Element) selectedNodes.item(i);
			NodeList allPolygons = currentNode.getElementsByTagName("gml:Polygon");
			
			for (int j=0, lenj = allPolygons.getLength(); j<lenj; j++) {
				Element polygon = (Element) allPolygons.item(j);
				String polygonId = polygon.getAttribute("gml:id");
				// create new surfaceMember
				Element surfaceMember = context.document.createElementNS("http://www.opengis.net/gml", "gml:surfaceMember");
				surfaceMember.setAttribute("xlink:href", "#" + polygonId);
				// append surfaceMember
				CompositeSurface.appendChild(surfaceMember);
			}
			
			if (CompositeSurface.hasChildNodes()) {
				exterior.appendChild(CompositeSurface);
				solid.appendChild(exterior);
				lod.appendChild(solid);
				
				Node referenceNode = XmlUtils.getFirstOccurence(currentNode, "bldg:boundedBy");
				currentNode.insertBefore(lod, referenceNode);
				currentNode.normalize();
			}
			
			lod = context.document.createElementNS("http://www.opengis.net/citygml/building/"+citygmlVersion, "bldg:"+levelOfDetail+"Solid");
			solid = context.document.createElementNS("http://www.opengis.net/gml", "gml:Solid");
			exterior = context.document.createElementNS("http://www.opengis.net/gml", "gml:exterior");
			CompositeSurface = context.document.createElementNS("http://www.opengis.net/gml", "gml:CompositeSurface");
			
		}
	}
	

}
