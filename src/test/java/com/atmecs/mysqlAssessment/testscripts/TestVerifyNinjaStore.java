package com.atmecs.mysqlAssessment.testscripts;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.atmecs.mysqlAssessment.constants.FileConstants;
import com.atmecs.mysqlAssessment.helper.JavaScriptHelper;
import com.atmecs.mysqlAssessment.helper.SeleniumHelper;
import com.atmecs.mysqlAssessment.helper.ValidaterHelper;
import com.atmecs.mysqlAssessment.helper.WaitForElement;
import com.atmecs.mysqlAssessment.helper.WebTableHelper;
import com.atmecs.mysqlAssessment.logreports.LogReporter;
import com.atmecs.mysqlAssessment.pages.NinjaStroreHomePage;
import com.atmecs.mysqlAssessment.testbase.TestBase;
import com.atmecs.mysqlAssessment.utils.DataBaseReader;
import com.atmecs.mysqlAssessment.utils.PropertiesReader;

public class TestVerifyNinjaStore extends TestBase {
	WaitForElement waitobject=new WaitForElement();
	LogReporter log=new LogReporter();
	ValidaterHelper validatehelp=new ValidaterHelper();
	SeleniumHelper seleniumhelp=new SeleniumHelper();
	PropertiesReader propread=new PropertiesReader();
	NinjaStroreHomePage ninjapage=new NinjaStroreHomePage();
	JavaScriptHelper javascript=new JavaScriptHelper();
	WebTableHelper webtable=new WebTableHelper();
	DataBaseReader database=new DataBaseReader();
	
	String grandTotalAfter,expectedGrandTotal,expectedTotalAfter,grandTotal,expectedMacBookText,macBookText,expectedIphoneText,iphoneText,expectedPageTitle;
	
	@BeforeClass
	public void webURLLoader() {
		driver.get(prop.getProperty("ninjastoreURL"));
		waitobject.waitForPageLoadTime(driver);
		driver.manage().window().maximize();
		log.logReportMessageBase("STEP 2: URL is loaed"+ driver.getCurrentUrl());
	}
	@Test
	public void verifyNinjaStore() throws IOException, InterruptedException, ClassNotFoundException, SQLException
	{
		Properties prop1=propread.keyValueLoader(FileConstants.NINJALOCATORS_PATH);
		log.logReportMessage("STEP 3: Validating the Home Page Title");
		database.getCellData("ninjaStoreTestSata", "Home_Page_Title", "Ts-01");
				expectedPageTitle=database.getCellData("ninjastoretestsata", "Home_Page_Title", "Ts-01");
				validatehelp.assertValidater(driver.getTitle(),expectedPageTitle);
				
		log.logReportMessage("STEP 4: Changing the currency to Dollar");
				ninjapage.verifyNinjaStoreProduct(driver,prop1);
				
		log.logReportMessage("STEP 13: Click the Cart link");
				seleniumhelp.clickElement(driver,prop1.getProperty("loc.btn.cart"));
				log.logReportMessage("Successfully navigate to the cart");
				
		log.logReportMessage("STEP 14: Validate the cart Product");
				iphoneText=validatehelp.getText(driver, prop1.getProperty("loc.txt.iphone"));
				expectedIphoneText=database.getCellData("ninjastoretestsata", "iPhone_Name", "Ts-01");
				validatehelp.assertValidater(iphoneText,expectedIphoneText);
				macBookText=validatehelp.getText(driver, prop1.getProperty("loc.txt.imacbook"));
				expectedMacBookText=database.getCellData("ninjastoretestsata", "Mac_Book_Name", "Ts-01");
				validatehelp.assertValidater(macBookText,expectedMacBookText);
				
		log.logReportMessage("STEP 15: Validate the Grand Total Before");
				grandTotal=validatehelp.getText(driver, prop1.getProperty("loc.txt.grandtotal"));
				expectedGrandTotal=database.getCellData("ninjastoretestsata", "Grand_Total", "Ts-01");
				validatehelp.assertValidater(grandTotal.substring(1, grandTotal.length()),expectedGrandTotal);
				
		log.logReportMessage("STEP 16: Remove the Product From the Cart");
				seleniumhelp.clickElement(driver, prop1.getProperty("loc.btn.remove"));
				log.logReportMessage("Product is Removed");
				
		log.logReportMessage("STEP 17: Validate the Grand Total After");
				waitobject.waitForInvisibilityOfElementLocated(driver, prop1.getProperty("loc.spinner.visibility"));
				grandTotalAfter=validatehelp.getText(driver, prop1.getProperty("loc.txt.grandtotal"));
				expectedTotalAfter=database.getCellData("ninjastoretestsata", "After_Grand_Total", "Ts-01");
				validatehelp.assertValidater(grandTotalAfter.substring(1, grandTotalAfter.length()),expectedTotalAfter);
	}
}
