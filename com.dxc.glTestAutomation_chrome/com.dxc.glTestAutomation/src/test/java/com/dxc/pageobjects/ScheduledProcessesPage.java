package com.dxc.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dxc.utilities.ConfigFileHandler;
import com.dxc.utilities.DownloadFileHandler;
import com.dxc.utilities.TestUtilities;

import org.openqa.selenium.support.ui.Select;

public class ScheduledProcessesPage {
	private WebDriver driver;
	private WebDriverWait wait;
	String mainWindowHandle;

//Constructor
	public ScheduledProcessesPage(WebDriver driver) {
		this.driver = driver;
		int waitInSec = Integer.parseInt(ConfigFileHandler.getProperty("explicitWaitInSec"));
		this.wait = new WebDriverWait(this.driver, waitInSec);
		PageFactory.initElements(this.driver, this); // Initialize
		this.mainWindowHandle = this.driver.getWindowHandle();
	}

	// Locators

	@FindBy(xpath = "//div[contains(@id,':requestBtns:submitButton')]")
	private WebElement submitBtn;

	// -----Schedule new Process-----
	@FindBy(xpath = "//span[text()='Schedule New Process']/..")
	private WebElement scheduleNewProcessBtn;

	@FindBy(xpath = "//a[@title='Search: Name']")
	private WebElement searchProcessDropdown;

	@FindBy(xpath = "//a[text()='Search...']")
	private WebElement searchLink;

	@FindBy(xpath = "//input[@id=(//label[text()=' Name'])/@for]")
	private WebElement searchProcessInput;

	@FindBy(xpath = "//input[@id=(//label[text()=' Name'])/@for]/following::table/tbody/tr/td[2]/div/table/tbody/tr")
	private WebElement searchProcessResult;

	@FindBy(xpath = "//input[@id=(//label[text()=' Name'])/@for]/following::button[3]")
	private WebElement searchProcessOKBtn;

	@FindBy(xpath = "//a[@title='Search: Name']/following::button[1]")
	private WebElement scheduleNewProcessOKBtn;
	// --------
	// ---Confirmation Pop-up----
	@FindBy(xpath = "//*[contains(@id,':requestBtns:confirmationPopup:confirmPopup::popup-container')]//label")
	private WebElement confirmationMsg;

	@FindBy(xpath = "//*[contains(@id,':requestBtns:confirmationPopup:confirmSubmitDialog::ok')]")
	private WebElement confirmationMsgOkBtn;

	// ------ Search a Process with Process ID ---------
	@FindBy(xpath = "//a[@title='Expand Search' or @title='Collapse Search']")
	private WebElement expandSearch;

	@FindBy(xpath = "//input[@id=(//label[text()=' Process ID'])/@for]")
	private WebElement processIdInput;

	@FindBy(xpath = "//table[@summary='List of Processes Meeting Search Criteria']//tr")
	private WebElement processResultRow1;

	@FindBy(xpath = "//table[@summary='List of Processes Meeting Search Criteria']//tr//a")
	private WebElement processStatusElem;

	@FindBy(xpath = "//*[@title='Refresh']")
	private WebElement refreshBtn;
	// ---------

	// ---Report Downloading---
	@FindBy(id = "reportViewMenu")
	private WebElement reportViewActions;

	@FindBy(xpath = "//*[@id='_xdoFMenu0']/div/div/ul/li[1]/div/a")
	private WebElement exportOption;

	@FindBy(xpath = "*//a//div[text()='PDF']")
	private WebElement exportToPDF;

	@FindBy(xpath = "*//span[@id='message' and text()='Report Completed']")
	private WebElement reportDwldMessage;
	// ----

	// -----Search Ledger----
	@FindBy(xpath = "//a[@title='Search: Ledger']")
	private WebElement ledgerSearchBtn;

	@FindBy(xpath = "//a[contains(@id,':ledgerAttrId::dropdownPopup::popupsearch')]")
	private WebElement ledgerSearchLink;

	@FindBy(xpath = "//input[@id=(//label[text()=' Ledger'])/@for]")
	private WebElement searchLedgerInputField;

	@FindBy(xpath = "//input[@id=(//label[text()=' Ledger'])/@for]/following::table/tbody/tr/td[2]/div/table/tbody/tr")
	private WebElement searchLedgerResult;

	@FindBy(xpath = "//input[@id=(//label[text()=' Ledger'])/@for]/following::button[3]")
	private WebElement searchLedgerOkBtn;
	// ----

	// ---Search From Accounting Period----
	@FindBy(xpath = "//a[@title='Search: From Accounting Period']")
	private WebElement fromAccPeriodSearchBtn;

	@FindBy(xpath = "//a[contains(@id,':aTTRIBUTE5Id::dropdownPopup::popupsearch')]")
	private WebElement fromAccPeriodSearchLink;

	@FindBy(xpath = "//input[@id=(//label[text()=' Accounting Period'])/@for]")
	private WebElement fromAccPeriodInputField;

	@FindBy(xpath = "//input[@id=(//label[text()=' Accounting Period'])/@for]/following::table/tbody/tr/td[2]/div/table/tbody/tr")
	private WebElement fromAccPeriodResult;

	@FindBy(xpath = "//input[@id=(//label[text()=' Accounting Period'])/@for]/following::button[3]")
	private WebElement fromAccPeriodOkBtn;
	// ----

	// ----Search To Accounting Period----
	@FindBy(xpath = "//a[@title='Search: To Accounting Period']")
	private WebElement ToAccPeriodSearchBtn;

	@FindBy(xpath = "//a[contains(@id,':aTTRIBUTE7Id::dropdownPopup::popupsearch')]")
	private WebElement ToAccPeriodSearchLink;

	@FindBy(xpath = "//input[contains(@id,':aTTRIBUTE7Id::_afrLovInternalQueryId:')]")
	private WebElement ToAccPeriodInputField;

	@FindBy(xpath = "//input[contains(@id,':aTTRIBUTE7Id::_afrLovInternalQueryId:')]/following::table/tbody/tr/td[2]/div/table/tbody/tr")
	private WebElement ToAccPeriodResult;

	@FindBy(xpath = "//input[contains(@id,':aTTRIBUTE7Id::_afrLovInternalQueryId:')]/following::button[3]")
	private WebElement ToAccPeriodOkBtn;
	// ----

	// ----From Period-----
	@FindBy(xpath = "//a[@title='Search and Select: From Period']")
	private WebElement fromPeriodDropDown;

	@FindBy(xpath = "//a[contains(@id,':periodFromID::dropdownPopup::popupsearch')]")
	private WebElement searchFromPeriod;

	@FindBy(xpath = "//input[@id=(//label[text()=' Period']/@for)]")
	private WebElement inputFromPeriod;

	@FindBy(xpath = "//input[@id=(//label[text()=' Period'])/@for]/following::table/tbody/tr/td[2]/div/table/tbody/tr")
	private WebElement searchFromPeriodResult;

	@FindBy(xpath = "//input[@id=(//label[text()=' Period'])/@for]/following::button[3]")
	private WebElement searchFromPeriodOKBtn;

	// ----To Period-----
	@FindBy(xpath = "//*[@title='Search and Select: To Period']")
	private WebElement toPeriodDropDown;

	@FindBy(xpath = "//a[contains(@id,':periodToID::dropdownPopup::popupsearch')]")
	private WebElement searchToPeriod;

	@FindBy(xpath = "//input[@id=(//label[text()=' Period']/@for)]")
	private WebElement inputToPeriod;

	@FindBy(xpath = "//input[@id=(//label[text()=' Period'])/@for]/following::table/tbody/tr/td[2]/div/table/tbody/tr")
	private WebElement searchToPeriodResult;

	@FindBy(xpath = "//input[@id=(//label[text()=' Period'])/@for]/following::button[3]")
	private WebElement searchToPeriodOKBtn;
	// ----

	@FindBy(xpath = "*//select[@id=(//label[text()='Data Access Set']/@for)]")
	private WebElement dataAccessSet;

	@FindBy(xpath = "*//select[@id=(//label[text()='Ledger']/@for)]")
	private WebElement selectLedgerElem;

	@FindBy(xpath = "*//select[@id=(//label[text()='Ledger Currency']/@for)]")
	private WebElement currency;

	@FindBy(xpath = "*//select[@id=(//label[text()='Currency Type']/@for)]")
	private WebElement currencyType;

	@FindBy(xpath = "*//select[@id=(//label[text()='Account Class']/@for)]")
	private WebElement accountClass;

	@FindBy(xpath = "*//select[@id=(//label[text()='Trial Balance Type']/@for)]")
	private WebElement balanceType;
	// -----
	// Methods

	public void clickOnSubmit() {
		wait.until(ExpectedConditions.visibilityOf(submitBtn)).click();
	}

	public void scheduleNewProcess(String processName) { // General Ledger Trial Balance Report
		wait.until(ExpectedConditions.visibilityOf(scheduleNewProcessBtn)).click();
		wait.until(ExpectedConditions.visibilityOf(searchProcessDropdown)).click();
		wait.until(ExpectedConditions.visibilityOf(searchLink)).click();
		wait.until(ExpectedConditions.visibilityOf(searchProcessInput)).sendKeys(processName, Keys.RETURN);
		wait.until(ExpectedConditions.visibilityOf(searchProcessResult)).click();
		wait.until(ExpectedConditions.visibilityOf(searchProcessOKBtn)).click();
		wait.until(ExpectedConditions.elementToBeClickable(scheduleNewProcessOKBtn)).click();
	}

	// ------
	public String getProcessId() {
		String cnfrmMsg = wait.until(ExpectedConditions.visibilityOf(confirmationMsg)).getText();
		String processId = TestUtilities.extractNumberFromText(cnfrmMsg);
		return processId;
	}

	public void clickOnConfirmationMsgOkBtn() {
		wait.until(ExpectedConditions.visibilityOf(confirmationMsgOkBtn)).click();
		TestUtilities.waitForElementInvisibility(confirmationMsgOkBtn);
	}
	// ---------------

	public boolean isProcessSucceeded(String processId) {
		try {
			wait.until(ExpectedConditions.visibilityOf(expandSearch)).click();
			wait.until(ExpectedConditions.visibilityOf(processIdInput)).clear();
			wait.until(ExpectedConditions.visibilityOf(processIdInput)).sendKeys(processId, Keys.RETURN);

			System.out.println("Waiting for the Process to Succeed...");
			System.out.println("Please don't close the Browser or Interfere with the Process.");
			Thread.sleep(3000);
			int failureCount = 0;
			String processStatus = "";
			while (true) {

				wait.until(ExpectedConditions.elementToBeClickable(refreshBtn)).click();
				Thread.sleep(20000);
				processStatus = processStatusElem.getText();
//				System.out.println(processStatus);
				failureCount++;
				if (processStatus.equals("Warning") || processStatus.equals("Succeeded")) {
					break;
				}
				if (processStatus.equals("Error")) {
					return false;
				}
				if (failureCount > 10) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
//			System.out.println(e);
			return false;
		}
	}

	public void highLightTheRow1() {
		processResultRow1.click();
	}
	// -------------

	public void switchToPopupWindow() {
		this.driver = TestUtilities.switchToPopupWindow(this.driver, this.mainWindowHandle);
//		Set<String> allWindowHandles = driver.getWindowHandles();
//		Iterator<String> iterator = allWindowHandles.iterator();
//		while (iterator.hasNext()) {
//			String ChildWindow = iterator.next();
//			if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {
//				driver.switchTo().window(ChildWindow);
////				String windowTitle = driver.getTitle();
////				System.out.println(windowTitle);
//			}
//		}
	}

	public void switchToMainWindow() {
		this.driver = TestUtilities.switchToMainWindow(this.driver, this.mainWindowHandle);
//		Set<String> allWindowHandles = driver.getWindowHandles();
//		Iterator<String> iterator2 = allWindowHandles.iterator();
//		while (iterator2.hasNext()) {
//			String childWindow = iterator2.next();
//			if (!mainWindowHandle.equalsIgnoreCase(childWindow)) {
//				driver.switchTo().window(childWindow);
//				driver.close();
//			}
//		}
//		driver.switchTo().window(mainWindowHandle);
	}

	public void waitForFileDwld() throws InterruptedException {
		DownloadFileHandler.waitForFileDownload(this.driver, this.wait, this.reportDwldMessage);
	}

	public void clickOnRepublishBtn() throws InterruptedException {
		TestUtilities.scrollBy(this.driver, 0, 500);
		Thread.sleep(10000);
		driver.switchTo().frame(wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Output']/following::iframe[1]"))));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("*//button[text()='Republish']"))).click();
	}

	public void exportReportToPDF() {
		wait.until(ExpectedConditions.visibilityOf(reportViewActions)).click();
		exportOption.click();
		exportToPDF.click();
	}

	public void selectDataAccessSet(String input) {
		Select s = new Select(wait.until(ExpectedConditions.visibilityOf(dataAccessSet)));
		s.selectByVisibleText(input);
	}

	public void selectLedger(String option) { // tc2_1
		Select s = new Select(wait.until(ExpectedConditions.visibilityOf(selectLedgerElem)));
		s.selectByVisibleText(option);
	}

	public void selectCurrency(String option) {
		Select s = new Select(currency);
		s.selectByVisibleText(option);
	}

	public void selectCurrencyType(String option) {
		Select s = new Select(currencyType);
		s.selectByVisibleText(option);
	}

	public void selectAccountClass(String option) {
		Select s = new Select(accountClass);
		s.selectByVisibleText(option);
	}

	public void selectBalanceType(String option) {
		Select s = new Select(balanceType);
		s.selectByVisibleText(option);
	}

	public void searchLedger(String ledger) {
		wait.until(ExpectedConditions.visibilityOf(ledgerSearchBtn)).click();
		wait.until(ExpectedConditions.visibilityOf(ledgerSearchLink)).click();
		wait.until(ExpectedConditions.visibilityOf(searchLedgerInputField)).clear();
		wait.until(ExpectedConditions.visibilityOf(searchLedgerInputField)).sendKeys(ledger, Keys.RETURN);
		wait.until(ExpectedConditions.visibilityOf(searchLedgerResult)).click();
		wait.until(ExpectedConditions.visibilityOf(searchLedgerOkBtn)).click();
		TestUtilities.waitForElementInvisibility(searchLedgerOkBtn);
	}

	public void searchFromAccountingPeriod(String ledger) {
		wait.until(ExpectedConditions.visibilityOf(fromAccPeriodSearchBtn)).click();
		wait.until(ExpectedConditions.visibilityOf(fromAccPeriodSearchLink)).click();
		wait.until(ExpectedConditions.visibilityOf(fromAccPeriodInputField)).clear();
		wait.until(ExpectedConditions.visibilityOf(fromAccPeriodInputField)).sendKeys(ledger, Keys.RETURN);
		wait.until(ExpectedConditions.visibilityOf(fromAccPeriodResult)).click();
		wait.until(ExpectedConditions.visibilityOf(fromAccPeriodOkBtn)).click();
		TestUtilities.waitForElementInvisibility(fromAccPeriodOkBtn);
	}

	public void searchToAccountingPeriod(String ledger) {
		wait.until(ExpectedConditions.visibilityOf(ToAccPeriodSearchBtn)).click();
		wait.until(ExpectedConditions.visibilityOf(ToAccPeriodSearchLink)).click();
		wait.until(ExpectedConditions.visibilityOf(ToAccPeriodInputField)).clear();
		wait.until(ExpectedConditions.visibilityOf(ToAccPeriodInputField)).sendKeys(ledger, Keys.RETURN);
		wait.until(ExpectedConditions.visibilityOf(ToAccPeriodResult)).click();
		wait.until(ExpectedConditions.visibilityOf(ToAccPeriodOkBtn)).click();
		TestUtilities.waitForElementInvisibility(ToAccPeriodOkBtn);
	}

	public void searchFromPeriod(String fromPeriod) {
		wait.until(ExpectedConditions.visibilityOf(fromPeriodDropDown)).click();
		wait.until(ExpectedConditions.visibilityOf(searchFromPeriod)).click();
		wait.until(ExpectedConditions.visibilityOf(inputFromPeriod)).clear();
		wait.until(ExpectedConditions.visibilityOf(inputFromPeriod)).sendKeys(fromPeriod, Keys.RETURN);
		wait.until(ExpectedConditions.visibilityOf(searchFromPeriodResult)).click();
		wait.until(ExpectedConditions.visibilityOf(searchFromPeriodOKBtn)).click();
		TestUtilities.waitForElementInvisibility(searchFromPeriodOKBtn);
	}

	public void searchToPeriod(String toPeriod) {
		wait.until(ExpectedConditions.visibilityOf(toPeriodDropDown)).click();
		wait.until(ExpectedConditions.visibilityOf(searchToPeriod)).click();
		wait.until(ExpectedConditions.visibilityOf(inputToPeriod)).clear();
		wait.until(ExpectedConditions.visibilityOf(inputToPeriod)).sendKeys(toPeriod, Keys.RETURN);
		wait.until(ExpectedConditions.visibilityOf(searchToPeriodResult)).click();
		wait.until(ExpectedConditions.visibilityOf(searchToPeriodOKBtn)).click();
		TestUtilities.waitForElementInvisibility(searchToPeriodOKBtn);
	}

}
