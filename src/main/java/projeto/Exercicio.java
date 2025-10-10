package main.java.MySQLConnector;

public class Exercicio {
	private int id;
	private int numero;
	private String tipo;
	private String enunciado;
	private String dificuldade;
	private String dicas;
	private String tipoInteracao;
	private String respostaEsperada;
	private String inputExemplo;

	public Exercicio(int id, int numero, String tipo, String enunciado, String dificuldade, String dicas,
			String tipoInteracao, String respostaEsperada, String inputExemplo) {
		this.id = id;
		this.numero = numero;
		this.tipo = tipo;
		this.enunciado = enunciado;
		this.dificuldade = (dificuldade == null || dificuldade.trim().isEmpty()) ? "Não definida" : dificuldade.trim();
		this.dicas = (dicas == null || dicas.trim().isEmpty()) ? "Sem dicas." : dicas.trim();
		this.tipoInteracao = (tipoInteracao == null || tipoInteracao.trim().isEmpty()) ? "DEMONSTRACAO"
				: tipoInteracao.trim().toUpperCase();
		this.respostaEsperada = respostaEsperada;
		this.inputExemplo = inputExemplo;
	}

	public Exercicio(int numero, String tipo, String enunciado, String dificuldade, String dicas, String tipoInteracao,
			String respostaEsperada, String inputExemplo) {
		this(0, numero, tipo, enunciado, dificuldade, dicas, tipoInteracao, respostaEsperada, inputExemplo);
	}

	public int getId() {
		return id;
	}

	public int getNumero() {
		return numero;
	}

	public String getTipo() {
		return tipo;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public String getDificuldade() {
		return dificuldade;
	}

	public String getDicas() {
		return dicas;
	}

	public String getTipoInteracao() {
		return tipoInteracao;
	}

	public String getRespostaEsperada() {
		return respostaEsperada;
	}

	public String getInputExemplo() {
		return inputExemplo;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public void setDificuldade(String dificuldade) {
		this.dificuldade = (dificuldade == null || dificuldade.trim().isEmpty()) ? "Não definida" : dificuldade.trim();
	}

	public void setDicas(String dicas) {
		this.dicas = (dicas == null || dicas.trim().isEmpty()) ? "Sem dicas." : dicas.trim();
	}

	public void setTipoInteracao(String tipoInteracao) {
		this.tipoInteracao = (tipoInteracao == null || tipoInteracao.trim().isEmpty()) ? "DEMONSTRACAO"
				: tipoInteracao.trim().toUpperCase();
	}

	public void setRespostaEsperada(String respostaEsperada) {
		this.respostaEsperada = respostaEsperada;
	}

	public void setInputExemplo(String inputExemplo) {
		this.inputExemplo = inputExemplo;
	}

	public String toString() {
		return String.format("ID: %d | N°: %d (%s) | Enunciado: %s | Dificuldade: %s | Interação: %s", id, numero, tipo,
				(enunciado.length() > 40 ? enunciado.substring(0, 37) + "..." : enunciado), dificuldade, tipoInteracao);
	}
}
