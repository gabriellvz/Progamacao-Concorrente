/* ***************************************************************
* Autor............: Gabriel Alves Cruz 
* Matricula........: 202411222
* Inicio...........: 24/06/2026
* Ultima alteracao.: 04/06/2026
* Nome.............: Principal.java
* Funcao...........: Programa principal  
*************************************************************** */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import controller.ControleTelaInicial;
import controller.ControleTelaPrincipal;

public class Principal extends Application {

  /*
   * ***************************************************************
   * Metodo: start
   * Funcao: iniciar o stage principal
   * Parametros: Stage primaryStage
   * Retorno: void
   * ***************************************************************
   */

  public void start(Stage primaryStage) throws Exception {

    try {
      
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaInicial.fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root);
      primaryStage.setScene(scene);
      Font.loadFont(getClass().getResourceAsStream("/fonts/dogicapixel.ttf"), 14);
      scene.getStylesheets().add(getClass().getResource("assets/css/EstilosTelaInicial.css").toExternalForm());
      primaryStage.setResizable(false);
      primaryStage.setTitle("Jantar dos Filosofos");
      primaryStage.initStyle(StageStyle.UNDECORATED);
      primaryStage.show();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

    /*
   * ***************************************************************
   * Metodo: main
   * Funcao: metodo principal para executar o programa
   * Parametros: String [] args
   * Retorno: void
   * ***************************************************************
   */

  public static void main(String[] args) {
    launch(args);
  }

} // fim
