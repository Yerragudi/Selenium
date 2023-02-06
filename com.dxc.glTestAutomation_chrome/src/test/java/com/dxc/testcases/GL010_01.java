package com.dxc.testcases;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dxc.utilities.ConfigFileHandler;
import com.dxc.utilities.ErrorHandler;
import com.dxc.utilities.ExcelFileHandler;
import com.dxc.pageobjects.LoginPage;
import com.dxc.pageobjects.HomePage;
import com.dxc.pageobjects.JournalsPage;
import com.dxc.pageobjects.CreateAndEditJournalPage;

public class GL010_01 {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	WebDriver driver;
	WebDriverWait wait;

	@Test
	public void testCase1001() {
		// variables and data input
		final String INSTANCE_URL = ConfigFileHandler.getProperty("instanceURL");
		final String USER_NAME = ConfigFileHandler.getProperty("generalAccountingManager");
		final String USER_PASSWORD = ConfigFileHandler.getProperty("password");
		final int INPUT_DATA_WORKSHEET_ROW_NUMBER = Integer
				.parseInt(ConfigFileHandler.getProperty("excel_inputData_rowNum_tc1001"));
		final int STATUS_LOG_WORKSHEET_ROW_NUMBER = Integer
				.parseInt(ConfigFileHandler.getProperty("excel_logData_rowNum_tc1001"));

		try {
			List<String> cells = ExcelFileHandler.getCellsAt(INPUT_DATA_WORKSHEET_ROW_NUMBER);

			final int JOURNAL_COUNT = 2; // Number of Journal, that should be created!
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
			final String CREDIT_ACCOUNT = cells.get(12);
			final String DEBIT_AMOUNT = cells.get(13);
			final String CREDIT_AMOUNT = cells.get(13);
			final String DATA_ACCESS_SET = cells.get(14);

			// Initialize driver
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get(INSTANCE_URL);

			// Instantiate Classes
			LoginPage loginPage = new LoginPage(driver);
			HomePage homePage = new HomePage(driver);
			JournalsPage jp = new JournalsPage(driver);
			CreateAndEditJournalPage cej = new CreateAndEditJournalPage(driver);

			// Test Execution
			loginPage.initLogin(USER_NAME, USER_PASSWORD);
			ErrorHandler.assertTrue(loginPage.isHomePage(), "Login Failed! Please check Login Credentials.");
			logger.info("Login to Application - Success.");

			homePage.clickOnNavigation();
			homePage.clickOnGeneralAccounting();
			homePage.clickOnJournals();

			Thread.sleep(30000);
			jp.selectDataAccessSet(DATA_ACCESS_SET);
			Thread.sleep(3000);
			jp.clickOnTaskbar();
			jp.clickOnCreateJournal();

			cej.enterJournalBatch(JOURNAL_BATCH);
			cej.enterBatchDescription(BATCH_DESCRIPTION);
			Thread.sleep(2000);
			cej.enterAccountingPeriod(ACCOUNTING_PERIOD);
			Thread.sleep(5000);
			cej.enterJournal(JOURNAL);
			cej.enterJournalDescription(JOURNAL_DESCRIPTION);
			Thread.sleep(2000);
			cej.enterLedger(LEDGER);
			Thread.sleep(2000);
			cej.enterLegalEntityName(LEGAL_ENTITY); // optional: System Dependent
			cej.enterAccountingDate(ACCOUNTING_DATE);
			Thread.sleep(4000);
			cej.enterCategory(CATEGORY);
			cej.enterCurrency(CURRENCY);
			Thread.sleep(2000);

			// cej.clickOnJournalLine(1);
			// Thread.sleep(10000);
			cej.enterAccount(DEBIT_ACCOUNT, 1);
			cej.enterDebitAmount(DEBIT_AMOUNT, 1);

			cej.clickOnJournalLine(2);
			Thread.sleep(10000);
			cej.enterAccount(CREDIT_ACCOUNT, 2);
			cej.enterCreditAmount(CREDIT_AMOUNT, 2);

			Thread.sleep(3000);
			cej.clickOnSave();
			Thread.sleep(10000);
			cej.clickOnComplete();
			Thread.sleep(3000);
			ErrorHandler.assertEquals(cej.getCompletionStatus(), "Complete", "Journal Completion Failed.");
			logger.info("Journal Created and Completed Successfully.");

			for (int i = 1; i < JOURNAL_COUNT; i++) { // (JOURNAL_COUNT - 1) Iterations
				cej.clickOnBatchActions();
				cej.clickOnCopyOption();
				cej.enterCopyJournalBatch(JOURNAL_BATCH + "_Copy" + i); // test1234 //test1234_Copy1
				// cej.enterCopyAccountingPeriod(ACCOUNTING_PERIOD);
				// cej.enterCopyAccountingDate(ACCOUNTING_DATE);
				cej.clickOnPopupOkBtn();

				cej.clickOnSave();
				Thread.sleep(10000);
				cej.clickOnComplete();
				Thread.sleep(3000);
				ErrorHandler.assertEquals(cej.getCompletionStatus(), "Complete", "Journal Completion Failed!");
			}
			ErrorHandler.logStatus(STATUS_LOG_WORKSHEET_ROW_NUMBER, "PASS", null);
			logger.info("Test case PASS: A New Journal Created and status updated to Complete.");

			// Support Data for Next Test cases
			final int EXCEL_INPUT_DATA_TC1002 = Integer
					.parseInt(ConfigFileHandler.getProperty("excel_inputData_rowNum_tc1002"));
			final int EXCEL_INPUT_DATA_TC1003 = Integer
					.parseInt(ConfigFileHandler.getProperty("excel_inputData_rowNum_tc1003"));
			final int EXCEL_INPUT_DATA_TC1004 = Integer
					.parseInt(ConfigFileHandler.getProperty("excel_inputData_rowNum_tc1004"));
			final int EXCEL_INPUT_DATA_TC1006 = Integer
					.parseInt(ConfigFileHandler.getProperty("excel_inputData_rowNum_tc1006"));

			ExcelFileHandler.writeInputDataAt(EXCEL_INPUT_DATA_TC1002, 1, JOURNAL_BATCH);

			ExcelFileHandler.writeInputDataAt(EXCEL_INPUT_DATA_TC1003, 1, JOURNAL_BATCH);
			ExcelFileHandler.writeInputDataAt(EXCEL_INPUT_DATA_TC1003, 2, JOURNAL);

			ExcelFileHandler.writeInputDataAt(EXCEL_INPUT_DATA_TC1004, 1, JOURNAL_BATCH);

			ExcelFileHandler.writeInputDataAt(EXCEL_INPUT_DATA_TC1006, 1, JOURNAL_BATCH);
			ExcelFileHandler.writeInputDataAt(EXCEL_INPUT_DATA_TC1006, 2, JOURNAL);
			logger.info("Required Data passed to next testcases: TC2, TC3, TC4, and TC6.");

		} catch (Exception e) {
			logger.error(e.toString());
			ErrorHandler.logStatus(STATUS_LOG_WORKSHEET_ROW_NUMBER, "FAIL", e.toString());
			ErrorHandler.takeScreenshot(this.driver, "cm_err_ss_tc1001.jpeg");
			Assert.fail(e.toString());
		} finally {
			driver.quit();
		}
	}
}
