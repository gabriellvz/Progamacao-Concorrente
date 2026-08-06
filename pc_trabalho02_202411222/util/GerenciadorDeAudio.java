/* ***************************************************************
* Autor............: Gabriel Alves Cruz 
* Matricula........: 202411222
* Inicio...........: 19/04/2026
* Ultima alteracao.: 20/04/2026
* Nome.............: GerenciadorDeAudio.java
* Funcao...........: Criar um gerenciador de audio para o programa 
*************************************************************** */


package util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

// Classe utilitaria para gerenciar o audio do programa.

public class GerenciadorDeAudio {

  private static MediaPlayer mediaPlayer; // atributo estatico que pertence a classe
  
  /* ***************************************************************
  * Metodo: iniciarAudio
  * Funcao: Tocar a musica de fundo da aplicacao
  * Parametros: sem parametros definidos
  * Retorno: retorno do tipo void
  *************************************************************** */

  public static void iniciarAudio(String caminhoMusica){
    
    // no trecho GerenciadorDeAudio.class, a classe esta sendo obtida diretamente 
    // o .class permite a criacao de um ponto de referencia
    // toexternalForm gera um formato valido 
    Media media = new Media(GerenciadorDeAudio.class.getResource(caminhoMusica).toExternalForm()); // Variavel que receber o caminho da musica
    mediaPlayer = new MediaPlayer(media); // Cria um novo MediaPlayer
    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Seta para indefinido a quantidade de vezes que musica ira repetir
    mediaPlayer.play(); // Inicia o som

  } // fim do metodo tocar musica 


  /* ***************************************************************
  * Metodo: getMediaPlayer
  * Funcao: retornar o media player
  * Parametros: sem parametros definidos
  * Retorno: retorno do tipo MediaPlayer
  *************************************************************** */

  public static MediaPlayer getMediaPlayer(){
    return mediaPlayer;
  } // fim do metodo MediaPlayer

} 
