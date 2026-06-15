# Application de Gestion de Commandes

Projet réalisé dans le cadre du module **Services WEB**.

## Technologies utilisées

* Java 17
* Spring Boot
* Spring Data JPA
* Spring Security
* JWT
* MySQL
* Swagger / OpenAPI
* Maven

---

## Architecture

```text
com.polytech.commandes
│
├── entity
├── repository
├── service
├── controller
├── dto
├── filter
├── exception
├── config
└── init
```

---

## Fonctionnalités

### Produits

* Création
* Modification
* Suppression
* Consultation
* Recherche multicritères
* Pagination
* Tri dynamique

### Clients

* CRUD complet

### Commandes

* Création
* Validation
* Annulation
* Consultation
* Recherche par client
* Recherche par période
* Chiffre d'affaires global
* Total des commandes par client

### Sécurité

* Inscription utilisateur
* Connexion utilisateur
* Authentification JWT
* Gestion des rôles ADMIN / USER
* Protection des routes via Spring Security

---

## Profils Spring

### Développement

```bash
SPRING_PROFILES_ACTIVE=dev
```

### Test

```bash
SPRING_PROFILES_ACTIVE=test
```

### Production

```bash
SPRING_PROFILES_ACTIVE=prod
```

---

## Variables d'environnement

```bash
SPRING_PROFILES_ACTIVE
DB_URL
DB_USERNAME
DB_PASSWORD
SERVER_PORT
JWT_SECRET
JWT_EXPIRATION
```

---

## Installation et Lancement

### Création de la base de données

```sql
CREATE DATABASE gestion_commande;
```

### Compilation du projet

```bash
mvn clean package
```

ou

```bash
./mvnw clean package
```

---

### Exécution (profil dev par défaut)

Se déplacer dans le dossier `target` :

```bash
cd target
```

Puis exécuter :

```bash
java -jar commandes-0.0.1-SNAPSHOT.jar
```

---

### Exécution avec le profil dev (PowerShell)

```powershell
$env:SPRING_PROFILES_ACTIVE="dev"
$env:DB_URL="jdbc:mysql://localhost:3306/gestion_commande"
$env:DB_USERNAME="root"
$env:DB_PASSWORD=""
$env:SERVER_PORT="8080"

java -jar commandes-0.0.1-SNAPSHOT.jar
```

---

### Exécution avec le profil prod (PowerShell)

```powershell
$env:SPRING_PROFILES_ACTIVE="prod"
$env:DB_URL="jdbc:mysql://localhost:3306/gestion_commande"
$env:DB_USERNAME="root"
$env:SERVER_PORT="8080"

java -jar commandes-0.0.1-SNAPSHOT.jar
```

---

## Authentification

### Compte administrateur

```text
username : admin
password : admin123
```

Ce compte possède le rôle **ROLE_ADMIN** et permet de tester toutes les routes protégées.

---

## Documentation Swagger

Accès à Swagger :

```text
http://localhost:8080/swagger-ui/index.html
```

### Utilisation du JWT dans Swagger

1. Créer un utilisateur avec `/auth/register` ou utiliser le compte administrateur.
2. Se connecter avec `/auth/login`.
3. Copier le token retourné.
4. Cliquer sur le bouton **Authorize** en haut à droite de Swagger.
5. Coller le token
6. Cliquer sur **Authorize** puis **Close**.
7. Les routes protégées deviennent accessibles.

---

## Exemples d'API

### Inscription

**POST** `/auth/register`

```json
{
  "username": "user1",
  "email": "user1@test.com",
  "password": "12345"
}
```

### Connexion

**POST** `/auth/login`

```json
{
  "username": "user1",
  "password": "12345"
}
```

### Création d'un produit

**POST** `/api/produits`

```json
{
  "nom": "PC Portable",
  "prix": 500000,
  "stock": 10
}
```

### Création d'un client

**POST** `/api/clients`

```json
{
  "nom": "Rakoto",
  "email": "rakoto@test.com",
  "telephone": "0340000000"
}
```

### Création d'une commande

**POST** `/api/commandes`

```json
{
  "clientId": 1,
  "lignes": [
    {
      "produitId": 1,
      "quantite": 2,
      "prixUnitaire": 0
    }
  ]
}
```

### Validation d'une commande

**PUT** `/api/commandes/{id}/validate`

### Annulation d'une commande

**PUT** `/api/commandes/{id}/cancel`

---

## Auteur

**ANDRIAMIADANARIVO Ismael Dini**

Master 2 Génie Logiciel (M2GL)
Groupe ISI
