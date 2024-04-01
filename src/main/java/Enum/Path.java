package Enum;

public enum Path {
  TXT_FILE_PATH_PATTERN("([A-Z]:)?([\\.\\\\/]?[A-Za-z0-9_-]*[\\.\\\\/])*[A-Za-z0-9_-]*\\.txt"),
  CSV_FILE_PATH_PATTERN("([A-Z]:)?([\\.\\\\/]?[A-Za-z0-9_-]*[\\.\\\\/])*[A-Za-z0-9_-]*\\.csv"),
  OUTPUT_DIR_PATTERN("([A-Z]:)?([\\.\\\\/]?[A-Za-z0-9_-]*[\\.\\\\/])*");

  private final String pattern;

  Path(String pattern) {
    this.pattern = pattern;
  }
  public String getPattern() {
    return pattern;
  }

}
