package TestCases;


import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ModifierUnFiltre {
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
	public void TestModifierUnFiltre() throws IOException, InterruptedException{
		String idFiltre = "idXue";
		String nomFiltre = "nomXue";
		String nomFiltreModifie = "nomXueModifie";
		String proprieteFiltreModifie = "3"; //0,1,2,3
		String trierFiltreModifie =  "2"; //1,2,3,4,5
		String nbFiltreModifie = "2"; //1-20
		String lengthMinFiltreModifie = "3"; //1-20
		String nbMinResultFiltreModifie = "4"; //1-20
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String date_current = dateFormat.format(date);
		
		//********pre-condition********
		//login(url,login,pw);
		CreerUnFiltre(idFiltre+date_current, nomFiltre);
		Thread.sleep(3000);
		logOut();
		//********test case********
		driver.switchTo().newWindow(WindowType.WINDOW);
		login(url,login,pw);
		ModifierUnFiltre(idFiltre+date_current, nomFiltreModifie, proprieteFiltreModifie, trierFiltreModifie, nbFiltreModifie, lengthMinFiltreModifie, nbMinResultFiltreModifie);
		Thread.sleep(3000);
		logOut();
		//********criteres de succes********
		driver.switchTo().newWindow(WindowType.WINDOW);
		login(url,login,pw);
		VerifierModificationFiltre(idFiltre+date_current, nomFiltreModifie, nbFiltreModifie, lengthMinFiltreModifie, nbMinResultFiltreModifie);
		Thread.sleep(3000);
		logOut();
		//********post-condition********
		driver.switchTo().newWindow(WindowType.WINDOW);
		login(url,login,pw);
		SupprimerUnFiltre(idFiltre+date_current);
		Thread.sleep(3000);
		logOut();
	}
	
//********************************************************************************************
//****************************************KEYWORDS********************************************
//********************************************************************************************
	public void VerifierModificationFiltre(String idFiltre, String nomFiltreModifie, String nbFiltreModifie, String lengthMinFiltreModifie, String nbMinResultFiltreModifie) throws IOException, InterruptedException{
		String latestPage = null;
		String link_HeaderOutilsAdmin = locators.getProperty("link_HeaderOutilsAdmin");
		String link_GestionnaireDeRecherche = locators.getProperty("link_GestionnaireDeRecherche");
		String link_iciSearchManager = locators.getProperty("link_iciSearchManager");
		String txt_Nomfiltre = locators.getProperty("txt_Nomfiltre");
		String link_FiltreCiblePart1 = locators.getProperty("link_FiltreCiblePart1");
		String link_FiltreCiblePart2 = locators.getProperty("link_FiltreCiblePart2");
		String txt_NbFiltre = locators.getProperty("txt_NbFiltre");
		String txt_lengthMinFiltre = locators.getProperty("txt_lengthMinFiltre");
		String txt_nbMinResultFiltre = locators.getProperty("txt_nbMinResultFiltre");
		String btn_AnnulerrFiltre = locators.getProperty("btn_AnnulerrFiltre");
		
		//cliquer sur onglet Outil admin
		driver.findElement(By.xpath(link_HeaderOutilsAdmin)).click();
		//cliquer sur le lien Gestionnaire de recherche
		driver.findElement(By.xpath(link_GestionnaireDeRecherche)).click();
		//cliquer sur le lien ici
		driver.findElement(By.xpath(link_iciSearchManager)).click();
		
		//switch to latest window
		// Stocker les ID des fenetres dans une liste set.(importer la classe Set au besoin)
		Set<String> handles = driver.getWindowHandles();
		// Attribuer a chaque element une iteration avec la methode iterator() (Importer la methode Iterator au besoin)..
		Iterator<String> itr = handles.iterator();
				
		while (itr.hasNext()) {
			latestPage = itr.next();// stocker ID of the lastest page
		}
		// Switcher latest page (la page ouverte aprés le clique sur "ici")
		driver.switchTo().window(latestPage);

		//cliquer sur le lien de filtre cible
		driver.findElement(By.xpath(link_FiltreCiblePart1+idFiltre+link_FiltreCiblePart2)).click();
		Thread.sleep(2000);

		//verifier le champ Nom du filtre
		Assert.assertEquals(driver.findElement(By.xpath(txt_Nomfiltre)).getAttribute("value"), nomFiltreModifie);
		//verifier nb de filtre
		Assert.assertEquals(driver.findElement(By.xpath(txt_NbFiltre)).getAttribute("value"), nbFiltreModifie);
		//verifier longueur minimum du filtre
		Assert.assertEquals(driver.findElement(By.xpath(txt_lengthMinFiltre)).getAttribute("value"), lengthMinFiltreModifie);
		//saisir nb minimum de resultat
		Assert.assertEquals(driver.findElement(By.xpath(txt_nbMinResultFiltre)).getAttribute("value"), nbMinResultFiltreModifie);
		//cliquer sur bouton Annuler
		driver.findElement(By.xpath(btn_AnnulerrFiltre)).click();
		System.out.println("End of verification");
		
	}
	
	public void SupprimerUnFiltre(String idFiltre) throws InterruptedException {
		String latestPage = null;
		String link_HeaderOutilsAdmin = locators.getProperty("link_HeaderOutilsAdmin");
		String link_GestionnaireDeRecherche = locators.getProperty("link_GestionnaireDeRecherche");
		String link_iciSearchManager = locators.getProperty("link_iciSearchManager");
		String btn_SupprimerFiltreCiblePart1 = locators.getProperty("btn_SupprimerFiltreCiblePart1");
		String btn_SupprimerFiltreCiblePart2 = locators.getProperty("btn_SupprimerFiltreCiblePart2");
		String btn_OuiSupprimerFiltre = locators.getProperty("btn_OuiSupprimerFiltre");
			
		//cliquer sur onglet Outil admin
		driver.findElement(By.xpath(link_HeaderOutilsAdmin)).click();
		//cliquer sur le lien Gestionnaire de recherche
		driver.findElement(By.xpath(link_GestionnaireDeRecherche)).click();
		//cliquer sur le lien ici
		driver.findElement(By.xpath(link_iciSearchManager)).click();
		
		//switch to latest window
		// Stocker les ID des fenetres dans une liste set.(importer la classe Set au besoin)
		Set<String> handles = driver.getWindowHandles();
		// Attribuer a chaque element une iteration avec la methode iterator() (Importer la methode Iterator au besoin)..
		Iterator<String> itr = handles.iterator();
				
		while (itr.hasNext()) {
			latestPage = itr.next();// stocker ID of the lastest page
		}
		// Switcher latest page (la page ouverte aprés le clique sur "ici")
		driver.switchTo().window(latestPage);
		
		//cliquer le bouton Supprimer de filtre cible
		driver.findElement(By.xpath(btn_SupprimerFiltreCiblePart1+idFiltre+btn_SupprimerFiltreCiblePart2)).click();
		Thread.sleep(2000);
		//cliquer le bouton oui de comfirmation
		driver.findElement(By.id(btn_OuiSupprimerFiltre)).click();
		
	}
	
	public void ModifierUnFiltre(String idFiltre, String nomFiltreModifie, String proprieteFiltreModifie, String trierFiltreModifie, String nbFiltreModifie, String lengthMinFiltreModifie, String nbMinResultFiltreModifie) throws IOException, InterruptedException{
		String latestPage = null;
		String link_HeaderOutilsAdmin = locators.getProperty("link_HeaderOutilsAdmin");
		String link_GestionnaireDeRecherche = locators.getProperty("link_GestionnaireDeRecherche");
		String link_iciSearchManager = locators.getProperty("link_iciSearchManager");
		String txt_Nomfiltre = locators.getProperty("txt_Nomfiltre");
		String btn_EnregistreCreerUnNouveauFiltre = locators.getProperty("btn_EnregistreCreerUnNouveauFiltre");
		String link_FiltreCiblePart1 = locators.getProperty("link_FiltreCiblePart1");
		String link_FiltreCiblePart2 = locators.getProperty("link_FiltreCiblePart2");
		String link_ProprieteFiltre = locators.getProperty("link_ProprieteFiltre");
		String btn_menuProprieteFiltre = locators.getProperty("btn_menuProprieteFiltre");
		String btn_menuTrierFiltre = locators.getProperty("btn_menuTrierFiltre");
		String link_TrierFiltrePart1 = locators.getProperty("link_TrierFiltrePart1");
		String link_TrierFiltrePart2 = locators.getProperty("link_TrierFiltrePart2");
		String txt_NbFiltre = locators.getProperty("txt_NbFiltre");
		String txt_lengthMinFiltre = locators.getProperty("txt_lengthMinFiltre");
		String txt_nbMinResultFiltre = locators.getProperty("txt_nbMinResultFiltre");
		String btn_EnregistrerFiltre = locators.getProperty("btn_EnregistrerFiltre");

		
		//cliquer sur onglet Outil admin
		driver.findElement(By.xpath(link_HeaderOutilsAdmin)).click();
		//cliquer sur le lien Gestionnaire de recherche
		driver.findElement(By.xpath(link_GestionnaireDeRecherche)).click();
		//cliquer sur le lien ici
		driver.findElement(By.xpath(link_iciSearchManager)).click();
		
		//switch to latest window
		// Stocker les ID des fenetres dans une liste set.(importer la classe Set au besoin)
		Set<String> handles = driver.getWindowHandles();
		// Attribuer a chaque element une iteration avec la methode iterator() (Importer la methode Iterator au besoin)..
		Iterator<String> itr = handles.iterator();
		
		while (itr.hasNext()) {
			latestPage = itr.next();// stocker ID of the lastest page
		}
		// Switcher latest page (la page ouverte aprés le clique sur "ici")
		driver.switchTo().window(latestPage);

		//cliquer sur le lien de filtre cible
		driver.findElement(By.xpath(link_FiltreCiblePart1+idFiltre+link_FiltreCiblePart2)).click();
		//saisir le champ Nom du filtre
		driver.findElement(By.xpath(txt_Nomfiltre)).clear();
		driver.findElement(By.xpath(txt_Nomfiltre)).sendKeys(nomFiltreModifie);
		//cliquer sur le dropdown menu propriete du filtre
		driver.findElement(By.xpath(btn_menuProprieteFiltre)).click();
		//selectionner propriete du filtre
		driver.findElement(By.id(link_ProprieteFiltre+proprieteFiltreModifie)).click();
		//cliquer sur le dropdown menu TrierPar du filtre
		driver.findElement(By.xpath(btn_menuTrierFiltre)).click();
		Thread.sleep(2000);
		//selectionner trier du filtre
		driver.findElement(By.id(link_TrierFiltrePart1+trierFiltreModifie+link_TrierFiltrePart2)).click();
		//saisir nb de filtre
		driver.findElement(By.xpath(txt_NbFiltre)).clear();
		driver.findElement(By.xpath(txt_NbFiltre)).sendKeys(nbFiltreModifie);
		//saisir longueur minimum du filtre
		driver.findElement(By.xpath(txt_lengthMinFiltre)).clear();
		driver.findElement(By.xpath(txt_lengthMinFiltre)).sendKeys(lengthMinFiltreModifie);
		//saisir nb minimum de resultat
		driver.findElement(By.xpath(txt_nbMinResultFiltre)).clear();
		driver.findElement(By.xpath(txt_nbMinResultFiltre)).sendKeys(nbMinResultFiltreModifie);
		//cliquer bouton Enregistrer
		driver.findElement(By.xpath(btn_EnregistrerFiltre)).click();
	}

	
	public void CreerUnFiltre(String idFiltre, String nomFiltre) throws IOException, InterruptedException{
		String latestPage = null;
		login(url,login,pw);
		String link_HeaderOutilsAdmin = locators.getProperty("link_HeaderOutilsAdmin");
		String link_GestionnaireDeRecherche = locators.getProperty("link_GestionnaireDeRecherche");
		String link_iciSearchManager = locators.getProperty("link_iciSearchManager");
		String btn_CreerUnNouveauFiltre = locators.getProperty("btn_CreerUnNouveauFiltre");
		String txt_IDfiltre = locators.getProperty("txt_IDfiltre");
		String txt_Nomfiltre = locators.getProperty("txt_Nomfiltre");
		String btn_EnregistreCreerUnNouveauFiltre = locators.getProperty("btn_EnregistreCreerUnNouveauFiltre");
		
		//cliquer sur onglet Outil admin
		driver.findElement(By.xpath(link_HeaderOutilsAdmin)).click();
		//cliquer sur le lien Gestionnaire de recherche
		driver.findElement(By.xpath(link_GestionnaireDeRecherche)).click();
		//cliquer sur le lien ici
		driver.findElement(By.xpath(link_iciSearchManager)).click();
		
		//switch to latest window
		// Stocker les ID des fenetres dans une liste set.(importer la classe Set au besoin)
		Set<String> handles = driver.getWindowHandles();
		// Attribuer a chaque element une iteration avec la methode iterator() (Importer la methode Iterator au besoin)..
		Iterator<String> itr = handles.iterator();
		
		while (itr.hasNext()) {
			latestPage = itr.next();// stocker ID of the lastest page
		}
		// Switcher latest page (la page ouverte aprés le clique sur "ici")
		driver.switchTo().window(latestPage);
		
		//cliquer sur le bouton Creer Un Nouveau Filtre
		driver.findElement(By.id(btn_CreerUnNouveauFiltre)).click();
		//saisir le champ ID du filtre
		driver.findElement(By.xpath(txt_IDfiltre)).sendKeys(idFiltre);
		//saisir le champ Nom du filtre
		driver.findElement(By.xpath(txt_Nomfiltre)).sendKeys(nomFiltre);
		Thread.sleep(3000);
		//cliquer sur le bouton Enregistrer
		driver.findElement(By.xpath(btn_EnregistreCreerUnNouveauFiltre)).click();
		
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























