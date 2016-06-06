package main;

import timeTableController.TimeTableController;
import userController.UserController;
import view.MainFrame;

import javax.swing.*;

/**
 * Cette classe est la classe principale de l'application. 
 * Elle crée les contrôleurs d'utilisateurs et d'emplois de temps ainsi que l'interface graphique (MainFrame).
 * 
 * @author Jose Mennesson
 * @version 04/2016
 */

//TODO Classe à ne pas modifier

public class Main {
	
	/**
	 * Crée l'interface graphique
	 * 
	 * @param userController
	 * 				Le controleur des utilisateurs
	 * 
	 * @param tTController
	 * 				Le controleur d'emplois du temps
	 */
	private static void createAndShowUI(UserController userController,TimeTableController tTController) {
		new MainFrame(userController,tTController);
	}
	/**
	 * Fonction principale du programme
	 * 
	 * @param args
	 * 		Arguments donnés en entrée du programme 
	 *
	 */
	public static void main(String args[]) {

        // Parce que c'est quand même mieux que le Look and Feel Metal
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String userfile = "userDB.xml";
		final String tTfile = "timeTableDB.xml";
		final UserController userController = new UserController(userfile);
		final TimeTableController tTController = new TimeTableController(tTfile);
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				createAndShowUI(userController, tTController);
			}
		});
	}
}
