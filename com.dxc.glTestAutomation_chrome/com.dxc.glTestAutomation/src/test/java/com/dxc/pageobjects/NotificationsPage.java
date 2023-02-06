package com.dxc.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.dxc.utilities.ConfigFileHandler;
import com.dxc.utilities.TestUtilities;

public class NotificationsPage {
	private WebDriver driver;
	private WebDriverWait wait;

	public NotificationsPage(WebDriver driver) {
		this.driver = driver;
		int waitInSec = Integer.parseInt(ConfigFileHandler.getProperty("explicitWaitInSec"));
		this.wait = new WebDriverWait(this.driver, waitInSec);
		PageFactory.initElements(this.driver, this); // Initializing
	}

	// Locators
//	@FindBy(xpath = "*//input[@type='search']")
//	private WebElement searchField;

//	@FindBy(xpath = "//button[text()='Approve']")
//	private WebElement approveBtn;
//
//	@FindBy(xpath = "//button[text()='Reject']")
//	private WebElement rejectBtn;

	@FindBy(xpath = "*//button[text()='Worklist']")
	private WebElement worklistBtn;

	// Methods
	public void isNotificationPage() {
		wait.until(ExpectedConditions.visibilityOf(worklistBtn));
	}

	public void clickOnApprove(String JOURNAL_BATCH) throws InterruptedException {
		WebElement approveBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("*//button[contains(@title, '" + JOURNAL_BATCH + " ') and text()='Approve']")));

		TestUtilities.scrollIntoView(this.driver, approveBtn);
		Thread.sleep(1000);

		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("*//button[contains(@title, '" + JOURNAL_BATCH + " ') and text()='Approve']"))).click();
		TestUtilities.waitForElementInvisibility(approveBtn);
	}

	public void clickOnReject(String JOURNAL_BATCH) throws InterruptedException {
		WebElement rejectBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("*//button[contains(@title, '" + JOURNAL_BATCH + " ') and text()='Reject']")));

		TestUtilities.scrollIntoView(this.driver, rejectBtn);
		Thread.sleep(1000);

		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("*//button[contains(@title, '" + JOURNAL_BATCH + " ') and text()='Reject']"))).click();
		TestUtilities.waitForElementInvisibility(rejectBtn);
	}
}
