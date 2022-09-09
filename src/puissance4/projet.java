package puissance4;
//Alexis et Mathéo Groupe 3
import java.util.Scanner;

import java.util.concurrent.ThreadLocalRandom;


public class projet {
    public static int[][] grille;

    public static int entierAleatoire(int a, int b) {
        //Retourne un entier aléatoire entre a (inclus) et b (inclus)
        return ThreadLocalRandom.current().nextInt(a, b + 1);
    }

    public static void initialiseGrille() {
        grille = new int[6][7];
    }

    public static void afficheGrille(int[][] g) {
        char symbole = '0';
        for (int i = 0; i < g.length; ++i) {
            System.out.print(" | ");
            for (int j = 0; j < g[0].length; ++j) {
                if (g[i][j] == 1)
                    symbole = 'X';
                else if (g[i][j] == 2)
                    symbole = 'O';
                else if (g[i][j] == 0)
                    symbole = ' ';

                System.out.print(symbole + " | ");
            }
            System.out.println();
        }
        for (int i = 0; i < g[0].length; ++i) {
            System.out.print("   " + i);
        }
        System.out.println();
    }

    /***
     *  foction qui modifie le tableau pour jouer le coup du jouer
     * @param le joueur qui pose
     * @param colonne
     * @param la grille ou poser
     *
     */
    public static void jouer(int joueur, int colonne, int[][] g) {
        int i = 0;
        int ranger = g.length - 1;
        boolean fin = false;
        while ((i < g.length) && !fin) {
            if (g[ranger][colonne] == 0) {
                g[ranger][colonne] = joueur;
                fin = true;
            }
            --ranger;
            ++i;


        }
    }

    /***
     *
     * @param joueur
     * @param x
     * @param y
     * @param grille
     * @return si le jouer a gagnier a un endroit precis horizontalement
     */
    public static boolean aGagneHor(int joueur, int x, int y, int[][] g) {
        int i = 0;
        int cpt = 0;
        boolean gagner = false;
        while ((i < 4) && ((i + x) != (g[0].length - 1)) && !gagner) {
            if (g[y][x + i] == joueur) {
                ++cpt;
                if (cpt == 4) {
                    gagner = true;


                }
            }
            ++i;
        }
        return gagner;
    }

    /***
     *
     * @param joueur
     * @param x
     * @param y
     * @param grille
     * @return comme la fonction ci dessus mais verifie verticalement
     */
    public static boolean aGagneVer(int joueur, int x, int y, int[][] g) {
        int cpt = 0;
        int i = 0;
        boolean gagner = false;
        while ((i < 4) && !gagner && ((y - i) >= 0)) {
            if (g[y - i][x] == joueur) {
                ++cpt;
                if (cpt == 4)
                    gagner = true;
            }
            ++i;

        }
        return gagner;
    }

    /***
     *
     * @param joueur
     * @param x
     * @param y
     * @param grille
     * @return meme chose mais pour diagonale vers le haut
     */

    public static boolean aGagneDiagMont(int joueur, int x, int y, int[][] g) {
        int cpt = 0;
        int i = 0;
        boolean gagner = false;
        while ((i < 4) && !gagner && ((y - i) >= 0) && ((x + i) != g[0].length)) {
            if (g[y - i][x + i] == joueur) {
                cpt++;

                if (cpt == 4)
                    gagner = true;
            }
            ++i;

        }
        return gagner;
    }

    /***
     *
     * @param joueur
     * @param x
     * @param y
     * @param grille
     * @return meme chose mais pour la diagonale descendent
     */
    public static boolean aGagneDiagDesc(int joueur, int x, int y, int[][] g) {
        int cpt = 0;
        int i = 0;
        boolean gagner = false;
        while ((i < 4) && !gagner && ((y + i) != g.length) && ((x + i) != g[0].length)) {
            if (g[y + i][x + i] == joueur) {
                ++cpt;
                if (cpt == 4)
                    gagner = true;

            }
            ++i;

        }
        return gagner;
    }

    /**
     *
     * @param joueur
     * @param grille
     * @return un boolean vrai si joueur  agagner
     */
    public static boolean aGagne(int joueur, int[][] g) {
        boolean gagner = false;
        for (int i = 0; i < g.length; ++i) {
            for (int j = 0; j < g[0].length; ++j) {
                if (aGagneDiagDesc(joueur, j, i, g) || aGagneDiagMont(joueur, j, i, g) || aGagneVer(joueur, j, i, g) || aGagneHor(joueur, j, i, g))
                    gagner = true;
            }
        }
        return gagner;

    }

    /***
     *
     * @return verifie si il y a un match nul
     */

    public static boolean matchNul() {
        boolean nul = true;
        int i = 0;
        if (aGagne(1, grille) || aGagne(2, grille))
            nul = false;
        while (i < grille[0].length && nul) {

            if (grille[0][i] == 0)
                nul = false;

        }

        return nul;
    }

    /***
     * fonction qui permet de faire fonctionner donne bonne ordre toute les fonction pour que le jeu fonctionne
     */

    public static void jeu() {
        initialiseGrille();
        afficheGrille(grille);
        Scanner sc = new Scanner(System.in);
        int nbJoueur = 0;
        while (nbJoueur == 0 || nbJoueur < 0 || nbJoueur > 2) {
            System.out.println("nombre de joueur :");
            nbJoueur = sc.nextInt();
        }
        if (nbJoueur == 2) {
            while (!aGagne(1, grille) && !aGagne(2, grille) && !matchNul()) {
                System.out.println("Quel coup pour le joueur 1 ?");

                int xJoueur1 = sc.nextInt();
                while (grille[0][xJoueur1] != 0) { // redement de au jouer son coup tant qu'il sort de la grille
                    System.out.println("colone pleine");

                    xJoueur1 = sc.nextInt();
                }
                jouer(1, xJoueur1, grille);
                afficheGrille(grille);
                if (aGagne(1, grille)) {
                    System.out.println("Bravo Joueur 1 à gagné");
                    break;
                }

                System.out.println("Quel coup pour le joueur 2 ?");
                int xJoueur2 = sc.nextInt();
                while (grille[0][xJoueur2] != 0){// demande tant que le coup depasse la grille
                    System.out.println("colone pleine");
                    xJoueur2 = sc.nextInt();}
                jouer(2, xJoueur2, grille);
                afficheGrille(grille);
                if (aGagne(2, grille)) {
                    System.out.println("Bravo Joueur 2 à gagné");
                    break;
                }

                if (matchNul()) {
                    System.out.println("match nul");
                }
            }
        } else {
            while (!aGagne(1, grille) && !aGagne(2, grille) && !matchNul()||true) {
                System.out.println("Quel coup pour le joueur 1 ?");

                int xJoueur1 = sc.nextInt();
                while (grille[0][xJoueur1] != 0) {
                	System.out.println("colone pleine");
                	xJoueur1 = sc.nextInt();}
                jouer(1, xJoueur1, grille);
                afficheGrille(grille);
                if (aGagne(1, grille)) {
                    System.out.println("Bravo Joueur 1 à gagné");
                    break;
                }

                System.out.println("");
                int coup = joueCoupRandom3();

                jouer(2, coup, grille);
                afficheGrille(grille);
                if (aGagne(2, grille)) {
                    System.out.println("Perdu");
                    break;
                }

                if (matchNul()) {
                    System.out.println("match nul");
                    break;
                }
            }
        }
    }

    /***
     *
     * @return un coup a jouer au hasard qui respect le dimenssion du tableau
     */
    public static int joueCoupRandom() {
        int ress = entierAleatoire(0, 6);
        boolean caseVide = false;
        while (!caseVide) {
            if (grille[0][ress] == 0)
                caseVide = true;
            else
                ress = entierAleatoire(0, 6);

        }
        return ress;
    }

    /***
     *
     * @param  grille a copier
     * @param  new grille
     */
    public static void copieGrille(int[][] g, int[][] g2) {
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g.length; j++) {
                g[i][j] = g2[i][j];
            }
        }

    }

    /***
     *
     * @return revoie un coup a jouer inteligent pour contrer un coup ou gagner en 1 coup ou pas faire gagner le joueru 1
     */
    public static int joueCoupRandom3() {
    //l'ia copie la grille pour poser sur une nouvelle grille toute les possibiliter et revoie la meilleur solution
        int[][] cloned = new int[6][7];
        for (int i = 0; i < 7; i++) {
            copieGrille(cloned, grille);

            jouer(2, i, cloned);
            if (aGagne(2, cloned)) {
                return i;
            }
        }

        for (int i = 0; i < 7; i++) {
            copieGrille(cloned, grille);
            if (cloned[0][i] == 0)
                jouer(1, i, cloned);
            if (aGagne(1, cloned)) {
                return i;
            }
        }

        int res;
        int i = 0;//indice pour pas que la boucle tourne à l'infini
        do {
            res = joueCoupRandom();
            copieGrille(cloned, grille);
            jouer(2, res, cloned);
            if (cloned[0][res] == 0) {
                jouer(1, res, cloned);
            
            }
            ++i;
        } while (aGagne(1, cloned)&&i<10);
        if (i==9)
        	res =joueCoupRandom();
        return res;


    }




    public static void main(String[] args) {

        jeu();


    }
}
