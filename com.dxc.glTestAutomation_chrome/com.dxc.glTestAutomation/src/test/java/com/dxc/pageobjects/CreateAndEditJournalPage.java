package com.dxc.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dxc.utilities.ConfigFileHandler;
import com.dxc.utilities.TestUtilities;

public class CreateAndEditJournalPage {
	private WebDriver driver;
	private WebDriverWait wait;

	public CreateAndEditJournalPage(WebDriver driver) {
		this.driver = driver;
		int waitInSec = Integer.parseInt(ConfigFileHandler.getProperty("explicitWaitInSec"));
		this.wait = new WebDriverWait(this.driver, waitInSec);
		PageFactory.initElements(this.driver, this); // Initialize Elements
	}

	// ----- Locators -----
	@FindBy(xpath = "*//span[text()='Save']/..")
	private WebElement saveBtn;

	@FindBy(xpath = "*//span[text()='Complete']/..")
	private WebElement completeBtn;

	@FindBy(xpath = "*//span[text()='Post']/..")
	private WebElement postBtn;

	@FindBy(xpath = "//span[text()='ancel']/..")
	private WebElement cancelBtn;

	@FindBy(xpath = "*//button[contains(@id,':userResponsePopupDialogButtonOk')]")
	private WebElement popupOkBtn;

//	@FindBy(xpath = "//a[contains(@id,':showMoreHeader')]")
	@FindBy(xpath = "//a[text()=' Show More']")
	private WebElement journalShowMore;

	@FindBy(xpath = "//a[text()='Reversal']")
	private WebElement reversalTab;

	@FindBy(xpath = "//input[@id=(//label[text()='Reversal Period'])/@for]")
	private WebElement reversalPeriod;

	@FindBy(xpath = "//label[text()='Reversal Status']/../../td[2]")
	private WebElement reversalStatus;

	@FindBy(xpath = "//a[text()='Journal Actions']")
	private WebElement journalActions;

	@FindBy(xpath = "//*[contains(@id,':cmi2')]")
	private WebElement reverseAction;

	@FindBy(xpath = "//*[contains(@id,':userResponsePopupDialogButtonOk')]")
	private WebElement reverseConfirmBtn;

	@FindBy(xpath = "*//input[@id=(//label[text()='Journal Batch']/@for)]")
	private WebElement journalBatch;

	@FindBy(xpath = "*//textarea[@id=(//label[text()='Description']/@for)]")
	private List<WebElement> descriptions;

	@FindBy(xpath = "*//input[@id=(//label[text()='Accounting Period']/@for)]/following-sibling::a")
	private WebElement accountingPeriodDropdown;

	@FindBy(xpath = "*//div[contains(@id,':showLessPeriodNameCLOV:') and contains(@id,'::popup-container')]")
	private WebElement accountingPeriodList;

	@FindBy(xpath = "*//input[@id=(//label[text()='Journal']/@for)]")
	private WebElement journal;

	@FindBy(xpath = "*//input[@id=(//label[text()='Ledger']/@for)]")
	private WebElement ledger;

	@FindBy(xpath = "*//input[@id=(//label[text()='Legal Entity Name']/@for)]")
	private WebElement legalEntity;

	@FindBy(xpath = "*//input[@id=(//label[text()='Accounting Date']/@for)]")
	private WebElement accountingDate;

	@FindBy(xpath = "*//input[@id=(//label[text()='Category']/@for)]/following-sibling::a")
	private WebElement categoryDropdown;

	@FindBy(xpath = "*//div[contains(@id,':userJeCategoryNameInputSearch1::sgstnCntnr')]")
	private WebElement categoryList;

	@FindBy(xpath = "*//input[@id=(//label[text()='Currency']/@for)]")
	private WebElement currency;

	@FindBy(xpath = "*//table[@summary='Journal Lines']//tr[contains(@class, 'xem')]")
	private List<WebElement> journalLines;

	@FindBy(xpath = "*//button[contains(@id, ':userResponsePopupDialogButtonYes')]")
	private WebElement popupYesBtn;

	@FindBy(xpath = "*//button[contains(@id, ':userResponsePopupDialogButtonNo')]")
	private WebElement popupNoBtn;

	@FindBy(xpath = "//label[text()='Completion Status']/../following-sibling::td")
	private WebElement completionStatus;

	@FindBy(xpath = "//label[text()='Batch Status']/../following-sibling::td/span")
	private WebElement batchStatus;

	@FindBy(xpath = "//label[text()='Approval Status']/../following-sibling::td")
	private WebElement approvalStatus;

	@FindBy(xpath = "*//div[contains(@id, ':ap1:postBatch')]")
	private WebElement postBtnDiv;

	@FindBy(xpath = "*//a[text()='Batch Actions']")
	private WebElement batchActions;

	@FindBy(xpath = "*//td[text()='Copy']")
	private WebElement copyOption;

	@FindBy(xpath = "*//div[contains(@id, ':userResponsePopup::popup-container')]//label[text()='Journal Batch']/../following-sibling::td/input")
	private WebElement popupJournalBatch;

	@FindBy(xpath = "*//div[contains(@id, ':userResponsePopup::popup-container')]//label[text()='Accounting Period']/../following-sibling::td//input[2]")
	private WebElement popupAccountingPeriod;

	@FindBy(xpath = "*//div[contains(@id, ':userResponsePopup::popup-container')]//label[text()='Accounting Date']/../following-sibling::td/input")
	private WebElement popupAccountingDate;

	// ----- Methods -----
	public void clickOnSave() {
		wait.until(ExpectedConditions.visibilityOf(saveBtn)).click();
	}

	public void clickOnComplete() {
		wait.until(ExpectedConditions.visibilityOf(completeBtn)).click();
		TestUtilities.waitForElementInvisibility(completeBtn);
	}

	public void clickOnPost() {
		wait.until(ExpectedConditions.visibilityOf(postBtn)).click();
	}

	public void clickOnCancel() {
		wait.until(ExpectedConditions.visibilityOf(cancelBtn)).click();
	}

	public void clickOnPopupOkBtn() {
		wait.until(ExpectedConditions.visibilityOf(popupOkBtn)).click();
		popupOkBtn.click();
		TestUtilities.waitForElementInvisibility(popupOkBtn);
	}

	public void enterJournalBatch(String journalBatch) {
		wait.until(ExpectedConditions.visibilityOf(this.journalBatch)).sendKeys(journalBatch);
	}

	public void enterBatchDescription(String description) {
		wait.until(ExpectedConditions.visibilityOf(this.descriptions.get(0))).sendKeys(description);
	}

	public void enterAccountingPeriod(String accountingPeriod) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(this.accountingPeriodDropdown));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", accountingPeriodDropdown);
//		this.accountingPeriodDropdown.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("*//td[text()='" + accountingPeriod + "']")))
				.click();
	}

	public void enterJournal(String journal) {
		wait.until(ExpectedConditions.visibilityOf(this.journal)).sendKeys(journal);
	}

	public void enterJournalDescription(String description) {
		wait.until(ExpectedConditions.visibilityOf(this.descriptions.get(1))).sendKeys(description);
	}

	public void enterLedger(String ledger) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(this.ledger)).clear();
		Thread.sleep(1000);
		this.ledger.sendKeys(ledger);
	}

	public void enterLegalEntityName(String legalEntity) {
		try {
			this.legalEntity.clear();
			this.legalEntity.sendKeys(legalEntity);
		} catch (Exception e) {
//			System.out.println("Legal Entity Input, not available.");
		}
	}

	public void enterAccountingDate(String date) {
		wait.until(ExpectedConditions.visibilityOf(this.accountingDate)).clear();
		this.accountingDate.sendKeys(date);
	}

	public void enterCategory(String category) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(this.categoryDropdown));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", categoryDropdown);
//		this.categoryDropdown.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("*//td[text()='" + category + "']"))).click();
//		WebElement catList = wait.until(ExpectedConditions.visibilityOf(this.categoryList));
//		catList.findElement(By.xpath("//td[text()='" + category + "']")).click();
	}

	public void enterCurrency(String currency) {
		wait.until(ExpectedConditions.visibilityOf(this.currency)).clear();
		this.currency.sendKeys(currency);
	}

	public void clickOnJournalLine(int lineNumber) {
		if (lineNumber > journalLines.size() || lineNumber <= 0) {
			throw new Error("Invalid Line Number."); // throw an error if the input line number is invalid.
		}
		WebElement journalLine = journalLines.get(lineNumber - 1);
		wait.until(ExpectedConditions.visibilityOf(journalLine)).click();
	}

	public void enterAccount(String accountSegment, int lineNumber) throws InterruptedException {
		if (lineNumber > journalLines.size() || lineNumber <= 0) {
			throw new Error("Invalid Line Number."); // throw an error if the input line number is invalid.
		}
		WebElement accountElem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"*//input[contains(@id,':jeLineAppTable:_ATp:t3:" + (lineNumber - 1) + ":accountCS::content')]")));

		accountElem.clear();
		accountElem.sendKeys(accountSegment);

		WebElement accountSearchIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("*//a[contains(@id,':jeLineAppTable:_ATp:t3:" + (lineNumber - 1) + ":accountKBIMG')]")));

		accountSearchIcon.click();

//		WebElement accountSearchPopupOkBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By
//				.xpath("*//button[contains(@id,':jeLineAppTable:_ATp:t3:" + (lineNumber - 1) + ":accountSEl')]")));
		WebElement accountSearchPopupOkBtn = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='O']")));

		accountSearchPopupOkBtn.click();
		TestUtilities.waitForElementInvisibility(accountSearchPopupOkBtn);
		this.isAccountCorrect(lineNumber);
	}

	private void isAccountCorrect(int lineNumber) throws InterruptedException {
		System.out.println("Fetching the Account Information...");
		System.out.println("Please don't close the Browser or Interfere with the Process.");
		String accountDesc = "";
		int failureCount = 0;
		while (accountDesc.isEmpty()) {
			failureCount++;
			Thread.sleep(2000);
			accountDesc = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
							"*//span[contains(@id,':ap1:jeLineAppTable:_ATp:t3:" + (lineNumber - 1) + ":ot41')]")))
					.getText().trim();
			if (failureCount == 50) {
				throw new Error("Unable to Fetch Account.");
			}
		}
	}

	public void enterDebitAmount(String amount, int lineNumber) {
		if (lineNumber > journalLines.size() || lineNumber <= 0) {
			throw new Error("Invalid Line Number."); // throw an error if the input line number is invalid.
		}
		WebElement debitElem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"*//input[contains(@id,':jeLineAppTable:_ATp:t3:" + (lineNumber - 1) + ":EnteredDr::content')]")));
		debitElem.clear();
		debitElem.sendKeys(amount);
	}

	public void enterCreditAmount(String amount, int lineNumber) {
		if (lineNumber > journalLines.size() || lineNumber <= 0) {
			throw new Error("Invalid Line Number."); // throw an error if the input line number is invalid.
		}
		WebElement creditElem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"*//input[contains(@id,':jeLineAppTable:_ATp:t3:" + (lineNumber - 1) + ":EnteredCr::content')]")));
		creditElem.clear();
		creditElem.sendKeys(amount);
	}

	public void clickOnPopupNo() {
		wait.until(ExpectedConditions.visibilityOf(popupNoBtn)).click();
		TestUtilities.waitForElementInvisibility(popupNoBtn);
	}

	// ------- for TC7// For Reversals ------
	public void clickOnJournalShowMore() {
		wait.until(ExpectedConditions.visibilityOf(journalShowMore)).click();
	}

	public void clickOnReversalTab() {
		wait.until(ExpectedConditions.visibilityOf(reversalTab)).click();
	}

	public void enterReversalPeriod(String input) {
		wait.until(ExpectedConditions.visibilityOf(reversalPeriod)).clear();
		wait.until(ExpectedConditions.visibilityOf(reversalPeriod)).sendKeys(input);
	}

	public void clickOnJournalActions() {
		wait.until(ExpectedConditions.visibilityOf(journalActions)).click();
	}

	public void clickOnReverse() {
		wait.until(ExpectedConditions.elementToBeClickable(reverseAction)).click();
	}

	public void clickOnReverseConfirmBtn() {
		wait.until(ExpectedConditions.visibilityOf(reverseConfirmBtn)).click();
	}
	// ---

	public void clickOnBatchActions() {
		wait.until(ExpectedConditions.visibilityOf(batchActions)).click();
	}

	public void clickOnCopyOption() {
		wait.until(ExpectedConditions.visibilityOf(copyOption)).click();
	}

	public void enterCopyJournalBatch(String journalBatch) {
		wait.until(ExpectedConditions.visibilityOf(popupJournalBatch)).clear();
		wait.until(ExpectedConditions.visibilityOf(popupJournalBatch)).sendKeys(journalBatch);
	}

	public void enterCopyAccountingPeriod(String accountingPeriod) {
		wait.until(ExpectedConditions.visibilityOf(popupAccountingPeriod)).clear();
		wait.until(ExpectedConditions.visibilityOf(popupAccountingPeriod)).sendKeys(accountingPeriod);
	}

	public void enterCopyAccountingDate(String accountingDate) {
		wait.until(ExpectedConditions.visibilityOf(popupAccountingDate)).clear();
		wait.until(ExpectedConditions.visibilityOf(popupAccountingDate)).sendKeys(accountingDate);
	}

	public String getReversalStatus() throws InterruptedException {
		System.out.println("Waiting for Reversal Status to change...");
		System.out.println("Please don't close the Browser or Interfere with the Process.");
		String status = wait.until(ExpectedConditions.visibilityOf(reversalStatus)).getText();
		int failureCount = 0;
		while (!status.equals("Reversed")) {
			failureCount++;
			Thread.sleep(20000);
			this.driver.navigate().refresh();
			Thread.sleep(2000);
			status = wait.until(ExpectedConditions.visibilityOf(reversalStatus)).getText();

			if (failureCount == 15) {
//				throw new Error("Reversal Failed!");
				return status;
			}
		}
		return status;
	}

	public String getCompletionStatus() {
		return wait.until(ExpectedConditions.visibilityOf(completionStatus)).getText();
	}

	public String getBatchStatus() throws InterruptedException {
		System.out.println("Waiting for Batch Status to change...");
		System.out.println("Please don't close the Browser or Interfere with the Process.");
		String status = "";
		int failureCount = 0;
		while (!status.equals("Posted")) {
			failureCount++;
			Thread.sleep(20000);
			this.driver.navigate().refresh();
			Thread.sleep(2000);
			status = wait.until(ExpectedConditions.visibilityOf(batchStatus)).getText();

			if (failureCount == 15) {
//				throw new Error("Approval Status Invalid!");
				return status;
			}
		}
		return status;
	}

	public String getApprovalStatus() throws InterruptedException {
		System.out.println("Waiting for Approval Status to change...");
		System.out.println("Please don't close the Browser or Interfere with the Process.");
		String status = wait.until(ExpectedConditions.visibilityOf(approvalStatus)).getText();
		int failureCount = 0;
		while (!status.equals("In process")) {
			if (status.equals("Approved") || status.equals("Not required") || status.equals("Rejected")) {
				return status;
			}
			failureCount++;
			Thread.sleep(20000);
			this.driver.navigate().refresh();
			Thread.sleep(2000);
			status = wait.until(ExpectedConditions.visibilityOf(approvalStatus)).getText();

			if (failureCount == 15) {
//				throw new Error("Approval Status Invalid!");
				return status;
			}
		}
		return status;
	}
}
