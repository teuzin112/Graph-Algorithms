import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
	private static Graph G;
	// Como o arquivo pode ser alterado durante a execucao, deve ser salvo para
	// gerar a imagem
	private static String actualFileName;

	/**
	 * Metodo para pegar o caminho completo para o arquivo
	 * 
	 * @param consoleInput input do teclado
	 * @return retorna a string com o caminho para o arquivo
	 */
	public static String getFilePath(Scanner consoleInput) {
		System.out.println("Complete file path with name: ");
		String fileName = consoleInput.nextLine();
		// Pega o caminho completo ate a classe Graph
		String path = Graph.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		fileName = path.replace("bin/", "src/") + fileName;
		return fileName;
	}

	/**
	 * Menu principal do programa, fica em looping ate o usuario digitar 0
	 * 
	 * @param consoleInput input do teclado
	 * @throws IOException
	 */
	public static void drawMenu(Scanner consoleInput) throws IOException {
		while (true) {
			System.out.println("\n[1] - DFS");
			System.out.println("[2] - BFS");
			System.out.println("[3] - Dijkstra");
			System.out.println("[4] - Bellman-Ford");
			System.out.println("[5] - Floyd-Warshall");
			System.out.println("[6] - Kruskal");
			System.out.println("[7] - Prim");
			System.out.println("[8] - Draw graph");
			System.out.println("[9] - Change graph");
			System.out.println("[0] - Exit");
			System.out.print("Option: ");
			int opt = consoleInput.nextInt();
			if (opt == 0)
				break;
			// Funcao auxiliar para redirecionar para cada metodo especifico
			redirectToAction(opt, consoleInput);
		}
	}

	/**
	 * Redireciona para os metodos especificos dado o input do usuario
	 * 
	 * @param option       Opcao escolhida pelo usuario de 1 a 9
	 * @param consoleInput input do teclado
	 * @throws IOException
	 */
	public static void redirectToAction(Integer option, Scanner consoleInput) throws IOException {
		Integer src = 0;
		// Alguns algoritmos precisam de um ponto inicial, se for algum deles lê o src
		if (option > 0 && option < 5 || option == 7) {
			System.out.print("Source vertex: ");
			src = consoleInput.nextInt();
		}
		switch (option) {
			case 1:
				Algorithms.dfs(G, src);
				break;

			case 2:
				Algorithms.bfs(G, src);
				getFilePath(consoleInput);
				break;

			case 3:
				Algorithms.dijkstra(G, src);
				break;

			case 4:
				if (Algorithms.bellmanFord(G, src))
					Algorithms.printPaths(G, src);
				break;

			case 5:
				Algorithms.floydWarshall(G);
				break;

			case 6:

				ArrayList<Edge> edges = Algorithms.kruskal(G);
				GraphDrawing.drawGraphKruskal(actualFileName.substring(0, actualFileName.length() - 4) + "KRUS", G, edges);
				break;

			case 7:
				Algorithms.prim(G, src);
				GraphDrawing.drawGraphPrim(actualFileName.substring(0, actualFileName.length() - 4) + "PRIM", G,
						Algorithms.predecessor);
				break;

			case 8:
				GraphDrawing.drawGraphPrim(actualFileName.substring(0, actualFileName.length() - 4), G, null);
				break;

			case 9:
				consoleInput.nextLine(); // Limpa o buffer do teclado
				actualFileName = getFilePath(consoleInput);
				G = Graph.loadGraph(actualFileName);
				break;
		}
	}

	public static void main(String[] args) throws IOException {
		Scanner consoleInput = new Scanner(System.in);
		G = null;
		// Pega o caminho do arquivo
		actualFileName = getFilePath(consoleInput);
		G = Graph.loadGraph(actualFileName);
		drawMenu(consoleInput);
		consoleInput.close();
		System.out.println("Exiting...");
	}

}
