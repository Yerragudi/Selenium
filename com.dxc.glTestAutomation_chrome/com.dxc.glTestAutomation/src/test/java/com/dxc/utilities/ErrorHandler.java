package com.dxc.utilities;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ErrorHandler {

	public static void takeScreenshot(WebDriver driver, String imgName) {
		try {
			TakesScreenshot ss = ((TakesScreenshot) driver);
			File imgFile = ss.getScreenshotAs(OutputType.FILE);
			File DestFile = new File(
					ConfigFileHandler.getProperty("downloadFolderLocation") + "\\Error_Screenshots\\" + imgName);
			FileUtils.copyFile(imgFile, DestFile);
		} catch (Exception e) {
			throw new Error(e);
		}
	}

	public static String getTimestamp() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public static void assertEquals(String actualResult, String expectedResult, String errorMessage) throws Exception {
		if (!actualResult.equals(expectedResult)) {
			throw new Exception("com.dxc.AssertionError: " + errorMessage + " Expected [" + expectedResult
					+ "] but found [" + actualResult + "].");
		}
	}

	public static void assertTrue(boolean condition, String errorMessage) throws Exception {
		if (!condition) {
			throw new Exception("com.dxc.AssertionError: " + errorMessage);
		}
	}

	public static void logStatus(int excelRowNumber, String status, String msg) {
		if (msg == null) {
			msg = "";
		}
		if (msg.contains("com.dxc.AssertionError")) {
			String ERR_CODE = status.equals("PASS") ? "" : "ERR_1001";
			String ERR_MSG = "DXC_Assertion_ERR: " + msg.substring(45)
					+ " Please make appropriate changes into the Data File or Application Setup, if required; and re-run the Testcase.";
			ErrorHandler.logger(excelRowNumber, status, ERR_CODE, ERR_MSG);
		} else if (msg.contains("NoSuchElementException")) {
			String ERR_CODE = status.equals("PASS") ? "" : "ERR_1002";
			String ERR_MSG = "No_Such_Element_Exception: Unable to Find/Locate a certain Element. This generally happens due to slow Internet Connection. If issue persists, contact to Support Team.";
			ErrorHandler.logger(excelRowNumber, status, ERR_CODE, ERR_MSG);
		} else if (msg.contains("TimeoutException")) {
			String ERR_CODE = status.equals("PASS") ? "" : "ERR_1003";
			String ERR_MSG = "Timeout_Exception: The command did not complete in the specified time. Please check your Internet Connection or either increase the 'explicitWaitInSec' value in Config File.";
			ErrorHandler.logger(excelRowNumber, status, ERR_CODE, ERR_MSG);
		} else if (msg.contains("ElementNotVisibleException")) {
			String ERR_CODE = status.equals("PASS") ? "" : "ERR_1004";
			String ERR_MSG = "Element_Not_Visible_Exception: Element Present, But not Visible to Interact with. This may happen due to bad connectivity. Please try again!";
			ErrorHandler.logger(excelRowNumber, status, ERR_CODE, ERR_MSG);
		} else if (msg.contains("StaleElementReferenceException")) {
			String ERR_CODE = status.equals("PASS") ? "" : "ERR_1005";
			String ERR_MSG = "Stale_Element_Exception: This error occurs due to Unexpected Page Refresh. Please run the Testcase again.";
			ErrorHandler.logger(excelRowNumber, status, ERR_CODE, ERR_MSG);
		} else if (msg.contains("ElementClickInterceptedException")
				|| msg.contains("ElementNotInteractableException")) {
			String ERR_CODE = status.equals("PASS") ? "" : "ERR_1006";
			String ERR_MSG = "Element_Click_Intercepted_Exception: A click could not be properly executed because the target element was obscured in some way. Please try again!";
			ErrorHandler.logger(excelRowNumber, status, ERR_CODE, ERR_MSG);
		} else if (msg.contains("WebDriverException") || msg.contains("SessionNotFoundException")) {
			String ERR_CODE = status.equals("PASS") ? "" : "ERR_1007";
			String ERR_MSG = "WebDriver_Exception: Looks like you closed the browser in-between the exceution.";
			ErrorHandler.logger(excelRowNumber, status, ERR_CODE, ERR_MSG);
		} else if (msg.contains("NoSuchWindowException")) {
			String ERR_CODE = status.equals("PASS") ? "" : "ERR_1008";
			String ERR_MSG = "No_Such_Window_Exception: Either the Target window is closed or Webdriver is trying to switch to an invalid window, which is unavailable.";
			ErrorHandler.logger(excelRowNumber, status, ERR_CODE, ERR_MSG);
		} else if (msg.contains("NoSuchFrameException")) {
			String ERR_CODE = status.equals("PASS") ? "" : "ERR_1009";
			String ERR_MSG = "No_Such_Frame_Exception: Webdriver attempts to switch to an invalid frame, which is unavailable";
			ErrorHandler.logger(excelRowNumber, status, ERR_CODE, ERR_MSG);
		} else if (msg.contains("ElementNotSelectableException")) {
			String ERR_CODE = status.equals("PASS") ? "" : "ERR_1010";
			String ERR_MSG = "WebDriver_Exception: An element is disabled (can not be clicked/selected). Please check!";
			ErrorHandler.logger(excelRowNumber, status, ERR_CODE, ERR_MSG);
		} else {
			String ERR_CODE = status.equals("PASS") ? "" : "ERR_1000";
			String ERR_MSG = status.equals("PASS") ? ""
					: "Unexpected_Error:: Something went Wrong! Error Message: " + msg;
			ErrorHandler.logger(excelRowNumber, status, ERR_CODE, ERR_MSG);
		}
	}

	private static void logger(int excelRowNumber, String status, String errCode, String msg) {
		ExcelFileHandler.logTestResult(excelRowNumber, 1, status);
		ExcelFileHandler.logTestResult(excelRowNumber, 2, ErrorHandler.getTimestamp());
		ExcelFileHandler.logTestResult(excelRowNumber, 3, errCode);
		ExcelFileHandler.logTestResult(excelRowNumber, 4, msg);
	}
}
