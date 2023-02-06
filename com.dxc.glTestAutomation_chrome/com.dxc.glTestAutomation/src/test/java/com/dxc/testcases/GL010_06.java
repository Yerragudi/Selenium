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
import com.dxc.pageobjects.CreateAndEditJournalPage;
import com.dxc.pageobjects.HomePage;
import com.dxc.pageobjects.JournalsPage;
import com.dxc.pageobjects.LoginPage;
import com.dxc.pageobjects.ManageJournalsPage;

public class GL010_06 {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	WebDriver driver;
	WebDriverWait wait;

	@Test
	public void testCase1006() {

		// variables and data
		final String INSTANCE_URL = ConfigFileHandler.getProperty("instanceURL");
		final String USERNAME = ConfigFileHandler.getProperty("adminUser");
		final String PASSWORD = ConfigFileHandler.getProperty("password");
		final int INPUT_DATA_WORKSHEET_ROW_NUMBER = Integer
				.parseInt(ConfigFileHandler.getProperty("excel_inputData_rowNum_tc1006"));
		final int STATUS_LOG_WORKSHEET_ROW_NUMBER = Integer
				.parseInt(ConfigFileHandler.getProperty("excel_logData_rowNum_tc1006"));
		try {
			List<String> cells = ExcelFileHandler.getCellsAt(INPUT_DATA_WORKSHEET_ROW_NUMBER);

			final String JOURNAL_BATCH = cells.get(1);
			final String JOURNAL = cells.get(2);
			final String REVERSAL_PERIOD = cells.get(3);
			final String DATA_ACCESS_SET = cells.get(4);
			final String SECONDARY_LEDGER = cells.get(5);

			// Initializing driver
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get(INSTANCE_URL);

			// classes
			HomePage homePage = new HomePage(driver);
			LoginPage loginPage = new LoginPage(driver);
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

			journal.selectDataAccessSet(SECONDARY_LEDGER);
			Thread.sleep(2000);
			journal.clickOnTaskbar();
			journal.clickOnManageJournals();
			mj.searchAndSelectJournalByName("Contains", JOURNAL_BATCH, JOURNAL);

			cej.clickOnPost();
			cej.clickOnPopupOkBtn();
			Thread.sleep(2000);
			ErrorHandler.assertEquals(cej.getBatchStatus(), "Posted", "Journal Approval Request Failed!");
			Thread.sleep(1000);
			cej.clickOnCancel();
			mj.clickOnDoneBtn();
			Thread.sleep(1000);

			journal.selectDataAccessSet(DATA_ACCESS_SET);
			Thread.sleep(3000);
			journal.clickOnTaskbar();
			journal.clickOnManageJournals();

			mj.searchAndSelectJournalByName("Equals", JOURNAL_BATCH, JOURNAL);

			cej.clickOnJournalShowMore();
			cej.clickOnReversalTab();
			Thread.sleep(3000);
			cej.enterReversalPeriod(REVERSAL_PERIOD);
			Thread.sleep(2000);
			cej.clickOnSave();
			Thread.sleep(6000);

			cej.clickOnJournalActions();
			Thread.sleep(1000);
			cej.clickOnReverse();
			cej.clickOnReverseConfirmBtn();
			ErrorHandler.assertEquals(cej.getReversalStatus(), "Reversed", "Journal Reversal Failed!");
			Thread.sleep(1000);

			ErrorHandler.logStatus(STATUS_LOG_WORKSHEET_ROW_NUMBER, "PASS", null);
		} catch (Exception e) {
			logger.error(e.toString());
			ErrorHandler.logStatus(STATUS_LOG_WORKSHEET_ROW_NUMBER, "FAIL", e.toString());
			ErrorHandler.takeScreenshot(this.driver, "cm_err_ss_tc1006.jpeg");
			Assert.fail(e.toString());
		} finally {
			driver.quit();
		}
	}
}