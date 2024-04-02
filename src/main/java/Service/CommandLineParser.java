package Service;


import static Common.CommandsEnum.CSV_FILE_COMMAND;
import static Common.CommandsEnum.EMAIL_TEMPLATE_COMMAND;
import static Common.CommandsEnum.EMAIL_TYPE_COMMAND;
import static Common.CommandsEnum.LETTER_TEMPLATE_COMMAND;
import static Common.CommandsEnum.LETTER_TYPE_COMMAND;
import static Common.CommandsEnum.OUTPUT_DIR_COMMAND;
import static Common.Constants.CSV_FILE_PATH_PATTERN;
import static Common.Constants.DEFAULT_ERROR_MESSAGE;
import static Common.Constants.OUTPUT_DIR_PATTERN;
import static Common.Constants.TXT_FILE_PATH_PATTERN;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import Common.CommandsEnum;
import Exception.*;

public class CommandLineParser {
  /**
   * CommandLineParserV2 : A simple command-line parser class for processing email letter templates.
   * Parses command-line arguments and validates provided options.
   * Generates error messages for invalid commands or missing required arguments.
   */
  private String[] args;
  private Map<String, String> arguments;
  private static final Set<String> LEGAL_COMMANDS = Set.of(
      CommandsEnum.EMAIL_TEMPLATE_COMMAND.getCommandString(),
      CommandsEnum.LETTER_TEMPLATE_COMMAND.getCommandString(),
      CommandsEnum.OUTPUT_DIR_COMMAND.getCommandString(),
      CommandsEnum.CSV_FILE_COMMAND.getCommandString(),
      CommandsEnum.LETTER_TYPE_COMMAND.getCommandString(),
      CommandsEnum.EMAIL_TYPE_COMMAND.getCommandString()
  );


  /**
   * Constructs a CommandLineParserV2 object with given command-line arguments.
   * Parses the arguments and initializes the arguments map.
   * Prints error messages for invalid commands or missing required arguments.
   *
   * @param args Command-line arguments to parse
   */
  public CommandLineParser(String[] args) throws IOException {
    try {
      this.args = args;
      this.arguments = new HashMap<>();
      parseArguments();
    } catch (IllegalCommandException | InvalidCommandException e) {
      throw new IOException("Error： " + e.getMessage() + "\n" + DEFAULT_ERROR_MESSAGE);
    }
  }

  /**
   * Parses command-line arguments and populates the command option boolean flag.
   * Validates provided options and throws exceptions for illegal or missing commands.
   *
   * @throws IllegalCommandException If an illegal command is encountered
   * @throws InvalidCommandException If a command has invalid arguments or format
   */
  private void parseArguments() throws IllegalCommandException, InvalidCommandException {
    if (args == null || args.length == 0) {
      throw new IllegalCommandException("Empty input command line");
    }
    boolean isOutDir = false;
    boolean isCSVFile = false;
    boolean isEmail = false;
    boolean isEmailTemplate = false;
    boolean isLetter = false;
    boolean isLetterTemplate = false;

    for (String arg : args) {
      if (arg.startsWith("--")) {
        validateLegalCommand(arg);
        CommandsEnum command = CommandsEnum.fromCommandString(arg);
        switch (command) {
          case EMAIL_TYPE_COMMAND -> isEmail = true;
          case EMAIL_TEMPLATE_COMMAND -> isEmailTemplate = true;
          case LETTER_TYPE_COMMAND -> isLetter = true;
          case LETTER_TEMPLATE_COMMAND -> isLetterTemplate = true;
          case OUTPUT_DIR_COMMAND -> isOutDir = true;
          case CSV_FILE_COMMAND -> isCSVFile = true;
        }
      }
    }

    for (int i = 0; i < args.length; i++) {
      String key = args[i];
      String value = null;

      if (key.startsWith("--")) {
        if (i < args.length - 1 && !args[i + 1].startsWith("--")) {
          value = args[i + 1];
          i++;
        }
        parseKeyAndValue(key, value);
      }
    }
    validateCommandOptions(isEmail, isEmailTemplate, isLetter, isLetterTemplate,
        isOutDir, isCSVFile);
  }

  /**
   * Parses a command and its value, and adds it to the arguments map.
   * Throws exceptions for invalid or missing command values.
   *
   * @param key   The command key
   * @param value The command value
   * @throws InvalidCommandException If the command value is invalid or missing
   */
  private void parseKeyAndValue(String key, String value) throws InvalidCommandException {
    CommandsEnum command = CommandsEnum.fromCommandString(key);
    switch (command) {
      case EMAIL_TYPE_COMMAND, LETTER_TYPE_COMMAND -> arguments.put(key, "true");
      case EMAIL_TEMPLATE_COMMAND -> {
        if (value == null) {
          throw new InvalidCommandException(
              EMAIL_TYPE_COMMAND + " provided but no " + EMAIL_TEMPLATE_COMMAND + " was given.");
        }
        if (isInValidTemplateFormat(value)) {
          throw new InvalidCommandException(
              "Input email template file path should be in the right format");
        }
        arguments.put(key, value);
      }
      case LETTER_TEMPLATE_COMMAND -> {
        if (value == null) {
          throw new InvalidCommandException(
              LETTER_TEMPLATE_COMMAND + " provided but no " + LETTER_TYPE_COMMAND + " was given.");
        }
        if (isInValidTemplateFormat(value)) {
          throw new InvalidCommandException(
              "Input letter template file path should be in the right format");
        }
        arguments.put(key, value);
      }
      case OUTPUT_DIR_COMMAND -> {
        if (value == null) {
          throw new InvalidCommandException(OUTPUT_DIR_COMMAND
              + " provided but no valid output directory was given.");
        }
        if (!isValidOutputDirPath(value)) {
          throw new InvalidCommandException(OUTPUT_DIR_COMMAND +
              " provided but output directory should be in the right format.");
        }
        arguments.put(key, value);
      }
      case CSV_FILE_COMMAND -> {
        if (value == null) {
          throw new InvalidCommandException(CSV_FILE_COMMAND +
              " provided but no valid CSV file path was given.");
        }
        if (!isValidCSVFilePath(value)) {
          throw new InvalidCommandException("Valid CSV file directory provided but no valid "
              + CSV_FILE_COMMAND + " was given.");
        }
        arguments.put(key, value);
      }
    }
  }

  /**
   * Validates if a given command is legal:
   * if it is from the LEGAL_COMMNAD_SET
   * if the command starts with "--" with no empty space
   *
   * @param key The command to validate
   */
  private void validateLegalCommand(String key) throws IllegalCommandException {
    if (!LEGAL_COMMANDS.contains(key)) {
      throw new IllegalCommandException("Not a legal command");
    }
  }

  /**
   * Validates the overall command options.
   * Throws exceptions for missing or conflicting options.
   *
   * @param isEmail           Flag indicating if email type command is provided
   * @param isEmailTemplate   Flag indicating if email template command is provided
   * @param isLetter          Flag indicating if letter type command is provided
   * @param isLetterTemplate  Flag indicating if letter template command is provided
   * @param isOutDir          Flag indicating if output directory command is provided
   * @param isCSVFile         Flag indicating if CSV file command is provided
   * @throws IllegalCommandException If there is an illegal combination of commands
   * @throws InvalidCommandException If required commands are missing or have invalid combinations
   */
  private void validateCommandOptions(boolean isEmail, boolean isEmailTemplate, boolean isLetter,
      boolean isLetterTemplate, boolean isOutDir, boolean isCSVFile)
      throws IllegalCommandException, InvalidCommandException {
    if (!isEmail && !isLetter) {
      throw new InvalidCommandException(
          "Need to provide at least one type of file to generate with commands "
              + EMAIL_TYPE_COMMAND + " and " + LETTER_TYPE_COMMAND);
    }
    if (isEmail && !isEmailTemplate) {
      throw new IllegalCommandException(
          EMAIL_TYPE_COMMAND + " provided but no " + EMAIL_TEMPLATE_COMMAND
              + " was given.");
    } else if (!isEmail && isEmailTemplate) {
      throw new IllegalCommandException(
          EMAIL_TEMPLATE_COMMAND + " provided but no " + EMAIL_TYPE_COMMAND
              + " was given.");
    }
    if (isLetter && !isLetterTemplate) {
      throw new InvalidCommandException(
          LETTER_TYPE_COMMAND + " provided but no " + LETTER_TEMPLATE_COMMAND
              + " was given.");
    } else if (!isLetter && isLetterTemplate) {
      throw new InvalidCommandException(
          LETTER_TEMPLATE_COMMAND + " provided but no " + LETTER_TYPE_COMMAND
              + " was given.");
    }
    if (!isOutDir) {
      throw new InvalidCommandException(OUTPUT_DIR_COMMAND + " should be provided.");
    }
    if (!isCSVFile) {
      throw new IllegalCommandException(CSV_FILE_COMMAND + " should be provided.");
    }
  }

  /**
   * Validates if the provided output directory path is in the correct format.
   * output path should be the folder path, with "/" at the end
   *
   * @param value The output directory path
   * @return True if the path is valid, false otherwise
   */
  private boolean isValidOutputDirPath(String value) {
    return Pattern.matches(OUTPUT_DIR_PATTERN, value);
  }

  /**
   * Validates if the provided template file path has a valid format.
   * valid file path should be with ".txt" as suffix
   *
   * @param value The template file path
   * @return True if the format is valid, false otherwise
   */
  private boolean isInValidTemplateFormat(String value) {
    return !Pattern.matches(TXT_FILE_PATH_PATTERN, value);
  }

  /**
   * Validates if the provided CSV file path has a valid format.
   * valid csv file should have ".csv" as suffix
   *
   * @param value The CSV file path
   * @return True if the format is valid, false otherwise
   */
  private boolean isValidCSVFilePath(String value) {
    return Pattern.matches(CSV_FILE_PATH_PATTERN, value);
  }

  /**
   * Retrieves the parsed arguments map.
   *
   * @return Map containing the parsed arguments
   */
  public Map<String, String> getArguments() {
    return arguments;
  }

  /**
   * Main method to test the command-line parser integrity.
   * should delete in tbe final release
   */
  public static void main(String[] args) {
    try {
      CommandLineParser parser = new CommandLineParser(args);
      Map<String, String> arguments = parser.getArguments();
      printArguments(arguments, parser);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private static void printArguments(Map<String, String> arguments, CommandLineParser parser) {
    if(arguments != null){
      System.out.println("\n" + "Test Parsed Arguments:");
      for (Map.Entry<String, String> entry : arguments.entrySet()) {
        System.out.println(entry.getKey() + ": " + entry.getValue());
      }
    }
  }
}