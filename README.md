# OptiPackRoute: Optimal Path Routing Algorithms

## Objetivo
Os projetos Floyd-Warshall Enhanced  e Anxietatem Algorithm têm como objetivo central a implementação e exposição de algoritmos avançados para resolver problemas de roteirização de caminhos ótimos e distribuição de carga em contêineres (bin packing). Estes algoritmos são acessíveis via uma API RESTful, permitindo que aplicações clientes os utilizem para calcular rotas otimizadas e soluções de empacotamento eficientes em diferentes cenários logísticos.

A motivação para ambos os projetos vem dos desafios práticos enfrentados em setores de transporte, logística e armazenamento, onde a eficiência no uso de espaço e o planejamento preciso de rotas podem reduzir significativamente custos operacionais e o tempo de entrega.

## Algoritmos Expostos

### Algoritmos de Roteirização
Os algoritmos de roteirização têm como foco a otimização de rotas em cenários de múltiplas paradas, buscando a melhor maneira de conectar pontos geográficos de forma eficiente.

1. A-Star (A*): Um algoritmo de busca de caminho mais curto que utiliza uma abordagem heurística para encontrar a rota mais eficiente entre um ponto de origem e um destino em um grafo.
2. Dijkstra: Este algoritmo clássico resolve o problema de caminho mínimo entre um ponto de origem e todos os outros nós de um grafo, sendo amplamente utilizado em redes de transporte e planejamento logístico.

### Algoritmos de Bin Packing
Os algoritmos de bin packing visam resolver o problema de alocar objetos tridimensionais em um espaço limitado de forma eficiente, otimizando o uso do volume disponível.

1. Anxietatem Algorithm: Um algoritmo baseado em heurísticas de pontos de canto, focado no empacotamento tridimensional, utilizando estratégias adaptativas para minimizar o espaço ocioso em contêineres.
2. Best Fit Decreasing Height (BFDH): Um algoritmo de bin packing que ordena os itens por altura decrescente e tenta colocá-los no espaço que melhor se ajusta, buscando minimizar o volume desperdiçado.
3. Bottom-Left-Back: Esta heurística posiciona itens no ponto inferior-esquerdo-traseiro do contêiner disponível, expandindo para cima e à direita, tentando maximizar o preenchimento de camadas.
4. Extreme Point Based (AFIT): Um algoritmo que se baseia em pontos extremos do espaço disponível, onde novos itens podem ser colocados, atualizando dinamicamente as possibilidades de posicionamento à medida que novos itens são empacotados.
5. Guillotine Split: Um método de empacotamento que divide o contêiner em sub-regiões menores após a colocação de cada item, utilizando um corte guilhotinado para criar novas áreas de colocação.

## Exposição via API RESTful
Ambos os projetos são integrados e expostos por meio de uma API RESTful, permitindo que aplicações clientes acessem as funcionalidades dos algoritmos de forma fácil e programática. Cada algoritmo possui seu respectivo endpoint, facilitando a integração de diferentes sistemas de logística, plataformas de gerenciamento de transporte e sistemas de armazenamento.

## Floyd-Warshall Enhanced Algorithm

Originalmente, o algoritmo Floyd-Warshall resolve o problema de encontrar os caminhos mais curtos entre todos os pares de nós em um grafo, com complexidade assintótica de 
𝑂(𝑛3), onde 𝑛 é o número de vértices. No entanto, o algoritmo tradicional não foi projetado para lidar de forma eficiente com problemas em que é necessário passar por pontos intermediários específicos, como ocorre em cenários de logística compartilhada, onde mercadorias são entregues em várias localidades antes de chegar ao destino final.

No OptiPackRoute, o algoritmo Floyd-Warshall foi melhorado para incluir a capavertice de otimizar rotas que envolvem destinos intermediários, simulando cenários complexos de logística de múltiplas paradas. Este aprimoramento permite calcular não apenas o menor caminho entre dois pontos, mas também a melhor configuração de rotas que passam por destinos intermediários obrigatórios — o que reflete casos reais, como a otimização de entregas para diferentes destinos em um sistema de distribuição compartilhada.

### Pre-Processamento

#### Fórmula Matemática:

$$
D_{ij}(k) = 
\begin{cases} 
\min\left(D_{ij}(k-1), D_{ik}(k-1) + D_{kj}(k-1)\right) & \text{se } i \neq j \\
0 & \text{se } i = j
\end{cases}
$$

Onde:
* 𝐷𝑖𝑗(𝑘)  representa a menor distância do vértice 𝑖 para o vértice 𝑗, considerando todos os vértices intermediários do conjunto {1,2,…,𝑘}.
* 𝐷𝑖𝑗(𝑘−1)  é a distância direta de 𝑖 para 𝑗, sem passar pelo vértice intermediário 𝑘.
* 𝐷𝑖𝑘(𝑘−1)+𝐷𝑘𝑗(𝑘−1) representa a distância passando pelo vértice intermediário 𝑘.

#### Pseudo-código:

O primeiro passo do algoritmo é realizar o pre-processamento da matriz de adjacências e de predecessores.

```
Função: calcularCaminhosMinimos(matrizDistancia)
    Para cada vértice intermediário k de 0 até N-1:
        Para cada vértice i de 0 até N-1:
            Para cada vértice j de 0 até N-1:
                Se a distância direta de i para j for maior que a distância passando por k:
                    Atualizar matrizDistancia[i][j] para o menor valor entre (i -> k -> j)
                    Atualizar matrizPredecessores[i][j] com a última etapa anterior do caminho (k -> j)

    Atualizar a matriz original com o estado atual da matriz de distância
    Armazenar a matriz de custos finais da rota em matrizCustoRota
    Armazenar as matrizes de distância e predecessores no resultado final

```
### Processamento

Com as matrizes calculadas, basta informar os vértices que se deseja percorrer e o vértice inicial.

#### Fórmula Matemática:
O custo total de uma rota 𝑃 pode ser descrito pela seguinte fórmula:

$$C(P)= i=1∑n−1 custo(P[i],P[i+1])$$

Onde:
* 𝐶(𝑃) é o custo total da rota 𝑃.
* 𝑃[𝑖] é o 𝑖-ésimo vértice na permutação da rota.
* custo(𝑃[𝑖],𝑃[𝑖+1]) é o custo de transitar do vértice 𝑃[𝑖] para o vértice 𝑃[𝑖+1], retirado da matriz de custos de rota.

O algoritmo gera todas as possíveis permutações de 𝑛 vértices e calcula o custo total para cada uma. 
O caminho com o menor 𝐶(𝑃) é considerado o caminho ótimo:

$$min(C(P))$$

#### Pseudo-código:
```
Função: encontrarConfiguracaoOtimizada(listaElementos, verticeInicial)
    
    Inicializar geradorDePermutacoes com o tamanho da listaElementos
    Inicializar variáveis: 
        custo = 0
        custoParcial = 0
        custoMinimo = infinito
        caminhoCurto[] com tamanho igual à listaElementos
        caminhoOtimo[] com tamanho igual à listaElementos
        paresDe[][] com tamanho listaElementos x 2
    
    Para cada permutação possível gerada:
        Recuperar os índices da permutação
        
        Para cada par de vertices consecutivas na permutação:
            Definir paresDeVertices[i] com a vertice atual e a próxima vertice
            Atualizar caminhoCurto[] com o par de vertices

            Calcular o custo parcial usando a matriz de custos da rota
            Somar custoParcial ao custo total
            Adicionar par de vertices e seu custo na lista de caminhos parciais
            
            Se for o último par na permutação:
                Se custo < custoMinimo:
                    Atualizar custoMinimo
                    Atualizar caminhoOtimo[] com o caminho atual

                Redefinir custo = 0
        
        Armazenar os caminhos parciais na iteração atual

    Definir no resultado o melhor caminho e o custo total mínimo
    Medir o tempo total de processamento

```

### Motivação Técnica: Complexidade e Roteamento em Cenários Logísticos
Em termos de computação, problemas como a otimização de rotas em múltiplas paradas muitas vezes se enquadram em categorias de problemas NP-difíceis, como o Problema do Caixeiro Viajante (TSP - Travelling Salesman Problem), onde a busca pela solução ótima pode exigir tempo exponencial. Embora o Floyd-Warshall seja eficiente para certos cenários com complexidade cúbica 
𝑂(𝑛3), a necessidade de destinos intermediários aumenta a complexidade cognitiva da solução, já que o algoritmo passa a verificar a combinação ótima de caminhos possíveis com base em diferentes configurações de pontos de parada.

A otimização de rotas com paradas intermediárias envolve uma abordagem diferente em relação à simples minimização de distância: é preciso considerar a ordem de visita aos nós intermediários e ajustar a rota de maneira a minimizar o custo total, o que adiciona uma sobrecarga significativa ao cálculo. O aprimoramento do Floyd-Warshall no OptiPackRoute ataca esse desafio ao reorganizar dinamicamente a rota durante o cálculo, oferecendo uma solução eficiente para problemas que tradicionalmente teriam complexidade não polinomial.

## Anxietatem Algorithm

O Anxietatem Algorithm é um algoritmo heurístico para o problema de 3D bin packing, desenvolvido especificamente para otimizar a distribuição de carga em contêineres em ambientes logísticos. Este algoritmo, baseado em heurísticas de pontos de canto, calcula o posicionamento mais eficiente de itens tridimensionais em espaços de contêineres limitados, maximizando o aproveitamento do volume e minimizando o espaço ocioso.

### Motivação técnica
No campo da logística de transporte e armazenamento, a otimização da distribuição de carga é fundamental para reduzir custos e maximizar a eficiência. Com o crescente volume de mercadorias e a necessidade de transporte em contêineres padronizados, a correta distribuição da carga dentro desses espaços é um problema logístico desafiador. O Anxietatem Algorithm foi desenvolvido para enfrentar essas dificuldades, fornecendo uma solução para o problema de empacotamento tridimensional em que objetos de diferentes formas e tamanhos devem ser posicionados em contêineres de maneira eficiente.

Além disso, a imprevisibilidade de formas e volumes das mercadorias faz com que o simples uso de algoritmos clássicos de empacotamento se torne insuficiente, aumentando a necessidade de uma solução que leve em consideração pontos de inserção específicos no espaço tridimensional e suas interações complexas. O Anxietatem Algorithm visa preencher essa lacuna, proporcionando resultados rápidos e precisos para cenários de empacotamento complexos.

### Modus Operandi
O Anxietatem Algorithm baseia-se na heurística de pontos de canto, uma técnica comumente utilizada para resolver problemas de bin packing tridimensional. O algoritmo mantém um conjunto dinâmico de "pontos de canto" dentro do contêiner, que são as posições potenciais para colocar novos itens.

1. Inicialização dos Pontos de Canto: O algoritmo começa com um ponto de canto inicial, geralmente o ponto de origem (0,0,0) do contêiner. À medida que os itens são colocados, novos pontos de canto são gerados ao redor dos itens posicionados.
2. Seleção de Itens: Cada item é avaliado de acordo com sua orientação e dimensões, e o algoritmo verifica em qual ponto de canto ele pode ser encaixado de forma a minimizar o espaço ocioso. Os pontos de canto são atualizados constantemente conforme o empacotamento progride.
3. Heurística de Escolha de Ponto: O algoritmo utiliza um conjunto de regras baseadas em uma heurística para determinar o melhor ponto de canto para cada item. Ele leva em consideração:
    * O volume ocioso restante após o item ser posicionado.
    * A proximidade com outros itens já posicionados.
    * A possibilidade de criar novos pontos de canto mais vantajosos para futuros itens.
5. Reorganização Adaptativa: Se a colocação de um item gera um excesso de espaço ocioso, o algoritmo pode reorganizar dinamicamente a sequência de itens ainda não alocados para encontrar uma configuração que reduza ao máximo o desperdício de espaço.
6. Fechamento: O processo continua até que todos os itens tenham sido posicionados ou até que não haja mais espaço suficiente no contêiner para acomodar qualquer item restante. Caso restem itens não alocados, o algoritmo sinaliza que um novo contêiner será necessário.

### Fórmula Matemática:

Para verificar se uma caixa pode ser empacotada no ponto de canto escolhido, a fórmula de distância entre pontos é utilizada:

$$d_x = |x_{\text{ponto}} - x_{\text{container}}|$$

$$d_y = |y_{\text{ponto}} - y_{\text{container}}|$$

$$d_z = |z_{\text{ponto}} - z_{\text{container}}|$$

A caixa pode ser colocada no ponto de canto se:

$$d_x > \text{largura da caixa} \quad \text{e} \quad d_y > \text{altura da caixa} \quad \text{e} \quad d_z > \text{comprimento da caixa}$$

### Pseudo-código

```
Função: empacotar(container, pontoDeCanto, numeroDeCaixas)
    Inicializar listaPontosDisponiveis como uma lista vazia
    Inicializar listaPontosUsados como uma lista vazia
    Inicializar listaCaixasEmpacotadas como uma lista vazia

    Adicionar pontoDeCanto à listaPontosUsados
    Adicionar pontoDeCanto à listaPontosDisponiveis

    Definir largura = container.largura
    Definir altura = container.altura
    Definir comprimento = container.comprimento

    Enquanto houver caixas não empacotadas:
        Se estado == 1:
            Selecionar caixa
            Selecionar ponto de canto
        Senão:
            Selecionar caixa

        Definir coordenadasPonto como as coordenadas do ponto de canto atual
        Definir coordenadaX como uma coordenada representando a largura do container
        Definir coordenadaY como uma coordenada representando a altura do container
        Definir coordenadaZ como uma coordenada representando o comprimento do container

        Se (distância entre coordenadasPonto e coordenadaX > largura da caixa) e
           (distância entre coordenadasPonto e coordenadaY > altura da caixa) e
           (distância entre coordenadasPonto e coordenadaZ > comprimento da caixa):
           
            Atribuir a caixa ao ponto de canto
            Gerar novo ponto de canto para a caixa
            Atualizar estado para 1
        Senão:
            Atualizar estado para 0

        Realizar coleta de lixo para liberar memória
    Retornar listaPontosUsados
```

## Estrutura do Projeto

```
com.codejukebox.optipackroute
│
├── api
│   ├── controllers
│
├── application
│   ├── services
│
├── core
│   ├── algorithms
│
├── domain
│   ├── models
│
└── OptiPackRouteApplication.java
```

![image](https://github.com/user-attachments/assets/fd715350-397d-49c3-a86c-26c41c36d9ab)



## Endpoints Disponíveis

![image](https://github.com/user-attachments/assets/700b8e1f-d1e1-4767-bccb-fa0ac5c786ea)


## Como Baixar e Executar o Projeto

### Pré-requisitos
* JDK 21 ou superior
* Maven

### Passo a Passo

##### 1. Clone o repositório:
```bash
git clone https://github.com/seu-repositorio/optipackroute.git
```
#### 2. Navegue até a pasta do projeto:
```bash
cd optipackroute
```
#### 3. Compile e instale as dependências:
```bash
mvn clean install
```
#### 4. Execute o projeto:
```bash
mvn spring-boot:run
```
#### 5. Acesse o Swagger para visualizar e testar os endpoints:
```
http://localhost:8080/swagger-ui.html
```

## Autenticação
Para autenticar-se corretamente e acessar os endpoints, use as seguintes credenciais:

```
Usuário: optipackroute
Senha: f47ac10b-58cc-4372-a567-0e02b2c3d479
```

Essas credenciais estão configuradas no arquivo InMemoryAuthWebSecurityConfigurer.java e são necessárias para garantir que apenas usuários autenticados possam utilizar os serviços.


![image](https://github.com/user-attachments/assets/82d3878f-2d4e-4623-9a54-abfd41fae6a0)


Você também pode usar o Postman para enviar requisições POST diretamente para os endpoints, utilizando os exemplos de requisição fornecidos.

## Como Acessar e Testar via Postman
1. Abra o Postman e crie uma nova requisição.
2. Configure a URL para http://localhost:8080/api/v1/shortest-path/floyd-warshall (ou outro endpoint).
3. No Postman, vá até a aba Authorization e selecione o tipo Basic Auth.
4. Informe as credenciais: Username: **optipackroute** e Password: **f47ac10b-58cc-4372-a567-0e02b2c3d479**

![image](https://github.com/user-attachments/assets/d483745c-c2e5-43df-b510-d11ea798199a)



No corpo da requisição, insira o JSON de exemplo da requisição.
Envie a requisição e confira a resposta com os resultados da rota calculada.

![image](https://github.com/user-attachments/assets/62472ad5-5d0d-440d-9875-d149adeca907)



## Testes Unitários
Testes unitários estão localizados em src/test/java/com/codejukebox/optipackroute/core.

Exemplo de comando para executar os testes:

```bash
mvn test
```

## Licença
Este projeto é licenciado sob a MIT License.

## Contato
Para mais informações, entre em contato com josesandromendes@gmail.com
