package com.test.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class webPageAssert {
  static WebDriver driver = new ChromeDriver();

  @Test(priority = 1)
  public void checkTitleOfPage() {
    initiateDriver();
    String expectedTitle = "Y1 - f\u00FChrende Magento-Agentur & eCommerce-Beratung";
    String actualTitle = driver.getTitle();

    Assert.assertEquals(actualTitle, expectedTitle);
  }

  @Test(priority = 2)
  public void checkThemeTitleOfPage() {

    String expectedTitle = "UNSERE TOP REFERENZEN";

    WebElement loanComponent = driver.findElement(By.cssSelector(".themen-slider__header >h3"));
    String actualTitle = loanComponent.getText();

    Assert.assertEquals(actualTitle, expectedTitle);
  }

  @Test(priority = 3)
  public void checkifLinksHaveValues() throws InterruptedException {

    boolean isEmptyLink = false;
    Thread.sleep(5000);
    List < WebElement > links = driver.findElements(By.tagName("a"));
    String theEmptyLink = "";
    for (WebElement link: links) {

      String urlLink = link.getAttribute("href");
      String urlTitle = link.getText();
      if (urlLink == null || urlLink.isEmpty()) {
        isEmptyLink = true;
        theEmptyLink = urlLink;
        System.out.println("The following Link is empty - " + theEmptyLink + " - with the title - " + urlTitle);
        break;
      }
    }

    Assert.assertEquals(false, isEmptyLink);
  }

  @Test(priority = 4)
  public void checkifLinksAreBroken() {

    HttpURLConnection huc = null;
    int respCode = 200;
    boolean isBrokenLink = false;

    List < WebElement > links = driver.findElements(By.tagName("a"));

    for (WebElement link: links) {

      String urlLink = link.getAttribute("href");
      String urlTitle = link.getText();

      try {
        huc = (HttpURLConnection)(new URL(urlLink).openConnection());

        huc.setRequestMethod("HEAD");

        huc.connect();

        respCode = huc.getResponseCode();
        if (respCode >= 400) {
          isBrokenLink = true;
          System.out.println(urlLink + " is not a working link with the title - " + urlTitle);
        } else {
          // System.out.println(url + " is a working link");
        }
      } catch(MalformedURLException e) {
        setUnsuccessMsg();
      } catch(ProtocolException e) {
        setUnsuccessMsg();
      } catch(IOException e) {
        setUnsuccessMsg();
      }
    }
    driver.close();
    Assert.assertEquals(false, isBrokenLink);

  }

  public static void setUnsuccessMsg() {
    System.out.println("Unable to process !");
  }

  public static void initiateDriver() {

    System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

    try {
      driver.manage().window().maximize();
      driver.get("https://www.y1.de/");

    } catch(Exception e) {
      setUnsuccessMsg();
    }
  }

}