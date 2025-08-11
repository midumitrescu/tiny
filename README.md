# tiny-url shortener

## How to run the project

```bash
git clone git@github.com:midumitrescu/tiny.git .
mvn clean verify spring-boot:run
```


## API Documentation
You can find a [swagger-ui here](http://localhost:8080/swagger-ui/index.html#/)

or copy
```
http://localhost:8080/swagger-ui/index.html#/
```
in the browser after application has started. 

## What is this project about?

This project solves the first part of this [URL Shortener Kata](https://ccd-school.de/coding-dojo/architecture-katas/url-shortener/).
It should be an easy demo project that showcases some Clean-Code skills.

While the link is in German, [ChatGPT]() translated this to: 

```
Architecture Kata “URL Shortener”

Develop an internet service for shortening URLs, like bit.ly, TinyURL, or Google URL Shortener.
Users can enter a URL on a page and receive a shortened version in return. The shortened version of a URL should always be the same for each shortening request.
When this shortened URL is used as an address in a browser, the internet service should redirect and display the document of the original (unshortened) URL.

A statistic is kept for each shortened URL:
How many times has the URL been shortened?
How many times has the shortened URL been accessed?

The statistics should be visible to everyone. They should also be automatically displayed after each shortening.
```

## Clean code and TDD

I am of the opinion that:

1. Writing software without tests returns no guarantee of the correctness of the 
implementation
2. Clean Code is the effect of continuous refactoring in a project. 
3. Continuous refactoring, especially in the real-world context of always changing
requirements, is a nightmare without enough tests. The tests should always give
the security to the company and the programmer that both old and new features work
exactly as required.

This project was developed using TDD. The tests were written before the
actual code. This way tests are not something cumbersome one has to add 
at the end of development, but a supporting tool for the whole development process.
The programmer can refactor as much and as often he desires, until results are
satisfactory, without fearing any issues with regression.

I see TDD as necessary tool for being able to reach Clean Code.