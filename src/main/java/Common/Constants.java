package Common;

import java.util.regex.Pattern;

public class Constants {
  public static final String TXT_FILE_PATH_PATTERN = "([A-Z]:)?([\\.\\\\/]?[A-Za-z0-9_-]*[\\.\\\\/])*[A-Za-z0-9_-]*\\.txt";
  public static final String CSV_FILE_PATH_PATTERN = "([A-Z]:)?([\\.\\\\/]?[A-Za-z0-9_-]*[\\.\\\\/])*[A-Za-z0-9_-]*\\.csv";
  public static final String OUTPUT_DIR_PATTERN = "([A-Z]:)?([\\.\\\\/]?[A-Za-z0-9_-]*[\\.\\\\/])*";

  public static final Pattern CSV_PATTERN = Pattern.compile("\"([^\"]*)\"");
  public final static String DEFAULT_ERROR_MESSAGE = "\nUsage：" +
      "\n--email Generate email messages. If this option is provided, then --email-template must also be provided." +
      "\n--email-template <path/to/file> A filename for the email template. --letter Generate letters. If this option is provided, then --letter-template must also be provided." +
      "\n--letter-template <path/to/file> A filename for the letter template. --output-dir <path/to/folder> The folder to store all generated files. This option is required." +
      "\n--csv-file <path/to/folder> The CSV file to process. This option is required." +
      "\n\nExamples：" +
      "\n--email --email-template email-template.txt --output-dir emails --csv-file customer.csv" +
      "\n--letter --letter-template letter-template.txt --output-dir letters --csv-file customer.csv";

}
