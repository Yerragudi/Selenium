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
import com.dxc.utilities.ExcelFileHandler;
import com.dxc.utilities.DownloadFileHandler;
import com.dxc.utilities.ErrorHandler;
import com.dxc.pageobjects.HomePage;
import com.dxc.pageobjects.LoginPage;
import com.dxc.pageobjects.ScheduledProcessesPage;

public class GL020_02 {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	WebDriver driver;
	WebDriverWait wait;

	@Test
	public void testCase2002() {

		// variables
		final String INSTANCE_URL = ConfigFileHandler.getProperty("instanceURL");
		final String USERNAME = ConfigFileHandler.getProperty("generalAccountingManager");
		final String PASSWORD = ConfigFileHandler.getProperty("password");
		final int INPUT_DATA_WORKSHEET_ROW_NUMBER = Integer
				.parseInt(ConfigFileHandler.getProperty("excel_inputData_rowNum_tc2002"));
		final int STATUS_LOG_WORKSHEET_ROW_NUMBER = Integer
				.parseInt(ConfigFileHandler.getProperty("excel_logData_rowNum_tc2002"));
		try {
			List<String> cells = ExcelFileHandler.getCellsAt(INPUT_DATA_WORKSHEET_ROW_NUMBER);

			final String PROCESS_NAME = "Account Analysis Report"; // cells.get(1);
			final String LEDGER = cells.get(2);
			final String FROM_DATE = cells.get(3);
			final String TO_DATE = cells.get(4);
			final String FILE_NAME = "AccountAnalysis_Account Analysis Report.pdf"; // cells.get(5);

			// Initiate driver
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get(INSTANCE_URL);

			// classes
			LoginPage loginPage = new LoginPage(driver);
			HomePage homePage = new HomePage(driver);
			ScheduledProcessesPage process = new ScheduledProcessesPage(driver);

			// Test Execution
			loginPage.initLogin(USERNAME, PASSWORD);
			ErrorHandler.assertTrue(loginPage.isHomePage(), "Login Failed! Please check Login Credentials.");
			logger.info("Login to Application - Success.");

			homePage.clickOnNavigation();
			homePage.clickOnTools();
			homePage.clickOnScheduledProcesses();

			process.scheduleNewProcess(PROCESS_NAME);
			process.searchLedger(LEDGER);
			process.searchFromAccountingPeriod(FROM_DATE);
			process.searchToAccountingPeriod(TO_DATE);
			Thread.sleep(1000);
			process.clickOnSubmit();

			String processId = process.getProcessId();
			process.clickOnConfirmationMsgOkBtn();
			ErrorHandler.assertTrue(process.isProcessSucceeded(processId), "ESS Job Error - Process did not Succeed!");

			process.highLightTheRow1();
			Thread.sleep(6000);
			process.clickOnRepublishBtn();
			process.switchToPopupWindow();
			Thread.sleep(3000);
			process.exportReportToPDF();
			Thread.sleep(3000);
			process.waitForFileDwld();
			Thread.sleep(3000);
			boolean isFileDownloded = DownloadFileHandler.isFileDownloaded(FILE_NAME);

			process.switchToMainWindow();
			ErrorHandler.assertTrue(isFileDownloded, "File Download Failed!");
			ErrorHandler.logStatus(STATUS_LOG_WORKSHEET_ROW_NUMBER, "PASS", null);
		} catch (Exception e) {
			logger.error(e.toString());
			ErrorHandler.logStatus(STATUS_LOG_WORKSHEET_ROW_NUMBER, "FAIL", e.toString());
			ErrorHandler.takeScreenshot(this.driver, "cm_err_ss_tc2002.jpeg");
			Assert.fail(e.toString());
		} finally {
			driver.quit();
		}
	}
}