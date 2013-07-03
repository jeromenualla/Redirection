
import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Scanner;
import java.io.FileWriter;

public class EMEDCO_Redirects {
   
    private static Selenium selenium;

    @Before
    public void setUp() throws Exception {
        selenium = new DefaultSelenium("10.192.200.25", 4444, "*firefox", "http://www.google.com");
        selenium.start();
        //selenium.windowMaximize();
    }
   
    @Test
    public void main() throws Exception {
        String fileName = "\\\\10.192.200.25\\csv\\CSV - MWSD-140 - STAGING v2 - Emedco_June_redirects";
        String fileNameCsv = fileName + ".csv";
        File file = new File(fileNameCsv); //File = Java file class
        Date date= new Date(); // java.util.Date will get date today 
        SimpleDateFormat df = new SimpleDateFormat(); //apply format to date
        df.applyPattern("MMddyyyy_hhmmaa"); // this is the format to apply
        String generateCsv = fileName+"_TestResults_"+ df.format(date) +".csv";
        FileWriter fw = new FileWriter(generateCsv);    //This is the FileWriter
        PrintWriter pw = new PrintWriter(fw);    //This Writes the Content of the File
        pw.println("Current URL"+","+"Redirect To URL"+","+"Requestor"+","+"Date"+","+"Actual Redirect URL"+","+"404 on Title"+","+"Test Result");
        pw.flush();
        //SETONCA = "404 Not Found" //SETONUS = "404 Not Found" //EMEDCO = "Page Not Found"
        String TestResult = "";
        String NotFound = "Page Not Found";
        int x = 1;
        
        try {
            Scanner inputStream = new Scanner(file); //this is what reads the file per line
            inputStream.next();inputStream.next();inputStream.next();inputStream.next();
           
            while (inputStream.hasNext()){
                String data = inputStream.next(); //Stores a whole line to variable "data"
                String[] values = data.split(","); //Splits the line to string array per delimeter used
                System.out.println(x);
                
                try {
                    selenium.open(values[0]);
                    selenium.waitForPageToLoad("30000");
                    String title = selenium.getTitle();
                    String URL = selenium.getLocation();
                    String RedirectUrl = values[1];
                    
                    if (URL.equals(RedirectUrl)){
                        TestResult="PASS";
                    }
                    else {
                        TestResult="FAIL";
                    }                   
                    if (title.equals(NotFound)){
                        //System.out.println("Actual: "+ NotFound);
                    	//System.out.println(CompareLive.compareprod(values[0]));
                    	pw.println(values[0]+","+values[1]+","+values[2]+","+values[3]+","+URL+","+NotFound+","+(TestResult));
                        pw.flush();
                    }
                    else {
                        //System.out.println("Actual: "+URL);
                        pw.println(values[0]+","+values[1]+","+values[2]+","+values[3]+","+URL+","+"ok"+","+TestResult);
                        pw.flush();
                    }
                    x++;
                  }
                  catch (SeleniumException seleniumerror) {
                      seleniumerror.printStackTrace();
                	  String URL=selenium.getLocation();
                      TestResult="FAIL";
                      pw.println(values[0]+","+values[1]+","+values[2]+","+values[3]+","+URL+","+seleniumerror+","+(TestResult));
                      pw.flush();
                      x++;
                  }
            }
        inputStream.close();       
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    pw.close();
    fw.close();
    }
       
    @After
    public void tearDown() throws Exception {
        selenium.stop();
    }
}