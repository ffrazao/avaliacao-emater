package br.gov;

public class UO {

	private Registro chefe;

	private Integer id;

	private String nome;

	private Integer pai;

	private String sigla;

	public UO(Integer id, String nome, String sigla, Integer pai) {
		this.id = id;
		this.nome = nome;
		this.sigla = sigla;
		this.pai = pai;
	}

	public Registro getChefe() {
		return chefe;
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Integer getPai() {
		return pai;
	}

	public String getSigla() {
		return sigla;
	}

	public void setChefe(Registro chefe) {
		this.chefe = chefe;
	}

}