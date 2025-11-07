# A25-GLO-4003 Equipe 10

---
# Conventions de Programmation Java et de Git

Ce document définit les conventions que notre équipe suit pour le **développement Java** et la **stratégie de branches Git**.  
L’objectif est d’assurer **la cohérence, la lisibilité et la maintenabilité** du code.

___Ce document n'est pas final et chaque sujet peut être mis à jour pendant la rencontre de 10 septembre.___

## ☕ Conventions Java

### 1. Structure du projet
- Suivre les principes de l'architecture propre/hexagonale (Domaine, Application, Infrastructure, API).
- Garder le **domaine indépendant des frameworks** et toutes bibliothèques technologiques externes.

### 2. Conventions de nommage
- Java
  - **Classes & Interfaces :** `PascalCase` (ex. : `ContactService`, `UserRepository`).
  - **Méthodes & Variables :** `camelCase` (ex. : `findContactById`, `contactName`).
  - **Constantes :** `UPPER_CASE_AVEC_UNDERSCORES` (ex. : `MAX_RETRY_COUNT`).
  - **Packages :** minuscules avec points, même pour les mots collés (ex. : `ca.ulaval.glo4003.ws.application.trottiservice`).

Architecture :
- **Services applicatifs** → Le terme`Application` doit être explicitement mentionné (ex. : `ContactApplicationService`).
- **Repositories** → 
  - Les methods CRUD doivent être nommées `save`, `find`, `remove`, `update`, etc.
  - Pas besoin de mentionner l'objet géré dans le nom de la méthode (ex. : `findById` au lieu de `findContactById` dans `ContactRepository`).

- **DTOs** → Suffixe `DTO` (ex. : `ContactDTO`).
- **Exceptions personnalisées** → Suffixe `Exception` (ex. : `ContactNotFoundException`).
- **Tests unitaires** → Suffixe `Test` (ex. : `ContactServiceTest`).
- **Tests d'intégration** → Suffixe `IntegrationTest` (ex. : `ContactServiceIntegrationTest`).
- **Tests de bout en bout (E2E)** → Suffixe `E2ETest` (ex. : `ContactE2ETest`).

### 3. Style de code
- Utiliser **4 espaces** pour l’indentation.
- Toujours utiliser des **accolades `{}`** même pour les blocs d’une ligne.
- Éviter les nombres magiques → remplacer par des constantes ou enums.
- Éviter les longues lignes → diviser en plusieurs lignes.
  - Pour les contructeurs:
    - ❌ Mauvais exemple:

        ```java
        public ClassA(DependenceA a, DependenceB b, DependenceC c, DependenceD d, DependenceE e) {
            //code 
        }
        ```
    - ✅ Bonne exemple (lisible) :
      ```java
        public ClassA(DependenceA a, 
                      DependenceB b, 
                      DependenceC c, 
                      DependenceD d, 
                      DependenceE e) {
            //code 
        }
        ```
- Aucune ligne vide inutile.
- **Espaces obligatoires autour des mots-clés et avant les accolades :**
- - ❌ `if(condition){`
- - ✅ `if (condition) {`
- - **Espacement vertical pour la lisibilité :**
- Laisser **une ligne vide avant et après** un bloc de contrôle (`if`, `for`, `while`, `switch`) sauf s’il est enchaîné directement (ex. `else if`).
  - ❌ Mauvais exemple (code compact et illisible) :

    ```java
    public void processContacts(List<Contact> contacts) {
        LOGGER.info("Début du traitement");
        if(contacts.isEmpty()){
            pass;
        }
        for(Contact c:contacts){
            LOGGER.debug("{}", c.getName());
        }
        LOGGER.info("Fin");
    }
    ```
  - ✅ Bonne exemple (lisible) :
    ```java
      public void processContacts(List<Contact> contacts) {
        LOGGER.info("Début du traitement");
    
        if (contacts.isEmpty()) {
            LOGGER.debug("Aucun traitement : pas de contact");
        }
    
        for (Contact contact : contacts) {
            LOGGER.debug("Traitement du contact : {}", contact.getName());
        }
    
        LOGGER.info("Fin");
      }
      ```

### 4. Imports et l'ordre des méthodes et attributs dans une classe.
#### - Imports
- Utiliser des imports explicites, pas de wildcard (`*`).
- Pas d’imports inutilisés.
- Pas d'imports statiques (les imports statiques sont acceptables pour les tests).
- Assurez vous d'activer l'option "Optimize Imports" dans votre IDE IntelliJ.

#### - Ordre des visibilités 
Voici l'ordre recommandé pour les membres d'une classe Java :
- Regroupement par type:
`static final` → `static` → `final` → `instance
- Regroupement par visibilité :`
`public` → `protected` → `private`

```java
public class ClassA {
    // 1. Constantes (static final)
    public static final String CONSTANT_NAME = "value";
    protected static final String PROTECTED_CONSTANT = "value";
    private static final String PRIVATE_CONSTANT = "value";

    // 2. Attributs de classe (static)
    public static int staticAttribute;
    protected static int protectedStaticAttribute;
    private static int privateStaticAttribute;

    // 3. Attributs d'instance (non-static)
    public int instanceAttribute;
    protected int protectedInstanceAttribute;
    private int privateInstanceAttribute;

    // 4. Constructeurs
    public ClassA() {
        // Initialisation
    }
    
    public static ClassA createInstance() {
        return new ClassA();
    }

    // 5. Méthodes publiques
    public void publicMethod() {
        // Code
    }

    // 6. Méthodes protégées
    protected void protectedMethod() {
        // Code
    }

    // 7. Méthodes privées
    private void privateMethod() {
        // Code
    }

    // 8. Classes internes
    private class InnerClass {
        // Code
    }
}


```
### 5. Conception orientée objet
- Appliquer les principes **SOLID + T**.
- En général, appliquer les principes vues en cours de GLO-4002 et GLO-4003.
- Utiliser des **Value Objects** pour les concepts du domaine comme Courriel, Mot de Passe, Money, Addresse, etc.
- Utiliser des **DTOs** pour la communication entre couches.
- Respecter le principe d’**Inversion de dépendances** : dépendre d’abstractions, pas d’implémentations.
- Favoriser la **composition** sur l’héritage.
- Utiliser des **design patterns** appropriés (Factory, Singleton, Strategy, etc.)
- Éviter les classes et méthodes trop longues.
- Limiter la complexité cyclomatique des méthodes (max 10).
- Utiliser le patron Builder pour les constructeurs avec plus de 4 paramètres.
- Utiliser des **génériques** pour la réutilisabilité.
- Utiliser des **Optional** pour éviter les nulls si vous allez verifier qq avec `if (obj == null)`.
- Les services du domaine doivent retourner une copie immuable des objets du domaine, et non des références modifiables. 
  - (Commentaire de Felix-Antoine Automne 2024 cours GLO-4002 - Revue du code 2 - Conseil pour le cours GLO-4003)
---

## <img src="https://cdn.simpleicons.org/git/DE4C36" alt="git" width="20"/> Conventions Git
Le nom des branches doit être **clair, descriptif et normalisé**, afin de faciliter la collaboration et la lecture de l’historique Git.
### 1. Types de branches et conventions de nommage
Nous utilisons un flux inspiré de **GitHub Flow** :
- **`main`** → code stable, de la production.
- **`dev`** → branche principale de développement et test.
- `feature/issueNumber-<courte-description>` → branches des fonctionnalités
- `bugfix/issueNumber-<courte-description>` → branches des corrections de bogues depuis `dev`
- `hotfix/issueNumber-<courte-description>` → branches des corrections de bogues depuis le code en production `main`
- `docs/issueNumber-<courte-description>` → branches des documentations
- `config/issueNumber-<courte-description>` → branches des configurations du projet (REST, CI/CD, Docker, etc.)

La courte description doit être en **minuscules**, avec des mots séparés par des **tirets** (`-`). [kebab-case]
- Mauvais exemples de nommage :
  - `Feature/1232-implementer-contact-repository` → Pas de majuscule au début.
  - `bugfix/4567_fix_null_pointer` → Utilisation de `_` au lieu de `-`.
  - `hotfix/89` → Pas de description.
  - `docs/UpdateReadme` → PascalCase au lieu de kebab-case. Pas de code de l'issue.
  - `config/12-add-docker-support-extra-for-production-branch` → Trop long.
  - `feature/1234` → Pas de description.
  - `bugfix/5678-FixIssue` → Majuscule au début et pas de tiret.
  - `hotfix/90_fix-bug` → Utilisation de `_` au lieu de `-`.

### 2. Règles de nommage des commits
- Utiliser l’impératif et le présent.
- Commencer par un verbe d’action suivi d’une description concise.
- Exemples :
  - `Add identity authentication`
  - `Fix null pointer exception in ContactService`
  - `Update README with setup instructions`
- Mauvais examples :
  - `identity authentication` → Quoi avec l'authentification de l'utilisateur ?
  - `Fixing null pointer exception`  → On ne sait pas où.
  - `Updated README with setup instructions` → Pas impératif.

### 3. Bonnes pratiques
- Faire de **petits** commits **ciblés**.
- Écrire des messages de commit **clairs et descriptifs**.
- Faire des **pulls requests** pour la revue de code avant de fusionner dans `dev` ou `main`.
- Toujours **mettre à jour votre branche de fonctionnalité** avec les dernières modifications de `dev`.
- Supprimer les branches locales et distantes après la fusion (si nécessaire).

### 4. Gestion des conflits
- Résoudre les conflits de fusion rapidement.
- Demander de l'aide en cas de conflits majeurs.
- Tester le code après la résolution des conflits pour s’assurer que tout fonctionne correctement.
- Documenter les résolutions de conflits dans le message de commit.
- Éviter les conflits en intégrant fréquemment les changements de `dev` dans les branches de fonctionnalité.

### 5. Pull Request et Revue de code
- Toute fusion dans `dev` ou `main` doit passer par un **pull request**.
- LOC (Lines of Code) maximum par pull request : **300 lignes**.
- Au moins **deux autres membres de l’équipe** doivent approuver le pull request avant la fusion.
- Donner une description claire du pull request, incluant le but et les changements/ajouts majeurs.
- Utiliser des **checklists** pour s’assurer que tous les critères de qualité sont respectés avant la fusion.
- La revue de code doit se concentrer sur la qualité du code, la conformité aux normes, et l’impact sur le projet.
- La revue de code commence quand le CI/CD passe avec succès et aucun conflit n’est détecté.
- Les commentaires doivent être constructifs et respectueux.
- Les auteurs doivent répondre aux commentaires et apporter les modifications nécessaires avant la fusion.
- C'est le rôle des code reviewers de résoudre les commentaires du pull request et non l'auteur.



## ✍️ Signatures des membres de l’équipe

| Nom         | Courriel                 |
|----------------------|--------------------------|
| Ahmed Sami           | <ahmed.sami.1@ulaval.ca> |
| Membre 2             |                          |
| Membre 3             |                          |
| Membre 4             |                          |
| Membre 5             |                          |
| Membre 6             |                          |
| Membre 7             |                          |
| Membre 8             |                          |
| Membre 9             |                          |
