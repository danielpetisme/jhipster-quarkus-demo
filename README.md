# JHipster Quarkus demo

## Introduction

### What is JHipster?

https://www.jhipster.tech/

### What is a JHipster Blueprint?

https://www.jhipster.tech/modules/creating-a-blueprint/

### What is Quarkus?

https://quarkus.io/

## Demo

### Install JHipster Quarkus

https://www.jhipster.tech/blueprints/quarkus/001_installing_jhipster_quarkus.html

```
$ npm install -g generator-jhipster-quarkus
```

### Creating an Application

https://www.jhipster.tech/blueprints/quarkus/002_creating_an_application.html

```
$ mkdir gronazon
$ cd gronazon
$ jhipster-quarkus
Local install was preferred but not found.
INFO! Using JHipster version installed globally
INFO! No custom sharedOptions found within blueprint: generator-jhipster-quarkus at /Users/daniel/workspace/jhipster/node_modules/generator-jhipster-quarkus
INFO! No custom commands found within blueprint: generator-jhipster-quarkus at /Users/daniel/workspace/jhipster/node_modules/generator-jhipster-quarkus
INFO! Executing jhipster:app
     info Using blueprint generator-jhipster-quarkus for app subgenerator


        ██╗ ██╗   ██╗ ████████╗ ███████╗   ██████╗ ████████╗ ████████╗ ███████╗
        ██║ ██║   ██║ ╚══██╔══╝ ██╔═══██╗ ██╔════╝ ╚══██╔══╝ ██╔═════╝ ██╔═══██╗
        ██║ ████████║    ██║    ███████╔╝ ╚█████╗     ██║    ██████╗   ███████╔╝
  ██╗   ██║ ██╔═══██║    ██║    ██╔════╝   ╚═══██╗    ██║    ██╔═══╝   ██╔══██║
  ╚██████╔╝ ██║   ██║ ████████╗ ██║       ██████╔╝    ██║    ████████╗ ██║  ╚██╗
   ╚═════╝  ╚═╝   ╚═╝ ╚═══════╝ ╚═╝       ╚═════╝     ╚═╝    ╚═══════╝ ╚═╝   ╚═╝


             ██████╗ ██╗   ██╗ █████╗ ██████╗ ██╗  ██╗██╗   ██╗███████╗
            ██╔═══██╗██║   ██║██╔══██╗██╔══██╗██║ ██╔╝██║   ██║██╔════╝
            ██║   ██║██║   ██║███████║██████╔╝█████╔╝ ██║   ██║███████╗
            ██║▄▄ ██║██║   ██║██╔══██║██╔══██╗██╔═██╗ ██║   ██║╚════██║
            ╚██████╔╝╚██████╔╝██║  ██║██║  ██║██║  ██╗╚██████╔╝███████║
             ╚══▀▀═╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝ ╚══════╝
                            https://www.jhipster.tech - https://quarkus.io

Welcome to JHipster Quarkus v1.0.0
Application files will be generated in folder: /Users/daniel/workspace/jhipster/jhipster-quarkus-demo/gronazon
 _______________________________________________________________________________________________________________

  Documentation for creating an application is at https://github.com/jhipster/jhipster-quarkus
  If you find JHipster useful, consider sponsoring the project at https://opencollective.com/generator-jhipster
 _______________________________________________________________________________________________________________

? What is the base name of your application? gronazon
? What is your default Java package name? com.gronazon
? Which *type* of authentication would you like to use? JWT authentication (stateless, with a token)
? Which *type* of database would you like to use? SQL (H2, MySQL, MariaDB, PostgreSQL, Oracle, MSSQL)
? Which *production* database would you like to use? MySQL
? Which *development* database would you like to use? H2 with disk-based persistence
? Do you want to use the Quarkus cache abstraction? Yes, with the Caffeine implementation (local cache, for a single node)
? Do you want to use Hibernate 2nd level cache? Yes
? Would you like to use Maven or Gradle for building the backend? Maven
? Which *Framework* would you like to use for the client? Angular

? Would you like to use a Bootswatch theme (https://bootswatch.com/)? Default JHipster
? Would you like to enable internationalization support? Yes
? Please choose the native language of the application English
? Please choose additional languages to install French
Installing languages: en, fr
```

At that point your should do a tour of the generated code:
* CodeStyle: `.editorconfig`, `prettierrc` , `.huskyrc`
* Dependencies curated: `pom.xml` + wrapper /`package.json`
* Infrastructure: `src/main/docker`
* Back:
  * Config Management
  * Serialization
  * Security/Authentication/Authorization
  * Mailing with templating and i18n
  * Tests
* Client: Standard JHipster front


### Running the application

In one shell,
```
./mvnw
```

At that point you should focus on:
* The authentication set up by default
* i18n
* The `Administration` menu (User Management, Logs, Swagger-ui, etc.)


### Creating an entity

https://www.jhipster.tech/blueprints/quarkus/003_creating_an_entity.html
```
$ jhipster-quarkus entity Product
Local install was preferred but not found.
INFO! Using JHipster version installed globally
INFO! No custom sharedOptions found within blueprint: generator-jhipster-quarkus at /Users/daniel/workspace/jhipster/jhipster-quarkus-demo/gronazon/node_modules/generator-jhipster-quarkus
INFO! No custom commands found within blueprint: generator-jhipster-quarkus at /Users/daniel/workspace/jhipster/jhipster-quarkus-demo/gronazon/node_modules/generator-jhipster-quarkus
INFO! Executing jhipster:entity Product
     info Using blueprint generator-jhipster-quarkus for entity subgenerator

The entity Product is being created.


Generating field #1

? Do you want to add a field to your entity? Yes
? What is the name of your field? title
? What is the type of your field? String
? Do you want to add validation rules to your field? Yes
? Which validation rules do you want to add? Required

================= Product =================
Fields
title (String) required


Generating field #2

? Do you want to add a field to your entity? Yes
? What is the name of your field? price
? What is the type of your field? Double
? Do you want to add validation rules to your field? No

================= Product =================
Fields
title (String) required
price (Double)


Generating field #3

? Do you want to add a field to your entity? No

================= Product =================
Fields
title (String) required
price (Double)


Generating relationships to other entities

? Do you want to add a relationship to another entity? No

================= Product =================
Fields
title (String) required
price (Double)



? Do you want to use separate repository class for your data access? No, the Entity will be used as an Active Record
? Do you want to use separate service class for your business logic? No, the REST controller should use the active record/repository directly
? Do you want pagination on your entity? Yes, with pagination links

Everything is configured, generating the entity...
```

At that point your should do a tour of the generated code
* Entity/PanacheEntity with the Active Record set up and `@RegisterForReflection`
* Liquibase migration/fake data
* REST Resource with pagination links
* Unit and Integration Tests

Run the application
```
./mvnw
```

At that point you should focus on:
* The CRUD forms with validation
* Fake data for development purpose

### Importing a JDL
https://www.jhipster.tech/jdl/

Download [./gronazon.jh] and place it in the project directory

```
jhipster-quarkus import-jdl ./gronazon.jh
```

Run the application
```
./mvnw
```

### Going native

TODO 

```
./mvnw -Pnative
```