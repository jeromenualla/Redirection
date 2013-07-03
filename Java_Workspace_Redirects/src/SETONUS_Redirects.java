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

public class SETONUS_Redirects {
   
    private Selenium selenium;

    @Before
    public void setUp() throws Exception {
        //selenium = new DefaultSelenium("10.192.200.25", 4444, "*firefox", "http://emedco.zeondemo.com");
        selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://emedco.zeondemo.com");
        selenium.start();
       //selenium.windowMaximize();
    }
   
    @Test
    public void main() throws Exception {
        String fileName = "\\\\10.192.200.25\\csv\\CSV - Staging - SUS June redirects v2";
        String fileNameCsv = fileName + ".csv";
        File file = new File(fileNameCsv); //File = Java file class
        Date date= new Date();
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("MMddyyyy_hhmmaa");
        String generateCsv = fileName+"_TestResults_"+ df.format(date) +".csv";
        FileWriter fw = new FileWriter(generateCsv);    //This is the FileWriter
        PrintWriter pw = new PrintWriter(fw);    //This Writes the Content of the File
        pw.println("Current URL"+","+"Redirect To URL"+","+"Requestor"+","+"Date"+","+"Actual Redirect URL"+","+"404 on Title"+","+"Test Result");
        pw.flush();
        int x = 1;
        String TestResult = ("");
        //SETONCA = "404 Not Found" //SETONUS = "404 Not Found" //EMEDCO = "Page Not Found"
       
        String NotFound = ("404 Not Found");
   
        try {
            Scanner inputStream = new Scanner(file);
            inputStream.next();inputStream.next();inputStream.next();inputStream.next();
           
            while (inputStream.hasNext()){
                String data = inputStream.next(); //Gets a whole line
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