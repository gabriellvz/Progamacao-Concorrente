/* ***************************************************************
* Autor............: Gabriel Alves Cruz 
* Matricula........: 202411222
* Inicio...........: 13/03/2026
* Ultima alteracao.: 22/03/2026
* Nome.............: Nave.java
* Funcao...........: Definir a classe nave com seus atributos e metodos 
*************************************************************** */

package model;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class Nave {
  
  @FXML
  private ImageView imagemNave; // Injeta o id da imagem da nave que sera manipulada na simulacao

  private double velocidadeNave; // Variavel que determina a velocidade do movimento da nave. 

  private int posicao; // Posicao inicial em que a nave estara 

  /* ***************************************************************
  * Metodo: Nave 
  * Funcao: instanciar um novo objeto do tipo Nave
  * Parametros: ImageView imagemNave, int posicao
  * Retorno: sem retorno difinido
  *************************************************************** */

  public Nave(ImageView imagemNave, int posicao) {

    this.imagemNave = imagemNave;
    this.posicao = posicao;
    this.velocidadeNave = 1; // Ao instanciar o objeto Nave, por padrao a velocidade eh definida como 1
    definirPosicaoInicial(posicao); // O objeto tera sua posicao definida ao ser instanciado 

  }

  /* ***************************************************************
  * Metodo: getImagemNave
  * Funcao: retornar a imagem da nave
  * Parametros: sem parametros
  * Retorno: retorna imagemNave do tipo ImageView
  *************************************************************** */

  public ImageView getImagemNave (){
    return imagemNave;
  }

  /* ***************************************************************
  * Metodo: getPosicao
  * Funcao: retornar a posicao da nave
  * Parametros: sem parametros definidos
  * Retorno: retorna posicao do tipo int
  *************************************************************** */

  public int getPosicao(){
    return posicao;
  }

  /* ***************************************************************
  * Metodo: getVelocidade
  * Funcao: retornar a velocidade da nave 
  * Parametros: sem parametros definidos
  * Retorno: retorna velocidadeNave do tipo int
  *************************************************************** */

  public double getVelocidade(){
    return velocidadeNave;
  }

  /* ***************************************************************
  * Metodo: setVelocidade 
  * Funcao: alterar o valor da velocidade da nave
  * Parametros: velocidade
  * Retorno: retorno do tipo void
  *************************************************************** */

  public void setVelocidade(double velocidadeNave){
    this.velocidadeNave = velocidadeNave;
  }

  /* ***************************************************************
  * Metodo: definirPosicaoInicial
  * Funcao: definir a posicao inicial da nave
  * Parametros: int pos
  * Retorno: retorn do tipo void 
  *************************************************************** */

  // Metodo que difine individualmente a posicao de cada nave
  public void definirPosicaoInicial (int pos){
    
    switch (pos){
      
      // Caso a nave partir da esquerda (acima)
      case 0:
        imagemNave.setLayoutX(126);
        imagemNave.setLayoutY(172);
        imagemNave.setRotate(0);
        break;
      
      // Caso a nave partir da esquerda (abaixo)
      case 1:
        imagemNave.setLayoutX(126);
        imagemNave.setLayoutY(228);
        imagemNave.setRotate(0);
        break; 
        
      // Caso a nave partir da direita (acima)  
      case 2:
        imagemNave.setLayoutX(470);
        imagemNave.setLayoutY(166);
        imagemNave.setRotate(180);
        break;

      // Caso a nave partir da direita (abaixo)
      case 3:
        imagemNave.setLayoutX(470);
        imagemNave.setLayoutY(228);
        imagemNave.setRotate(180);
        break;      

    } // fim do switch case
        
  } // fim do metodo definirPosicaoInicial

  /* ***************************************************************
  * Metodo: moverNaveEmLinhaReta 
  * Funcao: definir um padrao para o movimento em linha reta, permitindo a reutilizacao em outros trechos do codigo
  * Parametros: double posicaoInicial, double posicaoFinal, double anguloRotacao
  * Retorno: retorno do tipo void 
  *************************************************************** */

  private void moverEmLinhaReta(double posicaoInicial, double posicaoFinal, double anguloRotacao){
    
    // Se a nave partir da esquerda para direita
    // Se a posicao inicial do intervalo for menor que a posicao final o layout x da imagem, sera somado com a velocidade, resultando em um movimento para a direita 
    if (posicaoInicial < posicaoFinal){

      if (imagemNave.getLayoutX() >= posicaoInicial && imagemNave.getLayoutX() <= posicaoFinal){

        imagemNave.setRotate(anguloRotacao);
        imagemNave.setLayoutX(imagemNave.getLayoutX() + velocidadeNave);

      } // fim do if interno
    } // fim do if externo
    
    // Se a nave partir da direita para esquerda
    // Se a posicao inicial do intervalo for maior que a posicao final o layout x da imagem, sera subtraido com a velocidade, resultando em um movimento para a esquerda      
    else{

      if (imagemNave.getLayoutX() <= posicaoInicial && imagemNave.getLayoutX() >= posicaoFinal){

        imagemNave.setRotate(anguloRotacao);
        imagemNave.setLayoutX(imagemNave.getLayoutX() - velocidadeNave);

      } // fim do if
    } // fim do else 

  } // fim metodo mover em linha reta

  /* ***************************************************************
  * Metodo: moverNaDiagonalDeclinada
  * Funcao: mover a nave na diagonal declinada
  * Parametros: double posicaInicial, double posicaoFinal, double anguloRotacao
  * Retorno: retorno do tipo void 
  *************************************************************** */

  private void moverNaDiagonalDeclinada(double posicaoInicial, double posicaoFinal, double anguloRotacao){

    // Se a nave partir da esquerda para direita
    // Se a posicao inicial do intervalo for menor que a posicao final o layout x da imagem e o layout y, serao somados com a velocidade, resultando em um movimento para a diagonal declinada
    if (posicaoInicial < posicaoFinal){
      if (imagemNave.getLayoutX() > posicaoInicial && imagemNave.getLayoutX() <= posicaoFinal) {
        
        imagemNave.setRotate(anguloRotacao);
        imagemNave.setLayoutX(imagemNave.getLayoutX() + velocidadeNave);
        imagemNave.setLayoutY(imagemNave.getLayoutY() + velocidadeNave);
      
      }
    }

    // Se a nave partir da direita para esquerda
    // Se a posicao inicial do intervalo for maior que a posicao final o layout x da imagem, sera subtraido com a velocidade e o layout y somado, resultando em um movimento para a diagonal declinada
    else{

       if (imagemNave.getLayoutX() <= posicaoInicial && imagemNave.getLayoutX() >= posicaoFinal) {

        imagemNave.setRotate(anguloRotacao);
        imagemNave.setLayoutX(imagemNave.getLayoutX() - velocidadeNave);
        imagemNave.setLayoutY(imagemNave.getLayoutY() + velocidadeNave);

      } // fim do if 
    } // fim do bloco if else 

  } // fim do metodo mover na diagonal declinada
  
  /* ***************************************************************
  * Metodo: moverNaDiagonalInclinada
  * Funcao: mover a nave na diagonal inclinada
  * Parametros: double posicaoInicial, double posicaoFinal, double anguloRotacao
  * Retorno: retorno do tipo void
  *************************************************************** */

  private void moverNaDiagonalInclinada(double posicaoInicial, double posicaoFinal, double anguloRotacao){
 
    // Se a nave partir da esquerda para direita
    // Se a posicao inicial do intervalo for menor que a posicao final o layout x da imagem, sera somados com a velocidade, e o layout y subtraido, resultando em um movimento para a diagonal inclinada  
    if (posicaoInicial < posicaoFinal){

      if (imagemNave.getLayoutX() > posicaoInicial && imagemNave.getLayoutX() <= posicaoFinal){

        imagemNave.setRotate(anguloRotacao);
        imagemNave.setLayoutX(imagemNave.getLayoutX() + velocidadeNave);
        imagemNave.setLayoutY(imagemNave.getLayoutY() - velocidadeNave);

      } // fim do if interno
    } // fim do if externo

    // Se a nave partir da direita para esquerda
    // Se a posicao inicial do intervalo for maior que a posicao final o layout x da imagem e o layout y, serao subtraidos com a velocidade, resultando em um movimento para a diagonal inclinada  
    else {
      if (imagemNave.getLayoutX() <= posicaoInicial && imagemNave.getLayoutX() >= posicaoFinal){

        imagemNave.setRotate(anguloRotacao);
        imagemNave.setLayoutX(imagemNave.getLayoutX() - velocidadeNave);
        imagemNave.setLayoutY(imagemNave.getLayoutY() - velocidadeNave);

      } // fim do if
    } // fim do bloco if-else

  } // fim do metodo moverNaDiagonalInclinada
  
  /* ***************************************************************
  * Metodo: reiniciarPercusso
  * Funcao: Reiniciar o percusso da nave para o ponto de origem
  * Parametros: double limiteFinal, double eixoxInicial, double eixoyInicial
  * Retorno: retorno do tipo void
  *************************************************************** */

  private void reiniciarPercuso(double limiteFinal, double eixoxInicial, double eixoyInicial ){

    // Limite final do percusso da esquerda para direita
    double limitePercussoEsqDir = 470;

    // Se o limite final do parametro for menor ou igual ao limite da variavel local
    // Nave termina o percuso na direita
    if (limiteFinal == limitePercussoEsqDir){

      // Se a nave chegar ao fim do percuso
      if (imagemNave.getLayoutX() >= limiteFinal){

        imagemNave.setLayoutX(eixoxInicial);
        imagemNave.setLayoutY(eixoyInicial);

      } // fim do if interno
    } // fim do if externo

    // Se o limite final do parametro for diferente do limite da variavel local
    // Nave termina percuso na esquerda  
    else{

      // Se a nave chegar ao fim do percuso
      if (imagemNave.getLayoutX() <= limiteFinal){

        imagemNave.setLayoutX(eixoxInicial);
        imagemNave.setLayoutY(eixoyInicial);

      } // fim do if 
    } // fim do else

  } // fim do metodo reiniciar percuso

  /* ***************************************************************
  * Metodo: movimentarNavePosicao0 
  * Funcao: realizar a chamada de metodos de movimento caso a nave partir da esquerda superior
  * Parametros: sem parametros definidos
  * Retorno: retorno do tipo void 
  *************************************************************** */

  // Superior esquerda
  private void movimentarNavePosicao0(){

    moverEmLinhaReta(126, 170,0);

    moverNaDiagonalDeclinada(170, 197, 45);

    moverEmLinhaReta(197, 238, 0);

    moverNaDiagonalDeclinada(238, 270, 60);

    moverEmLinhaReta(270, 310, 0);

    moverNaDiagonalInclinada(310, 345, -45);

    moverEmLinhaReta(345, 395, 0);

    moverNaDiagonalInclinada(395, 420, -45);

    moverEmLinhaReta(420, 470, 0);

    reiniciarPercuso(470, 126, 172);

  } // fim do metodo movimentarNavePosicao0

  /* ***************************************************************
  * Metodo: movimentarNavePosicao1
  * Funcao: realizar a chamada de metodos de movimento caso a nave partir da esquerda inferior
  * Parametros: sem parametros definidos
  * Retorno: retorno do tipo void
  *************************************************************** */

  // Inferior esquerda
  private void movimentarNavePosicao1(){

    moverEmLinhaReta(126, 170,0);

    moverNaDiagonalInclinada(170, 197, -45);

    moverEmLinhaReta(197, 238, 0);

    moverNaDiagonalInclinada(238, 270, -65);

    moverEmLinhaReta(270,310,0);

    moverNaDiagonalDeclinada(310, 345, 45);

    moverEmLinhaReta(345, 395, 0);

    moverNaDiagonalDeclinada(395, 420, 45);

    moverEmLinhaReta(420, 470, 0);

    reiniciarPercuso(470, 126, 228);

  } // fim do metodo movimentarNavePosicao1

  /* ***************************************************************
  * Metodo: movimentarNavePosicao2
  * Funcao: realizar a chamada de metodos de movimento caso a nave partir da superior direita
  * Parametros: sem parametros definidos
  * Retorno: retorno do tipo void
  *************************************************************** */

  // Superior direita
  private void movimentarNavePosicao2(){

    moverEmLinhaReta(470, 418, 180);

    moverNaDiagonalDeclinada(418, 388, 135);

    moverEmLinhaReta(388, 344, 180);

    moverNaDiagonalDeclinada(344, 315, 135);

    moverEmLinhaReta(315, 270, 180);

    moverNaDiagonalInclinada(270, 238, 225);

    moverEmLinhaReta(238, 197, 180);

    moverNaDiagonalInclinada(197, 170, 225);

    moverEmLinhaReta(170, 126, 180);

    reiniciarPercuso(126, 470, 172);

  } // fim do metodo movimentarNavePosicao2

  /* ***************************************************************
  * Metodo: movimentarNavePosicao3
  * Funcao: realizar a chamada de metodos de movimento caso a nave partir da direita inferior
  * Parametros: sem parametros definidos
  * Retorno: retorno do tipo void 
  *************************************************************** */

  // Inferior direita 
  private void movimentarNavePosicao3(){

    moverEmLinhaReta(470, 418, 180);

    moverNaDiagonalInclinada(418, 388, 225);

    moverEmLinhaReta(388, 344, 180);

    moverNaDiagonalInclinada(344, 315, 225);

    moverEmLinhaReta(315, 270, 180);

    moverNaDiagonalDeclinada(270, 238, 135);

    moverEmLinhaReta(238, 197, 180);

    moverNaDiagonalDeclinada(197, 170 , 135);

    moverEmLinhaReta(170, 126, 180);

    reiniciarPercuso(126, 470, 228);

  } // fim do metodo movimentarNavePosicao3

  /* ***************************************************************
  * Metodo: movimentarNave 
  * Funcao: realizar a chamada do metodo de movimento corresnponde com a posicao da nave
  * Parametros: sem parametros definidos
  * Retorno: retorno do tipo void
  *************************************************************** */

  public void movimentarNave(){
    
    switch (getPosicao()){ // Utiliza a posicao que foi utilizada na instanciacao do objeto
      case 0:
        movimentarNavePosicao0();
        break;
      
      case 1:
        movimentarNavePosicao1();
        break; 
      
      case 2:
        movimentarNavePosicao2();
        break;

      case 3:
        movimentarNavePosicao3();  
        break;

    } // fim do switch case 

  } // fim do metodo movimentar nave

} //  fim da classe nave 
