package br.com.ednaldo.conversor.desafio;
// Imports gerados
import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

// Classe principal onde montaremos os codigos para interagir e converter
public class ConversorDeMoedas {
      public static void main(String[] args) throws Exception {
            // Chamada e import para que o usuario consiga interagir no console
            Scanner scanner = new Scanner(System.in);

            // Aqui chamamos e importamos uma lista ...
            // Onde iremos colocar  as siglas + nome da moeda
            ArrayList<String> moedas = new ArrayList<>();
            moedas.add("USD -   Dólar Americano");
            moedas.add("EUR -   Euro");
            moedas.add("BRL -   Real Brasileiro");
            moedas.add("JPY -    Iene Japonês");
            moedas.add("GBP -   Libra Esterlina");
            moedas.add("AUD -  Dólar Australiano");
            moedas.add("CAD -  Dólar Canadense");
            moedas.add("CHF -  Franco Suíço");
            moedas.add("CNY -  Yuan Chinês");
            moedas.add("HKD - Dólar de Hong Kong");

            // Pequeno cabeçalho para apresentar as moedas
            System.out.println("\n***********************************************");
            System.out.println("*          ====  CONVERSOR DE MOEDAS  ===            *");
            System.out.println("***********************************************");
            System.out.println("***********************************************");
            System.out.println("* Moedas disponíveis:");


            // Laço para trazer a lista das moedas e seus nomes
            // Coloquei +1 porque o indice começa com zero , então agora ele traz
            // na ordem correta.
            for (int i = 0; i < moedas.size(); i++) {
                  System.out.println((i + 1) + " - " + moedas.get(i));
            }

            // Pequeno detalhe para o rodapé
            System.out.println("***********************************************");

            // Pergunta ao usuario qual vai ser a primeira moeda
            // Como deixamos o indice começando por 1 ... então diminuimos 1 para não dar erro na escolha
            System.out.println("Digite o número da primeira moeda: ");
            int primeiraMoeda = scanner.nextInt() - 1;
            String siglaOrigem = moedas.get(primeiraMoeda).split(" - ")[0]; // Pega dentro da lista oque o usuario escolheu

            // Pergunta ao usuario qual vai ser a segunda  moeda
            // Como deixamos o indice começando por 1 ... então diminuimos 1 para não dar erro na escolha
            System.out.println("Digite o número da segunda moeda: ");
            int segundaMoeda = scanner.nextInt() - 1;
            String siglaDestino = moedas.get(segundaMoeda).split(" - ")[0]; // Pega dentro da lista oque o usuario escolheu

            // Pergunta ao usuario qual valor vai ser convertido
            System.out.println("Qual valor gostaria de estar convertendo: ");
            double valor = scanner.nextDouble();
            System.out.println("***********************************************");

            // Minha chave e a URL  da API usada
            // Concatenando a minha chave com a primeira moeda da lista escolhida pelo usuario
            String chave = "24ea0cb495e1e69e95453670";
            String endereco = "https://v6.exchangerate-api.com/v6/" + chave + "/latest/" + siglaOrigem;

            // Chamada do request e do response + concatenando com o endereço da API
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(endereco)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Instanciando o Gson
            Gson gson = new Gson();

            // Chamamos a nossa classe Molde ( MoldeDoConversor) e criamos um objeto (dados)
            // Pedimos para o Gson pegar oque foi recebido e converter em objeto (gson.fromJson)
            //  Resposta que recebemos da API como String em formato Json (response.body)
            // a classe onde esta a estrutura do Json ( MoldeDoConversor.class)
            DadosApi dados = gson.fromJson(response.body(), DadosApi.class);


            // Objeto que criamos que vai chamar as informações da nossa classe MoldeDoConversor (dados)
            // Taxa de conversão da API salva no nosso Map dentro da classe Molde (  getConversion_rates() )
            // Sigla da moeda da API salva no nosso Map dentro da classe Molde (  containsKey )
            // a sigla destino foi a que o usuario escolheu (siglaDestino )
            // *** Resumo do if *** Se a taxa de conversão e a moeda que o usuario escolheu estiver dentro da lista que a API nos deu ...
            // ela vai pegar o valor digitado e multiplicar pela taxa da cotação
            if (dados.getConversion_rates().containsKey(siglaDestino)) {

                  // Criamos um atributo de valor decimal com o nome de taxa
                  // dentro da classe MoldeDoConversor pegamos a taxa de conversão e a sigla dda moeda
                  double taxa = dados.getConversion_rates().get(siglaDestino);
                  //Criamos um atributo de valor decimal com o nome de convertido
                  // pegamos o valor que o usuario digitou e multiplicamos pela taxa da moeda escolhida
                  double convertido = valor * taxa;

                  // Pequeno cabeçalho para apresentação do resultado da conversão
                  // uso do printf para organizar as casas decimais
                  // chamada das taxas e data da cotação que a  API tras e foi salva na classe Molde
                  System.out.println("\n************************");
                  System.out.println("* ===  CONVERSÃO  === *");
                  System.out.println("************************");
                  System.out.printf("Você escolheu converter:  %.2f  %s ",valor,  siglaOrigem);
                  System.out.println("Para a moeda: " + siglaDestino);
                  System.out.println("\n=========================");
                  System.out.printf(" %.2f %s =  %.2f %s\n", valor, siglaOrigem, convertido, siglaDestino);
                  System.out.println("=========================");
                  System.out.println("Taxa de Conversão: " + taxa);
                  System.out.println("Data da cotação: " + dados.getTime_last_update_utc());
            }
            // se a moeda escolhida não estiver na lista da API , mensagem de erro.
            else {
                  System.out.println("Moeda de destino inválida.");
            }
      }
}
