# Programação Concorrente

Este repositório reúne os trabalhos desenvolvidos na disciplina de **Programação Concorrente**. Ao longo da disciplina foram implementadas diferentes simulações envolvendo concorrência, permitindo aplicar conceitos como **threads**, **regiões críticas**, **condições de corrida** e **semáforos**, além de compreender técnicas de sincronização e comunicação entre processos.

---

## 📑 Trabalhos

* 🚂 Trabalho 01 – Problema dos Trens
* 🚂 Trabalho 02 – Problema dos Trens com Threads
* 📦 Trabalho 03 – Produtor-Consumidor
* 🍽️ Trabalho 04 – Jantar dos Filósofos

---

# 🚂 Trabalho 01 – Problema dos Trens

## 📝 Descrição

O primeiro problema, denominado Problema dos Trens, consiste na simulação do deslocamento de dois trens por uma ferrovia que possui trechos compartilhados. O objetivo desta etapa foi observar o comportamento da aplicação em diferentes cenários de execução, analisando como a movimentação simultânea dos trens pode ocasionar conflitos ao acessarem uma mesma região compartilhada. Nesse primeiro momento, não foi realizado o tratamento das condições de corrida, buscando apenas evidenciar os problemas decorrentes da ausência de mecanismos de sincronização e compreender a necessidade de técnicas que garantam o acesso correto às regiões críticas.

---

# 🚂 Trabalho 02 – Problema dos Trens com Threads

## 📝 Descrição

Este problema consiste na simulação do deslocamento simultâneo de dois trens por trechos compartilhados de uma ferrovia. Nesta etapa, cada trem foi implementado como uma **thread** independente, permitindo a execução concorrente da aplicação e a movimentação simultânea dos trens durante a simulação. Diferentemente do problema anterior, o objetivo passou a ser **tratar as condições de corrida**, garantindo que apenas um trem acesse a região crítica por vez e evitando conflitos decorrentes do acesso simultâneo. Além disso, buscou-se analisar o comportamento de cada thread durante a execução, observando como diferentes mecanismos de sincronização influenciam o acesso às regiões compartilhadas. Para isso, foram implementadas e comparadas três soluções clássicas descritas por Tanenbaum em *Sistemas Operacionais*: **Variável de Travamento**, **Estrita Alternância** e **Solução de Peterson**, possibilitando a avaliação das características, limitações e do funcionamento de cada abordagem em diferentes cenários de execução.

### 📷 Demonstração

<img width="643" height="386" alt="image" src="https://github.com/user-attachments/assets/72361fcd-b613-471e-b18c-7d6076dd04ea" />

---

# 📦 Trabalho 03 – Produtor-Consumidor

## 📝 Descrição

O Problema do Produtor-Consumidor consiste na simulação da interação entre uma thread produtora, responsável por inserir itens em um buffer compartilhado, e uma thread consumidora, encarregada de remover esses itens. Para representar a execução concorrente, o produtor e o consumidor foram implementados como threads independentes, permitindo que ambas executassem simultaneamente durante a simulação. O principal objetivo desta etapa foi tratar os problemas de sincronização decorrentes do acesso concorrente ao buffer compartilhado, evitando condições de corrida e garantindo a correta coordenação entre as threads. Para isso, foram utilizados semáforos, responsáveis por controlar o acesso à região crítica e sincronizar as operações de produção e consumo, possibilitando a análise do comportamento das threads em diferentes cenários de execução.

### 📷 Demonstração

<img width="595" height="396" alt="image" src="https://github.com/user-attachments/assets/b74282dd-c917-4d28-ad8b-caaa1d671169" />

---

# 🍽️ Trabalho 04 – Jantar dos Filósofos

## 📝 Descrição

O **Problema do Jantar dos Filósofos** consiste na simulação de cinco filósofos que alternam entre os estados de pensar e comer, compartilhando os garfos disponíveis entre eles. Nesta etapa, cada filósofo foi implementado como uma **thread** independente, permitindo que todos executassem suas ações de forma concorrente. O objetivo foi compreender os desafios da sincronização no compartilhamento de recursos, garantindo que dois filósofos vizinhos não utilizassem o mesmo garfo simultaneamente e evitando problemas como condições de corrida e *deadlocks*. Para isso, foram utilizados **semáforos** para controlar o acesso aos garfos, possibilitando observar como as threads se comportam durante a execução e como a sincronização influencia o funcionamento correto da aplicação.

### 📷 Demonstração

<img width="592" height="387" alt="image" src="https://github.com/user-attachments/assets/c87743f8-7de7-449d-add9-b0e85e12e77a" />

---

## 🛠️ Tecnologias Utilizadas

Os projetos foram desenvolvidos em **Java 8** utilizando **JavaFX** para a interface gráfica e os recursos de programação concorrente da linguagem para a implementação das simulações.

