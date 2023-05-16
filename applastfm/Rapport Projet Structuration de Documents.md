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

Nous pouvons obeserver dans un premier temps qu'une information importante est renseigné en haut à droite de l'interface. Cette information est le type de requettage. 
Si la requette est faite sur l'API LastFM alors cette partie s'affichera comme suit  :	![[2.2Choix2.png]]
En revanche, si la requette est faite sur la base de données mangoDB alors cette partie s'affichera de la manière suivante : 
![[2.2Choix3.png]]

Sur cette interface vous pouvez observer plusieurs fonctionalitées mises à disposition de l'utilisateur. Nous avons décidé de placer chaques fonctionnalitée dans une case permettant ainsi à l'utilisateur de bien disocier chaqu'une d'entre elles. 

Comme vous pouvez le constater l'interface peut se découper en deux parties : 

* La première permet de choisir quelle fonctionnalitée l'utilisateur veut utiliser ainsi que renseigner les informations associé. 
* La seconde partie de l'interface sert à afficher les résultats. 

Selon la fonctionnalitée choisie par l'utilisateur, les résultats ne seront pas affichés de la même façon. Pour valider son choix, l'utilisateur doit appuyer sur le boutton "Valider" en bas de l'interface.

Dans la suite de cette partie nous alons detailer comment fonctionne l'interface pour chaques fonctionnalitées.

### 2.2.1 - Information sur un tag : 

![[2.2.1InfoTag1.png]]
Dans cette partie l'utilisateur souhaite connaitre des informations sur un style musicale. La première chose qu'il doit faire c'est de renseigner le nom du style. Par la suite il doit choisir entrre avoir le résumé ou l'ensemble du contenue concernant ce style. Par defaut s'il ne coche rien, l'application lui affichera les deux. Voici comment l'application affiche le résulatat de cette fonctionnalité : 
![[2.2.1InfoTag2.png]]
On peut constater qu'une information sur la date du dernier enregistrement dans la base de données est aussi renseigné. 

### 2.2.2 - Information sur un album : 

![[2.2.2InfoAlbum1.png]]

Dans cette partie l'utilisateur souhaite avoir des informations sur un album. Pour cela il doit renseigner le nom de l'album ainsi que le nom de l'artiste. Une fois ses choix fait il appuyer sur le boutton "Valider". Le resultat est le suivant : 
![[2.2.2InfoAlbum2.png]]
On peut observer que les informations renseigné à l'utilisateur sont donc le nom de l'album, le nom de l'artiste, la date de derniere modification dans la base de données, le nombre de morceaux contenue dans l'album, sa durée totale en seconde, son nombre d'écoute et pour finir la liste des musique qui composent l'album. 

### 2.2.3 - Information sur une musique  : 

![[2.2.3InfoTrack1.png]]
Ici l'utilisateur souhaite connaitre des informations sur une musique. Pour cela il doit donc renseigner le nom de la musique et le nom de l'artiste. Dans cette exemple nous prendrons donc le titre "Holiday" du groupe Green Day.
Le résultat retourné par l'application est le suivant :
![[2.2.3InfoTrack2.png]]
Les informations affichées à l'utilisateur sont donc les nom de l'artiste, le nom de la musique, le nombre d'écoute, le nombre d'utilisateur écoutant la musique, la date de dernière modification en base et pour finir la liste des styles musicaux auquelle appartient la musique. 

### 2.2.4 - Information sur les TOPs 10  : 
Cette fonctionnalitée permets de donner des renseignements sur les différents TOPs 10. L'utiilisateur à plusieur possibilitées : 
* Avoir les informations sur les TOPs 10 global, c'est à dire TOPS 10 monde. 
* Avoir les informations sur les TOPs 10 spécifiquement par pays. 

Pour le premier choix, l'utilisateur doit simplement coché la case attribué :

![[2.2.4InfoTop1.png]]

Pour le second choix, l'utilisateur peut choisir le nom du pay dans la liste déroulante comme ceci : 
![[2.2.4InfoTop2.png]]

Une fois cette partie renseigner, l'utilisateur valide ses choix et l'application retourne : 
* Pour le TOP 10 globale : 
![[2.2.4InfoTOP3.png]]
* Pour le TOP 10 par pay avec l'exemple des Etats Unis :
 ![[2.2.4InfoTOP4.png]]
L'utilisateur à donc accès au top 10 des musiques, des styles musicaux et des artistes ainsi que la date de requettage. 

### 2.2.5 - Information sur les musiques et albums similaires  : 
Dans cette fonctionnalitée, l'utilisateur souhaite récupérer les musiques et les albums, des artites renseigné, qui ont le même styles musicale. Cela permet ainsi de récupére rles musiques et albums similaire entre les deux artistes : 
![[2.2.5InfoSimilaire1.png]]

Pour ce faire l'utilisateur peut simplement entrer les noms des deux artites puis appuyer lancer l'application. Dans le cas de cette exemple, nous souhaitons avoir les informations entre l'artiste D12 et Eminem. Le resultat est le suivant : 
![[2.2.5InfoSimilaire2.png]]
L'utilisateur à donc accès à la liste des musiques et des albums similaires entres les artistes.

### 2.2.6 - Information sur un artiste : 
Dans cette partie l'utilisateur souhaite avoir des informations sur un artiste. Pour ce faire il doit renrtrer le nom de l'artiste comme suit : 
![[2.2.6InfoArist1.png]]
Une fois cette information renseigner et l'application lancé, l'utilisateur obtiens les informations suivantes : 
![[2.2.6InfoArist2.png]]

On peut constater que les informations renseigner sur l'application sont donc : 
* le nom de l'artiste
* le nombre d'écoute
* la liste de ses albums
* la liste de ses musiques
* la liste de ses différents styles musicaux
* la liste des artistes similaires
* la date de la dernière mise a jours de ces informations dans la base de données

### 2.2.7 - Information sur un nombre d'écoute : 

![[2.2.7InfoSeuil1.png]]

![[2.2.7InfoSeuil2.png]]

![[2.2.7InfoSeuil3.png]]