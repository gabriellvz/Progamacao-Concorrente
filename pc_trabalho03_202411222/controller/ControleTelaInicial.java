/* ***************************************************************
* Autor............: Gabriel Alves Cruz 
* Matricula........: 202411222
* Inicio...........: 08/05/2026
* Ultima alteracao.: 16/05/2026
* Nome.............: ControleTelaInicial.java
* Funcao...........: Criar metodos de controle para a tela inicial  
*************************************************************** */

package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControleTelaInicial {

  private Parent root;
  private Stage stage;
  private Scene scene;

  /*
   * ***************************************************************
   * Metodo: alterarTela1
   * Funcao: Trocar para a tela principal
   * Parametros: ActionEvent event
   * Retorno: void
   * ***************************************************************
   */

  @FXML
  public void alterarTela1(ActionEvent event) throws IOException {

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPrincipal.fxml"));
    root = loader.load();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    scene.getStylesheets().add(getClass().getResource("/assets/css/EstilosTelaPrincipal.css").toExternalForm());
    stage.show();

  }

  /*
   * ***************************************************************
   * Metodo: fecharTela1
   * Funcao: encerrar a primeira tela
   * Parametros: ActionEvent event
   * Retorno: void
   * ***************************************************************
   */

  @FXML
  public void fecharTela1(ActionEvent event) {
    System.exit(0);
  }

} // fim 
