package com.dxc.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dxc.utilities.ConfigFileHandler;
import com.dxc.utilities.TestUtilities;

public class JournalsPage {
	private WebDriver driver;
	private WebDriverWait wait;

	// Constructor
	public JournalsPage(WebDriver driver) {
		this.driver = driver;
		int waitInSec = Integer.parseInt(ConfigFileHandler.getProperty("explicitWaitInSec"));
		this.wait = new WebDriverWait(this.driver, waitInSec);
		PageFactory.initElements(this.driver, this); // Initializing Elements
	}

	// Locators
	@FindBy(xpath = "*//img[@title='Tasks']/..")
	private WebElement taskPanel;

	@FindBy(xpath = "*//a[text()='Manage Journals']")
	private WebElement manageJournals;

	@FindBy(xpath = "*//a[text()='Create Journal']")
	private WebElement createJournal;

	@FindBy(xpath = "*//a[text()='Run AutoReverse']")
	private WebElement runAutoReverse;

	@FindBy(xpath = "//a[text()='Change']")
	private WebElement changeLink;

	@FindBy(xpath = "*//select[@id=(//label[text()='Data Access Set']/@for)]")
	private WebElement selectDataset;

	@FindBy(xpath = "*//button[text()='OK']")
	private WebElement okBtn;

	// Methods
	public void clickOnTaskbar() {
		wait.until(ExpectedConditions.visibilityOf(taskPanel)).click();
	}

	public void clickOnManageJournals() {
		wait.until(ExpectedConditions.visibilityOf(manageJournals)).click();
	}

	public void clickOnCreateJournal() {
		wait.until(ExpectedConditions.visibilityOf(createJournal)).click();
	}

	public void clickOnRunAutoReverse() {
		wait.until(ExpectedConditions.visibilityOf(runAutoReverse)).click();
	}

	public void selectDataAccessSet(String input) throws InterruptedException {
		
					
		wait.until(ExpectedConditions.visibilityOf(changeLink));
		Thread.sleep(6000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", changeLink);

		Thread.sleep(3000);
		Select s = new Select(wait.until(ExpectedConditions.visibilityOf(selectDataset)));
		s.selectByVisibleText(input);
		wait.until(ExpectedConditions.visibilityOf(okBtn)).click();
		TestUtilities.waitForElementInvisibility(okBtn);
	}
}
