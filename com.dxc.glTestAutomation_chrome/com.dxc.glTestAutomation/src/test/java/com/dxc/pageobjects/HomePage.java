package com.dxc.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.dxc.utilities.ConfigFileHandler;

public class HomePage {
	private WebDriver driver;
	private WebDriverWait wait;

	// Constructor
	public HomePage(WebDriver driver) {
		this.driver = driver;
		int waitInSec = Integer.parseInt(ConfigFileHandler.getProperty("explicitWaitInSec"));
		this.wait = new WebDriverWait(this.driver, waitInSec); // Explicit wait
		PageFactory.initElements(this.driver, this);// Initialize
	}

	// Locators
	@FindBy(xpath = "*//a[@title='Oracle Logo Home']")
	private WebElement mainLogo;

	@FindBy(xpath = "*//a[@title='Navigator']")
	private WebElement navigationMenu;

	@FindBy(xpath = "*//div[@title='Tools']")
	private WebElement toolsSection;

	@FindBy(xpath = "*//a[@title='Scheduled Processes']")
	private WebElement scheduledProcessesLink;

	@FindBy(xpath = "//div[@title='General Accounting']")
	private WebElement generalAccountingSection;

	@FindBy(xpath = "//a[@title='Journals']")
	private WebElement journalsLink;

	@FindBy(xpath = "*//a[contains(@id,'pt1:_UISatr:0:cil1') and contains(@title,'Notifications')]")
	private WebElement notifications;

	@FindBy(xpath = "//*[(text()='Show All') or (@title='Show All')]")
	private WebElement showAllLink;

	// Methods
	public void backToHomePage() {
		wait.until(ExpectedConditions.visibilityOf(mainLogo)).click();
	}

	public void clickOnNavigation() {
		wait.until(ExpectedConditions.visibilityOf(navigationMenu)).click();
	}

	public void clickOnTools() {
		wait.until(ExpectedConditions.visibilityOf(toolsSection)).click();
	}

	public void clickOnScheduledProcesses() {
		wait.until(ExpectedConditions.visibilityOf(scheduledProcessesLink)).click();
	}

	public void clickOnJournals() {
		wait.until(ExpectedConditions.visibilityOf(journalsLink)).click();
	}

	public void clickOnGeneralAccounting() {
		wait.until(ExpectedConditions.visibilityOf(generalAccountingSection)).click();
	}

	public void clickOnNotifications() {
		wait.until(ExpectedConditions.visibilityOf(notifications)).click();
	}

	public void clickOnShowAll() {
		wait.until(ExpectedConditions.visibilityOf(showAllLink)).click();
	}
}
