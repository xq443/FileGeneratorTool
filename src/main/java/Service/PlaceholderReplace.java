package Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import Util.FileOutputWriter;
import Util.TextReader;

public class PlaceholderReplace {
  private final String emailTemplatePath;
  private final String letterTemplatePath;
  private final List<Map<String, String>> csvData;
  private final String outputDir;
  private final String isEmail;
  private final String isLetter;
  private boolean emailAllSuccess = false;
  private boolean letterAllSuccess = false;


  public PlaceholderReplace(String isEmail, String emailTemplatePath, String isLetter,
      String letterTemplatePath, String outputDir, List<Map<String, String>> csvData) {
    this.emailTemplatePath = emailTemplatePath;
    this.letterTemplatePath = letterTemplatePath;
    this.csvData = csvData;
    this.outputDir = outputDir;
    this.isEmail = isEmail;
    this.isLetter = isLetter;
  }
  public void generateFiles()  {
    if (isEmail != null) {
      try{
        generateEmails();
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
    }
    if (isLetter != null) {
      try {
        generateLetters();
        //System.out.println("Letters are all successfully generated.");
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
    }
  }
  public void generateEmails() throws IOException {
    try {
      String emailTemplate = TextReader.readTextFileToString(emailTemplatePath);
      for (Map<String, String> rowData : csvData) {
        String generatedEmail = replaceEmailPlaceholders(emailTemplate, rowData);
        String recipientId = rowData.get("first_name") + "_" + rowData.get("last_name") + "_"
            + "email";
        FileOutputWriter.writeToFile(outputDir, generatedEmail, recipientId);
      }
    } catch (Exception e){
      throw new IOException("Error generating email: " + e.getMessage());
    }
  }

  public void generateLetters() throws IOException {
    try {
      String letterTemplate = TextReader.readTextFileToString(letterTemplatePath);
      for (Map<String, String> rowData : csvData) {
        String generatedLetter = replaceLetterPlaceholders(letterTemplate, rowData);
        String recipientId = rowData.get("first_name") + "_" + rowData.get("last_name") + "_"
            + "letter";
        FileOutputWriter.writeToFile(outputDir, generatedLetter, recipientId);
      }
    } catch (Exception e) {
      throw new IOException("Error generating letter: " + e.getMessage());
    }
  }

  private String replaceEmailPlaceholders(String template, Map<String, String> rowData) {
    String firstName = rowData.get("first_name");
    String lastName = rowData.get("last_name");
    String email = rowData.get("email");

    template = template.replace("[[first_name]]", firstName);
    template = template.replace("[[last_name]]", lastName);
    template = template.replace("[[email]]", email);

    return template;
  }
  private String replaceLetterPlaceholders(String template, Map<String, String> rowData) {
    String firstName = rowData.get("first_name");
    String lastName = rowData.get("last_name");
    String email = rowData.get("email");
    String address = rowData.get("address");
    String city = rowData.get("city");
    String county = rowData.get("county");
    String state = rowData.get("state");
    String zip = rowData.get("zip");

    // Replace placeholders with actual values
    template = template.replace("[[first_name]]", firstName);
    template = template.replace("[[last_name]]", lastName);
    template = template.replace("[[email]]", email);
    template = template.replace("[[address]]", address);
    template = template.replace("[[city]]", city);
    template = template.replace("[[county]]", county);
    template = template.replace("[[state]]", state);
    template = template.replace("[[zip]]", zip);

    return template;
  }
}
