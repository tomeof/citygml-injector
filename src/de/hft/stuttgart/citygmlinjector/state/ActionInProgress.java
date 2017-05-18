package de.hft.stuttgart.citygmlinjector.state;

import java.io.File;

public class ActionInProgress extends State{

	@Override
	public void loadFile(Operations operations, File f) {
		operations.log("Cannot invoke while action is in progress");
	}

	@Override
	public void applyAction(Operations operations) {
		operations.log("Cannot invoke while action is in progress");
	}

	@Override
	public void saveFile(Operations operations) {
		operations.log("Cannot invoke while action is in progress");
	}
	
	

}
