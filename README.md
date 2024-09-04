# EcoMove
# Application Console de Gestion des Partenariats

## Présentation

Ce projet est une application console développée en Java pour la gestion des partenaires, contrats, offres promotionnelles, et billets. Il utilise PostgreSQL pour la persistance des données et JDBC pour les opérations sur la base de données.

## Fonctionnalités Principales

- **Gestion des Partenaires**
    - Création, modification et suppression de partenaires
    - Gestion des informations : ID, nom de la compagnie, contact, type de transport, zone géographique, conditions spéciales, statut, date de création

- **Gestion des Contrats**
    - Création et gestion des contrats avec les partenaires
    - Informations : ID, dates de début et de fin, tarif spécial, conditions, renouvelable, statut

- **Gestion des Offres Promotionnelles**
    - Création et gestion des offres promotionnelles
    - Détails : ID, nom, description, dates, type de réduction, valeur, conditions, statut

- **Suivi des Billets**
    - Suivi des billets émis
    - Détails : ID, type de transport, prix d'achat, prix de vente, date de vente, statut
    - Génération de rapports financiers

## Technologies

- **Langage** : Java
- **Base de Données** : PostgreSQL
- **API** : JDBC
- **Modélisation** : UML

## Installation

1. **Cloner le Dépôt**

    ```bash
    https://github.com/nabilettihadi/EcoMove.git
    cd EcoMove
    ```

2. **Configurer la Base de Données**
    - Exécutez `schema.sql` pour initialiser la base de données.

3. **Compiler le Code**

    ```bash
    javac -d bin src/main/java/**/*.java
    ```

4. **Lancer l'Application**

    ```bash
    java -cp bin main.java.eco.views.BilletView
    ```

## Structure du Répertoire

- **`src/main/java`** : Code source Java
    - **`views`** : Interfaces utilisateur
    - **`models`** : Modèles de données
    - **`services`** : Services de gestion
    - **`db`** : Accès à la base de données
- **`schema.sql`** : Script SQL pour la base de données
- **`README.md`** : Documentation du projet

## Critères de Qualité

- **Principes SOLID** : Conception modulaire et évolutive
- **Design Pattern Singleton** : Utilisé pour la gestion des connexions
- **Validation des Données** : Vérification des entrées
- **Conventions de Nommage** : Conformité aux standards

## Échéancier et Livrables

- **Début du Projet** : 29/08/2024
- **Date Limite** : 04/09/2024

**Livrables** :
- Code source sur GitHub
- Diagramme de classes et story map
- Script SQL et code Java
- Interface console fonctionnelle
- Documentation complète