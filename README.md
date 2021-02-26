# Projet de Programmation Répartie
### DUT Informatique - Metz

## 1. Introduction
Dans le cadre de notre DUT Informatique, nous devons réaliser plusieurs projets dans le but de comprendre les notions vues en cours ainsi que d'acquérir de nouvelles compétences. Ces compétences peuvent être pour la programmation de manière générale, mais aussi pour la gestion d'un projet ou le travail en équipe lorsque ces projets sont à réaliser en groupe. Cela nous permet de nous préparer à travailler en entreprise et de pouvoir s'adapter à tout type de projet.
Ce projet de Programmation Répartie consiste à appliquer les notions de réseaux vues en cours avec l'utilisation de Java.

## 2. Sujet du projet
Nous devons réaliser une application contenant, pour l'instant, trois jeux. Dans notre cas, les jeux seront les suivants.

### a. Le jeu du Pendu
Le serveur pense à un mot qui fait partie de son dictionnaire et dessine une rangée de tirets, chacune correspondant à une lettre. Le client propose une lettre.
Si celle-ci existe dans le mot, le serveur enverra la configuration de départ dans laquelle certains tirets sont remplacés par la lettre proposée, autant de fois que celle-ci figure dans le mot.
Le client gagne la partie quand il aura découvert le mot, ou il perd la partie au bout d’un certain nombre d'essais.

### b. Le jeu des allumettes
Il existe plusieurs versions de ce jeu. Une version basique consiste à mettre un tas d’allumettes sur la table. Ce tas contient un nombre impair d’allumettes. Chaque joueur à son tour prend une ou deux allumettes. Le jeu s’arrête quand le tas est vide.
Le gagnant est celui qui a pris un nombre impair d’allumettes. Le joueur qui commence peut être choisi de façon aléatoire. Dans ce jeu le client joue avec le serveur.

### c. Le jeu du Tic-Tac-Toe
Le Tic-tac-toe se joue sur une grille carrée de 3 × 3 cases. Deux joueurs s’affrontent. Ils doivent remplir chacun leur tour une case de la grille avec le symbole qui leur est attribué : O ou X. 
Le gagnant est celui qui arrive à aligner trois symboles identiques, horizontalement, verticalement ou en diagonale.

## 3. Structure du projet
Le projet doit être réalisé dans le modèle MVC.

#### a. La partie Modèle
Il s’agit de La partie Jeux qui contient les classes nécessaires pour la réalisation de chaque jeu.

#### b. La partie Vue
Cette partie contiendra une interface graphique d’accueil pour le jeu en réseau proposant au joueur de choisir son jeu parmi les jeux proposés. Elle contiendra aussi une interface graphique pour chaque jeu choisi. Ces interfaces peuvent être implémentées de façon indépendante dans un premier temps.

#### c. La partie Contrôle
Cette partie décrira le lien entre la vue et le traitement proprement dit.

## 4. Organisation des tâches
Ce projet est à réaliser à trois, par conséquent, nous avons décidé dans un premier temps de programmer un jeu chacun. Cela comprend la partie Modèle, Vue et Contrôle du design pattern du paragraphe 3.
Le jeu du Pendu sera réalisé par Mehdi.
Le jeu des allumettes sera réalisé par Luc.
Le jeu du Tic-Tac-Toe sera réalisé par Esther.

