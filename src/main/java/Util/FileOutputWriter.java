package Util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileOutputWriter{
  public static void writeToFile(String outputDir, String generatedFile, String recipient) {
    String fileName = outputDir + "/" + recipient + ".txt";
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      writer.write(generatedFile);
    } catch (IOException e) {
      System.err.println("Error writing email to file: " + e.getMessage());
    }
  }
}
