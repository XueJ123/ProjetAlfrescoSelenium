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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class RejoindreSitePublic {
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
	public void TestRejoindreSitePublic() throws IOException, InterruptedException{
		String prenom = "Prenom";
		String email = "@abc.com";
		String nom = "Nom";
		String pwUser = "123456";
		String nomSite = "NomSite";
		String urlSite = "UrlSite";
		String visibiliteSite = "PUBLIC";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String date_current = dateFormat.format(date);
		
		//********pre-condition********
		login(url,login,pw);
		creerUnCompte(prenom+date_current, date_current+email, nom+date_current, pwUser);
		Thread.sleep(10000);
		logOut();
		
		login(url,nom+date_current,pwUser);
		creerUnSite(nomSite+date_current, urlSite+date_current, visibiliteSite);
		Thread.sleep(3000);
		logOut();
		//********test case********
		login(url,login,pw);
		Thread.sleep(10000);
		rejoindreUnSite(nomSite+date_current);
		Thread.sleep(3000);
		//logOut();
		//********criteres de succes********
		//login(url,login,pw);
		verifierRejondreUnSite(nomSite+date_current);
		Thread.sleep(3000);
		logOut();
		//********post-condition********
		login(url,nom+date_current,pwUser);
		supprimerUnSite(nomSite+date_current);
		Thread.sleep(3000);
		logOut();
		login(url,login,pw);
		supprimerUnCompte(prenom+date_current);
		Thread.sleep(10000);
		viderCorbeille();
		Thread.sleep(3000);
		logOut();
	}
	
//********************************************************************************************
//****************************************KEYWORDS********************************************
//********************************************************************************************
	public void supprimerUnCompte(String prenom) throws InterruptedException {
		String link_HeaderOutilsAdmin = locators.getProperty("link_HeaderOutilsAdmin");
		String link_Utilisateur = locators.getProperty("link_Utilisateur");
		String txt_RechercherUtilisateur = locators.getProperty("txt_RechercherUtilisateur");
		String btn_RechercherUtilisateur = locators.getProperty("btn_RechercherUtilisateur");
		String link_UtilisateurCilbePart1 = locators.getProperty("link_UtilisateurCilbePart1");
		String link_UtilisateurCilbePart2 = locators.getProperty("link_UtilisateurCilbePart2");
		String btn_SupprimerUtilisateur = locators.getProperty("btn_SupprimerUtilisateur");
		String btn_OkSupprimerUtilisateur = locators.getProperty("btn_OkSupprimerUtilisateur");
		
		//cliquer sur onglet Outil admin
		driver.findElement(By.xpath(link_HeaderOutilsAdmin)).click();
		//cliquer lien Utilisateur
		driver.findElement(By.xpath(link_Utilisateur)).click();
		//saisir prenom de utilisateur cherche
		driver.findElement(By.id(txt_RechercherUtilisateur)).sendKeys(prenom);
		//cliquer bouton rechercher
		driver.findElement(By.id(btn_RechercherUtilisateur)).click();
		Thread.sleep(2000);
		//cliquer lien de utilisateur
		driver.findElement(By.xpath(link_UtilisateurCilbePart1+prenom+link_UtilisateurCilbePart2)).click();
		Thread.sleep(2000);
		//cliquer bouton Supprimer
		driver.findElement(By.id(btn_SupprimerUtilisateur)).click();
		Thread.sleep(2000);
		//cliquer bouton Supprimer dan fenetre confirmation
		driver.findElement(By.xpath(btn_OkSupprimerUtilisateur)).click();
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
		//cliquer lien de site cherche
		driver.findElement(By.xpath(link_SiteCiblePart1+nomSite+link_SiteCiblePart2)).click();
		//cliquer icone de configuration
		driver.findElement(By.id(btn_ConfigurationSite)).click();
		//cliquer lien Supprimer site
		driver.findElement(By.id(link_SupprimerSite)).click();
		Thread.sleep(2000);
		//cliquer bouton ok
		driver.findElement(By.id(btn_OkSupprimerSite)).click();
	}
	
	public void verifierRejondreUnSite(String nomSite) {
		String link_HeaderSites = locators.getProperty("link_HeaderSites");
		String link_MesSites = locators.getProperty("link_MesSites");
		String link_SiteCiblePart1 = locators.getProperty("link_SiteCiblePart1");
		String link_SiteCiblePart2 = locators.getProperty("link_SiteCiblePart2");
		
		//cliquer lien Sites
		driver.findElement(By.id(link_HeaderSites)).click();
		//cliquer lien Mes Sites
		driver.findElement(By.id(link_MesSites)).click();
		//verifier si la page contient le nom de site rejoindre
		if (driver.findElement(By.xpath(link_SiteCiblePart1+nomSite+link_SiteCiblePart2))==null) {
			fail("Le site n'est pas trouve");
		}
		
		System.out.println("End of verification");
	}
	
	public void rejoindreUnSite(String nomSite) throws InterruptedException {
		String link_HeaderSites = locators.getProperty("link_HeaderSites");
		String link_RechercheDeSite = locators.getProperty("link_RechercheDeSite");
		String txt_RechercherSite = locators.getProperty("txt_RechercherSite");
		String btn_RechercherSite = locators.getProperty("btn_RechercherSite");
		String btn_RejoindreCiblePart1 = locators.getProperty("btn_RejoindreCiblePart1");
		String btn_RejoindreCiblePart2 = locators.getProperty("btn_RejoindreCiblePart2");
		
		//cliquer lien Sites
		driver.findElement(By.id(link_HeaderSites)).click();
		//cliquer lien Recherche de site
		driver.findElement(By.xpath(link_RechercheDeSite)).click();
		//saisir nom de site cherche
		driver.findElement(By.xpath(txt_RechercherSite)).sendKeys(nomSite);
		//cliquer bouton Rechercher
		driver.findElement(By.id(btn_RechercherSite)).click();
		Thread.sleep(2000);
		//cliquer bouton Rejoindre de site cible
		driver.findElement(By.xpath(btn_RejoindreCiblePart1+nomSite+btn_RejoindreCiblePart2)).click();
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
	
	
	public void creerUnCompte(String prenom, String email, String nom, String pwUser) throws InterruptedException {
		String link_HeaderOutilsAdmin = locators.getProperty("link_HeaderOutilsAdmin");
		String link_Utilisateur = locators.getProperty("link_Utilisateur");
		String btn_NouvelUtilisateur = locators.getProperty("btn_NouvelUtilisateur");
		String txt_Prenom = locators.getProperty("txt_Prenom");
		String txt_Email = locators.getProperty("txt_Email");
		String txt_Nom = locators.getProperty("txt_Nom");
		String txt_PasswordUser = locators.getProperty("txt_PasswordUser");
		String txt_PasswordVerifier = locators.getProperty("txt_PasswordVerifier");
		String btn_CreerUtilisateur = locators.getProperty("btn_CreerUtilisateur");
		
		//cliquer sur onglet Outil admin
		driver.findElement(By.xpath(link_HeaderOutilsAdmin)).click();
		//cliquer lien utilisateur
		driver.findElement(By.xpath(link_Utilisateur)).click();
		Thread.sleep(2000);
		//cliquer bouton Nouvel Utilisateur
		driver.findElement(By.id(btn_NouvelUtilisateur)).click();
		Thread.sleep(2000);
		//saisir les champs
		driver.findElement(By.id(txt_Prenom)).sendKeys(prenom);
		driver.findElement(By.id(txt_Email)).sendKeys(email);
		driver.findElement(By.id(txt_Nom)).sendKeys(nom);
		driver.findElement(By.id(txt_PasswordUser)).sendKeys(pwUser);
		driver.findElement(By.id(txt_PasswordVerifier)).sendKeys(pwUser);
		//cliquer bouton Creer utilisateur
		driver.findElement(By.id(btn_CreerUtilisateur)).click();
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























