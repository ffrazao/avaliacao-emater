package br.gov;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class Registro {

	private String cargo;

	private Registro chefe;

	private boolean chefeDaUO;

	private Calendar dataAdmissao;

	private String empregadoNome;

	private String funcao;

	private String grupoAvaliacao;

	private String lotacao;

	private String matricula;

	private String nivelSalarialAtual;

	private String nome;

	private Integer numero;

	private Integer pessoaId;

	private String refFuncional;

	private String status;

	private UO uo;

	private UO uoSuperior;

	private Integer usuarioId;

	public Registro() {
	}

	public String getCargo() {
		return cargo;
	}

	public Registro getChefe() {
		return chefe;
	}

	public Calendar getDataAdmissao() {
		return dataAdmissao;
	}

	public String getEmpregadoNome() {
		return empregadoNome;
	}

	public String getFuncao() {
		return funcao;
	}

	public String getGrupoAvaliacao() {
		return grupoAvaliacao;
	}

	public String getLotacao() {
		return lotacao;
	}

	public String getMatricula() {
		return matricula;
	}

	public String getNivelSalarialAtual() {
		return nivelSalarialAtual;
	}

	public String getNome() {
		return nome;
	}

	public Integer getNumero() {
		return numero;
	}

	public Integer getPessoaId() {
		return pessoaId;
	}

	public String getRefFuncional() {
		return refFuncional;
	}

	public String getStatus() {
		return status;
	}

	public UO getUo() {
		return uo;
	}

	public UO getUoSuperior() {
		return uoSuperior;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public boolean isChefeDaUO() {
		return chefeDaUO;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public void setChefe(Registro chefe) {
		this.chefe = chefe;
	}

	public void setChefeDaUO(boolean chefeDaUO) {
		this.chefeDaUO = chefeDaUO;
	}

	public void setDataAdmissao(Calendar dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public void setEmpregadoNome(String empregadoNome) {
		this.empregadoNome = empregadoNome;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public void setGrupoAvaliacao(String grupoAvaliacao) {
		this.grupoAvaliacao = grupoAvaliacao;
	}

	public void setLotacao(String lotacao) {
		this.lotacao = lotacao;
		Optional<List<UO>> uoList = EmaterApplication.getUO(lotacao);
		if (uoList.isPresent() && uoList.get().size() == 1) {
			setUo(uoList.get().get(0));
		} else {
			// throw new NullPointerException("lotacao " + lotacao);
			System.out.println("lotacao " + lotacao);
		}
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public void setNivelSalarialAtual(String nivelSalarialAtual) {
		this.nivelSalarialAtual = nivelSalarialAtual;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public void setPessoaId(Integer pessoaId) {
		this.pessoaId = pessoaId;
	}

	public void setRefFuncional(String refFuncional) {
		this.refFuncional = refFuncional;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUo(UO uo) {
		this.uo = uo;

		if (uo.getPai() != null) {
			for (UO uoSuperior : EmaterApplication.uoList) {
				if (uo.getPai().equals(uoSuperior.getId())) {
					this.uoSuperior = uoSuperior;
					break;
				}
			}
		}
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

}