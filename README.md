# Graph-Algorithms
Java program to execute some of the most important graph algorithms.

This project implements the following algorithms:

- Depth-First Search (DFS)
- Bread-First Search (BFS)
- Dijkstra's algorithm
- Bellman–Ford algorithm
- Floyd–Warshall algorithm
- Kruskal's algorithm
- Prim's algorithm

For the functionality of drawing you will need to download and install Graphviz from [here](https://graphviz.org/download/).

#### What is Graphviz ?

>Graphviz is open source graph visualization software. It has several main graph layout programs. It also has web and interactive graphical interfaces, and auxiliary tools, libraries, and language bindings.

>Graph visualization is a way of representing structural information as diagrams of abstract graphs and networks. Automatic graph drawing has many important applications in software engineering, database and web design, networking, and in visual interfaces for many other domains.

The program only loads one graph at a time through a .txt file, which needs to follow this specific syntax:

1. **_directed=yes_** or **_directed=no_**: indicates if the graph is directed or not. It is the first line of the file.
2. **_V=<n>_**: contains the number of vertices of the graph. The vertices are enumerated from **0** to **n-1**
