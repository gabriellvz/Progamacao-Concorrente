/* ***************************************************************
* Autor............: Gabriel Alves Cruz 
* Matricula........: 202411222
* Inicio...........: 08/05/2026
* Ultima alteracao.: 20/05/2026
* Nome.............: ControleTelaPrincipal.java
* Funcao...........: Criar metodos de controle para a tela principal do programa 
*************************************************************** */


package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Produtor;
import model.Consumidor;
import model.Animacao;

public class ControleTelaPrincipal implements Initializable {

  private Parent root;
  private Stage stage;
  private Scene scene;

  @FXML
  ImageView imgPeixeiro, imgCachorro;

  @FXML
  ImageView peixe1, peixe2, peixe3;

  @FXML
  Slider sliderProdutor, sliderConsumidor;

  @FXML
  ImageView botaoPausarProdutor, botaoPausarConsumidor;

  Produtor p;
  Consumidor c;

  Animacao a = new Animacao();

  static final int buffer = 3; // tamanho do buffer

  // semaforo que controla o acesso a recurso compartilhado
  public static Semaphore mutex = new Semaphore(1);

  // semaforo que controla o numero de posicoes vazias no buffer
  public static Semaphore vazio = new Semaphore(buffer);

  // semaforo que controla o numero de posicoes cheias no buffer
  public static Semaphore cheio = new Semaphore(0);

  /*
   * ***************************************************************
   * Metodo: getPeixe1
   * Funcao: retornar imagem do peixe1
   * Parametros: sem parametros
   * Retorno: ImageView peixe1
   * ***************************************************************
   */

  public ImageView getPeixe1() {
    return peixe1;
  }

  /*
   * ***************************************************************
   * Metodo: getPeixe2
   * Funcao: retornar imagem do peixe2
   * Parametros: sem parametros
   * Retorno: peixe2
   * ***************************************************************
   */

  public ImageView getPeixe2() {
    return peixe2;
  }

  /*
   * ***************************************************************
   * Metodo: getPeixe3 
   * Funcao: retornar a imagem do peixe 3
   * Parametros: sem parametros
   * Retorno: ImageView peixe3
   * ***************************************************************
   */

  public ImageView getPeixe3() {
    return peixe3;
  }

  /*
   * ***************************************************************
   * Metodo: getImgPeixeiro
   * Funcao: retornar imagem do peixeiro
   * Parametros: sem parametros
   * Retorno: imgPeixeiro
   * ***************************************************************
   */

  public ImageView getImgPeixeiro() {
    return imgPeixeiro;
  }

  /*
   * ***************************************************************
   * Metodo: ImageView 
   * Funcao: getImgCachorro
   * Parametros: sem parametros
   * Retorno: ImageView imgCachorro
   * ***************************************************************
   */

  public ImageView getImgCachorro() {
    return imgCachorro;
  }

  /*
   * ***************************************************************
   * Metodo: getSliderProdutor
   * Funcao: retornar slider do produtor
   * Parametros: sem parametros
   * Retorno: Slider sliderProdutor
   * ***************************************************************
   */

  public Slider getSliderProdutor() {
    return sliderProdutor;
  }

  /*
   * ***************************************************************
   * Metodo: voltarParaTela1
   * Funcao: Trocar para a primeira tela
   * Parametros: ActionEvent event
   * Retorno: void
   * ***************************************************************
   */

  @SuppressWarnings("deprecation")
  @FXML
  public void voltarParaTela1(ActionEvent event) throws IOException {

    // garante que as threads nao continuem executando quando alterar a tela
    p.stop();
    c.stop();

    // reinicia valores dos semaforos
    reiniciarValores();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaInicial.fxml")); // carrega o fxml da tela 1
    root = loader.load(); // define o no raiz como o fxml da tela 1
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // define o stage
    scene = new Scene(root); // instancia uma cena com um root
    stage.setScene(scene); // seta o stage com a cena criada
    scene.getStylesheets().add(getClass().getResource("/assets/css/EstilosTelaInicial.css").toExternalForm());
    stage.show(); // exibe o stage

  }

  /*
   * ***************************************************************
   * Metodo: fecharTela2
   * Funcao: encerrar a tela 2
   * Parametros: ActionEvent event
   * Retorno: void
   * ***************************************************************
   */

  @FXML
  public void fecharTela2(ActionEvent event) {
    System.exit(0);
  }

  /*
   * ***************************************************************
   * Metodo: pausarProdutor
   * Funcao: pausar a Thread de producao
   * Parametros: MouseEvent event
   * Retorno: void
   * ***************************************************************
   */

  @SuppressWarnings("deprecation")
  @FXML
  public void pausarProdutor(MouseEvent event) {
    // criando imagens
    Image imagemRetomada = new Image("assets/image/imagem-retomar-produtor.png");
    Image imagemPausa = new Image("assets/image/imagem-pausar-produtor.png");

    // verificando a flag da thread Produtor
    if (p.getContinuar()) {
      p.setContinuar(false); 
      p.suspend(); // pausa a thread 
      botaoPausarProdutor.setImage(imagemRetomada);
      sliderProdutor.setDisable(true);
    } else {
      p.setContinuar(true);
      p.resume();
      botaoPausarProdutor.setImage(imagemPausa);
      sliderProdutor.setDisable(false);
    }

  }

  /*
   * ***************************************************************
   * Metodo: pausaConsumidor
   * Funcao: pausar a thread do consumidor
   * Parametros: controller, imagem, sliderProd
   * Retorno: retorna um objeto do tipo produtor
   * ***************************************************************
   */

  @SuppressWarnings("deprecation")
  @FXML
  public void pausarConsumidor(MouseEvent event) {
    // criando imagens
    Image imagemRetomada = new Image("assets/image/imagem-retomar-consumidor.png");
    Image imagemPausa = new Image("assets/image/imagem-pausar-consumidor.png");

    // verificando flag do consumidor
    if (c.getContinuar()) {
      c.setContinuar(false);
      c.suspend(); // garante que a thread seja pausada
      botaoPausarConsumidor.setImage(imagemRetomada);
      sliderConsumidor.setDisable(true);
    } else {
      c.setContinuar(true);
      c.resume();
      botaoPausarConsumidor.setImage(imagemPausa);
      sliderConsumidor.setDisable(false);
    }

  }

  /*
   * ***************************************************************
   * Metodo: reiniciarAnimacao
   * Funcao: resetar a animacao da tela principal
   * Parametros: MouseEvent event
   * Retorno: void
   * ***************************************************************
   */

  @SuppressWarnings("deprecation")
  @FXML
  public void reiniciarAnimacao(MouseEvent event) {

    // criando imagens dos botoes
    Image imagemPausaProd = new Image("assets/image/imagem-pausar-produtor.png");
    Image imagemPausaConsu = new Image("assets/image/imagem-pausar-consumidor.png");

    // metodos que encerram a execucao das thread 
    p.stop();
    c.stop();

    // intanciando os objetos novamente
    p = new Produtor(this, imgPeixeiro, sliderProdutor);
    c = new Consumidor(this, imgCachorro, sliderConsumidor);

    // anterando imagens dos botoes
    botaoPausarProdutor.setImage(imagemPausaProd);
    botaoPausarConsumidor.setImage(imagemPausaConsu);

    // alterando visibilidade das imagens
    peixe1.setVisible(false);
    peixe2.setVisible(false);
    peixe3.setVisible(false);

    // reiniciando valores dos semaforos
    reiniciarValores();

    // resetando sliders 
    sliderProdutor.setValue(2);
    sliderConsumidor.setValue(2);

    // resetando visibilidade dos botoes
    sliderProdutor.setDisable(false);
    sliderConsumidor.setDisable(false);

    // iniciando as threads
    p.start();
    c.start();
  }

  /*
   * ***************************************************************
   * Metodo: reiniciarValores
   * Funcao: intanciar os semaforos com seus respectivos semaforos
   * Parametros: sem parametros
   * Retorno: void
   * ***************************************************************
   */

  public void reiniciarValores() {
    mutex = new Semaphore(1);
    vazio = new Semaphore(buffer); // buffer = 3;
    cheio = new Semaphore(0);
  }

  /*
   * ***************************************************************
   * Metodo: initialize
   * Funcao: inicializar componentes assim que o controler for criado
   * Parametros: URL url, ResourceBundle rc
   * Retorno: void
   * ***************************************************************
   */

  public void initialize(URL url, ResourceBundle rc) {

    p = new Produtor(this, imgPeixeiro, sliderProdutor);
    c = new Consumidor(this, imgCachorro, sliderConsumidor);

    // metodos para animar as imagens
    a.animarImagem(imgPeixeiro, 972, 256, 6);
    a.animarImagem(imgCachorro, 288, 48, 6);

    // configurando slider do Produtor
    sliderProdutor.setMin(1);
    sliderProdutor.setMax(3);
    sliderProdutor.setValue(2);
    sliderProdutor.setMajorTickUnit(1);
    sliderProdutor.setMinorTickCount(0);
    sliderProdutor.setSnapToTicks(true);
    sliderProdutor.setShowTickLabels(true);

    // configurando slider do Consumidor
    sliderConsumidor.setMin(1);
    sliderConsumidor.setMax(3);
    sliderConsumidor.setValue(2);
    sliderConsumidor.setMajorTickUnit(1);
    sliderConsumidor.setMinorTickCount(0);
    sliderConsumidor.setSnapToTicks(true);
    sliderConsumidor.setShowTickLabels(true);

    // iniciando a visibilidade dos peixes
    peixe1.setVisible(false);
    peixe2.setVisible(false);
    peixe3.setVisible(false);

    // iniciando as threads
    p.start();
    c.start();
  }
} // fim
