import java.io.File;
import java.util.ArrayList;

public class GraphDrawing {
	static private Graph G;
	static private int[][] adj;

	/**
	 * Gera um pdf com a imagem do grafo g recebido como parametro
	 * 
	 * @param fileName nome do arquivo de saida
	 * @param g        grafo a ser desenhado
	 * @param predecessor[] vetor de antecessores para pintar as arestas no algoritmo Prim
	 */
	public static void drawGraphPrim(String fileName, Graph g, int predecessor[]) {
		G = g;
		// Popula matriz adjacencia com conteudo do grafo g
		adj = G.getMatrix();
		GraphVizAPI gv = new GraphVizAPI();
		gv.addln(G.isDirected() ? gv.start_dir_graph() : gv.start_graph());
		gv.add(createCommandStringPrim(predecessor));
		gv.addln(gv.end_graph());

		// Arquivo de saida com o grafo
		File outputFile = new File(fileName + ".pdf");
		gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), "pdf"), outputFile);
		System.out.println(outputFile + " gerado com sucesso");
	}

	/**
	 * Gera o texto para criacao da imagem do grafo
	 * Por exemplo: 0->1[label =11];0->2[label =-4];1->3[label =-5];2->4[label
	 * =7];3->0[label =2];3->2[label =8];4->2[label =19];
	 * 
	 * @return retorna a String para criacao da imagem
	 * @param predecessor[]  vetor de antecessores para pintar as arestas no algoritmo Prim, pode ser NULL caso seja escolhida a opcao 8 do menu
	 */
	private static String createCommandStringPrim(int predecessor[]) {
		String command = "";

		// Tipo do grafo, direcionado ou nao
		String graphType = G.isDirected() ? "->" : "--";
		for (int lin = 0; lin < G.getNumVertices(); lin++)
			for (int col = (G.isDirected() ? 0 : lin); col < G.getNumVertices(); col++)
				if (predecessor == null) {
					if (adj[lin][col] != Algorithms.INF)
						command = command + lin + graphType + col + "[label =" + adj[lin][col] + "];";
				} else {
					if (adj[lin][col] != Algorithms.INF) {
						command += lin + graphType + col + "[label =" + adj[lin][col];
						if (predecessor[lin] == col || predecessor[col] == lin) {
							command += " style = bold color = blue";
						}
						command += "]" + ";";
					}
				}

		// System.out.println(command);
		return command;
	}

	/**
	 * Desenha o grafo g usando a lista de arestas
	 * @param fileName nome do arquivo de saida
	 * @param g 	   grafo a ser desenhado
	 * @param mst	   lista de arestas
	 */
	public static void drawGraphKruskal(String fileName, Graph g, ArrayList<Edge> mst) {
		G = g;
		// Popula matriz adjacencia com conteudo do grafo g
		adj = G.getMatrix();
		GraphVizAPI gv = new GraphVizAPI();
		gv.addln(G.isDirected() ? gv.start_dir_graph() : gv.start_graph());
		gv.add(createCommandStringKruskal(mst));
		gv.addln(gv.end_graph());

		// Arquivo de saida com o grafo
		File outputFile = new File(fileName + ".pdf");
		gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), "pdf"), outputFile);
		System.out.println(outputFile + " gerado com sucesso");
	}

	/**
	 * Cria a String com os parametros necessarios para desenho do grafo
	 * @param mst	   lista de arestas
	 */
	private static String createCommandStringKruskal(ArrayList<Edge> mst) {
		String command = "";

		// Tipo do grafo, direcionado ou nao
		String graphType = G.isDirected() ? "->" : "--";
		for (int lin = 0; lin < G.getNumVertices(); lin++) {
			for (int col = lin; col < G.getNumVertices(); col++) {
				if (adj[lin][col] != Algorithms.INF) {
					command += lin + graphType + col + "[label =" + adj[lin][col];

					Edge e = new Edge(lin, col, Algorithms.weight(lin, col, G.getAdj()));
					Edge e_ = new Edge(col, lin, Algorithms.weight(col, lin, G.getAdj()));

					if (mst.contains(e) || mst.contains(e_))
						command += " style = bold color = blue";
					command += "]" + ";";
				}
			}
		}

		// System.out.println(command);
		return command;
	}
}
