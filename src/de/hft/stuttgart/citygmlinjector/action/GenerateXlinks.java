/*
 * CityGML Injector is a Java software for editing a CityGML file. 
 * https://github.com/tomeof/citygml-injector
 * 
 * Copyright (c) 2016, Hochschule fur Technik Stuttgart
 *
 * CityGML Injector is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * CityGML Injector is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with CityGML Injector. If not, see <http://www.gnu.org/licenses/>.
 */

package de.hft.stuttgart.citygmlinjector.action;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.hft.stuttgart.citygmlinjector.contract.IAction;
import de.hft.stuttgart.citygmlinjector.state.Context;

public class GenerateXlinks extends BaseAction implements IAction{
	
	public GenerateXlinks(Context context) {
		super(context);
	}

	@Override
	public void applyAction() {
		Element root = context.document.getDocumentElement();
		String[] namespaceTokens = root.getNamespaceURI().split("/");
		String citygmlVersion = namespaceTokens[namespaceTokens.length-1];
		
		/*
		NodeList allBuildingParts = context.document.getElementsByTagName("bldg:BuildingPart");
		boolean hasBuildingParts = (allBuildingParts.getLength()>0);
		*/
		
		String levelOfDetail;
		if (context.document.getElementsByTagName("bldg:lod1MultiSurface").getLength() > 0) {
			levelOfDetail = "lod1";
		}else if (context.document.getElementsByTagName("bldg:lod2MultiSurface").getLength() > 0) {
			levelOfDetail = "lod2";
		}else if (context.document.getElementsByTagName("bldg:lod3MultiSurface").getLength() > 0) {
			levelOfDetail = "lod3";
		}else{
			levelOfDetail = "lod4";
		}
		
		/*
		NodeList allSurfaceMembers = context.document.getElementsByTagName("gml:surfaceMember");
		List<String> reusedPolygons = new ArrayList<>(); 
		for (int i=0; i<allSurfaceMembers.getLength(); i++) {
			Element element = (Element) allSurfaceMembers.item(i);
			if (element.hasAttribute("xlink:href")) {
				String polygonId = element.getAttribute("xlink:href");
				polygonId = polygonId.substring(1, polygonId.length()-1);
				reusedPolygons.add(polygonId);
			}
		}
		*/
		
		Node lod = context.document.createElementNS("http://www.opengis.net/citygml/building/"+citygmlVersion, "bldg:"+levelOfDetail+"Solid");
		Node solid = context.document.createElementNS("http://www.opengis.net/gml", "gml:Solid");
		Node exterior = context.document.createElementNS("http://www.opengis.net/gml", "gml:exterior");
		Node CompositeSurface = context.document.createElementNS("http://www.opengis.net/gml", "gml:CompositeSurface");

		
		NodeList selectedNodes = context.document.getElementsByTagName(context.selectedElement);
		for (int i=0; i<selectedNodes.getLength(); i++) {
			Element currentNode = (Element) selectedNodes.item(i);
			NodeList allPolygons = currentNode.getElementsByTagName("gml:Polygon");
			for (int j=0; j<allPolygons.getLength(); j++) {
				Element polygon = (Element) allPolygons.item(j);
				String polygonId = polygon.getAttribute("gml:id");
				// create new surfaceMember
				Element surfaceMember = context.document.createElementNS("http://www.opengis.net/gml", "gml:surfaceMember");
				surfaceMember.setAttribute("xlink:href", "#" + polygonId);
				// append surfaceMember
				CompositeSurface.appendChild(surfaceMember);
			}
			exterior.appendChild(CompositeSurface);
			solid.appendChild(exterior);
			lod.appendChild(solid);
			//NodeList bldg = context.document.getElementsByTagName(context.selectedElement);
			currentNode.insertBefore(lod, currentNode.getFirstChild());
			
			lod = context.document.createElementNS("http://www.opengis.net/citygml/building/"+citygmlVersion, "bldg:"+levelOfDetail+"Solid");
			solid = context.document.createElementNS("http://www.opengis.net/gml", "gml:Solid");
			exterior = context.document.createElementNS("http://www.opengis.net/gml", "gml:exterior");
			CompositeSurface = context.document.createElementNS("http://www.opengis.net/gml", "gml:CompositeSurface");
			
		}
		
		/*
		for (int i=0; i<allBuildingParts.getLength(); i++) {
			Element bp = (Element) allBuildingParts.item(i);
			NodeList allPolygons = bp.getElementsByTagName("gml:Polygon");
			for (int j=0; i<allPolygons.getLength(); j++) {
				Element polygon = (Element) allPolygons.item(j);
				String id = polygon.getAttribute("gml:id");
				// create new element
				Element surfaceMember = context.document.createElementNS("http://www.opengis.net/gml", "gml:surfaceMember");
				surfaceMember.setAttribute("xlink:href", "#" + id);
				
				// append element
				CompositeSurface.appendChild(surfaceMember);
				
			}
			exterior.appendChild(CompositeSurface);
			solid.appendChild(exterior);
			lod.appendChild(solid);
			NodeList bldg = context.document.getElementsByTagName(context.selectedElement);
			bldg.item(0).insertBefore(lod, bldg.item(0).getFirstChild());
		}
		*/
		
	}

}
