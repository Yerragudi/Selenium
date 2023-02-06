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
import com.dxc.pageobjects.RunAutoReversePage;
import com.dxc.pageobjects.ScheduledProcessesPage;
import com.dxc.pageobjects.CreateAndEditJournalPage;
import com.dxc.pageobjects.HomePage;
import com.dxc.pageobjects.JournalsPage;

public class GL010_07 {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	WebDriver driver;
	WebDriverWait wait;

	@Test
	public void testCase1007() {

		// variables and data input
		final String INSTANCE_URL = ConfigFileHandler.getProperty("instanceURL");
		final String USER_NAME = ConfigFileHandler.getProperty("generalAccountingManager");
		final String USER_PASSWORD = ConfigFileHandler.getProperty("password");
		final int INPUT_DATA_WORKSHEET_ROW_NUMBER = Integer
				.parseInt(ConfigFileHandler.getProperty("excel_inputData_rowNum_tc1007"));
		final int STATUS_LOG_WORKSHEET_ROW_NUMBER = Integer
				.parseInt(ConfigFileHandler.getProperty("excel_logData_rowNum_tc1007"));
		try {
			List<String> cells = ExcelFileHandler.getCellsAt(INPUT_DATA_WORKSHEET_ROW_NUMBER);

			final String DATA_ACCESS_SET = cells.get(1);
			final String LEDGER = cells.get(2);
			final String REVERSAL_PERIOD = cells.get(3);
			final String JOURNAL_BATCH = cells.get(4); // Previously Not Reversed
			final String JOURNAL = cells.get(5);

			// Initialize driver
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get(INSTANCE_URL);

			// Instantiate Classes
			LoginPage loginPage = new LoginPage(driver);
			HomePage homePage = new HomePage(driver);
			JournalsPage jp = new JournalsPage(driver);
			RunAutoReversePage rar = new RunAutoReversePage(driver);
			ScheduledProcessesPage sp = new ScheduledProcessesPage(driver);
			ManageJournalsPage mj = new ManageJournalsPage(driver);
			CreateAndEditJournalPage cej = new CreateAndEditJournalPage(driver);

			// Test Execution
			loginPage.initLogin(USER_NAME, USER_PASSWORD);
			ErrorHandler.assertTrue(loginPage.isHomePage(), "Login Failed! Please check Login Credentials.");
			logger.info("Login to Application - Success.");

			homePage.clickOnNavigation();
			homePage.clickOnGeneralAccounting();
			homePage.clickOnJournals();

			Thread.sleep(9000);
			jp.selectDataAccessSet(DATA_ACCESS_SET);
			Thread.sleep(2000);

			jp.clickOnTaskbar();
			jp.clickOnRunAutoReverse();

			rar.selectDataAccessSet(DATA_ACCESS_SET);
			rar.selectLedger(LEDGER);
			rar.selectReversalPeriod(REVERSAL_PERIOD);
			rar.clickOnSubmit();
			String processId = rar.getProcessId();
			rar.clickOnConfirmationMsgOkBtn();

			Thread.sleep(2000);

			homePage.clickOnNavigation();
			homePage.clickOnTools();
			homePage.clickOnScheduledProcesses();
			ErrorHandler.assertTrue(sp.isProcessSucceeded(processId), "ESS Job Error - Process did not Succeed!");
			// ------------------------

			Thread.sleep(2000);
			homePage.clickOnNavigation();
			homePage.clickOnJournals();
			jp.clickOnTaskbar();
			jp.clickOnManageJournals();

			mj.searchAndSelectJournalByName("Equals", JOURNAL_BATCH, JOURNAL);

			cej.clickOnJournalShowMore();
			cej.clickOnReversalTab();
			ErrorHandler.assertEquals(cej.getReversalStatus(), "Reversed", "Journal Reversal Failed!");
			Thread.sleep(2000);
			ErrorHandler.logStatus(STATUS_LOG_WORKSHEET_ROW_NUMBER, "PASS", null);
		} catch (Exception e) {
			logger.error(e.toString());
			ErrorHandler.logStatus(STATUS_LOG_WORKSHEET_ROW_NUMBER, "FAIL", e.toString());
			ErrorHandler.takeScreenshot(this.driver, "cm_err_ss_tc1007.jpeg");
			Assert.fail(e.toString());
		} finally {
			driver.quit();
		}
	}
}