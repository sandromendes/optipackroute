package com.codejukebox.optipackroute.core.algorithms.binpacking.anxietatem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A classe Empacotador gerencia o empacotamento de caixas em um container, 
 * considerando a seleção de caixas e pontos de canto, além de gerar novos 
 * pontos de canto com base nas caixas empacotadas.
 */
public class Empacotador {

    private int estado = 1; // Estado atual do empacotador
    private ArrayList<PontoCanto> pontosDeCantoDisponiveis; // Conjunto de pontos de canto disponíveis
    private ArrayList<PontoCanto> pontosDeCantoUtilizados; // Conjunto de pontos já utilizados e em sequência
    private ArrayList<Caixa> caixasPosicionadas; // Conjunto de caixas empacotadas
    private ArrayList<Caixa> caixasNaoPosicionadas; // Conjunto de caixas disponíveis
    private Caixa caixaEscolhida; // Caixa atualmente escolhida

    /**
     * Construtor da classe Empacotador.
     */
    public Empacotador() {
    }

    /**
     * Escolhe uma caixa aleatoriamente do conjunto de caixas não posicionadas e 
     * a adiciona ao conjunto de caixas posicionadas.
     * 
     * @return A caixa escolhida aleatoriamente.
     * 
     * @example
     * <pre>
     * Empacotador empacotador = new Empacotador();
     * Caixa caixa = empacotador.escolheCaixa();
     * System.out.println("Caixa escolhida: " + caixa.getID());
     * </pre>
     */
    public Caixa escolheCaixa() {

        Caixa caixa = null;
        Random random = new Random();


        int randomIndice = random.nextInt(caixasNaoPosicionadas.size());
        caixa = caixasNaoPosicionadas.get(randomIndice);

        caixasPosicionadas.add(caixa);
        caixasNaoPosicionadas.remove(randomIndice);
        System.out.println("Caixa " + caixa.getID() + " escolhida");

        return caixa;

    }

    /**
     * Escolhe um ponto de canto aleatoriamente do conjunto de pontos de canto disponíveis e
     * o adiciona ao conjunto de pontos de canto utilizados.
     * 
     * @return O ponto de canto escolhido aleatoriamente.
     * 
     * @example
     * <pre>
     * Empacotador empacotador = new Empacotador();
     * PontoCanto ponto = empacotador.escolhePonto();
     * System.out.println("Ponto de canto escolhido: " + ponto.getPontoDeCanto());
     * </pre>
     */
    public PontoCanto escolhePonto() {
        PontoCanto pontoDeCanto = null;

        //Heurística Aleatória
        Random random = new Random();
        int randomIndice = random.nextInt(pontosDeCantoDisponiveis.size());

        System.out.println("Numero aleatório escolhido: " + randomIndice);

        pontoDeCanto = pontosDeCantoDisponiveis.get(randomIndice);
        pontosDeCantoUtilizados.add(pontoDeCanto);
        pontosDeCantoDisponiveis.remove(randomIndice);

        /*
         * Heurística À esquerda
         * Empacotar no ponto de canto mais à esquerda e no nível mais baixo
         */

        return pontoDeCanto;
    }

    /**
     * Preenche o conjunto de caixas não posicionadas com caixas padrão 
     * até o limite especificado.
     * 
     * @param limite O número de caixas a serem adicionadas.
     * 
     * @example
     * <pre>
     * Empacotador empacotador = new Empacotador();
     * empacotador.carregaArrayCaixas(50);
     * </pre>
     */
    public void carregaArrayCaixas(int limite) {

        caixasNaoPosicionadas = new ArrayList<Caixa>();

        for (int i = 0; i < limite; i++) {
            Caixa caixa = new Caixa();
            caixa.setID(i);
            Dimensoes dimensoes = new Dimensoes(8, 5, 4);
            caixa.setDimensoes(dimensoes);
            caixasNaoPosicionadas.add(caixa);
        }
    }

    /**
     * Preenche o conjunto de caixas não posicionadas com caixas geradas aleatoriamente.
     * 
     * @example
     * <pre>
     * Empacotador empacotador = new Empacotador();
     * empacotador.carregaArrayCaixasRandom();
     * </pre>
     */
    public void carregaArrayCaixasRandom() {

        caixasNaoPosicionadas = new ArrayList<Caixa>();
        Random random = new Random();
        int largura, comprimento, altura;
        for (int i = 0; i < 200; i++) {
            Caixa caixa = new Caixa();
            caixa.setID(i);
            largura = random.nextInt(10) + 1;
            comprimento = random.nextInt(10) + 1;
            altura = random.nextInt(10) + 1;
            Dimensoes dimensoes = new Dimensoes(largura, comprimento, altura);
            caixa.setDimensoes(dimensoes);
            caixasNaoPosicionadas.add(caixa);
        }
    }

    /**
     * Empacota caixas dentro de um container utilizando um ponto de canto inicial.
     * O processo considera a escolha aleatória de caixas e pontos de canto,
     * e evita ultrapassar os limites do container.
     * 
     * @param conteiner O container onde as caixas serão empacotadas.
     * @param pontoDeCanto O ponto de canto inicial para o empacotamento.
     * @param numeroDeCaixas O número de caixas a serem carregadas.
     * 
     * @example
     * <pre>
     * Empacotador empacotador = new Empacotador();
     * Caixa container = new Caixa();
     * PontoCanto pontoInicial = new PontoCanto(new Coordenadas(0, 0, 0));
     * empacotador.empacotar(container, pontoInicial, 100);
     * </pre>
     */
    public List<PontoCanto> empacotar(Caixa conteiner, PontoCanto pontoDeCanto, int numeroDeCaixas) {

        //carregaArrayCaixas(numeroDeCaixas);
        carregaArrayCaixasRandom();

        pontosDeCantoDisponiveis = new ArrayList<PontoCanto>();
        pontosDeCantoUtilizados = new ArrayList<PontoCanto>();

        caixasPosicionadas = new ArrayList<Caixa>();

        pontosDeCantoUtilizados.add(pontoDeCanto);

        pontosDeCantoDisponiveis.add(pontoDeCanto);

        int largura, altura, comprimento;

        largura = conteiner.getDimensoes().getLargura();
        altura = conteiner.getDimensoes().getAltura();
        comprimento = conteiner.getDimensoes().getComprimento();
        
        /*
         * Condição de parada:
         * Quando não for mais possível empacotar mais nenhuma caixa, ou seja,
         * quando qualquer caixa a ser empacotada ultrapassar o limite do conteiner
         */

        System.out.println("Tamanho do array de caixas antes do Loop: "+caixasNaoPosicionadas.size());
        for (int i = 0; caixasNaoPosicionadas.size()>0 ; i++) {
            System.out.println("Tamanho do array de caixas é: "+pontosDeCantoDisponiveis.size()+" na iteração nº: "+ i);

            if (estado == 1) {
                caixaEscolhida = escolheCaixa();
                pontoDeCanto = escolhePonto();
                System.out.println("Ponto com coordenadas: " + pontoDeCanto.getCoordenada().getX() + "," + pontoDeCanto.getCoordenada().getY() + "," + pontoDeCanto.getCoordenada().getZ() + " ESCOLHIDO. Condição st = 1");
            } else if (estado == 0) {
                caixaEscolhida = escolheCaixa();
                System.out.println("Ponto com coordenadas: " + pontoDeCanto.getCoordenada().getX() + "," + pontoDeCanto.getCoordenada().getY() + "," + pontoDeCanto.getCoordenada().getZ() + " ESCOLHIDO. Condição st = 0");
            }

            
            /*
             * Não ultrapassar o limite do bin
             * sendo pci o ponto de canto atual e ci a caixa a ser inserida
             */
            Coordenadas coordenadaDoPonto = new Coordenadas(pontoDeCanto.getCoordenada().getX(), pontoDeCanto.getCoordenada().getY(), pontoDeCanto.getCoordenada().getZ());
            Coordenadas coordenadaCaixaEixoX = new Coordenadas(largura, 0, 0);
            Coordenadas coordenadaCaixaEixoY = new Coordenadas(0, altura, 0);
            Coordenadas coordenadaCaixaEixoZ = new Coordenadas(0, 0, comprimento);

            //System.out.println("Tamanho do array de pontos criados: " + APCC.size());
            if ((calcularDistanciaEntrePontos(coordenadaDoPonto, coordenadaCaixaEixoX) > caixaEscolhida.getDimensoes().getLargura())
                    && (calcularDistanciaEntrePontos(coordenadaDoPonto, coordenadaCaixaEixoY) > caixaEscolhida.getDimensoes().getAltura())
                    && (calcularDistanciaEntrePontos(coordenadaDoPonto, coordenadaCaixaEixoZ) > caixaEscolhida.getDimensoes().getComprimento())) {
                pontoDeCanto.setCaixa(caixaEscolhida);
                geraPontoDeCanto(pontoDeCanto, caixaEscolhida);
                estado = 1;
            } else {

                estado = 0;
            }

             Runtime runtume = Runtime.getRuntime();
             runtume.gc();
        }

        System.out.println("Quantidade de pontos utilizados: " + pontosDeCantoUtilizados.size());
        System.out.println("Quantidade de caixas empacotadas: " + caixasPosicionadas.size());
        
        return pontosDeCantoUtilizados; // Retornar o HashMap com pontos de canto e caixas
    }
    
    /**
     * Gera novos pontos de canto com base nas dimensões da caixa e nas coordenadas fornecidas.
     * 
     * @param pontoDeCanto O ponto de canto a ser utilizado.
     * @param caixa A caixa que define as dimensões para o cálculo dos novos pontos de canto.
     */
    public void geraPontoDeCanto(PontoCanto pontoDeCanto, Caixa caixa) {
        //escolha dos pontos de canto
        //se pci = <0,0,0>

        Coordenadas coordenada;
        ArrayList<PontoCanto> pontos = new ArrayList<PontoCanto>();

        if ((pontoDeCanto.getCoordenada().getX() == 0) && (pontoDeCanto.getCoordenada().getY() == 0) && (pontoDeCanto.getCoordenada().getZ() == 0)) {
            //cria novos pontos de canto

            coordenada = new Coordenadas(caixa.getDimensoes().getLargura(), 0, 0);
            PontoCanto pci1 = new PontoCanto(coordenada);
            pci1.setCaixa(caixa);
            
            System.out.println("Ponto com coordenadas: " 
            		+ pci1.getCoordenada().getX() + "," 
            		+ pci1.getCoordenada().getY() + "," 
            		+ pci1.getCoordenada().getZ() + " GERADO");
            
            pontosDeCantoDisponiveis.add(pci1);
            pontos.add(pci1);
            
            coordenada = new Coordenadas(0, caixa.getDimensoes().getAltura(), 0);
            PontoCanto pci2 = new PontoCanto(coordenada);
            pci2.setCaixa(caixa);
            
            System.out.println("Ponto com coordenadas: " 
            		+ pci2.getCoordenada().getX() + "," 
            		+ pci2.getCoordenada().getY() + "," 
            		+ pci2.getCoordenada().getZ() + " GERADO");
            
            pontosDeCantoDisponiveis.add(pci2);
            pontos.add(pci2);
            
            coordenada = new Coordenadas(0, 0, caixa.getDimensoes().getComprimento());
            PontoCanto pci3 = new PontoCanto(coordenada);
            pci3.setCaixa(caixa);
            
            System.out.println("Ponto com coordenadas: " 
            		+ pci3.getCoordenada().getX() + "," 
            		+ pci3.getCoordenada().getY() + "," 
            		+ pci3.getCoordenada().getZ() + " GERADO");

            pontosDeCantoDisponiveis.add(pci3);
            pontos.add(pci3);
        } else {

            if (pontoDeCanto.getCaixa().getDimensoes().getLargura() < caixa.getDimensoes().getLargura()) {
                coordenada = new Coordenadas(pontoDeCanto.getCoordenada().getX() + pontoDeCanto.getCaixa().getDimensoes().getLargura(), pontoDeCanto.getCoordenada().getY(), pontoDeCanto.getCoordenada().getZ());
                
                PontoCanto pontoDeCantoGeradoPelaLargura = new PontoCanto(coordenada);
                pontoDeCantoGeradoPelaLargura.setCaixa(caixa);
                
                System.out.println("Ponto com coordenadas: " + pontoDeCantoGeradoPelaLargura.getCoordenada().getX() + "," + pontoDeCantoGeradoPelaLargura.getCoordenada().getY() + "," + pontoDeCantoGeradoPelaLargura.getCoordenada().getZ() + " GERADO");
                
                pontosDeCantoDisponiveis.add(pontoDeCantoGeradoPelaLargura);
                pontos.add(pontoDeCantoGeradoPelaLargura);
            } else {
                coordenada = new Coordenadas(pontoDeCanto.getCoordenada().getX() + caixa.getDimensoes().getLargura(), pontoDeCanto.getCoordenada().getY(), pontoDeCanto.getCoordenada().getZ());
                
                PontoCanto pontoDeCantoGeradoPelaLargura = new PontoCanto(coordenada);
                pontoDeCantoGeradoPelaLargura.setCaixa(caixa);
                
                System.out.println("Ponto com coordenadas: " + pontoDeCantoGeradoPelaLargura.getCoordenada().getX() + "," + pontoDeCantoGeradoPelaLargura.getCoordenada().getY() + "," + pontoDeCantoGeradoPelaLargura.getCoordenada().getZ() + " GERADO");
                
                pontosDeCantoDisponiveis.add(pontoDeCantoGeradoPelaLargura);
                pontos.add(pontoDeCantoGeradoPelaLargura);
            }

            if (pontoDeCanto.getCaixa().getDimensoes().getComprimento() < caixa.getDimensoes().getComprimento()) {
                coordenada = new Coordenadas(pontoDeCanto.getCoordenada().getX(), pontoDeCanto.getCoordenada().getY(), pontoDeCanto.getCoordenada().getZ() + pontoDeCanto.getCaixa().getDimensoes().getComprimento());
                
                PontoCanto pontoDeCantoGeradoPeloComprimento = new PontoCanto(coordenada);
                pontoDeCantoGeradoPeloComprimento.setCaixa(caixa);
                
                System.out.println("Ponto com coordenadas: " + pontoDeCantoGeradoPeloComprimento.getCoordenada().getX() + "," + pontoDeCantoGeradoPeloComprimento.getCoordenada().getY() + "," + pontoDeCantoGeradoPeloComprimento.getCoordenada().getZ() + " GERADO");
                
                pontosDeCantoDisponiveis.add(pontoDeCantoGeradoPeloComprimento);
                pontos.add(pontoDeCantoGeradoPeloComprimento);
            } else {
                coordenada = new Coordenadas(pontoDeCanto.getCoordenada().getX(), pontoDeCanto.getCoordenada().getY(), pontoDeCanto.getCoordenada().getZ() + caixa.getDimensoes().getComprimento());
                
                PontoCanto pontoDeCantoGeradoPeloComprimento = new PontoCanto(coordenada);
                pontoDeCantoGeradoPeloComprimento.setCaixa(caixa);
                
                System.out.println("Ponto com coordenadas: " + pontoDeCantoGeradoPeloComprimento.getCoordenada().getX() + "," + pontoDeCantoGeradoPeloComprimento.getCoordenada().getY() + "," + pontoDeCantoGeradoPeloComprimento.getCoordenada().getZ() + " GERADO");
                
                pontosDeCantoDisponiveis.add(pontoDeCantoGeradoPeloComprimento);
                pontos.add(pontoDeCantoGeradoPeloComprimento);
            }

            if (pontoDeCanto.getCaixa().getDimensoes().getAltura() < caixa.getDimensoes().getAltura()) {
                coordenada = new Coordenadas(pontoDeCanto.getCoordenada().getX(), pontoDeCanto.getCoordenada().getY() + pontoDeCanto.getCaixa().getDimensoes().getAltura(), pontoDeCanto.getCoordenada().getZ());
                
                PontoCanto pontoDeCantoGeradoPelaAltura = new PontoCanto(coordenada);
                pontoDeCantoGeradoPelaAltura.setCaixa(caixa);
                
                System.out.println("Ponto com coordenadas: " + pontoDeCantoGeradoPelaAltura.getCoordenada().getX() + "," + pontoDeCantoGeradoPelaAltura.getCoordenada().getY() + "," + pontoDeCantoGeradoPelaAltura.getCoordenada().getZ() + " GERADO");
                
                pontosDeCantoDisponiveis.add(pontoDeCantoGeradoPelaAltura);
                pontos.add(pontoDeCantoGeradoPelaAltura);
            } else {
                coordenada = new Coordenadas(pontoDeCanto.getCoordenada().getX(), pontoDeCanto.getCoordenada().getY() + caixa.getDimensoes().getAltura(), pontoDeCanto.getCoordenada().getZ());
                PontoCanto pontoDeCantoGeradoPelaAltura = new PontoCanto(coordenada);
                pontoDeCantoGeradoPelaAltura.setCaixa(caixa);
                System.out.println("Ponto com coordenadas: " + pontoDeCantoGeradoPelaAltura.getCoordenada().getX() + "," + pontoDeCantoGeradoPelaAltura.getCoordenada().getY() + "," + pontoDeCantoGeradoPelaAltura.getCoordenada().getZ() + " GERADO");
                pontosDeCantoDisponiveis.add(pontoDeCantoGeradoPelaAltura);
                pontos.add(pontoDeCantoGeradoPelaAltura);
            }
        }

        System.out.println("Tamanho do array de pontos disponíveis antes do garbage: " + pontosDeCantoDisponiveis.size());

        GarbagePoint garbagePoint = new GarbagePoint();
        pontosDeCantoDisponiveis = garbagePoint.coleta(pontos, pontosDeCantoDisponiveis);

        System.out.println("Tamanho do array de pontos disponíveis depois do garbage: " + pontosDeCantoDisponiveis.size());

    }

    /**
     * Calcula a distância entre dois pontos no espaço tridimensional.
     * 
     * @param ponto1 O primeiro ponto.
     * @param ponto2 O segundo ponto.
     * @return A distância entre os dois pontos.
     */
    public Double calcularDistanciaEntrePontos(Coordenadas pontoA, Coordenadas pontoB) {
        Double distancia = Math.sqrt(Math.pow(pontoB.getY() - pontoA.getY(), 2) + Math.pow(pontoB.getX() - pontoA.getX(), 2) + Math.pow(pontoB.getZ() - pontoA.getZ(), 2));

        return distancia;
    }
}
	