package com.dxc.testcases;

import java.util.List;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.bonigarcia.wdm.WebDriverManager;

import com.dxc.utilities.ConfigFileHandler;
import com.dxc.utilities.ErrorHandler;
import com.dxc.utilities.ExcelFileHandler;
import com.dxc.pageobjects.LoginPage;
import com.dxc.pageobjects.ManageJournalsPage;
import com.dxc.pageobjects.CreateAndEditJournalPage;
import com.dxc.pageobjects.HomePage;
import com.dxc.pageobjects.JournalsPage;

public class GL010_05 {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	WebDriver driver;
	WebDriverWait wait;

	@Test
	public void testCase1005() {
		// variables and data input
		final String INSTANCE_URL = ConfigFileHandler.getProperty("instanceURL");
		final String USER_NAME = ConfigFileHandler.getProperty("adminUser");
		final String USER_PASSWORD = ConfigFileHandler.getProperty("password");
		final int INPUT_DATA_WORKSHEET_ROW_NUMBER = Integer
				.parseInt(ConfigFileHandler.getProperty("excel_inputData_rowNum_tc1005"));
		final int STATUS_LOG_WORKSHEET_ROW_NUMBER = Integer
				.parseInt(ConfigFileHandler.getProperty("excel_logData_rowNum_tc1005"));

		try {
			List<String> cells = ExcelFileHandler.getCellsAt(INPUT_DATA_WORKSHEET_ROW_NUMBER);

			final String JOURNAL_BATCH = cells.get(1);
			final String BATCH_DESCRIPTION = cells.get(2);
			final String ACCOUNTING_PERIOD = cells.get(3);
			final String JOURNAL = cells.get(4);
			final String JOURNAL_DESCRIPTION = cells.get(5);
			final String LEDGER = cells.get(6);
			final String LEGAL_ENTITY = cells.get(7);
			final String ACCOUNTING_DATE = cells.get(8);
			final String CATEGORY = cells.get(9);
			final String CURRENCY = cells.get(10);
			final String DEBIT_ACCOUNT = cells.get(11);
			final String CREDIT_ACCOUNT = cells.get(12); // New Addition - Please update the excel sheet
			final String DEBIT_AMOUNT = cells.get(13);
			final String CREDIT_AMOUNT = cells.get(13);
			final String INVALID_CREDIT_AMOUNT = cells.get(14);
			final String DATA_ACCESS_SET = cells.get(15);
			final String SECONDARY_LEDGER = cells.get(16);

			// Initialize driver
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get(INSTANCE_URL);

			// Instantiate Classes
			LoginPage loginPage = new LoginPage(driver);
			CreateAndEditJournalPage cej = new CreateAndEditJournalPage(driver);
			HomePage homePage = new HomePage(driver);
			JournalsPage jp = new JournalsPage(driver);
			ManageJournalsPage mj = new ManageJournalsPage(driver);

			// Test Execution
			loginPage.initLogin(USER_NAME, USER_PASSWORD);
			ErrorHandler.assertTrue(loginPage.isHomePage(), "Login Failed! Please check Login Credentials.");
			logger.info("Login to Application - Success.");

			homePage.clickOnNavigation();
			homePage.clickOnGeneralAccounting();
			homePage.clickOnJournals();

			jp.selectDataAccessSet(DATA_ACCESS_SET);
			Thread.sleep(3000);
			jp.clickOnTaskbar();
			jp.clickOnCreateJournal();

			cej.enterJournalBatch(JOURNAL_BATCH);
			cej.enterBatchDescription(BATCH_DESCRIPTION);
			Thread.sleep(2000);
			cej.enterAccountingPeriod(ACCOUNTING_PERIOD);
			Thread.sleep(2000);
			cej.enterJournal(JOURNAL);
			cej.enterJournalDescription(JOURNAL_DESCRIPTION);
			Thread.sleep(2000);
			cej.enterLedger(LEDGER);
			Thread.sleep(2000);
			cej.enterLegalEntityName(LEGAL_ENTITY); // optional: System Dependent
			cej.enterAccountingDate(ACCOUNTING_DATE);
			Thread.sleep(3000);
			cej.enterCategory(CATEGORY);
			cej.enterCurrency(CURRENCY);

			Thread.sleep(4000);

			// cej.clickOnJournalLine(1);
			// Thread.sleep(5000);
			cej.enterAccount(DEBIT_ACCOUNT, 1);
			cej.enterDebitAmount(DEBIT_AMOUNT, 1);

			cej.clickOnJournalLine(2);
			Thread.sleep(10000);
			cej.enterAccount(CREDIT_ACCOUNT, 2);
			cej.enterCreditAmount(INVALID_CREDIT_AMOUNT, 2);

			Thread.sleep(3000);
			cej.clickOnSave();
			cej.clickOnPopupNo();
			cej.enterCreditAmount(CREDIT_AMOUNT, 2);

			Thread.sleep(3000);
			cej.clickOnSave();
			Thread.sleep(10000);
			cej.clickOnComplete();
			Thread.sleep(10000);
			ErrorHandler.assertEquals(cej.getCompletionStatus(), "Complete", "Journal Completion Failed!");

			cej.clickOnPost();
			cej.clickOnPopupOkBtn();
			Thread.sleep(1000);
			ErrorHandler.assertEquals(cej.getBatchStatus(), "Posted", "Journal not posted!");
			Thread.sleep(1000);
			cej.clickOnCancel();

			// ----- Secondary Ledger --------
			jp.selectDataAccessSet(SECONDARY_LEDGER);
			Thread.sleep(3000);
			jp.clickOnTaskbar();
			jp.clickOnManageJournals();

			mj.searchAndSelectJournalByName("Contains", JOURNAL_BATCH, JOURNAL);

			cej.clickOnPost();
			cej.clickOnPopupOkBtn();
			Thread.sleep(1000);
			ErrorHandler.assertEquals(cej.getBatchStatus(), "Posted", "Journal not posted!");
			Thread.sleep(1000);
			ErrorHandler.logStatus(STATUS_LOG_WORKSHEET_ROW_NUMBER, "PASS", null);

			// Support Data for Test case 1007
			final int EXCEL_INPUT_DATA_TC1007 = Integer
					.parseInt(ConfigFileHandler.getProperty("excel_inputData_rowNum_tc1007"));

			ExcelFileHandler.writeInputDataAt(EXCEL_INPUT_DATA_TC1007, 4, JOURNAL_BATCH);
			ExcelFileHandler.writeInputDataAt(EXCEL_INPUT_DATA_TC1007, 5, JOURNAL);

		} catch (Exception e) {
			logger.error(e.toString());
			ErrorHandler.logStatus(STATUS_LOG_WORKSHEET_ROW_NUMBER, "FAIL", e.toString());
			ErrorHandler.takeScreenshot(this.driver, "cm_err_ss_tc1005.jpeg");
			Assert.fail(e.toString());
		} finally {
			driver.quit();
		}
	}
}