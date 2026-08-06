/* ***************************************************************
* Autor............: Gabriel Alves Cruz 
* Matricula........: 202411222
* Inicio...........: 13/03/2026
* Ultima alteracao.: 22/03/2026
* Nome.............: ControllerTela2.java
* Funcao...........: Responsavel por realizar o controle da segunda tela da aplicacao
*************************************************************** */

package controller;

// Importacoes necessarias para a execucao do programa
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;


public class ControllerTela2 implements Initializable{
  
  @FXML
  private ChoiceBox <String> choiceBox; // Choice box que  sera utilizado para a escolha de posicao das naves
 
  private Parent root;

  private Scene scene;

  private Stage stage;

  private int escolha = 0; // Variavel que armazena a escolha do usuario. Eh inicializada com 0 como opcao padrao 

  private String [] opcoesInicializacao = {"Partindo da esquerda (superior) e esquerda (inferior)", "Partindo da direita (superior) e direita (inferior)", 
                                           "Partindo da esquerda (superior) e direita (inferior)", "Partindo da esquerda (inferior) e direita (superior)"};
  
  /* ***************************************************************
  * Metodo: voltarParaTela1
  * Funcao: voltar para a primeira tela
  * Parametros: ActionEvent event 
  * Retorno: retorno do tipo void
  *************************************************************** */

  @FXML
  public void voltarParaTela1 (ActionEvent event) throws IOException{

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tela1.fxml")); // Carrega o controller da tela 1
    root = loader.load(); // Atribui o carregador FXML ao root
    stage = (Stage)  ((Node) event.getSource()).getScene().getWindow(); // Define o stage
    scene = new Scene(root); // Instancia a cena com o root
    scene.getStylesheets().add(getClass().getResource("/resources/css/estilosTelas.css").toExternalForm()); // Aplica a folha de estilos
    stage.setScene(scene); // Define o stage com a cena
    stage.show();// Exibe o stage

  } // fim do metodo voltarParaTela1

  /* ***************************************************************
  * Metodo: mudarParaTela3
  * Funcao: avancar para a tela 3
  * Parametros: ActionEvent event
  * Retorno: retorno do tipo void
  *************************************************************** */

  @FXML
  public void mudarParaTela3(ActionEvent event) throws IOException{

    escolha = choiceBox.getSelectionModel().getSelectedIndex(); // Atribui o indice a opcao escolhida pelo usuario a variavel escolha

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tela3.fxml")); // Cria instancia do carregador FXML
    root = loader.load(); // Atribui o carregador FXML ao root

    ControllerTela3 controllerTela3 = loader.getController(); // O controller da tela 3 eh carregado
    controllerTela3.configurarTela(escolha); // Chamada do metodo que recebe a escolha
    controllerTela3.atualizarLabel(choiceBox); // Chamada do metodo que atualiza a label com texto da opcao escolhida
    
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Define o stage
    scene = new Scene(root); // Intancia uma nova cena
    scene.getStylesheets().add(getClass().getResource("/resources/css/estilosTelas.css").toExternalForm()); // Aplicando a folha de estilos css
    stage.setScene(scene); //  Define a cena
    stage.show(); // Exibe o stage

  } // fim do metodo mudarParaTela3

  /* ***************************************************************
  * Metodo: initialize
  * Funcao: inicializar componentes assim que o controller for totalmente carregado
  * Parametros: URL url, ResourceBundle rb
  * Retorno: retorno do tipo void
  *************************************************************** */

  //Inicializa um controller depois de um root ter sido processado
  @Override
  public void initialize(URL url, ResourceBundle rb){
    choiceBox.getItems().addAll(opcoesInicializacao); // Adiciona o array de opcoes de inicializacao a choiceBox
    choiceBox.setValue(opcoesInicializacao[escolha]); // Seta o valor da choiceBox, a variavel 'escolha' eh passada como indice 
  } // im do metodo initialize 

} // fim da classe ControllerTela2
