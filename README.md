# OptiPackRoute: Optimal Path Routing Algorithms

## Descrição do Projeto

O OptiPackRoute é uma aplicação Java que implementa algoritmos clássicos de roteamento de caminhos ótimos, focando em soluções de problemas de logística e otimização de transporte. A aplicação expõe três algoritmos principais: Floyd-Warshall, Dijkstra e A-Star, acessíveis via uma API REST. Esses algoritmos permitem calcular rotas eficientes em grafos, simulando cenários como redes de transporte, planejamento de entregas, e otimização de percursos.

## Motivação

Em ambientes de logística compartilhada, como o planejamento de rotas de entregas de mercadorias em centros de distribuição e entrega de última milha, encontrar rotas ótimas que minimizem tempo e custo é um desafio essencial. O projeto OptiPackRoute simula esse tipo de situação, permitindo que usuários calculem rotas eficientes considerando vários pontos intermediários de parada.

## Melhoria do Algoritmo Floyd-Warshall

Originalmente, o algoritmo Floyd-Warshall resolve o problema de encontrar os caminhos mais curtos entre todos os pares de nós em um grafo, com complexidade assintótica de 
𝑂(𝑛3), onde 𝑛 é o número de vértices. No entanto, o algoritmo tradicional não foi projetado para lidar de forma eficiente com problemas em que é necessário passar por pontos intermediários específicos, como ocorre em cenários de logística compartilhada, onde mercadorias são entregues em várias localidades antes de chegar ao destino final.

No OptiPackRoute, o algoritmo Floyd-Warshall foi melhorado para incluir a capacidade de otimizar rotas que envolvem destinos intermediários, simulando cenários complexos de logística de múltiplas paradas. Este aprimoramento permite calcular não apenas o menor caminho entre dois pontos, mas também a melhor configuração de rotas que passam por destinos intermediários obrigatórios — o que reflete casos reais, como a otimização de entregas para diferentes destinos em um sistema de distribuição compartilhada.

## Motivação Técnica: Complexidade e Roteamento em Cenários Logísticos
Em termos de computação, problemas como a otimização de rotas em múltiplas paradas muitas vezes se enquadram em categorias de problemas NP-difíceis, como o Problema do Caixeiro Viajante (TSP - Travelling Salesman Problem), onde a busca pela solução ótima pode exigir tempo exponencial. Embora o Floyd-Warshall seja eficiente para certos cenários com complexidade cúbica 
𝑂(𝑛3), a necessidade de destinos intermediários aumenta a complexidade cognitiva da solução, já que o algoritmo passa a verificar a combinação ótima de caminhos possíveis com base em diferentes configurações de pontos de parada.

A otimização de rotas com paradas intermediárias envolve uma abordagem diferente em relação à simples minimização de distância: é preciso considerar a ordem de visita aos nós intermediários e ajustar a rota de maneira a minimizar o custo total, o que adiciona uma sobrecarga significativa ao cálculo. O aprimoramento do Floyd-Warshall no OptiPackRoute ataca esse desafio ao reorganizar dinamicamente a rota durante o cálculo, oferecendo uma solução eficiente para problemas que tradicionalmente teriam complexidade não polinomial.

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── codejukebox/
│   │           └── optipackroute/
│   │               ├── common/
│   │               ├── config/
│   │               ├── controllers/
│   │               ├── dtos/
│   │               ├── heuristics/
│   │               ├── samples/
│   │               └── services/
│   └── resources/
├── test/
│   └── java/
│       └── com/
│           └── codejukebox/
│               └── optipackroute/

```


## Endpoints Disponíveis

![image](https://github.com/user-attachments/assets/0ea26e53-bb7d-4302-a736-61497ec35b55)


### 1. Floyd-Warshall Melhorado
```
Endpoint: /api/v1/router/floyd-warshall
Método: POST
Requisição: JSON com matriz de adjacências e nós de partida, destino e intermediários.
```
* Exemplo de Request:
```json
{
    "matrix": [
        [0, 3, 8, 999999, 4],
        [999999, 0, 999999, 1, 7],
        [999999, 4, 0, 999999, 999999],
        [2, 999999, 5, 0, 999999],
        [999999, 999999, 999999, 6, 0]
    ],
    "nodes": [1, 4, 2, 5], // Nós intermediários
    "startNode": 2
}
```
* Resposta:
```json
{
    "optimalPath": [2, 4, 1, 5],
    "totalCost": 7,
    "distanceMatrix": [[...]],
    "predecessorMatrix": [[...]],
    "subPaths": {...}
}
```

### 2. Dijkstra
```
Endpoint: /api/v1/router/dijkstra
Método: POST
Requisição: JSON com matriz de adjacências e nós de partida e destino.
```
* Exemplo de request:
```json
{
    "matrix": [
        [0,     3, 8, 999999, 4],
        [999999, 0, 999999, 1, 7],
        [999999, 4, 0, 999999, 999999],
        [2, 999999, 5, 0, 999999],
        [999999, 999999, 999999, 6, 0]
    ],
    "startNode": 1,
    "targetNode": 5
}
```
* Resposta:
```json
{
    "optimalPath": [1, 4, 5],
    "totalCost": 6,
    "distanceMatrix": [[...]],
    "predecessorMatrix": [[...]]
}
```

### 4. A-Star
```
Endpoint: /api/v1/router/astar
Método: POST
Requisição: JSON com matriz de adjacências, nós de partida e destino, e heurísticas.
```
* Exemplo de request:
```json
{
    "matrix": [
        [0, 3, 8, 999999, 4],
        [999999, 0, 999999, 1, 7],
        [999999, 4, 0, 999999, 999999],
        [2, 999999, 5, 0, 999999],
        [999999, 999999, 999999, 6, 0]
    ],
    "startNode": 1,
    "targetNode": 5,
    "heuristic": [7, 6, 2, 1, 0]
}
```
* Resposta:
```json
{
    "optimalPath": [1, 4, 5],
    "totalCost": 6
}
```
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
2. Configure a URL para http://localhost:8080/api/v1/router/floyd-warshall (ou outro endpoint).
3. No Postman, vá até a aba Authorization e selecione o tipo Basic Auth.
4. Informe as credenciais: Username: **optipackroute** e Password: **f47ac10b-58cc-4372-a567-0e02b2c3d479**

![image](https://github.com/user-attachments/assets/4959e31d-4a16-4fd9-b953-b10a4dc7e081)


No corpo da requisição, insira o JSON de exemplo da requisição.
Envie a requisição e confira a resposta com os resultados da rota calculada.

![image](https://github.com/user-attachments/assets/219708c3-b46c-42f5-91de-8894960638ee)


## Testes Unitários
Testes unitários estão localizados em src/test/java/com/codejukebox/optipackroute/.

Exemplo de comando para executar os testes:

```bash
mvn test
```

## Licença
Este projeto é licenciado sob a MIT License.

## Contato
Para mais informações, entre em contato com josesandromendes@gmail.com
