# Kalaha

Please see [README.md](README.md) for game rules.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- [JDK 1.8+](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html) 
- [Maven 3+](https://maven.apache.org/install.html)

### Execution
Clone the git/unzip to a local directory. <br/>
Navigate to the project folder:

```
cd ~/gokhan-daglioglu
```
Run the following command:
```
mvn spring-boot:run
```
And you should see a similar output to the following where no errors reported.
```

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.6.3)

2022-02-08 11:02:41.583  INFO 11566 --- [           main] com.kalaha.application.GameApplication   : Starting GameApplication using Java 17.0.1 on gokhans-mbp.home with PID 11566 (/Users/gokhandaglioglu/SoftwareDevelopment/gokhan-daglioglu/target/classes started by gokhandaglioglu in /Users/gokhandaglioglu/SoftwareDevelopment/gokhan-daglioglu)
2022-02-08 11:02:41.586  INFO 11566 --- [           main] com.kalaha.application.GameApplication   : No active profile set, falling back to default profiles: default
2022-02-08 11:02:42.340  INFO 11566 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2022-02-08 11:02:42.349  INFO 11566 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2022-02-08 11:02:42.349  INFO 11566 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.56]
2022-02-08 11:02:42.456  INFO 11566 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2022-02-08 11:02:42.456  INFO 11566 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 805 ms
2022-02-08 11:02:43.451  INFO 11566 --- [           main] c.v.f.s.VaadinServletContextInitializer  : Search for subclasses and classes with annotations took 0 seconds
2022-02-08 11:02:43.452  INFO 11566 --- [           main] c.v.f.s.VaadinServletContextInitializer  : Due to slow search it is recommended to use the whitelisted-packages feature to make scanning faster.

See the whitelisted-packages section in the docs at https://vaadin.com/docs/latest/flow/integrations/spring/configuration#special-configuration-parameters
2022-02-08 11:02:43.455  INFO 11566 --- [           main] c.v.f.server.startup.DevModeInitializer  : Starting dev-mode updaters in /Users/gokhandaglioglu/SoftwareDevelopment/gokhan-daglioglu folder.
2022-02-08 11:02:43.487  INFO 11566 --- [           main] dev-updater                              : Visited 93 classes. Took 27 ms.
2022-02-08 11:02:43.567  INFO 11566 --- [           main] dev-updater                              : Skipping `npm install` because the frontend packages are already installed in the folder '/Users/gokhandaglioglu/SoftwareDevelopment/gokhan-daglioglu/node_modules' and the hash in the file '/Users/gokhandaglioglu/SoftwareDevelopment/gokhan-daglioglu/node_modules/.vaadin/vaadin.json' is the same as in 'package.json'
2022-02-08 11:02:43.576  INFO 11566 --- [           main] dev-updater                              : Copying frontend resources from jar files ...
2022-02-08 11:02:43.625  INFO 11566 --- [           main] dev-updater                              : Visited 13 resources. Took 49 ms.
2022-02-08 11:02:43.668  INFO 11566 --- [           main] dev-updater                              : Visited 93 classes. Took 7 ms.
Vaadin application has been deployed and started to the context path "/".
2022-02-08 11:02:43.727  INFO 11566 --- [onPool-worker-1] dev-updater                              : Skipping `npm install` because the frontend packages are already installed in the folder '/Users/gokhandaglioglu/SoftwareDevelopment/gokhan-daglioglu/node_modules' and the hash in the file '/Users/gokhandaglioglu/SoftwareDevelopment/gokhan-daglioglu/node_modules/.vaadin/vaadin.json' is the same as in 'package.json'
2022-02-08 11:02:43.733  INFO 11566 --- [onPool-worker-1] dev-updater                              : Copying frontend resources from jar files ...
2022-02-08 11:02:43.741  INFO 11566 --- [onPool-worker-1] dev-updater                              : Visited 13 resources. Took 8 ms.
2022-02-08 11:02:43.766  INFO 11566 --- [onPool-worker-2] dev-webpack                              : Starting webpack-dev-server
2022-02-08 11:02:44.074  INFO 11566 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2022-02-08 11:02:44.089  INFO 11566 --- [           main] com.kalaha.application.GameApplication   : Started GameApplication in 2.843 seconds (JVM running for 3.137)

------------------ Starting Frontend compilation. ------------------
2022-02-08 11:02:45.003  INFO 11566 --- [onPool-worker-2] dev-webpack                              : Running webpack to compile frontend resources. This may take a moment, please stand by...
2022-02-08 11:02:49.013  INFO 11566 --- [onPool-worker-2] dev-webpack                              : Started webpack-dev-server. Time: 4013ms

```
The `Kalaha Game` is now ready to play!

In your web browser of choice, navigate to `http://localhost:8080/` and ENJOY!

To stop the execution you can use `CTRL+C` in your terminal window.

## Screenshots

Please see [SCREENSHOTS.md](SCREENSHOTS.md) for screenshots.

## Built With

* [Spring Boot](http://spring.io/projects/spring-boot) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Vaadin](https://vaadin.com/) - Used to generate UI components

## Known issues and Improvements

### Issues

* N/A

## Improvements

* Persist games into a repository.
  * Support multiple games to be played at the same time using game id.
  * Allow `2` players to play against one another

* Replay system
  * This should be possible if turn data is persisted.
  
* We could increase the competitiveness of the game by:
  * Adding a timer to play
  * Designing a system user to play against a real user
  
* General UI improvements

## Author

**Gokhan Daglioglu**

## License

This project is free to use, any suggestions and feedback is very welcome!