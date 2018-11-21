package k1s4g4;

import org.fusesource.jansi.AnsiConsole;

import k1s4g4.data.App;
import k1s4g4.data.tools.FileManager;
import k1s4g4.menus.LoginMenu;

public class Main {
	public static void main(String[] args) {
		App.app();
		App.dbManager();
		FileManager.start();
		AnsiConsole.systemInstall();
		LoginMenu.start();
		App.dbManager().close();
	}
	
	
	
}
