/*
 * Copyright 2014 Black Pepper Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.blackpepper.support.selenium;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public final class ExpectedConditions2 {
	
	private ExpectedConditions2() {
		throw new AssertionError();
	}
	
	public static ExpectedCondition<Boolean> visibilityOfElementLocated(final By locator) {
		return ExpectedConditions.not(ExpectedConditions.invisibilityOfElementLocated(locator));
	}
	
	public static ExpectedCondition<Boolean> urlIs(final String url) {
		return new ExpectedCondition<Boolean>() {
			
			private String currentUrl;
			
			@Override
			public Boolean apply(WebDriver driver) {
				currentUrl = driver.getCurrentUrl();
				return url.equals(currentUrl);
			}
			
			@Override
			public String toString() {
				return String.format("URL to be \"%s\". Current URL: \"%s\"", url, currentUrl);
			}
		};
	}
	
	public static ExpectedCondition<Boolean> and(final ExpectedCondition<Boolean> condition1,
		final ExpectedCondition<Boolean> condition2) {
		return new ExpectedCondition<Boolean>() {
			
			@Override
			public Boolean apply(WebDriver driver) {
				Boolean value1 = condition1.apply(driver);
				Boolean value2 = condition2.apply(driver);
				return value1 && value2;
			}
			
			@Override
			public String toString() {
				return String.format("%s and %s", condition1, condition2);
			}
		};
	}
	
	public static ExpectedCondition<Boolean> anotherWindowToBeAvailableAndSwitchToIt() {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				Set<String> handles = driver.getWindowHandles();
				handles.remove(driver.getWindowHandle());
				
				if (handles.isEmpty()) {
					return false;
				}
				
				driver.switchTo().window(handles.iterator().next());
				return true;
			}
			
			@Override
			public String toString() {
				return "another window to be available";
			}
		};
	}
}
