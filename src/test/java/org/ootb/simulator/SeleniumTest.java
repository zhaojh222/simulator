package org.ootb.simulator;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;

/**
 * Created by jiazhao on 11/16/17.
 */
public class SeleniumTest {

    @Test
    public void testCaptureWebPage() throws Exception{
//        System.setProperty("webdriver.chrome.driver","/Applications/Firefox.app/Contents/MacOS/firefox");
        String url = "https://api-devshowcase.selling.gapinc.dev/orders/151070626897305955060131201149001/receipt/print";
        WebDriver webDriver = new FirefoxDriver();
        webDriver.get("www.google.com");
        File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file,new File("151070626897305955060131201149001.png"));
    }
}
