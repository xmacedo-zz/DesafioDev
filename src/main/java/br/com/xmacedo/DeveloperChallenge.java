package br.com.xmacedo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import br.com.xmacedo.fileProcessor.service.FileProcessorService;

public class DeveloperChallenge {

    private String VALID_FILE_EXTENSION = ".dat";

    private void whatcherDirectory() throws IOException, InterruptedException {
        WatchService watchService
            = FileSystems.getDefault().newWatchService();

        FileProcessorService fileProcessorService = new FileProcessorService();
        Path inputPath = getInputPath();
        Path outputPath = getOutputPath();

        inputPath.register(watchService, ENTRY_CREATE);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                String filename = event.context().toString();

                if (VALID_FILE_EXTENSION.equalsIgnoreCase(getFileExtension(filename))) {
                    Path inputFilePath = inputPath.resolve((Path) event.context());
                    List<String> lines = Files.readAllLines(inputFilePath);
                    fileProcessorService.validateAndProcessorLinesOfFile(lines);

                    Path outputFilePath = outputPath.resolve(filename.replace(".dat", ".done.dat"));
                    generateFileAfterProcessing(outputFilePath, fileProcessorService);

                } else {
                    getRejectionMessageByExtension(filename);
                }
            }

            boolean valid = key.reset();
            if (!valid)
                break;
        }
    }

    private void generateFileAfterProcessing(Path outputFilePath, FileProcessorService service) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(outputFilePath)) {
            writer.write(service.getReportResult());
            System.out.println("Resultado do processamento em ".concat(outputFilePath.toString()));
        }
    }

    private Path getOutputPath() {
        return Paths.get(
            System.getProperty("user.home")
                .concat(File.separator)
                .concat("data")
                .concat(File.separator)
                .concat("out"));
    }

    private Path getInputPath() {
        return Paths.get(
            System.getProperty("user.home")
                .concat(File.separator)
                .concat("data")
                .concat(File.separator)
                .concat("in"));
    }

    private void getRejectionMessageByExtension(String filename) {
        System.out.println("Arquivo '" + filename + "', desconsiderado por nao ter a extensao " + VALID_FILE_EXTENSION + ".");
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.length() - 4);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        DeveloperChallenge app = new DeveloperChallenge();
        app.whatcherDirectory();
    }
}
