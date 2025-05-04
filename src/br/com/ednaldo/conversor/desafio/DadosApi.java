package br.com.ednaldo.conversor.desafio;
// Import do Map
import java.util.Map;

// Na classe de Molde coloquei apenas a data de visualização da cotação e as siglas e valores da taxa
public class DadosApi {

      // Na API a data de cotação está time_last_update_utc... então trouxe para a classe molde para ser exibida
      // no principal ao dar a conversão ao usuario
      private  String time_last_update_utc;// vai trazer a data da cotação

      // Na API a taxa de conversão está time_last_update_utc ...
      // o Map  vai trazer da API as siglas (string) e a taxa de conversão (double)
      private Map<String, Double> conversion_rates; // Vai trazer as taxas de conversão com as siglas


      // Feito os gets deles já que foi deixado em privado
      public String getTime_last_update_utc() {
            return time_last_update_utc;
      }
      public Map<String, Double> getConversion_rates() {
            return conversion_rates;
      }
}
