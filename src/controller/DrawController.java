package controller;

import model.DrawModel;

public class DrawController {

	private DrawModel model;

	public DrawController(DrawModel model) {
		this.model = model;
	}

	public void controlUndo() {
		model.undo();

	}

	public void controlRedo() {
		model.redo();

	}

}
