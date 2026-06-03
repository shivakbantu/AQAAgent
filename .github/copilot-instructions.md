# Copilot Instructions for Selenium + Java AQA Agent

Follow the behavior in `AGENTS.md`.

## Repository-specific instructions

- Framework: Java + Selenium + TestNG + Maven.
- Prefer Page Object Model.
- Use explicit waits (`WebDriverWait`, `ExpectedConditions`).
- Use stable locators in this order: `data-testid`, `id`, `name`, CSS, then XPath.
- Keep tests deterministic and assertion-focused.
- Do not use hard-coded sleeps unless user explicitly asks.

## Required conversation contract

1. Ask for user story.
2. Parse and replay story, then request confirmation.
3. Incorporate feedback and ask for relevant `outerHTML`.
4. Generate framework-aligned tests and page objects.

## Input-adaptive contract (apply on every user request)

1. Parse the latest user input and classify intent as one of:
   - `test-creation`, `test-update`, `locator-help`, `debug-failure`, `refactor`, `framework-config`, `general-question`.
2. Replay interpreted task in 1-2 lines.
3. Ask for confirmation only when intent is ambiguous, risky, or conflicts with earlier context.
4. Request missing `outerHTML` only when robust locator design depends on it.
5. Generate or modify code in framework-aligned packages.
6. List assumptions and missing information before finalizing output.

## Code generation contract

When generating code:
- Match package structure under `src/test/java/org/example`.
- Add page object class in `src/test/java/org/example/pages`.
- Add test class in `src/test/java/org/example/tests`.
- Reuse utilities from `src/test/java/org/example/core`.
- Include clear assertions and small reusable methods.
- Keep assertions in test classes; avoid assertions in page objects.

## File extension-based instructions

### `.feature` files (Gherkin)

- Use behavior-focused scenarios with Given/When/Then.
- Keep steps business-readable and avoid Selenium implementation details.
- Prefer one acceptance criterion per scenario.
- Use `Scenario Outline` only when data variation is required.
- Add meaningful tags such as `@smoke`, `@regression`, `@login` when relevant.

Template:

```gherkin
Feature: <business capability>

  @regression
  Scenario: <acceptance criterion title>
    Given <initial state>
    When <user action>
    Then <observable outcome>
```

### `.java` files

- Keep package structure under `src/test/java/org/example`.
- Place page objects in `src/test/java/org/example/pages`.
- Place tests in `src/test/java/org/example/tests`.
- Reuse core utilities from `src/test/java/org/example/core`.
- Keep page object methods small and reusable.
- Use explicit waits and stable locator priority.
- Name tests to match acceptance criteria.

## Dynamic routing hints by input type

- If input indicates new automation (for example: create test, new scenario), create or update page object and test classes.
- If input indicates failure analysis (for example: failing test, stacktrace, error), diagnose root cause first and patch minimally.
- If input indicates locator instability, ask for target `outerHTML` and propose a fallback locator chain.
- If input indicates refactoring, preserve behavior and improve readability or reuse.
- If input is free-form or random, infer the most likely QA intent and ask for confirmation only if needed.

## Safety checks before final output

- Ensure test names reflect acceptance criteria.
- Include prerequisite assumptions.
- Mention any missing DOM sections required for robust selectors.
- Confirm alignment with Selenium + TestNG + POM conventions.
