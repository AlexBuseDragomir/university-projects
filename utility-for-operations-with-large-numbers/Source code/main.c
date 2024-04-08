/**
* @file main.c
* @brief This file will contain the main block of the program
*
* @author Bușe-Dragomir Alexandru
*
* @date 07/06/2017
*/


# include <stdio.h>
# include <stdlib.h>
# include <math.h>
# include <string.h>
# include "functions.h"

typedef int numar_mare[1000];

numar_mare X;
numar_mare Y;
numar_mare suma;
numar_mare diferenta;
numar_mare produs;
numar_mare cat;
numar_mare radical;

char numar_citit[1000];


int main ()
{

	int i;
	int scalar_dividere;
	int numar_mic;
	int scalar_produs;
	int rest = 0;
	int alegere_consola;
	int verificare = 0;

	char caracter;


        printf ("\n\n\n\n\n\n\n");
        printf ("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        printf ("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        printf ("                          OPERATII CU NUMERE MARI\n\n");
        printf ("Selectati una din optiunile de mai jos tastand numarul acesteia in consola : \n\n");
        printf ("0. Ce sunt numerele mari? \n");
        printf ("1. Compararea a doua numere mari \n");
        printf ("2. Adunarea a doua numere mari \n");
        printf ("3. Scaderea a doua numere mari \n");
        printf ("4. Inmultirea unui numar mare cu un scalar \n");
        printf ("5. Inmultirea a doua numere mari \n");
        printf ("6. Impartirea unui numar mare la un scalar \n");
        printf ("7. Extragerea radicalului dintr-un numar mare \n\n\n");
        printf ("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        printf ("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        printf ("Inserati aici alegerea dumneavoastra : ");
        scanf ("%d", &alegere_consola);
        printf ("\n\n");


        if (alegere_consola == 0)
        {
                printf ("\n\n\n");
                printf ("                          Numerele mari\n\n");
                printf ("Numere intregi pozitive de dimensiuni foarte mari.\n");
                printf ("Lungimea lor va fi limitata la 999 de cifre in acest algoritm.\n\n");
                printf ("Observatie : Se va considera valid ca numar mare orice numar cu dimensiunea\n");
                printf ("intre 1 si 999 de cifre.\n\n");
                printf ("!ATENTIE!\nIn cazul unor operatii precum inmultirea, trebuie luata in considerare");
                printf ("\ndimensiunea finala a rezultatului.\n");
                printf ("Se recomanda introducerea unor numere de 1-500 cifre pentru a fi evitate\n");
                printf ("eventualele erori de calcul.\n");
                printf ("Daca se doreste lucrul cu numere mai mari, acest lucru se poate face prin\n");
                printf ("marirea lungimii vectorului din spatele tipului de date numere_mari.\n\n\n\n");
                verificare = 1;
        }


        if (alegere_consola == 1)
        {
                printf ("\n\n\n");
                printf ("                     Compararea numerelor mari\n\n");
                printf ("Inserati de la tastatura un numar mare\n");
                printf ("--->  ");
                scanf ("%s", numar_citit);
                citire_numar_mare (numar_citit, X);
                printf ("Numarul citit a fost salvat!");
                printf ("\n\n\n");

                printf ("Inserati de la tastatura inca un numar mare\n");
                printf ("--->  ");
                scanf ("%s", numar_citit);
                citire_numar_mare (numar_citit, Y);
                printf ("Numarul citit a fost salvat!");
                printf ("\n\n\n");

                        if (comparare_numere_mari(X, Y) == 0)
                {
                        printf ("Cele doua numere sunt egale!\n\n");
                }

                else
                {
                        if (comparare_numere_mari(X, Y) == 1)
                        {
                                printf ("Primul numar este mai mare decat al doilea!\n\n");
                        }

                        else
                        {
                                if (comparare_numere_mari (X, Y) == 2)
                                {
                                        printf ("Al doilea numar este mai mare decat primul!\n\n");
                                }
                        }
                }

                verificare = 1;
        }


        if (alegere_consola == 2)
        {
                printf ("\n\n\n");
                printf ("                        Adunarea numerelor mari\n\n");
                printf ("Inserati de la tastatura un numar mare\n");
                printf ("--->  ");
                scanf ("%s", numar_citit);
                citire_numar_mare (numar_citit, X);
                printf ("Numarul citit a fost salvat!");
                printf ("\n\n\n");

                printf ("Inserati de la tastatura inca un numar mare\n");
                printf ("--->  ");
                scanf ("%s", numar_citit);
                citire_numar_mare (numar_citit, Y);
                printf ("Numarul citit a fost salvat!");
                printf ("\n\n\n");

                printf ("Suma celor doua numere este :\n");
                adunare_numere_mari (X, Y, suma);
                printf ("--->  ");
                afisare_numar_mare (suma);
                printf ("\n\n\n");

                verificare = 1;
        }


        if (alegere_consola == 3)
        {
                printf ("\n\n\n");
                printf ("                        Scaderea numerelor mari\n\n");
                printf ("Inserati de la tastatura un numar mare\n");
                printf ("--->  ");
                scanf ("%s", numar_citit);
                citire_numar_mare (numar_citit, X);
                printf ("Numarul citit a fost salvat!");
                printf ("\n\n\n");

                printf ("Inserati de la tastatura inca un numar mare\n");
                printf ("--->  ");
                scanf ("%s", numar_citit);
                citire_numar_mare (numar_citit, Y);
                printf ("Numarul citit a fost salvat!");
                printf ("\n\n\n");

                scadere_numere_mari (X, Y, diferenta);
                printf ("\n");

                if (diferenta[0] != -1)
                {
                        printf ("Diferenta celor doua numere este :\n");
                        printf ("--->  ");
                        afisare_numar_mare (diferenta);
                        printf ("\n\n\n");
                }

                verificare = 1;
        }


        if (alegere_consola == 4)
        {
                printf ("\n\n\n");
                printf ("                    Inmultirea unui numar mare cu un scalar\n\n");
                printf ("Inserati de la tastatura un numar mare\n");
                printf ("--->  ");
                scanf ("%s", numar_citit);
                citire_numar_mare (numar_citit, X);
                printf ("Numarul citit a fost salvat!");
                printf ("\n\n\n");

                printf ("Inserati acum un scalar\n");
                printf ("--->  ");
                scanf ("%d", &scalar_produs);
                printf ("\nProdusul celor doua numere este : \n");
                printf ("--->  ");
                inmultire_scalar (X, scalar_produs, produs);
                afisare_numar_mare (produs);
                printf ("\n\n\n");

                verificare = 1;
        }


        if (alegere_consola == 5)
        {
                printf ("\n\n\n");
                printf ("                      Inmultirea a doua numere mari\n\n");
                printf ("Inserati de la tastatura un numar mare\n");
                printf ("--->  ");
                scanf ("%s", numar_citit);
                citire_numar_mare (numar_citit, X);
                printf ("Numarul citit a fost salvat!");
                printf ("\n\n\n");

                printf ("Inserati de la tastatura inca un numar mare\n");
                printf ("--->  ");
                scanf ("%s", numar_citit);
                citire_numar_mare (numar_citit, Y);
                printf ("Numarul citit a fost salvat!");
                printf ("\n\n\n");

                printf ("\nProdusul celor doua numere este :\n");
                printf ("--->  ");
                inmultire_numar_mare (X, Y, produs);
                afisare_numar_mare (produs);
                printf ("\n\n\n");


                verificare = 1;
        }


        if (alegere_consola == 6)
        {
                printf ("\n\n\n");
                printf ("                   Impartirea unui numar mare la un scalar\n\n");
                printf ("Inserati de la tastatura un numar mare\n");
                printf ("--->  ");
                scanf ("%s", numar_citit);
                citire_numar_mare (numar_citit, X);
                printf ("Numarul citit a fost salvat!");
                printf ("\n\n\n");

                printf ("Inserati de la tastatura un scalar\n");
                printf ("--->  ");
                scanf ("%d", &scalar_dividere);
                rest = impartire_la_scalar (X, scalar_dividere);

                if (rest == -1)
                {
                        printf ("\n\n                     Eroare!\n");
                        printf ("         !S-a efectuat impartirea la 0!");
                        printf ("\n\n\n");
                }

                else
                {
                        printf ("\nCatul impartirii este  ");
                        printf ("--->  ");
                        afisare_numar_mare (X);
                        printf ("\nRestul impartirii este  --->  %d", rest);
                        printf ("\n\n\n");
                }

                verificare = 1;
        }


        if (alegere_consola == 7)
        {
                printf ("\n\n\n");
                printf ("                   Extragerea radicalului dintr-un numar mare\n\n");
                printf ("Inserati de la tastatura un numar mare\n");
                printf ("--->  ");
                scanf ("%s", numar_citit);
                citire_numar_mare (numar_citit, X);
                printf ("Numarul citit a fost salvat!");
                printf ("\n\n\n");

                printf ("Partea intreaga a radicalului numarului introdus este:\n");
                printf ("--->  ");
                radical_numar_mare (X, radical);
                afisare_numar_mare (radical);
                printf ("\n\n\n");

                verificare = 1;
        }


        if (verificare == 0)
        {
                printf ("\n");
                printf ("          ---> Numarul introdus nu se regaseste in lista de mai sus.\n");
                printf ("          ---> Inserati una din valorile 0,1,2,3,4,5,6,7.");
                printf ("\n\n\n");
        }

        else
        {
                printf ("\n\n\n                                  **********\n");
                printf ("                             Press any key for exit\n");
                printf ("                                **************");
                printf ("\n\n\n\n");
        }

        return 0;
}







