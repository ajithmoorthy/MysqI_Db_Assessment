package com.atmecs.mysqlAssessment.pages;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.atmecs.mysqlAssessment.helper.JavaScriptHelper;
import com.atmecs.mysqlAssessment.helper.SeleniumHelper;
import com.atmecs.mysqlAssessment.helper.ValidaterHelper;
import com.atmecs.mysqlAssessment.helper.WaitForElement;
import com.atmecs.mysqlAssessment.logreports.LogReporter;
import com.atmecs.mysqlAssessment.utils.DataBaseReader;





public class NinjaStroreHomePage {
	LogReporter log=new LogReporter();
	WaitForElement waitobject=new WaitForElement();
	JavaScriptHelper javascript=new JavaScriptHelper();
	ValidaterHelper validatehelp=new ValidaterHelper();
	SeleniumHelper seleniumhelp=new SeleniumHelper();
	DataBaseReader database=new DataBaseReader();
	
	WebElement inputElement,qtyElement;
	String searchInput,availability,expectedAvailability,price,expectedPrice,exTax,expectedExTax,description,expectedDescription,quantity;
	String[] availabiltyArray,exTaxArray;
	
	public void verifyNinjaStoreProduct(WebDriver driver,Properties prop) throws InterruptedException, IOException, SQLException {
		for(int count=1; count<=2; count++) {
			waitobject.waitForImplicit(driver);
		log.logReportMessage("STEP 5: Searching the Product");
			inputElement=waitobject.WaitForFluent(driver,prop.getProperty("loc.txtfield.search"));
			inputElement.clear();
			searchInput=database.getCellData("ninjastoreproductdata", "SearchInput", "Ts-0"+count);
			seleniumhelp.sendKeys(prop.getProperty("loc.txtfield.search"), driver, searchInput);
			seleniumhelp.clickElement(driver,prop.getProperty("loc.btn.search"));
			log.logReportMessage("Product name "+searchInput+" is entered");
			
		log.logReportMessage("STEP 6: Select the Product");
			seleniumhelp.scrollPageMethod(driver,prop.getProperty("loc.imglink.iphone") );
			seleniumhelp.clickElement(driver, prop.getProperty("loc.imglink.iphone"));
			log.logReportMessage("Product is selected");
			
		log.logReportMessage("STEP 7: Validate the the  Product Availability");
			availability=validatehelp.getText(driver, prop.getProperty("loc.txt.prodavailability"));
			availabiltyArray=availability.split(":");
			expectedAvailability=database.getCellData("ninjastoreproductdata", "Availability", "Ts-0"+count);
			validatehelp.assertValidater(availabiltyArray[1], expectedAvailability);
			
		log.logReportMessage("STEP 8: Validate the the  Product Price");
			price=validatehelp.getText(driver, prop.getProperty("loc.txt.prodprice"));
			price=price.substring(1,price.length());
			expectedPrice=database.getCellData("ninjastoreproductdata", "Price", "Ts-0"+count);
			validatehelp.assertValidater(price, expectedPrice);
			
		log.logReportMessage("STEP 9: Validate the the  Product Ex Tax");
			exTax=validatehelp.getText(driver, prop.getProperty("loc.txt.exprice"));
			exTaxArray=exTax.split(":");
			exTaxArray[1]=exTaxArray[1].substring(2,price.length());
			expectedExTax=database.getCellData("ninjastoreproductdata", "ExTax", "Ts-0"+count);
			validatehelp.assertValidater(exTaxArray[1], expectedExTax);
			
		log.logReportMessage("STEP 10: Validate the the  Product Description");
			description=validatehelp.getText(driver, prop.getProperty("loc.txt.proddiscription"));
			expectedDescription=database.getCellData("ninjastoreproductdata", "Description", "Ts-0"+count);
			validatehelp.assertValidater(description, expectedDescription);
			
		log.logReportMessage("STEP 11: Change the Quantity");
			qtyElement=waitobject.WaitForFluent(driver, prop.getProperty("loc.txtfield.qty"));
			qtyElement.clear();
			quantity=database.getCellData("ninjastoreproductdata", "Qty", "Ts-0"+count);
			seleniumhelp.sendKeys(prop.getProperty("loc.txtfield.qty"), driver,quantity );
			
		log.logReportMessage("STEP 12: Click Add to Cart Button");
			javascript.javascriptClickElement(driver,prop.getProperty("loc.btn.addtocart") );
		}
	}
}

