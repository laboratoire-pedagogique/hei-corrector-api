package mg.hei.heicorrectorapi.service.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.imageio.ImageIO;
import mg.hei.heicorrectorapi.model.FileContent;

public class FileUtils {
  private FileUtils() {

  }


  private static String getFileType(File file) {
    String fileName = file.getName();
    int dotIndex = fileName.lastIndexOf('.');
    return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
  }


  public static FileContent readContent(File file) throws IOException {
    FileContent fileContent = new FileContent();

    String fileType = getFileType(file).toLowerCase();
    switch (fileType) {
      case "jpg", "png", "gif", "bmp", "webp" -> {
        BufferedImage image = ImageIO.read(file);
        fileContent.setImage(image);
      }
      default -> {
        String content = new String(Files.readAllBytes(file.toPath()));
        fileContent.setContent(content);
      }
    }

    return fileContent;
  }

}
