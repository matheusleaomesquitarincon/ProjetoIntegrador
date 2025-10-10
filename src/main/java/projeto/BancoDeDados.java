package main.java.projeto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BancoDeDados {

	private static final String URL = "jdbc:mysql://localhost:3306/Atividadefinal11";
	private static final String USER = "root";
	private static final String PASSWORD = "Kxique06*";

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	private Exercicio criarExercicioDeResultSet(ResultSet rs) throws SQLException {
		return new Exercicio(rs.getInt("id"), rs.getInt("numero_exercicio"), rs.getString("tipo_exercicio"),
				rs.getString("enunciado_exercicio"), rs.getString("dificuldade"), rs.getString("dicas"),
				rs.getString("tipo_interacao"), rs.getString("resposta_esperada"), rs.getString("input_exemplo"));
	}

	public boolean cadastrarDefinicaoExercicio(Exercicio exercicio) {
		String sql = "INSERT INTO definicoes_exercicios (numero_exercicio, tipo_exercicio, enunciado_exercicio, dificuldade, dicas, tipo_interacao, resposta_esperada, input_exemplo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setInt(1, exercicio.getNumero());
			stmt.setString(2, exercicio.getTipo());
			stmt.setString(3, exercicio.getEnunciado());
			stmt.setString(4, exercicio.getDificuldade());
			stmt.setString(5, exercicio.getDicas());
			stmt.setString(6, exercicio.getTipoInteracao());
			stmt.setString(7, exercicio.getRespostaEsperada());
			stmt.setString(8, exercicio.getInputExemplo());

			int affectedRows = stmt.executeUpdate();
			if (affectedRows > 0) {
				try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						exercicio.setId(generatedKeys.getInt(1));
					}
				}
				System.out.println("✅ Definição do exercício N°" + exercicio.getNumero() + " (" + exercicio.getTipo()
						+ ") cadastrada com sucesso!");
				return true;
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
				System.err.println("❌ Erro ao cadastrar: Já existe um exercício do tipo '" + exercicio.getTipo()
						+ "' com o número " + exercicio.getNumero() + ".");
			} else {
				System.err.println("❌ Erro SQL ao cadastrar definição do exercício: " + e.getMessage());
			}
		}
		return false;
	}

	public List<Exercicio> listarDefinicoesPorTipo(String tipo) {
		List<Exercicio> exercicios = new ArrayList<>();
		String sql = "SELECT * FROM definicoes_exercicios WHERE tipo_exercicio = ? ORDER BY numero_exercicio ASC";

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, tipo);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				exercicios.add(criarExercicioDeResultSet(rs));
			}
		} catch (SQLException e) {
			System.err.println("❌ Erro ao listar definições de exercícios por tipo '" + tipo + "': " + e.getMessage());
		}
		return exercicios;
	}

	public List<Exercicio> listarTodasDefinicoes() {
		List<Exercicio> exercicios = new ArrayList<>();
		String sql = "SELECT * FROM definicoes_exercicios ORDER BY tipo_exercicio, numero_exercicio ASC";
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				exercicios.add(criarExercicioDeResultSet(rs));
			}
		} catch (SQLException e) {
			System.err.println("❌ Erro ao listar todas as definições de exercícios: " + e.getMessage());
		}
		return exercicios;
	}

	public Exercicio buscarDefinicaoPorId(int id) {
		String sql = "SELECT * FROM definicoes_exercicios WHERE id = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return criarExercicioDeResultSet(rs);
			}
		} catch (SQLException e) {
			System.err.println("❌ Erro ao buscar definição do exercício por ID " + id + ": " + e.getMessage());
		}
		return null;
	}

	public boolean atualizarDefinicaoExercicio(Exercicio exercicio) {
		String sql = "UPDATE definicoes_exercicios SET numero_exercicio = ?, tipo_exercicio = ?, enunciado_exercicio = ?, "
				+ "dificuldade = ?, dicas = ?, tipo_interacao = ?, resposta_esperada = ?, input_exemplo = ? "
				+ "WHERE id = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, exercicio.getNumero());
			stmt.setString(2, exercicio.getTipo());
			stmt.setString(3, exercicio.getEnunciado());
			stmt.setString(4, exercicio.getDificuldade());
			stmt.setString(5, exercicio.getDicas());
			stmt.setString(6, exercicio.getTipoInteracao());
			stmt.setString(7, exercicio.getRespostaEsperada());
			stmt.setString(8, exercicio.getInputExemplo());
			stmt.setInt(9, exercicio.getId());

			int affectedRows = stmt.executeUpdate();
			if (affectedRows > 0) {
				System.out.println("✅ Definição do exercício ID " + exercicio.getId() + " atualizada com sucesso!");
				return true;
			} else {
				System.out.println("ℹ Nenhuma definição de exercício encontrada com o ID " + exercicio.getId()
						+ " para atualizar.");
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
				System.err.println(
						"❌ Erro ao atualizar: Violação de restrição de unicidade (provavelmente tipo e número duplicados para outro ID).");
			} else {
				System.err.println("❌ Erro SQL ao atualizar definição do exercício ID " + exercicio.getId() + ": "
						+ e.getMessage());
			}
		}
		return false;
	}

	public boolean removerDefinicaoExercicio(int idExercicio) {
		String sql = "DELETE FROM definicoes_exercicios WHERE id = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, idExercicio);
			int affectedRows = stmt.executeUpdate();
			if (affectedRows > 0) {
				System.out.println("🗑 Definição do exercício ID " + idExercicio + " removida com sucesso!");
				return true;
			} else {
				System.out.println(
						"ℹ Nenhuma definição de exercício encontrada com o ID " + idExercicio + " para remover.");
			}
		} catch (SQLException e) {
			System.err
					.println("❌ Erro SQL ao remover definição do exercício ID " + idExercicio + ": " + e.getMessage());
		}
		return false;
	}

	public void salvarResolucao(String tipo, int numeroExercicio) {
		String sql = "INSERT INTO exercicios_resolvidos (tipo, numero_exercicio, data_resolucao) VALUES (?, ?, CURRENT_TIMESTAMP) "
				+ "ON DUPLICATE KEY UPDATE data_resolucao = CURRENT_TIMESTAMP";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, tipo);
			stmt.setInt(2, numeroExercicio);

			stmt.executeUpdate();
			System.out.println(
					"✅ Exercício N°" + numeroExercicio + " (" + tipo + ") status de resolução SALVO/ATUALIZADO!");

		} catch (SQLException e) {
			System.err.println("❌ Erro CRÍTICO ao salvar status de resolução do exercício N°" + numeroExercicio + " ("
					+ tipo + "): " + e.getMessage());
			System.err.println(
					"   Certifique-se de que a tabela 'exercicios_resolvidos' existe e possui uma CHAVE PRIMÁRIA ou ÚNICA em (tipo, numero_exercicio).");
		}
	}

	public boolean buscarResolucao(String tipo, int numeroExercicio) {
		String sql = "SELECT COUNT(*) AS total FROM exercicios_resolvidos WHERE tipo = ? AND numero_exercicio = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, tipo);
			stmt.setInt(2, numeroExercicio);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("total") > 0;
			}
		} catch (SQLException e) {
			System.err.println("❌ Erro ao buscar status de resolução para N°" + numeroExercicio + " (" + tipo + "): "
					+ e.getMessage());
		}
		return false;
	}

	public void atualizarResolucao(String tipoAntigo, int exAntigo, String tipoNovo, int exNovo) {
		String sql = "UPDATE exercicios_resolvidos SET tipo = ?, numero_exercicio = ?, data_resolucao = CURRENT_TIMESTAMP "
				+ "WHERE tipo = ? AND numero_exercicio = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, tipoNovo);
			stmt.setInt(2, exNovo);
			stmt.setString(3, tipoAntigo);
			stmt.setInt(4, exAntigo);
			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("✅ Status de resolução atualizado de N°" + exAntigo + " (" + tipoAntigo + ") para N°"
						+ exNovo + " (" + tipoNovo + ").");
			} else {
				System.out.println("ℹ Status de resolução para N°" + exAntigo + " (" + tipoAntigo
						+ ") não encontrado para atualização.");
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
				System.err.println("❌ Erro ao atualizar status de resolução: A nova combinação tipo/número ( "
						+ tipoNovo + "/" + exNovo + " ) já existe.");
			} else {
				System.err.println("❌ Erro SQL ao atualizar status de resolução: " + e.getMessage());
			}
		}
	}

	public void removerResolucao(String tipo, int numeroExercicio) {
		String sql = "DELETE FROM exercicios_resolvidos WHERE tipo = ? AND numero_exercicio = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, tipo);
			stmt.setInt(2, numeroExercicio);
			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("🗑 Status de resolução do exercício N°" + numeroExercicio + " (" + tipo
						+ ") removido com sucesso!");
			} else {
				System.out.println("ℹ Status de resolução do N°" + numeroExercicio + " (" + tipo
						+ ") não encontrado para remoção.");
			}
		} catch (SQLException e) {
			System.err.println("❌ Erro SQL ao remover status de resolução: " + e.getMessage());
		}
	}

	public void mostrarResumoResolvidos() {
		System.out.println("\n📊 RESUMO DE EXERCÍCIOS MARCADOS COMO RESOLVIDOS");
		String sql = "SELECT tipo, GROUP_CONCAT(numero_exercicio ORDER BY numero_exercicio ASC SEPARATOR ', ') AS numeros_resolvidos "
				+ "FROM exercicios_resolvidos GROUP BY tipo ORDER BY tipo ASC";
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			boolean found = false;
			while (rs.next()) {
				found = true;
				String tipo = rs.getString("tipo");
				String exerciciosStr = rs.getString("numeros_resolvidos");
				System.out.println("  ‣ " + tipo.substring(0, 1).toUpperCase() + tipo.substring(1) + ": "
						+ (exerciciosStr != null ? exerciciosStr : "Nenhum"));
			}
			if (!found) {
				System.out.println("  ℹ Nenhum exercício marcado como resolvido ainda.");
			}
		} catch (SQLException e) {
			System.err.println("❌ Erro ao mostrar resumo de exercícios resolvidos: " + e.getMessage());
		}
	}

	public void mostrarQuantidadeTotalResolvidos() {
		String sql = "SELECT COUNT(*) AS total_resolvidos FROM exercicios_resolvidos";
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			if (rs.next()) {
				System.out.println("🔢 Total de registros de exercícios resolvidos: " + rs.getInt("total_resolvidos"));
			}
		} catch (SQLException e) {
			System.err.println("❌ Erro ao mostrar quantidade total de exercícios resolvidos: " + e.getMessage());
		}
	}
}