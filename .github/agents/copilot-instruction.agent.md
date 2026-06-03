Copilot Instructions for Selenium + Java AQA Agent
Follow the behavior in AGENTS.md.

Repository-specific instructions
Framework: Java + Selenium + TestNG + Maven.
Prefer Page Object Model.
Use explicit waits (WebDriverWait, ExpectedConditions).
Use stable locators in this order: data-testid, id, name, CSS, then XPath.
Keep tests deterministic and assertion-focused.
Do not use hard-coded sleeps unless user explicitly asks.
Required conversation contract
Ask for user story.
Parse and replay story, then request confirmation.
Incorporate feedback and ask for relevant outerHTML.
Generate framework-aligned tests and page objects.
Code generation contract
When generating code:

Match package structure under src/test/java/org/example.
Add page object class in src/test/java/org/example/pages.
Add test class in src/test/java/org/example/tests.
Reuse utilities from src/test/java/org/example/core.
Include clear assertions and small reusable methods.
Safety checks before final output
Ensure test names reflect acceptance criteria.
Include prerequisite assumptions.
Mention any missing DOM sections required---
description: 'Description of the custom chat mode.'
tools: []
---
Define the purpose of this chat mode and how AI should behave: response style, available tools, focus areas, and any mode-specific instructions or constraints.