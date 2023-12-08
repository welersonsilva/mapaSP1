package manipulacaocsv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe que representa uma doação de sangue.
 */
public class Doacao {

    private int codigo;
    private String nome;
    private String cpf;
    private Date dataNascimento;
    private String tipoSanguineo;
    private int quantidadeDoada;

    /**
     * Construtor da classe Doacao.
     *
     * @param codigo           O código da doação.
     * @param nome             O nome do doador.
     * @param cpf              O CPF do doador.
     * @param dataNascimento   A data de nascimento do doador no formato "AAAA-MM-DD".
     * @param tipoSanguineo    O tipo sanguíneo do doador.
     * @param quantidadeDoada A quantidade de sangue doada em ml.
     */
    public Doacao(int codigo, String nome, String cpf, String dataNascimento, String tipoSanguineo, int quantidadeDoada) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.tipoSanguineo = tipoSanguineo;
        this.quantidadeDoada = quantidadeDoada;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.dataNascimento = dateFormat.parse(dataNascimento);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Formato de data inválido. Use o formato AAAA-MM-DD.");
        }
    }

    // Getters e setters aqui...

    /**
     *
     * @return
     */

    public int getCodigo() {
        return codigo;
    }

    /**
     *
     * @param codigo
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     *
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *
     * @return
     */
    public String getCpf() {
        return cpf;
    }

    /**
     *
     * @param cpf
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     *
     * @return
     */
    public Date getDataNascimento() {
        return dataNascimento;
    }

    /**
     *
     * @param dataNascimento
     */
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     *
     * @return
     */
    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    /**
     *
     * @param tipoSanguineo
     */
    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    /**
     *
     * @return
     */
    public int getQuantidadeDoada() {
        return quantidadeDoada;
    }

    /**
     *
     * @param quantidadeDoada
     */
    public void setQuantidadeDoada(int quantidadeDoada) {
        this.quantidadeDoada = quantidadeDoada;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("%d,%s,%s,%s,%s,%d", codigo, nome, cpf, dateFormat.format(dataNascimento),
                tipoSanguineo, quantidadeDoada);
    }

    /**
     * Converte a doação para uma string no formato CSV.
     *
     * @return A representação da doação em formato CSV.
     */
    public String toCSV() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("%d,%s,%s,%s,%s,%d", codigo, nome, cpf, dateFormat.format(dataNascimento),
                tipoSanguineo, quantidadeDoada);
    }
    
}

