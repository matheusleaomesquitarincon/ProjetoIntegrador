package projeto;

import java.util.List;
import java.util.Scanner;

public class AtividadeFinal11 {

	private static Scanner sc = new Scanner(System.in);
	private static BancoDeDados banco = new BancoDeDados();

	private static String[] tiposDeExercicio = { "sequencial", "condicional", "repeticao", "vetor", "Java - Sequencial",
			"Java - Condicionais", "Java - Repetição", "Java - Vetores (Arrays)", "Java - Matrizes" };
	private static String[] dificuldadesDisponiveis = { "Fácil", "Médio", "Difícil", "Não definida" };
	private static String[] tiposInteracaoDisponiveis = { "DEMONSTRACAO", "RESPOSTA_UNICA", "PREVISAO_SAIDA" };

	public static void main(String[] args) {
		char repetirProgramaChar = ' ';

		System.out.print("Iniciar programa? (S para Sim / N para Não): ");
		String inputInicial = sc.nextLine().toUpperCase();
		if (!inputInicial.isEmpty() && inputInicial.charAt(0) == 'S') {
			repetirProgramaChar = 'S';
		} else {
			repetirProgramaChar = 'N';
		}

		while (repetirProgramaChar == 'S') {
			exibirMenuPrincipal();
			int opcao = lerOpcaoInteira(-1);

			switch (opcao) {
			case 1:
				resolverExercicioFluxoCompleto();
				break;
			case 2:
				buscarStatusResolucao();
				break;
			case 3:
				atualizarStatusResolucao();
				break;
			case 4:
				removerStatusResolucao();
				break;
			case 5:
				verResumoEtotalResolvidos();
				break;
			case 6:
				gerenciarDefinicoesExercicios();
				break;
			case 0:
				System.out.println("Saindo do menu principal...");
				repetirProgramaChar = 'N';
				break;
			default:
				if (opcao != -1)
					System.out.println("❌ Opção inválida. Tente novamente.");
				break;
			}

			if (opcao != 0 && repetirProgramaChar == 'S') {
				System.out.print("\nDeseja voltar ao menu principal? (S para Sim / N para Finalizar Programa): ");
				String mChoice = sc.nextLine().toUpperCase();
				if (!mChoice.isEmpty() && mChoice.charAt(0) == 'S') {
					repetirProgramaChar = 'S';
				} else {
					repetirProgramaChar = 'N';
				}
			}
		}
		System.out.println("\n✅ Programa finalizado!");
		sc.close();
	}

	private static void exibirMenuPrincipal() {
		System.out.println("\n╔═════════════════════════════════════════╗");
		System.out.println("║            MENU PRINCIPAL               ║");
		System.out.println("╠═════════════════════════════════════════╣");
		System.out.println("║ 1 - Resolver Exercício                  ║");
		System.out.println("║ 2 - Buscar Status Exercício Resolvido   ║");
		System.out.println("║ 3 - Atualizar Status Exercício Resolvido║");
		System.out.println("║ 4 - Remover Status Exercício Resolvido  ║");
		System.out.println("║ 5 - Ver Resumo e Total (Resolvidos)     ║");
		System.out.println("║ 6 - Gerenciar Definições de Exercícios  ║");
		System.out.println("║ 0 - Sair                                ║");
		System.out.println("╚═════════════════════════════════════════╝");
		System.out.print("Escolha uma opção: ");
	}

	private static int lerOpcaoInteira(int valorDefaultSeVazioOuErro) {
		try {
			String linha = sc.nextLine();
			if (linha.trim().isEmpty()) {
				return valorDefaultSeVazioOuErro;
			}
			return Integer.parseInt(linha.trim());
		} catch (NumberFormatException e) {
			System.out.println("❌ Entrada inválida. Por favor, insira um número.");
			return valorDefaultSeVazioOuErro;
		}
	}

	private static String escolherOpcaoDeLista(String[] opcoes, String tituloPrompt, String opcaoSair) {
		System.out.println("\n" + tituloPrompt);
		for (int i = 0; i < opcoes.length; i++) {
			System.out.printf("  [%d] %s%n", i, opcoes[i]);
		}
		if (opcaoSair != null && !opcaoSair.isEmpty()) {
			System.out.println("  [" + opcoes.length + "] " + opcaoSair);
		}
		System.out.print("Escolha uma opção (pelo número): ");

		int escolhaInt = lerOpcaoInteira(-1);

		if (opcaoSair != null && escolhaInt == opcoes.length) {
			return "SAIR_DA_LISTA";
		}
		if (escolhaInt >= 0 && escolhaInt < opcoes.length) {
			return opcoes[escolhaInt];
		}
		System.out.println("❌ Opção de lista inválida selecionada.");
		return null;
	}

	private static void resolverExercicioFluxoCompleto() {
		String tipoEscolhido;
		List<Exercicio> definidos;

		while (true) {
			tipoEscolhido = escolherOpcaoDeLista(tiposDeExercicio, "Escolha o TIPO de exercício:",
					"Voltar ao Menu Principal");
			if ("SAIR_DA_LISTA".equals(tipoEscolhido)) {
				System.out.println("Retornando ao Menu Principal...");
				return;
			}
			if (tipoEscolhido == null) {
				System.out.println("Por favor, tente escolher um tipo novamente.");
				continue;
			}

			definidos = banco.listarDefinicoesPorTipo(tipoEscolhido);
			if (definidos.isEmpty()) {
				System.out
						.println("ℹ Nenhum exercício do tipo '" + tipoEscolhido.toUpperCase() + "' cadastrado ainda.");
				System.out.print("Deseja tentar escolher outro TIPO de exercício? (S/N): ");
				if (!sc.nextLine().trim().equalsIgnoreCase("S")) {
					System.out.println("Retornando ao Menu Principal...");
					return;
				}
			} else {
				break;
			}
		}

		Exercicio exercicioParaResolver;
		while (true) {
			System.out.println("\n📚 Exercícios '" + tipoEscolhido.toUpperCase() + "' disponíveis:");
			for (int i = 0; i < definidos.size(); i++) {
				Exercicio ex = definidos.get(i);
				System.out.printf("  [%d] (ID: %d) N°%d (%s) - %s%n", (i + 1), ex.getId(), ex.getNumero(),
						ex.getDificuldade(),
						(ex.getEnunciado().length() > 50 ? ex.getEnunciado().substring(0, 47) + "..."
								: ex.getEnunciado()));
			}
			System.out.print("Escolha o exercício pela lista (ex: 1), ou 0 para voltar à escolha de TIPO: ");
			int escolhaLista = lerOpcaoInteira(-1);

			if (escolhaLista == 0) {
				resolverExercicioFluxoCompleto();
				return;
			}
			if (escolhaLista > 0 && escolhaLista <= definidos.size()) {
				exercicioParaResolver = definidos.get(escolhaLista - 1);
				break;
			} else {
				if (escolhaLista != -1)
					System.out.println("❌ Opção de exercício inválida. Tente novamente.");
			}
		}

		System.out.println("\n---\n▶ Exercício Selecionado: N°" + exercicioParaResolver.getNumero() + " ("
				+ exercicioParaResolver.getTipo().toUpperCase() + ")");
		System.out.println("Enunciado: " + exercicioParaResolver.getEnunciado());
		System.out.println("Dificuldade: " + exercicioParaResolver.getDificuldade());

		if (!"Sem dicas.".equalsIgnoreCase(exercicioParaResolver.getDicas()) && exercicioParaResolver.getDicas() != null
				&& !exercicioParaResolver.getDicas().trim().isEmpty()) {
			System.out.print("Deseja ver uma dica? (S/N): ");
			if (sc.nextLine().trim().equalsIgnoreCase("S")) {
				System.out.println("💡 Dica: " + exercicioParaResolver.getDicas());
			}
		}

		boolean resolvidoCorretamente = false;
		int tentativasMaximas = 3;
		int tentativasFeitas = 0;
		String tipoInteracao = exercicioParaResolver.getTipoInteracao().toUpperCase();

		switch (tipoInteracao) {
		case "RESPOSTA_UNICA":
		case "PREVISAO_SAIDA":
			while (tentativasFeitas < tentativasMaximas && !resolvidoCorretamente) {
				tentativasFeitas++;
				System.out.printf("%n--- Tentativa %d de %d ---%n", tentativasFeitas, tentativasMaximas);
				if ("PREVISAO_SAIDA".equals(tipoInteracao) && exercicioParaResolver.getInputExemplo() != null
						&& !exercicioParaResolver.getInputExemplo().trim().isEmpty()) {
					System.out
							.println("Considere a seguinte situação/input: " + exercicioParaResolver.getInputExemplo());
				}
				System.out.print("Sua resposta: ");
				String respostaUsuario = sc.nextLine().trim();

				if (exercicioParaResolver.getRespostaEsperada() != null
						&& exercicioParaResolver.getRespostaEsperada().equalsIgnoreCase(respostaUsuario)) {
					System.out.println("✅ Resposta Correta!");
					resolvidoCorretamente = true;
				} else {
					System.out.println("❌ Resposta Incorreta.");
					if (tentativasFeitas < tentativasMaximas) {
						System.out.print("Tentar novamente? (S/N): ");
						if (!sc.nextLine().trim().equalsIgnoreCase("S")) {
							break;
						}
					} else {
						System.out.println("Você esgotou suas tentativas.");
					}
				}
			}
			break;

		case "DEMONSTRACAO":
		default:
			System.out.println("\n--- Iniciando Demonstração da Solução ---");
			if (exercicioParaResolver.getTipo().equals("sequencial")
					|| exercicioParaResolver.getTipo().equals("condicional")
					|| exercicioParaResolver.getTipo().equals("repeticao")
					|| exercicioParaResolver.getTipo().equals("vetor")) {

				switch (exercicioParaResolver.getTipo()) {
				case "sequencial":
					SolucoesExercicios.sequencial(exercicioParaResolver.getNumero());
					break;
				case "condicional":
					SolucoesExercicios.condicional(exercicioParaResolver.getNumero());
					break;
				case "repeticao":
					SolucoesExercicios.repeticao(exercicioParaResolver.getNumero());
					break;
				case "vetor":
					SolucoesExercicios.vetor(exercicioParaResolver.getNumero());
					break;
				}
			} else if (exercicioParaResolver.getTipo().startsWith("Java -")) {
				System.out.println("ℹ Solução/Explicação para este exercício Java (Modo Demonstração):");
				if (exercicioParaResolver.getRespostaEsperada() != null
						&& !exercicioParaResolver.getRespostaEsperada().trim().isEmpty()) {
					System.out.println(exercicioParaResolver.getRespostaEsperada());
				} else {
					System.out.println(
							"Nenhuma demonstração ou resposta esperada detalhada cadastrada para este exercício.");
				}
			} else {
				System.out.println("❌ Tipo de exercício '" + exercicioParaResolver.getTipo()
						+ "' desconhecido para demonstração no modo atual.");
			}
			System.out.println("--- Demonstração Concluída ---");
			resolvidoCorretamente = true;
			break;
		}

		if (!"DEMONSTRACAO".equals(tipoInteracao)) {
			System.out.print("Deseja ver a resposta correta para este exercício? (S/N): ");
			if (sc.nextLine().trim().equalsIgnoreCase("S")) {
				if (exercicioParaResolver.getRespostaEsperada() != null
						&& !exercicioParaResolver.getRespostaEsperada().trim().isEmpty()) {
					System.out.println("💡 Resposta Correta: " + exercicioParaResolver.getRespostaEsperada());
				} else {
					System.out.println("ℹ Nenhuma resposta correta explícita cadastrada para este exercício.");
				}
			}
		}

		if (resolvidoCorretamente) {
			banco.salvarResolucao(exercicioParaResolver.getTipo(), exercicioParaResolver.getNumero());
		} else if (!"DEMONSTRACAO".equals(tipoInteracao)) {
			System.out.print("Deseja marcar este exercício como 'tentado/visto' mesmo não tendo acertado? (S/N): ");
			if (sc.nextLine().trim().equalsIgnoreCase("S")) {
				banco.salvarResolucao(exercicioParaResolver.getTipo(), exercicioParaResolver.getNumero());
			}
		}
		System.out.print("\nResolver outro exercício deste TIPO (" + tipoEscolhido.toUpperCase() + ")? (S/N): ");
		if (sc.nextLine().trim().equalsIgnoreCase("S")) {
			resolverExercicioFluxoCompleto();
		} else {
			System.out.println("Voltando ao menu principal...");
		}
	}

	private static void buscarStatusResolucao() {
		System.out.println("\n🔍 BUSCAR STATUS DE EXERCÍCIO RESOLVIDO");
		String tipoBusca = escolherOpcaoDeLista(tiposDeExercicio, "Escolha o TIPO do exercício:", null);
		if (tipoBusca == null)
			return;

		System.out.print("Número do exercício: ");
		int exBusca = lerOpcaoInteira(-1);
		if (exBusca <= 0 && exBusca != -1) {
			System.out.println("Número de exercício inválido.");
			return;
		}
		if (exBusca == -1)
			return;

		if (banco.buscarResolucao(tipoBusca, exBusca)) {
			System.out.println("✅ O exercício N°" + exBusca + " (" + tipoBusca + ") JÁ FOI MARCADO COMO RESOLVIDO.");
		} else {
			System.out.println("❌ O exercício N°" + exBusca + " (" + tipoBusca + ") NÃO FOI MARCADO COMO RESOLVIDO.");
		}
	}

	private static void atualizarStatusResolucao() {
		System.out.println("\n✏ ATUALIZAR STATUS DE EXERCÍCIO RESOLVIDO");
		System.out.println("--- Dados Atuais do Registro de Resolução ---");
		String tipoAntigo = escolherOpcaoDeLista(tiposDeExercicio, "Escolha o TIPO ATUAL do exercício resolvido:",
				null);
		if (tipoAntigo == null)
			return;
		System.out.print("Número do exercício ATUAL: ");
		int exAntigo = lerOpcaoInteira(-1);
		if (exAntigo <= 0 && exAntigo != -1) {
			System.out.println("Número inválido.");
			return;
		}
		if (exAntigo == -1)
			return;

		System.out.println("\n--- Novos Dados para o Registro de Resolução ---");
		String tipoNovo = escolherOpcaoDeLista(tiposDeExercicio, "Escolha o NOVO TIPO do exercício resolvido:", null);
		if (tipoNovo == null)
			return;
		System.out.print("Novo NÚMERO do exercício: ");
		int exNovo = lerOpcaoInteira(-1);
		if (exNovo <= 0 && exNovo != -1) {
			System.out.println("Número inválido.");
			return;
		}
		;
		if (exNovo == -1)
			return;

		banco.atualizarResolucao(tipoAntigo, exAntigo, tipoNovo, exNovo);
	}

	private static void removerStatusResolucao() {
		System.out.println("\n🗑 REMOVER STATUS DE EXERCÍCIO RESOLVIDO");
		String tipoRemover = escolherOpcaoDeLista(tiposDeExercicio,
				"Escolha o TIPO do exercício a ter o status removido:", null);
		if (tipoRemover == null)
			return;
		System.out.print("Número do exercício: ");
		int exRemover = lerOpcaoInteira(-1);
		if (exRemover <= 0 && exRemover != -1) {
			System.out.println("Número inválido.");
			return;
		}
		;
		if (exRemover == -1)
			return;

		System.out.print("Tem certeza que deseja remover o status de resolvido para N°" + exRemover + " (" + tipoRemover
				+ ")? (S/N): ");
		if (sc.nextLine().trim().equalsIgnoreCase("S")) {
			banco.removerResolucao(tipoRemover, exRemover);
		} else {
			System.out.println("Remoção cancelada.");
		}
	}

	private static void verResumoEtotalResolvidos() {
		banco.mostrarResumoResolvidos();
		banco.mostrarQuantidadeTotalResolvidos();
	}

	private static void gerenciarDefinicoesExercicios() {
		char repetirMenuGerenciamento = 'S';
		while (repetirMenuGerenciamento == 'S') {
			System.out.println("\n╔═════════════════════════════════════════╗");
			System.out.println("║    GERENCIAR DEFINIÇÕES DE EXERCÍCIOS   ║");
			System.out.println("╠═════════════════════════════════════════╣");
			System.out.println("║  1 - Cadastrar Nova Definição           ║");
			System.out.println("║  2 - Listar Todas as Definições         ║");
			System.out.println("║  3 - Atualizar Definição de Exercício   ║");
			System.out.println("║  4 - Remover Definição de Exercício     ║");
			System.out.println("║  0 - Voltar ao Menu Principal           ║");
			System.out.println("╚═════════════════════════════════════════╝");
			System.out.print("Escolha uma opção: ");
			int subOpcao = lerOpcaoInteira(-1);

			switch (subOpcao) {
			case 1:
				cadastrarNovaDefinicao();
				break;
			case 2:
				listarTodasDefinicoes();
				break;
			case 3:
				atualizarDefinicao();
				break;
			case 4:
				removerDefinicao();
				break;
			case 0:
				repetirMenuGerenciamento = 'N';
				break;
			default:
				if (subOpcao != -1)
					System.out.println("❌ Opção inválida.");
				break;
			}
			if (subOpcao != 0 && repetirMenuGerenciamento == 'S') {
				System.out.print("\nDeseja continuar gerenciando definições? (S/N): ");
				String mChoice = sc.nextLine().toUpperCase();
				if (!mChoice.isEmpty() && mChoice.charAt(0) == 'S') {
					repetirMenuGerenciamento = 'S';
				} else {
					repetirMenuGerenciamento = 'N';
				}
			}
		}
	}

	private static void cadastrarNovaDefinicao() {
		System.out.println("\n➕ CADASTRO DE NOVA DEFINIÇÃO DE EXERCÍCIO");
		String tipo = escolherOpcaoDeLista(tiposDeExercicio, "Escolha o TIPO do novo exercício:", null);
		if (tipo == null)
			return;

		System.out.print("Número do novo exercício (ex: 101, 201): ");
		int numero = lerOpcaoInteira(-1);
		if (numero <= 0) {
			if (numero != -1)
				System.out.println("Número inválido.");
			return;
		}

		System.out.print("Enunciado completo do novo exercício: ");
		String enunciado = sc.nextLine().trim();
		if (enunciado.isEmpty()) {
			System.out.println("Enunciado não pode ser vazio.");
			return;
		}

		String dificuldade = escolherOpcaoDeLista(dificuldadesDisponiveis, "Escolha a DIFICULDADE:", null);
		if (dificuldade == null)
			dificuldade = "Não definida";

		System.out.print("Dicas para o exercício (opcional, Enter para pular): ");
		String dicas = sc.nextLine().trim();

		String tipoInteracao = escolherOpcaoDeLista(tiposInteracaoDisponiveis, "Escolha o TIPO DE INTERAÇÃO:", null);
		if (tipoInteracao == null)
			tipoInteracao = "DEMONSTRACAO";

		String respostaEsperada = null;
		String inputExemplo = null;

		if ("RESPOSTA_UNICA".equals(tipoInteracao) || "PREVISAO_SAIDA".equals(tipoInteracao)) {
			System.out.print("Resposta/Saída Esperada para este exercício: ");
			respostaEsperada = sc.nextLine().trim();
			if (respostaEsperada.isEmpty()) {
				System.out.println("⚠ Resposta esperada não fornecida. Será cadastrada como nula.");
				respostaEsperada = null;
			}
		}
		if ("PREVISAO_SAIDA".equals(tipoInteracao)) {
			System.out.print("Input/Contexto de Exemplo (opcional, Enter para pular): ");
			inputExemplo = sc.nextLine().trim();
			if (inputExemplo.isEmpty())
				inputExemplo = null;
		}

		Exercicio novoEx = new Exercicio(numero, tipo, enunciado, dificuldade, dicas, tipoInteracao, respostaEsperada,
				inputExemplo);
		banco.cadastrarDefinicaoExercicio(novoEx);
	}

	private static void listarTodasDefinicoes() {
		System.out.println("\n📋 LISTA DE TODAS AS DEFINIÇÕES DE EXERCÍCIOS CADASTRADAS:");
		List<Exercicio> todasDefinicoes = banco.listarTodasDefinicoes();
		if (todasDefinicoes.isEmpty()) {
			System.out.println("ℹ Nenhuma definição de exercício cadastrada ainda.");
			return;
		}
		for (Exercicio ex : todasDefinicoes) {
			System.out.println(ex.toString());
		}
	}

	private static void atualizarDefinicao() {
		System.out.println("\n✏ ATUALIZAR DEFINIÇÃO DE EXERCÍCIO");
		listarTodasDefinicoes();
		System.out.print("Digite o ID do exercício que deseja atualizar (ou 0 para cancelar): ");
		int idParaAtualizar = lerOpcaoInteira(0);

		if (idParaAtualizar == 0) {
			System.out.println("Atualização cancelada.");
			return;
		}

		Exercicio exExistente = banco.buscarDefinicaoPorId(idParaAtualizar);
		if (exExistente == null) {
			System.out.println("❌ Exercício com ID " + idParaAtualizar + " não encontrado.");
			return;
		}

		System.out.println("\nEditando Exercício ID: " + exExistente.getId()
				+ " (Deixe em branco e pressione Enter para manter o valor atual, exceto para escolhas de lista)");

		String tipoNovo = escolherOpcaoDeLista(tiposDeExercicio, "Novo TIPO (" + exExistente.getTipo() + "):",
				"Manter atual");
		if (tipoNovo != null && !"SAIR_DA_LISTA".equals(tipoNovo))
			exExistente.setTipo(tipoNovo);

		System.out.print("Novo NÚMERO (" + exExistente.getNumero() + "): ");
		String numStr = sc.nextLine().trim();
		if (!numStr.isEmpty()) {
			try {
				exExistente.setNumero(Integer.parseInt(numStr));
			} catch (NumberFormatException e) {
				System.out.println("Número inválido, mantendo o anterior.");
			}
		}

		System.out.print("Novo ENUNCIADO (Atual: \""
				+ exExistente.getEnunciado().substring(0, Math.min(exExistente.getEnunciado().length(), 30))
				+ "...\"): ");
		String enunciadoNovo = sc.nextLine().trim();
		if (!enunciadoNovo.isEmpty())
			exExistente.setEnunciado(enunciadoNovo);

		String dificuldadeNova = escolherOpcaoDeLista(dificuldadesDisponiveis,
				"Nova DIFICULDADE (" + exExistente.getDificuldade() + "):", "Manter atual");
		if (dificuldadeNova != null && !"SAIR_DA_LISTA".equals(dificuldadeNova))
			exExistente.setDificuldade(dificuldadeNova);

		System.out.print("Novas DICAS (Atual: \""
				+ exExistente.getDicas().substring(0, Math.min(exExistente.getDicas().length(), 30)) + "...\"): ");
		String dicasNovas = sc.nextLine();
		if (!dicasNovas.isEmpty() || (dicasNovas.isEmpty() && !"Sem dicas.".equals(exExistente.getDicas()))) {
			exExistente.setDicas(dicasNovas);
		}

		String tipoInteracaoNovo = escolherOpcaoDeLista(tiposInteracaoDisponiveis,
				"Novo TIPO DE INTERAÇÃO (" + exExistente.getTipoInteracao() + "):", "Manter atual");
		if (tipoInteracaoNovo != null && !"SAIR_DA_LISTA".equals(tipoInteracaoNovo))
			exExistente.setTipoInteracao(tipoInteracaoNovo);

		String respostaEsperadaAtual = exExistente.getRespostaEsperada() != null ? exExistente.getRespostaEsperada()
				: "";
		String inputExemploAtual = exExistente.getInputExemplo() != null ? exExistente.getInputExemplo() : "";

		if ("RESPOSTA_UNICA".equals(exExistente.getTipoInteracao())
				|| "PREVISAO_SAIDA".equals(exExistente.getTipoInteracao())) {
			System.out.print("Nova RESPOSTA/SAÍDA ESPERADA (" + respostaEsperadaAtual + "): ");
			String respNovaStr = sc.nextLine().trim();
			if (!respNovaStr.isEmpty())
				exExistente.setRespostaEsperada(respNovaStr);
		} else {
			exExistente.setRespostaEsperada(null);
		}

		if ("PREVISAO_SAIDA".equals(exExistente.getTipoInteracao())) {
			System.out.print("Novo INPUT/CONTEXTO DE EXEMPLO (" + inputExemploAtual + "): ");
			String inputNovoStr = sc.nextLine().trim();
			if (!inputNovoStr.isEmpty())
				exExistente.setInputExemplo(inputNovoStr);
		} else {
			exExistente.setInputExemplo(null);
		}

		banco.atualizarDefinicaoExercicio(exExistente);
	}

	private static void removerDefinicao() {
		System.out.println("\n🗑 REMOVER DEFINIÇÃO DE EXERCÍCIO");
		listarTodasDefinicoes();
		System.out.print("Digite o ID do exercício que deseja remover (ou 0 para cancelar): ");
		int idParaRemover = lerOpcaoInteira(0);

		if (idParaRemover == 0) {
			System.out.println("Remoção cancelada.");
			return;
		}

		Exercicio exParaRemover = banco.buscarDefinicaoPorId(idParaRemover);
		if (exParaRemover == null) {
			System.out.println("❌ Exercício com ID " + idParaRemover + " não encontrado.");
			return;
		}

		System.out.print("Tem certeza que deseja remover a definição do exercício ID " + idParaRemover + " (N°"
				+ exParaRemover.getNumero() + " - " + exParaRemover.getTipo() + ")? (S/N): ");
		if (sc.nextLine().trim().equalsIgnoreCase("S")) {
			banco.removerDefinicaoExercicio(idParaRemover);
		} else {
			System.out.println("Remoção cancelada.");
		}
	}
}