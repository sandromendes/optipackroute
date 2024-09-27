package com.codejukebox.optipackroute.core.samples.binpacking;

import java.util.List;

import com.codejukebox.optipackroute.core.algorithms.binpacking.anxietatem.Caixa;
import com.codejukebox.optipackroute.core.algorithms.binpacking.anxietatem.Coordenadas;
import com.codejukebox.optipackroute.core.algorithms.binpacking.anxietatem.Dimensoes;
import com.codejukebox.optipackroute.core.algorithms.binpacking.anxietatem.Empacotador;
import com.codejukebox.optipackroute.core.algorithms.binpacking.anxietatem.PontoCanto;

public class AnxietatemSample {
	
    public static void main(String args[]){        
        Dimensoes dimensoes = new Dimensoes(100,40,20);
        
        Caixa conteiner = new Caixa();
        conteiner.setDimensoes(dimensoes);
        
        Coordenadas coordenadas = new Coordenadas(0,0,0);
        
        PontoCanto pontoCanto = new PontoCanto();
        pontoCanto.setPC(coordenadas);
        
        long inicio = System.currentTimeMillis();
        
        Empacotador empacotador = new Empacotador();
        List<PontoCanto> resultado = empacotador.empacotar(conteiner, pontoCanto, 10);
        
        for (PontoCanto item : resultado) {
        	System.out.println();
			item.imprimirDetalhes();
		}
        
        long fim = System.currentTimeMillis();

        System.out.println("Tempo decorrido: "+(fim-inicio)+"ms");
    }
}
