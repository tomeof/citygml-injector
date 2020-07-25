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
import de.hft.stuttgart.citygmlinjector.contract.IActionHandle;


public class ActionHandlerFactory {
	
	private static interface IActionHandlerCommand {
		public IActionHandle make();
	}
	
	private HashMap<String, IActionHandlerCommand> map = new HashMap<String, IActionHandlerCommand>();
	
	public ActionHandlerFactory() {

		
		map.put("bldg:Building", new IActionHandlerCommand() {
			public IActionHandle make() {
				return new BuildingHandler();
			}
		});
		
		map.put("bldg:BuildingPart", new IActionHandlerCommand() {
			public IActionHandle make() {
				return new BuildingPartHandler();
			}
		});
		
	}
	
	public IActionHandle makeActionHandler(String command) {
		IActionHandlerCommand actionCommand = map.get(command);

		if (actionCommand != null)
			return actionCommand.make();
		else {
			return null;
		}

	}
	
	

}
