# Massive-Maze

```
// Generates a random 1337 x 1337 maze
gradle generate -Psize=1337
```

## Motivation (origin story)
It all began when I saw a maze [online](http://www.astrolog.org/labyrnth/maze.htm) claiming it was the largest maze on the internet. "Nonsense!" I exclaimed, I can generate a larger one. Upon looking into the eyes of the maze gods, I learned their secrets. I now had the tools I needed to generate the world's largest maze.

## Algorithms (secrets of the maze gods)
The maze core maze generation process is written in Java and uses the Hunt-and-Kill maze generation algorithm. The maze data is then written out to a maze.dat file. Next, a C process runs to interpret the maze.dat file and write out a maze.bmp file containing the resulting maze image.

This implementation is designed to be memory efficient to allow very large mazes to be generated. My humble Macbook can generate a 16,383 x 16,383 maze in just over 3 minutes. This is impressive considering the resulting maze image file is 134 mb and is large enough to crash my default image viewer. Further improvements could perhaps be coding everything in C or utilizing multiple threads.
