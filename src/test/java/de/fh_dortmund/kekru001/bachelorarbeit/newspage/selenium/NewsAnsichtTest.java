package de.fh_dortmund.kekru001.bachelorarbeit.newspage.selenium;

import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by krummenauer on 09.01.2017.
 */
public class NewsAnsichtTest {

    @BeforeClass
    public static void initClass() throws MalformedURLException {
        final String hub = "http://10.1.6.210:30002/wd/hub";

        WebDriver driver = new RemoteWebDriver(
                new URL(hub),
                DesiredCapabilities.firefox());

        final String url = "http://newspage:8081/";
        //Webseite ueber Selenium im Firefox aufrufen
        driver.get(url);
    }


}
