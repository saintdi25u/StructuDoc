# Collecte de statistiques et de recommandation de musique

Ce projet a été réalisé par MANUELLI Théo et SAINT-DIZIER Corentin.

## 1 - Conception du schéma de données

Tout d'abord,  notre base de données est nommé de la façon suivante : `SD2023_SAINT-DIZIER_MANUELLI` conformément à ce qui est demandé dans le sujet. 

### 1.1 - Pattern utilisés

#### Patron polymorphique

Nous avons utilisé le patron polymorphique pour stocker les informations sur les tendances globales (ou par pays). En effet, on s'est demander comment on pouvait récupérer les différents classements des catégories (style, artiste...).
Alors, pour éviter de devoir accéder à plusieurs collections pour récupérer les différents classement, et de gérer les structures des différentes collections, on a décidé d'avoir une seule collection qui va stocker les différents classement (voir image ci-dessous).

![](assets/1.png)


### 1.2 - Performances 

Concernant le performance de notre base de données, nous avons fait en sorte d'effectuer le moins de requête/jointure possible pour obtenir une/plusieurs information(s). 

Alors, cela engendre une plus grande complexité de programmation, car par exemple, pour obtenir les informations concernant les musiques et albums simmilaire entre deux artistes, il y a une complexité importante si ces informations ne sont pas contenues dans la base de données.

Mais, dès lors que les données sont deja présentes au sein de la base de données, ce sont uniquement des requêtes simple qui sont effectuées. Elles sont par conséquent plus performantes et prennent moins d'espaces disque.

### 1.3 - Schéma graphique de l'organisation de la base de données


## 2 - Interface homme-machine

### 2.1 - Installation
Voici les étapes à suivre pour installer l'application sur votre ordinateur :

* Récupérez le dossier compressé nommé "APP_LastFM_Manuelli_Saint-Dizier" et placez-le à l'endroit souhaité sur votre ordinateur. Par défaut, placez-le sur le "Bureau".
* Décompressez le dossier en effectuant les actions suivantes : clic droit -> Extraire tout -> Extraire.
* Ouvrez le dossier décompressé "APP_LastFM_Manuelli_Saint-Dizier". 
* Allez dans le dossier "bindist" puis "bin".
* Cliquez sur la barre de navigation et tapez le mot "cmd". Voici un exemple :
![[2.1Installation1.png]]


![[2.1Installation2.png]]

* Une fois l'invite de commandes ouverte, tapez la commande "applastfm" puis appuyez sur "Entrée". Voici un exemple :
![[2.1Installation3.png]]
Une fois ces étapes réalisées, l'application s'ouvre, vous n'avez plus qu'à l'utiliser.
### 2.2 - Choix IHM
L'objectif de ce projet était de réaliser une application en java permettant de collecter des données concernant le milieu musicale afin d'incrémenter une base de données mangoDB et de les communiquer à l'utilisateur. 
Pour réaliser cette application nous avons donc décider de réaliser une interface graphique permettant ainsi de faciliter et améliorer l'experience utilisateur. 
Voici un apercu de l'interface homme machine :

![[2.2Choix1.png]]

Sur cette interface vous pouvez observer plusieurs fonctionalitées mises à disposition de l'utilisateur. Nous avons décidé de placer chaques fonctionnalitée dans une case permettant ainsi à l'utilisateur de bien disocier chaqu'une d'entre elles. 
Comme vous pouvez le constater

