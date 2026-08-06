/* ***************************************************************
* Autor............: Gabriel Alves Cruz 
* Matricula........: 202411222
* Inicio...........: 13/03/2026
* Ultima alteracao.: 22/03/2026
* Nome.............: ControllerTela1.java
* Funcao...........: Responsavel por realizar o controle da primeira tela da aplicacao
*************************************************************** */

package controller;

// Importacoes necessarias
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControllerTela1 {

  private Parent root;

  private Scene scene;

  private Stage stage;

  /* ***************************************************************
  * Metodo: botaoIniciar 
  * Funcao: ao receber um evento, executa a acao de iniciar a aplicacao
  * Parametros: ActionEvent event
  * Retorno: retorno do tipo void
  *************************************************************** */

  @FXML
  public void botaoIniciar (ActionEvent event) throws IOException{
    
      // Carrega o fxml da tela 2
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tela2.fxml")); // Define a cena como o no raiz, o fxml da tela 2
      root = loader.load();
      
      stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Define o stage
      scene = new Scene(root); // Define a cena com o root
      
      stage.setScene(scene); // Seta a cena atual
      
      scene.getStylesheets().add(getClass().getResource("/resources/css/estilosTelas.css").toExternalForm()); // Aplica a folha de estilos css
      
      stage.show(); // Exibe a javela
    
  } // fim do metodo botaoIniciar

  /* ***************************************************************
  * Metodo: botaoFechar
  * Funcao: Ao receber um evento, executa a acao de encerrar a aplicacao
  * Parametros: ActionEvento evento
  * Retorno: retorno do tipo void
  *************************************************************** */

  @FXML
  public void botaoFechar (ActionEvent evento){
    System.exit(0);
  } // fim do metodo botaoFechar

} // fim da classe ControllerTela1