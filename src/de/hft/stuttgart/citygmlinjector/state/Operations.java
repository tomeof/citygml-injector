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
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.hft.stuttgart.citygmlinjector.action.ActionFactory;
import de.hft.stuttgart.citygmlinjector.contract.IAction;
import de.hft.stuttgart.citygmlinjector.exception.FileNotCityGmlException;
import de.hft.stuttgart.citygmlinjector.validate.IsCityGml;
import de.hft.stuttgart.citygmlinjector.validate.SelectionIsValid;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Operations {

	private static final String DASH = "-";
	private State state;
	private Context context;
	
	public Operations(Context context){
		this.context = context;
		this.state = new Uninitialised();
		wireGui();
	}
	
	public void loadFile(File f){
		this.state.loadFile(this, f);
	}
	
	public void log(String message){
		this.state.log(this, message);
	}
	
	public void applyAction(){
		this.state.applyAction(this);
	}

	public void saveFile(){
		this.state.saveFile(this);
	}
	
	void setState(State state) {
		this.state = state;
	}
	
	void invokeLoadFile(File f) throws FileNotCityGmlException, ParserConfigurationException, SAXException, IOException{
		
		context.reset();
		context.gui.citygmlList.getItems().clear();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		dbf.setNamespaceAware(true);
		DocumentBuilder db = null;
		db = dbf.newDocumentBuilder();
		
		context.loadedFile = f;
		Document doc = db.parse(f);
		context.document = doc;
		
		Element root = doc.getDocumentElement();
		context.rootNodeName = root.getNodeName();
		IsCityGml isCityGmlRule = new IsCityGml(context);
		if (!context.validator.validate(isCityGmlRule)) {
			context.document = null;
			throw new FileNotCityGmlException("File " + f.getName() + " seems to be not a CityGML file");
		}
		
		NodeList citygmlNodes = doc.getElementsByTagName("*");
		SortedSet<String> uniqueCitygmlNodes = new TreeSet<>();
		for (int i=0, leni = citygmlNodes.getLength(); i<leni; i++) {
		    Element element = (Element) citygmlNodes.item(i);
		    uniqueCitygmlNodes.add(element.getNodeName());
		}
		for (String s : uniqueCitygmlNodes) {
		    context.gui.citygmlList.getItems().add(s);
		}
		
	}

	void invokeLog(String message){
		context.gui.logField.appendText(message+System.getProperty("line.separator"));
	}
	
	void invokeApplyAction(){
		String command = context.selectedAction + DASH + context.selectedAttribute;
		ActionFactory factory = new ActionFactory(context);
		IAction action = factory.makeAction(command);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(context.selectedAction);
		stringBuilder.append(" ");
		stringBuilder.append(context.selectedAttribute);
		stringBuilder.append(" for ");
		stringBuilder.append(context.selectedElement);
		stringBuilder.append("...");
		log(stringBuilder.toString());
		context.gui.progressBar.setProgress(-1);
		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				action.applyAction();
				return null;
			}
			
			@Override
			protected void succeeded() {
				super.succeeded();
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("Applied action ");
				stringBuilder.append(context.selectedAction);
				stringBuilder.append(" ");
				stringBuilder.append(context.selectedAttribute);
				stringBuilder.append(" to CityGML element ");
				stringBuilder.append(context.selectedElement);
				log(stringBuilder.toString());
				context.gui.progressBar.setProgress(0.0);
				setState(new AllElementsSelected());
			}
			
		};
		
		Thread t = new Thread(task);
		t.start();
	}
	
	void invokeSaveFile() throws TransformerFactoryConfigurationError, TransformerException{
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		Result output = new StreamResult(context.loadedFile);
		Source input = new DOMSource(context.document);
		transformer.transform(input, output);
		log("Saved " + context.loadedFile.getAbsolutePath());
	}
	
	
	private void wireGui(){
			
			context.gui.citygmlList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					context.selectedElement = newValue;
					validateSelection();
				}
	
			});
			
			context.gui.actionsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					context.selectedAction = newValue;
					validateSelection();
				}
			});
			
			context.gui.attributeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					context.selectedAttribute = newValue;
					validateSelection();
				}
			});
			
			context.gui.openButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					FileChooser fileChooser = new FileChooser();
					 fileChooser.setTitle("Open CityGML File");
					 fileChooser.getExtensionFilters().addAll(
					         new ExtensionFilter("CityGML Files", "*.gml", "*.xml"));
					 File selectedFile = fileChooser.showOpenDialog(context.gui.stage);
					 
					 if (selectedFile != null) {
						 loadFile(selectedFile);
					 }
				}
			});
			
			context.gui.applyButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					applyAction();
				}
			});
			
			context.gui.saveButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					saveFile();
				}
			});
			
		}

	private void validateSelection() {
		SelectionIsValid selectionIsValid = new SelectionIsValid(context);
		if (context.validator.validate(selectionIsValid)) {
			setState(new AllElementsSelected());
		}
	}
	
}
