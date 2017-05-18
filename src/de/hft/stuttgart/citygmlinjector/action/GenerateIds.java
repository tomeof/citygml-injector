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

import java.util.UUID;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.hft.stuttgart.citygmlinjector.contract.IAction;
import de.hft.stuttgart.citygmlinjector.state.Context;
import de.hft.stuttgart.citygmlinjector.values.Attributes;

public class GenerateIds extends BaseAction implements IAction{
	
	public GenerateIds(Context context) {
		super(context);
	}

	@Override
	public void applyAction() {
		NodeList selectedCitygmlNodes = context.document.getElementsByTagName(context.selectedElement);
		for (int i=0, leni = selectedCitygmlNodes.getLength(); i<leni; i++) {
			Element element = (Element) selectedCitygmlNodes.item(i);
		    if (!element.hasAttribute(Attributes.GMLID)) {
		    	String newId = "uuid_" + UUID.randomUUID();
		    	element.setAttribute(Attributes.GMLID, newId);
			}
		}
	}
}
