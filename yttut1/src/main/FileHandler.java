package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHandler {
	
	public static void makeFile(String args) {
		try {
			File scores = new File("scores/highscores.txt");
			scores.createNewFile();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(NumberFormatException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void readFile(String args, boolean won) {
		try {
			File scores = new File("scores/highscores.txt");
			Scanner scoresReader = new Scanner(scores);
			if(scoresReader.hasNextLine() == false) {
				System.out.println("nothing");
				writeFile("pleasework", won);
			}
			while(scoresReader.hasNextLine()) {
				String data = scoresReader.nextLine();
				System.out.println("this step has happened");
				System.out.println("data is: " + data);
				writeFile(data, won);
				
			}
			scoresReader.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(NumberFormatException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeFile(String args, boolean won) {
		try {
			FileWriter scoresWriter = new FileWriter("scores/highscores.txt"); 
			if(Game.isNumeric(args)) {
				int CHscore = Integer.parseInt(args);
				if(CHscore > HUD.totalScore) {
					System.out.println(CHscore);
					scoresWriter.write(CHscore + "");
				}else {
					System.out.println(HUD.totalScore);
					scoresWriter.write(HUD.totalScore + "");
					scoresWriter.write("\n" + HUD.damageTaken);
					scoresWriter.write("\n" + won);
				}
			}else scoresWriter.write(HUD.totalScore + "");
			scoresWriter.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(NumberFormatException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public static boolean booleanChecker(String str) {
		return "true".equals(str) || "false".equals(str);
	}
	

}
