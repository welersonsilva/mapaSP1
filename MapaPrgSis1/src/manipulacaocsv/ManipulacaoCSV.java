package manipulacaocsv;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Classe para manipulação de registros em um arquivo CSV de doações de sangue.
 * 
 * Esta classe oferece funcionalidades para exibir, inserir e deletar doações de sangue
 * em um arquivo CSV, proporcionando interação através de um menu.
 * 
 * A estrutura do arquivo CSV deve ser a seguinte:
 * Cód., Nome, CPF, Data Nasc., Tipo Sanguíneo, mls Doados
 * 
 * @author Welerson Gomes Da Silva
 * @author RA20025257-5
 */
public class ManipulacaoCSV {

    /** Caminho do arquivo CSV de doações de sangue. */
    private static final String CAMINHO_ARQUIVO = "C:\\CSV\\doacoes.csv";
    private static final String CAMINHO_ARQUIVO_TEMP = "C:\\CSV\\Temp.csv";
    private static final SimpleDateFormat FORMATO_DATA = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Método principal que inicia o programa e exibe o menu.
     * 
     * @param args Os argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        exibirMenu();
    }

    /**
     * Exibe o Menu principal e realiza ações com base na escolha do usuário.
     */
    private static void exibirMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int opcao;

            do {
                System.out.println("----- MENU -----");
                System.out.println("1. Exibir conteúdo do arquivo");
                System.out.println("2. Inserir nova doação");
                System.out.println("3. Deletar doação por código");
                System.out.println("4. Sair");
                System.out.print("Escolha a opção desejada: ");

                try {
                    opcao = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer do scanner
                    switch (opcao) {
                        case 1 -> exibirConteudoArquivo();
                        case 2 -> inserirNovaDoacao(scanner);
                        case 3 -> deletarDoacaoPorCodigo(scanner);
                        case 4 -> System.out.println("Programa encerrado.");
                        default -> System.out.println("Opção inválida. Tente novamente.");
                    }
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                    scanner.nextLine(); // Limpar o buffer do scanner em caso de exceção
                    opcao = 0; // Reiniciar o loop
                }
            } while (opcao != 4);
        }
    }

    /**
     * Exibe o conteúdo do arquivo CSV de doações de sangue.
     * 
     * Este método lê o conteúdo do arquivo CSV e o exibe formatado no console.
     */
    private static void exibirConteudoArquivo() {
            try {
                List<Doacao> doacoes = lerDoacoes();
            } catch (IOException ex) {
                Logger.getLogger(ManipulacaoCSV.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("----- Conteúdo do Arquivo -----");
            System.out.printf("%-5s%-25s%-18s%-23s%-18s%-15s\n", "Cód.", "Nome", "CPF", "Data Nasc.",
                    "Tipo Sanguíneo", "mls Doados");

           try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
        String linha;
        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(",");
            System.out.printf("%-5s%-25s%-18s%-23s%-18s%-15s\n", campos[0], campos[1], campos[2], campos[3],
                    campos[4], campos[5]);
        }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    /**
     * Insere uma nova doação no arquivo CSV de doações de sangue.
     * 
     * @param scanner O scanner utilizado para entrada do usuário.
     * 
     * Este método solicita informações ao usuário para criar uma nova doação e a insere
     * no arquivo CSV. Após a inserção, exibe a tabela atualizada.
     */
    private static void inserirNovaDoacao(Scanner scanner) {
        try {
            List<Doacao> doacoes = lerDoacoes();
            System.out.println("Informe os Dados da nova Doação:");
            int codigo;
            do {
                System.out.print("Código: ");
                codigo = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer do scanner
            } while (codigoJaExiste(doacoes, codigo));

            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Data de Nascimento (AAAA-MM-DD): ");
            String dataNascimentoStr = scanner.nextLine();
            if (!validarFormatoData(dataNascimentoStr)) {
                System.out.println("Formato de data inválido. Use o formato AAAA-MM-DD.");
                return;
            }
            System.out.print("Tipo Sanguíneo: ");
            String tipoSanguineo = scanner.nextLine();
            System.out.print("Quantidade doada (ml): ");
            int quantidadeDoada = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            Doacao novaDoacao = new Doacao(codigo, nome, cpf, dataNascimentoStr, tipoSanguineo, quantidadeDoada);
            doacoes.add(novaDoacao);

            escreverDoacoes(doacoes);

            System.out.println("Doação inserida com sucesso!");
            System.out.println();
            // Exibir a tabela após a inserção
            exibirConteudoArquivo();
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    /**
     * Deleta uma doação do arquivo CSV de doações de sangue com base no código especificado.
     * 
     * @param scanner O scanner utilizado para entrada do usuário.
     * 
     * Este método solicita ao usuário o código da doação a ser deletada, remove a doação
     * do arquivo CSV e exibe a tabela atualizada.
     */
    private static void deletarDoacaoPorCodigo(Scanner scanner) {
        try {
            List<Doacao> doacoes = lerDoacoes();
            System.out.print("Informe o código da doação a ser deletada: ");
            int codigoDeletar = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            List<Doacao> novasDoacoes = new ArrayList<>();
            for (Doacao doacao : doacoes) {
                if (doacao.getCodigo() != codigoDeletar) {
                    novasDoacoes.add(doacao);
                }
            }

            escreverDoacoes(novasDoacoes);

            System.out.println("Doação deletada com sucesso!");
            // Exibir a Tabela após Deletar Doação
            exibirConteudoArquivo();
        } catch (IOException e) {
            System.out.println("Erro ao manipular o arquivo: " + e.getMessage());
        }
    }

    /**
     * Verifica se o código já existe na lista de doações.
     * 
     * @param doacoes A lista de doações.
     * @param codigo O código a ser verificado.
     * @return True se o código já existir, False caso contrário.
     * 
     * Este método verifica se o código fornecido já existe na lista de doações.
     */
    private static boolean codigoJaExiste(List<Doacao> doacoes, int codigo) {
        for (Doacao doacao : doacoes) {
            if (doacao.getCodigo() == codigo) {
                System.out.println("Código já existente. Informe outro código.");
                return true;
            }
        }
        return false;
    }

    /**
     * Valida o formato da data.
     * 
     * @param data A data a ser validada.
     * @return True se o formato for válido, False caso contrário.
     */
    private static boolean validarFormatoData(String data) {
        try {
            FORMATO_DATA.parse(data);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Lê as doações do arquivo CSV.
     * 
     * @return Uma lista de objetos Doacao.
     * @throws IOException Se ocorrer um erro de leitura do arquivo.
     */
    private static List<Doacao> lerDoacoes() throws IOException {
        List<Doacao> doacoes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                int codigo = Integer.parseInt(campos[0]);
                String nome = campos[1];
                String cpf = campos[2];
                String dataNascimento = campos[3];
                String tipoSanguineo = campos[4];
                int quantidadeDoada = Integer.parseInt(campos[5]);
                doacoes.add(new Doacao(codigo, nome, cpf, dataNascimento, tipoSanguineo, quantidadeDoada));
            }
        }
        return doacoes;
    }

    /**
     * Escreve as doações no arquivo CSV.
     * 
     * @param doacoes A lista de doações a ser escrita.
     * @throws IOException Se ocorrer um erro de escrita no arquivo.
     */
    private static void escreverDoacoes(List<Doacao> doacoes) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            for (Doacao doacao : doacoes) {
                bw.write(doacao.toCSV());
                bw.newLine();
            }
        }
    }
}
