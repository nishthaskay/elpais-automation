package utils;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageDownloader {
    public static void downloadImage(String url, String path) {
        try (InputStream in = new URL(url).openStream()) {
            Files.copy(in, Paths.get(path));
        } catch (Exception e) {
            System.out.println("Image download failed: " + e.getMessage());
        }
    }
}
