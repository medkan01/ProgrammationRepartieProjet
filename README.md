# Projet de Programmation Répartie
### DUT Informatique - Metz

## I. Introduction
Dans le cadre de notre DUT Informatique, nous devons réaliser plusieurs projets dans le but de comprendre les notions vues en cours ainsi que d'acquérir de nouvelles compétences. Ces compétences peuvent être pour la programmation de manière générale, mais aussi pour la gestion d'un projet ou le travail en équipe lorsque ces projets sont à réaliser en groupe. Cela nous permet de nous préparer à travailler en entreprise et de pouvoir s'adapter à tout type de projet.
Ce projet de Programmation Répartie consiste à appliquer les notions de réseaux vues en cours avec l'utilisation de Java.

## II. Sujet du projet
Nous devons réaliser une application contenant, pour l'instant, trois jeux. Dans notre cas, les jeux seront les suivants.

### 1. Le jeu du Pendu
Le serveur pense à un mot qui fait partie de son dictionnaire et dessine une rangée de tirets, chacune correspondant à une lettre. Le client propose une lettre.
Si celle-ci existe dans le mot, le serveur enverra la configuration de départ dans laquelle certains tirets sont remplacés par la lettre proposée, autant de fois que celle-ci figure dans le mot.
Le client gagne la partie quand il aura découvert le mot, ou il perd la partie au bout d’un certain nombre d'essais.

### 2. Le jeu des allumettes
Il existe plusieurs versions de ce jeu. Une version basique consiste à mettre un tas d’allumettes sur la table. Ce tas contient un nombre impair d’allumettes. Chaque joueur à son tour prend une ou deux allumettes. Le jeu s’arrête quand le tas est vide.
Le gagnant est celui qui a pris un nombre impair d’allumettes. Le joueur qui commence peut être choisi de façon aléatoire. Dans ce jeu le client joue avec le serveur.

### 3. Le jeu du Tic-Tac-Toe
Le Tic-tac-toe se joue sur une grille carrée de 3 × 3 cases. Deux joueurs s’affrontent. Ils doivent remplir chacun leur tour une case de la grille avec le symbole qui leur est attribué : O ou X. 
Le gagnant est celui qui arrive à aligner trois symboles identiques, horizontalement, verticalement ou en diagonale.

## III. Structure du projet
Le projet doit être réalisé dans le modèle MVC. De plus, il faudra être attentif à la structure et l'arborescence du projet en raison de l'utilisation d'interfaces RMI.

### 1. Modèle MVC
Le modèle MVC est réparti en trois parties distinctes.

#### a. La partie Modèle
Il s’agit de La partie Jeux qui contient les classes nécessaires pour la réalisation de chaque jeu.

#### b. La partie Vue
Cette partie contiendra une interface graphique d’accueil pour le jeu en réseau proposant au joueur de choisir son jeu parmi les jeux proposés. Elle contiendra aussi une interface graphique pour chaque jeu choisi. Ces interfaces peuvent être implémentées de façon indépendante dans un premier temps.

#### c. La partie Contrôle
Cette partie décrira le lien entre la vue et le traitement proprement dit.

### 2. Interfaces RMI
Les interfaces RMI vont permettre au client de communiquer avec le serveur et d'utiliser les classes que le serveur propose.
Il y a donc deux parties distinctes, le serveur et le client.

#### a. Le Serveur
Le serveur va s'occuper d'instancier la classe de chaque jeux et de définir une route d'accès à ces classes. Cela va permettre au client d'accéder et d'utiliser les fonctions associées. Evidemment, ces classes doivent être déclarées dans les interfaces RMI.
Le serveur contiendra alors :
* Un package **modele** dans lequel on aura accès au packages :
    + **interfaceRMI** qui contiendra les interfaces RMI
    + **pojo** qui contiendra les classes avec le corps des fonctions déclarées dans les interfaces RMI
* Un package

#### b. Le Client
Le client 

## IV. Organisation des tâches
Ce projet est à réaliser à trois, par conséquent, nous avons décidé dans un premier temps d'écrire les interfaces RMI de chaque jeux.
Comme nous allons programmer un jeu chacun. Chacun écrira l'interface du jeu qui lui est attribué. Cela comprend la partie Modèle, Vue et Contrôle du design pattern vue au paragraphe 3. Pour l'écriture du programmes des jeux, elle se fera comme suit :
* Mehdi : Jeu du Pendu
* Luc : Jeu des allumettes
* Esther : Jeu du Tic-Tac-Toe

Une fois que le programme de chaque jeux est écrit, On pourra commencer à réaliser la partie Serveur et Client. Ces parties seront réalisées tous ensemble afin d'éviter un quelconque problème d'intégration des jeux.