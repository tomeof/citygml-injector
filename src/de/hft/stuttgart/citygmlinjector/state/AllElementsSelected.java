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
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.xml.sax.SAXException;

import de.hft.stuttgart.citygmlinjector.exception.FileNotCityGmlException;

public class AllElementsSelected extends State{
	
	@Override
	public void loadFile(Operations operations, File f) {
		try {
			operations.invokeLoadFile(f);
			operations.setState(new FileLoaded());
			operations.invokeLog("Loaded " + f.getAbsolutePath());
			operations.setState(new FileLoaded());
		} catch (FileNotCityGmlException | IOException | ParserConfigurationException | SAXException e) {
			operations.invokeLog(e.getMessage());
		}
	}
	
	@Override
	public void applyAction(Operations operations) {
		operations.invokeApplyAction();
	}
	
	@Override
	public void saveFile(Operations operations) {
		try {
			operations.invokeSaveFile();
		} catch (TransformerFactoryConfigurationError | TransformerException e) {
			operations.log(e.getMessage());
		}
	}

}
