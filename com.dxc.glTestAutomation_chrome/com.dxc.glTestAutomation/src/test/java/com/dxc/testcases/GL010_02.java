package com.dxc.testcases;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import com.dxc.utilities.ConfigFileHandler;
import com.dxc.utilities.ErrorHandler;
import com.dxc.utilities.ExcelFileHandler;
import com.dxc.pageobjects.LoginPage;
import com.dxc.pageobjects.HomePage;
import com.dxc.pageobjects.CreateAndEditJournalPage;
import com.dxc.pageobjects.JournalsPage;
import com.dxc.pageobjects.ManageJournalsPage;

public class GL010_02 {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	WebDriver driver;
	WebDriverWait wait;

	@Test
	public void testCase1002() throws InterruptedException {
		// variables and data
		final String INSTANCE_URL = ConfigFileHandler.getProperty("instanceURL");
		final String USERNAME = ConfigFileHandler.getProperty("adminUser");
		final String PASSWORD = ConfigFileHandler.getProperty("password");
		final int INPUT_DATA_WORKSHEET_ROW_NUMBER = Integer
				.parseInt(ConfigFileHandler.getProperty("excel_inputData_rowNum_tc1002"));
		final int STATUS_LOG_WORKSHEET_ROW_NUMBER = Integer
				.parseInt(ConfigFileHandler.getProperty("excel_logData_rowNum_tc1002"));

		try {
			List<String> cells = ExcelFileHandler.getCellsAt(INPUT_DATA_WORKSHEET_ROW_NUMBER);

			final int JOURNAL_COUNT = 2; // Number of Journal, that should be created!
			final String JOURNAL_BATCH = cells.get(1);
			final String DATA_ACCESS_SET = cells.get(2);
			// final String SECONDARY_LEDGER = cells.get(3);

			// Initialize driver
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get(INSTANCE_URL);

			// classes
			LoginPage loginPage = new LoginPage(driver);
			HomePage homePage = new HomePage(driver);
			JournalsPage journal = new JournalsPage(driver);
			ManageJournalsPage mj = new ManageJournalsPage(driver);
			CreateAndEditJournalPage cej = new CreateAndEditJournalPage(driver);

			// Test Execution
			loginPage.initLogin(USERNAME, PASSWORD);
			ErrorHandler.assertTrue(loginPage.isHomePage(), "Login Failed! Please check Login Credentials.");
			logger.info("Login to Application - Success.");

			homePage.clickOnNavigation();
			homePage.clickOnGeneralAccounting();
			homePage.clickOnJournals();

			journal.selectDataAccessSet(DATA_ACCESS_SET);
			Thread.sleep(3000);
			journal.clickOnTaskbar();
			journal.clickOnManageJournals();

			mj.searchAndSelectJournalByBatchName("Starts with", JOURNAL_BATCH); // test1234 //test1234_Copy1
			cej.clickOnPost();
			cej.clickOnPopupOkBtn();
			Thread.sleep(2000);
			ErrorHandler.assertEquals(cej.getApprovalStatus(), "In process", "Journal Approval Request Failed!");
			cej.clickOnCancel();

			for (int i = 1; i < JOURNAL_COUNT; i++) { // (JOURNAL_COUNT - 1) Iterations
				mj.selectJournal(JOURNAL_BATCH + "_Copy" + i); // test1234_Copy1
				cej.clickOnPost();
				cej.clickOnPopupOkBtn();
				Thread.sleep(2000);
				ErrorHandler.assertEquals(cej.getApprovalStatus(), "In process", "Journal Approval Request Failed!");
				cej.clickOnCancel();
			}
			ErrorHandler.logStatus(STATUS_LOG_WORKSHEET_ROW_NUMBER, "PASS", null);
		} catch (Exception e) {
			logger.error(e.toString());
			ErrorHandler.logStatus(STATUS_LOG_WORKSHEET_ROW_NUMBER, "FAIL", e.toString());
			ErrorHandler.takeScreenshot(this.driver, "cm_err_ss_tc1002.jpeg");
			Assert.fail(e.toString());
		} finally {
			driver.quit();
		}
	}
}