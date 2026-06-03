# AQA Agent Selenium + Java (Hybrid: TestNG + Cucumber)

This project provides a **Hybrid UI test automation framework** using:

- Selenium WebDriver (browser automation)
- TestNG (execution, grouping, parallelism, assertions)
- Maven (build + dependency management)
- Cucumber (BDD Gherkin feature files)
- ExtentReports (HTML reporting via Cucumber adapter)
- Log4j2 (logging)
- Apache POI / Jackson / OpenCSV (Excel/JSON/CSV test data)

## Project structure

- `src/test/java/org/example/core`
  - Drivergit  lifecycle (`DriverManager`, `DriverFactory`)
  - Base test (`BaseTest`) for TestNG tests
  - Config (`Config`, `ConfigLoader`) reading `src/test/resources/config/*.properties`
  - Common explicit-wait actions (`ElementActions`)
- `src/test/java/org/example/pages` – Page Objects (POM)
- `src/test/java/org/example/data` – Data readers (CSV/JSON/Excel) + loader
- `src/test/java/org/example/keywords` – Keyword-driven layer (keyword engine + object repository)
- `src/test/java/org/example/bdd` – Cucumber hooks/steps/runners
- `src/test/resources/features` – `.feature` files
- `src/test/resources/testdata` – external test data files
- `src/test/resources/object-repo` – object repository (logical keys → locators)

## Hybrid design (how it fits together)

- **Modular (POM):** UI elements and small reusable actions live in page objects (example: `LoginPage`).
- **Data-driven:** test data is loaded from `src/test/resources/testdata/*` (CSV/JSON/Excel). TestNG uses `@DataProvider`, and Cucumber can load external datasets inside steps.
- **Keyword-driven:** `KeywordEngine` can run externalized step rows like `TYPE login.username Admin` using an object repo (`object-repo/login.properties`). This allows non-developers to compose test flows from reusable actions.
- **BDD:** business-readable scenarios live in `src/test/resources/features/*.feature`, while step definitions call into page objects and utilities.

## Configuration

Default config file: `src/test/resources/config/config.properties`

You can override values at runtime using JVM properties:
- `-Denv=local`
- `-Dbrowser=chrome`
- `-Dapp.url=<url>`
- `-Dapp.username=<username>` `-Dapp.password=<password>`

## Run tests

### 1) Run UI tests (TestNG)

```powershell
mvn clean test
```

### 2) Run BDD tests (Cucumber + TestNG runner)

```powershell
mvn clean test -Pbdd
```

### 3) Run with overrides

```powershell
mvn clean test -Dapp.url="https://opensource-demo.orangehrmlive.com/web/index.php/auth/login" -Dbrowser=chrome
```

### 4) Run specific Cucumber tags

Update tags in `src/test/java/org/example/bdd/runners/RunCucumberTestNgTest.java` (the `tags` option), or pass tags via:

```powershell
mvn clean test -Pbdd -Dcucumber.filter.tags="@smoke"
```

## Reports

- Cucumber HTML/JSON: `target/cucumber-reports/`
- Extent report (adapter): `target/extent/ExtentReport.html`
- TestNG reports: `test-output/`

## How to add new tests

### Add new Page Object
1. Create a new class under `src/test/java/org/example/pages`.
2. Keep page methods small and reusable.
3. Use stable locators in this order: `data-testid`, `id`, `name`, CSS, then XPath.
4. Don’t add assertions in page objects.

### Add new TestNG test
1. Add a new test class in `src/test/java/org/example/tests`.
2. Extend `BaseTest`.
3. Keep assertions in the test class.
4. If needed, add a `@DataProvider` in `org.example.data.providers` and store datasets in `src/test/resources/testdata`.

### Add new Cucumber scenario
1. Add/extend a `.feature` file under `src/test/resources/features`.
2. Implement step definitions under `src/test/java/org/example/bdd/steps`.
3. Reuse page objects and utilities.

### Add keyword-driven flows
1. Add element mappings to `src/test/resources/object-repo/*.properties`.
2. Build a list of `StepDefinitionRow` and call `KeywordEngine.execute(...)` inside a TestNG test or a Cucumber step.
