/* ***************************************************************
* Autor............: Gabriel Alves Cruz 
* Matricula........: 202411222
* Inicio...........: 24/06/2026
* Ultima alteracao.: 04/06/2026
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Comentarista;

public class ControleTelaPrincipal implements Initializable {

  private Parent root;
  private Stage stage;
  private Scene scene;

  @FXML
  ImageView garfo01, garfo02, garfo03, garfo04, garfo05;

  @FXML
  ImageView prato01, prato02, prato03, prato04, prato05;

  @FXML
  Slider slider01Pensando, slider02Pensando, slider03Pensando, slider04Pensando, slider05Pensando,
         slider01Comendo, slider02Comendo, slider03Comendo, slider04Comendo, slider05Comendo;

  @FXML
  ImageView comentarista01_pensando, comentarista01_comendo,
      comentarista02_pensando, comentarista02_comendo,
      comentarista03_pensando, comentarista03_comendo,
      comentarista04_pensando, comentarista04_comendo,
      comentarista05_pensando, comentarista05_comendo;


  @FXML
  Button botaoPausar01, botaoPausar02, botaoPausar03, botaoPausar04, botaoPausar05;

  @FXML
  Label label01, label02, label03, label04, label05;
  
  public ImageView[] imgGarfos;
  public ImageView[] imgPratos;
  public ImageView[][] imgComentaristas;
  public Slider[] slidersPensando;
  public Slider[] slidersComendo;
  public Label [] labelsDeEstado; 

  Image imgPratoCheio;
  Image imgPratoVazio;

  Comentarista[] comentaristas = new Comentarista[Comentarista.NUM_COMENTARISTAS];

  /*
   * ***************************************************************
   * Metodo: voltarParaTela1
   * Funcao: Trocar para a primeira tela
   * Parametros: ActionEvent event
   * Retorno: void
   * ***************************************************************
   */

  @FXML
  public void voltarTela(MouseEvent event) throws IOException {
    interromperThreads();

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
  public void fecharTela(MouseEvent event) {
    System.exit(0);
  }

  /*
  * ***************************************************************
  * Metodo: alternarEstadoVisual
  * Funcao: alternar imagens de estado dos comentaristas
  * Parametros: int i, int estadoComentarista
  * Retorno: void
  * ***************************************************************
  */

  public void alternarEstadoVisual(int i, int estadoComentarista) {
    esconderImagens(i);

    if (estadoComentarista == Comentarista.PENSANDO) {
      imgComentaristas[i][0].setVisible(true);
      imgPratos[i].setImage(imgPratoVazio);

    } else if (estadoComentarista == Comentarista.COMENDO) {
      imgComentaristas[i][1].setVisible(true);
      imgPratos[i].setImage(imgPratoCheio);
    }

  }

   /*
  * ***************************************************************
  * Metodo: alternarVisibilidadesGarfos
  * Funcao: alternar visibiladades dos garfos na mesa
  * Parametros: int id, int idDireita, boolean visibilidade
  * Retorno: void
  * ***************************************************************
  */

  public void alternarVisibilidadeGarfos(int id, int idDireita, boolean visibilidade) {
    imgGarfos[id].setVisible(visibilidade); // visibilidade garfo da esquerda
    imgGarfos[idDireita].setVisible(visibilidade); // visibilidade garfo da direita
  }

  /*
  * ***************************************************************
  * Metodo: esconderImagens
  * Funcao: esconder imagens dos comentaristas
  * Parametros: int idComentarista
  * Retorno: void
  * ***************************************************************
  */

  public void esconderImagens(int idComentarista) {
    for (int i = 0; i <= 1; i++) {
      imgComentaristas[idComentarista][i].setVisible(false);
    }
  }


  /*
  * ***************************************************************
  * Metodo: pausarComentarista01
  * Funcao: pausa e retoma o comentarista01
  * Parametros: ActionEvent event
  * Retorno: void
  * ***************************************************************
  */

  @FXML
  public void pausarComentarista01 (ActionEvent event){

    if (!comentaristas[0].getPausado()){
      botaoPausar01.setText("RETOMAR");
      botaoPausar01.setLayoutX(142);
      comentaristas[0].pausar();
      slider01Pensando.setDisable(true);
      slider01Comendo.setDisable(true);
    } 
    else{
      botaoPausar01.setText("PAUSAR");
      botaoPausar01.setLayoutX(150);
      comentaristas[0].retomar();
      slider01Pensando.setDisable(false);
      slider01Comendo.setDisable(false);
    } 

  }

  /*
  * ***************************************************************
  * Metodo: pausaComentarista02
  * Funcao: pausa e retoma o comentarista02
  * Parametros: ActionEvent event
  * Retorno: void
  * ***************************************************************
  */

  @FXML
  public void pausarComentarista02 (ActionEvent event){

    if (!comentaristas[1].getPausado()){
      botaoPausar02.setText("RETOMAR");
      botaoPausar02.setLayoutX(457);
      comentaristas[1].pausar();
      slider02Pensando.setDisable(true);
      slider02Comendo.setDisable(true);
    } 
    else{
      botaoPausar02.setText("PAUSAR");
      botaoPausar02.setLayoutX(465);
      comentaristas[1].retomar();
      slider02Pensando.setDisable(false);
      slider02Comendo.setDisable(false);
    } 

  }

  /*
  * ***************************************************************
  * Metodo: pausarComentarista03
  * Funcao: pausa e retoma o comentarista03
  * Parametros: ActionEvent event
  * Retorno: void
  * ***************************************************************
  */

  @FXML
  public void pausarComentarista03 (ActionEvent event){

    if (!comentaristas[2].getPausado()){
      botaoPausar03.setText("RETOMAR");
      botaoPausar03.setLayoutX(457);
      comentaristas[2].pausar();
      slider03Pensando.setDisable(true);
      slider03Comendo.setDisable(true);
    } 
    else{
      botaoPausar03.setText("PAUSAR");
      botaoPausar03.setLayoutX(465);
      comentaristas[2].retomar();
      slider03Pensando.setDisable(false);
      slider03Comendo.setDisable(false);
    } 
  }

  /*
  * ***************************************************************
  * Metodo: pausarComentarista04
  * Funcao: pausa e retoma o comentarista04
  * Parametros: ActionEvent event
  * Retorno: void
  * ***************************************************************
  */

  @FXML
  public void pausarComentarista04 (ActionEvent event){

    if (!comentaristas[3].getPausado()){
      botaoPausar04.setText("RETOMAR");
      botaoPausar04.setLayoutX(47);
      comentaristas[3].pausar();
      slider04Pensando.setDisable(true);
      slider04Comendo.setDisable(true);
    } 
    else{
      botaoPausar04.setText("PAUSAR");
      botaoPausar04.setLayoutX(55);
      comentaristas[3].retomar();
      slider04Pensando.setDisable(false);
      slider04Comendo.setDisable(false);
    } 

  }

  /*
  * ***************************************************************
  * Metodo: pausarComentarista05
  * Funcao: pausa e retoma o comentarista05
  * Parametros: ActionEvent event
  * Retorno: void
  * ***************************************************************
  */

  @FXML
  public void pausarComentarista05 (ActionEvent event){

    if (!comentaristas[4].getPausado()){
      botaoPausar05.setText("RETOMAR");
      botaoPausar05.setLayoutX(47);
      comentaristas[4].pausar();
      slider05Pensando.setDisable(true);
      slider05Comendo.setDisable(true);
    } 
    else{
      botaoPausar05.setText("PAUSAR");
      botaoPausar05.setLayoutX(55);
      comentaristas[4].retomar();
      slider05Pensando.setDisable(false);
      slider05Comendo.setDisable(false);
    } 

  }

  /*
  * ***************************************************************
  * Metodo: configurarSlidersPensando
  * Funcao: inicializa os sliders  
  * Parametros: ActionEvent event
  * Retorno: void
  * ***************************************************************
  */

  public void configurarSlidersPensando (){
    for (int i = 0; i < comentaristas.length; i++){
      slidersPensando[i].setMin(1);
      slidersPensando[i].setMax(3);
      slidersPensando[i].setValue(2);
      slidersPensando[i].setMajorTickUnit(1);
      slidersPensando[i].setMinorTickCount(0);
      slidersPensando[i].setSnapToTicks(true);
      slidersPensando[i].setShowTickLabels(true);
    }
  }

  /*
  * ***************************************************************
  * Metodo: configurarSlidersComendo
  * Funcao: incializa os sliders 
  * Parametros: sem parametros
  * Retorno: void
  * ***************************************************************
  */

  public void configurarSlidersComendo (){
    for (int i = 0; i < comentaristas.length; i++){
      slidersComendo[i].setMin(1);
      slidersComendo[i].setMax(3);
      slidersComendo[i].setValue(2);
      slidersComendo[i].setMajorTickUnit(1);
      slidersComendo[i].setMinorTickCount(0);
      slidersComendo[i].setSnapToTicks(true);
      slidersComendo[i].setShowTickLabels(true);
    }
  }

  /*
  * ***************************************************************
  * Metodo: reiniciarTextoDosBotoes
  * Funcao: garantir que os botes tenham seus textos atualizados ao reiniciar a simulacao
  * Parametros: sem parametros
  * Retorno: void
  * ***************************************************************
  */

  public void reiniciarTextoDosBotoes (){
    botaoPausar01.setText("PAUSAR");
    botaoPausar02.setText("PAUSAR");
    botaoPausar03.setText("PAUSAR");
    botaoPausar04.setText("PAUSAR");
    botaoPausar05.setText("PAUSAR");
  }

  /*
  * ***************************************************************
  * Metodo: alterarTextoDeEstado
  * Funcao: alterar texto de estato das labels
  * Parametros: int id, int estado
  * Retorno: void
  * ***************************************************************
  */

  public void alterarTextoDeEstado(int id, int estado){
    if (estado == 0){    
      labelsDeEstado[id].setText("Pensando");
    }
    else if (estado == 2){
      labelsDeEstado[id].setText("Comendo");
    }
  }

  /*
  * ***************************************************************
  * Metodo: interromperThreads
  * Funcao: interrompe as threads dos comentaristas
  * Parametros: sem parametros
  * Retorno: void
  * ***************************************************************
  */

  public void interromperThreads(){
    
    // interrompendo as threads
    // interrupt sinaliza que as threads serao interrompidas
    for (Comentarista c: comentaristas){
      c.interrupt();
    }
    
    // join espera que todas as threads terminem sua execucao
    for (Comentarista c: comentaristas){
      try{
        c.join(); 
      } catch (InterruptedException e){};
    }
    
  }

  /*
  * ***************************************************************
  * Metodo: configurarPosicoesDosBotoes
  * Funcao: garantir que os botes sejam reiniciados na posicao correta
  * Parametros: sem parametros
  * Retorno: void
  * ***************************************************************
  */

  public void configurarPosicoesDosBotoes(){
    botaoPausar01.setLayoutX(150);
    botaoPausar02.setLayoutX(465);
    botaoPausar03.setLayoutX(465);
    botaoPausar04.setLayoutX(55);
    botaoPausar05.setLayoutX(55);
  }

  /*
  * ***************************************************************
  * Metodo: reiniciarSimulacao
  * Funcao: reinicar a simulacao, resetando todos os valores
  * Parametros: ActionEvent event
  * Retorno: void
  * ***************************************************************
  */

  @FXML
  public void reiniciarSimulacao (ActionEvent event){
 
    interromperThreads();
    
    reiniciarTextoDosBotoes();
    configurarPosicoesDosBotoes();

    for (int i = 0; i < labelsDeEstado.length; i++){
      labelsDeEstado[i].setText("Pensando");
    }

    // instancia os objetos
    for (int i = 0; i < comentaristas.length; i++) {
      alternarVisibilidadeGarfos(i, comentaristas[i].direita(i), true);
      Comentarista.semaforos[i] = new Semaphore(0);
      Comentarista.estado[i] = Comentarista.PENSANDO;
      comentaristas[i] = new Comentarista(this, i, slidersPensando[i], slidersComendo[i]);
    }
    

    for (int i = 0; i < comentaristas.length; i++){
      slidersPensando[i].setValue(2);
      slidersPensando[i].setDisable(false);
      slidersComendo[i].setValue(2);
      slidersComendo[i].setDisable(false);      
    }
   
    for (Comentarista c : comentaristas) {
      c.start();
    }

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

    imgGarfos = new ImageView[] {garfo01, garfo02, garfo03, garfo04, garfo05};

    imgPratos = new ImageView[] {prato01, prato02, prato03, prato04, prato05};

    imgComentaristas = new ImageView[][] {
        {comentarista01_pensando, comentarista01_comendo},
        {comentarista02_pensando, comentarista02_comendo},
        {comentarista03_pensando, comentarista03_comendo},
        {comentarista04_pensando, comentarista04_comendo},
        {comentarista05_pensando, comentarista05_comendo}
    };

    slidersPensando = new Slider[] {slider01Pensando, slider02Pensando, slider03Pensando, slider04Pensando, slider05Pensando};
    slidersComendo = new Slider[] {slider01Comendo, slider02Comendo, slider03Comendo, slider04Comendo, slider05Comendo};

    imgPratoCheio = new Image(getClass().getResourceAsStream("/assets/image/imagem-prato-macarrao.png"));
    imgPratoVazio = new Image(getClass().getResourceAsStream("/assets/image/imagem-prato-vazio.png"));

    labelsDeEstado = new Label[] {label01,label02,label03,label04,label05};

    configurarSlidersPensando();
    configurarSlidersComendo();
    
    // instancia os objetos
    for (int i = 0; i < comentaristas.length; i++) {
      comentaristas[i] = new Comentarista(this, i, slidersPensando[i], slidersComendo[i]);
      Comentarista.semaforos[i] = new Semaphore(0);
    }

    for (Comentarista c : comentaristas) {
      c.start();
    }

  }

} // fim
