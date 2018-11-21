package k1s4g4.menus;

import static org.fusesource.jansi.Ansi.ansi;
import static org.fusesource.jansi.Ansi.Color.*;

import org.fusesource.jansi.Ansi.Color;
public class MenuTools {
	
	private static final Color[] COLORS= {BLACK,BLUE,CYAN,DEFAULT,GREEN,MAGENTA,RED,WHITE,YELLOW};
	private static final String myTab="    ";
	
	
	private static Color color(String color) {
		switch(color.toLowerCase()) {
			case "black":
				return BLACK;
			case "blue":
				return BLUE;
			case "cyan":
				return CYAN;
			case "default":
				return DEFAULT;
			case "green":
				return GREEN;
			case "magenta":
				return MAGENTA;
			case "red":
				return RED;
			case "white":
				return WHITE;
			case "yellow":
				return YELLOW;
			default:
				return null;	
		}
	}
	
	public static void set(String fg,String bg) {
		System.out.print(ansi().bg(color(fg)).fg(color(bg)));
	}
	public static void reset() {
		System.out.print(ansi().reset());
	}
	
	public static void print(int space,String text,String bg,String fg) {
		for(int i=0;i<space;i++) {
			System.out.print(" ");
		}
		System.out.print(ansi().bg(color(bg)).fg(color(fg)).a(text).reset());
	}
	
	public static void cursor(int x, int y) {
		System.out.println(ansi().cursor(x, y));
	}
	
	public static void printColors() {
		clear();
		for(int i=0;i<COLORS.length;i++) {
			for(int j=0;j<COLORS.length;j++) {
				System.out.println(ansi().bg(COLORS[i]).fg(COLORS[j]).a("something").reset());
			}
		}
	}
	
	
	public static void printUserTitle(String username) {
		System.out.println(ansi().cursor(3, 0));
	}
	
	public static void delay(int delay) {
		try {
            Thread.sleep(delay);
        }catch (InterruptedException ex) {
            // do nothing
        }

	}
	public static void clear() {
		System.out.println(ansi().eraseScreen());
		System.out.println(ansi().cursor(0, 0));
	}
	
	public static void mainTitle() {
		System.console().writer().println("Welcome to Individual Project");
		System.console().writer().println("");
	}
	public static int printTable(String[] headers,String[][] table) {
		int lineLength=0;
		int[] maximums=new int[headers.length];
		for(int i=0; i< headers.length ;i++) {
			
			maximums[i]=headers[i].length();
			
			for(int j=0;j<table.length;j++) {
				
				if(maximums[i]<table[j][i].length()) {
					maximums[i]=table[j][i].length();
				}
				
			}
			
		}
		
		//Print headers line
		String headersLine="";
		for(int j=0;j<headers.length;j++) {
			
				String spaces="";
				for(int k=0;k<maximums[j]-headers[j].length()-9;k++) {
					spaces+=" ";
				}
				headersLine+=headers[j]+spaces+myTab;	
			
		}
		String headersline=headersLine;
		lineLength=headersline.length();
		MenuTools.print(5, headersLine+"\n", "green", "green");
		MenuTools.print(5, headersLine+"\n", "green", "black");
		MenuTools.print(5, headersLine+"\n", "green", "green");
		
		//Print table lines
		
		for(int i=0;i<table.length;i++) {
			
			String line="";
			for(int j=0;j<headers.length;j++) {
				if(j==headers.length-1) {
					line+=table[i][j];
				}else {
					String spaces="";
					for(int k=0;k<maximums[j]-table[i][j].length();k++) {
						spaces+=" ";
					}
					line+=table[i][j]+spaces+myTab;	
					
				}
			}
			System.out.print("     ");
			System.out.println(ansi().render(line));
			
		}
		System.out.println("");
		return lineLength;
	}
}
