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

import java.util.HashMap;

import de.hft.stuttgart.citygmlinjector.contract.IAction;
import de.hft.stuttgart.citygmlinjector.state.Context;
import de.hft.stuttgart.citygmlinjector.values.Actions;
import de.hft.stuttgart.citygmlinjector.values.Attributes;

public class ActionFactory {
	
	private static final String DASH = "-";
	private Context context;
	private static interface IActionCommand {
		public IAction make();
	}
	
	private HashMap<String, IActionCommand> map = new HashMap<String, IActionCommand>();
	
	public ActionFactory(final Context c) {
		this.context = c;
		
		map.put(Actions.GENERATE+DASH+Attributes.GMLID, new IActionCommand() {
			public IAction make() {
				return new GenerateIds(context);
			}
		});
		
		map.put(Actions.GENERATE+DASH+Attributes.XLINK, new IActionCommand() {
			public IAction make() {
				return new GenerateXlinks(context);
			}
		});
		
		map.put(Actions.REMOVE+DASH+Attributes.GMLID, new IActionCommand() {
			public IAction make() {
				return new RemoveAttribute(context);
			}
		});
		
		map.put(Actions.REMOVE+DASH+Attributes.XLINK, new IActionCommand() {
			public IAction make() {
				return new RemoveAttribute(context);
			}
		});
		
	}
	
	public IAction makeAction(String command) {
		IActionCommand actionCommand = map.get(command);

		if (actionCommand != null)
			return actionCommand.make();
		else {
			return null;
		}

	}
	
	

}
