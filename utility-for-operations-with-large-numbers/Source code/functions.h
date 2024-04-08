/**
* @file functions.h
* @brief This file will contain the function prototypes
*
* @author Bușe-Dragomir Alexandru
*
* @date 07/06/2017
*/

typedef int numar_mare[1000];

void citire_numar_mare (char auxiliar[1000], numar_mare valoare);

void afisare_numar_mare (numar_mare valoare);

void initializare_cu_mic (numar_mare valoare_mare, int numar_mic);

void initializare_cu_mare (numar_mare valoare_initiala, numar_mare valoare_noua);

int comparare_numere_mari (numar_mare numar1, numar_mare numar2);

void adunare_numere_mari (numar_mare numar1, numar_mare numar2, numar_mare suma);

void scadere_numere_mari (numar_mare numar1, numar_mare numar2, numar_mare diferenta);

void inmultire_scalar (numar_mare valoare, int scalar, numar_mare produs);

void inmultire_numar_mare (numar_mare numar1, numar_mare numar2, numar_mare produs);

int impartire_la_scalar (numar_mare cat, int scalar);

void radical_numar_mare (numar_mare valoare, numar_mare radical);
