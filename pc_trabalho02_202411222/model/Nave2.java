/* ***************************************************************
* Autor............: Gabriel Alves Cruz 
* Matricula........: 202411222
* Inicio...........: 25/04/2026
* Ultima alteracao.: 02/05/2026
* Nome.............: Nave2.java
* Funcao...........: Definir a classe Nave2 com seus atributos e metodos 
*************************************************************** */

package model;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import controller.ControllerTela3;

public class Nave2 extends Thread {

  @FXML
  private ImageView imagemNave; // Injeta o id da imagem da nave que sera manipulada na simulacao

  private Slider slider;

  private double velocidadeNave; // Variavel que determina a velocidade do movimento da nave.

  private int posicao; // valor da posicao inicial em que a nave estara

  private int solucao; // valor da solucao para lidar com condicao de corrida 

  boolean executar = true; // variavel responsavel por controlar a execucao do loop da thread

  private int processo = 1; // valor do processo para a solucao de peterson

  // variaveis de controle para a regiao critica
  boolean dentroRegiaoCritica1 = false;
  boolean dentroRegiaoCritica2 = false;

  /*
   * ***************************************************************
   * Metodo: Nave
   * Funcao: instanciar um novo objeto do tipo Nave
   * Parametros: ImageView imagemNave, int posicao
   * Retorno: sem retorno difinido
   * ***************************************************************
   */

  public Nave2(ImageView imagemNave, int posicao, Slider slider, int solucao) {

    this.imagemNave = imagemNave;
    this.posicao = posicao;
    this.solucao = solucao;
    this.slider = slider;
    this.velocidadeNave = slider.getValue(); // Ao instanciar o objeto Nave, por padrao a velocidade eh definida como 1
    definirPosicaoInicial(posicao); // O objeto tera sua posicao definida ao ser instanciado

  } // fim do metodo nave

  /*
   * ***************************************************************
   * Metodo: getImagemNave
   * Funcao: retornar a imagem da nave
   * Parametros: sem parametros
   * Retorno: retorna imagemNave do tipo ImageView
   * ***************************************************************
   */

  public ImageView getImagemNave() {
    return imagemNave;
  } // fim do metodo getImagemNave

  /*
   * ***************************************************************
   * Metodo: getPosicao
   * Funcao: retornar a posicao da nave
   * Parametros: sem parametros definidos
   * Retorno: retorna posicao do tipo int
   * ***************************************************************
   */

  public int getPosicao() {
    return posicao;
  } // fim do metodo getPosicao

  /*
   * ***************************************************************
   * Metodo: getVelocidade
   * Funcao: retornar a velocidade da nave
   * Parametros: sem parametros definidos
   * Retorno: retorna velocidadeNave do tipo int
   * ***************************************************************
   */

  public double getVelocidade() {
    return velocidadeNave;
  } // fim do metodo getVelocidade

  /*
   * ***************************************************************
   * Metodo: setVelocidade
   * Funcao: alterar o valor da velocidade da nave
   * Parametros: velocidade
   * Retorno: retorno do tipo void
   * ***************************************************************
   */

  public void setVelocidade(double velocidadeNave) {
    this.velocidadeNave = velocidadeNave;
  } // fim do metodo setVelocidade

  /*
   * ***************************************************************
   * Metodo: getSolucao
   * Funcao: obter o valor da solucao
   * Parametros: sem parametros
   * Retorno: retorno do tipo int
   * ***************************************************************
   */

  public int getSolucao() {
    return solucao;
  } // fim do metodo getSolucao

  /*
   * ***************************************************************
   * Metodo: parar
   * Funcao: parar a execucao do loop da thread e garantir que a thread sera interrompida 
   * Parametros: sem parametros
   * Retorno: retorno do tipo void
   * ***************************************************************
   */

  public void parar() {
    this.executar = false;
    this.interrupt();
  } // fim do metodo parar

  /*
   * ***************************************************************
   * Metodo: run
   * Funcao: executa a thread 
   * Parametros: sem parametros
   * Retorno: retorno do tipo void
   * ***************************************************************
   */

  @Override
  public void run() {

    while (executar) {

      // Platform.runLater faz com que o codigo de movimento da nave seja executado em
      // algum momento na thread principal do javafx, evitando comportamentos
      // indesejados

      Platform.runLater(()->{
        movimentarNave();
      });

      try {
        Thread.sleep(16); // tempo em que a thread sera sera interrompida
      } catch (InterruptedException e) {
        //e.printStackTrace();
        break; 
      }
    }
  } // fim do metodo run

  // Metodo que difine individualmente a posicao de cada nave
  public void definirPosicaoInicial(int pos) {

    switch (pos) {

      // Caso a nave partir da esquerda (abaixo)
      case 1:
        imagemNave.setLayoutX(126);
        imagemNave.setLayoutY(228);
        imagemNave.setRotate(0);
        break;

      // Caso a nave partir da direita (abaixo)
      case 3:
        imagemNave.setLayoutX(470);
        imagemNave.setLayoutY(228);
        imagemNave.setRotate(180);
        break;

    } // fim do switch case

  } // fim do metodo definirPosicaoInicial

  /*
   * ***************************************************************
   * Metodo: entrouNaRegiaoCritica1
   * Funcao: verificar se a nave entrou na regiao critica 1
   * Parametros: int solucao
   * Retorno: retorno do tipo boolean
   * ***************************************************************
   */

  public boolean entrouNaRegiaoCritica1(int solucao) {

    switch (solucao) {

      case 1: // variavel de travamento

        // se vt = 1, significa que a rc esta ocupada, entao o processo deve esperar
        if (ControllerTela3.variavelTravamento1 == 1) {
          return false;
        // se vt = 0, rc nao esta ocupada   
        } else {
          ControllerTela3.variavelTravamento1 = 1; // vt eh ocupada
          dentroRegiaoCritica1 = true; // rc eh ocupada
          return true;
        }
       
      case 2: // estrita alternancia
        
        // se a vez eh do outro processo, espera
        if (ControllerTela3.vezEstAlt1 == 0) {
          return false; 
        }
        // se nao, o processo ocupa a rc
        else {
          dentroRegiaoCritica1 = true; 
          return true; 
        }

      case 3: // peterson
        
        int outro = 1 - processo; // variavel para o outro processo 
        ControllerTela3.interesseNaRegiao1 [processo] = true; // o processo tem interesse na rc 
        ControllerTela3.vez1SP = processo; // a vez eh do processo 1

        // se a vez eh do processo 1 e o outro processo tambem tem interesse na rc 1, espera
        if (ControllerTela3.vez1SP == processo && ControllerTela3.interesseNaRegiao1 [outro] == true){
          return false;
        }
        // se nao, pode ocupar a rc 1
        else {
          dentroRegiaoCritica1 = true; // rc eh ocupada
          return true; 
        }
      
      default:
        return true;

    }
  } // fim do metodo entrou na rc 1

  /*
   * ***************************************************************
   * Metodo: saiuDaRegiaoCritica1
   * Funcao: verificar se a nave saiu da regiao critica 1
   * Parametros: int solucao
   * Retorno: retorno do tipo boolean
   * ***************************************************************
   */

  public void saiuDaRegiaoCritica1(int solucao) {

    switch (solucao) {

      case 1: // variavel de travamento
        ControllerTela3.variavelTravamento1 = 0; // vt eh desocupada
        dentroRegiaoCritica1 = false; // rc eh desocupada
        break;

      case 2: // estrita alternancia
        ControllerTela3.vezEstAlt1 = 0; // vez passa a ser do  outro processo
        dentroRegiaoCritica1 = false; // rc eh desocupada 
        break;
        
      case 3: // peterson
        ControllerTela3.interesseNaRegiao1 [processo] = false; // processo nao tem interesse na regiao critica 1
        dentroRegiaoCritica1 = false; // rc eh desocupada
        break;   

      default: // sem controle de colisao
        break;
    }
  } // fim do metodo saiu da regiao critica 1

  /*
   * ***************************************************************
   * Metodo: entrouNaRegiaoCritica2
   * Funcao: verificar se a nave saiu da regiao critica 2
   * Parametros: int solucao
   * Retorno: retorno do tipo boolean
   * ***************************************************************
   */

  public boolean entrouNaRegiaoCritica2(int solucao) {

    switch (solucao) {

      case 1: // variavel de travamento

        if (ControllerTela3.variavelTravamento2 == 1) {
          return false; // se variavel de travamento for 1, significa que esta regiao critica nao esta desocupada

        } else { // caso seja 0
          ControllerTela3.variavelTravamento2 = 1; // com a rc desocupada, a variavel tem seu valor modificado
          dentroRegiaoCritica2 = true; // indica que rc esta ocupada
          return true;
        }

      case 2: // estritia alternancia
        
        // se a vez eh do outro processo, espera
        if (ControllerTela3.vezEstAlt2 == 0){
          return false; 
        }
        // se nao, pode ocupar a rc
        else{
          dentroRegiaoCritica2 = true; 
          return true; 
        }

      case 3: // peterson
        
        int outro = 1 - processo; // variavel para o outro processo
        ControllerTela3.interesseNaRegiao2 [processo] = true; // processo 1 tem interesse na regiao
        ControllerTela3.vez2SP = processo; // vez passa a ser do processo 1
        
        // se a vez eh do processo 1 e o outro processo tem interesse na regiai, espera
        if (ControllerTela3.vez2SP == processo && ControllerTela3.interesseNaRegiao2 [outro] == true){
          return false; 
        }
        // se nao, rc pode ser ocupada
        else {
          dentroRegiaoCritica2 = true; 
          return true;
        }

      default: // sem controle de colisao
        return true;

    }
  } // fim do metodo entrou na regiao critica 2

  /*
   * ***************************************************************
   * Metodo: saiuDaRegiaoCritica2
   * Funcao: verificar se a nave saiu da regiao critica 2
   * Parametros: int solucao
   * Retorno: retorno do tipo boolean
   * ***************************************************************
   */

  public void saiuDaRegiaoCritica2(int solucao) {

    switch (solucao) {

      case 1: // variavel de travamento
        ControllerTela3.variavelTravamento2 = 0; // variavel eh desocupada
        dentroRegiaoCritica2 = false; // rc eh desocupada
        break;
      
      case 2: //  estrita alternancia
        ControllerTela3.vezEstAlt2 = 0; // vez passa a ser do outro processo
        dentroRegiaoCritica2 = false; // rc eh desocupada
        break;   

      case 3: // peterson
        ControllerTela3.interesseNaRegiao2 [processo] = false; // o processo nao tem mais interesse na regiao
        dentroRegiaoCritica2 = false; // rc eh desocupada
        break; 

      default: // sem controle de colisao
        break;
    }
  } // fim do metodo saiu da regiao critica 2

  /*
   * ***************************************************************
   * Metodo: movimentarNave
   * Funcao: executar o movimento da nave
   * Parametros: sem parametros
   * Retorno: retorno do tipo void
   * ***************************************************************
   */

  public void movimentarNave() {

    switch (getPosicao()) { // Utiliza a posicao que foi utilizada na instanciacao do objeto

      case 1: // esquerda abaixo

        if (imagemNave.getLayoutX() >= 126 && imagemNave.getLayoutX() <= 170) {

          imagemNave.setLayoutX(imagemNave.getLayoutX() + velocidadeNave);
          imagemNave.setRotate(0);
          
        }

        // ------------ INICIO REGIAO CRITICA 1 ------------
        if (imagemNave.getLayoutX() > 170 && imagemNave.getLayoutX() <= 197) {

          // se nao for possivel entrar na rc 1
          if (!entrouNaRegiaoCritica1(solucao) && !dentroRegiaoCritica1) {
            break;
          }

          imagemNave.setLayoutX(imagemNave.getLayoutX() + velocidadeNave);
          imagemNave.setLayoutY(imagemNave.getLayoutY() - velocidadeNave);
          imagemNave.setRotate(-45);
          
        
        }

        if (imagemNave.getLayoutX() > 197 && imagemNave.getLayoutX() <= 238) {

          imagemNave.setLayoutX(imagemNave.getLayoutX() + velocidadeNave);
          imagemNave.setRotate(0);
    
        }

        if (imagemNave.getLayoutX() > 238 && imagemNave.getLayoutX() <= 270) { 
          
          imagemNave.setLayoutX(imagemNave.getLayoutX() + velocidadeNave);
          imagemNave.setLayoutY(imagemNave.getLayoutY() - velocidadeNave);
          imagemNave.setRotate(-65);
        
        }
        
        // ---------- FIM REGIAO CRITICA 1 ----------

        if (imagemNave.getLayoutX() >= 270 && imagemNave.getLayoutX() <= 310) {
          
          saiuDaRegiaoCritica1(solucao); // sinaliza que deixou a rc 1
          imagemNave.setLayoutX(imagemNave.getLayoutX() + velocidadeNave);
          imagemNave.setRotate(0);
        
        }

        // --------- INICIO REGIAO CRITICA 2 ----------

        if (imagemNave.getLayoutX() > 310 && imagemNave.getLayoutX() <= 345) {

          // se nao for possivel entrar na rc 2
          if (!entrouNaRegiaoCritica2(solucao) && !dentroRegiaoCritica2) {
            break;
          }

          imagemNave.setLayoutX(imagemNave.getLayoutX() + velocidadeNave);
          imagemNave.setLayoutY(imagemNave.getLayoutY() + velocidadeNave);
          imagemNave.setRotate(45);
        
        }

        if (imagemNave.getLayoutX() > 345 && imagemNave.getLayoutX() <= 395) {

          imagemNave.setLayoutX(imagemNave.getLayoutX() + velocidadeNave);
          imagemNave.setRotate(0);
        
        }

        if (imagemNave.getLayoutX() > 395 && imagemNave.getLayoutX() <= 420) {
          
          
          imagemNave.setLayoutX(imagemNave.getLayoutX() + velocidadeNave);
          imagemNave.setLayoutY(imagemNave.getLayoutY() + velocidadeNave);
          imagemNave.setRotate(45);
          
        }
        
        // -------- FIM REG CRITICA 2 --------
        if (imagemNave.getLayoutX() >= 420 && imagemNave.getLayoutX() <= 470) {
          
          saiuDaRegiaoCritica2(solucao); // sinaliza que saiu da rc 2
          imagemNave.setLayoutX(imagemNave.getLayoutX() + velocidadeNave);
          imagemNave.setRotate(0);
        
        }

        if (imagemNave.getLayoutX() >= 470) {

          imagemNave.setLayoutX(126);
          imagemNave.setLayoutY(228);
        
        }

        break;

      case 3: // direita abaixo

        if (imagemNave.getLayoutX() <= 470 && imagemNave.getLayoutX() >= 418) {

          imagemNave.setLayoutX(imagemNave.getLayoutX() - velocidadeNave);
          imagemNave.setRotate(180);
          
        }

        // ------------- INICIO REGIAO CRITICA 1 -------------
        if (imagemNave.getLayoutX() < 418 && imagemNave.getLayoutX() >= 388) {

          // se nao for possivel entrar na rc 2
          if (!entrouNaRegiaoCritica2(solucao) && !dentroRegiaoCritica2) {
            break;
          }

          imagemNave.setLayoutX(imagemNave.getLayoutX() - velocidadeNave);
          imagemNave.setLayoutY(imagemNave.getLayoutY() - velocidadeNave);
          imagemNave.setRotate(225);
          
        }

        if (imagemNave.getLayoutX() < 388 && imagemNave.getLayoutX() >= 344) {

          imagemNave.setLayoutX(imagemNave.getLayoutX() - velocidadeNave);
          imagemNave.setRotate(180);
          
        }

        
        if (imagemNave.getLayoutX() < 344 && imagemNave.getLayoutX() >= 315) {
                    
          imagemNave.setLayoutX(imagemNave.getLayoutX() - velocidadeNave);
          imagemNave.setLayoutY(imagemNave.getLayoutY() - velocidadeNave);
          imagemNave.setRotate(225);
          
        }
        
        // ---------- FIM REGIAO CRITICA 1 -----------

        if (imagemNave.getLayoutX() < 315 && imagemNave.getLayoutX() >= 270) {
          
          saiuDaRegiaoCritica2(solucao); // sinaliza que saiu da rc 2
          imagemNave.setLayoutX(imagemNave.getLayoutX() - velocidadeNave);
          imagemNave.setRotate(180);
          
        }

        // --------- INICIO REGIAO CRITICA 2 ----------

        if (imagemNave.getLayoutX() < 270 && imagemNave.getLayoutX() >= 238) {

          // se nao for possivel entrar na rc 1
          if (!entrouNaRegiaoCritica1(solucao) && !dentroRegiaoCritica1) {
            break;
          }

          imagemNave.setLayoutX(imagemNave.getLayoutX() - velocidadeNave);
          imagemNave.setLayoutY(imagemNave.getLayoutY() + velocidadeNave);
          imagemNave.setRotate(135);
          
        }

        if (imagemNave.getLayoutX() < 238 && imagemNave.getLayoutX() >= 197) {

          imagemNave.setLayoutX(imagemNave.getLayoutX() - velocidadeNave);
          imagemNave.setRotate(180);
          
        }

        if (imagemNave.getLayoutX() < 197 && imagemNave.getLayoutX() >= 170) {
          
          
          imagemNave.setLayoutX(imagemNave.getLayoutX() - velocidadeNave);
          imagemNave.setLayoutY(imagemNave.getLayoutY() + velocidadeNave);
          imagemNave.setRotate(135);
          
        }
        
        // -------- FIM REGIAO CRITICA 2 --------

        if (imagemNave.getLayoutX() < 170 && imagemNave.getLayoutX() >= 126) {
          
          saiuDaRegiaoCritica1(solucao); // sinaliza que saiu da rc 2
          imagemNave.setLayoutX(imagemNave.getLayoutX() - velocidadeNave);
          imagemNave.setRotate(180);
          
        }

        if (imagemNave.getLayoutX() <= 126) {

          imagemNave.setLayoutX(470);
          imagemNave.setLayoutY(228);
          
        }

        break;

    } // fim do switch case

  } // fim do metodo movimentar nave

} // fim da classe Nave2
