package com.codejukebox.optipackroute.core.samples.binpacking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.codejukebox.optipackroute.core.algorithms.binpacking.anxietatem.AnxietatemAlgorithm;
import com.codejukebox.optipackroute.domain.models.binpacking.CornerPoint;
import com.codejukebox.optipackroute.domain.models.binpacking.Dimensions;

@SpringBootApplication(scanBasePackages = {"com.codejukebox.optipackroute"})
public class AnxietatemSample {
	
	public static void main(String[] args) {        
		// Inicia o contexto da aplicação Spring
        ApplicationContext context = SpringApplication.run(AnxietatemSample.class, args);

        // Obtém o bean do algoritmo Anxietatem com as dependências injetadas
        AnxietatemAlgorithm packer = context.getBean(AnxietatemAlgorithm.class);

        // Define as dimensões e a quantidade de caixas
        Dimensions dimensions = new Dimensions(100, 40, 20);

        // Marca o tempo de início
        long start = System.currentTimeMillis();

        // Configura o algoritmo com as dimensões e o número de caixas
        packer.setup(dimensions, 200);

        // Executa o empacotamento
        var result = packer.pack();

        // Imprime os detalhes dos pontos de canto utilizados
        for (CornerPoint item : result.getUsedCornerPoints()) {
            System.out.println();
            item.printDetails();
        }

        // Marca o tempo de término
        long end = System.currentTimeMillis();

        // Exibe o tempo total de execução
        System.out.println("Elapsed time: " + (end - start) + "ms");
	}
}
