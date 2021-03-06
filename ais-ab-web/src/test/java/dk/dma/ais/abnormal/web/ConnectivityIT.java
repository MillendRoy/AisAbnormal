/* Copyright (c) 2011 Danish Maritime Authority
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */
package dk.dma.ais.abnormal.web;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ConnectivityIT {

    private static WebDriver driver;

    @BeforeClass
    public static void setUp() throws IOException {
        driver = IntegrationTestHelper.createPhantomJSWebDriver();
    }

    @Test
    public void testInternetConnectivity() {
        driver.get("http://www.google.com");

        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("Cheese!");
        element.submit();

        assertEquals("Cheese! - Google", driver.getTitle().substring(0, "Cheese! - Google".length()));
    }

    @Test
    public void testApplicationConnectivity() {
        driver.get("http://127.0.0.1:8080/abnormal");
        assertEquals("Danish Maritime Authority", driver.getTitle());
    }

    @AfterClass
    public static void tearDown() {
        driver.close();
    }

}
