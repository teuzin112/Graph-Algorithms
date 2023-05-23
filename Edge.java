import java.util.Objects;

public class Edge {
	public int src;
	public int dst;
	public int weight;

	/**
	 * Construtor da classe Edge
	 * @param src 	 Vertice de origem
	 * @param dst 	 Vertice de destino
	 * @param weight Peso da aresta
	 */
	public Edge(int src, int dst, int weight) {
		this.src = src;
		this.dst = dst;
		this.weight = weight;
	}

	/**
	 * Override do metodo equals, compara dois objetos Edge
	 * @param o Objeto a ser comparado
	 * @return retorna true caso os dois objetos sejam iguais, caso contrario false
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Edge edge = (Edge) o;
		return src == edge.src &&
				dst == edge.dst &&
				weight == edge.weight;
	}

	/**
	 * Override do metodo hashCode, gera um hash do objeto para ser usado em HashMap ou HashSet
	 * @return retorna codigo hash gerado
	 */
	@Override
	public int hashCode() {
		return Objects.hash(src, dst, weight);
	}

}
