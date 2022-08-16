package com.dbc.curriculo.dto.completoApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

/**
 * Essa classe é a lista de vagas da API. Será utilizada somente para buscar os dados
 * @author Clebson Mendonça
 */
@Data
public class VagaAPIRootListDTO {
    @JsonProperty("id")
    public Integer id;
//    @JsonProperty("Ano_Mes_Dt_Abertura")
//    public String ano_Mes_Dt_Abertura;
//    @JsonProperty("Semana_Data_Abertura")
//    public Date semana_Data_Abertura;
//    @JsonProperty("Ano_Mes_Dt_Fechamento")
//    public String ano_Mes_Dt_Fechamento;
//    @JsonProperty("Semana_Data_Fechamento")
//    public Object semana_Data_Fechamento;
//    @JsonProperty("Ano_Mes_Dt_Cancelamento")
//    public String ano_Mes_Dt_Cancelamento;
//    @JsonProperty("Semana_Data_Cancelamento")
//    public Date semana_Data_Cancelamento;
//    @JsonProperty("Codigo")
//    public Object codigo;
    @JsonProperty("Titulo")
    public String titulo;
//    @JsonProperty("Posicoes")
//    public int posicoes;
//    @JsonProperty("Privada")
//    public boolean privada;
//    @JsonProperty("StatusVaga_Id")
//    public int statusVaga_Id;
    @JsonProperty("Status")
    public String status;
//    @JsonProperty("Cliente")
//    public String cliente;
//    @JsonProperty("Contato")
//    public Object contato;
//    @JsonProperty("Analista")
//    public String analista;
    @JsonProperty("DataAbertura")
    public Date dataAbertura;
//    @JsonProperty("DataFechamento")
//    public Object dataFechamento;
//    @JsonProperty("Filial")
//    public Object filial;
//    @JsonProperty("DataCancelamento")
//    public Date dataCancelamento;
//    @JsonProperty("PCD")
//    public boolean pCD;
    @JsonProperty("Categoria")
    public Object categoria;
    @JsonProperty("Cidade")
    public String cidade;
    @JsonProperty("Estado")
    public String estado;
//    @JsonProperty("Temporario")
//    public boolean temporario;
//    @JsonProperty("Hunting")
//    public boolean hunting;
//    @JsonProperty("ValorMinimoCobradoCliente")
//    public Object valorMinimoCobradoCliente;
//    @JsonProperty("ValorMaximoCobradoCliente")
//    public Object valorMaximoCobradoCliente;
//    @JsonProperty("TipoValorCliente")
//    public Object tipoValorCliente;
//    @JsonProperty("MotivoCancelamento")
//    public String motivoCancelamento;
//    @JsonProperty("TipoContratacao")
//    public String tipoContratacao;
//    @JsonProperty("DataLimiteContratacao")
//    public Object dataLimiteContratacao;
//    @JsonProperty("Dias_Desde_Abertura")
//    public int dias_Desde_Abertura;
//    @JsonProperty("Dias_Ate_DataLimiteContratacao")
//    public Object dias_Ate_DataLimiteContratacao;
//    @JsonProperty("Dias_Entre_Abertura_e_Fechamento")
//    public Object dias_Entre_Abertura_e_Fechamento;
//    @JsonProperty("Dias_entre_DataLimiteContratacao_e_Fechamento")
//    public Object dias_entre_DataLimiteContratacao_e_Fechamento;
//    @JsonProperty("ContatoEmail")
//    public Object contatoEmail;
//    @JsonProperty("Responsavel")
//    public String responsavel;
//    @JsonProperty("Contratados")
//    public int contratados;
//    @JsonProperty("DiferencaPosicoesContratados")
//    public int diferencaPosicoesContratados;
//    @JsonProperty("Justificativa")
//    public String justificativa;
//    @JsonProperty("DescricaoObservacao")
//    public String descricaoObservacao;
//    @JsonProperty("NomeSubstituido")
//    public Object nomeSubstituido;
//    @JsonProperty("VagaPrioridadeDescricao")
//    public String vagaPrioridadeDescricao;
//    @JsonProperty("CampoGenerico")
//    public Object campoGenerico;
//    @JsonProperty("HistoricoMudancaStatus")
//    public ArrayList<Object> historicoMudancaStatus;
//    @JsonProperty("ListaContatos")
//    public ArrayList<Object> listaContatos;
    @JsonProperty("Tags")
    public ArrayList<Object> tags;
}
