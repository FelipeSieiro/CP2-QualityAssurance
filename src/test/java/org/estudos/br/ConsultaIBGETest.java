package org.estudos.br;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsultaIBGETest {
 @Mock
    private HttpURLConnection connectionMock;
    private static final String ESTADOS_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/";
    private static final String DISTRITOS_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/distritos/";
    private static final String JSON_RESPONSE = "{\"id\":33,\"sigla\":\"RJ\",\"nome\":\"Rio de Janeiro\",\"regiao\":{\"id\":3,\"sigla\":\"SE\",\"nome\":\"Sudeste\"}}";
    private static final String REGIOES_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/regioes/";


    @Test
    @DisplayName("Teste para consulta única de um estado")
    public void testConsultarEstado() throws IOException {
        // Arrange
        String uf = "SP"; // Define o estado a ser consultado

        // Act
        String resposta = ConsultaIBGE.consultarEstado(uf); // Chama o método a ser testado

        // Assert
        // Verifica se a resposta não está vazia
        assert !resposta.isEmpty();

        // Verifica se o status code é 200 (OK)
        HttpURLConnection connection = (HttpURLConnection) new URL(ESTADOS_API_URL + uf).openConnection();
        int statusCode = connection.getResponseCode();
        assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    }


    
    @ParameterizedTest
    @CsvSource({"520005005", "310010405", "520010005"})
    @DisplayName("Teste para consulta de distritos com CsvSource")
    public void testConsultarDistrito(int identificador) throws IOException {
        // Arrange
        int id = identificador; // Define o estado a ser consultado

        // Act
        String resposta = ConsultaIBGE.consultarDistrito(id); // Chama o método a ser testado

        // Assert
        // Verifica se a resposta não está vazia
        assert !resposta.isEmpty();

        // Verifica se o status code é 200 (OK)
        HttpURLConnection connection = (HttpURLConnection) new URL(DISTRITOS_API_URL + id).openConnection();
        int statusCode = connection.getResponseCode();
        assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    }

    

    @Test
    @DisplayName("Consulta usando o Estado com Mock")
    public void testConsultarEstadoComMock() throws IOException {
        String uf = "RJ";

        // Act - Chama o método a ser testado
        String response = ConsultaIBGE.consultarEstado(uf);

        // Verifica se o json retornado é igual ao esperado
        assertEquals(JSON_RESPONSE, response, "O JSON retornado não corresponde ao esperado.");
    }


    @Test
    @DisplayName("Teste para consultar estados pela região")
    public void testConsultarEstadosRegiao() throws IOException {
        // Arrange
        int id = 2; // Define a regiao que consultaremos os estados

        // Act
        String resposta = ConsultaIBGE.consultarEstadosRegiao(id); // Chama o método a ser testado

        // Assert
        // Verifica se a resposta não está vazia
        assert !resposta.isEmpty();

        // Verifica se o status code é 200 (OK)
        HttpURLConnection connection = (HttpURLConnection) new URL(REGIOES_API_URL + id + "/estados").openConnection();
        int statusCode = connection.getResponseCode();
        assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    }
}