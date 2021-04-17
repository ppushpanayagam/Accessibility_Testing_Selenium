package testPackage;

import com.deque.axe.AXE;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AccessibilityTestCase {

    WebDriver driver;
    private static final URL scriptURL = AccessibilityTestCase.class.getResource("/axe-min.js");

    @BeforeClass
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.amazon.co.uk/");
    }

    @Test
    public void accessibilityTests(){
        JSONObject responseObject = new AXE.Builder(driver, scriptURL).analyze();
        JSONArray violations = responseObject.getJSONArray("violations");

        if(violations.length()==0){
            System.out.println("***********No ERRORS**********");
        }else{
            AXE.writeResults("Accessibility Tests", responseObject);
            Assert.assertTrue(false, AXE.report(violations));
        }

    }

    @AfterClass
    public void tearDown(){
        driver.close();
        driver.quit();
    }
}
