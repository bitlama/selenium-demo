package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Scenario1 {
    private RemoteWebDriver driver;

    public static void main(String[] args) throws InterruptedException {
        new Scenario1().execute();
    }

    private void execute() throws InterruptedException {
        try {
            driver = new ChromeDriver();
            driver.get("http://yandex.ru");
            waitUntil(driver -> driver.findElement(By.id("tabnews_newsc")) != null);

            List<WebElement> news = driver.findElementsByCssSelector("ol.b-news-list > li > a");
            news.stream()
                    .filter(WebElement::isDisplayed)
                    .forEach(element -> {
                        System.out.println(element.getText());
                        System.out.println(element.getAttribute("href"));
                        System.out.println();
                    });

        } finally {
            if (driver != null) {
                driver.close();
            }
        }
    }

    private void waitUntil(ExpectedCondition<?> condition) {
        new FluentWait<>(driver)
                .withTimeout(20, TimeUnit.SECONDS)
                .pollingEvery(100, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .until(condition);
    }
}
