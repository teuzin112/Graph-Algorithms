import java.util.ArrayList;
import java.util.Map;
import java.io.File;
import java.util.Scanner;
import java.util.TreeMap;
import java.io.FileNotFoundException;

public class Graph {
  private ArrayList<TreeMap<Integer, Integer>> Adj; // Lista de adjacencia dos vertices
  private boolean isDirected; // Orientado
  private int numVertices; // Numero de vertices

  /**
   * Constroi um grafo com a lista de adjacencia vazia.
   * 
   * @param numVertices Numero de vertices do grafo
   * @param isDirected  Grafo orientado true ou false
   */
  public Graph(int numVertices, boolean isDirected) {
    this.isDirected = isDirected;
    this.numVertices = numVertices;

    this.Adj = new ArrayList<TreeMap<Integer, Integer>>(numVertices);

    for (int i = 0; i < this.numVertices; i++)
      this.Adj.add(new TreeMap<Integer, Integer>());

  }

  /**
   * Adiciona uma aresta ao grafo
   * 
   * @param srcVertice vertice de origem da aresta
   * @param dstVertice vertice de destino da aresta
   * @param weight     peso da aresta
   */
  public void add(int srcVertice, int dstVertice, int weight) {
    this.Adj.get(srcVertice).put(dstVertice, weight);

    // Se nao for orientado adiciona a aresta tambem no vertice de destino na lista
    // de adjacencias
    if (!this.isDirected)
      this.Adj.get(dstVertice).put(srcVertice, weight);

  }

  /**
   * Cria uma instancia de Graph com sua lista de adjacencia devidamente
   * inicializada a partir de um arquivo de texto.
   * 
   * @param filePath caminho do arquivo completo juntamente com seu nome
   * @return o novo grafo devidamente inicializado
   * @throws FileNotFoundException se o arquivo nao for encontrado
   */
  public static Graph loadGraph(String filePath) throws FileNotFoundException {
    // Cria um scanner para leitura do arquivo
    Scanner fileInput = new Scanner(new File(filePath));
    String line = fileInput.nextLine();

    boolean directed;
    if (line.contains("sim"))
      directed = true;
    else
      directed = false;

    line = fileInput.nextLine();
    Integer n = Integer.parseInt(line.substring(2));

    // Cria e inicializa um novo grafo a partir do numero de vertices e sua
    // orientacao
    Graph G = new Graph(n, directed);

    // Loop para ler as arestas do arquivo e inserir na lista de adjacencia do grafo
    while (fileInput.hasNextLine()) {
      line = fileInput.nextLine();

      String[] tokens = line.split("[(),:]");
      int src = Integer.parseInt(tokens[1]); // primeiro valor
      int dst = Integer.parseInt(tokens[2]); // segundo valor
      int weight = Integer.parseInt(tokens[4]); // terceiro valor

      // Adiciona no grafo a aresta lida
      G.add(src, dst, weight);
    }

    return G;
  }

  /**
   * Gera a matriz de adjacencia com os pesos presentes na lista de
   * adjacencia do item do tipo Graph
   * 
   * @return matriz de adjacencia com os devidos pesos
   */
  public int[][] getMatrix() {
    int[][] adj = new int[this.numVertices][this.numVertices];

    for (int i = 0; i < this.numVertices; i++) {
      for (int j = 0; j < this.numVertices; j++) {
        // Inicializa matriz de adjacencia modificada
        adj[i][j] = Algorithms.INF;
      }
    }

    for (int u = 0; u < this.numVertices; u++) {
      // Variavel auxiliar que armazena a lista de adjacencia ao vertice U
      TreeMap<Integer, Integer> adjToU = this.getAdj().get(u);

      for (Map.Entry<Integer, Integer> v : adjToU.entrySet()) {
        adj[u][v.getKey()] = Algorithms.weight(u, v.getKey(), this.getAdj()); // Gera a matriz de adjacencia modificada
      }
    }
    return adj;

  }

  public ArrayList<TreeMap<Integer, Integer>> getAdj() {
    return Adj;
  }

  public boolean isDirected() {
    return isDirected;
  }

  public int getNumVertices() {
    return numVertices;
  }

}
