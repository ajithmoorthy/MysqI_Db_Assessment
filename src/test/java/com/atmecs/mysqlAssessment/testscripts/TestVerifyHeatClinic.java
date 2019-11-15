package com.atmecs.mysqlAssessment.testscripts;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.atmecs.mysqlAssessment.constants.FileConstants;
import com.atmecs.mysqlAssessment.helper.JavaScriptHelper;
import com.atmecs.mysqlAssessment.helper.SeleniumHelper;
import com.atmecs.mysqlAssessment.helper.ValidaterHelper;
import com.atmecs.mysqlAssessment.helper.WaitForElement;
import com.atmecs.mysqlAssessment.helper.WebTableHelper;
import com.atmecs.mysqlAssessment.logreports.LogReporter;
import com.atmecs.mysqlAssessment.pages.HeatClinicHomePage;
import com.atmecs.mysqlAssessment.testbase.TestBase;
import com.atmecs.mysqlAssessment.utils.DataBaseReader;
import com.atmecs.mysqlAssessment.utils.PropertiesReader;



public class TestVerifyHeatClinic extends TestBase{
	WaitForElement waitobject=new WaitForElement();
	LogReporter log=new LogReporter();
	ValidaterHelper validatehelp=new ValidaterHelper();
	SeleniumHelper seleniumhelp=new SeleniumHelper();
	PropertiesReader propread=new PropertiesReader();
	JavaScriptHelper javascript=new JavaScriptHelper();
	WebTableHelper webtable=new WebTableHelper();
	DataBaseReader database=new DataBaseReader();
	HeatClinicHomePage heatclinic=new HeatClinicHomePage();
	
	String expectedProductPriceAfter, expectedTotalPriceAfter,productQty,expectedProductPrice,expectedTotalPrice,productName;
	String viewingMensText,expectedViewingText,mensPageTitle,inputName;
	WebElement element;

	@BeforeClass
	public void webURLLoader() {
		driver.get(prop.getProperty("heatclinicURL"));
		waitobject.waitForPageLoadTime(driver);
		driver.manage().window().maximize();
	}
	
	@Test
	public void verifyHeatClinic() throws IOException, InterruptedException, SQLException {
		log.logReportMessage("STEP 1: Starting browser :"+chooser[1]);
		log.logReportMessage("STEP 2: URL is loaed"+ driver.getCurrentUrl());
		Properties prop1=propread.keyValueLoader(FileConstants.HEATCLINICLOCATORS_PATH);
		log.logReportMessage("STEP 3: Validate all the Menu");
			heatclinic.verifyHeatclinicMenu(driver,prop1);
			
		log.logReportMessage("STEP 4: Mouse Over Merchandise and click Men");
			seleniumhelp.mouseOver(driver,prop1.getProperty("loc.menu.merchandise"));
			waitobject.waitForElementToBeClickable(driver, prop1.getProperty("loc.submenu.mens"));
			seleniumhelp.clickElement(driver,prop1.getProperty("loc.submenu.mens"));
			
		log.logReportMessage("STEP 5: Verify the Text 'Viewing Mens'");
			waitobject.waitForElementToBeClickable(driver,prop1.getProperty("loc.txt.viewing"));
			mensPageTitle=database.getCellData("heatclinictestdata", "Men_Page_Title", "Ts-01");
			validatehelp.assertValidater(driver.getTitle().toString(),mensPageTitle);
			expectedViewingText=database.getCellData("heatclinictestdata", "ViewingText", "Ts-01");
			viewingMensText=validatehelp.getText(driver, prop1.getProperty("loc.txt.viewing"));
			validatehelp.assertValidater(viewingMensText,expectedViewingText);
			
		log.logReportMessage("STEP 6: Click the Buy now of Hawt Like a Habanero Shirt (Men's)");
			seleniumhelp.clickElement(driver,prop1.getProperty("loc.btn.buynow"));
			inputName=database.getCellData("heatclinictestdata", "Name", "Ts-01");
			heatclinic.handlePopUp(driver, prop1,inputName);
			seleniumhelp.clickElement(driver, prop1.getProperty("loc.btn.popupbuynow"));
			
		log.logReportMessage("STEP 7: click the Cart");
			seleniumhelp.clickElement(driver,prop1.getProperty("loc.link.cart"));
			
		log.logReportMessage("STEP 8: Validation of the product");
			productName=database.getCellData("heatclinictestdata", "ProductName", "Ts-01");
			heatclinic.validateProduct(driver, prop1,productName);
			expectedProductPrice=database.getCellData("heatclinictestdata", "Price", "Ts-01");
			expectedTotalPrice=database.getCellData("heatclinictestdata", "Total", "Ts-01");
			heatclinic.verifyGrandTotal(driver, prop1, expectedProductPrice,expectedTotalPrice);
			
		log.logReportMessage("STEP 9: Increase the Qty count");
			element=waitobject.WaitForFluent(driver, prop1.getProperty("loc.btn.qtyincrease"));
			element.clear();
			productQty=database.getCellData("heatclinictestdata", "Qty", "Ts-01");
			seleniumhelp.sendKeys(prop1.getProperty("loc.btn.qtyincrease"), driver,productQty);
			
		log.logReportMessage("STEP 10: Update the  Cart");
			waitobject.waitForElementToBeClickable(driver, prop1.getProperty("loc.btn.update"));
			seleniumhelp.clickElement(driver, prop1.getProperty("loc.btn.update"));
			expectedProductPriceAfter=database.getCellData("heatclinictestdata", "PriceAfter", "Ts-01");
			expectedTotalPriceAfter=database.getCellData("heatclinictestdata", "TotalAfter", "Ts-01");
			heatclinic.verifyGrandTotal(driver, prop1, expectedProductPriceAfter,expectedTotalPriceAfter);
	}
}
