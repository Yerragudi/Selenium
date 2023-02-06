package com.dxc.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dxc.utilities.ConfigFileHandler;
import com.dxc.utilities.TestUtilities;

public class ManageJournalsPage {
	private WebDriver driver;
	private WebDriverWait wait;

//Constructor
	public ManageJournalsPage(WebDriver driver) {
		this.driver = driver;
		int waitInSec = Integer.parseInt(ConfigFileHandler.getProperty("explicitWaitInSec"));
		this.wait = new WebDriverWait(this.driver, waitInSec);
		PageFactory.initElements(this.driver, this); // Initializing

	}

	// Locators
	@FindBy(xpath = "//span[text()='ne']/..")
	private WebElement doneBtn;

	@FindBy(xpath = "//a[@title='Expand Search' or @title='Collapse Search']")
	private WebElement expandSearch;

	@FindBy(xpath = "//input[@id=(//label[text()=' Accounting Period']/@for)]")
	private WebElement acctPeriod;

	@FindBy(xpath = "*//select[@id=(//label[text()='Journal Operator']/@for)]")
	private WebElement journalDropdown;

	@FindBy(xpath = "//input[@id=(//label[text()=' Journal']/@for)]")
	private WebElement journalInput;

	@FindBy(xpath = "*//select[@id=(//label[text()='Journal Batch Operator']/@for)]")
	private WebElement journalBatchDropdown;

	@FindBy(xpath = "//input[@id=(//label[text()=' Journal Batch']/@for)]")
	private WebElement journalBatchInput;

	public void clickOnDoneBtn() {
		wait.until(ExpectedConditions.visibilityOf(doneBtn)).click();
		TestUtilities.waitForElementInvisibility(doneBtn);
	}

	public void expandSearch() {
		wait.until(ExpectedConditions.visibilityOf(expandSearch)).click();
	}

	public void clearAccountingPeriod() {
		wait.until(ExpectedConditions.visibilityOf(acctPeriod)).clear();
	}

	public void selectJournalCriteria(String option) {
		Select jbc = new Select(wait.until(ExpectedConditions.visibilityOf(journalDropdown)));
		jbc.selectByVisibleText(option);
	}

	public void selectJournalBatchCriteria(String option) {
		Select jbc = new Select(wait.until(ExpectedConditions.visibilityOf(journalBatchDropdown)));
		jbc.selectByVisibleText(option);
	}

	public void enterJournal(String value) {
		wait.until(ExpectedConditions.visibilityOf(journalInput)).sendKeys(value);
	}

	public void enterJournalBatch(String value) {
		wait.until(ExpectedConditions.visibilityOf(journalBatchInput)).sendKeys(value, Keys.RETURN);
	}

	public void selectJournal(String journalName_caseSensitive) {
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("*//a[text()='" + journalName_caseSensitive + "']"))).click();
	}

	public void searchAndSelectJournalByName(String searchCriteria, String batchName, String journalName) {
		this.clearAccountingPeriod();

		this.selectJournalCriteria(searchCriteria);
		this.enterJournal(journalName);

		this.selectJournalBatchCriteria(searchCriteria);
		this.enterJournalBatch(batchName); // sends Keys.RETURN

		this.selectJournal(journalName);
	}

	public void searchAndSelectJournalByBatchName(String searchCriteria, String batchName) {
		this.clearAccountingPeriod();

		this.selectJournalBatchCriteria(searchCriteria);
		this.enterJournalBatch(batchName); // sends Keys.RETURN

		this.selectJournal(batchName);
	}

	// public void searchJournalAndSelect(String searchCriteria, String batchName) {
	// this.clearAccountingPeriod();
	// this.selectJournalBatchCriteria(searchCriteria);
	// this.enterJournalBatch(batchName);
	// this.selectJournal(batchName);
	// }
	//
	// public void searchJournalAndSelect(String searchCriteria, String journalName,
	// String batchName) {
	// this.clearAccountingPeriod();
	// this.selectJournalCriteria(searchCriteria);
	// this.enterJournal(journalName);
	//
	// this.selectJournalBatchCriteria(searchCriteria);
	// this.enterJournalBatch(batchName);
	//
	// this.selectJournal(batchName);
	// }
}