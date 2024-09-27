package com.codejukebox.optipackroute.core.algorithms.binpacking.anxietatem;

import java.util.ArrayList;

public class GarbagePoint {

    public GarbagePoint() {
    }

    public ArrayList<PontoCanto> coleta(ArrayList<PontoCanto> pontos, ArrayList<PontoCanto> APE) {

        for (int j = 0; j < pontos.size(); j++) {
            for (int i = 0; i < APE.size(); i++) {
                if ((pontos.get(j).getCoordenada().getX() == APE.get(i).getCoordenada().getX())
                        && (pontos.get(j).getCoordenada().getY() == APE.get(i).getCoordenada().getY())
                        && (pontos.get(j).getCoordenada().getZ() + pontos.get(j).getCaixa().getDimensoes().getComprimento() == APE.get(i).getCoordenada().getZ())) {
                    APE.remove(i);
                    System.out.println("Removeu ponto de canto. Condição 1");
                }
                if ((pontos.get(j).getCoordenada().getX() == APE.get(i).getCoordenada().getX())
                        && (pontos.get(j).getCoordenada().getY() + pontos.get(j).getCaixa().getDimensoes().getAltura() == APE.get(i).getCoordenada().getY())
                        && (pontos.get(j).getCoordenada().getZ() == APE.get(i).getCoordenada().getZ())) {
                    System.out.println("Removeu ponto de canto. Condição 2");
                    APE.remove(i);
                }
                if ((pontos.get(j).getCoordenada().getX() == APE.get(i).getCoordenada().getX())
                        && (pontos.get(j).getCoordenada().getY() + pontos.get(j).getCaixa().getDimensoes().getAltura() == APE.get(i).getCoordenada().getY())
                        && (pontos.get(j).getCoordenada().getZ() + pontos.get(j).getCaixa().getDimensoes().getComprimento() == APE.get(i).getCoordenada().getZ())) {
                    System.out.println("Removeu ponto de canto. Condição 3");
                    APE.remove(i);
                }
                if ((pontos.get(j).getCoordenada().getX() + pontos.get(j).getCaixa().getDimensoes().getLargura() == APE.get(i).getCoordenada().getX())
                        && (pontos.get(j).getCoordenada().getY() == APE.get(i).getCoordenada().getY())
                        && (pontos.get(j).getCoordenada().getZ() == APE.get(i).getCoordenada().getZ())) {
                    System.out.println("Removeu ponto de canto. Condição 4");
                    APE.remove(i);
                }
                if ((pontos.get(j).getCoordenada().getX() + pontos.get(j).getCaixa().getDimensoes().getLargura() == APE.get(i).getCoordenada().getX())
                        && (pontos.get(j).getCoordenada().getY() == APE.get(i).getCoordenada().getY())
                        && (pontos.get(j).getCoordenada().getZ() + pontos.get(j).getCaixa().getDimensoes().getComprimento() == APE.get(i).getCoordenada().getZ())) {
                    System.out.println("Removeu ponto de canto. Condição 5");
                    APE.remove(i);
                }
                if ((pontos.get(j).getCoordenada().getX() + pontos.get(j).getCaixa().getDimensoes().getLargura() == APE.get(i).getCoordenada().getX())
                        && (pontos.get(j).getCoordenada().getY() + pontos.get(j).getCaixa().getDimensoes().getAltura() == APE.get(i).getCoordenada().getY())
                        && (pontos.get(j).getCoordenada().getZ() == APE.get(i).getCoordenada().getZ())) {
                    System.out.println("Removeu ponto de canto. Condição 6");
                    APE.remove(i);
                }
                if ((pontos.get(j).getCoordenada().getX() + pontos.get(j).getCaixa().getDimensoes().getLargura() == APE.get(i).getCoordenada().getX())
                        && (pontos.get(j).getCoordenada().getY() + pontos.get(j).getCaixa().getDimensoes().getAltura() == APE.get(i).getCoordenada().getY())
                        && (pontos.get(j).getCoordenada().getZ() + pontos.get(j).getCaixa().getDimensoes().getComprimento() == APE.get(i).getCoordenada().getZ())) {
                    System.out.println("Removeu ponto de canto. Condição 7");
                    APE.remove(i);
                }
            }

        }

        for (int i = 0; i < APE.size(); i++) {
            PontoCanto pci, pcj;
            pci = APE.get(i);

            for (int j = i; j < APE.size(); j++) {
                pcj = APE.get(j);
                if (i != j) {
                    if (pci.getCoordenada().getX() == pcj.getCoordenada().getX()
                            && pci.getCoordenada().getY() == pcj.getCoordenada().getY()
                            && pci.getCoordenada().getZ() == pcj.getCoordenada().getZ()) {
                        APE.remove(pcj);
                        System.out.println("Removeu ponto de canto idêntico");
                    }
                }
            }

        }

        return APE;
    }
}
