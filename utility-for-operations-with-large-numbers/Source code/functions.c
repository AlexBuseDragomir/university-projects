/**
* @file functions.c
* @brief This file will contain all functions used in order to work with large numbers.
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


/**
* This function will get a string and save it as a large_number data type.
* @author Bușe-Dragomir Alexandru
* @param auxiliar The number represented as a string
* @param valoare The value of the number represented as a vector of integers
* @date 07/06/2017
*/
void citire_numar_mare(char auxiliar[1000], numar_mare valoare) {
    int i;
    int numar_cifre = strlen(auxiliar);

    for (i = 0; i <= numar_cifre; i++) {
        valoare[i] = 0;
    }

    valoare[0] = numar_cifre;

    for (i = 1; i <= numar_cifre; i++) {
        valoare[i] = (int) (auxiliar[numar_cifre - i]) - 48;
    }
}


/**
* This function will get a large number and print its value.
* @author Bușe-Dragomir Alexandru
* @param valoare The value of the number that will be printed
* @date 07/06/2017
*/
void afisare_numar_mare(numar_mare valoare) {
    int i;
    int lungime_numar = valoare[0];

    for (i = lungime_numar; i >= 1; i--) {
        printf("%d", valoare[i]);
    }
}


/**
* This function will replace the value of the given large number with a scalar value.
* @author Bușe-Dragomir Alexandru
* @param valoare_mare The large number that will be replaced
* @param numar_mic The new integer value of the large number
* @date 07/06/2017
*/
void initializare_cu_mic(numar_mare valoare_mare, int numar_mic) {
    int i = 1;

    if (numar_mic == 0) {
        valoare_mare[0] = 1;
        valoare_mare[1] = 0;
        return;
    } else {
        while (numar_mic != 0) {
            valoare_mare[i] = numar_mic % 10;
            i++;
            numar_mic = numar_mic / 10;
        }

        i--;
    }

    valoare_mare[0] = i;
}


/**
* This function will replace the value of the given large number with the value of a secondary large number.
* @author Bușe-Dragomir Alexandru
* @param valoare_initiala The large number that will be replaced
* @param valoare_noua The new value of the large number
* @date 07/06/2017
*/
void initializare_cu_mare(numar_mare valoare_initiala, numar_mare valoare_noua) {
    int i;
    int lungime_numar_nou;

    lungime_numar_nou = valoare_noua[0];
    valoare_initiala[0] = lungime_numar_nou;

    for (i = 1; i <= lungime_numar_nou; i++) {
        valoare_initiala[i] = valoare_noua[i];
    }
}


/**
* This function will compare the two large numbers.
* @author Bușe-Dragomir Alexandru
* @param numar1 The first large number
* @param numar2 The second large number
* @date 07/06/2017
*/
int comparare_numere_mari(numar_mare numar1, numar_mare numar2) {
    int i;
    int lungime_numar1 = numar1[0];
    int lungime_numar2 = numar2[0];

    if (lungime_numar1 > lungime_numar2) {
        return 1;
    } else {
        if (lungime_numar1 < lungime_numar2) {
            return 2;
        } else {
            for (i = lungime_numar1; i >= 1; i--) {
                if (numar1[i] > numar2[i]) {
                    return 1;
                } else {
                    if (numar1[i] < numar2[i]) {
                        return 2;
                    }
                }
            }

            return 0;
        }
    }
}


/**
* This function will calculate the sum of two large numbers.
* @author Bușe-Dragomir Alexandru
* @param numar1 The first large number
* @param numar2 The second large number
* @param suma The resulting sum of the two large numbers
* @date 07/06/2017
*/
void adunare_numere_mari(numar_mare numar1, numar_mare numar2, numar_mare suma) {
    int i;
    int lungime_numar1 = numar1[0];
    int lungime_numar2 = numar2[0];
    int lungime_mare;
    int lungime_mica;
    int transport = 0;

    if (lungime_numar1 > lungime_numar2) {
        lungime_mare = lungime_numar1;
        lungime_mica = lungime_numar2;

        for (i = lungime_mica + 1; i <= lungime_mare; i++) {
            numar2[i] = 0;
        }
    } else {
        if (lungime_numar1 < lungime_numar2) {
            lungime_mare = lungime_numar2;
            lungime_mica = lungime_numar1;

            for (i = lungime_mica + 1; i <= lungime_mare; i++) {
                numar1[i] = 0;
            }
        } else {
            lungime_mare = lungime_numar1;
            lungime_mica = lungime_numar2;
        }
    }

    suma[0] = lungime_mare;

    for (i = 1; i <= lungime_mare; i++) {
        suma[i] = (numar1[i] + numar2[i] + transport) % 10;
        transport = (numar1[i] + numar2[i] + transport) / 10;
    }

    if (transport != 0) {
        suma[i] = transport;
        suma[0]++;
    }
}


/**
* This function will calculate the difference of two large numbers.
* @author Bușe-Dragomir Alexandru
* @param numar1 The first large number
* @param numar2 The second large number
* @param diferenta The resulting difference of the two large numbers
* @date 07/06/2017
*/
void scadere_numere_mari(numar_mare numar1, numar_mare numar2, numar_mare diferenta) {
    int i;
    int lungime_numar1 = numar1[0];
    int lungime_numar2 = numar2[0];
    int lungime_mare;
    int lungime_mica;
    int index_scadere = 0;
    int numar_cifre = 0;

    diferenta[0] = -1;

    if (comparare_numere_mari(numar1, numar2) == 2) {
        printf("\nEROARE!\n");
        printf("Inserati numerele astfel incat descazutul sa fie mai mare sau egal cu scazatorul\n\n");
        return;
    } else {
        if (comparare_numere_mari(numar1, numar2) == 0) {
            printf("\nNumerele introduse sunt egale\n");
            printf("Diferenta este %d\n\n", 0);
            return;
        } else {
            if (comparare_numere_mari(numar1, numar2) == 1) {
                lungime_mare = lungime_numar1;
                lungime_mica = lungime_numar2;

                for (i = lungime_mica + 1; i <= lungime_mare + 1; i++) {
                    numar2[i] = 0;
                }

                for (i = 1; i <= lungime_mare; i++) {
                    if (numar1[i] < numar2[i]) {
                        if (index_scadere == 0) {
                            diferenta[i] = (numar1[i] + 10) - numar2[i];
                            index_scadere = 1;
                        } else {
                            if (index_scadere == 1) {
                                diferenta[i] = (numar1[i] - 1 + 10) - numar2[i];
                                index_scadere = 1;
                            }
                        }

                    }

                    if (numar1[i] == numar2[i]) {
                        if (index_scadere == 1) {
                            diferenta[i] = 9;
                            index_scadere = 1;
                        } else {
                            if (index_scadere == 0) {
                                diferenta[i] = 0;
                                index_scadere = 0;
                            }
                        }
                    }

                    if (numar1[i] > numar2[i]) {
                        if (index_scadere == 1) {
                            diferenta[i] = (numar1[i] - 1) - numar2[i];
                            index_scadere = 0;
                        } else {
                            diferenta[i] = numar1[i] - numar2[i];
                            index_scadere = 0;
                        }
                    }
                }

                for (i = lungime_mare; i >= 1; i--) {
                    if (diferenta[i] == 0) {
                        numar_cifre++;
                    } else {
                        numar_cifre = lungime_mare - numar_cifre;
                        diferenta[0] = numar_cifre;
                        return;
                    }
                }
            }
        }
    }
}


/**
* This function will calculate the product between a large number and a scalar value.
* @author Bușe-Dragomir Alexandru
* @param valoare The value of the large number
* @param scalar The value of the scalar
* @param produs The resulting product of the numbers
* @date 07/06/2017
*/
void inmultire_scalar(numar_mare valoare, int scalar, numar_mare produs) {
    int i;
    int count = 0;
    int transport = 0;
    int scalar_value;
    int lungime_numar_mare = valoare[0];
    int auxiliar[10000] = {0};
    int verificare = 0;

    while (scalar != 0) {
        scalar_value = scalar % 10;

        for (i = 1; i <= valoare[0]; i++) {
            auxiliar[i + count] = auxiliar[i + count] + valoare[i] * scalar_value;
        }

        count++;
        scalar = scalar / 10;
    }

    for (i = 1; i <= lungime_numar_mare + count; i++) {
        produs[i] = (auxiliar[i] + transport) % 10;
        transport = (auxiliar[i] + transport) / 10;
    }

    produs[0] = lungime_numar_mare + count;

    if (produs[produs[0]] == 0) {
        produs[0]--;
    }

    for (i = 1; i <= produs[0]; i++) {
        if (produs[i] != 0) {
            verificare = 1;
        }
    }

    if (verificare == 0) {
        produs[0] = 1;
        produs[1] = 0;
    }
}


/**
* This function will calculate the product of two large numbers.
* @author Bușe-Dragomir Alexandru
* @param numar1 The first large number
* @param numar2 The second large number
* @param produs The resulting product of the two large numbers
* @date 07/06/2017
*/
void inmultire_numar_mare(numar_mare numar1, numar_mare numar2, numar_mare produs) {
    int i;
    int j;
    int count = 0;
    int current_value;
    int transport = 0;
    int lungime1 = numar1[0];
    int lungime2 = numar2[0];
    int auxiliar[100000] = {0};
    int verificare = 0;

    for (j = 1; j <= lungime2; j++) {
        current_value = numar2[j];

        for (i = 1; i <= lungime1; i++) {
            auxiliar[i + count] = auxiliar[i + count] + numar1[i] * current_value;
        }

        count++;
    }

    for (i = 1; i <= lungime1 + count; i++) {
        produs[i] = (auxiliar[i] + transport) % 10;
        transport = (auxiliar[i] + transport) / 10;
    }

    produs[0] = lungime1 + count;

    if (produs[produs[0]] == 0) {
        produs[0]--;
    }

    for (i = 1; i <= produs[0]; i++) {
        if (produs[i] != 0) {
            verificare = 1;
        }
    }

    if (verificare == 0) {
        produs[0] = 1;
        produs[1] = 0;
    }
}


/**
* This function will divide a large number by a scalar value and return the remainder.
* The quotient will be saved in the original large number ("cat").
* @author Bușe-Dragomir Alexandru
* @param cat The large dividend (also the parameter where the resulting quotient will be saved)
* @param scalar The scalar value which we will divide by (the divisor)
* @date 07/06/2017
*/
int impartire_la_scalar(numar_mare cat, int scalar) {
    int i;
    int rest = 0;
    int verificare = 0;
    int lungime_numar = cat[0];

    if (scalar == 0) {
        return -1;
    } else {
        for (i = lungime_numar; i >= 1; i--) {
            rest = 10 * rest + cat[i];
            cat[i] = rest / scalar;
            rest = rest % scalar;
        }

        for (i = lungime_numar; i >= 1; i--) {
            if (cat[i] != 0) {
                verificare = 1;
            } else {
                if (cat[i] == 0 && verificare == 0) {
                    cat[0]--;
                }
            }
        }

        if (verificare == 0) {
            cat[0] = 1;
            cat[1] = 0;
        }

        return rest;
    }
}


/**
* This function will calculate the floor of the square root of a large number.
* @author Bușe-Dragomir Alexandru
* @param valoare The large number value
* @param radical The floor of the square root of the given number 
* @date 07/06/2017
*/
void radical_numar_mare(numar_mare valoare, numar_mare radical) {
    int i;
    int lungime_numar = valoare[0];

    numar_mare inceput;
    numar_mare sfarsit;
    numar_mare mijloc;
    numar_mare patrat;
    numar_mare suma;
    numar_mare auxiliar;

    for (i = 0; i <= lungime_numar; i++) {
        inceput[i] = 0;
        sfarsit[i] = 0;
        mijloc[i] = 0;
        patrat[i] = 0;
        suma[i] = 0;
        auxiliar[i] = 0;
    }

    if (valoare[0] == 1 && valoare[1] == 0) {
        radical[0] = 1;
        radical[1] = 0;
        return;
    } else {
        if (valoare[0] == 1 && valoare[1] == 1) {
            radical[0] = 1;
            radical[1] = 1;
            return;
        } else {
            initializare_cu_mic(inceput, 1);
            initializare_cu_mare(sfarsit, valoare);

            while (comparare_numere_mari(inceput, sfarsit) == 0 || comparare_numere_mari(inceput, sfarsit) == 2) {
                adunare_numere_mari(inceput, sfarsit, mijloc);
                impartire_la_scalar(mijloc, 2);

                inmultire_numar_mare(mijloc, mijloc, patrat);

                if (comparare_numere_mari(patrat, valoare) == 0) {
                    initializare_cu_mare(radical, mijloc);
                    return;
                } else {
                    if (comparare_numere_mari(patrat, valoare) == 2) {
                        initializare_cu_mic(auxiliar, 1);
                        adunare_numere_mari(mijloc, auxiliar, inceput);
                        initializare_cu_mare(radical, mijloc);
                    } else {
                        if (comparare_numere_mari(patrat, valoare) == 1) {
                            initializare_cu_mic(auxiliar, 1);
                            scadere_numere_mari(mijloc, auxiliar, sfarsit);
                        }
                    }
                }
            }
        }
    }
}












