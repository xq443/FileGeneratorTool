import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

  // Define CSV_PATTERN as a regular expression Pattern
  public static final Pattern CSV_PATTERN = Pattern.compile("\"([^\"]*)\"");

  private String[] parseCSVLine(String line) {
    List<String> values = new ArrayList<>();
    Matcher matcher = CSV_PATTERN.matcher(line);
    while (matcher.find()) {
      values.add(matcher.group(1));
    }
    return values.toArray(new String[0]);
  }

  public static void main(String[] args) {
    String exampleLine = "\"John\",\"Doe\",\"30\",\"Male\"";
    test csvReader = new test();
    String[] parsedValues = csvReader.parseCSVLine(exampleLine);
    System.out.println("Parsed CSV line:");
    for (String value : parsedValues) {
      System.out.println(value);
      String input = "\"Benton, John B Jr\"";
      Pattern pattern = Pattern.compile("\"([^\"]*)\"");
      Matcher matcher = pattern.matcher(input);

      if (matcher.find()) {
        System.out.println("Group 0: " + matcher.group(0)); // Entire matched substring
        System.out.println("Group 1: " + matcher.group(1)); // Substring matched by capturing group
        //System.out.println("Group 1: " + matcher.group(2));
      }
//    String org = "07:23:21PM";
//    Pattern pattern = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2})");
//    Matcher matcher = pattern.matcher(org);
//    if (matcher.find()) {
//      System.out.println(matcher.group(1));
//      System.out.println(matcher.group(2));
//      System.out.println(matcher.group(3));
//    }
//      Path regex = TXT_FILE_PATH_PATTERN;
//      String text = "/User/cathyqin/Desktop/email-template.txt";
//
//      Pattern pattern11 = Pattern.compile(regex.getPattern());
//      Matcher matcher11 = pattern11.matcher(text);
//
//      if (matcher11.matches()) {
//        System.out.println("Match found: " + matcher11.group());
//      } else {
//        System.out.println("No match found.");
//      }
//    }
    }
  }
}