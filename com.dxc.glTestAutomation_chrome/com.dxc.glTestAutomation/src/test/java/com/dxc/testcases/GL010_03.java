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

import com.dxc.pageobjects.CreateAndEditJournalPage;
import com.dxc.pageobjects.HomePage;
import com.dxc.pageobjects.JournalsPage;
import com.dxc.pageobjects.LoginPage;
import com.dxc.pageobjects.ManageJournalsPage;
import com.dxc.pageobjects.NotificationsPage;
import com.dxc.utilities.ConfigFileHandler;
import com.dxc.utilities.ErrorHandler;
import com.dxc.utilities.ExcelFileHandler;

public class GL010_03 {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	WebDriver driver;
	WebDriverWait wait;

	@Test
	public void testCase1003() throws InterruptedException {
		// variables and data
		final String INSTANCE_URL = ConfigFileHandler.getProperty("instanceURL");
		final String USERNAME = ConfigFileHandler.getProperty("chiefFinOfficer"); // ANITA.KENNEDY
		final String PASSWORD = ConfigFileHandler.getProperty("password");
		final int INPUT_DATA_WORKSHEET_ROW_NUMBER = Integer
				.parseInt(ConfigFileHandler.getProperty("excel_inputData_rowNum_tc1003"));
		final int STATUS_LOG_WORKSHEET_ROW_NUMBER = Integer
				.parseInt(ConfigFileHandler.getProperty("excel_logData_rowNum_tc1003"));
		try {
			List<String> cells = ExcelFileHandler.getCellsAt(INPUT_DATA_WORKSHEET_ROW_NUMBER);

			final String JOURNAL_BATCH = cells.get(1);
			final String JOURNAL = cells.get(2);
			final String DATA_ACCESS_SET = cells.get(3);

			// Initializing driver
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get(INSTANCE_URL);

			// Classes
			LoginPage loginPage = new LoginPage(driver);
			HomePage homePage = new HomePage(driver);
			NotificationsPage notifications = new NotificationsPage(driver);
			JournalsPage journal = new JournalsPage(driver);
			ManageJournalsPage mj = new ManageJournalsPage(driver);
			CreateAndEditJournalPage cej = new CreateAndEditJournalPage(driver);

			// Test Execution
			loginPage.initLogin(USERNAME, PASSWORD);
			ErrorHandler.assertTrue(loginPage.isHomePage(), "Login Failed! Please check Login Credentials.");
			logger.info("Login to Application - Success.");

			homePage.clickOnNotifications();
			homePage.clickOnShowAll();

//			notifications.searchTask(JOURNAL_BATCH); // test1234
			notifications.isNotificationPage();
			notifications.clickOnApprove(JOURNAL_BATCH);

			homePage.clickOnNavigation();
			homePage.clickOnGeneralAccounting();
			homePage.clickOnJournals();

			journal.selectDataAccessSet(DATA_ACCESS_SET);

			journal.clickOnTaskbar();
			journal.clickOnManageJournals();

			mj.searchAndSelectJournalByName("Equals", JOURNAL_BATCH, JOURNAL); // test1234
			ErrorHandler.assertEquals(cej.getApprovalStatus(), "Approved", "Journal Approval Failed!");
			Thread.sleep(1000);
			ErrorHandler.logStatus(STATUS_LOG_WORKSHEET_ROW_NUMBER, "PASS", null);
		} catch (Exception e) {
			logger.error(e.toString());
			ErrorHandler.logStatus(STATUS_LOG_WORKSHEET_ROW_NUMBER, "FAIL", e.toString());
			ErrorHandler.takeScreenshot(this.driver, "cm_err_ss_tc1003.jpeg");
			Assert.fail(e.toString());
		} finally {
			driver.quit();
		}
	}
}