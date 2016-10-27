package main;

import view.guiview.DrawGUIView;
import model.DrawModel;
import controller.DrawController;

public class PackageMain {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// create Model
		DrawModel model = new DrawModel();

		// Create controller
		DrawController controller = new DrawController(model);

		// Create View (GUI)
		DrawGUIView view = new DrawGUIView(model, controller);
		view.mainFrame.show();

	}

}
