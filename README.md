### The project directory structure

```Gherkin
src
  + main
  + test
    + java                        Test runners and supporting code
      + starter 
        + actions                 Low-level api-call implementation
        + assertions              api-call response assertions
        + enums                   Constants storage
        + pojo                    Test data models storage
        + specification           Specification for requests and responses
        + stepdefinitions         Screenplay components are assigning to action methods
          + TestRunner            Testsuite
    + resources
      + features                  Feature files directory 
        + account                 account tests subdirectory
        + failed                  failed tests subdirectory
        + product                 product tests subdirectory
      + schema                    Storage json schema validation files
        + logback-test.xml        Logger configuration file
        + serenity.conf           Serenity configuration file       

## The sample scenario

Both variations of the sample project uses the sample Cucumber scenario. In this scenario, Sergey (who likes to search
for stuff) is performing a search on the internet:

```Gherkin
Feature: Search by keyword

  Scenario: Searching for a term
    Given Sergey is researching things on the internet
    When he looks up "Cucumber"
    Then he should see information about "Cucumber"
```

### The Screenplay implementation

The sample code in the master branch uses the Screenplay pattern. The Screenplay pattern describes tests in terms of
actors and the tasks they perform. Tasks are represented as objects performed by an actor, rather than methods. This
makes them more flexible and composable, at the cost of being a bit more wordy. Here is an example:

```java
    @Given("{actor} is researching things on the internet")
    public void researchingThings(Actor actor) {
        actor.wasAbleTo(NavigateTo.theWikipediaHomePage());
    }

    @When("{actor} looks up {string}")
    public void searchesFor(Actor actor, String term) {
        actor.attemptsTo(
                LookForInformation.about(term)
        );
    }

    @Then("{actor} should see information about {string}")
    public void should_see_information_about(Actor actor, String term) {
        actor.attemptsTo(
                Ensure.that(WikipediaArticle.HEADING).hasText(term)
        );
    }
```

Screenplay classes emphasise reusable components and a very readable declarative style, whereas Lean Page Objects and
Action Classes (that you can see further down) opt for a more imperative style.

The `NavigateTo` class is responsible for opening the Wikipedia home page:

```java
public class NavigateTo {
    public static Performable theWikipediaHomePage() {
        return Task.where("{0} opens the Wikipedia home page",
                Open.browserOn().the(WikipediaHomePage.class));
    }
}
```

The `LookForInformation` class does the actual search:

```java
public class LookForInformation {
    public static Performable about(String searchTerm) {
        return Task.where("{0} searches for '" + searchTerm + "'",
                Enter.theValue(searchTerm)
                        .into(SearchForm.SEARCH_FIELD)
                        .thenHit(Keys.ENTER)
        );
    }
}
```

In Screenplay, we keep track of locators in light weight page or component objects, like this one:

```java
class SearchForm {
    static Target SEARCH_FIELD = Target.the("search field")
                                       .locatedBy("#searchInput");

}
```

The Screenplay DSL is rich and flexible, and well suited to teams working on large test automation projects with many
team members, and who are reasonably comfortable with Java and design patterns.

### The Action Classes implementation.

A more imperative-style implementation using the Action Classes pattern can be found in the `action-classes` branch. The
glue code in this version looks this this:

```java
    @Given("^(?:.*) is researching things on the internet")
    public void i_am_on_the_Wikipedia_home_page() {
        navigateTo.theHomePage();
    }

    @When("she/he looks up {string}")
    public void i_search_for(String term) {
        searchFor.term(term);
    }

    @Then("she/he should see information about {string}")
    public void all_the_result_titles_should_contain_the_word(String term) {
        assertThat(searchResult.displayed()).contains(term);
    }
```

These classes are declared using the Serenity `@Steps` annotation, shown below:

```java
    @Steps
    NavigateTo navigateTo;

    @Steps
    SearchFor searchFor;

    @Steps
    SearchResult searchResult;
```

The `@Steps`annotation tells Serenity to create a new instance of the class, and inject any other steps or page objects
that this instance might need.

Each action class models a particular facet of user behaviour: navigating to a particular page, performing a search, or
retrieving the results of a search. These classes are designed to be small and self-contained, which makes them more
stable and easier to maintain.

The `NavigateTo` class is an example of a very simple action class. In a larger application, it might have some other
methods related to high level navigation, but in our sample project, it just needs to open the DuckDuckGo home page:

```java
public class NavigateTo {

    WikipediaHomePage homePage;

    @Step("Open the Wikipedia home page")
    public void theHomePage() {
        homePage.open();
    }
}
```

It does this using a standard Serenity Page Object. Page Objects are often very minimal, storing just the URL of the
page itself:

```java
@DefaultUrl("https://wikipedia.org")
public class WikipediaHomePage extends PageObject {}
```

The second class, `SearchFor`, is an interaction class. It needs to interact with the web page, and to enable this, we
make the class extend the Serenity `UIInteractionSteps`. This gives the class full access to the powerful Serenity
WebDriver API, including the `$()` method used below, which locates a web element using a `By` locator or an XPath or
CSS expression:

```java
public class SearchFor extends UIInteractionSteps {

    @Step("Search for term {0}")
    public void term(String term) {
        $(SearchForm.SEARCH_FIELD).clear();
        $(SearchForm.SEARCH_FIELD).sendKeys(term, Keys.ENTER);
    }
}
```

The `SearchForm` class is typical of a light-weight Page Object: it is responsible uniquely for locating elements on the
page, and it does this by defining locators or occasionally by resolving web elements dynamically.

```java
class SearchForm {
    static By SEARCH_FIELD = By.cssSelector("#searchInput");
}
```

The last step library class used in the step definition code is the `SearchResult` class. The job of this class is to
query the web page, and retrieve a list of search results that we can use in the AssertJ assertion at the end of the
test. This class also extends `UIInteractionSteps` and

```java
public class SearchResult extends UIInteractionSteps {
    public String displayed() {
        return find(WikipediaArticle.HEADING).getText();
    }
}
```

The `WikipediaArticle` class is a lean Page Object that locates the article titles on the results page:

```java
public class WikipediaArticle {
    public static final By HEADING =  By.id("firstHeading");
}
```

The main advantage of the approach used in this example is not in the lines of code written, although Serenity does
reduce a lot of the boilerplate code that you would normally need to write in a web test. The real advantage is in the
use of many small, stable classes, each of which focuses on a single job. This application of the _Single Responsibility
Principle_ goes a long way to making the test code more stable, easier to understand, and easier to maintain.

## Executing the tests

To run the sample project, you can either just run the `CucumberTestSuite` test runner class, or run either `mvn verify`
from the command line.

```json
$ mvn clean verify
```

The test results will be recorded in the `target/site/serenity` directory.

## Generating the reports

Since the Serenity reports contain aggregate information about all of the tests, they are not generated after each
individual test (as this would be extremenly inefficient). Rather, The Full Serenity reports are generated by
the `serenity-maven-plugin`. You can trigger this by running `mvn serenity:aggregate` from the command line or from your
IDE.

They reports are also integrated into the Maven build process: the following code in the `pom.xml` file causes the
reports to be generated automatically once all the tests have completed when you run `mvn verify`

```
             <plugin>
                <groupId>net.serenity-bdd.maven.plugins</groupId>
                <artifactId>serenity-maven-plugin</artifactId>
                <version>${serenity.maven.version}</version>
                <configuration>
                    <tags>${tags}</tags>
                </configuration>
                <executions>
                    <execution>
                        <id>serenity-reports</id>
                        <phase>post-integration-test</phase>
                        <goals>
                            <goal>aggregate</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```
### Environment-specific configurations

We can also configure environment-specific properties and options, so that the tests can be run in different
environments. Here, we configure three environments, __dev__, _staging_ and _prod_, with different starting URLs for
each:

```json
environments {
  default {
    webdriver.base.url = "https://duckduckgo.com"
  }
  dev {
    webdriver.base.url = "https://duckduckgo.com/dev"
  }
  staging {
    webdriver.base.url = "https://duckduckgo.com/staging"
  }
  prod {
    webdriver.base.url = "https://duckduckgo.com/prod"
  }
}
```

You use the `environment` system property to determine which environment to run against. For example to run the tests in
the staging environment, you could run:

```json
$ mvn clean verify -Denvironment=staging
```


## Brief changes list:
1) Clean up project (by YAGNI principle)
2) Updated `pom.xml` with additional plugins for assertions, logging, newer versions (only stable, and not-conflicted ones) - for maximal safety against known vulnerabilities
3) Redesigned project structure - added packages according to instructions.   
4) Testcases in `search.feature` file extended by scenario with tables - to show tech skills.
   (initial tests are present in `initial_post_product.feature` file in redesigned form)
5) added feature `failed.feature` file with "in purpose failed" tests - for more realistic picture.

### To see Serenity test run report in Gitlab CI you can follow by link: 
### https://xt4k.gitlab.io/-/{repo-name}/-/jobs/{build-id}/artifacts/target/site/serenity/index.html
##### where provided `build-id` can be taken from Gitlab CI https://gitlab.com/xt4k/Deu_example/-/jobs
https://xt4k.gitlab.io/-/Deu_example/-/jobs/4801157680/artifacts/target/site/serenity/index.html

## Pipeline job build log is available by link https://gitlab.com/xt4k/-/jobs/{build-id}
https://gitlab.com/xt4k/Deu_example/-/jobs/4801157680