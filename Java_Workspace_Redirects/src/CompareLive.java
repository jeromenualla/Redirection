import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;


public class CompareLive {
	
	private static Selenium selenium;
	
	 @Before
	    public void setUp() throws Exception {
	        selenium = new DefaultSelenium("10.192.200.25", 4444, "*firefox", "http://www.google.com");
	        selenium.start();
	        //selenium.windowMaximize();
	    }
	 
	
	 @Test
	 
	 public static String compareprod(String CurrentURL) throws Exception {
		 String convertCurrentURL ="";
		 if (CurrentURL.startsWith("http://setonstage.zeondemo.com")) {
			 convertCurrentURL= CurrentURL.replaceFirst("http://setonstage.zeondemo.com", "http://www.seton.com");
		 } else if (CurrentURL.startsWith("setonstage.zeondemo.com")) {
			 convertCurrentURL= CurrentURL.replaceFirst("setonstage.zeondemo.com", "http://www.seton.com");
		 } 
		 else if (CurrentURL.startsWith("http://setoncastg.zeondemo.com")) {
			 convertCurrentURL= CurrentURL.replaceFirst("http://setoncastg.zeondemo.com", "http://www.seton.ca");
		 } else if (CurrentURL.startsWith("setoncastg.zeondemo.com")) {
			 convertCurrentURL= CurrentURL.replaceFirst("setoncastg.zeondemo.com", "http://www.seton.ca");
		 } 	
		 else if (CurrentURL.startsWith("http://emedco.zeondemo.com")) {
			 convertCurrentURL= CurrentURL.replaceFirst("http://emedco.zeondemo.com", "http://www.emedco.com");
		 } else if (CurrentURL.startsWith("setoncastg.zeondemo.com")) {
			 convertCurrentURL= CurrentURL.replaceFirst("emedco.zeondemo.com", "http://www.emedco.com");
		 }
		 else{
			 convertCurrentURL = CurrentURL;
		 }
		 
		 return convertCurrentURL;
    }

    
    @After
    public void tearDown() throws Exception {
        selenium.stop();
    }
}
