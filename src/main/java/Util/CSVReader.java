package Util;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A CSV reader class for reading CSV files and converting them into a list of maps.
 * Each map represents a row in the CSV file, with column names as keys and corresponding values.
 */

public class CSVReader {
  /**
   * Reads a CSV file and converts its data into a list of maps.
   *
   * @param filePath The path to the CSV file
   * @return List of maps representing CSV data
   * @throws IOException If an I/O error occurs while reading the file
   */

  public static final Pattern CSV_PATTERN = Pattern.compile("\"([^\"]*)\"");
  public List<Map<String, String>> readCSV(String filePath) {
    List<Map<String, String>> csvData = new ArrayList<>();
    BufferedReader reader = null; // declare reader outside try block
    try {
      reader = new BufferedReader(new FileReader(filePath));
      String headerLine = reader.readLine();
      if (headerLine != null) {
        String[] headers = parseCSVLine(headerLine);
        String line;
        while ((line = reader.readLine()) != null) {
          String[] values = parseCSVLine(line);
          if (values.length == headers.length) {
            Map<String, String> rowData = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
              //String header = headers[i].replace("\"", "");
              //String value = values[i].replace("\"", "");
              rowData.put(headers[i], values[i]);
            }
            csvData.add(rowData);
          }
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("The CSV file was not found : " + e.getMessage());
    } catch (IOException e) {
      System.err.println("Error reading CSV file: " + e.getMessage());
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          System.err.println("Error closing file reader: " + e.getMessage());
        }
      }
    }
    return csvData;
  }
  private String[] parseCSVLine(String line) {
    List<String> values = new ArrayList<>();
    Matcher matcher = CSV_PATTERN.matcher(line);
    while (matcher.find()) {
      values.add(matcher.group(1));
    }
    return values.toArray(new String[0]);
  }
  public static void main(String[] args) {
//    String absoluteFilePath = "/Users/cathyqin/Desktop/insurance-company-members.csv";
//    String relativePath = "src/resources/insurance-company-members.csv";
    try{
      if (args.length != 1) {
        throw new IllegalArgumentException(
            "Need to provide valid insurance-company-members.csv file path.");
      }
      CSVReader csvReader = new CSVReader();
      List<Map<String, String>> csvData = csvReader.readCSV(args[0]);

      for (Map<String, String> rowData : csvData) {

          System.out.println("Test Row:");
          for (Map.Entry<String, String> entry : rowData.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
          }
          System.out.println();

      }
    } catch (Exception e){
      System.err.println(e.getMessage());
    }
  }
}
//Row:
//"company_name": "Feltz Printing Service"
//"address": "639 Main St"
//"state": "AK"
//"email": "lpaprocki@hotmail.com"
//"first_name": "Lenna"
//"phone1": "907-385-4412"
//"phone2": "907-921-2010"
//"web": "http://www.feltzprintingservice.com"
//"county": "Anchorage"
//"last_name": "Paprocki"
//"zip": "99501"
//"city": "Anchorage"