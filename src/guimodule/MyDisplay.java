package guimodule;

import processing.core.PApplet;

public class MyDisplay extends PApplet {
	
	@Override
	public void setup() {
		// TODO Auto-generated method stub
		super.setup();
		size(400, 400);
		background(200, 200, 200);
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		super.draw();
		fill(255, 255, 0);
		ellipse(200, 200, 390, 390);
		fill(0, 0, 0);
		ellipse(120, 130, 50, 70);
		ellipse(280, 130, 50, 70);
		
		noFill();
		arc(200, 280, 75, 75, 0, PI);

	}


}
