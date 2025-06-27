package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ImageDownloader;
import utils.TranslationUtil;
import utils.WordFrequencyAnalyzer;

import java.util.ArrayList;
import java.util.List;

public class ElPaisTest extends BaseTest {

    @Test
    public void testOpinionArticles() throws Exception {
        driver.get("https://elpais.com/");
        Assert.assertTrue(driver.findElement(By.tagName("html")).getAttribute("lang").contains("es"));

        driver.findElement(By.xpath("//a[contains(text(), 'Opinión')][@cmp-ltrk='portada_menu']")).click();
        List<WebElement> articles = driver.findElements(By.tagName("article"));
        System.out.println("Number of articles on page : "+articles.size());
        List<String> titles = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            WebElement article = articles.get(i);
            String currTitle = article.findElement(By.tagName("h2")).getText();
            titles.add(currTitle);
            System.out.println("Title of article number "+i+" : " + currTitle);
            String paragraph = article.findElement(By.tagName("p")).getText();
            System.out.println("Content of article number "+i+" : " + paragraph);
            try {
                String imgUrl = article.findElement(By.tagName("img")).getAttribute("src");
                ImageDownloader.downloadImage(imgUrl, "images/image" + i + ".jpg");
            } catch (Exception ignored) {
                System.out.println("Image doesnt exist for current article");
            }
        }

        List<String> translated = new ArrayList<>();
        for (String title : titles) {
            String en = TranslationUtil.translateToEnglish(title);
            System.out.println("ENGLISH: " + en);
            translated.add(en);
        }

        WordFrequencyAnalyzer.analyze(translated);
    }
}
