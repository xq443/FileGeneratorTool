package Enum;

public enum Commands {
  EMAIL_TYPE_COMMAND("--email"),
  EMAIL_TEMPLATE_COMMAND("--email-template"),
  LETTER_TYPE_COMMAND("--letter"),
  LETTER_TEMPLATE_COMMAND("--letter-template"),
  OUTPUT_DIR_COMMAND("--output-dir"),
  CSV_FILE_COMMAND("--csv-file");

  private final String commandString;

  Commands(String commandString) {
    this.commandString = commandString;
  }

  public static Commands fromCommandString(String commandString) {
    for (Commands command : Commands.values()) {
      if (command.getCommandString().equals(commandString)) {
        return command;
      }
    }
    throw new IllegalArgumentException("No enum constant found for command string: " + commandString);
  }

  public String getCommandString() {
    return commandString;
  }
}
