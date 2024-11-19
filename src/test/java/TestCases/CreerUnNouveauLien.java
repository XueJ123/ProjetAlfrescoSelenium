package TestCases;


import static org.testng.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreerUnNouveauLien {
	public static final int TIMEOUT_IN_SECONDS = 5;
	private WebDriver driver;
	private String url;
	private String login;
	private String pw;
	Date dateCourante;
	WebDriverWait wait;
  	Properties locators = new Properties();
  	
 @BeforeClass
 public void setup() throws IOException {
	WebDriverManager.chromedriver().setup();
	driver = new ChromeDriver();
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);
	url = "http://localhost:8088/share/page/";
  	login = "1689374";
  	pw = "12345678";
  	FileInputStream propfile = new FileInputStream("src/test/java/Ressources/Locators/Alfresco.properties");
  	locators.load(propfile);
 }
  	  
 @AfterClass
 public void teardown() {
	 driver.quit();
 }	
 
 @BeforeTest
 public void beforeTest() {
 }
 
 @AfterTest
 public void afterTest() {
	 //driver.quit();
 }
	
//********************************************************************************************
//*****************************************SCRIPTS********************************************
//********************************************************************************************	
	@Test (priority = 1)
	public void TestCreerUnNouveauLien() throws IOException, InterruptedException{
		String nomSite = "SiteXue";
		String urlSite = "Url";
		String descriptionSite = "Description";
		String visibiliteSite = "PUBLIC";
		String titreLien = "TitreLien"; 
		String urlLien = "UrlLien";
		String descriptionLien = "Description"; 
		String Interne = "OUI";
		String Tags = "TagLien";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String date_current = dateFormat.format(date);
		
		//********pre-condition********
		login(url,login,pw);
		//Creer un site
		creerUnSiteComplet(nomSite+date_current, urlSite+date_current, descriptionSite, visibiliteSite);
		Thread.sleep(3000);
		logOut();
		//********test case********
		login(url,login,pw);
		creerUnNouveauLien(nomSite+date_current, titreLien+date_current, urlLien, descriptionLien, Interne, Tags);
		Thread.sleep(3000);
		logOut();
		//********criteres de succes********
		login(url,login,pw);
		verifierCreerNouveauLien(nomSite+date_current, titreLien+date_current, urlLien);
		Thread.sleep(3000);
		logOut();
		//********post-condition********
		login(url,login,pw);
		supprimerUnLien(nomSite+date_current, titreLien+date_current);
		Thread.sleep(3000);
		//supprimer un site
		supprimerUnSite(nomSite+date_current);
		Thread.sleep(5000);
		viderCorbeille();
		Thread.sleep(3000);
		logOut();
	}
	
//********************************************************************************************
//****************************************KEYWORDS********************************************
//********************************************************************************************
	public void supprimerUnLien(String nomSite, String titreLien) throws InterruptedException {
		String link_HeaderSites = locators.getProperty("link_HeaderSites");
		String link_MesSites = locators.getProperty("link_MesSites");
		String link_SiteCiblePart1 = locators.getProperty("link_SiteCiblePart1");
		String link_SiteCiblePart2 = locators.getProperty("link_SiteCiblePart2");
		String link_LiensSites = locators.getProperty("link_LiensSites");
		String link_PlusPageSites = locators.getProperty("link_PlusPageSites");
		String link_LienCiblePart1 = locators.getProperty("link_LienCiblePart1");
		String link_LienCiblePart2 = locators.getProperty("link_LienCiblePart2");
		String link_SupprimerLienCiblePart1 = locators.getProperty("link_SupprimerLienCiblePart1");
		String link_SupprimerLienCiblePart2 = locators.getProperty("link_SupprimerLienCiblePart2");
		String btn_SupprimerLien = locators.getProperty("btn_SupprimerLien");

		Actions action = new Actions(driver);
		//cliquer lien Sites
		driver.findElement(By.id(link_HeaderSites)).click();
		//cliquer lien Mes Sites
		driver.findElement(By.id(link_MesSites)).click();
		Thread.sleep(2000);
		//cliquer liens de site cible
		driver.findElement(By.xpath(link_SiteCiblePart1+nomSite+link_SiteCiblePart2)).click();
		Thread.sleep(5000);
		//Si le lien de Liens sur la page cliquer sur Lien, sinon cliquer sur Plus puis sur Liens
		if(driver.findElement(By.id(link_LiensSites)).isDisplayed()) {
			driver.findElement(By.id(link_LiensSites)).click();
		}
		else {
			driver.findElement(By.id(link_PlusPageSites)).click();
			driver.findElement(By.id(link_LiensSites)).click();
		}
		Thread.sleep(5000);
		//survoler le souris sur le lien cible
		action.moveToElement(driver.findElement(By.xpath(link_LienCiblePart1+titreLien+link_LienCiblePart2))).perform();
		Thread.sleep(3000);
		//cliquer lien Supprimer 
		driver.findElement(By.xpath(link_SupprimerLienCiblePart1+titreLien+link_SupprimerLienCiblePart2)).click();
		Thread.sleep(2000);
		//cliquer bouton Supprimer
		driver.findElement(By.xpath(btn_SupprimerLien)).click();
	}
	
	public void verifierCreerNouveauLien(String nomSite, String titreLien, String urlLien) throws InterruptedException {
		String link_HeaderSites = locators.getProperty("link_HeaderSites");
		String link_MesSites = locators.getProperty("link_MesSites");
		String link_SiteCiblePart1 = locators.getProperty("link_SiteCiblePart1");
		String link_SiteCiblePart2 = locators.getProperty("link_SiteCiblePart2");
		String link_LiensSites = locators.getProperty("link_LiensSites");
		String link_PlusPageSites = locators.getProperty("link_PlusPageSites");
		String link_LienCiblePart1 = locators.getProperty("link_LienCiblePart1");
		String link_LienCiblePart2 = locators.getProperty("link_LienCiblePart2");
		String link_UrlLienCiblePart1 = locators.getProperty("link_UrlLienCiblePart1");
		String link_UrlLienCiblePart2 = locators.getProperty("link_UrlLienCiblePart2");
		
		//cliquer lien Sites
		driver.findElement(By.id(link_HeaderSites)).click();
		//cliquer lien Mes Sites
		driver.findElement(By.id(link_MesSites)).click();
		Thread.sleep(2000);
		//cliquer liens de site cible
		driver.findElement(By.xpath(link_SiteCiblePart1+nomSite+link_SiteCiblePart2)).click();
		Thread.sleep(5000);
		//Si le lien de Liens sur la page cliquer sur Lien, sinon cliquer sur Plus puis sur Liens
		if(driver.findElement(By.id(link_LiensSites)).isDisplayed()) {
			driver.findElement(By.id(link_LiensSites)).click();
		}
		else {
			driver.findElement(By.id(link_PlusPageSites)).click();
			driver.findElement(By.id(link_LiensSites)).click();
		}
		Thread.sleep(5000);
		//verifier les donnees de lien cree
		Assert.assertEquals(driver.findElement(By.xpath(link_LienCiblePart1+titreLien+link_LienCiblePart2)).getText(), titreLien);
		Assert.assertEquals(driver.findElement(By.xpath(link_UrlLienCiblePart1+titreLien+link_UrlLienCiblePart2)).getText(), urlLien);
		
		System.out.println("End of verification");
	}
	
	public void creerUnNouveauLien(String nomSite, String titreLien, String urlLien, String descriptionLien, String Interne, String Tags) throws InterruptedException {
		//Interne = OUI, NON
		String link_HeaderSites = locators.getProperty("link_HeaderSites");
		String link_MesSites = locators.getProperty("link_MesSites");
		String link_SiteCiblePart1 = locators.getProperty("link_SiteCiblePart1");
		String link_SiteCiblePart2 = locators.getProperty("link_SiteCiblePart2");
		String link_LiensSites = locators.getProperty("link_LiensSites");
		String link_PlusPageSites = locators.getProperty("link_PlusPageSites");
		String btn_NouveauLien = locators.getProperty("btn_NouveauLien");
		String txt_TitreLien = locators.getProperty("txt_TitreLien");
		String txt_UrlLien = locators.getProperty("txt_UrlLien");
		String txt_DescriptionLien = locators.getProperty("txt_DescriptionLien");
		String checkbox_InterneLien = locators.getProperty("checkbox_InterneLien");
		String txt_TagsLien = locators.getProperty("txt_TagsLien");
		String btn_AjouterTagsLien = locators.getProperty("btn_AjouterTagsLien");
		String btn_EnregistrerLien = locators.getProperty("btn_EnregistrerLien");
		
		//cliquer lien Sites
		driver.findElement(By.id(link_HeaderSites)).click();
		//cliquer lien Mes Sites
		driver.findElement(By.id(link_MesSites)).click();
		Thread.sleep(2000);
		//cliquer liens de site cible
		driver.findElement(By.xpath(link_SiteCiblePart1+nomSite+link_SiteCiblePart2)).click();
		Thread.sleep(5000);
		//Si le lien de Liens sur la page cliquer sur Lien, sinon cliquer sur Plus puis sur Liens
		if(driver.findElement(By.id(link_LiensSites)).isDisplayed()) {
			driver.findElement(By.id(link_LiensSites)).click();
		}
		else {
			driver.findElement(By.id(link_PlusPageSites)).click();
			driver.findElement(By.id(link_LiensSites)).click();
		}
			
		Thread.sleep(3000);
		//cliquer bouton Nouveau lien
		driver.findElement(By.id(btn_NouveauLien)).click();
		Thread.sleep(3000);
		//saisir les champs
		driver.findElement(By.id(txt_TitreLien)).sendKeys(titreLien);
		driver.findElement(By.id(txt_UrlLien)).sendKeys(urlLien);
		driver.findElement(By.id(txt_DescriptionLien)).sendKeys(descriptionLien);
		//si Interne == OUI, cocher checkbox Interne
		if (Interne.equals("OUI")) {
			driver.findElement(By.id(checkbox_InterneLien)).click();
		}
		//saisir tags
		driver.findElement(By.id(txt_TagsLien)).sendKeys(Tags);
		//cliquer bouton Ajouter
		driver.findElement(By.id(btn_AjouterTagsLien)).click();
		Thread.sleep(2000);
		//cliquer bouton Enregistrer
		driver.findElement(By.id(btn_EnregistrerLien)).click();	
	}
	
	public void supprimerUnSite(String nomSite) throws InterruptedException {
		String link_HeaderSites = locators.getProperty("link_HeaderSites");
		String link_MesSites = locators.getProperty("link_MesSites");
		String link_SiteCiblePart1 = locators.getProperty("link_SiteCiblePart1");
		String link_SiteCiblePart2 = locators.getProperty("link_SiteCiblePart2");
		String btn_ConfigurationSite = locators.getProperty("btn_ConfigurationSite");
		String link_SupprimerSite = locators.getProperty("link_SupprimerSite");
		String btn_OkSupprimerSite = locators.getProperty("btn_OkSupprimerSite");
		
		//cliquer lien Sites
		driver.findElement(By.id(link_HeaderSites)).click();
		//cliquer lien Mes Sites
		driver.findElement(By.id(link_MesSites)).click();
		//cliquer lien de site cible
		driver.findElement(By.xpath(link_SiteCiblePart1+nomSite+link_SiteCiblePart2)).click();
		//cliquer icone de configuration
		driver.findElement(By.id(btn_ConfigurationSite)).click();
		//cliquer lien Supprimer site
		driver.findElement(By.id(link_SupprimerSite)).click();
		Thread.sleep(2000);
		//cliquer bouton ok
		driver.findElement(By.id(btn_OkSupprimerSite)).click();
	}
	
	public void creerUnSite(String nomSite, String urlSite, String visibiliteSite) throws InterruptedException {
		//visibiliteSite = PUBLIC, MODERATED, PRIVATE
		String link_HeaderSites = locators.getProperty("link_HeaderSites");
		String link_CreerUnSite = locators.getProperty("link_CreerUnSite");
		String txt_NomSite = locators.getProperty("txt_NomSite");
		String txt_UrlSite = locators.getProperty("txt_UrlSite");
		String radio_VisbiliteSitePart1 = locators.getProperty("radio_VisbiliteSitePart1");
		String radio_VisbiliteSitePart2 = locators.getProperty("radio_VisbiliteSitePart2");
		String btn_CreerSite = locators.getProperty("btn_CreerSite");
		
		//cliquer lien Sites
		driver.findElement(By.id(link_HeaderSites)).click();
		//cliquer lien Creer un site
		driver.findElement(By.id(link_CreerUnSite)).click();
		Thread.sleep(2000);
		//saisir le champs
		driver.findElement(By.xpath(txt_NomSite)).sendKeys(nomSite);
		driver.findElement(By.xpath(txt_UrlSite)).sendKeys(urlSite);
		Thread.sleep(2000);
		//cliquer sur le bouton radio choisi de visibilite de site
		driver.findElement(By.xpath(radio_VisbiliteSitePart1+visibiliteSite+radio_VisbiliteSitePart2)).click();
		
		Thread.sleep(2000);
		//cliquer bouton Creer site
		driver.findElement(By.id(btn_CreerSite)).click();	
	}
	
	public void login(String url,String login,String pw) throws IOException {
	  	String txt_UserName = locators.getProperty("txt_UserName");
	  	String txt_Password = locators.getProperty("txt_Password");
	  	String btn_Login = locators.getProperty("btn_Login");
		
		//driver.manage().window().maximize();
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(url);
		driver.findElement(By.name(txt_UserName)).sendKeys(login);
		driver.findElement(By.id(txt_Password)).sendKeys(pw);
		driver.findElement(By.id(btn_Login)).click();
	}
	
	public void logOut() throws InterruptedException {
		String link_HeaderUserMenu = locators.getProperty("link_HeaderUserMenu");
	    String link_HeaderDeconnexion = locators.getProperty("link_HeaderDeconnexion");
	    driver.findElement(By.id(link_HeaderUserMenu)).click();
	    driver.findElement(By.id(link_HeaderDeconnexion)).click();
	}	
	
	public void viderCorbeille() throws InterruptedException {
		String link_HeaderSites = locators.getProperty("link_HeaderSites");
		String link_MesSites = locators.getProperty("link_MesSites");
		String link_Corbeille = locators.getProperty("link_Corbeille");
		String btn_Vider = locators.getProperty("btn_Vider");
		String btn_OkVider = locators.getProperty("btn_OkVider");
		
		//cliquer lien Sites
		driver.findElement(By.id(link_HeaderSites)).click();
		//cliquer lien Mes Sites
		driver.findElement(By.id(link_MesSites)).click();
		Thread.sleep(2000);
		//cliquer lien corbeille
		driver.findElement(By.id(link_Corbeille)).click();
		Thread.sleep(3000);
		//cliquer bouton Vider
		driver.findElement(By.id(btn_Vider)).click();
		Thread.sleep(2000);
		//cliquer bouton ok
		driver.findElement(By.xpath(btn_OkVider)).click();
	}
	
	public void creerUnSiteComplet(String nomSite, String nomUrl, String description, String visibiliteSite) throws InterruptedException {
		//visibiliteSite = PUBLIC, MODERATED, PRIVATE
		String link_HeaderSites = locators.getProperty("link_HeaderSites");
		String link_CreerUnSite = locators.getProperty("link_CreerUnSite");
		String txt_NomSite = locators.getProperty("txt_NomSite");
		String txt_UrlSite = locators.getProperty("txt_UrlSite");
		String txt_DescriptionSite = locators.getProperty("txt_DescriptionSite");
		String radio_VisbiliteSitePart1 = locators.getProperty("radio_VisbiliteSitePart1");
		String radio_VisbiliteSitePart2 = locators.getProperty("radio_VisbiliteSitePart2");
		String btn_CreerSite = locators.getProperty("btn_CreerSite");
		String img_SiteConfiguration = locators.getProperty("img_SiteConfiguration");
		String link_PersonaliserSites = locators.getProperty("link_PersonaliserSites");
		
		String img_LienPageSite = locators.getProperty("img_LienPageSite");
		String img_ListeDonneesPage = locators.getProperty("img_ListeDonneesPage");
		String img_BlogPage = locators.getProperty("img_BlogPage");
		String img_WikiPage = locators.getProperty("img_WikiPage");
		String img_DiscussionPage = locators.getProperty("img_DiscussionPage");
		String img_CalendrierPage = locators.getProperty("img_CalendrierPage");
		String target_dropplace = locators.getProperty("target_dropplace");
		String btn_OkPersonaliserSites = locators.getProperty("btn_OkPersonaliserSites");

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//cliquer sur le lien site
		driver.findElement(By.id(link_HeaderSites)).click();
	    //cliquer sur le lien creer site
	    driver.findElement(By.id(link_CreerUnSite)).click();  
	    //Remplir le formulaire
	    //saisir le champ nom
	    driver.findElement(By.xpath(txt_NomSite)).sendKeys(nomSite);  
	    //Remplir le champ url
	    driver.findElement(By.xpath(txt_UrlSite)).sendKeys(nomUrl);      
	    //Remplir le description
	    driver.findElement(By.xpath(txt_DescriptionSite)).sendKeys(description);      
		//cliquer sur le bouton radio choisi de visibilite de site
		driver.findElement(By.xpath(radio_VisbiliteSitePart1+visibiliteSite+radio_VisbiliteSitePart2)).click();
	    //cliquer le bouton creer 
	    driver.findElement(By.id(btn_CreerSite)).click();	
	    Thread.sleep(6000);
	    //cliquer sur le bouton congiguration
	    driver.findElement(By.id(img_SiteConfiguration)).click();
	    Thread.sleep(3000);
	    //cliquer sur personaliser
	    driver.findElement(By.id(link_PersonaliserSites)).click();
	    Thread.sleep(3000);
	    //ajouter les composants 
	    
	    WebElement fromElement = driver.findElement(By.id(img_LienPageSite));
	    WebElement toElement = driver.findElement(By.id(target_dropplace));
	    (new Actions(driver)).dragAndDrop(fromElement, toElement).perform();
	    Thread.sleep(3000);
	    
	    WebElement fromElement1 = driver.findElement(By.id(img_ListeDonneesPage));
	    WebElement toElement1 = driver.findElement(By.id(target_dropplace));
	    (new Actions(driver)).dragAndDrop(fromElement1, toElement1).perform();
	    Thread.sleep(3000);
	    
	    WebElement fromElement2 = driver.findElement(By.id(img_BlogPage));
	    WebElement toElement2 = driver.findElement(By.id(target_dropplace));
	    (new Actions(driver)).dragAndDrop(fromElement2, toElement2).perform();
	    Thread.sleep(4000);
	    
	    WebElement fromElement3 = driver.findElement(By.id(img_WikiPage));
	    WebElement toElement3 = driver.findElement(By.id(target_dropplace));
	    (new Actions(driver)).dragAndDropBy(fromElement3, 700, 200).perform();
	    Thread.sleep(5000);
	    
	    WebElement fromElement4 = driver.findElement(By.id(img_DiscussionPage));
	    WebElement toElement4 = driver.findElement(By.id(target_dropplace));
	    (new Actions(driver)).dragAndDropBy(fromElement4, 800, 200).perform();
	    Thread.sleep(5000);
	    
	    WebElement fromElement5 = driver.findElement(By.id(img_CalendrierPage));
	    WebElement toElement5 = driver.findElement(By.id(target_dropplace));
	    (new Actions(driver)).dragAndDropBy(fromElement5, 1000, 200).perform();
	    Thread.sleep(5000);
	 
	    //cliquer sur le bouton OK
	    driver.findElement(By.id(btn_OkPersonaliserSites)).click();
	   
	}	


}























