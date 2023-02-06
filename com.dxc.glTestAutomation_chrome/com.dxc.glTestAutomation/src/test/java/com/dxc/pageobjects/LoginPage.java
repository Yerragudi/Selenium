package com.dxc.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	private WebDriver driver;

	// Constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);// Initialize Elements
	}

	@FindBy(id = "userid")
	private WebElement userId;

	@FindBy(id = "password")
	private WebElement password;

	@FindBy(id = "btnActive")
	private WebElement SignInbtn;

	public void initLogin(String user_id, String user_password) {
		userId.sendKeys(user_id);
		password.sendKeys(user_password);
		SignInbtn.click();
	}

	public boolean isHomePage() {
		System.out.println("Test Script designed and developed by DXC Technology. Confidential Information.");
//		System.out.println(driver.getTitle());
		return driver.getTitle().toString().equals("Welcome");
	}
}
