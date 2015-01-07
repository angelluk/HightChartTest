package com.webaction;


import static org.junit.Assert.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class AppTest {
	
	public static WebDriver driver;
	 
	@BeforeClass
	public static void setUp() throws Exception {
		
		// create anonymous class WebDriverManager and get driver for chooses browser
		driver = new WebDriverManager(driver).getWebDriverFor(WebDriverManager.Browser.CHROME);
	 	// set time for wait driver
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	 /*
	  * main test method
	  */
	 @Test
	 public void testUserProfile() throws InterruptedException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
	     	
	
		 
		 // expected data
		 Long[][] anArrayOfObjects = {
				 {1l, 1257033600000l},
				 {2l, 1290211200000l},
				 {3l, 1301616000000l},
				 {4l, 1312156800000l},
				 {5l, 1313366400000l},
				 {6l, 1338508800000l},
				 {5l, 1346457600000l},
				 {6l, 1347667200000l},
				 {7l, 1356998400000l},
				 {8l, 1375315200000l},
				 {9l, 1376956800000l},
				 {10l, 1380585600000l},
				 {11l, 1412121600000l},
				 {11l, 1417046400000l}
		 };
		 
 		    // load frame source
		 	driver.get("http://www.highcharts.com/samples/view.php?path=highcharts/demo/combo-timeline");
	 	
		 	Thread.sleep(1000);// debug
		 	
		 	// JavaScript fetch data from chart
		 	List<Object> ChartData = (List<Object>) ((JavascriptExecutor)driver).executeScript(
		 		      "var chart = $('#container').highcharts();" 
		 		    + "var data = chart.options.series[2].data;"   
		 		    + "return data;");
		 	
		 	int i = 0;
		 	int k = 0;
		 	
		 	// output
		 	for (Object currObj : ChartData) {
		
		
		 		// get all fields of Object
				for (Field field : currObj.getClass().getDeclaredFields()) {
				    
					field.setAccessible(true); 
				    Object value = field.get(currObj); 
				     // parse only HashMap
				    if ((value != null)&&(value instanceof HashMap)) {
				       
				    	//get HashMap
				        HashMap hm = (HashMap)value;
				     	// Get a set of the entries
				        Set set = hm.entrySet();
				        // Get an iterator
				        Iterator j = set.iterator();
				       
				        // Display elements
				        while(j.hasNext()) {
				           Map.Entry me = (Map.Entry)j.next();
				           if ((me.getKey().toString().equalsIgnoreCase("y"))){
				        	 assertTrue(Long.parseLong(me.getValue().toString()) == anArrayOfObjects[i][0]); // check pairs 				        	   
				        	 System.out.println("Actual y value - " + me.getValue().toString() + " expected value - " + anArrayOfObjects[k][0].toString());
				        	 i++;  
				           }
				           
				           if ((me.getKey().toString().equalsIgnoreCase("x"))){
					          assertTrue(Long.parseLong(me.getValue().toString()) == anArrayOfObjects[k][1]);// check pairs 
				        	  System.out.println("Actual x value - " + me.getValue().toString() + " expected value - " + anArrayOfObjects[k][1].toString());
					          k++;
				           }
				        }
				    }
				}
			}	      				    		        
	    }

		
	@AfterClass
     public static void tearDown()
     { 
          driver.quit(); 
     } 
	
}
