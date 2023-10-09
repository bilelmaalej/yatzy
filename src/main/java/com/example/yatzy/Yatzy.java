package com.example.yatzy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Yatzy {

    /**
     * Représente les chiffres obtenus
     */
    private final int[] dice;

    /**
     * Constructeur avec les 5 dés
     *
     * @param d1 valeur dé 1
     * @param d2 valeur dé 2
     * @param d3 valeur dé 3
     * @param d4 valeur dé 4
     * @param d5 valeur dé 5
     */
    public Yatzy(int d1, int d2, int d3, int d4, int d5) {
        this.dice = new int[]{d1, d2, d3, d4, d5};
        Arrays.sort(this.dice);
    }

    /**
     * La somme de tous les dés
     *
     * @return
     */
    public int chance() {
        return Arrays.stream(dice).sum();
    }

    /**
     * Tous les dés ont le même numéro
     *
     * @return 50 si Tous les dés ont le même numéro
     * 0 sinon
     */
    public int yatzy() {
        return Arrays.stream(dice).distinct().count() <= 1 ? 50 : 0;
    }

    /**
     * Nombre d'occurence des "uns"
     *
     * @return Resultats des uns (nb occurence * 1)
     */
    public int ones() {
        return numberOfOccurrences(1);
    }

    /**
     * Resultats si on choisit pour les "deux"
     *
     * @return (nb occurence * 2)
     */
    public int twos() {
        return numberOfOccurrences(2) * 2;
    }

    /**
     * Resultats si on choisit pour les "trois"
     *
     * @return (nb occurence * 3)
     */
    public int threes() {
        return numberOfOccurrences(3) * 3;
    }

    /**
     * Resultats si on choisit pour les "quatres"
     *
     * @return (nb occurence * 4)
     */
    public int fours() {
        return numberOfOccurrences(4) * 4;
    }

    /**
     * Resultats si on choisit pour les "cinqs"
     *
     * @return (nb occurence * 5)
     */
    public int fives() {
        return numberOfOccurrences(5) * 5;
    }

    /**
     * Resultats si on choisit pour les "six"
     *
     * @return (nb occurence * 6)
     */
    public int sixes() {
        return numberOfOccurrences(6) * 6;
    }

    /**
     * Chercher les paires pour le nombre n
     *
     * @return La somme des paires les plus elevés
     */
    public int scorePair() {
        return findNOfAKind(2) * 2;
    }

    /**
     * Détecter si'il existe deux paires
     *
     * @return
     */
    public int twoPair() {
        // Utiliser un stream pour compter les occurrences de chaque numéro dans le tableau
        Map<Integer, Long> occurrences = Arrays.stream(dice).boxed().collect(Collectors.groupingBy(num -> num, Collectors.counting()));
        // Parcourir les entrées du map pour trouver les paires
        int somme = 0;
        for (Map.Entry<Integer, Long> entry : occurrences.entrySet()) {
            long count = entry.getValue();
            if (count >= 2) {
                // Une paire trouvée, ajouter le double du numéro à la somme
                somme += entry.getKey() * 2;
            }
        }
        // Si on a trouvé deux paires, renvoyer la somme, sinon renvoyer 0
        return somme >= 4 ? somme : 0;
    }

    /**
     * Chercher s'il y a un carrée (4 fois le meme nombre)
     *
     * @return le joueur marque la somme de ces dés
     */
    public int fourOfAKind() {
        return findNOfAKind(4) * 4;
    }

    /**
     * Chercher s'il y a un brelan (3 fois le meme nombre)
     *
     * @return
     */
    public int threeOfAKind() {
        return findNOfAKind(3) * 3;
    }

    /**
     * Petite ligne droite : Lorsqu'il est placé sur une « petite quinte », si le dé indique1,2,3,4,5,
     *
     * @return 15 (la somme de tous les dés)
     * 0 sinon
     */
    public int smallStraight() {
        return Arrays.equals(dice, new int[]{1, 2, 3, 4, 5}) ? 15 : 0;
    }

    /**
     * Grande ligne droite : Lorsqu'il est placé sur une « grande ligne », si le dé indique 2,3,4,5,6
     *
     * @return 20 (la somme de tous les dés)
     * 0 sinon
     */
    public int largeStraight() {
        return Arrays.equals(dice, new int[]{2, 3, 4, 5, 6}) ? 20 : 0;
    }

    /**
     * CHercher si c'est fullHouse : les dés contiennet un brelan et un paire
     *
     * @return la somme des nombre si fullhouse
     * 0 sinon
     */
    public int fullHouse() {
        // Création d'un nouveau tableau (taille 6 car on peut avoir 6 valeurs) pour stocker les nombres d'occurences
        // L'indice du nouveau tableau c'est la valeur à chercher son occurence (-1 car l'indice du tableau commence par 0)
        int[] counts = new int[6];
        boolean paire = false;
        boolean brelan = false;
        Arrays.stream(dice).forEach(d -> counts[d - 1]++);

        for (int count : counts) {
            if (count == 2) {
                paire = true;
            }
            if (count == 3) {
                brelan = true;
            }
        }

        return paire && brelan ? chance() : 0;
    }

    /**
     * Compte le nombre d'occurrence d'un nombre donnée
     *
     * @param value
     * @return
     */
    private int numberOfOccurrences(int value) {
        return (int) Arrays.stream(dice).filter(d -> d == value).count();
    }

    /**
     * CHercher le nombre d'occurence n d'un meme nombre
     *
     * @param n le nombre d'occurence à chercher
     * @return Le nombre ayant l'occurence demandée
     * 0 sinon
     */
    private int findNOfAKind(int n) {
        // Création d'un nouveau tableau (taille 6 car on peut avoir 6 valeurs) pour stocker les nombres d'occurences
        // L'indice du nouveau tableau c'est la valeur à chercher son occurence (-1 car l'indice du tableau commence par 0)
        int[] counts = new int[6];
        for (int d : dice) {
            counts[d - 1]++;
        }
        for (int i = counts.length - 1; i >= 0; i--) {
            if (counts[i] >= n) {
                return (i + 1);
            }
        }
        return 0;
    }
}
