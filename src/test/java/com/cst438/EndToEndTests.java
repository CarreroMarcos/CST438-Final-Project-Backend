package com.cst438;

import static org.assertj.core.api.Assertions.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

/*
 * This example shows how to use selenium testing using the web driver 
 * with Chrome browser.
 * 
 *  - Buttons, input, and anchor elements are located using XPATH expression.
 *  - onClick( ) method is used with buttons and anchor tags.
 *  - Input fields are located and sendKeys( ) method is used to enter test data.
 *  - Spring Boot JPA is used to initialize, verify and reset the database before
 *      and after testing.
 *      
 *  In SpringBootTest environment, the test program may use Spring repositories to 
 *  setup the database for the test and to verify the result.
 */

@SpringBootTest
public class EndToEndTests {

	public static final String CHROME_DRIVER_FILE_LOCATION = "/Users/benjaminmeier/Downloads/chromedriver-mac-arm64/chromedriver";

	public static final String URL = "http://localhost:3000";
	public static final int SLEEP_DURATION = 1000; 
	
	
	@Test
	public void loadHomepageSongs() throws Exception {
		System.setProperty("webdriver.http.factory", "jdk-http-client");
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
		WebDriver driver = new ChromeDriver();
		// Puts an Implicit wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(URL);
		Thread.sleep(SLEEP_DURATION);
		
		
		try {
			WebElement top_song_0 = driver.findElement(By.id("songs_0"));
			WebElement title = top_song_0.findElements(By.xpath("//h4")).get(0);
			assertThat(title.getText());
			
			WebElement featuredArtistElement = driver.findElement(By.id("featured_artist"));
			String featuredArtistName = featuredArtistElement.getText().substring(16);
			
			int same = 0, different = 0;
			
			List<WebElement> featuredArtistSongs = driver.findElement(By.id("albums")).findElements(By.className("card"));
			for(int i = 0; i < featuredArtistSongs.size(); i++) {
				if(featuredArtistSongs.get(i).findElements(By.xpath("//a")).get(0).getText().equals(featuredArtistName)) {
					same ++;
				} else {
					different ++;
				}
			}
			assertThat((same / (different * 1.0)) > 0.50);
		} catch(Exception e) {
			throw e;
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void testUserLibrary() throws Exception {
		System.setProperty("webdriver.http.factory", "jdk-http-client");
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
		WebDriver driver = new ChromeDriver();
		// Puts an Implicit wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(URL);
		Thread.sleep(SLEEP_DURATION);
		
		
		try {
			driver.findElement(By.className("nav_actions")).findElement(By.className("link")).click();
			Thread.sleep(SLEEP_DURATION);
			
			driver.findElement(By.name("username")).sendKeys("user");
			driver.findElement(By.name("password")).sendKeys("user");
			
			Thread.sleep(SLEEP_DURATION);
			
			driver.findElement(By.className("main_button")).click();
			
			Thread.sleep(SLEEP_DURATION);
			
			WebElement library = driver.findElement(By.className("nav_actions")).findElement(By.className("link"));
			assertThat(library.getText().equals("Your Library")).isTrue();
			
			driver.findElement(By.id("songs_0")).findElement(By.className("heart_button")).click();
			Thread.sleep(SLEEP_DURATION);
			
			library.click();
			Thread.sleep(SLEEP_DURATION);
			
			assertThat(driver.findElement(By.id("0"))).isNotNull();
		} catch(Exception e) {
			
		} finally {
			driver.quit();
		}
	}


//	@Test
//	public void addCourseTest() throws Exception {
//
//
//
//		// set the driver location and start driver
//		//@formatter:off
//		// browser	property name 				Java Driver Class
//		// edge 	webdriver.edge.driver 		EdgeDriver
//		// FireFox 	webdriver.firefox.driver 	FirefoxDriver
//		// IE 		webdriver.ie.driver 		InternetExplorerDriver
//		//@formatter:on
//		
//		/*
//		 * initialize the WebDriver and get the home page. 
//		 */
//
//		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
//		WebDriver driver = new ChromeDriver();
//		// Puts an Implicit wait for 10 seconds before throwing exception
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//
//		driver.get(URL);
//		Thread.sleep(SLEEP_DURATION);
//		
//		WebElement w;
//		
//
//		try {
//			/*
//			* locate the <td> element for assignment title 'db design'
//			* 
//			*/
//			
//			List<WebElement> elements  = driver.findElements(By.xpath("//td"));
//			boolean found = false;
//			for (WebElement we : elements) {
//				if (we.getText().equals(TEST_ASSIGNMENT_NAME)) {
//					found=true;
//					we.findElement(By.xpath("..//a")).click();
//					break;
//				}
//			}
//			assertThat(found).withFailMessage("The test assignment was not found.").isTrue();
//
//			/*
//			 *  Locate and click Grade button to indicate to grade this assignment.
//			 */
//			
//			Thread.sleep(SLEEP_DURATION);
//
//			/*
//			 *  enter grades for all students, then click save.
//			 */
//			ArrayList<String> originalGrades = new ArrayList<>();
//			elements  = driver.findElements(By.xpath("//input"));
//			for (WebElement element : elements) {
//				originalGrades.add(element.getAttribute("value"));
//				element.clear();
//				element.sendKeys(NEW_GRADE);
//				Thread.sleep(SLEEP_DURATION);
//			}
//			
//			for (String s : originalGrades) {
//				System.out.println("'"+s+"'");
//			}
//
//			/*
//			 *  Locate submit button and click
//			 */
//			driver.findElement(By.id("sgrade")).click();
//			Thread.sleep(SLEEP_DURATION);
//			
//			w = driver.findElement(By.id("gmessage"));
//			assertThat(w.getText()).withFailMessage("After saving grades, message should be \"Grades saved.\"").startsWith("Grades saved");
//			
//			driver.navigate().back();  // back button to last page
//			Thread.sleep(SLEEP_DURATION);
//			
//			// find the assignment 'db design' again.
//			elements  = driver.findElements(By.xpath("//td"));
//			found = false;
//			for (WebElement we : elements) {
//				if (we.getText().equals(TEST_ASSIGNMENT_NAME)) {
//					found=true;
//					we.findElement(By.xpath("..//a")).click();
//					break;
//				}
//			}
//			Thread.sleep(SLEEP_DURATION);
//			assertThat(found).withFailMessage("The test assignment was not found.").isTrue();
//			
//			// verify the grades. Change grades back to original values
//
//			elements  = driver.findElements(By.xpath("//input"));
//			for (int idx=0; idx < elements.size(); idx++) {
//				WebElement element = elements.get(idx);
//				assertThat(element.getAttribute("value")).withFailMessage("Incorrect grade value.").isEqualTo(NEW_GRADE);
//				
//				// clear the input value by backspacing over the value
//				while(!element.getAttribute("value").equals("")){
//			        element.sendKeys(Keys.BACK_SPACE);
//			    }
//				if (!originalGrades.get(idx).equals("")) element.sendKeys(originalGrades.get(idx));
//				Thread.sleep(SLEEP_DURATION);
//			}
//			driver.findElement(By.id("sgrade")).click();
//			Thread.sleep(SLEEP_DURATION);
//			
//			w = driver.findElement(By.id("gmessage"));
//			assertThat(w.getText()).withFailMessage("After saving grades, message should be \"Grades saved.\"").startsWith("Grades saved");
//
//
//		} catch (Exception ex) {
//			throw ex;
//		} finally {
//
//			driver.quit();
//		}
//
//	}
}
