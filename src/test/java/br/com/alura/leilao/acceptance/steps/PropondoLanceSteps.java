package br.com.alura.leilao.acceptance.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.query.criteria.internal.MapJoinImplementor;

import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

public class PropondoLanceSteps {

	private List<Lance> lances;
	private Leilao leilao;

	@Before
	public void setup() {
		this.lances = new ArrayList<>();
	}

	@After
	public void tearDown() {
		this.lances.clear();
	}

	@Dado("um lance de {double} reais do usuário {string}")
	public void um_lance_de_reais_do_usuario(Double valor, String nome) {
		Usuario usuario = new Usuario(nome);
		Lance lance = new Lance(usuario, BigDecimal.valueOf(valor));
		this.lances.add(lance);
	}

	@Dado("um lance de {double} do usuário {string}")
	public void e_um_lance_de_do_usuário(Double valor, String nome) {
		um_lance_de_reais_do_usuario(valor, nome);
	}

	@Dado("um lance inválido de {double} reais do usuário {string}")
	public void um_lance_inválido_de_reais_do_usuário(Double valor, String nome) {
		um_lance_de_reais_do_usuario(valor, nome);
	}

	@Dado("dois lances")
	public void dois_lances(DataTable dataTable) {
		Map<String, String> unmodifiableLances = dataTable.asMap(String.class, String.class);
		Map<String, String> lances = new LinkedHashMap<>(unmodifiableLances);
		String first = lances.entrySet().iterator().next().getKey();
		lances.remove(first);
		lances.forEach((k,v) -> um_lance_de_reais_do_usuario(Double.parseDouble(k), v));   
	}

	@Entao("o segundo lance não é aceito")
	public void o_segundo_lance_não_é_aceito() {

	}

	@Quando("propõe ao leilão")
	public void quando_propoe_ao_leilao() {
		leilao = new Leilao("Table XPTO");
		this.lances.forEach(leilao::propoe);
	}

	@Entao("o lance é aceito")
	public void entao_o_lance_e_aceito() {
		assertEquals(1, leilao.getLances().size());
		assertEquals(BigDecimal.valueOf(50.00), leilao.getLances().get(0).getValor());
	}

	@Entao("os lance são aceitos")
	public void então_os_lance_são_aceitos() {
		assertEquals(2, leilao.getLances().size());
		assertEquals(BigDecimal.valueOf(10.00), leilao.getLances().get(0).getValor());
		assertEquals(BigDecimal.valueOf(15.00), leilao.getLances().get(1).getValor());
	}

	@Entao("o lance não é aceito")
	public void o_lance_não_é_aceito() {
		assertEquals(0, leilao.getLances().size());
	}

}
