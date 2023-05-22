	# Collecte de statistiques et de recommandation de musique

Ce projet a été réalisé par MANUELLI Théo et SAINT-DIZIER Corentin.

## 1 - Conception du schéma de données

Tout d'abord,  notre base de données est nommé de la façon suivante : `SD2023_SAINT-DIZIER_MANUELLI` conformément à ce qui est demandé dans le sujet. 

Nous avons au total 6 collections : 

- GCSTM_Tracks : Cette collection contient touutes les données relatives à une musique. Voici les informations que nous avons décidé de stocker : le nom, l'artiste, le nombre d'auditeur, le nombre de lecture, les styles de la musique et la date de collection des données afin de pouvoir les mettre à jour toutes les 72h.

- GCSTM_Top10 : Cette collection contient tous les classement pour un pays. C'est à dire qu'à l'interieur de cette collection nous avons : le lieu du classement (un nom de pays ou global pour le monde entier), le top 10 des musiques du lieu, le top 10 des artistes, le nombre d'écoute et le nopmbre d'auditeurs ainsi que la date de collection des données pour pouvoir voir l'évolution dans le temps des classements.

- GCSTM_tag : Cette collection contient toutes les données concernant un style de musique. On retrouvera les informations suivantes : le nom du style, le résumé du style,le nombre d'utilisateur qui écoute ce style de musique, la date de collection des données pour pouvoir mettre à jour et une explication du style.

- GCSTM_artist: Cette collection contient toutes les informations sur un artiste. On pourra retrouver son nom, ces albums, ces musiques, ces styles de musique, son nombre d'écoute total, des artistes similaire et la date de récupération des informations. 

- GCSTM_album : Dans cette collection, on pourra retrouver le nom de l'album, l'artiste, le nombre de musique au sein de l'album, la liste des musiques au sein de l'album, les styles de musiques de cette album, la durée totale de l'album, la date de récupération des informations et le nombre d'écoute total de l'album. 

- GCSTM_opinion : Cette collection rassemble toutes les informations concernant un commentaire publié par un utilisateur. On pourra y retrouver l'auteur du commentaire, le sujet auquel l'utilisateur commente, le nom du sujet, la note attribué et le commentaire ainsi que la date d'insertion du commentaire.




### 1.1 - Pattern utilisés

#### Patron polymorphique

Nous avons utilisé le patron polymorphique pour stocker les informations sur les tendances globales (ou par pays). En effet, on s'est demander comment on pouvait récupérer les différents classements des catégories (style, artiste...).
Alors, pour éviter de devoir accéder à plusieurs collections pour récupérer les différents classement, et de gérer les structures des différentes collections, on a décidé d'avoir une seule collection qui va stocker les différents classement (voir image ci-dessous).

![](assets/1.png)

#### Patron de référence étendue

Nous avons utilisé le patron de référence étendue pour permettre à l'utilisateur d'obtenir des informations plus précise sur ce qu'il recherche.
Par exemple, il a beaucoup aimé l'artiste de la chanson "Boyka".

![](assets/boyka.png)

On observe alors que l'artiste qui a réalisé cette chanson est "Zamdane", alors, si l'utilisateur souhaite avoir plus d'information sur cet artiste, il peut les consulter dans la collection des artistes.

![](assets/zamdane.png)

Cela permet alors d'optimiser les indexes de recherches et d'avoir des requêtes plus simples à écrire et plus rapide à éxécuter.
L'inconvénient de ce patron est que la mise à jour d'un document ne se résume pas à un simple changement de valeur à une clé donnée. 


### 1.2 - Performances 

Concernant le performance de notre base de données, nous avons fait en sorte d'effectuer le moins de requête/jointure possible pour obtenir une/plusieurs information(s). 

Alors, cela engendre une plus grande complexité de programmation, car par exemple, pour obtenir les informations concernant les musiques et albums simmilaire entre deux artistes, il y a une complexité importante si ces informations ne sont pas contenues dans la base de données.

Mais, dès lors que les données sont deja présentes au sein de la base de données, ce sont uniquement des requêtes simple qui sont effectuées. Elles sont par conséquent plus performantes et prennent moins d'espaces disque.

### 1.3 - Schéma graphique de l'organisation de la base de données

JE SAIS PAS SI ON LE FAIT OU PAS.

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
L'objectif de ce projet était de réaliser une application en Java permettant de collecter des données concernant le milieu musical afin d'incrémenter une base de données MongoDB et de les communiquer à l'utilisateur.

Pour réaliser cette application, nous avons donc décidé de réaliser une interface graphique permettant ainsi de faciliter et améliorer l'expérience utilisateur.

Voici un aperçu de l'interface homme-machine :

![[2.2Choix1.png]]

Nous pouvons observer dans un premier temps qu'une information importante est renseignée en haut à droite de l'interface. Cette information est le type de requêtage.

Si la requête est faite sur l'API LastFM, alors cette partie s'affichera comme suit:	![[2.2Choix2.png]]
En revanche, si la requête est faite sur la base de données MongoDB, alors cette partie s'affichera de la manière suivante:
![[2.2Choix3.png]]

Sur cette interface, vous pouvez observer plusieurs fonctionnalités mises à disposition de l'utilisateur. Nous avons décidé de placer chaque fonctionnalité dans une case, permettant ainsi à l'utilisateur de bien distinguer chacune d'entre elles.

Comme vous pouvez le constater, l'interface peut se découper en deux parties :

-   La première permet de choisir quelle fonctionnalité l'utilisateur veut utiliser, ainsi que de renseigner les informations associées.
-   La seconde partie de l'interface sert à afficher les résultats.

Selon la fonctionnalité choisie par l'utilisateur, les résultats ne seront pas affichés de la même façon. Pour valider son choix, l'utilisateur doit appuyer sur le bouton "Valider" en bas de l'interface.

Dans la suite de cette partie, nous allons détailler comment fonctionne l'interface pour chaque fonctionnalité.

### 2.2.1 - Information sur un tag : 

![[2.2.1InfoTag1.png]]
Dans cette partie, l'utilisateur souhaite connaître des informations sur un style musical. La première chose qu'il doit faire est de renseigner le nom du style. Par la suite, il doit choisir entre avoir le résumé ou l'ensemble du contenu concernant ce style. Par défaut, s'il ne coche rien, l'application lui affichera les deux. Voici comment l'application affiche le résultat de cette fonctionnalité :
![[2.2.1InfoTag2.png]]
On peut constater qu'une information sur la date du dernier enregistrement dans la base de données est également renseignée. 

### 2.2.2 - Information sur un album : 

![[2.2.2InfoAlbum1.png]]

Dans cette partie, l'utilisateur souhaite obtenir des informations sur un album. Pour cela, il doit renseigner le nom de l'album ainsi que le nom de l'artiste. Une fois ses choix faits, il appuie sur le bouton "Valider". Le résultat est le suivant :

![[2.2.2InfoAlbum2.png]]

On peut observer que les informations fournies à l'utilisateur sont donc le nom de l'album, le nom de l'artiste, la date de dernière modification dans la base de données, le nombre de morceaux contenus dans l'album, sa durée totale en secondes, son nombre d'écoutes, et enfin la liste des musiques qui composent l'album.

### 2.2.3 - Information sur une musique  : 

![[2.2.3InfoTrack1.png]]
Ici, l'utilisateur souhaite obtenir des informations sur une musique. Pour cela, il doit renseigner le nom de la musique et le nom de l'artiste. Dans cet exemple, nous prendrons donc le titre "Holiday" du groupe Green Day.

Le résultat retourné par l'application est le suivant :

![[2.2.3InfoTrack2.png]]

Les informations affichées à l'utilisateur sont donc le nom de l'artiste, le nom de la musique, le nombre d'écoutes, le nombre d'utilisateurs écoutant la musique, la date de dernière modification dans la base de données, et enfin la liste des styles musicaux auxquels appartient la musique.

### 2.2.4 - Information sur les TOPs 10  : 
Cette fonctionnalité permet de donner des renseignements sur les différents TOP 10. L'utilisateur a plusieurs possibilités :

-   Avoir les informations sur les TOP 10 mondiaux (TOP 10 global).
-   Avoir les informations sur les TOP 10 spécifiquement par pays.

Pour le premier choix, l'utilisateur doit simplement cocher la case correspondante :

![[2.2.4InfoTop1.png]]

Pour le deuxième choix, l'utilisateur peut choisir le nom du pays dans la liste déroulante comme ceci :

![[2.2.4InfoTop2.png]]

Une fois cette partie renseignée, l'utilisateur valide ses choix et l'application retourne :

-   Pour le TOP 10 mondial :

![[2.2.4InfoTOP3.png]]

-   Pour le TOP 10 par pays avec l'exemple des États-Unis :

![[2.2.4InfoTOP4.png]]

L'utilisateur a donc accès au top 10 des musiques, des styles musicaux et des artistes, ainsi qu'à la date de requêtage.

### 2.2.5 - Information sur les musiques et albums similaires  : 
Dans cette fonctionnalité, l'utilisateur souhaite récupérer les musiques et les albums des artistes renseignés, qui ont le même style musical. Cela permet ainsi de récupérer les musiques et albums similaires entre les deux artistes :

![[2.2.5InfoSimilaire1.png]]

Pour ce faire, l'utilisateur peut simplement entrer les noms des deux artistes, puis lancer l'application. Dans le cas de cet exemple, nous souhaitons avoir les informations entre l'artiste D12 et Eminem. Le résultat est le suivant :

![[2.2.5InfoSimilaire2.png]]

L'utilisateur a donc accès à la liste des musiques et des albums similaires entre les artistes.

### 2.2.6 - Information sur un artiste : 
Dans cette partie, l'utilisateur souhaite obtenir des informations sur un artiste. Pour ce faire, il doit entrer le nom de l'artiste comme suit : 
![[2.2.6InfoArist1.png]]
Une fois cette information renseignée et l'application lancée, l'utilisateur obtient les informations suivantes :
![[2.2.6InfoArist2.png]]

On peut constater que les informations fournies par l'application sont donc :

-   Le nom de l'artiste.
-   Le nombre d'écoutes.
-   La liste de ses albums.
-   La liste de ses musiques.
-   La liste de ses différents styles musicaux.
-   La liste des artistes similaires.
-   La date de la dernière mise à jour de ces informations dans la base de données.

### 2.2.7 - Information sur un nombre d'écoute : 
Cette fonctionnalité permet à l'utilisateur de récupérer des informations sur un artiste, un album ou une musique, dont le nombre d'écoutes est supérieur ou inférieur à un certain seuil. Pour ce faire, l'utilisateur doit renseigner successivement ce qu'il souhaite. Voici quelques exemples d'utilisation :

-   L'utilisateur souhaite obtenir des informations sur une musique dont le nombre d'écoutes est supérieur à 500. Dans ce cas, il renseignera les informations suivantes : ![[2.2.7InfoSeuil1.png]]
    
-   L'utilisateur souhaite obtenir des informations sur un artiste dont le nombre d'écoutes est inférieur à 300. Dans ce cas, il renseignera les informations suivantes : ![[2.2.7InfoSeuil2.png]]
    
-   L'utilisateur souhaite obtenir des informations sur un album dont le nombre d'écoutes est supérieur à 400. Dans ce cas, il renseignera les informations suivantes : ![[2.2.7InfoSeuil3.png]]
    

Une fois que l'utilisateur a effectué ses choix, voici le résultat de l'application : ![[2.2.7InfoSeuil4.png]]

L'application affiche donc, dans le cas du premier exemple, l'ensemble des musiques dont le nombre d'écoutes est supérieur à 500. Les résultats pour les autres exemples suivront la même forme.

### 2.2.8 - Publier un commentaire : 
Cette fonctionnalité permet à l'utilisateur de publier des commentaires sur des musiques, des albums, des styles musicaux et des artistes. Pour ce faire, l'utilisateur doit commencer par renseigner son nom en haut à droite de l'interface :
![[2.2.8ComMusique0.png]]
#### 2.2.8.1 - Publier un commentaire sur une musique : 

Pour publier un commentaire sur une musique, l'utilisateur doit tout d'abord sélectionner la case "musique". Ensuite, il doit renseigner le nom de la musique, le nom de l'artiste, le commentaire et enfin la note (comprise entre 0 et 5).

![[2.2.8.1ComMusique1.png]]

#### 2.2.8.2 - Publier un commentaire sur un album : 

Pour publier un commentaire sur un album, l'utilisateur doit tout d'abord sélectionner la case "Album". Ensuite, il doit renseigner le nom de l'album, le commentaire et enfin la note.

![[2.2.8.2ComAlbum1.png]]

#### 2.2.8.3 - Publier un commentaire sur un style musical :

Pour publier un commentaire sur un style musical, l'utilisateur doit tout d'abord sélectionner la case "Tag". Ensuite, il doit renseigner le nom du style musical, le commentaire et enfin la note.

![[2.2.8.3ComTag.png]]

#### 2.2.8.4 - Publier un commentaire sur un artiste : 

Pour publier un commentaire sur un artiste, l'utilisateur doit tout d'abord sélectionner la case "Artiste". Ensuite, il doit renseigner le nom de l'artiste, le commentaire et enfin la note.

![[2.2.8.4ComArtiste.png]]

#### 2.2.8.5 - Résultats :

Si une erreur survient lors de la saisie des données, un message vous indiquera la source du problème. Par exemple :

![[2.2.8.5Res1.png]]

Si aucune erreur ne survient, un message vous informera que le commentaire a bien été inséré en base de données : 

![[2.2.8.5Res2.png]]

### 2.2.9 - Consulter un commentaire : 
Dans cette fonctionnalité, l'utilisateur peut consulter les commentaires qui ont été faits. De la même manière que pour la fonctionnalité précédente, il doit choisir entre une musique, un style musical, un artiste ou un album. Dans le cas où il choisit un style musical, l'utilisateur doit renseigner le nom du style musical comme ci-dessous :

![[2.2.9ConsultCom1.png]]

L'application lui renverra différentes informations telles que la date de création du commentaire, le nom de l'utilisateur l'ayant créé et le contenu du commentaire :
![[2.2.9ConsultCom2.png]]

