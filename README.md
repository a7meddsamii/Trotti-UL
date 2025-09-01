# Telephony WS

## Qu'est-ce que c'est

Un exemple de projet pour créer un API Web Rest en utilisant Java, Jetty et Jersey seulement.

## Contexte

Voici une structure de projet qui n'utilise pas de plateforme d'injection de dépendances et qui explicite
la création de l'application et de ses composantes. C'est une petite application, mais complète, qui sera une base de
projet pour un travail à l'Université. La couverture des tests est minimale, et ce, pour encourager les étudiants à explorer.
L'objectif de l'exercice est de comprendre comment le tout fonctionne pour mieux bâtir une application en utilisant
l'injection de dépendances.

## Comment l'utiliser
* Vous pouvez installer manuellement Java 21 et mettre à jour la variable `JAVA_HOME`. Cependant, nous vous recommandons d'utiliser
  [SDKMAN](https://sdkman.io/) pour gérer Java ainsi que plusieurs outils et *frameworks* reliés à la JVM. Cet outil va vous
  faciliter énormément la gestion des versions de Java et des outils annexes surtout si vous en voulez plusieurs sur votre machine.
* Avec Java 21 et Maven d'installés et de configurés;
  * Pour lancer l'aplication:
    * Dans un terminal, exécutez `start.sh` si vous êtes sur Linux / OSX
    * Dans un terminal, exécutez `start.bat` si vous êtes sur Windows
    * Dans un IDE, exécutez la classe `TelephonyWsMain` en tant que "Java Application"
  * Une fois l'application démarrée, vous trouverez les données aux URLs suivantes:
    * [http://localhost:8080/api/telephony/contacts](http://localhost:8080/api/telephony/contacts)
    * [http://localhost:8080/api/telephony/calllogs](http://localhost:8080/api/telephony/calllogs)
  * Afin de valider votre projet, vous pouvez:
    * Exécuter les tests unitaires avec la commande `mvn test`
    * Exécuter tous les tests (unitaires et d'intégrations) avec `mvn integration-test`
    * Exécuter toutes les vérifications (test, dependency-check, etc...) et produire un *artifact* pour votre application (se trouvant sous
      `target/application.jar`) avec `mvn verify` que vous pouvez invoquer directement avec `java -jar target/application.jar`
