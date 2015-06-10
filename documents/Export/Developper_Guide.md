# Guide de Développement

## Prérequis
L'application est basée sur le JDK SE 1.8.
Le rendu graphic est réalisé grâce à la librairie Zen 5.



## Architecture

	Root Folder
	|
	|--> fr.upem.ir1.curvysnake
	|    |
	|    |--> controller
	|    |    |
	|    |    |--> exception
	|    |    |    |
	|    |    |    | BonusException.java 		* Exception dù à un bonus n'étant pas une caractéristique applicable directement à un Snake *
	|    |    |    | CollisionException.java    * Exception dù à une collision entre un Snake et lui-même, ou un autre Snake, ou un mur *
	|    |    |    | GameSizeException.java 	* Exception dù à une non intialisation des paramètres (statiques) du plateau de jeux (x, y, hauteur, largeur) *
	|    |    |
	|    |    | Bonus.java 				* Class représentant un Bonus. C'est cette objet qui possède les différentes caractéristiques offrant un bonus (ou un malus) *
	|    |    | BonusAvailable.java 	* Enum représentant la liste des types de Bonus existant *
	|    |    | BonusListInGame.java 	* Class représentant les Bonus actuellement sur le plateau de jeu *
	|    |    | Entry.java 				* Class représentant un couple de données : Clé/Valeur *
	|    |    | Movement.java 			* Class représentant le corps du serpent *
	|    |    | MoveTo.java 			* Enum représentant le déplacement possible (droite/gauche) de la tête du Snake *
	|    |    | Snake.java 				* Class représentant le Snake en lui même * ** CLASS PRINCIPALE **
	|    |
	|    |--> view
	|    |    |
	|    |    | MultiPlayer.java 	* Interface graphique utilisée dans le cas d'un mulitjoueur en local *
	|    |    | SinglePlayer.java 	* Interface graphique utilisée dans le cas d'un jeu en solo, en local *
	|    |
	|    | Main.java * Point d'entrée de l'application *



## Normes
L'application a été développée avec la norme MVC (Model View Controller). Cependant, comme aucune base de données n'a été utilisée, la partie Model n'a pas été implémentée.

La totalité du code (variables, javadoc ...) a été rédigée en Anglais.

Aucune class n'a été récrite. C'est-à-dire que les class existants déjà au sein de Java ont été réutilisées.



## Utilitaires
La class Entry est une représentation d'un couple clé/valeur.

Les Exceptions ne représentent que des situations des class développées.



## Corps du Serpent

## Snake

## Gestion des Snakes

## Bonus

## Gestion des Bonus
