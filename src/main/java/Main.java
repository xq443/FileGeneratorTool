import static Enum.Commands.CSV_FILE_COMMAND;
import static Enum.Commands.EMAIL_TEMPLATE_COMMAND;
import static Enum.Commands.EMAIL_TYPE_COMMAND;
import static Enum.Commands.LETTER_TEMPLATE_COMMAND;
import static Enum.Commands.LETTER_TYPE_COMMAND;
import static Enum.Commands.OUTPUT_DIR_COMMAND;

import java.util.List;
import java.util.Map;
import Processor.CommandLineParser;
import Processor.PlaceholderReplace;
import Util.CSVReader;

public class Main {
  public static void main(String[] args) {
    try{
      CommandLineParser commandLineParser = new CommandLineParser(args);
      Map<String, String> cli = commandLineParser.getArguments();
      CSVReader csvReader = new CSVReader();
      List<Map<String, String>> csvData = csvReader.readCSV(cli.get(
          CSV_FILE_COMMAND.getCommandString()));
      PlaceholderReplace templateGenerator = new PlaceholderReplace(
          cli.get(EMAIL_TYPE_COMMAND.getCommandString()),
          cli.get(EMAIL_TEMPLATE_COMMAND.getCommandString()),
          cli.get(LETTER_TYPE_COMMAND.getCommandString()),
          cli.get(LETTER_TEMPLATE_COMMAND.getCommandString()),
          cli.get(OUTPUT_DIR_COMMAND.getCommandString()),
          csvData);
      templateGenerator.generateFiles();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
