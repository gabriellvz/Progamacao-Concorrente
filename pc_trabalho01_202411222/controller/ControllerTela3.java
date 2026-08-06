/* ***************************************************************
* Autor............: Gabriel Alves Cruz 
* Matricula........: 202411222
* Inicio...........: 13/03/2026
* Ultima alteracao.: 22/03/2026
* Nome.............: ControllerTela3.java
* Funcao...........: Responsavel por realizar o controle da terceira tela da aplicacao
*************************************************************** */

package controller;

// Importacoes necessarias para a execucao do programa
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Nave;

public class ControllerTela3 implements Initializable{

  @FXML
  private ImageView imagemNave1, imagemNave2; // Imagens que serao manipuladas

  @FXML
  private Label textoDePosicao; // Label para representar a posicao escolhida

  @FXML
  private Slider sliderNaveVerde, sliderNaveAzul; // Sliders para controle de velocidade

  private Nave nave1, nave2; // Objetos que serao manipulados 

  private int escolha; // Escolha do usuario

  private Parent root; 

  private Scene scene;

  private Stage stage;
  
  private static final double VELOCIDADE_PADRAO = 1.0; // Velocidade padrao que sera setado no slider

  private Timeline timeline = new Timeline (new KeyFrame(Duration.millis(16), event -> { // Timiline para executar a animacao a cada 16 milissegundos
    nave1.movimentarNave(); 
    nave2.movimentarNave();
  } ));
  
  /* ***************************************************************
  * Metodo: configurarTela
  * Funcao: passar o valor de escolha da choice box para o atributo da classe ControllerTela3 e realizar a chamada do metodo de posicionar naves
  * Parametros: int escolha
  * Retorno: retorno do tipo void
  *************************************************************** */

  public void configurarTela (int escolha){
    this.escolha = escolha; // O atributo da classe recebe o valor de escolha do usuario
    posicionar();
  } // fim do metodo configurarTela

  /* ***************************************************************
  * Metodo: iniciarAnimacao
  * Funcao: Iniciar a animacao e definir a repeticao como indefinido
  * Parametros: ActionEvent event
  * Retorno: retorno do tipo void
  *************************************************************** */

  @FXML
  public void iniciarAnimacao(ActionEvent event){
    timeline.setCycleCount(Animation.INDEFINITE); // Seta a quantidade de vezes que a animacao ira se repetir para indefinido
    timeline.play(); // Inicia o timeline
  } // fim do metodo iniciarAnimacao

  /* ***************************************************************
  * Metodo: resetarAnimacao
  * Funcao: reseta a animacao das naves
  * Parametros: ActionEvent event
  * Retorno: retorno do tipo void
  *************************************************************** */

  @FXML
  public void resetarAnimacao (ActionEvent event) throws IOException{
    definirVelocidadePadrao(); // Valores do sliders tambem sao resetados
    posicionar(); // Naves reposicionadas
    timeline.stop(); // Pausa o timiline
  } // fim do metodo resetarAnimacao

  /* ***************************************************************
  * Metodo: voltarParatela2
  * Funcao: definir uma acao para o botao de voltar para a tela 2
  * Parametros: ActionEvent event 
  * Retorno: retorno do tipo void
  *************************************************************** */

  @FXML
  public void voltarParaTela2 (ActionEvent event) throws IOException{
    
    root = FXMLLoader.load(getClass().getResource("/view/tela2.fxml")); // Carrega o controller da tela 2
    scene = new Scene(root); //  Instancia a cena com o root
    stage = (Stage)  ((Node) event.getSource()).getScene().getWindow(); // Define o stage
    scene.getStylesheets().add(getClass().getResource("/resources/css/estilosTelas.css").toExternalForm()); // Aplica a folha de estilos
    stage.setScene(scene); // Define o stage com cena
    stage.show(); // Exibe o stage
    
  } // fim do metodo voltar para tela 2

  /* ***************************************************************
  * Metodo: fecharTela3 
  * Funcao: encerrar a aplicacao
  * Parametros: ActionEvent event 
  * Retorno: retorno do tipo void
  *************************************************************** */

  @FXML 
  public void fecharTela3 (ActionEvent event) throws IOException{
    System.exit(0);    
  }

  /* ***************************************************************
  * Metodo: atualizarLabel
  * Funcao: atualizar o texto de selecao de opcao conforme a escolha do usuario
  * Parametros: ChoiceBox <String> cb
  * Retorno: retorno do tipo void
  *************************************************************** */

  public void atualizarLabel(ChoiceBox <String> cb){
    textoDePosicao.setText("Posição escolhida: " + cb.getValue());
  } // fim do metodo atualizarLabel

  /* ***************************************************************
  * Metodo: definirVelocidadePadrao
  * Funcao: setar a velocidade padrao dos sliders de velocidade
  * Parametros: sem parametros definidos
  * Retorno: retorno do tipo void 
  *************************************************************** */

  private void definirVelocidadePadrao(){
    sliderNaveVerde.setValue(VELOCIDADE_PADRAO);
    sliderNaveAzul.setValue(VELOCIDADE_PADRAO);
  } // fim do metodo definirVelocidadePadrao

  /* ***************************************************************
  * Metodo: definirPosicoes0
  * Funcao: definir a posicao das naves caso a escolha for superior esquerda e inferior esquerda
  * Parametros: sem parametros definidos
  * Retorno: retorno do tipo void 
  *************************************************************** */

  // Caso o usuario escolher a opcao 0 em que a primeira nave parte da esquerda (em cima) e a segunda parte da esquerda (embaixo)
  private void definirPosicoes0 (){
    nave1 = new Nave(imagemNave1, 0);
    nave2 = new Nave(imagemNave2, 1);
  } // fim do metodo definirPosicoes0
  
  /* ***************************************************************
  * Metodo: definirPosicoes1 
  * Funcao: definir a posicao das naves caso a escolha for superior direita e inferior direita
  * Parametros: sem parametros definidos
  * Retorno: retorno do tipo void
  *************************************************************** */

  // Caso o usuario escolher a opcao 1 em que a primeira nave parte da direita (em cima) e a segunda parte da direita (embaixo)
  private void definirPosicoes1 (){
    nave1 = new Nave(imagemNave1, 2);
    nave2 = new Nave(imagemNave2, 3);
  } // fim do metodo definirPosicoes1

  /* ***************************************************************
  * Metodo: definirPosicoes2
  * Funcao: definir a posicao das naves caso a escolha for superior esquerda e direita inferior
  * Parametros: sem parametros definidos 
  * Retorno: retorno do tipo void
  *************************************************************** */

  // Caso o usuario escolher a opcao 2 em que a primeira nave parte da esquerda (em cima) e a segunda parte da direita (embaixo)
  private void definirPosicoes2 (){
    nave1 = new Nave(imagemNave1, 0);
    nave2 = new Nave(imagemNave2, 3);
  } // fim do metodo definirPosicoes2

  /* ***************************************************************
  * Metodo: definirPosicoes3
  * Funcao: definir a posicoes das naves caso a escolha for inferior esquerda e superior direita
  * Parametros: sem parametros definidos
  * Retorno: retorno do tipo void 
  *************************************************************** */

  // Caso o usuario escolher a opcao 3 em que a primeira nave parte da esquerda (embaixo) e a segunda parte da direita (em cima)
  private void definirPosicoes3 (){
    nave1 = new Nave(imagemNave1, 1);
    nave2 = new Nave(imagemNave2, 2);
  } // fim do metodo definirPosicoes3

  /* ***************************************************************
  * Metodo: posicionar
  * Funcao: Posicionar as naves conforme a escolha do usuario
  * Parametros: sem parametros definidos
  * Retorno: retorno do tipo void
  *************************************************************** */

  public void posicionar (){
    
    switch (escolha){

      case 0:
        definirPosicoes0();
        break;

      case 1:
        definirPosicoes1();
        break;

      case 2:
        definirPosicoes2();
        break;

      case 3:
        definirPosicoes3();
        break;

    } // fim do bloco switch-case

  } // fim do metodo posicionar

  /* ***************************************************************
  * Metodo: initialize
  * Funcao: inicializar componentes assim que o controller for totalmente carregado
  * Parametros: URL url, ResourceBundle rb
  * Retorno: retorno do tipo void
  *************************************************************** */

  @Override
  public void initialize (URL url, ResourceBundle rb){
  
    definirVelocidadePadrao(); // Valor padrao dos sliders eh iniciado assim asim que o controller eh carregado
    
    // Os metodos 'changed' em ambos listeners servem como um observador. Quando ha uma interacao com o slider ele executa o codigo em seu escopo.
    // valueProperty() : Eh a propriedade observavel do slider. Permite observar quando o valor da propriedade muda. 

    // Listener para o slider da nave verde. Assim que o usuario interagir com o slider, a velocidade sera modificada
    sliderNaveVerde.valueProperty().addListener(new ChangeListener<Number>() { 
      @Override
      public void changed(ObservableValue<? extends Number> observavel, Number valorAntigo, Number valorNovo){
        nave1.setVelocidade(sliderNaveVerde.getValue()); // A velocidade da nave eh setada com o valor do slider
      }
    });
    
    // Listener para o slider da nave azul. Assim que o usuario interagir com o slider, a velocidade sera modificada
    sliderNaveAzul.valueProperty().addListener(new ChangeListener<Number>() { 
      @Override
      public void changed(ObservableValue<? extends Number> observavel, Number valorAntigo, Number valorNovo){
        nave2.setVelocidade(sliderNaveAzul.getValue()); // A velocidade da nave eh setada com o valor do slider
      }
    });
  } // fim do initialize
} // fim da classe controllerTela3