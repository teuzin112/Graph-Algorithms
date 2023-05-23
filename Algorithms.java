import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class Algorithms {
  public final static int INF = 99999999;
  private static String color[];
  public static int predecessor[];
  private static int distance[];
  private static LinkedList<Integer> answerQ; // Variavel auxiliar para imprimir as respostas

  /**
   * Imprime na tela a ordem de visitacao dos vertices do grafo, dado um vertice
   * de origem. Utiliza busca em profundidade.
   * 
   * @param G   objeto grafo
   * @param src vertice de origem
   */
  public static void dfs(Graph G, int src) {
    color = new String[G.getNumVertices()];
    answerQ = new LinkedList<Integer>();

    for (int u = 0; u < G.getNumVertices(); u++)
      color[u] = "white";

    dfsVisit(src, G.getAdj());

    System.out.print("DFS: ");
    while (answerQ.size() > 1)
      System.out.printf("%d - ", answerQ.poll());
    System.out.printf("%d\n", answerQ.poll());

  }

  /**
   * Metodo auxiliar para dfs() que visita um vertice e atribui os devidos valores
   * nas variaveis globais.
   * 
   * @param u   Vertice atual que esta sendo visitado
   * @param Adj Lista de adjacencias
   */
  private static void dfsVisit(int u, ArrayList<TreeMap<Integer, Integer>> Adj) {
    color[u] = "gray";
    answerQ.add(u);

    // Variavel auxiliar que armazena a lista de adjacencia ao vertice U
    TreeMap<Integer, Integer> adjToU = Adj.get(u);

    // Para cada vertice adjacente a U
    for (Map.Entry<Integer, Integer> v : adjToU.entrySet()) {
      if (color[v.getKey()] == "white")
        dfsVisit(v.getKey(), Adj);
    }
    color[u] = "black";
  }

  /**
   * Imprime na tela a ordem de visitacao dos vertices do grafo, dado um vertice
   * de origem. Utiliza busca em largura.
   * 
   * @param G   objeto grafo
   * @param src vertice de origem
   */
  public static void bfs(Graph G, int src) {
    color = new String[G.getNumVertices()];
    answerQ = new LinkedList<Integer>();

    for (int u = 0; u < G.getNumVertices(); u++)
      color[u] = "white";

    color[src] = "gray";

    Queue<Integer> Q = new LinkedList<Integer>();
    Q.add(src);

    while (!Q.isEmpty()) {
      int u = Q.element();

      TreeMap<Integer, Integer> adjToU = G.getAdj().get(u);
      for (Map.Entry<Integer, Integer> v : adjToU.entrySet()) {
        if (color[v.getKey()] == "white") {
          color[v.getKey()] = "gray";
          Q.add(v.getKey());
        }

      }
      answerQ.add(Q.poll());
      color[u] = "black";
    }

    System.out.print("BFS: ");
    while (answerQ.size() > 1)
      System.out.printf("%d - ", answerQ.poll());
    System.out.printf("%d\n", answerQ.poll());
  }

  /**
   * Utilizando o algoritmo de Dijkstra, imprime na tela o menor caminho entre um
   * vertice de origem e todos os outro vertices de um grafo, juntamente com suas
   * respectivas distancias.
   * 
   * @precondition grafo possui somente arestas de pesos positivos
   * @param G   objeto grafo
   * @param src vertice de origem
   */
  public static void dijkstra(Graph G, int src) {
    answerQ = new LinkedList<Integer>();
    predecessor = new int[G.getNumVertices()];

    if (hasNegativeWeight(G)) {
      System.out.println("It is not possible to run Dijkstra's algorithm - graph has negative weights edges");
      return;
    }

    initializeSrc(G, src);
    // Inicializa origem
    List<Integer> Q = new ArrayList<Integer>();
    for (int u = 0; u < G.getNumVertices(); u++)
      Q.add(u);

    int u;
    while (!Q.isEmpty()) {
      u = getMin(Q, distance);

      // Variavel auxiliar que armazena a lista de adjacencia ao vertice U
      TreeMap<Integer, Integer> adjToU = G.getAdj().get(u);

      // Para cada vertice adjacente a U
      for (Map.Entry<Integer, Integer> v : adjToU.entrySet())
        relax(u, v.getKey(), G.getAdj());
    }
    // Imprime todos os caminhos
    printPaths(G, src);
  }

  /**
   * Utilizando o algoritmo de Bellman Ford, imprime na tela o menor caminho entre
   * um vertice de origem e todos os outro vertices de um grafo, juntamente com
   * suas respectivas distancias.
   * 
   * @precondition grafo deve ser orientado
   * @param G   objeto grafo
   * @param src vertice de origem
   * @return false se o grafo possui ciclo de peso negativo, true caso contrario
   */
  public static boolean bellmanFord(Graph G, int src) {
    answerQ = new LinkedList<Integer>();
    predecessor = new int[G.getNumVertices()];

    if (!G.isDirected()) {
      System.out.println("It is not possible to run Bellman-Ford algorithm - graph not directed");
      return false;
    }

    initializeSrc(G, src);
    for (int i = 1; i < G.getNumVertices(); i++) {
      // Percorre todas as aresta existentes
      for (int u = 0; u < G.getNumVertices(); u++) {
        // Variavel auxiliar que armazena a lista de adjacencia ao vertice U
        TreeMap<Integer, Integer> adjToU = G.getAdj().get(u);

        // Para cada vertice adjacente a U
        for (Map.Entry<Integer, Integer> v : adjToU.entrySet())
          relax(u, v.getKey(), G.getAdj());
      }
    }
    for (int u = 0; u < G.getNumVertices(); u++) {
      // Variavel auxiliar que armazena a lista de adjacencia ao vertice U
      TreeMap<Integer, Integer> adjToU = G.getAdj().get(u);

      // Para cada vertice adjacente a U
      for (Map.Entry<Integer, Integer> v : adjToU.entrySet()) {
        if (distance[u] == Integer.MAX_VALUE)
          continue;
        if (distance[v.getKey()] > distance[u] + weight(u, v.getKey(), G.getAdj())) {
          System.out.println("Cycle of negative weight found");
          return false;
        }
      }
    }
    // printPaths(src, G);
    return true;
  }

  /**
   * Utilizando o algoritmo de Floyd-Warshall, imprime na tela a matriz de
   * distancias, juntamente com todos os menores caminhos entre todos os pares de
   * vertices
   * 
   * @precondition grafo nao deve possuir ciclos de peso negativo
   * @param G objeto grafo
   */
  public static void floydWarshall(Graph G) {
    // Verifica se o grafo possui ciclo de peso negativo
    for (int u = 0; u < G.getNumVertices(); u++) {

      if (!bellmanFord(G, u)) {
        System.out
            .println("It is not possible to apply Floyd-Warshall algorithm - graph has cycle of negative weight");
        return;
      }
    }

    int numV = G.getNumVertices();
    int dist[][] = new int[numV][numV]; // Matriz de adjacencia modificada
    int sucessor[][] = new int[numV][numV]; // Matriz de sucessores para imprimir os menores caminhos

    for (int i = 0; i < numV; i++) {
      for (int j = 0; j < numV; j++) {
        sucessor[i][j] = -1; // Inicializa matriz de sucessores

        // Inicializa matriz de adjacencia modificada
        if (i == j)
          dist[i][j] = 0;
        else
          dist[i][j] = INF;
      }
    }

    for (int u = 0; u < numV; u++) {
      // Variavel auxiliar que armazena a lista de adjacencia ao vertice U
      TreeMap<Integer, Integer> adjToU = G.getAdj().get(u);

      for (Map.Entry<Integer, Integer> v : adjToU.entrySet()) {
        dist[u][v.getKey()] = weight(u, v.getKey(), G.getAdj()); // Gera a matriz de adjacencia modificada
        sucessor[u][v.getKey()] = v.getKey(); // Gera matriz de sucessores (Como se fosse uma matriz de adjacencia sem
                                              // os pesos das arestas)
      }
    }

    // Logica principal do algoritmo
    for (int k = 0; k < numV; k++) {
      for (int i = 0; i < numV; i++) {
        for (int j = 0; j < numV; j++) {
          if (dist[i][k] + dist[k][j] < dist[i][j]) {
            dist[i][j] = dist[i][k] + dist[k][j];
            sucessor[i][j] = sucessor[i][k]; // Atualiza a matriz de sucessor com o novo menor caminho calculado
          }
        }
      }
    }

    printMatrix(dist, numV);
    printAllPaths(sucessor, numV);
  }

  /**
   * Imprime na tela todas as arestas que compoem a arvore geradora minima,
   * juntamente com o peso total da arvore gerada.
   * 
   * @precondition grafo deve ser nao orientado
   * @param G objeto grafo
   * @return Arraylist de arestas que determinam a arvore geradora minima
   */
  public static ArrayList<Edge> kruskal(Graph G) {
    if (G.isDirected()) {
      System.out.println("It is not possible to apply Kruskal's algorithm - directed graph");
      return null;
    }

    int numV = G.getNumVertices();
    predecessor = new int[numV]; // Utilizada para armazenar os pais dos conjuntos
    answerQ = new LinkedList<Integer>();

    // Inicializa matriz de adjacencia modificada sem zeros na diagonal principal
    int dist[][] = new int[numV][numV];
    for (int u = 0; u < numV; u++) {
      predecessor[u] = u; // Inicializa os conjuntos com apenas seu vertice correspondente
      for (int v = 0; v < numV; v++)
        dist[u][v] = INF;
    }

    for (int u = 0; u < numV; u++) {
      // Variavel auxiliar que armazena a lista de adjacencia ao vertice U
      TreeMap<Integer, Integer> adjToU = G.getAdj().get(u);

      for (Map.Entry<Integer, Integer> v : adjToU.entrySet())
        dist[u][v.getKey()] = weight(u, v.getKey(), G.getAdj()); // Gera a matriz de adjacencia modificada
    }

    // Logica principal
    int totalWeight = 0;
    for (int numEdges = 0; numEdges < numV - 1; numEdges++) {
      int min = INF, a = -1, b = -1;
      for (int i = 0; i < numV; i++) {
        for (int j = 0; j < numV; j++) {
          if (find(i) != find(j) && dist[i][j] < min) {
            min = dist[i][j];
            a = i;
            b = j;
          }
        }
      }

      union(a, b);
      totalWeight += min;

      // Armazena as arestas calculadas para futura impressao
      answerQ.add(a);
      answerQ.add(b);

    }
    // Cria um novo arraylist para armazenar as arestas
    ArrayList<Edge> edges = new ArrayList<Edge>(answerQ.size() / 2);

    // Adiciona as arestas na lista
    for (int i = 0; i < answerQ.size(); i += 2) {
      int u = answerQ.get(i);
      int v = answerQ.get(i + 1);
      edges.add(new Edge(u, v, weight(u, v, G.getAdj())));
    }

    printMst(totalWeight, -1);

    return edges;
  }

  /**
   * Imprime na tela todas as arestas que compoem a arvore geradora minima a
   * partir de um vertice de origem, juntamente com o peso total da arvore gerada.
   * 
   * @precondition grafo deve ser nao orientado
   * @param G   objeto grafo
   * @param src vertice de origem
   */
  public static void prim(Graph G, int src) {
    if (G.isDirected()) {
      System.out.println("It is not possible to apply Prim's algorithm - directed graph");
      return;
    }

    int numV = G.getNumVertices();
    predecessor = new int[numV];

    List<Integer> Q = new ArrayList<Integer>();
    int key[] = new int[numV];
    for (int u = 0; u < numV; u++) {
      Q.add(u);
      key[u] = INF;
    }

    key[src] = 0;
    predecessor[src] = -1;

    // Extrai o vertice com menor chave da fila
    answerQ = new LinkedList<Integer>();
    int u;
    while (!Q.isEmpty()) {
      u = getMin(Q, key);

      // Variavel auxiliar que armazena a lista de adjacencia ao vertice U
      TreeMap<Integer, Integer> adjToU = G.getAdj().get(u);

      // Para cada vertice adjacente a U
      for (Map.Entry<Integer, Integer> v : adjToU.entrySet())
        if (Q.contains(v.getKey()) && weight(u, v.getKey(), G.getAdj()) < key[v.getKey()]) {
          predecessor[v.getKey()] = u;
          key[v.getKey()] = weight(u, v.getKey(), G.getAdj());
        }
    }

    // Logica principal
    int totalWeight = 0;
    for (u = 0; u < numV; u++) {
      if (predecessor[u] != -1) {
        answerQ.add(u);
        answerQ.add(predecessor[u]);
        totalWeight += weight(u, predecessor[u], G.getAdj());
      }
    }

    printMst(totalWeight, src);
  }

  /**
   * Dado uma lista de indices e um vetor de inteiros remove e retorna da lista de
   * indices o indice que possui o menor elemento no vetor
   * 
   * @param Q     lista de indices.
   * @param array vetor.
   * @return indice do menor elemento removido do vetor.
   */
  private static int getMin(List<Integer> Q, int array[]) {
    int u = Q.get(0);
    int indexOfVerticeToRemove = 0;

    for (int i = 0; i < Q.size(); i++) {
      if (array[Q.get(i)] < array[u]) {
        u = Q.get(i);
        indexOfVerticeToRemove = i;
      }
    }
    Q.remove(indexOfVerticeToRemove);
    return u;
  }

  /**
   * Metodo auxiliar para imprimir na tela a arvore geradora minima previamente
   * calculada.
   * 
   * @param totalWeight peso total da arvore
   * @param src         vertice de origem, -1 caso nao tenha origem
   */
  private static void printMst(int totalWeight, int src) {
    if (src != -1)
      System.out.println("initial vertex: " + src);

    System.out.println("total weight: " + totalWeight);

    while (answerQ.size() > 1)
      System.out.printf("(%d,%d) ", answerQ.poll(), answerQ.poll());

    System.out.println();
  }

  /**
   * Metodo auxiliar para realizar a uniao de dois conjuntos
   * 
   * @param i conjunto a.
   * @param j conjunto b.
   * @postcondition uniao realizada e armazena na varial predecessor.
   */
  private static void union(int i, int j) {
    int a = find(i);
    int b = find(j);
    predecessor[a] = b;
  }

  /**
   * Metodo auxiliar para encontrar o conjunto de um vertice
   * 
   * @param u vertice
   * @return o indice do conjunto
   */
  private static int find(int u) {
    while (predecessor[u] != u)
      u = predecessor[u];
    return u;
  }

  /**
   * Metodo auxiliar para imprimir todos os caminhos calculados pelo algoritmo de
   * Floyd-Warshall
   * 
   * @precondition caminhos corretamente calculados
   * @param sucessor matriz de sucessores
   * @param n        ordem da matriz de sucessores
   */
  private static void printAllPaths(int sucessor[][], int n) {
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        System.out.printf("path from %d to %d: ", i, j);
        if (i == j)
          System.out.println(i);
        else if (sucessor[i][j] == -1)
          System.out.println("---");
        else {
          System.out.print(i + " ");
          int aux = sucessor[i][j];
          while (aux != -1) {
            System.out.printf("- %d ", aux);
            aux = sucessor[aux][j];
          }
          System.out.println();
        }
      }
    }
  }

  /**
   * Metodo auxiliar para imprimir uma matriz quadrada de inteiros
   * 
   * @precondition matriz quadrada
   * @param m matriz
   * @param n ordem da matriz
   */
  public static void printMatrix(int m[][], int n) {
    System.out.printf("   ");
    for (int i = 0; i < n; i++)
      System.out.printf("%2d ", i);
    System.out.println();

    for (int i = 0; i < n; i++) {
      System.out.printf("%2d ", i);
      for (int j = 0; j < n; j++) {
        if (m[i][j] > INF / 10)
          System.out.printf("-- ");
        else
          System.out.printf("%2d ", m[i][j]);
      }
      System.out.println();
    }
  }

  /**
   * Metodo auxiliar para obter o peso da aresta de dois vertices
   * 
   * @precondition aresta deve existir
   * @param u   vertice u
   * @param v   vertice v
   * @param Adj lista de adjacencia
   * @return peso da aresta
   */
  public static int weight(int u, int v, ArrayList<TreeMap<Integer, Integer>> Adj) {
    return Adj.get(u).get(v);
  }

  /**
   * Metodo auxiliar utilizado para calcular a distancia e menor caminho entre
   * dois vertices
   * 
   * @preconditon vertices devem existir na lista de adjacencia
   * @param u   vertice u
   * @param v   vertice v
   * @param Adj lista de adjacencia
   */
  public static void relax(int u, int v, ArrayList<TreeMap<Integer, Integer>> Adj) {
    if (distance[u] == Integer.MAX_VALUE)
      return;
    if (distance[v] > distance[u] + weight(u, v, Adj)) {
      distance[v] = distance[u] + weight(u, v, Adj);
      predecessor[v] = u;
    }

  }

  /**
   * Metodo auxiliar para determinar se um grafo possui arestas de peso negativo
   * 
   * @param G objeto grafo
   * @return true caso haja arestas com peso negativo, false caso contrario
   */
  public static boolean hasNegativeWeight(Graph G) {
    for (int u = 0; u < G.getNumVertices(); u++) {
      // Variavel auxiliar que armazena a lista de adjacencia ao vertice U
      TreeMap<Integer, Integer> adjToU = G.getAdj().get(u);

      // Para cada vertice adjacente a U
      for (Map.Entry<Integer, Integer> v : adjToU.entrySet())
        if (v.getValue() < 0)
          return true;
    }
    return false;
  }

  /**
   * Metodo auxilar utilizado para calcular o caminho de um vertice
   * 
   * @param src vertice de origem
   * @param dst vertice de destion
   * @postconditon variavel answerQ atualizada com o caminho calculado
   */
  private static void getPath(int src, int dst) {
    if (predecessor[dst] != src)
      getPath(src, predecessor[dst]);
    answerQ.add(dst);
  }

  /**
   * Metodo auxilar para imprimir todos os caminhos dado um vertice de origem
   * 
   * @precondition vetor de distancias previamente calculado
   * @param G   objeto grafo
   * @param src vertice de origem
   */
  public static void printPaths(Graph G, int src) {
    System.out.println("source: " + src);
    for (int u = 0; u < G.getNumVertices(); u++) {

      if (distance[u] != Integer.MAX_VALUE) {
        if (u == src)
          System.out.printf("destination: %d  distance: %d  path: %d\n", src, 0, src);

        else {
          System.out.printf("destination: %d  distance: %d  path: ", u, distance[u]);
          getPath(src, u);
          System.out.print(src + " - ");
          while (answerQ.size() > 1)
            System.out.print(answerQ.pollFirst() + " - ");

          System.out.println(answerQ.pollFirst());
        }
      }
    }
  }

  /**
   * Metodo auxilar para inicializar a origem nos algoritmos de Dijkstra e Bellman
   * Ford
   * 
   * @param G   objeto grafo
   * @param src vertice de origem
   */
  private static void initializeSrc(Graph G, int src) {
    distance = new int[G.getNumVertices()];
    for (int u = 0; u < G.getNumVertices(); u++) {
      distance[u] = Integer.MAX_VALUE;
    }
    distance[src] = 0;
  }

}
