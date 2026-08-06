/* ***************************************************************
* Autor............: Gabriel Alves Cruz 
* Matricula........: 202411222
* Inicio...........: 24/06/2026
* Ultima alteracao.: 04/06/2026
* Nome.............: Comentarista.java
* Funcao...........: Definir o comportamento do comentarista  
*************************************************************** */

package model;

import java.util.concurrent.Semaphore;

import controller.ControleTelaPrincipal;
import javafx.application.Platform;
import javafx.scene.control.Slider;

public class Comentarista extends Thread {

  private ControleTelaPrincipal control;
  private int i; // identificador do comentarista
  private Slider sliderVelPensando;
  private Slider sliderVelComendo; 
  private int velocidadePensando;
  private int velocidadeComendo;
  private volatile boolean pausado = false; // flag booleado para indicar se a thread foi pausada  

  public static final int NUM_COMENTARISTAS = 5; 
  public static final int PENSANDO = 0;
  public static final int FOME = 1;
  public static final int COMENDO = 2;
  public static int[] estado = new int[NUM_COMENTARISTAS];
  public static Semaphore mutex = new Semaphore(1);
  public static Semaphore[] semaforos = new Semaphore[NUM_COMENTARISTAS];
  

  /*
  * ***************************************************************
  * Metodo: Comentarista
  * Funcao: construtor para criar novos objetos do tipo Comentarista
  * Parametros: ControleTelaPrincipal controle, int id, Slider sliderPensando, Slider sliderComendo
  * Retorno: sem retorno
  * ***************************************************************
  */

  public Comentarista(ControleTelaPrincipal controle, int id, Slider sliderPensando, Slider sliderComendo) {
    this.control = controle;
    this.i = id;
    this.sliderVelPensando = sliderPensando;
    this.sliderVelComendo = sliderComendo; 
  }

  /*
  * ***************************************************************
  * Metodo: getPausado 
  * Funcao: obter valor da flag booleana 
  * Parametros: sem parametros
  * Retorno: boolean
  * ***************************************************************
  */

  public boolean getPausado(){
    return pausado;
  }

  /*
  * ***************************************************************
  * Metodo: run
  * Funcao: metodo responsavel por executar a thread
  * Parametros: sem parametros
  * Retorno: void
  * ***************************************************************
  */

  @Override
  public void run() {

    while (!Thread.interrupted()) {

      try {
        
        pensar();
        
        pegaGarfos(i);
        
        comer();
        
        devolveGarfo(i);

      } catch (InterruptedException e) {
        break; // caso a excessao for capturada, sai do loop 
      }
    }
  }

  /*
  * ***************************************************************
  * Metodo: pegarGarfos
  * Funcao: representar de forma logica um comentarista obtendo um garfo da mesa
  * Parametros: int i
  * Retorno: void
  * ***************************************************************
  */

  private void pegaGarfos(int i) throws InterruptedException {
    mutex.acquire(); // thread vai fazer acesso a regiao critica
    estado[i] = FOME; // estado passa a ser de fome
    testaGarfo(i); // verifica se os garfos estao livres
    mutex.release(); // libera a regiao critica 
    semaforos[i].acquire(); // a thread fica bloqueada caso nao conseguir adquirir os dois garfos
  }

   /*
  * ***************************************************************
  * Metodo: devolveGargo
  * Funcao: representar de forma logica um comentarista devolvendo um garfo a mesa
  * Parametros: int i
  * Retorno: void
  * ***************************************************************
  */

  private void devolveGarfo(int i) throws InterruptedException {
    mutex.acquire(); // faz acesso a regiao critica
    estado[i] = PENSANDO; // estado volta para pensando
    testaGarfo(esquerda(i)); // verifica se o vizinho da esquerda pode comer 
    testaGarfo(direita(i)); // verifica se o viznhio da direita pode comer
    mutex.release(); // deixa a regiao critica
  }

  /*
  * ***************************************************************
  * Metodo: testaGarfo
  * Funcao: testar se um comentarista pode comer, alterando seu estado para comendo
  * Parametros: int i
  * Retorno: void
  * ***************************************************************
  */

  private void testaGarfo(int i) {
  
    // se o estado for de fome e o vizinho da esquerda e o da direita nao esta comendo
    if (estado[i] == FOME && estado[esquerda(i)] != COMENDO && estado[direita(i)] != COMENDO) {
      estado[i] = COMENDO; // pode comer
      semaforos[i].release(); // pode obter os dois garfos
    }

  }

  /*
  * ***************************************************************
  * Metodo: pensar
  * Funcao: representar de forma visual o comentarista pensando
  * Parametros: sem parametros
  * Retorno: void
  * ***************************************************************
  */

  private void pensar() throws InterruptedException {
    Platform.runLater(()->control.alternarEstadoVisual(i, PENSANDO));
    Platform.runLater(()-> control.alterarTextoDeEstado(i, PENSANDO));
    sleepTime(definirVelocidadeDePensar());
    verificaPausa();
  }

  /*
  * ***************************************************************
  * Metodo: comer
  * Funcao: representar de forma visual o comentarista comendo
  * Parametros: sem parametros
  * Retorno: void
  * ***************************************************************
  */

  private void comer() throws InterruptedException {
    Platform.runLater(()-> control.alternarEstadoVisual(i, COMENDO));
    Platform.runLater(()-> control.alterarTextoDeEstado(i, COMENDO));
    
    int garfoEsquerda = i; 
    int garfoDireita = (i + 1) % NUM_COMENTARISTAS;
    
    // comentaristas estao comendo, logo os garfos nao ficam visiveis
    Platform.runLater(()-> control.alternarVisibilidadeGarfos(garfoEsquerda, garfoDireita, false)); 
    
    sleepTime(definirVelocidadeDeComer());
    
    verificaPausa(); // verifica se o processo foi pausado

    // garfos voltar a ficaar visiveis
    Platform.runLater(()-> control.alternarVisibilidadeGarfos(garfoEsquerda, garfoDireita, true)); // comentaristas terminaram de comer
    
  }

  /*
  * ***************************************************************
  * Metodo: sleepTime 
  * Funcao: definir o sleep
  * Parametros: int valor
  * Retorno: void
  * ***************************************************************
  */

  private void sleepTime (int valor) throws InterruptedException {
    sleep((long) valor * 1000);
  }

   /*
  * ***************************************************************
  * Metodo: esquerda
  * Funcao: alternar para o viznho da esquerda
  * Parametros: int i
  * Retorno: int (i + NUM_COMENTARISTAS - 1) % NUM_COMENTARISTAS;
  * ***************************************************************
  */

  private int esquerda (int i){
    return (i + NUM_COMENTARISTAS - 1) % NUM_COMENTARISTAS;
  }

  /*
  * ***************************************************************
  * Metodo: direita
  * Funcao: alternar para o viznho da direita
  * Parametros: int i
  * Retorno: int (i + 1) % NUM_COMENTARISTAS;
  * ***************************************************************
  */

  public int direita (int i){
    return (i + 1) % NUM_COMENTARISTAS;
  }

  /*
  * ***************************************************************
  * Metodo: definirVelocidadeDePensar 
  * Funcao: definir qual vai ser o sleep de pensar conforme o valor do slider
  * Parametros: sem parametros
  * Retorno: int velocidadePensando;
  * ***************************************************************
  */

  private int definirVelocidadeDePensar(){
    
    if (this.sliderVelPensando.getValue()== 1){
      this.velocidadePensando = 6; 
    }
    else if (this.sliderVelPensando.getValue() == 2){
      this.velocidadePensando = 3; 
    }
    else if (this.sliderVelPensando.getValue() == 3){
      this.velocidadePensando = 1; 
    }

    return velocidadePensando;

  }

  /*
  * ***************************************************************
  * Metodo: definirVelocidadeDeComer
  * Funcao: definir qual vai ser o sleep de comer conforme o valor do slider 
  * Parametros: sem parametros
  * Retorno: int velocidadeComendo
  * ***************************************************************
  */

  private int definirVelocidadeDeComer(){
    
    if (this.sliderVelComendo.getValue()== 1){
      this.velocidadeComendo = 6; 
    }
    else if (this.sliderVelComendo.getValue() == 2){
      this.velocidadeComendo = 3; 
    }
    else if (this.sliderVelComendo.getValue() == 3){
      this.velocidadeComendo = 1; 
    }

    return velocidadeComendo;

  }

  /*
  * ***************************************************************
  * Metodo: verificaPausa
  * Funcao: verifica se a thread foi pausada e se nao foi interrompida 
  * Parametros: sem parametros
  * Retorno: voi
  * ***************************************************************
  */

  private void verificaPausa() throws InterruptedException{
    while (pausado && !Thread.interrupted()){ // se a thread esta pausada e se nao foi interrompida
      sleepTime(1);
    }
  }

  /*
  * ***************************************************************
  * Metodo: pausar
  * Funcao: mudar o valor da flag para true, indicando que nao esta pausada a thread
  * Parametros: sem parametros
  * Retorno: void
  * ***************************************************************
  */

  public void pausar (){
    this.pausado = true;
  }

  /*
  * ***************************************************************
  * Metodo: retomar
  * Funcao: mudar o valor da flag para falso, indicando que nao esta pausada a thread
  * Parametros: sem parametros
  * Retorno: int velocidadeComendo
  * ***************************************************************
  */

  public void retomar (){
    this.pausado = false; 
  }

} // fim
