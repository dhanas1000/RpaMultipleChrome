package org.example.rpa;

import org.example.constants.Constants;
import org.example.entity.Employee;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Rpa implements Runnable{
    private final Employee employeeData;

    public Rpa(Employee employee) {
        this.employeeData = employee;
    }

    @Override
    public void run() {
        System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER);
        WebDriver driver = new ChromeDriver();

        try {
            // 3. Load the HTML form
            driver.get(getHtmlFilePath()); // Replace with actual path

            // 4. Fill the form using data from ConcurrentHashMap
            driver.findElement(By.id("empId")).sendKeys(employeeData.getId());
            driver.findElement(By.id("name")).sendKeys(employeeData.getName());
            driver.findElement(By.id("email")).sendKeys(employeeData.getEmail());

            // 5. Click Register button
            WebElement registerButton = driver.findElement(By.xpath("//button[text()='Register']"));
            registerButton.click();

            // 6. Wait and handle alert popup
            Thread.sleep(1000);
            String alertText = driver.switchTo().alert().getText();
            System.out.println("Popup says: " + alertText);
            driver.switchTo().alert().accept();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            driver.quit();
            ConcurrentMap.remove(employeeData.getId());
        }
    }


    private static String getHtmlFilePath() {
        String htmlPath = System.getProperty("user.dir") + "/user_registration.html";
        return "file:///" + htmlPath.replace("\\", "/");
    }

}
