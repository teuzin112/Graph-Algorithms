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

The program only loads one graph at a time through a _.txt_ file, which needs to follow this specific syntax:

1. **_"directed=yes_"** or **"_directed=no_"**      Indicates if the graph is directed or not. It is the first line of the file.
2. **"_V=\<n\>_"**      Contains the number of vertices of the graph. The vertices are enumerated from **0** to **n-1**. It is the second line of the file.
3. **"_(\<u\>, \<n\>): \<weight\>_"**     Represents the edge **_(u, v)_** with their respective weight. **_\<u\>_** and **_\<v\>_** are integers values between **0** and **n-1**. The weight must be an integer value(it can be negative). The edges are specified starting at the third line from the file, being **_one edge per line_**.

In the case of a not directed graph, the edge **_(u, v)_** will show up only once in the file. Being considered the same edge **_(v, u)_**.

For example, lets consider the following graph:

<img src="https://github.com/teuzin112/Graph-Algorithms/blob/main/graph.png" width="250" height="250" />
