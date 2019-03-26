package com.n11;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import junit.framework.Assert;

//import org.eclipse.jetty.websocket.common.message.SimpleBinaryMessage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestN11 {
	public String mail = "xxxxxxx@yyyyyy.zzz";
	String pass = "xyzxyz";
	String url = "https://www.n11.com";
	public static WebDriver browser;

	@BeforeClass
	public static void setupBeforeRun() {
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\hamzakonsalt\\Downloads\\geckodriver-v0.24.0-win64\\geckodriver.exe");
		browser = new FirefoxDriver();
	}

	@Test
	public void test1N11() {
		//n11'in acilmasi(asama 1)
		browser.get(url);

		try {
			//n11'in acildiginin teiti(asama 1)
			WebElement titleEl = browser.findElement(By.tagName("title"));
			System.out.println(titleEl.getAttribute("text"));
			if (titleEl.getAttribute("text")=="Alýþveriþin Uðurlu Adresi") {
				Assert.fail("N11 ' e hos geldiniz.");
				
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public void test2Login() {

		try {
			//asama2 login
			WebElement logElement = browser.findElement(By.className("btnSignIn"));
			logElement.click();
			WebElement email = browser.findElement(By.id("email"));
			email.sendKeys(mail);
			WebElement password = browser.findElement(By.id("password"));
			password.sendKeys(pass);
			WebElement signIn = browser.findElement(By.id("loginButton"));
			signIn.click();
			Thread.sleep(6000);

		} catch (Exception e) {
			System.out.println(e);

		}

	}

	@Test
	public void test3Search() {

		try {
			//search
			WebElement searchData = browser.findElement(By.id("searchData"));
			searchData.sendKeys("samsung");
			WebElement searchBtn = browser.findElement(By.className("searchBtn"));
			searchBtn.click();
			Thread.sleep(5000);

			//burada gelen datanin , isaretinden dolayi integer olmamasini engellemek icin
			// ve herhangi bir sonuc varmi
			WebElement isItCorrect = browser.findElement(By.xpath("//div[@class='resultText ']/strong"));
			String isTrue=(isItCorrect.getText()).replaceAll(",", "");
			if (isItCorrect.getText() == null) {
				Assert.fail("Samsung için arama sonucu bulunamadý.");

			}
			if(Integer.parseInt(isTrue)>0) {
				System.out.println(Integer.parseInt(isTrue)+"Arama sonucu bulundu");

			}


		} catch (Exception e) {
			System.out.println(e);

		}

	}

	@Test
	public void test4SsecondryItem() {
		try {
			//2. sayfa
			WebElement item2Page = browser.findElements(By.xpath("//div[@class='pagination']/a")).get(1);
			item2Page.click();
			Thread.sleep(6000);
			
			WebElement item2 = browser.findElements(By.xpath("//div[@class='columnContent adBg']/div[@class='pro']/a")).get(2);
			WebElement item2Title = browser.findElements(By.xpath("//div[@class='columnContent adBg']/div[@class='pro']/a[@class='plink']/h3")).get(2);
			String item2Name=item2Title.getText();
			System.out.println("secilecek item "+item2Name);
			item2.click();

			Thread.sleep(6000);
			
			WebElement itemDetailTitle = browser.findElement(By.xpath("//div[@class='nameHolder']/h1"));
			String itemDetailName=itemDetailTitle.getText();

			System.out.println("secilmis item detay"+itemDetailName);
			if (!item2Name.equals(itemDetailName)) {
				Assert.fail("Samsung için yapýlan arama sonucu ile urun detay sonucu eslesmiyor.");

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}

	@Test
	public void test5FavoriEkle() {
		try {
			//browser.navigate().back();
			Thread.sleep(6000);
			//Burada Favoriye eklemek icin urunun rengi istendigi icin.
			WebElement colorChoose = browser.findElements(By.xpath("//select[@class='val-error']/option")).get(1);
			colorChoose.click();
			
			Thread.sleep(6000);
			
			//Favorilere ekleme
			WebElement item3FavoriEkle = browser.findElements(By.xpath("//div[@class='paymentDetail']/div[@class='btnHolder']/a")).get(2);
			item3FavoriEkle.click();
			Thread.sleep(6000);
			
			//Burda birden fazla liste olmadigi icin findElements kullanmadim.
			WebElement item3FavoriEkleFavorilerim = browser.findElement(By.xpath("//a[@class='addToFavorites item selected']"));
			item3FavoriEkleFavorilerim.click();
			
			//Cikan pop up'i yok etmek icin 
			browser.navigate().refresh();
			
			//Favoriler de karsilastirmak icin
			WebElement item3 = browser.findElements(By.xpath("//div[@class='proNameHolder']/div[@class='nameHolder']/h1")).get(3);
			//WebElement item3 = browser.findElements(By.xpath("//div[@class='pro']/a[@class='plink']/h3")).get(3);
			String item3Title=item3.getText();
									
			Thread.sleep(6000);
			
			//favorilere girmek icin
			WebElement favorilerimeGit = browser.findElements(By.xpath("//div[@class='customMenu']/div[@class='myAccountHolder customMenuItem hasMenu  withLocalization ']/div[@class='myAccountMenu hOpenMenu']/div[@class='hOpenMenuContent']/a")).get(2);
			favorilerimeGit.click();

			Thread.sleep(6000);
			
			//favori listesini acmak icin
			WebElement openFavori = browser.findElement(By.xpath("//div[@class='wishGroupListItem favorites']/div[@class='listItemWrap']/a"));
			openFavori.click();
			
			Thread.sleep(6000);
			
			//acilan favorilerdeki listeden title almak icin
			WebElement favoriDetail = browser.findElements(By.xpath("//div[@class='pro']/a[@class='plink']/h3")).get(0);
			String favoriDetailTitle=favoriDetail.getText();
			boolean condition=false;

			if (favoriDetail!=null) {
				Assert.fail("Favoriye ürün eklenmiþ\n");
				if (!item3Title.equals(favoriDetailTitle)) {
					Assert.fail("Favoriye eklediginiz ürün listede yok\n");

				}else {
					Assert.fail("Favoriye eklediginiz ürün favorilerinize baþarý ýle eklenmýs\n");

				}
			}			
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

	}
	
		@Test
	public void test6DeleteItem() {
		try {
			WebElement remo = browser.findElements(By.xpath("//div[@class='wishProBtns']/span")).get(0);
			remo.click();
			Thread.sleep(6000);
			browser.navigate().refresh();
			Thread.sleep(6000);

			List<WebElement> removeChechk = browser.findElements(By.xpath("//div[@class='pro']/a[@class='plink']/h3"));
			if (removeChechk==null) {
				System.out.println("Silme islemi basari ile gerceklesti");
				
				
			}else {
				System.out.println("Silme islemi gerceklestirilemedi");

			}
			
			
			

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}
}