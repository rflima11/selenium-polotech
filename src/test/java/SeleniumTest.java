import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.List;

public class SeleniumTest {

    WebDriver webDriver;

    @BeforeAll
    static void setupClass() {
        System.setProperty("webdriver.chrome.driver","chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        webDriver = new ChromeDriver(options);
    }

//    @AfterEach
//    void tearDown() {
//        webDriver.quit();
//    }

    @Test
    void deveLogarNoEcommerce() throws InterruptedException {
        dadoQueEstouLogado();
    }

    @Test
    void deveAdicionarProdutosNoCarrinho() throws InterruptedException {
        dadoQueEstouLogado();
        List<WebElement> itens = webDriver.findElements(By.xpath("//*[starts-with(@id,'add-to-cart')]"));
        for (WebElement element : itens) {
            element.click();
        }
        webDriver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(3000);
    }

    @Test
    void deveAdicionarNoCarrinhoIrParaTelaCheckout() throws InterruptedException {
        dadoQueEstouLogado();
        quandoAdicionoProdutosNoCarrinho();
        deveIrParaTelaCheckout();
        eApresentarErroCasoNaoPasseParametrosObrigatorios();
    }

    @Test
    void deveAdicionarNoCarrinhoIrParaTelaCheckoutComSucesso() throws InterruptedException {
        dadoQueEstouLogado();
        quandoAdicionoProdutosNoCarrinho();
        deveIrParaTelaCheckout();
        quandoPreenchoParametrosCheckout();
        //print screen do fim do teste
        ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
    }

    private void quandoPreenchoParametrosCheckout() {
        WebElement firstName = webDriver.findElement(By.id("first-name"));
        firstName.click();
        firstName.sendKeys("Rodolfo");

        WebElement lastName = webDriver.findElement(By.id("last-name"));
        lastName.click();
        lastName.sendKeys("Ferreira");

        WebElement zipCode = webDriver.findElement(By.id("postal-code"));
        zipCode.click();
        zipCode.sendKeys("719023502");

        webDriver.findElement(By.id("continue")).click();
    }

    private void deveIrParaTelaCheckout() {
        webDriver.findElement(By.id("checkout")).click();
    }
    private void quandoAdicionoProdutosNoCarrinho() {
        List<WebElement> itens = webDriver.findElements(By.xpath("//*[starts-with(@id,'add-to-cart')]"));
        for (WebElement element : itens) {
            element.click();
        }
        webDriver.findElement(By.className("shopping_cart_link")).click();
    }

    private void eApresentarErroCasoNaoPasseParametrosObrigatorios() {
        webDriver.findElement(By.id("continue")).click();
        webDriver.findElement(By.className("error-button"));
    }

    private void dadoQueEstouLogado() {
        webDriver.get("https://www.saucedemo.com/");
        WebElement username = webDriver.findElement(By.id("user-name"));
        username.click();
        username.sendKeys("standard_user");

        WebElement password = webDriver.findElement(By.id("password"));
        password.click();
        password.sendKeys("secret_sauce");

        WebElement button = webDriver.findElement(By.id("login-button"));
        button.click();
    }
}
