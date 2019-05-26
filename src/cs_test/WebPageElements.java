package cs_test;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WebPageElements {
    @Test
    public static void main(String[] args) throws IOException, TransformerException, ParserConfigurationException {
        System.setProperty("webdriver.chrome.driver", "C:\\idea\\chromedriver.exe");
        var driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://csltd.com.ua");
        WebDriverWait wait = new WebDriverWait(driver, 10);

        ///////getting to necessary page
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"pure-menu pure-menu-horizontal menu-main\"]/nav/ul/li[8]")));
        button.click();

        WebElement langButton = driver.findElement(By.xpath("//li[@class=\"pure-menu-item pure-menu-has-children pure-menu-allow-hover locale-container\"]/a"));
        langButton.click();

        WebElement lang1 = driver.findElement(By.xpath("//a[@class=\"pure-menu-link squared\"][1]"));
        lang1.click();
        WebElement officeText = driver.findElement(By.xpath("(//div[@class=\"container\"]/h1[1])[1]"));
        String dataoutput1 = officeText.getText();

        /////////////////////specifying elements for extract the text
        WebElement office = driver.findElement(By.xpath("//section[@id=\"section-contacts\"]/div[3]/preceding-sibling::* "));
        WebElement address = driver.findElement(By.xpath("(//div[@class=\"contacts-info\"][1]/div[2])[1]"));
        WebElement phone = driver.findElement(By.xpath("(//div[@class=\"contacts-info\"][1]/div[3])[1]"));
        WebElement email = driver.findElement(By.xpath("(//div[@class=\"contacts-info\"][1]/div[4])[1]"));

        String dataoutput_office = office.getText();
        String dataoutput_address = address.getText();
        String dataoutput_phone = phone.getText();
        String dataoutput_email = email.getText();

        ////////////creating file to work with
        new File("C:/testFolder").mkdir();

        try (FileWriter gettext = new FileWriter(System.getProperty("user.dir") + "_ru" + ".txt"))
        {
            String lineSeparator = System.getProperty("line.separator");
            String[] output = dataoutput_office.split("\n");
            String[] output2 = dataoutput_address.split("\n");
            String[] output3 = dataoutput_phone.split("\n");
            String[] output4 = dataoutput_email.split("\n");

            for (int i = 0; i <= output.length-1; i++) {
                gettext.write(output[i]);
                gettext.write(lineSeparator);
            }
            for (int i = 0; i <= output2.length-1; i++) {
                gettext.write(output2[i]);
                gettext.write(lineSeparator);
            }
            for (int i = 0; i <= output3.length-1; i++) {
                gettext.write(output3[i]);
                gettext.write(lineSeparator);
            }
            for (int i = 0; i <= output4.length-1; i++) {
                gettext.write(output4[i]);
                gettext.write(lineSeparator);
            }
        }
        //System.out.println("Present Project Directory : "+ System.getProperty("user.dir"));
        System.out.println(dataoutput_office);
        System.out.println(dataoutput_address);
        System.out.println(dataoutput_phone);
        System.out.println(dataoutput_email);

        ////////////////////////////////////////////////////////////////////////
        ////////////////IN XML

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbuilder = dbFactory.newDocumentBuilder();

        Document document = dbuilder.newDocument();

        Element parent = document.createElement("ROW");
        document.appendChild(parent);

        Element addr = document.createElement("address");
        parent.appendChild(addr);
        addr.setTextContent(dataoutput_address);

        Element ph = document.createElement("phone");
        parent.appendChild(ph);
        ph.setTextContent(dataoutput_phone);

        Element em = document.createElement("email");
        parent.appendChild(em);
        em.setTextContent(dataoutput_email);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);

        StreamResult result = new StreamResult(new File("C:/testFolder/text_ru_xml.xml"));
        transformer.transform(source, result);
    }
}