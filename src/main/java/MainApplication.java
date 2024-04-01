import Generator.FileGenerationRunner;

public class MainApplication {
  public static void main(String[] args) {
    FileGenerationRunner runner = new FileGenerationRunner();
    runner.runTemplateGeneration(args);
  }
}