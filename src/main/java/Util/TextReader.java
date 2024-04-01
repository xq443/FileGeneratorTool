package Util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import Exception.InvalidCommandException;

public class TextReader {
  public static String readTextFileToString(String filePath) {
    StringBuilder contentBuilder = new StringBuilder();
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(filePath));
      String line;
      while ((line = reader.readLine()) != null) {
        contentBuilder.append(line).append("\n");
      }
    } catch (FileNotFoundException e) {
      System.out.println("The template text file was not found : " + e.getMessage());
    } catch (IOException e) {
      System.err.println("Error reading text file: " + e.getMessage());
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
      } catch (IOException e) {
        System.err.println("Error closing file: " + e.getMessage());
      }
    }
    return contentBuilder.toString();
  }

  public static void main(String[] args) throws InvalidCommandException {
    if(args.length != 1) {
      throw new InvalidCommandException(
          "Need to provide valid template file path.");
    }
    try{
      String fileContent = readTextFileToString(args[0]);
      System.out.println("File content:");
      System.out.println(fileContent);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}