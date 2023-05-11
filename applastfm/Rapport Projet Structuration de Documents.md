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


### 2.2 - Explication


