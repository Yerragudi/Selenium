package com.dxc.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.dxc.utilities.ConfigFileHandler;
import com.dxc.utilities.TestUtilities;

public class RunAutoReversePage {
	private WebDriver driver;
	private WebDriverWait wait;

	public RunAutoReversePage(WebDriver driver) {
		this.driver = driver;
		int waitInSec = Integer.parseInt(ConfigFileHandler.getProperty("explicitWaitInSec"));
		this.wait = new WebDriverWait(this.driver, waitInSec);
		PageFactory.initElements(this.driver, this); // Initialize Elements
	}

	// Locators
	@FindBy(xpath = "*//select[@id=(//label[text()='Data Access Set']/@for)]")
	private WebElement dataAccessSet;

	@FindBy(xpath = "*//select[@id=(//label[text()='Ledger']/@for)]")
	private WebElement ledger;

	@FindBy(xpath = "*//select[@id=(//label[text()='Reversal Period']/@for)]")
	private WebElement reversalPeriod;

	@FindBy(xpath = "*//div[contains(@id, ':requestBtns:submitButton')]")
	private WebElement submitBtn;

	@FindBy(xpath = "*//div[contains(@id, ':confirmationPopup:confirmPopup::popup-container')]//label")
	private WebElement confirmationMsg;

	@FindBy(xpath = "*//div[contains(@id, ':confirmationPopup:confirmPopup::popup-container')]//button")
	private WebElement confirmationMsgOkBtn;

	// Methods
	public void clickOnSubmit() {
		wait.until(ExpectedConditions.visibilityOf(submitBtn)).click();
	}

	public void selectDataAccessSet(String option) {
		Select das = new Select(wait.until(ExpectedConditions.visibilityOf(dataAccessSet)));
		das.selectByVisibleText(option);
	}

	public void selectLedger(String option) {
		Select lg = new Select(wait.until(ExpectedConditions.visibilityOf(ledger)));
		lg.selectByVisibleText(option);
	}

	public void selectReversalPeriod(String option) {
		Select rp = new Select(wait.until(ExpectedConditions.visibilityOf(reversalPeriod)));
		rp.selectByVisibleText(option);
	}

	public String getProcessId() {
		String cnfrmMsg = wait.until(ExpectedConditions.visibilityOf(confirmationMsg)).getText();
		String processId = TestUtilities.extractNumberFromText(cnfrmMsg);
		return processId;
	}

	public void clickOnConfirmationMsgOkBtn() {
		wait.until(ExpectedConditions.visibilityOf(confirmationMsgOkBtn)).click();
		TestUtilities.waitForElementInvisibility(confirmationMsgOkBtn);
	}
}
