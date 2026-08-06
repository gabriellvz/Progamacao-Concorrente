/* ***************************************************************
* Autor............: Gabriel Alves Cruz 
* Matricula........: 202411222
* Inicio...........: 08/05/2026
* Ultima alteracao.: 16/05/2026
* Nome.............: Animacao.java
* Funcao...........: Criar metodos para animar as sprites do Produtor e do Consumidor 
*************************************************************** */

package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;

public class Animacao {

  // criando sprites
  private Image spritesPeixeiro1 = new Image(getClass().getResourceAsStream("/assets/image/spritesheet-andando1-peixeiro.png"));
  private Image spritesPeixeiro2 = new Image(getClass().getResourceAsStream("/assets/image/spritesheet-andando2-peixeiro.png"));
  private Image spritesCachorro1 = new Image(getClass().getResourceAsStream("/assets/image/spritesheet-andando1-cachorro.png"));
  private Image spritesCachorro2 = new Image(getClass().getResourceAsStream("/assets/image/spritesheet-andando2-cachorro.png"));

  int larguraFrame; // variavel para a largura do frame
  int frameAtual = 0; // frame atual que sera exibido no viewport

  /*
   * ***************************************************************
   * Metodo: getSpritesPeixeiro1
   * Funcao: retorna imagem do primeiro movimento do peixeiro
   * Parametros: controller, imagem, sliderProd
   * Retorno: retorna um objeto do tipo produtor
   * ***************************************************************
   */

  public Image getSpritesPeixeiro1() {
    return spritesPeixeiro1;
  }

  /*
   * ***************************************************************
   * Metodo: getSpritesPeixeiro2
   * Funcao: retornar imagem do segundo movimento do peixeiro
   * Parametros: sem parametros
   * Retorno: Image spritesPeixeiro2
   * ***************************************************************
   */

  public Image getSpritesPeixeiro2() {
    return spritesPeixeiro2;
  }

  /*
   * ***************************************************************
   * Metodo: getSpritesCachorro1
   * Funcao: obter sprites do primeiro movimento do cachorro
   * Parametros: sem parametros
   * Retorno: Image spritescachorro1
   * ***************************************************************
   */

  public Image getSpritesCachorro1() {
    return spritesCachorro1;
  }

  /*
   * ***************************************************************
   * Metodo: getSpritesCachorro2
   * Funcao: retornar imagem do segundo movimento do cachorro 
   * Parametros: sem parametros
   * Retorno: Image sprites cachorro2
   * ***************************************************************
   */

  public Image getSpritesCachorro2() {
    return spritesCachorro2;
  }

  /*
   * ***************************************************************
   * Metodo: animarImagem
   * Funcao: animar imagens frame a frame
   * Parametros: ImageView img, int larguraImagem, int alturaFrame, int totalframes
   * Retorno: void
   * ***************************************************************
   */

  public void animarImagem(ImageView img, int larguraImagem, int alturaFrame, int totalFrames) {

    // variavel de controle que sera incrimentada a cada frame, e para reiniciar eh
    // feita uma operacao com o resto da divisao pelo total de frames
    this.frameAtual = (this.frameAtual + 1) % totalFrames;

    // variavel que representa a largura de cada frame
    this.larguraFrame = larguraImagem / totalFrames; // 972 eh a largura total da imagem e 6 eh a quantidade de frames

    // indica qual sera o pixel a ser mostrado no viewport
    int x = this.frameAtual * larguraFrame;

    // altera a visualizacao da imagem para uma sprite por vez
    // x indica o pixel do eixoX e y indica o pixel do eixoY

    Platform.runLater(() -> {
      img.setViewport(new Rectangle2D(x, 0, larguraFrame, alturaFrame));
    });

  }

  /*
   * ***************************************************************
   * Metodo: trocarSpritesheet
   * Funcao: realizar a troca de spritesheet das imagens
   * Parametros: ImageView img, Image spritesheet
   * Retorno: void
   * ***************************************************************
   */

  public void trocarSpritesheet(ImageView img, Image spritesheet) {
    Platform.runLater(() -> {
      img.setImage(spritesheet);
    });
  }
} // fim
