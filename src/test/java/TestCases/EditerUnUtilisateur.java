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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class EditerUnUtilisateur {
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
	public void TestEditerUnUtilisateur() throws IOException, InterruptedException{
		String prenom = "Prenom";
		String email = "@abc.com";
		String nom = "Nom";
		String pwUser = "123456";
		String prenomNew = "PrenomNew";
		String emailNew = "@Newabc.com";
		String nomNew = "NomNew";
		String pwUserNew = "123456NewPassword";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String date_current = dateFormat.format(date);
		
		//********pre-condition********
		login(url,login,pw);
		creerUnCompte(prenom+date_current, date_current+email, nom+date_current, pwUser);
		Thread.sleep(10000);
		logOut();
		//********test case********
		login(url,login,pw);
		editerUnUtilisateur(prenom+date_current, prenomNew+date_current, date_current+emailNew, nomNew+date_current, pwUserNew);
		Thread.sleep(3000);
		logOut();
		//********criteres de succes********
		login(url,login,pw);
		verifierEditerUnUtilisateur(prenomNew+date_current, nomNew+date_current, date_current+emailNew);
		Thread.sleep(3000);
		//logOut();
		//********post-condition********
		//login(url,login,pw);
		supprimerUnCompte(prenomNew+date_current);
		Thread.sleep(10000);
		logOut();
	}
	
//********************************************************************************************
//****************************************KEYWORDS********************************************
//********************************************************************************************
	public void verifierEditerUnUtilisateur(String prenom, String nom, String email) throws InterruptedException {
		String link_HeaderOutilsAdmin = locators.getProperty("link_HeaderOutilsAdmin");
		String link_Utilisateur = locators.getProperty("link_Utilisateur");
		String txt_RechercherUtilisateur = locators.getProperty("txt_RechercherUtilisateur");
		String btn_RechercherUtilisateur = locators.getProperty("btn_RechercherUtilisateur");
		String link_UtilisateurCilbePart1 = locators.getProperty("link_UtilisateurCilbePart1");
		String link_UtilisateurCilbePart2 = locators.getProperty("link_UtilisateurCilbePart2");
		String label_PrenomNomUser = locators.getProperty("label_PrenomNomUser");
		String label_EmailUser = locators.getProperty("label_EmailUser");
		
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
		Assert.assertEquals(driver.findElement(By.id(label_PrenomNomUser)).getText(), prenom+" "+nom);
		Assert.assertEquals(driver.findElement(By.id(label_EmailUser)).getText(), email);
		System.out.println("End of verification");
	}
	
	public void editerUnUtilisateur(String prenom, String prenomNew, String emailNew, String nomNew, String pwUserNew) throws InterruptedException {
		String link_HeaderOutilsAdmin = locators.getProperty("link_HeaderOutilsAdmin");
		String link_Utilisateur = locators.getProperty("link_Utilisateur");
		String txt_RechercherUtilisateur = locators.getProperty("txt_RechercherUtilisateur");
		String btn_RechercherUtilisateur = locators.getProperty("btn_RechercherUtilisateur");
		String link_UtilisateurCilbePart1 = locators.getProperty("link_UtilisateurCilbePart1");
		String link_UtilisateurCilbePart2 = locators.getProperty("link_UtilisateurCilbePart2");
		String btn_ModifierUtilisateur = locators.getProperty("btn_ModifierUtilisateur");
		String txt_PrenomUpdate = locators.getProperty("txt_PrenomUpdate");
		String txt_NomUpdate = locators.getProperty("txt_NomUpdate");
		String txt_EmailUpdate = locators.getProperty("txt_EmailUpdate");
		String txt_PasswordUpdate = locators.getProperty("txt_PasswordUpdate");
		String txt_PasswordVerifyUpdate = locators.getProperty("txt_PasswordVerifyUpdate");
		String btn_EnregistrerModification = locators.getProperty("btn_EnregistrerModification");
		
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
		driver.findElement(By.id(btn_ModifierUtilisateur)).click();
		Thread.sleep(2000);
		//saisir les champs
		driver.findElement(By.id(txt_PrenomUpdate)).clear();
		driver.findElement(By.id(txt_PrenomUpdate)).sendKeys(prenomNew);
		driver.findElement(By.id(txt_NomUpdate)).clear();
		driver.findElement(By.id(txt_NomUpdate)).sendKeys(nomNew);
		driver.findElement(By.id(txt_EmailUpdate)).clear();
		driver.findElement(By.id(txt_EmailUpdate)).sendKeys(emailNew);
		driver.findElement(By.id(txt_PasswordUpdate)).sendKeys(pwUserNew);
		driver.findElement(By.id(txt_PasswordVerifyUpdate)).sendKeys(pwUserNew);
		//cliquer bouton Enregister
		driver.findElement(By.id(btn_EnregistrerModification)).click();
		
	}
	
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























