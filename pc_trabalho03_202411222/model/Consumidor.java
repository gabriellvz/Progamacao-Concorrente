/* ***************************************************************
* Autor............: Gabriel Alves Cruz 
* Matricula........: 202411222
* Inicio...........: 08/05/2026
* Ultima alteracao.: 20/05/2026
* Nome.............: Consumidor.java
* Funcao...........: Definir comportamento da classe Consumidor
*************************************************************** */

package model;

import controller.ControleTelaPrincipal;
import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class Consumidor extends Thread {

  private ImageView imgConsumidor;
  private int velocidadeConsumidor;
  private Slider sliderVelConsumidor;
  private boolean continuar = true;

  int posX = 524; // posicao inicial do consumidor 

  Animacao a = new Animacao();
  ControleTelaPrincipal control = new ControleTelaPrincipal();

  /*
   * ***************************************************************
   * Metodo: Consumidor 
   * Funcao: Retorna objeto do tipo consumidor
   * Parametros: ControllerTelaPrincipal controller, ImageView imagem, Slider sliderProd
   * Retorno: retorna um objeto do tipo produtor
   * ***************************************************************
   */

  public Consumidor(ControleTelaPrincipal controller, ImageView imagem, Slider sliderProd) {
    this.control = controller;
    this.imgConsumidor = imagem;
    this.sliderVelConsumidor = sliderProd;
    this.velocidadeConsumidor = (int) sliderVelConsumidor.getValue(); // valor da velocidade eh atribuido com o valor do slider 
  }

  /*
   * ***************************************************************
   * Metodo: getImagemConsumidor
   * Funcao: retornar imagem do consumidor
   * Parametros: sem parametros
   * Retorno: ImageView
   * ***************************************************************
   */

  public ImageView getImagemConsumidor() {
    return imgConsumidor;
  }

  /*
   * ***************************************************************
   * Metodo: setImagemConsumidor
   * Funcao: alterar imagem do consumidor
   * Parametros: ImageView imgConsumidor
   * Retorno: void
   * ***************************************************************
   */

  public void setImagemConsumidor(ImageView imgConsumidor) {
    this.imgConsumidor = imgConsumidor;
  }

  /*
   * ***************************************************************
   * Metodo: getVelocidadeConsumidor
   * Funcao: obter velocidade do consumidor
   * Parametros: sem parametros
   * Retorno: int velocidadeConsumidor
   * ***************************************************************
   */

  public int getVelocidadeConsumidor() {
    return velocidadeConsumidor;
  }

  /*
   * ***************************************************************
   * Metodo: setVelocidadeConsumidor
   * Funcao: alterar velocidade do consumidor
   * Parametros: int velocidadeConsumidor
   * Retorno: void
   * ***************************************************************
   */

  public void setVelocidadeConsumidor(int velocidadeConsumidor) {
    this.velocidadeConsumidor = velocidadeConsumidor;
  }

  /*
   * ***************************************************************
   * Metodo: getContinuar
   * Funcao: obter valor de continuar
   * Parametros: sem parametros
   * Retorno: boolean continuar
   * ***************************************************************
   */

  public boolean getContinuar() {
    return continuar;
  }

  /*
   * ***************************************************************
   * Metodo: setContinuar
   * Funcao: alterar valor de continuar
   * Parametros: boolean continuar
   * Retorno: void 
   * ***************************************************************
   */

  public void setContinuar(boolean continuar) {
    this.continuar = continuar;
  }

  /*
   * ***************************************************************
   * Metodo: definirVelocidadeConsumo 
   * Funcao: definir velocidade de consumo para o consumidor atraves dos valores dos sliders
   * Parametros: sem parametros
   * Retorno: int velcidadeConsumidor
   * ***************************************************************
   */

  public int definirVelocidadeConsumo() {

    if (sliderVelConsumidor.getValue() == 1) {
      this.velocidadeConsumidor = 120;
    } else if (sliderVelConsumidor.getValue() == 2) {
      this.velocidadeConsumidor = 80;
    } else if (sliderVelConsumidor.getValue() == 3) {
      this.velocidadeConsumidor = 40;
    }

    return velocidadeConsumidor;
  }

  /*
   * ***************************************************************
   * Metodo: consumir
   * Funcao: representar o consumo de itens
   * Parametros: sem parametros definidos
   * Retorno: void
   * ***************************************************************
   */

  public void consumir() {
    try {
      Thread.sleep(definirVelocidadeConsumo());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /*
   * ***************************************************************
   * Metodo: removeItem
   * Funcao: remover itens do buffer
   * Parametros: sem parametros
   * Retorno: void
   * ***************************************************************
   */

  public void removeItem() {

    if (control.getPeixe3().isVisible()) {
      control.getPeixe3().setVisible(false);
    } else if (control.getPeixe2().isVisible()) {
      control.getPeixe2().setVisible(false);
    } else if (control.getPeixe1().isVisible()) {
      control.getPeixe1().setVisible(false);
    }

  }

  /*
   * ***************************************************************
   * Metodo: moverConsumidorEsq
   * Funcao: mover consumidor para a esquerda
   * Parametros: ImageView img, int posFinal
   * Retorno: void
   * ***************************************************************
   */

  public void moverConsumidorEsq(ImageView img, int posFinal) {

    while (posX >= posFinal) {
      try {
        Thread.sleep(definirVelocidadeConsumo());
        a.animarImagem(img, 288, 48, 6);
        Platform.runLater(() -> {
          img.setLayoutX(posX);
        });
        posX = posX - 5;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }
  }

  /*
   * ***************************************************************
   * Metodo: moverConsumidorDir 
   * Funcao: realizar o movimento do consumidor para a direita
   * Parametros: ImageView img, int posFinal
   * Retorno: void
   * ***************************************************************
   */

  public void moverConsumidorDir(ImageView img, int posFinal) {

    while (posX <= posFinal) {
      try {
        Thread.sleep(definirVelocidadeConsumo());
        a.animarImagem(img, 288, 48, 6);
        Platform.runLater(() -> {
          img.setLayoutX(posX);
        });
        posX = posX + 5;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }

  /*
   * ***************************************************************
   * Metodo: moverConsumidorIda
   * Funcao: realizar chamada de metodos para o movimento de ida do consumidor
   * Parametros: sem parametros
   * Retorno: void
   * ***************************************************************
   */

  public void moverConsumidorIda() {

    a.trocarSpritesheet(imgConsumidor, a.getSpritesCachorro1());
    moverConsumidorEsq(imgConsumidor, 325);

  }

  /*
   * ***************************************************************
   * Metodo: moverConsumidorVolta
   * Funcao: realizar chamada de metodos para a volta do consumidor
   * Parametros: sem parametros
   * Retorno: void
   * ***************************************************************
   */

  public void moverConsumidorVolta() {

    a.trocarSpritesheet(imgConsumidor, a.getSpritesCachorro2());
    moverConsumidorDir(imgConsumidor, 524);

  }

  /*
   * ***************************************************************
   * Metodo: run
   * Funcao: executar loop da thread do consumidor
   * Parametros: sem parametros
   * Retorno: void
   * ***************************************************************
   */

  @Override
  public void run() {

    try {
      while (true) {

        moverConsumidorIda(); // metodo que anima a imagem para mover a esquerda

        ControleTelaPrincipal.cheio.acquire(); // decrementa semaforo de posicoes ocupadas
        ControleTelaPrincipal.mutex.acquire(); // decrementa mutex para para fazer acesso a rc
        removeItem(); // remove item do buffer
        ControleTelaPrincipal.mutex.release(); // incrementa mutex quando o consmidor deixar a rc
        ControleTelaPrincipal.vazio.release(); // semaforo de posicoes vazia eh imcrementado

        moverConsumidorVolta(); // metodo que anima a imgame para mover a direita
        consumir(); // metodo que representa o consumo de itens
      }

    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

} // fim 
