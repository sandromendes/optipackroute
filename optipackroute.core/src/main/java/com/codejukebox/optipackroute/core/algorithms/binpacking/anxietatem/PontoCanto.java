package com.codejukebox.optipackroute.core.algorithms.binpacking.anxietatem;

public class PontoCanto {

    private Coordenadas coordenadas;
    private Caixa caixa;

    public PontoCanto(){
        this.caixa = null;
    }

    public PontoCanto(Coordenadas coordenadas) {
    	this.coordenadas = coordenadas;
    }
    
    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    public Coordenadas getCoordenada() {
        return coordenadas;
    }

    public void setPC(Coordenadas PC) {
        this.coordenadas = PC;
    }

    @Override
    public boolean equals(Object sender) {

        boolean result = false;

        PontoCanto ponto = (PontoCanto) sender;

        if ((ponto.getCoordenada().getX() == getCoordenada().getX())
                && ((ponto.getCoordenada().getY() == getCoordenada().getY())
                && ((ponto.getCoordenada().getZ() == getCoordenada().getZ())))) {
            result = true;
        }

        return result;
    }
    
    public void imprimirDetalhes() {
        String coordenadasStr = (coordenadas != null) ? coordenadas.toString() : "Coordenadas não definidas";
        String caixaStr = (caixa != null) ? caixa.toString() : "Caixa não definida";

        System.out.println(String.format("PontoCanto Details:\n- Coordenadas: %s\n- Caixa: %s", coordenadasStr, caixaStr));
    }
}
