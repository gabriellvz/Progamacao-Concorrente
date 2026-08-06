/* ***************************************************************
* Autor............: Gabriel Alves Cruz 
* Matricula........: 202411222
* Inicio...........: 19/04/2026
* Ultima alteracao.: 20/04/2026
* Nome.............: Principal.java
* Funcao...........: Responsavel por realizar as importacoes necessarias para para a execucao do javafx e para a inicializacao das telas
*************************************************************** */

// Bibliotecas necessarias para o funcionamento do programa

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.GerenciadorDeAudio;
import controller.ControllerTela1;
import controller.ControllerTela2;
import controller.ControllerTela3;

public class Principal extends Application {

  /* ***************************************************************
  * Metodo: Metodo start
  * Funcao: Inicia a aplicacao carregando o fxml
  * Parametros: Recebe primaryStage da classe Stage, responsavel pela
  * configuracao da tela apos ser incializada
  * Retorno: void
  *************************************************************** */

  @Override
  public void start(Stage primaryStage) throws Exception {
    
    try {

      GerenciadorDeAudio.iniciarAudio("/resources/audio/musica-8bits.wav");
      Parent root = FXMLLoader.load(getClass().getResource("/view/tela1.fxml")); // Carrega o fxml da primeira tela
      Scene scene = new Scene(root); // Define a nova cena com o root
      String css = getClass().getResource("resources/css/estilosTelas.css").toExternalForm(); // Recebe o caminho da folha de estilos 
      scene.getStylesheets().add(css); // vincula a folha de stilos css a cena
      Image icone = new Image("assets/icone.png"); // Imagem do icone da aplicacao 
      primaryStage.getIcons().add(icone);
      primaryStage.setTitle("Naves Concorrentes"); // Titulo do stage
      primaryStage.setScene(scene); // Define a cena
      primaryStage.setResizable(false); // Desabilita a opcao de maximizar a tela
      primaryStage.initStyle(StageStyle.UNDECORATED);
      primaryStage.show(); // Exibe o stage
     
    } catch (Exception e) {
      e.printStackTrace();

    } // fim do bloco try-catch

  } // fim do metodo start
  
  /* ***************************************************************
  * Metodo: main 
  * Funcao: inicializar a aplicacao
  * Parametros: String [] args
  * Retorno: retorno do tipo void 
  *************************************************************** */

  public static void main(String[] args) {
    launch(args);
  } // fim do metodo main

} // fim da classe Principal
