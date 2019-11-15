package com.atmecs.mysqlAssessment.pages;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.atmecs.mysqlAssessment.helper.JavaScriptHelper;
import com.atmecs.mysqlAssessment.helper.SeleniumHelper;
import com.atmecs.mysqlAssessment.helper.ValidaterHelper;
import com.atmecs.mysqlAssessment.helper.WaitForElement;
import com.atmecs.mysqlAssessment.helper.WebTableHelper;
import com.atmecs.mysqlAssessment.logreports.LogReporter;
import com.atmecs.mysqlAssessment.utils.DataBaseReader;






public class HeatClinicHomePage {
	LogReporter log = new LogReporter();
	WaitForElement waitobject = new WaitForElement();
	JavaScriptHelper javascript = new JavaScriptHelper();
	ValidaterHelper validatehelp = new ValidaterHelper();
	SeleniumHelper seleniumhelp = new SeleniumHelper();
	WebTableHelper webtable=new WebTableHelper();
	DataBaseReader database=new DataBaseReader();
	
	
	String[] productDetailsArray,productNameArray;
	String titleData,productPrice,productTotalPrice,productName,productDetails,expectedProductDetails,locatorData;
	String[] columnName= {"Size","ExpectedName","Color"};
	
	public void verifyHeatclinicMenu(WebDriver driver, Properties prop) throws IOException, SQLException {
		for (int count = 1; count <=5; count++) {
			locatorData = database.getCellData("heatclinictitletestdata","Locators", "Ts-0"+count);
			titleData= database.getCellData("heatclinictitletestdata","Titles", "Ts-0"+count);
			seleniumhelp.clickElement(driver, prop.getProperty(locatorData));
			validatehelp.assertValidater(driver.getTitle(),titleData);
		}
	}
	public void handlePopUp(WebDriver driver, Properties prop, String inputname) {
		seleniumhelp.sendKeys(prop.getProperty("loc.txtfield.name"), driver, inputname);
		seleniumhelp.clickElement(driver, prop.getProperty("loc.btn.color"));
		seleniumhelp.clickElement(driver, prop.getProperty("loc.btn.size"));
	}

	public void validateProduct(WebDriver driver,Properties prop,String expectedProductName) throws IOException, SQLException { 
		productName=validatehelp.getText(driver, prop.getProperty("loc.txt.name"));
		productNameArray=productName.split("\n");
		validatehelp.assertValidater(productNameArray[0].substring(0,productNameArray[0].length()-8), expectedProductName);
		int initial=0;
		for(int count=1; count<=3; count++) {
			String productDetails=validatehelp.getText(driver, prop.getProperty("loc.txt.proddetails").replace("xxx", ""+count));
			productDetailsArray=productDetails.split(":");
			expectedProductDetails=database.getCellData("heatclinictestdata",columnName[initial] , "Ts-01");
			validatehelp.assertValidater(productDetailsArray[1],expectedProductDetails);
			initial++;
		}
	}
	public void verifyGrandTotal(WebDriver driver,Properties prop,String expectedProductPrice,String expectedTotalPrice) {
		productPrice=validatehelp.getText(driver, prop.getProperty("loc.txt.price"));
		validatehelp.assertValidater(productPrice.substring(1, productPrice.length()),expectedProductPrice);
		productTotalPrice=validatehelp.getText(driver, prop.getProperty("loc.txt.totalprice"));
		validatehelp.assertValidater(productTotalPrice.substring(1,productTotalPrice.length()),expectedTotalPrice);
		
	}
}
