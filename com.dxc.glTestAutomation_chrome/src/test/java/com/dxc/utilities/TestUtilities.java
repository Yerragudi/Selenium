package com.dxc.utilities;

import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

public class TestUtilities {

	public static SoftAssert softAssert = new SoftAssert();

	public static String extractNumberFromText(String text) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher m = pattern.matcher(text);
		String number = "";
		while (m.find()) {
			number = m.group();
		}
		return number;
	}

	public static boolean waitForElementInvisibility(WebElement element) {
		try {
			int failureCount = 0;
			while (element.isDisplayed()) {
				Thread.sleep(2000);
				failureCount++;
				if (failureCount > 15) {
					return false;
				}
			}
			;
			return true;
		} catch (Exception e) {
//			System.out.println("IsPopupInvisible:ErrorMessage:(Please Ignore) " + e);
			return true;
		}
	}

	public static void scrollIntoView(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public static void scrollBy(WebDriver driver, int xDirection, int yDirection) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(" + xDirection + ", " + yDirection + ")");
	}

	public static WebDriver switchToPopupWindow(WebDriver driver, String mainWindowHandle) {
		Set<String> allWindowHandles = driver.getWindowHandles();
		Iterator<String> iterator = allWindowHandles.iterator();
		while (iterator.hasNext()) {
			String ChildWindow = iterator.next();
			if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {
				driver.switchTo().window(ChildWindow);
//				String windowTitle = driver.getTitle();
//				System.out.println(windowTitle);
			}
		}
		return driver;
	}

	public static WebDriver switchToMainWindow(WebDriver driver, String mainWindowHandle) {
		Set<String> allWindowHandles = driver.getWindowHandles();
		Iterator<String> iterator2 = allWindowHandles.iterator();
		while (iterator2.hasNext()) {
			String childWindow = iterator2.next();
			if (!mainWindowHandle.equalsIgnoreCase(childWindow)) {
				driver.switchTo().window(childWindow);
				driver.close();
			}
		}
		driver.switchTo().window(mainWindowHandle);
		return driver;
	}
}
