/**
 * É só preencher o arquivo origem.xlsx com os campos que já existem e rodar a importação
 * No caso de pessoas cedidas, tem que colocar CEDIDO CHEFE e a matricula da pessoa no campo LOTACAO 
 * veja exemplo no final do arquivo
 *  
 */

package br.gov;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EmaterApplication {

	private static List<Chefia> chefiaList = new ArrayList<>();

	private static Map<String, Integer> formularioMap = new HashMap<>();

	private static Date inicio;

	private static Integer patraoId;

	private static Integer relacionadoFuncaoId;

	private static Integer relacionadorFuncaoId;

	private static Integer relacionamentoId;

	private static Date termino;

	public static List<UO> uoList = new ArrayList<>();

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		uoList.add(new UO(1, "PRESIDÊNCIA", "PRESI", null));
		uoList.add(new UO(2, "GABINETE", "GABIN", 1));
		uoList.add(new UO(3, "ASSESSORIA JURÍDICA", "ASJUR", 1));
		uoList.add(new UO(4, "OUVIDORIA", "OUVID", 1));
		uoList.add(new UO(5, "CONTROLE INTERNO", "CONIN", 1));
		uoList.add(new UO(6, "ASSESSORIA ESPECIAL DA PRESIDÊNCIA", "ASPRE", 1));
		uoList.add(new UO(7, "ASSESSORIA DE COMUNICAÇÃO", "ASCOM", 1));
		uoList.add(new UO(8, "DIRETORIA EXECUTIVA", "DIREX", 1));
		uoList.add(new UO(9, "ASSESSORIA DA DIRETORIA", "ASDIR", 8));
		uoList.add(new UO(10, "COORDENADORIA DE GESTÃO E MODERNIZAÇÃO", "COGEM", 8));
		uoList.add(new UO(11, "Gerência de Desenvolvimento Institucional", "GEDIN", 10));
		uoList.add(new UO(12, "Gerência de Programação Orçamentária", "GEPRO", 10));
		uoList.add(new UO(13, "Gerência de Tecnologia da Informação", "GETIN", 10));
		uoList.add(new UO(14, "Gerência de Contratos e Convênios", "GCONV", 10));
		uoList.add(new UO(15, "COORDENADORIA DE OPERAÇÕES", "COPER", 8));
		uoList.add(new UO(16, "Gerência de Desenvolvimento Econômico Rural", "GEDEC", 15));
		uoList.add(new UO(17, "Gerência de Meio Ambiente", "GEAMB", 15));
		uoList.add(new UO(18, "Gerência de Desenvolvimento Sócio Familiar", "GEDES", 15));
		uoList.add(new UO(19, "Gerência de Desenvolvimento Agropecuário", "GEAGR", 15));
		uoList.add(new UO(20, "Gerência de Metodologia e Comunicação Rural", "GEMEC", 15));
		uoList.add(new UO(21, "Gerência do Centro de Capacitação", "CENTRER", 15));
		uoList.add(new UO(22, "Escritório Especializado em Comercialização", "ESCOM", 15));
		uoList.add(new UO(23, "Gerência de Projetos Especiais", "GEPRE", 15));
		uoList.add(new UO(24, "REGIONAL OESTE", "UREO", 15));
		uoList.add(new UO(25, "Alexandre Gusmão", "ELALG", 24));
		uoList.add(new UO(26, "Brazlândia", "ELBRA", 24));
		uoList.add(new UO(27, "Ceilândia", "ELCEI", 24));
		uoList.add(new UO(28, "Gama", "ELGAM", 24));
		uoList.add(new UO(29, "São Sebastião", "ELSEB", 24));
		uoList.add(new UO(30, "Sobradinho", "ELSOB", 24));
		uoList.add(new UO(31, "Vargem Bonita", "ELVAB", 24));
		uoList.add(new UO(32, "EP NOROESTE Projetos Especiais Noroeste", "PE.BERNARDO", 24));
		uoList.add(new UO(33, "REGIONAL LESTE", "URLE", 15));
		uoList.add(new UO(34, "Jardim", "ELJAR", 33));
		uoList.add(new UO(35, "PAD/DF", "ELPAD", 33));
		uoList.add(new UO(36, "Paranoá", "ELPAR", 33));
		uoList.add(new UO(37, "Pipiripau", "ELPIP", 33));
		uoList.add(new UO(38, "Planaltina", "ELPLA", 33));
		uoList.add(new UO(39, "Rio Preto", "ELRIP", 33));
		uoList.add(new UO(40, "Tabatinga", "ELTAB", 33));
		uoList.add(new UO(41, "Taquara", "ELTAQ", 33));
		uoList.add(new UO(42, "Projetos Especiais Leste", "FORMOSA", 33));
		uoList.add(new UO(43, "Projetos Especiais Norte", "PIPIRIPAU", 33));
		uoList.add(new UO(44, "COORDENADORIA DE ADM E FINANÇAS", "COAFI", 8));
		uoList.add(new UO(45, "Gerência de Contabilidade", "GECON", 44));
		uoList.add(new UO(46, "Gerência de Finanças", "GEFIN", 44));
		uoList.add(new UO(47, "Gerência de Infraestrutura", "GINFR", 44));
		uoList.add(new UO(48, "Gerência de Material e Patrimônio", "GEMAP", 44));
		uoList.add(new UO(49, "Gerência de Pessoal", "GEPES", 44));
		uoList.add(new UO(50, "Gerência de Pessoal CEDIDOS", "CEDIDOS", 44));

		chefiaList.add(new Chefia("CHEFE DA ASSESSORIA JURIDICA"));
		chefiaList.add(new Chefia("CHEFE DA COMUNICACAO SOCIAL"));
		chefiaList.add(new Chefia("CHEFE DE GABINETE"));
		chefiaList.add(new Chefia("CONTROLADOR"));
		chefiaList.add(new Chefia("COORDENADOR"));
		chefiaList.add(new Chefia("DIRETOR"));
		chefiaList.add(new Chefia("GERENTE"));
		chefiaList.add(new Chefia("OUVIDOR"));
		chefiaList.add(new Chefia("PRESIDENTE"));
		chefiaList.add(new Chefia("SUPERVISOR REGIONAL"));

	}

	@SuppressWarnings("rawtypes")
	private static List<Registro> abrirArquivoExcel() throws Exception {
		List<Registro> result = new ArrayList<>();

		try (FileInputStream file = new FileInputStream(new File("./origem.xlsx"));) {

			XSSFWorkbook wb = new XSSFWorkbook(file);
			XSSFSheet sheet = wb.getSheetAt(0);
			Iterator rowIterator = sheet.rowIterator();

			while (rowIterator.hasNext()) {
				Row row = (Row) rowIterator.next();

				// Descantando a primeira linha com o header
				if (row.getRowNum() == 0) {
					continue;
				}

				Iterator cellIterator = row.cellIterator();
				Registro registro = new Registro();
				result.add(registro);
				while (cellIterator.hasNext()) {
					Cell cell = (Cell) cellIterator.next();
					switch (cell.getColumnIndex()) {
					case 0: // numero
						registro.setNumero((int) Double.valueOf(cell.getNumericCellValue()).doubleValue());
						break;
					case 1: // matricula
						registro.setMatricula(cell.getCellType() == Cell.CELL_TYPE_STRING ? cell.getStringCellValue()
								: "" + Double.valueOf(cell.getNumericCellValue()).intValue());
						break;
					case 2: // nome
						registro.setNome(cell.getStringCellValue());
						break;
					case 3: // cargo
						registro.setCargo(cell.getStringCellValue());
						break;
					case 4: // lotacao
						registro.setLotacao(cell.getStringCellValue());
						break;
					case 5: // funcao
						registro.setFuncao(cell.getStringCellValue());
						break;
					case 6: // refFuncional
						registro.setRefFuncional(cell.getStringCellValue());
						break;
					case 7: // nivelSalarialAtual
						registro.setNivelSalarialAtual(cell.getStringCellValue());
						break;
					case 8: // dataAdmissao
						Calendar d = Calendar.getInstance();
						d.setTime(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
						registro.setDataAdmissao(d);
						break;
					case 9: // status
						registro.setStatus(cell.getStringCellValue());
						break;
					case 10: // grupoAvaliacao
						registro.setGrupoAvaliacao(cell.getStringCellValue());
						break;
					}
				}
			}
		}

		return result;
	}

	public static Registro getChefeUo(List<Registro> registroList, UO uo) {

		if (uo.getSigla().equalsIgnoreCase("ASDIR")) {
			uo = getUO("DIREX").get().get(0);
		}

		for (Registro registro : registroList) {
			if (registro.getUo() != null && registro.getUo().getId().equals(uo.getId())) {
				List<Chefia> chefiaList = getChefia(registro.getFuncao());
				if (chefiaList != null && chefiaList.size() == 1) {
					return registro;
				}
			}
		}

		return null;
	}

	public static List<Chefia> getChefia(String nome) {
		List<Chefia> result = null;
		for (Chefia chefia : chefiaList) {
			if (nome.toLowerCase().contains(chefia.getNome().toLowerCase())) {
				if (result == null) {
					result = new ArrayList<>();
				}
				result.add(chefia);
			}
		}
		return result;
	}

	private static String getComplemento(Registro registro, boolean chefe) {
		StringBuilder result = new StringBuilder();
		if (chefe && registro.getLotacao().equalsIgnoreCase("CEDIDOS - GERENCIA DE PESSOAL")) {
			result.append("chefe (");
			result.append(getChefeCedido(registro.getMatricula()).getNome());
			result.append(") avalia servidor CEDIDO (");
			result.append(registro.getMatricula());
			result.append(", ");
			result.append(registro.getEmpregadoNome());
			result.append(") ");
		} else if (chefe && registro.isChefeDaUO()) {
			result.append("uo(");
			result.append(registro.getUoSuperior() != null ? registro.getUoSuperior().getSigla()
					: registro.getUo().getSigla());
			result.append(") avalia gestor da (");
			result.append(registro.getUo().getSigla());
			result.append(", ");
			result.append(registro.getMatricula());
			result.append(", ");
			result.append(registro.getEmpregadoNome());
			result.append(") ");
		} else {
			result.append("uo(");
			result.append(registro.getUo().getSigla());
			result.append(") matricula (");
			result.append(registro.getMatricula());
			result.append(") ");
			result.append(registro.getEmpregadoNome());
		}
		return result.toString();
	}

	private static Registro getChefeCedido(String matricula) {
		for (Registro chefeCedido : registroList) {
			if (chefeCedido.getLotacao().equalsIgnoreCase("CEDIDO CHEFE " + matricula)) {
				return chefeCedido;
			}
		}
		return null;
	}

	private static int getFormularioId(Connection con, String grupoAvaliacao) throws Exception {
		if (!formularioMap.containsKey(grupoAvaliacao)) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT id").append("\n");
			sql.append("FROM enquete.formulario").append("\n");
			sql.append("WHERE codigo = ?").append("\n");
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setString(1, grupoAvaliacao);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				formularioMap.put(grupoAvaliacao, rs.getInt(1));
			} else {
				throw new RuntimeException("formulario não encontrado " + grupoAvaliacao);
			}
		}

		return formularioMap.get(grupoAvaliacao);
	}

	private static Date getInicio() {
		if (inicio == null) {
			Calendar c = Calendar.getInstance();
			c.set(2018, 9, 1);
			inicio = new Date(c.getTimeInMillis());
		}
		return inicio;
	}

	private static String getNomeUsuario(Connection con, String nome) throws Exception {
		StringBuilder result = null;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT nome_usuario FROM sistema.usuario WHERE nome_usuario = ?");
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ResultSet rs = null;

		String[] nomes = nome.trim().toLowerCase().split("(\\s)+");
		int pos = nomes.length - 1;
		do {
			result = new StringBuilder();
			result.append(nomes[0].trim());
			result.append(".");
			result.append(nomes[pos--].trim());
			ps.setString(1, result.toString());
			rs = ps.executeQuery();
		} while (rs.next());

		return result.toString();
	}

	private static int getPatraoId(Connection con) throws Exception {
		if (patraoId == null) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT e.id").append("\n");
			sql.append("FROM funcional.empregador e").append("\n");
			sql.append("JOIN pessoa.pessoa p").append("\n");
			sql.append("ON p.id = e.id").append("\n");
			sql.append("WHERE apelido_sigla = 'EMATER-DF'").append("\n");
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			rs.next();
			patraoId = rs.getInt(1);
		}
		return patraoId;
	}

	private static Integer getRelacionadoFuncaoId(Connection con) throws Exception {
		if (relacionadoFuncaoId == null) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT relacionador_funcao_id").append("\n");
			sql.append("    , relacionado_funcao_id ").append("\n");
			sql.append("FROM  pessoa.relacionamento_configuracao a").append("\n");
			sql.append("JOIN  pessoa.relacionamento_funcao rf").append("\n");
			sql.append("ON    a.relacionador_funcao_id = rf.id").append("\n");
			sql.append("WHERE relacionamento_tipo_id = ?").append("\n");
			sql.append("AND   rf.participacao = 'A'").append("\n");
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setInt(1, getRelacionamentoId(con));
			ResultSet rs = ps.executeQuery();
			rs.next();
			relacionadoFuncaoId = rs.getInt(2);
		}
		return relacionadoFuncaoId;
	}

	private static Integer getRelacionadorFuncaoId(Connection con) throws Exception {
		if (relacionadorFuncaoId == null) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT relacionador_funcao_id").append("\n");
			sql.append("    , relacionado_funcao_id ").append("\n");
			sql.append("FROM  pessoa.relacionamento_configuracao a").append("\n");
			sql.append("JOIN  pessoa.relacionamento_funcao rf").append("\n");
			sql.append("ON    a.relacionador_funcao_id = rf.id").append("\n");
			sql.append("WHERE relacionamento_tipo_id = ?").append("\n");
			sql.append("AND   rf.participacao = 'A'").append("\n");
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setInt(1, getRelacionamentoId(con));
			ResultSet rs = ps.executeQuery();
			rs.next();
			relacionadorFuncaoId = rs.getInt(1);
		}
		return relacionadorFuncaoId;
	}

	private static Integer getRelacionamentoId(Connection con) throws Exception {
		if (relacionamentoId == null) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ID FROM pessoa.relacionamento_tipo WHERE codigo = 'PROFISSIONAL'").append("\n");
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			rs.next();
			relacionamentoId = rs.getInt(1);
		}
		return relacionamentoId;

	}

	private static Date getTermino() {
		if (termino == null) {
			Calendar c = Calendar.getInstance();
			c.set(2018, 10, 30, 23, 59, 59);
			termino = new Date(c.getTimeInMillis());
		}
		return termino;
	}

	public static Optional<List<UO>> getUO(String lotacao) {
		List<UO> result = null;

		if (lotacao.equalsIgnoreCase("ELPIP-ESCRITÓRIO LOCAL DA EMATER PIPIRIPAU")) {
			result = uoList.stream().filter(u -> u.getSigla().equals("ELPIP")).collect(Collectors.toList());
		} else if (lotacao.equalsIgnoreCase("EP NORTE-ESCRITÓRIO DE PROJETOS ESPECIAIS NORTE (PIPIRIPAU)")) {
			result = uoList.stream().filter(u -> u.getSigla().equals("PIPIRIPAU")).collect(Collectors.toList());
		} else if (lotacao.equalsIgnoreCase("GABIN-GABINETE DA PRESIDÊNCIA")) {
			result = uoList.stream().filter(u -> u.getSigla().equals("GABIN")).collect(Collectors.toList());
		} else if (lotacao.equalsIgnoreCase("GERÊNCIA DE PROJETOS ESPECIAIS")) {
			result = uoList.stream().filter(u -> u.getSigla().equals("GEPRE")).collect(Collectors.toList());
		} else {
			for (UO uo : uoList) {
				if (lotacao.toLowerCase().startsWith(uo.getSigla().toLowerCase())
						|| lotacao.toLowerCase().contains(uo.getSigla().toLowerCase())) {
					if (result == null) {
						result = new ArrayList<>();
					}
					result.add(uo);
				}

			}
		}

		return Optional.ofNullable(result);
	}

	private static List<Registro> registroList;

	public static void main(String[] args) throws Exception {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://10.233.33.3:3306", "root", "root")) {
			// try (Connection con =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306",
			// "root", "root")) {
			StringBuilder sql;
			Statement config = null;

			registroList = abrirArquivoExcel();
			try {
				con.setAutoCommit(false);

				config = con.createStatement();
				config.execute("SET FOREIGN_KEY_CHECKS=0");

				// excluir todas as vinculações de formularios
				con.prepareStatement(
						"UPDATE enquete.formulario SET nome = '<b>Avaliação de Desempenho por Mérito 2018</b><br/>Grupo SubGrupo A – Motorista, Auxiliar de Serviços Gerais, Eletricista, Mecânico Automotivo', inicio = '2018-10-01', termino = '2018-10-31 23:59:59' WHERE id = 1")
						.executeUpdate();
				con.prepareStatement(
						"UPDATE enquete.formulario SET nome = '<b>Avaliação de Desempenho por Mérito 2018</b><br/>Grupo SubGrupo B – Digitador, Desenhista, Assistente Administrativo, Técnico em Informática, Cedido - SAB', inicio = '2018-10-01', termino = '2018-10-31 23:59:59' WHERE id = 2")
						.executeUpdate();
				con.prepareStatement(
						"UPDATE enquete.formulario SET nome = '<b>Avaliação de Desempenho por Mérito 2018</b><br/>Grupo SubGrupo C1 – Extensionista Rural Nível Superior e Nível Médio, Técnico Especializado e Assessor 1, 2 e 3', inicio = '2018-10-01', termino = '2018-10-31 23:59:59' WHERE id = 3")
						.executeUpdate();
				con.prepareStatement(
						"UPDATE enquete.formulario SET nome = '<b>Avaliação de Desempenho por Mérito 2018 </b><br/>Grupo SubGrupo C2 – Chefe de Gabinete, Chefe da Comunicação Social e Assessoria Jurídica', inicio = '2018-10-01', termino = '2018-10-31 23:59:59' WHERE id = 4")
						.executeUpdate();
				con.prepareStatement(
						"UPDATE enquete.formulario SET nome = '<b>Avaliação de Desempenho por Mérito 2018</b><br/>Grupo SubGrupo D1 – Coordenador', inicio = '2018-10-01', termino = '2018-10-31 23:59:59' WHERE id = 5")
						.executeUpdate();
				con.prepareStatement(
						"UPDATE enquete.formulario SET nome = '<b>Avaliação de Desempenho por Mérito 2018</b><br/>Grupo SubGrupo D2 – Gerente e Supervisor', inicio = '2018-10-01', termino = '2018-10-31 23:59:59' WHERE id = 6")
						.executeUpdate();

				con.prepareStatement("truncate table enquete.resposta").executeUpdate();
				con.prepareStatement("truncate table enquete.resposta_versao").executeUpdate();
				con.prepareStatement("truncate table enquete.formulario_direcionamento").executeUpdate();

				for (Registro registro : registroList) {
					if (!registro.getLotacao().startsWith("CEDIDO CHEFE")) {
						Optional<List<UO>> uoList = getUO(registro.getLotacao());
						if (!uoList.isPresent() || uoList.get().size() > 1) {
							System.out.printf("não encontrado %s\n", registro.getLotacao());
						} else {
							Registro chefe = getChefeUo(registroList, registro.getUo());
							registro.setChefe(chefe);
							if (registro.getUo().getChefe() == null) {
								registro.getUo().setChefe(chefe);
							}

							List<Chefia> chefiaList = getChefia(registro.getFuncao());
							registro.setChefeDaUO(chefe != null && registro.getNumero().equals(chefe.getNumero())
									|| chefiaList != null && chefiaList.size() == 1);
							System.out.printf("encontrado %s - chefe %s chefe da uo %b\n", registro.getLotacao(),
									registro.getChefe() == null ? " nao identificado " : registro.getChefe().getNome(),
									registro.isChefeDaUO());
						}
					}
				}

				// if (1==1) {
				// throw new NullPointerException();
				// }

				// abrir arquivo excel
				for (Registro registro : registroList) {

					// percorrer cada linha

					// procurar o empregado
					sql = new StringBuilder();
					sql.append("SELECT ").append("\n");
					sql.append("    empregado_id, empregado_nome, u.id AS usuario_id").append("\n");
					sql.append("FROM").append("\n");
					sql.append("    funcional.emprego_vi a").append("\n");
					sql.append("        LEFT JOIN").append("\n");
					sql.append("    sistema.usuario u ON u.pessoa_id = a.empregado_id").append("\n");
					sql.append("WHERE").append("\n");
					sql.append("    matricula = ?").append("\n");
					PreparedStatement encontraEmpregadoPs = con.prepareStatement(sql.toString());
					encontraEmpregadoPs.setString(1, registro.getMatricula());
					ResultSet encontraEmpregadoRs = encontraEmpregadoPs.executeQuery();

					if (!encontraEmpregadoRs.next()) {
						registro.setEmpregadoNome(registro.getNome());

						// insere pessoa
						sql = new StringBuilder();
						sql.append("INSERT INTO pessoa.pessoa (nome) VALUES (?)").append("\n");
						PreparedStatement inserePessoaPs = con.prepareStatement(sql.toString(),
								Statement.RETURN_GENERATED_KEYS);
						inserePessoaPs.setString(1, registro.getEmpregadoNome());
						if (inserePessoaPs.executeUpdate() == 0) {
							throw new SQLException("Não inserido em pessoa");
						}
						try (ResultSet generatedKeys = inserePessoaPs.getGeneratedKeys()) {
							if (generatedKeys.next()) {
								registro.setPessoaId(generatedKeys.getInt(1));
							} else {
								throw new SQLException("Id pessoa não gerado");
							}
						}

						// insere pessoa física
						sql = new StringBuilder();
						sql.append("INSERT INTO pessoa.pessoa_fisica (id, sexo) VALUES (?, 'M')").append("\n");
						PreparedStatement inserePessoaFisicaPs = con.prepareStatement(sql.toString());
						inserePessoaFisicaPs.setInt(1, registro.getPessoaId());
						if (inserePessoaFisicaPs.executeUpdate() == 0) {
							throw new SQLException("Não inserido em pessoa física");
						}

						// insere relacionamento
						sql = new StringBuilder();
						sql.append("INSERT INTO pessoa.relacionamento (relacionamento_tipo_id, inicio) VALUES (?, ?)")
								.append("\n");
						PreparedStatement insereRelacionamentoPs = con.prepareStatement(sql.toString(),
								Statement.RETURN_GENERATED_KEYS);
						insereRelacionamentoPs.setInt(1, getRelacionamentoId(con));
						insereRelacionamentoPs.setDate(2,
								new java.sql.Date(
										registro.getDataAdmissao() == null ? Calendar.getInstance().getTimeInMillis()
												: registro.getDataAdmissao().getTime().getTime()));
						if (insereRelacionamentoPs.executeUpdate() == 0) {
							throw new SQLException("Não inserido em relacionamento");
						}
						Integer relacionamentoId = null;
						try (ResultSet generatedKeys = insereRelacionamentoPs.getGeneratedKeys()) {
							if (generatedKeys.next()) {
								relacionamentoId = generatedKeys.getInt(1);
							} else {
								throw new SQLException("Id relacionamento não gerado");
							}
						}

						// insere relacionamento pessoa patrao
						sql = new StringBuilder();
						sql.append(
								"INSERT INTO pessoa.pessoa_relacionamento (relacionamento_id, pessoa_id, relacionamento_funcao_id) VALUES (?, ?, ?)")
								.append("\n");
						PreparedStatement inserePessoaRelacionamentoPs = con.prepareStatement(sql.toString());
						inserePessoaRelacionamentoPs.setInt(1, relacionamentoId);
						inserePessoaRelacionamentoPs.setInt(2, getPatraoId(con));
						inserePessoaRelacionamentoPs.setInt(3, getRelacionadorFuncaoId(con));
						if (inserePessoaRelacionamentoPs.executeUpdate() == 0) {
							throw new SQLException("Não inserido em pessoa relacionamento patrao");
						}

						// insere relacionamento pessoa empregado
						inserePessoaRelacionamentoPs.setInt(1, relacionamentoId);
						inserePessoaRelacionamentoPs.setInt(2, registro.getPessoaId());
						inserePessoaRelacionamentoPs.setInt(3, getRelacionadoFuncaoId(con));
						if (inserePessoaRelacionamentoPs.executeUpdate() == 0) {
							throw new SQLException("Não inserido em pessoa relacionamento empregado");
						}

						// insere emprego
						sql = new StringBuilder();
						sql.append("INSERT INTO funcional.emprego (id, matricula, cargo_id) VALUES (?, ?, 1)")
								.append("\n");
						PreparedStatement insereEmpregoPs = con.prepareStatement(sql.toString());
						insereEmpregoPs.setInt(1, relacionamentoId);
						insereEmpregoPs.setString(2, registro.getMatricula());
						if (insereEmpregoPs.executeUpdate() == 0) {
							throw new SQLException("Não inserido em emprego");
						}

						// insere usuario
						sql = new StringBuilder();
						sql.append("INSERT INTO sistema.usuario (pessoa_id, nome_usuario) VALUES (?, ?)").append("\n");
						PreparedStatement insereUsuarioPs = con.prepareStatement(sql.toString(),
								Statement.RETURN_GENERATED_KEYS);
						insereUsuarioPs.setInt(1, registro.getPessoaId());
						insereUsuarioPs.setString(2, getNomeUsuario(con, registro.getNome()));
						if (insereUsuarioPs.executeUpdate() == 0) {
							throw new SQLException("Não inserido em usuario");
						}
						try (ResultSet generatedKeys = insereUsuarioPs.getGeneratedKeys()) {
							if (generatedKeys.next()) {
								registro.setUsuarioId(generatedKeys.getInt(1));
							} else {
								throw new SQLException("Id usuário não gerado");
							}
						}
					} else {
						registro.setPessoaId(encontraEmpregadoRs.getInt(1));
						registro.setEmpregadoNome(encontraEmpregadoRs.getString(2));
						registro.setUsuarioId(encontraEmpregadoRs.getInt(3));
					}

					// atualizar senha
					sql = new StringBuilder();
					sql.append("UPDATE sistema.usuario SET senha = ? WHERE id = ?").append("\n");
					PreparedStatement atualizaSenhaPs = con.prepareStatement(sql.toString());
					String senha = md5(
							registro.getUsuarioId() + removeZeroEsquerda(registro.getMatricula().toUpperCase()));
					atualizaSenhaPs.setString(1, senha);
					atualizaSenhaPs.setInt(2, registro.getUsuarioId());

					System.out.printf("usuario(%d) senha(%s) md5(%s)\n", registro.getUsuarioId(),
							registro.getUsuarioId() + removeZeroEsquerda(registro.getMatricula().toUpperCase()), senha);
					if (atualizaSenhaPs.executeUpdate() == 0) {
						throw new SQLException("Senha não alterada " + registro.getNome());
					}

				}

				for (Registro registro : registroList) {
					if (!registro.getLotacao().startsWith("CEDIDO CHEFE")) {
						// insere formulario_direcionamento
						Integer formularioId = getFormularioId(con, registro.getGrupoAvaliacao());

						sql = new StringBuilder();

						// autoavaliação
						sql.append(
								"INSERT INTO enquete.formulario_direcionamento (formulario_id, usuario_id, complemento, ordem, lixo, inicio, termino) VALUES (?, ?, ?, ?, ?, ?, ?)")
								.append("\n");
						PreparedStatement insereFormularioDirecionamentoPs = con.prepareStatement(sql.toString());
						insereFormularioDirecionamentoPs.setInt(1, formularioId);
						insereFormularioDirecionamentoPs.setInt(2, registro.getUsuarioId());
						insereFormularioDirecionamentoPs.setString(3, getComplemento(registro, false));
						insereFormularioDirecionamentoPs.setInt(4, 1);
						insereFormularioDirecionamentoPs.setString(5, "A");
						insereFormularioDirecionamentoPs.setDate(6, getInicio());
						insereFormularioDirecionamentoPs.setDate(7, getTermino());
						if (insereFormularioDirecionamentoPs.executeUpdate() == 0) {
							throw new SQLException("Não inserido em formulario direcionamento auto avaliação");
						}

						// avaliação do chefe -- avalia gestor da
						if (registro.getChefe() != null
								|| registro.getLotacao().equalsIgnoreCase("CEDIDOS - GERENCIA DE PESSOAL")) {
							insereFormularioDirecionamentoPs.setInt(1, formularioId);
							insereFormularioDirecionamentoPs.setInt(2,
									registro.getLotacao().equalsIgnoreCase("CEDIDOS - GERENCIA DE PESSOAL")
											? getChefeCedido(registro.getMatricula()).getUsuarioId()
											: registro.isChefeDaUO() && registro.getUoSuperior() != null
													? registro.getUoSuperior().getChefe().getUsuarioId()
													: registro.getChefe().getUsuarioId());
							insereFormularioDirecionamentoPs.setString(3, getComplemento(registro, true));
							insereFormularioDirecionamentoPs.setInt(4, 1);
							insereFormularioDirecionamentoPs.setString(5, registro.isChefeDaUO() ? "I" : "S");
							insereFormularioDirecionamentoPs.setDate(6, getInicio());
							insereFormularioDirecionamentoPs.setDate(7, getTermino());
							if (insereFormularioDirecionamentoPs.executeUpdate() == 0) {
								throw new SQLException("Não inserido em formulario direcionamento chefe");
							}
						}
					}

				}

				con.commit();
			} catch (Exception e) {
				if (con != null) {
					con.rollback();
				}
				e.printStackTrace();
			} finally {
				if (config != null) {
					config.execute("SET FOREIGN_KEY_CHECKS=1");
				}
			}
		}
	}

	private static String md5(String md5) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
		}
		return null;
	}

	private static String removeZeroEsquerda(String matricula) {
		StringBuilder result = new StringBuilder();
		boolean inicio = false;
		for (char p : matricula.toCharArray()) {
			if (p != '0') {
				inicio = true;
			}
			if (inicio) {
				result.append(p);
			}
		}
		return result.toString();
	}
}