/*
 * A menu for the tricks; The menu isa linked list.
 * 
 * Author: Joey Weidema // Jorik Noorda
 * 
 */

package menu;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import models.Robot;

public class TrickMenu {

	private final String valueFirstTrick = "VL";
	private final String nameFirstTrick = "Volg de Lijn";
	private final String valueSecondTrick = "MM";
	private final String nameSecondTrick = "Master Mind";
	private final String valueThirdTrick = "DR";
	private final String nameThirdTrick = "Draw";

	MenuItem menuItems;
	Robot robot;
	MenuItem currentItem;

	public TrickMenu(Robot robot) {
		super();
		this.robot = robot;
		MenuItem firstItem = new MenuItem(valueFirstTrick, nameFirstTrick);
		MenuItem secondItem = new MenuItem(valueSecondTrick, nameSecondTrick);
		MenuItem thirdItem = new MenuItem(valueThirdTrick, nameThirdTrick);

		// linking elements
		firstItem.setNext(secondItem);
		secondItem.setNext(thirdItem);
		// wrapping list
		firstItem.wrapList();

		// setting a wrapped list as menu item
		menuItems = firstItem;
		// setting initial item
		currentItem = firstItem;
	}

	public void drawMenu() {

		LCD.clear();
		LCD.drawString(((MenuItem) (currentItem.getPrevious())).trickName, 0, 1);
		LCD.drawString("    /\\", 0, 2);
		LCD.drawString(currentItem.trickName.toUpperCase(), 0, 3);
		LCD.drawString("    \\/", 0, 4);
		LCD.drawString(((MenuItem) (currentItem.getNext())).trickName, 0, 5);
	}

	public void runMenu() {

		drawMenu();

		int button = Button.waitForAnyPress();

		switch (button) {
		case Button.ID_DOWN:
			pressDown();
			break;
		case Button.ID_UP:
			pressUp();
			break;
		case Button.ID_ENTER:
			pressEnter();
			break;
		case Button.ID_ESCAPE:
			pressEsc();
			break;
		}
		runMenu();
	}

	public void pressUp() {

		currentItem = currentItem.getPrevious();

		runMenu();
	}

	public void pressDown() {

		currentItem = currentItem.getNext();

		runMenu();

	}

	public void pressEnter() {

		play(currentItem.getValue());
	}

	public void pressEsc() {

		LCD.drawString("Shutting down menu sequence", 0, 3);

		Sound.beepSequence();
	}

	public void play(String value) {

		LCD.clear();
		Delay.msDelay(1000);
		
		switch (value) {
		case valueFirstTrick:
			runVolglijn();
			break;
		case valueSecondTrick:
			runMasterMind();
			break;
		case valueThirdTrick:
			runDraw();
			break;
		}
	}

	public void runVolglijn() {
		System.out.printf("testing %s %s ", valueFirstTrick, nameFirstTrick);
	}

	public void runMasterMind() {
		System.out.printf("testing %s %s ", valueSecondTrick, nameSecondTrick);
	}

	public void runDraw() {
		//System.out.printf("testing %s %s ", valueThirdTrick, nameThirdTrick);
		// robot.getDraw().drawCircle();
		LCD.clear();
		LCD.drawString("Bye Felicia", 0, 0);
	}

}
