# ChaTop API

API REST développée avec Spring Boot pour l'application ChaTop, un portail de location de biens immobiliers (gestion des utilisateurs, des locations, des messages et de l'authentification).

## Prérequis

- Java 17
- Une instance MySQL (locale ou distante)
- Aucune installation de Gradle n'est nécessaire : le wrapper (`gradlew` / `gradlew.bat`) est fourni.

## Installation

1. Cloner le dépôt :
   ```bash
   git clone <url-du-repo>
   cd ChaTop_Api
   ```

2. Créer une base de données MySQL nommée `chatop_db` :
   ```sql
   CREATE DATABASE chatop_db;
   ```

3. Exécuter le script de création du schéma disponible dans le dépôt frontend du projet, sur la base `chatop_db` :
   [`ressources/sql/script.sql`](https://github.com/OpenClassrooms-Student-Center/P3-Full-Stack-portail-locataire/blob/main/ressources/sql/script.sql)
   ```bash
   mysql -u <utilisateur> -p chatop_db < script.sql
   ```

## Configuration

La configuration de l'application (`src/main/resources/application.properties`) repose entièrement sur des **variables d'environnement** : aucune valeur par défaut n'est définie dans le projet.

| Variable       | Description                                          |
|----------------|-------------------------------------------------------|
| `DB_URL`       | URL JDBC de connexion à la base MySQL                 |
| `DB_USER`      | Utilisateur de la base de données                      |
| `DB_PASSWORD`  | Mot de passe de la base de données                     |
| `DB_DRIVER`    | Classe du driver JDBC MySQL                            |
| `JWT_SECRET`   | Clé secrète utilisée pour signer les tokens JWT        |
| `SERVER_PORT`  | Port d'écoute du serveur Spring Boot                   |
| `BASE_URL`     | URL de base de l'API (utilisée pour générer des liens) |

Exemple de valeurs pour un environnement local :

```
DB_URL=jdbc:mysql://localhost:3306/chatop_db
DB_USER=chatop_user
DB_PASSWORD=changeme
DB_DRIVER=com.mysql.cj.jdbc.Driver
JWT_SECRET=une_chaine_secrete_longue_et_aleatoire
SERVER_PORT=3001
BASE_URL=http://localhost:3001
```

Ces variables doivent être définies dans l'environnement avant de lancer l'application :

- **Windows (PowerShell)** :
  ```powershell
  $env:DB_URL="jdbc:mysql://localhost:3306/chatop_db"
  $env:DB_USER="chatop_user"
  $env:DB_PASSWORD="changeme"
  $env:DB_DRIVER="com.mysql.cj.jdbc.Driver"
  $env:JWT_SECRET="une_chaine_secrete_longue_et_aleatoire"
  $env:SERVER_PORT="3001"
  $env:BASE_URL="http://localhost:3001"
  ```

- **Linux / macOS** :
  ```bash
  export DB_URL=jdbc:mysql://localhost:3306/chatop_db
  export DB_USER=chatop_user
  export DB_PASSWORD=changeme
  export DB_DRIVER=com.mysql.cj.jdbc.Driver
  export JWT_SECRET=une_chaine_secrete_longue_et_aleatoire
  export SERVER_PORT=3001
  export BASE_URL=http://localhost:3001
  ```

Il est également possible de configurer ces variables directement dans la configuration de lancement de votre IDE.

### Gestion du schéma par Hibernate

Le projet ne définit pas de valeur pour `spring.jpa.hibernate.ddl-auto` : le schéma doit donc être créé au préalable via le script SQL mentionné ci-dessus. Si vous souhaitez qu'Hibernate synchronise automatiquement le schéma au démarrage (en complément ou à la place du script), définissez la variable d'environnement `SPRING_JPA_HIBERNATE_DDL_AUTO=update` (ou ajoutez `spring.jpa.hibernate.ddl-auto=update` dans `application.properties`).

## Lancement du projet

- Lancer l'application :
  ```bash
  ./gradlew bootRun        # Linux / macOS
  gradlew.bat bootRun      # Windows
  ```

- Construire le projet :
  ```bash
  ./gradlew build
  ```

- Exécuter les tests :
  ```bash
  ./gradlew test
  ```

## Documentation de l'API (Swagger)

Une fois l'application démarrée, la documentation interactive de l'API est disponible aux adresses suivantes (en remplaçant `<SERVER_PORT>` par le port configuré) :

- Swagger UI : `http://localhost:<SERVER_PORT>/swagger-ui.html`
- Spécification OpenAPI (JSON) : `http://localhost:<SERVER_PORT>/v3/api-docs`

## Upload d'images

Les images uploadées (photos des locations) sont stockées dans le dossier `pictures/` à la racine du projet (configurable via `app.upload.dir`). Les limites de taille appliquées sont de 5 Mo par fichier et 6 Mo par requête.
