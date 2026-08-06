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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Nave1;
import model.Nave2; 
import util.GerenciadorDeAudio;

public class ControllerTela3 implements Initializable{

  @FXML
  private ImageView imagemNave1, imagemNave2; // Imagens que serao manipuladas

  @FXML
  private Label textoDePosicao; // Label para representar a posicao escolhida

  @FXML
  private Slider sliderNaveVerde, sliderNaveAzul, sliderMusica; // Sliders para controle de velocidade e volume da musica

  @FXML
  private Button botaoIniciarTela3; 

  private Nave1 nave1; 

  private Nave2 nave2; 

  private int escolha; // Escolha do usuario

  private int solucao; 

  private Parent root; 

  private Scene scene;

  private Stage stage;
  
  private static final double VELOCIDADE_PADRAO = 1.0; // Velocidade padrao que sera setado no slider

  // Variavel de travamento 
  public static volatile int variavelTravamento1 = 0; 
  public static volatile int variavelTravamento2 = 0;

  // Estrita alternancia
  public static volatile int vezEstAlt1 = 0; 
  public static volatile int vezEstAlt2 = 0;

  // Solucao de peterson
  public static volatile int vez1SP = 0;
  public static volatile int vez2SP = 0;
  public static volatile boolean [] interesseNaRegiao1 = {false, false};
  public static volatile boolean [] interesseNaRegiao2 = {false, false}; 

  /* ***************************************************************
  * Metodo: configurarTela
  * Funcao: passar o valor de escolha da choice box para o atributo da classe ControllerTela3 e realizar a chamada do metodo de posicionar naves
  * Parametros: int escolha
  * Retorno: retorno do tipo void
  *************************************************************** */

  public void configurarTela (int escolha, int solucao){
    this.escolha = escolha; // o atributo da classe recebe o valor de escolha do usuario
    this.solucao = solucao; // o atributo da classe recebe o valor da solucao, escolhido pelo usuario 
    posicionarNaves();
  } // fim do metodo configurarTela
 
  /* ***************************************************************
  * Metodo: iniciarAnimacao
  * Funcao: Iniciar a animacao e definir a repeticao como indefinido
  * Parametros: ActionEvent event
  * Retorno: retorno do tipo void
  *************************************************************** */

  @FXML
  public void iniciarAnimacao(ActionEvent event){
    
    // desabilita botao de iniciar ao ser clicado para evitar chamadas desnecessarias dos metodos start das threads
    botaoIniciarTela3.setDisable(true);
    nave1.start();
    nave2.start();

  } // fim do metodo iniciarAnimacao

  /* ***************************************************************
  * Metodo: resetarAnimacao
  * Funcao: reseta a animacao das naves
  * Parametros: ActionEvent event
  * Retorno: retorno do tipo void
  *************************************************************** */

  @FXML
  public void resetarAnimacao (ActionEvent event) throws IOException{
    
    // habilita botao de iniciar 
    botaoIniciarTela3.setDisable(false);

    // resetando valores das variaveis ao reiniciar a animacao
    variavelTravamento1 = 0;
    variavelTravamento2 = 0; 
    vezEstAlt1 = 0;
    vezEstAlt2 = 0; 
    interesseNaRegiao1[0] = false;
    interesseNaRegiao1[1] = false;
    interesseNaRegiao2[0] = false;
    interesseNaRegiao2[1] = false; 

    definirVelocidadePadrao(); // Valores do sliders tambem sao resetados
    
    reset(); 

  } // fim do metodo resetarAnimacao

  /* ***************************************************************
  * Metodo: voltarParatela2
  * Funcao: definir uma acao para o botao de voltar para a tela 2
  * Parametros: ActionEvent event 
  * Retorno: retorno do tipo void
  *************************************************************** */

  @FXML
  public void voltarParaTela2 (ActionEvent event) throws IOException{

    // resetando variaveis quando a acao de voltar para a tela 2 eh executada 
    variavelTravamento1 = 0;
    variavelTravamento2 = 0; 
    vezEstAlt1 = 0;
    vezEstAlt2 = 0;  
    interesseNaRegiao1[0] = false;
    interesseNaRegiao1[1] = false;
    interesseNaRegiao2[0] = false;
    interesseNaRegiao2[1] = false; 

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
  } // fim do metodo fechar tela 

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
    nave1 = new Nave1(imagemNave1, 0, sliderNaveAzul, solucao);
    nave2 = new Nave2(imagemNave2, 1, sliderNaveVerde, solucao);
  } // fim do metodo definirPosicoes0
  
  /* ***************************************************************
  * Metodo: definirPosicoes1 
  * Funcao: definir a posicao das naves caso a escolha for superior direita e inferior direita
  * Parametros: sem parametros definidos
  * Retorno: retorno do tipo void
  *************************************************************** */

  // Caso o usuario escolher a opcao 1 em que a primeira nave parte da direita (em cima) e a segunda parte da direita (embaixo)
  private void definirPosicoes1 (){
    nave1 = new Nave1(imagemNave1, 2, sliderNaveAzul, solucao);
    nave2 = new Nave2(imagemNave2, 3, sliderNaveVerde, solucao);
  } // fim do metodo definirPosicoes1

  /* ***************************************************************
  * Metodo: definirPosicoes2
  * Funcao: definir a posicao das naves caso a escolha for superior esquerda e direita inferior
  * Parametros: sem parametros definidos 
  * Retorno: retorno do tipo void
  *************************************************************** */

  // Caso o usuario escolher a opcao 2 em que a primeira nave parte da esquerda (em cima) e a segunda parte da direita (embaixo)
  private void definirPosicoes2 (){
    nave1 = new Nave1(imagemNave1, 0, sliderNaveAzul, solucao);
    nave2 = new Nave2(imagemNave2, 3, sliderNaveVerde, solucao);
  } // fim do metodo definirPosicoes2

  /* ***************************************************************
  * Metodo: definirPosicoes3
  * Funcao: definir a posicoes das naves caso a escolha for inferior esquerda e superior direita
  * Parametros: sem parametros definidos
  * Retorno: retorno do tipo void 
  *************************************************************** */

  // Caso o usuario escolher a opcao 3 em que a nave1 parte da direita (em cima) e a nave2 parte da esquerda (embaixo)
  private void definirPosicoes3 (){
    nave1 = new Nave1(imagemNave1, 2, sliderNaveAzul, solucao);
    nave2 = new Nave2(imagemNave2, 1, sliderNaveVerde, solucao);
  } // fim do metodo definirPosicoes3

  /* ***************************************************************
  * Metodo: posicionar
  * Funcao: realiza a chamada de metodos para cada posicao das naves
  * Parametros: sem parametros definidos
  * Retorno: retorno do tipo void
  *************************************************************** */

  public void posicionarNaves (){
    
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
  * Metodo: getSliderMusica
  * Funcao: retornar o slider
  * Parametros: sem parametros 
  * Retorno: retorno do tipo Slider
  *************************************************************** */

  public Slider getSliderMusica(){
    return sliderMusica;
  }

  /* ***************************************************************
  * Metodo: reset
  * Funcao: resetar as naves
  * Parametros: sem parametros 
  * Retorno: retorno do tipo void
  *************************************************************** */

  public void reset (){
    nave1.parar();
    nave2.parar();
    posicionarNaves();
  }

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

    // listener para atualizar o volume da musica
    sliderMusica.valueProperty().addListener(new ChangeListener<Number>(){
      @Override
      public void changed(ObservableValue<? extends Number> observavel, Number valorAntigo, Number valorNovo){
        GerenciadorDeAudio.getMediaPlayer().setVolume(sliderMusica.getValue());
      }
    });

  } // fim do initialize
} // fim da classe controllerTela3