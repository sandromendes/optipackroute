# OptiPackRoute: Optimal Path Routing Algorithms

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.0-brightgreen)
![Redis](https://img.shields.io/badge/Redis-6.2-red)
![License](https://img.shields.io/badge/license-MIT-green)


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

O projeto OptiPackRoute é dividido em módulos para garantir uma arquitetura limpa e organizada. A **API** gerencia as interações externas via endpoints REST, enquanto o **Application** orquestra os casos de uso, coordenando a execução dos algoritmos de rota e empacotamento. Os módulos **Core** e **Domain** concentram a lógica dos algoritmos e entidades de negócio, enquanto o **Persistence** gerencia a persistência dos resultados e simulações.

![image](https://github.com/user-attachments/assets/df8158b7-23ac-4fcf-8455-6329c3786d99)


Os componentes do projeto OptiPackRoute desempenham papéis fundamentais na organização da lógica de negócio. O **Controller** gerencia as requisições HTTP, encaminhando-as para o **Service**, que orquestra a lógica de aplicação e interage com os **Algorithms** para calcular rotas e empacotamento. O **Domain** (Model) define as entidades e regras de negócio, enquanto o **Repository** cuida da persistência dos dados e do acesso ao banco de dados.


![image](https://github.com/user-attachments/assets/93f20446-3ea0-4382-a757-34222da23542)

## Como Rodar a Aplicação
Para executar a aplicação **OptiPackRoute**, siga os passos detalhados abaixo. Esta aplicação é uma **API RESTful** que realiza simulações de rotas e distribuição de carga em contêineres, armazenando temporariamente os resultados em uma base de dados **Redis** enquanto os algoritmos estão em execução. A execução deve ser feita a partir da camada optipackroute.api.

### Pré-requisitos

Antes de iniciar a aplicação, verifique se você possui os seguintes pré-requisitos:

1. **Java Development Kit (JDK) 17 ou superior**: Certifique-se de que o JDK está instalado e configurado corretamente. Você pode verificar a instalação executando o comando:

```bash
java -version
```

2. **Maven**: O Maven deve estar instalado para gerenciar as dependências do projeto. Verifique a instalação com:

```
mvn -version
```

3. **Redis**: 

Para o correto funcionamento do projeto OptiPackRoute, é necessário ter previamente o banco de dados Redis configurado. O Redis será utilizado para o armazenamento de cache e outras funcionalidades relacionadas ao desempenho da aplicação, garantindo respostas rápidas e eficientes durante a execução dos algoritmos de roteamento e empacotamento.

**Configuração do Redis**

Para que o projeto **OptiPackRoute** funcione corretamente, é necessário configurar o banco de dados **Redis**, que será usado para caching e otimização do desempenho. 

Após instalar o Redis, é necessário configurar a base de dados chamada **optipackroute-dbcache**. Por padrão, o Redis utiliza a DB 0. Se necessário, você pode modificar o banco de dados para uma DB específica no arquivo de configuração do Redis (redis.conf) ou através das configurações de Spring Boot.

Para manter a DB 0, você pode seguir a configuração padrão.

Certifique-se de que as seguintes propriedades estão configuradas no arquivo application.properties:

```json
# Configuração do Redis
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.database=0  # ou a DB configurada
```

### Passo a Passo para Rodar a Aplicação

1. **Clone o repositório**: Clone o repositório da aplicação em sua máquina local usando o seguinte comando:
```bash
git clone https://github.com/sandromendes/optipackroute.git
```

2. **Navegue até a camada da API**: Acesse a pasta do projeto e navegue até a camada optipackroute.api:
```bash
cd optipackroute/optipackroute.api
```

3. **Compile o projeto**: Execute o comando abaixo, na raiz do projeto, para compilar e resolver todas as dependências:
```bash
mvn clean install
```

4. **Execute a aplicação**: Após a compilação bem-sucedida, entre no diretório optipackroute.api, para rodar a aplicação usando o Maven:

```bash
cd optipackroute.api
```

```bash
mvn spring-boot:run
```

Ou, na raiz da aplicação, digite o comando:

```bash
mvn -pl optipackroute.api spring-boot:run
```

Este comando iniciará a aplicação, que por padrão estará disponível em http://localhost:8080.

5. **Acesse o Swagger para visualizar e testar os endpoints**:
```
http://localhost:8080/swagger-ui.html
```

## Endpoints Disponíveis

![image](https://github.com/user-attachments/assets/700b8e1f-d1e1-4767-bccb-fa0ac5c786ea)


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
