/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gacurve;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Jet_m
 */
public class GACurve {
    Population population = new Population();
    Individual fittest;
    Individual secondFittest;
    int generationCount = 0;
    int[] TARGET = {1,0,1,0,0,1,1,1,1,0,1,1,1};

    public void execute() {

        Random rn = new Random();
        Boolean found = false;
        GACurve gac = new GACurve();

        gac.population.initializePopulation(10);
        gac.population.calculateFitness();

        System.out.println("Generation: " + gac.generationCount + " Fittest: " + gac.population.fittest);

        while (!found) {
            if(gac.population.fittest==13){
                found = true;
                break;
            }
            
            ++gac.generationCount;

            gac.selection();

            gac.crossover();

            if (rn.nextInt()%7 < 5) {
                gac.mutation();
            }

            gac.addFittestOffspring();

            gac.population.calculateFitness();

            System.out.println("Generation: " + gac.generationCount + " Fittest: " + gac.population.fittest);
        }
        
        

        System.out.println("\nSolution found in generation " + gac.generationCount);
        System.out.println("Fitness: "+gac.population.getFittest().fitness);
        /*System.out.print("Genes: ");
        for (int i = 0; i < 13; i++) {
            System.out.print(gac.population.getFittest().genes[i]);
        }

        System.out.println("");*/
        int pos = 8;
        int n = TARGET.length;
        int aa[] = new int[pos];
        int bb[] = new int[n-pos];
        int aaa;
        int bbb;
        for(int i=0;i<pos;i++){
            aa[i] = gac.population.getFittest().genes[i];
        }
        for (int i = 0; i < n - pos; i++) {
            bb[i] = gac.population.getFittest().genes[i + pos];
        }
        
        System.out.print("Genes: ");
        System.out.print(Arrays.toString(aa));
        System.out.print(Arrays.toString(bb));
        System.out.println("");
        
        aaa=binC(aa);
        bbb=binC(bb);
        System.out.print("Los valores resultantes son: "+aaa+" + "+bbb+"x");
    }
    
    int binC(int[] yo){
        int exp=0;
        int dec=0;
        for (int i = yo.length - 1; i >= 0; i--) {
            if (yo[i] == 1) //Es un 1, acumulamos la potencia de 2 correspondiente
            dec += Math.pow(2, exp);
            exp++;
        }
        return dec;
    }

    void selection() {

        fittest = population.getFittest();
        secondFittest = population.getSecondFittest();
    }

    void crossover() {
        Random rn = new Random();

        int crossOverPoint = rn.nextInt(population.individuals[0].geneLength);

        for (int i = 0; i < crossOverPoint; i++) {
            int temp = fittest.genes[i];
            fittest.genes[i] = secondFittest.genes[i];
            secondFittest.genes[i] = temp;

        }

    }

    void mutation() {
        Random rn = new Random();

        int mutationPoint = rn.nextInt(population.individuals[0].geneLength);
        if (fittest.genes[mutationPoint] == 0) {
            fittest.genes[mutationPoint] = 1;
        } else {
            fittest.genes[mutationPoint] = 0;
        }

        mutationPoint = rn.nextInt(population.individuals[0].geneLength);

        if (secondFittest.genes[mutationPoint] == 0) {
            secondFittest.genes[mutationPoint] = 1;
        } else {
            secondFittest.genes[mutationPoint] = 0;
        }
    }

    Individual getFittestOffspring() {
        if (fittest.fitness > secondFittest.fitness) {
            return fittest;
        }
        return secondFittest;
    }


    void addFittestOffspring() {

        fittest.calcFitness();
        secondFittest.calcFitness();

        int leastFittestIndex = population.getLeastFittestIndex();

        population.individuals[leastFittestIndex] = getFittestOffspring();
    }

}

class Individual {
    GACurve ga = new GACurve();
    int fitness = 0;
    int[] genes = new int[13];
    int geneLength = 13;

    public Individual() {
        Random rn = new Random();

        for (int i = 0; i < genes.length; i++) {
            genes[i] = Math.abs(rn.nextInt() % 2);
        }

        fitness = 0;
    }

    public void calcFitness() {
        int len = ga.TARGET.length;
        fitness = 0;
        for(int i=0;i<len;i++){
           if(genes[i] == ga.TARGET[i])
               fitness++;
        }
        
    }

}

class Population {

    int popSize = 50;
    Individual[] individuals = new Individual[13];
    int fittest = 0;

    public void initializePopulation(int size) {
        for (int i = 0; i < individuals.length; i++) {
            individuals[i] = new Individual();
        }
    }

    public Individual getFittest() {
        int maxFit = Integer.MIN_VALUE;
        int maxFitIndex = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (maxFit <= individuals[i].fitness) {
                maxFit = individuals[i].fitness;
                maxFitIndex = i;
            }
        }
        fittest = individuals[maxFitIndex].fitness;
        return individuals[maxFitIndex];
    }

    public Individual getSecondFittest() {
        int maxFit1 = 0;
        int maxFit2 = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (individuals[i].fitness > individuals[maxFit1].fitness) {
                maxFit2 = maxFit1;
                maxFit1 = i;
            } else if (individuals[i].fitness > individuals[maxFit2].fitness) {
                maxFit2 = i;
            }
        }
        return individuals[maxFit2];
    }

    public int getLeastFittestIndex() {
        int minFitVal = Integer.MAX_VALUE;
        int minFitIndex = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (minFitVal >= individuals[i].fitness) {
                minFitVal = individuals[i].fitness;
                minFitIndex = i;
            }
        }
        return minFitIndex;
    }

    public void calculateFitness() {

        for (Individual individual : individuals) {
            individual.calcFitness();
        }
        getFittest();
    }  
}