/* ***************************************************************
* Autor............: Gabriel Alves Cruz 
* Matricula........: 202411222
* Inicio...........: 08/05/2026
* Ultima alteracao.: 21/05/2026
* Nome.............: Produtor.java
* Funcao...........: Definir comportamento da classe Produtor 
*************************************************************** */


package model;

import javafx.scene.image.ImageView;
import javafx.scene.control.Slider;
import controller.ControleTelaPrincipal;
import javafx.application.Platform;

public class Produtor extends Thread {

  private ImageView imgProdutor;
  private int velocidadeProd;
  private Slider sliderVelocidadeProd;
  private boolean continuar = true;

  int posX = 74; // posicao inicial do produtor

  Animacao a = new Animacao();
  ControleTelaPrincipal control = new ControleTelaPrincipal();

  /*
   * ***************************************************************
   * Metodo: Produtor
   * Funcao: Construtor para a classe Produtor
   * Parametros: controller, imagem, sliderProd
   * Retorno: retorna um objeto do tipo produtor
   * ***************************************************************
   */

  public Produtor(ControleTelaPrincipal controller, ImageView imagem, Slider sliderProd) {
    this.control = controller;
    this.imgProdutor = imagem;
    this.sliderVelocidadeProd = sliderProd;
    this.velocidadeProd = (int) sliderVelocidadeProd.getValue(); // seta o valor do slider como velocidade de producao
  }

  
  /*
   * ***************************************************************
   * Metodo: getImagemProdutor
   * Funcao: retornar imagem do produtor
   * Parametros: sem parametros
   * Retorno: imgProdutor
   * ***************************************************************
   */

  public ImageView getImagemProdutor() {
    return imgProdutor;
  }

  
  /*
   * ***************************************************************
   * Metodo: setImagemProdutor
   * Funcao: alterar imagem do produtor
   * Parametros: imagemProdutor
   * Retorno: void
   * ***************************************************************
   */

  public void setImagemProdutor(ImageView imagemProdutor) {
    this.imgProdutor = imagemProdutor;
  }

  
  /*
   * ***************************************************************
   * Metodo: getVelocidadeProd
   * Funcao: retornar velocidade de producao
   * Parametros: sem parametros
   * Retorno: int velocidade prod
   * ***************************************************************
   */

  public int getVelocidadeProd() {
    return velocidadeProd;
  }

  
  /*
   * ***************************************************************
   * Metodo: setVelocidadeProd 
   * Funcao: alterar valor da velocidade de producao
   * Parametros: int velocidadeProd
   * Retorno: void
   * ***************************************************************
   */

  public void setVelocidadeProd(int velocidadeProd) {
    this.velocidadeProd = velocidadeProd;
  }

  
  /*
   * ***************************************************************
   * Metodo: getContinuar
   * Funcao: retornar variavel continuar
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
   * Funcao: alterar valor da variavel continuar
   * Parametros: continuar
   * Retorno: void
   * ***************************************************************
   */

  public void setContinuar(boolean continuar) {
    this.continuar = continuar;
  }

  /*
   * ***************************************************************
   * Metodo: definirVelocidadeProducao
   * Funcao: definir velocidade de producao para diferentes valores do slider
   * Parametros: sem parametros
   * Retorno: int
   * ***************************************************************
   */

  public int definirVelocidadeProducao() {

    if (sliderVelocidadeProd.getValue() == 1) {
      this.velocidadeProd = 120;
    } else if (sliderVelocidadeProd.getValue() == 2) {
      this.velocidadeProd = 80;
    } else if (sliderVelocidadeProd.getValue() == 3) {
      this.velocidadeProd = 40;
    }

    return velocidadeProd;
  }

  /*
   * ***************************************************************
   * Metodo: produzirItem
   * Funcao: representar a producao de itens
   * Parametros: sem parametros
   * Retorno: void
   * ***************************************************************
   */

  // produzir item na interface visual representa o produtor saindo da mesa com
  // varios peixes e indo ate a bancada
  public void produzirItem() {
    try {
      Thread.sleep(definirVelocidadeProducao());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  
  /*
   * ***************************************************************
   * Metodo: insereItem
   * Funcao: inserir itens no buffer
   * Parametros: sem parametros
   * Retorno: void
   * ***************************************************************
   */


  public void insereItem() {

    if (!control.getPeixe1().isVisible()) {
      control.getPeixe1().setVisible(true);
    } else if (!control.getPeixe2().isVisible()) {
      control.getPeixe2().setVisible(true);
    } else if (!control.getPeixe3().isVisible()) {
      control.getPeixe3().setVisible(true);
    }

  }
  
  /*
   * ***************************************************************
   * Metodo: moverProdutorDir
   * Funcao: movimentar o produtor para a direita
   * Parametros: img, posFinal
   * Retorno: void
   * ***************************************************************
   */

  void moverProdutorDir(ImageView img, int posFinal) {

    while (posX <= posFinal) {
      try {
        Thread.sleep(definirVelocidadeProducao());
        a.animarImagem(img, 972, 256, 6);
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
   * Metodo: moverProdutorEsq
   * Funcao: movimentar o produtor para esquerda
   * Parametros: img, posFinal
   * Retorno: void
   * ***************************************************************
   */

  void moverProdutorEsq(ImageView img, int posFinal) {

    while (posX >= posFinal) {
      try {
        Thread.sleep(definirVelocidadeProducao());
        a.animarImagem(img, 972, 256, 6);
        Platform.runLater(() -> {
          img.setLayoutX(posX);
        });
        posX = posX - 5;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    ;

  }

  
  /*
   * ***************************************************************
   * Metodo: moverProdutorIda
   * Funcao: realizar chamada de metodos para o caminho de volta do produtor
   * Parametros: sem parametros 
   * Retorno: void
   * ***************************************************************
   */

  public void moverProdutorIda() {

    a.trocarSpritesheet(imgProdutor, a.getSpritesPeixeiro1());
    moverProdutorDir(imgProdutor, 225);

  }

  
  /*
   * ***************************************************************
   * Metodo: moverProdutorVolta
   * Funcao: realizar chamada de metodos para o caminho de volta do produtor
   * Parametros: sem parametros
   * Retorno: void
   * ***************************************************************
   */

  // adaptar os metodos para evitar o alto acoplamento
  public void moverProdutorVolta() {

    a.trocarSpritesheet(imgProdutor, a.getSpritesPeixeiro2());
    moverProdutorEsq(imgProdutor, 74);

  }
  
  /*
   * ***************************************************************
   * Metodo: run
   * Funcao: realizar loop da Thread Produtor
   * Parametros: sem parametros
   * Retorno: void
   * ***************************************************************
   */

  @Override
  public void run() {

    try {

      while (true) {

        produzirItem();

        moverProdutorIda();

        ControleTelaPrincipal.vazio.acquire(); // decrementa posicoes vazias pois um item sera inserido no buffer
        ControleTelaPrincipal.mutex.acquire(); // decrementa o mutex para proteger o recurso compartilhado
        insereItem(); // onde ocorre o acesso ao recurso compartilhado (regiao critica)
        ControleTelaPrincipal.mutex.release(); // mutex eh incrementando sinalizando que foi retirado da fila de espera
        ControleTelaPrincipal.cheio.release(); // cheio eh incrementado, pois um item foi inserido no buffer
        System.out.println(ControleTelaPrincipal.cheio);
        moverProdutorVolta();

      }

    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

}
