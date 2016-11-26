#Spring Boot template
## English

This is a Spring Boot Customization project, which is aimed to save developer from the Spring Boot neat implications and tricky things, taking place when the project is started.

This is not "all-in-one-box" yet another opinionated java framework, nor the one single silver bullet for killing all the problems, mostly it is one of the many architecture skeleton, which you can use as the working prototype.

The template not intended to use all features and magic annotations of Spring Boot, it's just simplify, and make things clearer and explicit to end developer.

List of features:
+ Flyway migration tool integration not through neat and tricky special annotation, but with use of simple API, provided by Flyway itself
+ Start-up migration scripts for Postgres 9+, H2
+ Two DataSources - Postgres, H2
+ JDBC-style DAO components
+ Native SQL queries for JdctTemplate in .yaml format
+ Interaction with remote RESTful web-services through RestTemplate
+ Scheduling-components
+ @RestController
+ Integration with Logback
+ REST client, based on Spring REST Template and Hystrix Commands

* Common information:
+ Spring Boot version : 1.4.1

## Russian 

Шаблон проекта на Spring Boot, призванный избавить разработчика от большинства проблем и подводных камней,
встающих во время старта проекта.

Шаблон выполняет лишь роль архитектурного каркаса, и не содержит общих компонентов, классов, и прочих средств, 
призванных "облегчить" жизнь разработчику. Его можно использовать лишь как наглядное пособие на этапе старта Spring Boot проекта, 
и ни в коем случае нельзя воспринимать как эталон архитектуры и некую "серебряную пулю" для решения всех проблем, возникающих на жизненном пути проекта.

Шаблон не использует всех программных возможностей Spring Boot'а, т.к автор по тем или иным причинам считает их излишними, "навязывающими свою точку зрения". 

Список возможностей шаблона:
+ Интеграция с Flyway. Через программное API Flyway, а не через неявные механизмы Spring Boot
+ Стартовые скрипты миграций для *Postgres 9+*, *H2*
+ Два DataSource - Postgres, H2
+ JDBC-based репозитории.
+ SQL-запросы в .yaml c возможностью шаблонизации (JTwig)
+ Интеграция *@RestTemplate*
+ Интеграция *@Scheduling*
+ Интеграция *@RestController*
+ Логгирование через Logback
+ REST-клиент, использующий Hystrix и Spring REST Template

Общая информация:
+ Используемая версия Spring Boot : 1.4.1
