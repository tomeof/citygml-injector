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

package de.hft.stuttgart.citygmlinjector.state;

import java.io.File;

import org.w3c.dom.Document;

import de.hft.stuttgart.citygmlinjector.validate.Validator;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Context {

	public Context(){
		this.gui = new Gui();
	}
	
	public String selectedElement;
	public String selectedAction;
	public String selectedAttribute;
	public Validator validator;
	public String rootNodeName;
	public Document document;
	public File fileToLoad;
	public File loadedFile;
	public Gui gui;
	
	public void reset(){
		selectedElement = null;
		rootNodeName = null;
		document = null;
		loadedFile = null;
	}
	
	public class Gui {
		public Stage stage;
	 	public ListView<String> citygmlList; 
	 	public ListView<String> actionsList; 
	 	public ListView<String> attributeList;
	 	public TextArea logField;
	 	public Button openButton;
	 	public Button applyButton;
	 	public Button saveButton;
	}
	
	
}
