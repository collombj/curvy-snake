# Guide de Développement

## Prérequis
L'application est basée sur le JDK SE 1.8.
Le rendu graphic est réalisé grâce à la librairie Zen 5.


## Références
Pour toutes informations complémentaires, se référer à la javadoc et au digramme de class.


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

Les Exceptions ne représentent que des situations d'exception pour des class développées. Par conséquent, aucun traitement n'est réalisé par ces class. Il est donc envisageable de mettre en place une gestion plus complète de celles-ci.
Elles sont attrapées que lors de leurs gestions : par exemple la collision est gérée au tout dernier moment.




## Corps du Serpent
Le corps du serpent est représenté par une liste de cercle. Il est ainsi stocké dans la class Movement. Celle-ci n'est accessible que grâce à une class présente dans le package controller. Il est donc ainsi necessaire d'implémenter une class utilisant le Movement.

Celle-ci prend en compte :
	- les collisions avec : un mur et un corp de serpent
	- la récupération de la tête et de la queue
	- le déplacement du corps
	- la traversé de mur
	- l'effacement quasi-complet du corps.

La collision est répartie en deux types : mur et autre corps. La collision a été pensé pour que l'on puisse détecté la collision entre plusieurs snakes.

La méthode de déplacement prend en paramètre 2 listes : une contenant les positions ajoutées et une autre pour les positions supprimées.
Le corps du snake est rallongé une fois sur deux. De plus, la méthode met en application les bonus du Snake.




## Snake
Le Snake est un stockage d'un Movement, avec une gestion de la direction et des bonus.

La direction est représenté avec un angle alpha variant sur 360 degrés.
Les bonus sont décrémenté tous les tours (1 tours = 1ms~). Ils sont tous interprétés pour être passés à Movement sauf EraseAll (effacé tout), qui lui est exécuté que lors de sa récupération.


Le serpent a été pensé pour du multijoeur. C'est à dire que dans la class, en static, on stocke une référence sur toutes les instances de Snake. Par conséquent, dès que l'on veut supprimer une instance, il faut utiliser la méthode destroy(). Celle-ci a pour effet de libérer l'élément de la liste d'instance.
Le fait d'avoir ce système permet de détecter automatiquement les collisions entre les serpents sans aucune contrainte, et ce très simplement. Ce traitement est totalement transparent. En effet, c'est la méthode Movement::move() qui gère cette aspect. D'où la gestion masquée et simplifiée en static.

Cette partie peut très largement être exportée et simplifiée.

TODO : exporté detectBonus() dans la class Bonus.



## Bonus
### TODO

## Gestion des Bonus
### TODO


## Utilisation de l'API
L'API s'utilise de la manière suivante :

import fr.upem.ir1.curvysnake.controller.BonusListInGame;
import fr.upem.ir1.curvysnake.controller.MoveTo;
import fr.upem.ir1.curvysnake.controller.Snake;
import fr.upem.ir1.curvysnake.controller.exception.CollisionException;




Snake.setGameSize(new Rectangle(x,y, largeur, hauteur));	// Informations sur le plateau de jeu
Snake.setBonusListInGame(new BonusListInGame());				// Instanciation de la liste de Bonus pour le jeux



Snake snake = new Snake(
	new Point(x, y),			// Position initiale
	-45							// Angle de départ
);



// Change l'angle de direction du serpent (va vers le bas)
// le false correspond au flag de virage. Si celui-ci est à true, le changement d'angle est plus faible.
snake.changeDirection(MoveTo.RIGHT, false);

// Change l'angle de direction du serpent (va vers le haut)
// le true correspond au flag de virage. Si celui-ci est à true, le changement d'angle est plus faible.
snake.changeDirection(MoveTo.RIGHT, true);

// ATTENTION : IllegalAccessException levé si le Snake possède un Bonus non applicable (exemple : EraseAll)



// Déplacement du serpent en fonction des bonus, de sa vitesse.
// listeDAjout correspond au point du corps du serpent ajoutés (pensez à vider la liste entre chaque appel)
// listeSuppresion correspond au point du corps du serpent supprimés (pensez à vider la liste entre chaque appel)
snake.move(listeDAjout, listeSuppresion);

// ATTENTION : CollisionException en cas de collision.



// Décremente les compteurs de temps des Bonus de tous les serpents
Snake.decrementAll();



// Ajoute (en fonction des probabilités, et de la place restante) un bonus, de manière aléatoire.
Snake.getBonusListInGame().random();



// Destruction du Snake
snake.destroy();
