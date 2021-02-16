# JHipster Quarkus demo

## Introduction

### What is JHipster?

https://www.jhipster.tech/

### What is a JHipster Blueprint?

https://www.jhipster.tech/modules/creating-a-blueprint/

### What is Quarkus?

https://quarkus.io/

### What is JHipster Quarkus

* https://github.com/jhipster/generator-jhipster-quarkus
* https://www.npmjs.com/package/generator-jhipster-quarkus


## Demo

### Install JHipster Quarkus

https://www.jhipster.tech/blueprints/quarkus/001_installing_jhipster_quarkus.html

```
$ npm install -g generator-jhipster-quarkus
```

### Creating an Application

https://www.jhipster.tech/blueprints/quarkus/002_creating_an_application.html

```
$ mkdir gronazon-simple
$ cd gronazon-simple
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

```
$ ./mvnw
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
# Because Liquibase is causing reloading issue, we recommend to stop/start the application
$ ./mvnw
```

At that point you should focus on:
* The CRUD forms with validation
* Fake data for development purpose


### Importing a JDL
https://www.jhipster.tech/jdl/


```
$ mkdir gronazon-advanced
$ cd gronazon-advanced
$ jhipster-quarkus import-jdl ../gronazon.jh
```

This time the app relies on MySQL and not h2
```
$ docker-compose -f src/main/docker/mysql up
```

Run the application
```
$ ./mvnw
```

At that point you should focus on:
* The different CRUDs


### Going native

https://quarkus.io/guides/building-native-image

Do not forget to set up GraalVM or Mandrel

```
#Using jabba (https://github.com/shyiko/jabba)
$ jabba install graalvm-ce-java11@20.3.0
$ jabba use graalvm-ce-java11@20.3.0
$ gu install native-image
$ java -version
openjdk version "11.0.9" 2020-10-20
OpenJDK Runtime Environment GraalVM CE 20.3.0 (build 11.0.9+10-jvmci-20.3-b06)
OpenJDK 64-Bit Server VM GraalVM CE 20.3.0 (build 11.0.9+10-jvmci-20.3-b06, mixed mode, sharing)
```

Package the application (for demo sake we skip the tests)
```
$ ./mvnw package -Pnative -DskipTests
[INFO] Scanning for projects...
[INFO]
[INFO] -----------------------< com.gronazon:gronazon >------------------------
[INFO] Building Gronazon 1.0.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- maven-enforcer-plugin:3.0.0-M3:enforce (enforce-versions) @ gronazon ---
[INFO]
[INFO] --- maven-enforcer-plugin:3.0.0-M3:enforce (enforce-dependencyConvergence) @ gronazon ---
[INFO]
[INFO] --- properties-maven-plugin:1.0.0:read-project-properties (default) @ gronazon ---
[INFO]
[INFO] --- maven-resources-plugin:3.1.0:resources (default-resources) @ gronazon ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 25 resources
[INFO]
[INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ gronazon ---
[INFO] Nothing to compile - all classes are up to date
[INFO]
[INFO] --- jacoco-maven-plugin:0.8.5:instrument (instrument-ut) @ gronazon ---
[INFO]
[INFO] --- maven-resources-plugin:3.1.0:testResources (default-testResources) @ gronazon ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 2 resources
[INFO]
[INFO] --- maven-compiler-plugin:3.8.1:testCompile (default-testCompile) @ gronazon ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 29 source files to /Users/daniel/workspace/jhipster/jhipster-quarkus-demo/gronazon-advanced/target/test-classes
[INFO]
[INFO] --- maven-surefire-plugin:3.0.0-M4:test (default-test) @ gronazon ---
[INFO] Tests are skipped.
[INFO]
[INFO] --- jacoco-maven-plugin:0.8.5:restore-instrumented-classes (restore-ut) @ gronazon ---
[INFO]
[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ gronazon ---
[INFO] Building jar: /Users/daniel/workspace/jhipster/jhipster-quarkus-demo/gronazon-advanced/target/gronazon-1.0.0-SNAPSHOT.jar
[INFO]
[INFO] --- quarkus-maven-plugin:1.11.0.Final:build (default) @ gronazon ---
[INFO] [org.jboss.threads] JBoss Threads version 3.2.0.Final
[INFO] [io.quarkus.smallrye.jwt.deployment.SmallRyeJwtProcessor] Adding META-INF/resources/publicKey.pem to native image
[INFO] [org.hibernate.Version] HHH000412: Hibernate ORM core version 5.4.27.Final
[INFO] [io.quarkus.arc.processor.BeanProcessor] Found unrecommended usage of private members (use package-private instead) in application beans:
	- @Inject field com.gronazon.service.mapper.OrderMapperImpl#customerMapper,
	- @Inject field com.gronazon.service.mapper.OrderMapperImpl#productMapper,
	- @Inject field com.gronazon.service.mapper.ProductMapperImpl#categoryMapper
[INFO] [io.quarkus.deployment.pkg.steps.JarResultBuildStep] Building native image source jar: /Users/daniel/workspace/jhipster/jhipster-quarkus-demo/gronazon-advanced/target/gronazon-1.0.0-SNAPSHOT-native-image-source-jar/gronazon-1.0.0-SNAPSHOT-runner.jar
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildStep] Building native image from /Users/daniel/workspace/jhipster/jhipster-quarkus-demo/gronazon-advanced/target/gronazon-1.0.0-SNAPSHOT-native-image-source-jar/gronazon-1.0.0-SNAPSHOT-runner.jar
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildStep] Running Quarkus native-image plugin on GraalVM Version 20.3.0 (Java Version 11.0.9+10-jvmci-20.3-b06)
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildStep] /Users/daniel/.jabba/jdk/graalvm-ce-java11@20.3.0/Contents/Home/bin/native-image -J-Dcom.mysql.cj.disableAbandonedConnectionCleanup=true -J-Djava.util.logging.manager=org.jboss.logmanager.LogManager -J-Dsun.nio.ch.maxUpdateArraySize=100 -J-DCoordinatorEnvironmentBean.transactionStatusManagerEnable=false -J-Dvertx.logger-delegate-factory-class-name=io.quarkus.vertx.core.runtime.VertxLogDelegateFactory -J-Dvertx.disableDnsResolver=true -J-Dio.netty.leakDetection.level=DISABLED -J-Dio.netty.allocator.maxOrder=1 -J-Dcom.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize=true -J-Duser.language=en -J-Dfile.encoding=UTF-8 -H:ResourceConfigurationFiles=resources-config.json --initialize-at-run-time=com.gronazon.security.RandomUtil --initialize-at-build-time= -H:InitialCollectionPolicy=com.oracle.svm.core.genscavenge.CollectionPolicy\$BySpaceAndTime -H:+JNI -H:+AllowFoldMethods -jar gronazon-1.0.0-SNAPSHOT-runner.jar -H:FallbackThreshold=0 -H:+ReportExceptionStackTraces -H:+AddAllCharsets -H:EnableURLProtocols=http,https --enable-all-security-services --no-server -H:-UseServiceLoaderFeature -H:+StackTrace gronazon-1.0.0-SNAPSHOT-runner
[gronazon-1.0.0-SNAPSHOT-runner:16820]    classlist:   4,722.97 ms,  1.50 GB
[gronazon-1.0.0-SNAPSHOT-runner:16820]        (cap):   3,280.98 ms,  1.50 GB
[gronazon-1.0.0-SNAPSHOT-runner:16820]        setup:   4,980.61 ms,  1.50 GB
22:23:36,692 INFO  [org.hib.val.int.uti.Version] HV000001: Hibernate Validator 6.2.0.Final
22:23:36,821 INFO  [org.hib.Version] HHH000412: Hibernate ORM core version 5.4.27.Final
22:23:36,824 INFO  [org.hib.ann.com.Version] HCANN000001: Hibernate Commons Annotations {5.1.2.Final}
22:23:36,849 INFO  [org.hib.dia.Dialect] HHH000400: Using dialect: org.hibernate.dialect.MySQL8Dialect
22:24:09,770 INFO  [org.jbo.threads] JBoss Threads version 3.2.0.Final
[gronazon-1.0.0-SNAPSHOT-runner:16820]     (clinit):   3,600.19 ms,  5.84 GB
[gronazon-1.0.0-SNAPSHOT-runner:16820]   (typeflow):  49,173.93 ms,  5.84 GB
[gronazon-1.0.0-SNAPSHOT-runner:16820]    (objects):  71,822.70 ms,  5.84 GB
[gronazon-1.0.0-SNAPSHOT-runner:16820]   (features):   2,988.04 ms,  5.84 GB
[gronazon-1.0.0-SNAPSHOT-runner:16820]     analysis: 133,972.21 ms,  5.84 GB
[gronazon-1.0.0-SNAPSHOT-runner:16820]     universe:   7,754.12 ms,  5.72 GB
[gronazon-1.0.0-SNAPSHOT-runner:16820]      (parse):  29,850.78 ms,  7.06 GB
[gronazon-1.0.0-SNAPSHOT-runner:16820]     (inline):  59,462.61 ms,  7.81 GB
[gronazon-1.0.0-SNAPSHOT-runner:16820]    (compile): 150,371.41 ms,  8.43 GB
[gronazon-1.0.0-SNAPSHOT-runner:16820]      compile: 258,910.87 ms,  8.43 GB
[gronazon-1.0.0-SNAPSHOT-runner:16820]        image:  37,543.74 ms,  8.22 GB
[gronazon-1.0.0-SNAPSHOT-runner:16820]        write:   4,890.21 ms,  8.36 GB
[gronazon-1.0.0-SNAPSHOT-runner:16820]      [total]: 453,747.63 ms,  8.36 GB
[WARNING] [io.quarkus.deployment.pkg.steps.NativeImageBuildStep] objcopy executable not found in PATH. Debug symbols will not be separated from executable.
[WARNING] [io.quarkus.deployment.pkg.steps.NativeImageBuildStep] That will result in a larger native image with debug symbols embedded in it.
[INFO] [io.quarkus.deployment.QuarkusAugmentor] Quarkus augmentation completed in 459167ms
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  07:47 min
[INFO] Finished at: 2021-02-16T22:31:01+01:00
[INFO] ------------------------------------------------------------------------
```

```

$ ./target/gronazon-1.0.0-SNAPSHOT-runner

        ██╗ ██╗   ██╗ ████████╗ ███████╗   ██████╗ ████████╗ ████████╗ ███████╗
        ██║ ██║   ██║ ╚══██╔══╝ ██╔═══██╗ ██╔════╝ ╚══██╔══╝ ██╔═════╝ ██╔═══██╗
        ██║ ████████║    ██║    ███████╔╝ ╚█████╗     ██║    ██████╗   ███████╔╝
  ██╗   ██║ ██╔═══██║    ██║    ██╔════╝   ╚═══██╗    ██║    ██╔═══╝   ██╔══██║
  ╚██████╔╝ ██║   ██║ ████████╗ ██║       ██████╔╝    ██║    ████████╗ ██║  ╚██╗
   ╚═════╝  ╚═╝   ╚═╝ ╚═══════╝ ╚═╝       ╚═════╝     ╚═╝    ╚═══════╝ ╚═╝   ╚═╝

:: JHipster 🤓  :: https://www.jhipster.tech ::

                                                 Powered by Quarkus 1.11.0.Final
2021-02-16 22:31:52,403 INFO  [io.agr.pool] (main) Datasource '<default>': Initial size smaller than min. Connections will be created when necessary
2021-02-16 22:31:52,525 INFO  [io.quarkus] (main) gronazon 1.0.0-SNAPSHOT native (powered by Quarkus 1.11.0.Final) started in 0.140s. Listening on: http://0.0.0.0:8080
2021-02-16 22:31:52,525 INFO  [io.quarkus] (main) Profile prod activated.
2021-02-16 22:31:52,525 INFO  [io.quarkus] (main) Installed features: [agroal, cdi, hibernate-orm, hibernate-orm-panache, hibernate-validator, jdbc-h2, jdbc-mysql, liquibase, mailer, micrometer, mutiny, narayana-jta, qute, resteasy, resteasy-jsonb, security, servlet, smallrye-context-propagation, smallrye-health, smallrye-jwt, smallrye-openapi, swagger-ui, vertx, vertx-web]
```

Check the consumed memory

```
$ rss gronazon-1.0.0-SNAPSHOT-runner
17548 53M ./target/gronazon-1.0.0-SNAPSHOT-runner
```

## Credits

* Why Gronazon? https://twitter.com/Groland/status/1361359624180559876