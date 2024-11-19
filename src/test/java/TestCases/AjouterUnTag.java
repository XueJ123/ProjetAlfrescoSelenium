package TestCases;


import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
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

public class AjouterUnTag {
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
	public void TestAjouterUnTag() throws IOException, InterruptedException{
		String nomDossier = "DossierXue";
		String nomTag = "TagXue";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String date_current = dateFormat.format(date);
		
		//********pre-condition********
		login(url,login,pw);
		creerUnDossier(nomDossier+date_current);
		Thread.sleep(3000);
		logOut();
		//********test case********
		login(url,login,pw);
		ajouterUnTag(nomDossier+date_current, nomTag+date_current);
		Thread.sleep(3000);
		logOut();
		//********criteres de succes********
		login(url,login,pw);
		verifierUnTagAjouter(nomDossier+date_current, nomTag+date_current);
		Thread.sleep(3000);
		logOut();
		//********post-condition********
		login(url,login,pw);
		supprimerUnTag(nomDossier+date_current, nomTag+date_current);
		supprimerUnDossier(nomDossier+date_current);
		Thread.sleep(6000);
		viderCorbeille();
		Thread.sleep(3000);
		logOut();
	}
	
//********************************************************************************************
//****************************************KEYWORDS********************************************
//********************************************************************************************
	public void verifierUnTagAjouter(String nomDossier, String nomTag) {
		String link_HeaderMesFichiers = locators.getProperty("link_HeaderMesFichiers");
		String link_TagDossierCiblePart1 = locators.getProperty("link_TagDossierCiblePart1");
		String link_TagDossierCiblePart2 = locators.getProperty("link_TagDossierCiblePart2");
		String link_TagDossierCiblePart3 = locators.getProperty("link_TagDossierCiblePart3");
		
		//cliquer sur le lien Mes Fichiers
		driver.findElement(By.id(link_HeaderMesFichiers)).click();
		System.out.println(driver.findElement(By.xpath(link_TagDossierCiblePart1+nomDossier+link_TagDossierCiblePart2+nomTag.toLowerCase()+link_TagDossierCiblePart3)).getText());
		//verifier le Tag
		Assert.assertEquals(driver.findElement(By.xpath(link_TagDossierCiblePart1+nomDossier+link_TagDossierCiblePart2+nomTag.toLowerCase()+link_TagDossierCiblePart3)).getText(), nomTag.toLowerCase());
		System.out.println("End of verification");
	}
	
	public void supprimerUnTag(String nomDossier, String nomTag) throws InterruptedException {
		String link_HeaderMesFichiers = locators.getProperty("link_HeaderMesFichiers");
		String link_TagDossierCiblePart1 = locators.getProperty("link_TagDossierCiblePart1");
		String link_TagDossierCiblePart2 = locators.getProperty("link_TagDossierCiblePart2");
		String link_TagDossierCiblePart3 = locators.getProperty("link_TagDossierCiblePart3");
		String btn_EditTagPart1 = locators.getProperty("btn_EditTagPart1");
		String btn_EditTagPart2 = locators.getProperty("btn_EditTagPart2");
		String btn_SupprimerUnTagPart1 = locators.getProperty("btn_SupprimerUnTagPart1");
		String btn_SupprimerUnTagPart2 = locators.getProperty("btn_SupprimerUnTagPart2");
		String btn_EngregistrerTagPart1 = locators.getProperty("btn_EngregistrerTagPart1");
		String btn_EngregistrerTagPart2 = locators.getProperty("btn_EngregistrerTagPart2");
		
		Actions action = new Actions(driver);
		
		//cliquer sur le lien Mes Fichiers
		driver.findElement(By.id(link_HeaderMesFichiers)).click();
		//Mouse over le lien de Tag cible
		action.moveToElement(driver.findElement(By.xpath(link_TagDossierCiblePart1+nomDossier+link_TagDossierCiblePart2+nomTag.toLowerCase()+link_TagDossierCiblePart3))).perform();
		Thread.sleep(3000);
		//cliquer sur icon rediger un Tag
		driver.findElement(By.xpath(btn_EditTagPart1+nomDossier+btn_EditTagPart2)).click();
		//cliquer sur bouton supprimer
		driver.findElement(By.xpath(btn_SupprimerUnTagPart1+nomTag.toLowerCase()+btn_SupprimerUnTagPart2)).click();
		//cliquer sur le bouton Enregistrer
		driver.findElement(By.xpath(btn_EngregistrerTagPart1+nomDossier+btn_EngregistrerTagPart2)).click();
		
	}
	
	public void ajouterUnTag(String nomDossier, String nomTag) throws InterruptedException {
		String link_HeaderMesFichiers = locators.getProperty("link_HeaderMesFichiers");
		String link_AucunTagCiblePart1 = locators.getProperty("link_AucunTagCiblePart1");
		String link_AucunTagCiblePart2 = locators.getProperty("link_AucunTagCiblePart2");
		String btn_EditTagPart1 = locators.getProperty("btn_EditTagPart1");
		String btn_EditTagPart2 = locators.getProperty("btn_EditTagPart2");
		String txt_Tag = locators.getProperty("txt_Tag");
		String btn_EngregistrerTagPart1 = locators.getProperty("btn_EngregistrerTagPart1");
		String btn_EngregistrerTagPart2 = locators.getProperty("btn_EngregistrerTagPart2");
		
		Actions action = new Actions(driver);
		//cliquer sur le lien Mes Fichiers
		driver.findElement(By.id(link_HeaderMesFichiers)).click();
		//Mouse over le lien "Aucun Tag" de Dossier cible
		action.moveToElement(driver.findElement(By.xpath(link_AucunTagCiblePart1+nomDossier+link_AucunTagCiblePart2))).perform();
		Thread.sleep(2000);
		//cliquer sur icon rediger un Tag
		driver.findElement(By.xpath(btn_EditTagPart1+nomDossier+btn_EditTagPart2)).click();
		//saisir nom de Tag
		driver.findElement(By.xpath(txt_Tag)).sendKeys(nomTag);
		//cliquer sur le bouton Enregistrer
		driver.findElement(By.xpath(btn_EngregistrerTagPart1+nomDossier+btn_EngregistrerTagPart2)).click();
		
	}
	
	public void supprimerUnDossier(String nomDossier) throws InterruptedException {
		String link_HeaderMesFichiers = locators.getProperty("link_HeaderMesFichiers");
		String link_DossierCiblePart1 = locators.getProperty("link_DossierCiblePart1");
		String link_DossierCiblePart2 = locators.getProperty("link_DossierCiblePart2");
		String link_PlusActionDossierCiblePart1 = locators.getProperty("link_PlusActionDossierCiblePart1");
		String link_PlusActionDossierCiblePart2 = locators.getProperty("link_PlusActionDossierCiblePart2");
		String link_SupprimerDossierCiblePart1 = locators.getProperty("link_SupprimerDossierCiblePart1");
		String link_SupprimerDossierCiblePart2 = locators.getProperty("link_SupprimerDossierCiblePart2");
		String btn_SupprimerDossier = locators.getProperty("btn_SupprimerDossier");
		
		Actions action = new Actions(driver);
		
		//cliquer sur le lien Mes Fichiers
		driver.findElement(By.id(link_HeaderMesFichiers)).click();
		//mouse over le lien de Dossier cible
		action.moveToElement(driver.findElement(By.xpath(link_DossierCiblePart1+nomDossier+link_DossierCiblePart2))).perform();
		//cliquer sur le lien plus Action
		driver.findElement(By.xpath(link_PlusActionDossierCiblePart1+nomDossier+link_PlusActionDossierCiblePart2)).click();
		Thread.sleep(2000);
		//cliquer sur le lien supprimer
		driver.findElement(By.xpath(link_SupprimerDossierCiblePart1+nomDossier+link_SupprimerDossierCiblePart2)).click();
		//cliquer bouton Supprimer
		driver.findElement(By.xpath(btn_SupprimerDossier)).click();
		
	}
	
	public void creerUnDossier(String nomDossier) {
		String link_HeaderMesFichiers = locators.getProperty("link_HeaderMesFichiers");
		String link_Creer = locators.getProperty("link_Creer");
		String link_CreerDossier = locators.getProperty("link_CreerDossier");
		String txt_NomDossier = locators.getProperty("txt_NomDossier");
		String btn_EnregistrerNouveauDossier = locators.getProperty("btn_EnregistrerNouveauDossier");
		
		//cliquer sur le lien Mes Fichiers
		driver.findElement(By.id(link_HeaderMesFichiers)).click();
		//cliquer sur le lien Creer
		driver.findElement(By.xpath(link_Creer)).click();
		//cliquer sur le lien Dossier
		driver.findElement(By.xpath(link_CreerDossier)).click();
		//saisir nom de Dossier
		driver.findElement(By.id(txt_NomDossier)).sendKeys(nomDossier);
		//cliquer sur le bouton Enregistrer
		driver.findElement(By.id(btn_EnregistrerNouveauDossier)).click();
		
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
	
	

}























