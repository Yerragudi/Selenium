package com.dxc.utilities;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DownloadFileHandler {

	public static boolean isFileDownloaded(String fileName) {
		File dir = new File(ConfigFileHandler.getProperty("downloadFolderLocation"));
		File[] dirContents = dir.listFiles();

		for (int i = 0; i < dirContents.length; i++) {
			if (dirContents[i].getName().equals(fileName)) {
				System.out.println("File Downloaded!");
				return true;
			}
		}
		return false;
	}

	public static void waitForFileDownload(WebDriver driver, WebDriverWait wait, WebElement confirmationElement)
			throws InterruptedException {
		Set<String> allWindowHandles = driver.getWindowHandles();
		Iterator<String> iterator2 = allWindowHandles.iterator();

		while (iterator2.hasNext()) {
			String childWindow = iterator2.next();
			driver.switchTo().window(childWindow);

			if (driver.getTitle().equals("Loading...")) {
				wait.until(ExpectedConditions.visibilityOf(confirmationElement));
				Thread.sleep(6000);
			}
		}
	}
}