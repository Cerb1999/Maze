# Maze

## Fonctionnalités 

- Héros
  - Le héros est placé aléatoirement sur une case du niveau
  - Le héros dispose de trois vies

- Labyrinthe
  - Les niveaux sont générés aléatoirement.
  - Le labyrinthe est généré à partir d’un fichier.
  - Le héros et les monstres ne peuvent pas traverser les murs.
  - Une partie commence au niveau 1.
  - Certains cases du labyrinthe sont spéciales :
    - clé : requise pour accéder au prochain niveau
    - échelle : aller au prochain niveau
    - pièges : piège classique sur une case, fait perdre une vie
    - magiques : si un personnage arrive sur la case un effet est déclenché (ralentissement, perte d'attaque)

- Monstres
  - Des monstres sont placés de manière aléatoire dans le labyrinthe.
  - Les monstres se déplacent de manière aléatoire jusqu'à une certaine distance du héros où ils les monstres se déplacent de manière intelligente en essayant de l'attraper.
 
- Attaques
  - Le héros perd une vie au contact d’un monstre, replacé au début du niveau (sans réapparation des objets).
  - Le héros peut attaquer les monstres (corps à corps - case adjacente)
  - Perte de vie par collision entre le héros et les monstres.
  - ((Attaque des monstres))
  
 - Autre
   - Pause
   - Timer
