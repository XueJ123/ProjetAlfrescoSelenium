package Keywords;

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

public class ScriptKeywords {
	public static final int TIMEOUT_IN_SECONDS = 5;
	private WebDriver driver;
	private String url;
	private String login;
	private String pw;
	Date dateCourante;
	WebDriverWait wait;
  	Properties locators = new Properties();
  	
 @BeforeClass
 public void beforeClass() throws IOException {
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
 public void afterClass() {
 }	
 
 @BeforeTest
 public void beforeTest() {
	  dateCourante = new Date();
 }
 
 @AfterTest
 public void afterTest() {
 }
	
//********************************************************************************************
//*****************************************SCRIPTS********************************************
//********************************************************************************************	
	@Test (priority = 1)
	public void ConnexionETdeconnexion() throws IOException {
		login(url,login,pw);
		logOut();
	}
	
	@Test
	public void creerUnGroupTest() throws IOException, InterruptedException {
		login(url,login,pw);
		creerUnGroup("abc","group");
	}
	
	@Test
	public void supprimerUnAspectTest() throws IOException, InterruptedException {
		login(url,login,pw);
		supprimerUnAspect("modeleRobotXue","aspectRobotXue");
	}
	
	@Test
	public void supprimerUneProprieteTest() throws IOException, InterruptedException {
		login(url,login,pw);
		supprimerUnePropriete("modeleRobotXue","typeRobotXue", "propRobotXue");
	}
	
//********************************************************************************************
//****************************************KEYWORDS********************************************
//********************************************************************************************
	public void login(String url,String login,String pw) throws IOException {
	  	String txt_UserName = locators.getProperty("txt_UserName");
	  	String txt_Password = locators.getProperty("txt_Password");
	  	String btn_Login = locators.getProperty("btn_Login");
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(url);
		driver.findElement(By.name(txt_UserName)).sendKeys(login);
		driver.findElement(By.id(txt_Password)).sendKeys(pw);
		driver.findElement(By.id(btn_Login)).click();
	}
	
	public void logOut() {
		String link_HeaderUserMenu = locators.getProperty("link_HeaderUserMenu");
	    String link_HeaderDeconnexion = locators.getProperty("link_HeaderDeconnexion");
	    driver.findElement(By.id(link_HeaderUserMenu)).click();
	    driver.findElement(By.id(link_HeaderDeconnexion)).click();
	    driver.quit();
	}	
	
	/*public String initDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		return date1;	
	}*/
	
	public void creerUnGroup(String groupId, String groupName) throws InterruptedException {
		String link_HeaderOutilsAdmin = locators.getProperty("link_HeaderOutilsAdmin");
		String link_Groupes = locators.getProperty("link_Groupes");
		String btn_Parcourir = locators.getProperty("btn_Parcourir");
		String btn_NouveauGroupe = locators.getProperty("btn_NouveauGroupe");
		String txt_IdentifiantNouveauGroupe = locators.getProperty("txt_IdentifiantNouveauGroupe");
		String txt_NomNouveauGroupe = locators.getProperty("txt_NomNouveauGroupe");
		String btn_CreerUnGroupe = locators.getProperty("btn_CreerUnGroupe");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String date_current = dateFormat.format(date);
		
		//cliquer sur onglet Outil admin
		driver.findElement(By.xpath(link_HeaderOutilsAdmin)).click();
		//cliquer sur le lien Groupes
		driver.findElement(By.xpath(link_Groupes)).click();
		//cliquer le bouton Parcourir
		driver.findElement(By.id(btn_Parcourir)).click();
		//cliquer sur icone + Nouveau groupe
		driver.findElement(By.cssSelector(btn_NouveauGroupe)).click();
		//sasir Identifiant de groupe
		driver.findElement(By.id(txt_IdentifiantNouveauGroupe)).sendKeys(groupId+date_current);
		//saisr nom de groupe
		driver.findElement(By.id(txt_NomNouveauGroupe)).sendKeys(groupName+date_current);
		//cliquer sur le bouton Creer un groupe
		driver.findElement(By.id(btn_CreerUnGroupe)).click();
	}
	
	public void supprimerUnAspect(String nomModele, String nomAspect) throws InterruptedException {
		String link_HeaderOutilsAdmin = locators.getProperty("link_HeaderOutilsAdmin");
		String link_GestionnaireDeModeles = locators.getProperty("link_GestionnaireDeModeles");
		String link_NomDeModeleCiblePart1 = locators.getProperty("link_NomDeModeleCiblePart1");
		String link_NomDeModeleCiblePart2 = locators.getProperty("link_NomDeModeleCiblePart2");
		String btn_ActionDeAspectCiblePart1 = locators.getProperty("btn_ActionDeAspectCiblePart1");
		String btn_ActionDeAspectCiblePart2 = locators.getProperty("btn_ActionDeAspectCiblePart2");
		String link_SupprimerAspect = locators.getProperty("link_SupprimerAspect");
		String btn_SupprimerAspect = locators.getProperty("btn_SupprimerAspect");
		
		//cliquer sur onglet Outil admin
		driver.findElement(By.xpath(link_HeaderOutilsAdmin)).click();
		//cliquer sur le lien Gestionnaire de modeles
		driver.findElement(By.xpath(link_GestionnaireDeModeles)).click();
		//cliquer sur le nom de modele cible
		driver.findElement(By.xpath(link_NomDeModeleCiblePart1+nomModele+link_NomDeModeleCiblePart2)).click();
		Thread.sleep(4000);
		//cliquer sur le menu Action de Aspect cible
		driver.findElement(By.xpath(btn_ActionDeAspectCiblePart1+nomAspect+btn_ActionDeAspectCiblePart2)).click();
		//cliquer sur le lien Supprimer
		driver.findElement(By.xpath(link_SupprimerAspect)).click();
		//cliquer sur le bouton Supprimer
		driver.findElement(By.xpath(btn_SupprimerAspect)).click();	
	}
	
	public void supprimerUnePropriete(String nomModele, String nomType, String nomPropriete) throws InterruptedException {
		String link_HeaderOutilsAdmin = locators.getProperty("link_HeaderOutilsAdmin");
		String link_GestionnaireDeModeles = locators.getProperty("link_GestionnaireDeModeles");
		String link_NomDeModeleCiblePart1 = locators.getProperty("link_NomDeModeleCiblePart1");
		String link_NomDeModeleCiblePart2 = locators.getProperty("link_NomDeModeleCiblePart2");
		String link_TypeCiblePart1 = locators.getProperty("link_TypeCiblePart1");
		String link_TypeCiblePart2 = locators.getProperty("link_TypeCiblePart2");
		String btn_ActionDeProprieteCiblePart1 = locators.getProperty("btn_ActionDeProprieteCiblePart1");
		String btn_ActionDeProprieteCiblePart2 = locators.getProperty("btn_ActionDeProprieteCiblePart2");
		String link_SupprimerPropriete = locators.getProperty("link_SupprimerPropriete");
		String btn_SupprimerPropriete = locators.getProperty("btn_SupprimerPropriete");
		
		//cliquer sur onglet Outil admin
		driver.findElement(By.xpath(link_HeaderOutilsAdmin)).click();
		//cliquer sur le lien Gestionnaire de modeles
		driver.findElement(By.xpath(link_GestionnaireDeModeles)).click();
		//cliquer sur le nom de modele cible
		driver.findElement(By.xpath(link_NomDeModeleCiblePart1+nomModele+link_NomDeModeleCiblePart2)).click();
		Thread.sleep(4000);
		//cliquer sur le lien de Type cible
		driver.findElement(By.xpath(link_TypeCiblePart1+nomType+link_TypeCiblePart2)).click();
		//cliquer sur le bouton Actions de Propriete cible
		driver.findElement(By.xpath(btn_ActionDeProprieteCiblePart1+nomPropriete+btn_ActionDeProprieteCiblePart2)).click();
		//cliquer sur le lien Supprimer
		driver.findElement(By.xpath(link_SupprimerPropriete)).click();
		//cliquer sur le bouton Supprimer
		driver.findElement(By.xpath(btn_SupprimerPropriete)).click();
	}

	

}























