package Common;

public enum CommandsEnum {
  EMAIL_TYPE_COMMAND("--email"),
  EMAIL_TEMPLATE_COMMAND("--email-template"),
  LETTER_TYPE_COMMAND("--letter"),
  LETTER_TEMPLATE_COMMAND("--letter-template"),
  OUTPUT_DIR_COMMAND("--output-dir"),
  CSV_FILE_COMMAND("--csv-file");

  private final String commandString;

  CommandsEnum(String commandString) {
    this.commandString = commandString;
  }

  public static CommandsEnum fromCommandString(String commandString) {
    for (CommandsEnum command : CommandsEnum.values()) {
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
