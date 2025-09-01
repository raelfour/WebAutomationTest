# SauceDemo Web Automation Test

## 📋 Project Overview

This is an automated testing framework built with **Cucumber**, **Selenium WebDriver**, and **Java** using **Gradle** build system. The framework tests the login functionality of [SauceDemo](https://www.saucedemo.com/) application using the **Page Object Model (POM)** design pattern.

## 🛠 Technology Stack

- **Java**: 24
- **Gradle**: 8.14.3
- **Cucumber**: 7.18.0
- **Selenium WebDriver**: 4.15.0
- **JUnit**: 5.10.0
- **WebDriverManager**: 5.5.3 
- **AssertJ**: 3.24.2

## 🚀 Getting Started

### Prerequisites

1. **Java 24** - Install from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
2. **IntelliJ IDEA**
3. **Firefox Browser** (default)

### Setup Instructions

1. **Clone the repository:**

2. **Import in IntelliJ IDEA:**

3. **Install required plugins in IntelliJ:**

4. **Verify setup:**
   ```bash
   ./gradlew build
   ```

##  Running Tests

### Command Line Options

#### Run all tests:
```bash
./gradlew test
```

```bash
./gradlew cucumberTest
```

#### Run with different browsers:
```bash
# Firefox (default)
./gradlew cucumberTest

# Chrome
./gradlew cucumberTest -Dbrowser=chrome
```

#### Run in headless mode:
```bash
./gradlew cucumberTest -Dheadless=true
```

### IDE Options

#### Run from IntelliJ IDEA:
1. **Run all tests**: Right-click on `TestRunner.java` → Run 'TestRunner'
2. **Run specific feature**: Right-click on `login.feature` → Run Feature

## 📊 Test Reports

After running tests, reports are generated in:
- **HTML Report**: `build/cucumber-reports/html-report/index.html`

## 🎯 Test Coverage

### Test Scenarios Covered:

#### ✅ Positive Test Cases:
- **Successful login** with valid credentials (standard_user/secret_sauce)
- **Successful logout** after login
- **Home page verification** after successful login

#### ❌ Negative Test Cases:
- Login with **invalid username**
- Login with **invalid password**
- Login with **empty username**
- Login with **empty password**
- Login with **both fields empty**

#### 🔍 Boundary Test Cases:
- Login with **extremely long username**
- Login with **extremely long password**

### Test Credentials:
- **Username**: `standard_user`
- **Password**: `secret_sauce`
- **URL**: https://www.saucedemo.com/

## 🏗 Framework Architecture

### Page Object Model (POM)
The framework uses POM design pattern with two main page classes:

- **LoginPage.java**: Handles all login page interactions
    - Elements: username field, password field, login button, error messages
    - Methods: enterUsername(), enterPassword(), clickLoginButton(), getErrorMessage()

- **HomePage.java**: Handles all home page interactions
    - Elements: app logo, menu, products
    - Methods: openMenu(), clickLogout(), getProductNames()

### Step Definitions
- **LoginStepDefinitions.java**: Maps Gherkin steps to Java methods
    - Uses WebDriver setup/teardown with @Before/@After hooks
    - Implements all step definitions for login scenarios

### Feature Files
- **login.feature**: Written in Gherkin syntax (English)
    - Background step for common setup
    - Scenarios tagged with @positive, @negative, @boundary, @smoke

## 🔧 Configuration

### Browser Configuration
Default browser is Firefox. To change:
```bash
# Firefox
./gradlew test -Dbrowser=firefox

# Chrome 
./gradlew test -Dbrowser=chrome
```

### WebDriver Management
- Uses **WebDriverManager** for automatic driver management
- No need to download and manage driver executables manually
- Supports Chrome and Firefox browsers

### Timeouts
- **Implicit Wait**: 10 seconds
- **Explicit Wait**: 10 seconds
- **Page Load Timeout**: 30 seconds

### WebDriver issues:
- Ensure Chrome/Firefox browser is installed
- Check internet connection for WebDriverManager downloads

### Test timeouts:
- Increase wait times in page object classes
- Check if website is accessible
- Verify element locators are correct
