# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

Helios is the **JavaScript / pro-code edition** of the codbex platform — a thin Spring Boot
distribution that assembles [Eclipse Dirigible](https://github.com/eclipse/dirigible) components
into a runnable, brandable web application. There is very little original Java here: almost all
behavior (web IDE, engines, security, database tooling, APIs) comes from `org.eclipse.dirigible:*`
dependencies declared in `application/pom.xml` and from the `com.codbex.platform:codbex-platform-parent`
parent POM (which defines all dependency versions, build plugins, and the Maven profiles below).

When changing functionality, the relevant code usually lives in the upstream Dirigible/platform
artifacts, **not in this repo**. This repo controls *which* components are bundled, the branding,
a couple of custom UI components, and the test suite wiring.

Helios is the **API-focused JavaScript edition** of the codbex platform (see
https://www.codbex.com/products/): a lean pro-code environment for building Enterprise APIs in
JavaScript, with UI, debugger, Git, theming, databases, jobs, message listeners, websockets,
security, and extensions — but deliberately *without* the heavier engines such as BPM and CMS.
That scoping is expressed entirely through the dependency set in `application/pom.xml` — the same
lever you use to add or drop a component here.

**Three deliberate departures from the platform defaults**, all in the edition's own configuration:

- **Full Java support alongside JavaScript.** `engine-java` (in-process `javac` + the bean container), `data-store-java` (Java `@Entity` -> Hibernate), the `api-modules-java` SDK, `ide-java-lsp` (JDT.LS), `ide-java-debug` (the DAP bridge) and the Java / Java Debug views. Note that the SDK exposes `org.eclipse.dirigible.sdk.bpm`, so `api-modules-java` -> `api-bpm` -> `engine-bpm-flowable` puts the BPM engine on the classpath; this edition ships none of the BPM tooling.
- **No Intent Driven tooling.** There is no `engine-intent`, so `resources-builder` (the conversational Builder shell) is excluded from `group-ui` rather than shipped with nothing behind it. `resources-inbox` is excluded for the same reason - the BPM task inbox has no BPM surface here.
- **Home is the Workbench IDE.** `DIRIGIBLE_HOME_URL=services/web/shell-ide/` in `dirigible.properties`, overriding the platform's 14.16.0+ default of `services/web/home/`. The launchpad stays reachable at `/services/web/home/`; only the `/` redirect changes. Consequence: upstream's `HomepageRedirectIT` asserts the platform default and cannot run here - it was dropped from the common suite, and the edition's own `HomePageIT` (Workbench welcome view on `/`) guards the override.

## Dirigible version

The version is pinned by `codbex-platform-parent` through its `dirigible.version` property; parent releases track Dirigible releases 1:1 (parent 14.17.0 -> Dirigible 14.17.0), so bumping the edition means bumping the parent. Bumps are not mechanical - Dirigible removes things: the OData engine was extracted in 14.16.0 (un-managing `com.codbex.olingo:olingo-odata2-lib`, which fails a version-less declaration at model-read time) and the AngularJS/TypeScript application templates went with it.

**The UI overrides under `components/ui/` are forks of upstream files** - diff them against the matching Dirigible module on every bump; they drift silently (translations must live in `i18n/<locale>/*.json`, the only folder `platform-core/extension-services/locales.js` scans).

## Build & run

There is no Maven wrapper; use a system `mvn` (Java 21 / Amazon Corretto 21 per the Dockerfile).

```bash
# Fast build (skips tests/checks) → produces application/target/codbex-helios-application-*.jar
mvn -T 1C clean install -P quick-build

# Run the standalone jar (port 80; needs the --add-opens flags)
java --add-opens=java.base/java.lang=ALL-UNNAMED \
     --add-opens=java.base/java.lang.reflect=ALL-UNNAMED \
     --add-opens=java.base/java.nio=ALL-UNNAMED \
     -jar application/target/codbex-helios-*.jar
# Debug on port 8000: add -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000

# Format the code (run before committing Java changes)
mvn verify -P format
```

App runs at http://localhost:80, default login `admin` / `admin`. REST API / Swagger at
`http://localhost/swagger-ui/index.html`.

The Maven profiles (`quick-build`, `unit-tests`, `integration-tests`, `tests`, `format`) are
defined in the parent POM, not here.

## Tests

```bash
mvn clean install -P unit-tests          # unit tests
mvn clean install -P integration-tests   # Selenium-based UI/integration tests
mvn clean install -P tests               # everything

# Run a single integration test (Surefire/Failsafe single-test syntax)
mvn clean install -P integration-tests -pl integration-tests -Dit.test=HomePageIT
```

- **Unit tests** (`application/src/test`): essentially just `HeliosApplicationTest#contextLoads`
  — a `@SpringBootTest` that verifies the assembled application context boots.
- **Integration tests** (`integration-tests/`): browser-driven tests extending
  `HeliosIntegrationTest` (which extends Dirigible's `UserInterfaceIntegrationTest`). Local Helios
  tests live in `com.codbex.helios.integration.tests`; `DirigibleCommonTestSuiteIT` additionally
  pulls in shared upstream Dirigible IT suites. `TestConfigurations` component-scans
  `org.eclipse.dirigible` so the full app is available under test.

## Module layout

Reactor modules (parent `pom.xml`): `application`, `branding`, `components`, `integration-tests`.

- **`application/`** — the Spring Boot app. `HeliosApplication` is a `@SpringBootApplication`
  that `scanBasePackages = "org.eclipse.dirigible"` (so all Dirigible Spring beans are picked up)
  and **excludes** the JDBC/JPA datasource auto-configurations (Dirigible manages datasources
  itself). The long dependency list in `application/pom.xml` is the actual product definition —
  add/remove a `dirigible-components-*` dependency to include/exclude an IDE view, engine, editor,
  template, or security provider. Also contains the `Dockerfile` (Corretto 21 + ttyd/node/git/fonts).
- **`branding/`** & **`components/`** — packaged as Dirigible content, not normal Java. Resources
  under `src/main/resources/META-INF/dirigible/<guid>/` (each with a `project.json`) are loaded
  into the Dirigible registry at runtime. `branding` supplies logo/favicon; `components/ui/menu-help`
  and `components/ui/view-welcome` are custom UI pieces that *override* the upstream Dirigible
  equivalents — note `application/pom.xml` `<exclusion>`s `dirigible-components-ui-menu-help` and
  swaps in the codbex versions.
- **`integration-tests/`** — test-only module.
- **`helm/otc/`** — Helm chart for Open Telekom Cloud deployment (versioned independently).

## Configuration

- `application/src/main/resources/dirigible.properties` — Dirigible/branding settings
  (product name, instance name, multi-tenant flag, branding theme). Placeholders like
  `${project.version}` / `${git.commit.id}` are filled at build time via resource filtering.
- `application.properties` activates Spring profiles `common,app-default`. To enable Dirigible's
  own behavior you must activate profiles explicitly, e.g.
  `SPRING_PROFILES_ACTIVE=common,snowflake,app-default`.
- `application-app-default.properties` sets `server.port=80`.
