package Service;

import java.util.List;
import java.util.Map;

import Util.CSVReader;
import static Common.CommandsEnum.*;

public class FileGenerationRunner {

  public void runTemplateGeneration(String[] args) {
    try {
      CommandLineParser commandLineParser = new CommandLineParser(args);
      Map<String, String> cli = commandLineParser.getArguments();

      CSVReader csvReader = new CSVReader();
      List<Map<String, String>> csvData = csvReader.readCSV(cli.get(CSV_FILE_COMMAND.getCommandString()));

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
