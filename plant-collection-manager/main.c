# include <stdio.h>
# include <stdlib.h>
# include <math.h>
# include <string.h>
# include <conio.h>
#include <ctype.h>


int string_compare(char string1[], char string2[]);

void clear_screen();

void add_record(char new_record[]);

void show_records(int *number_of_records);

void delete_record(char record_name[]);

void show_genera(char genera_name[]);

void show_ordered_records();

void select_hardy_plants(int celsius_temperature, char hardy[500][500], char tender[500][500]);
///celsius_temperature -> the minimum temperature for a tender plant
///hardy and tender -> arrays that will save both categories of plants


void clear_screen() {
    printf("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    printf("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
}


int string_compare(char string1[500], char string2[500]) {
    int i;
    int length1 = strlen(string1);
    int length2 = strlen(string2);
    int lengthMin = 0; ///the shortest string

    char string1_save[500]; ///in order to preserv the string upper case
    char string2_save[500];

    strcpy(string1_save, string1);
    strcpy(string2_save, string2);

    if (length1 <= length2) {
        lengthMin = length1;
    } else {
        lengthMin = length2;
    }

    for (i = 0; i <= lengthMin - 1; i++) {
        string1_save[i] = tolower(string1_save[i]);
        string2_save[i] = tolower(string2_save[i]);

        if (string1_save[i] < string2_save[i]) {
            return 2;
        } else {
            if (string1_save[i] > string2_save[i]) {
                return 1;
            }
        }
    }

    if (length1 > length2) {
        return 1;
    } else {
        if (length1 < length2) {
            return 2;
        } else {
            return 0;
        }
    }

}


void add_record(char new_record[]) {
    FILE *file;

    file = fopen("Cacti.txt", "a");

    fputs(new_record, file);
    fputs("\n", file);

    fclose(file);
}


void show_records(int *number_of_records) {
    int i;

    char record[500];

    FILE *file;

    file = fopen("Cacti.txt", "r");

    for (i = 1; i <= *number_of_records; i++) {
        fgets(record, 500, (FILE *) file);

        if (record[strlen(record) - 1] == '\n')   ///checking if there is a trailing newline & eliminating it
        {
            record[strlen(record) - 1] = '\0';
        }

        printf("%d. ", i);
        printf("%s", record);
        printf("\n");
    }

    fclose(file);
}


void show_ordered_records() {
    char record[500];
    char auxiliary[500][500];

    int i;
    int j;
    int index = 0;

    FILE *file;
    file = fopen("Cacti.txt", "r");

    while (fgets(record, 500, (FILE *) file) != NULL) ///we are not at the end of the file
        ///we save all records in an array of strings
    {
        if (record[strlen(record) - 1] == '\n')   ///checking if there is a trailing newline & eliminating it
        {
            record[strlen(record) - 1] = '\0';
        }

        index++;
        strcpy(auxiliary[index], record);

    }


    ///now we will sort alphabetically
    for (i = 1; i < index; i++) {
        for (j = i + 1; j <= index; j++) {
            if (string_compare(auxiliary[i], auxiliary[j]) == 1) ///first string is greater
                ///we swap the strings
            {
                char swap[500];

                strcpy(swap, auxiliary[i]);
                strcpy(auxiliary[i], auxiliary[j]);
                strcpy(auxiliary[j], swap);
            }
        }
    }


    ///now we copy the sorted list into the original file
    fclose(file);

    file = fopen("Cacti.txt", "w"); ///we empty the file in order to rewrite information
    fclose(file);


    for (i = 1; i <= index; i++) ///we replace the old data with the new, sorted one
    {
        add_record(auxiliary[i]);
    }


    for (i = 1; i <= index; i++) ///displaying records
    {
        printf("%d. %s\n", i, auxiliary[i]);
    }

}


void delete_record(char to_delete[]) {
    int i;
    int flag = 0;
    int current_position = 0;
    int number_of_records = 0;

    char record[500]; ///the name of the plant that we want to delete

    FILE *file;
    file = fopen("Cacti.txt", "r");


    while (fgets(record, 500, (FILE *) file) != NULL) ///we get the total number of records
    {
        number_of_records++;
    }

    printf("\n\nThe number of records is %d.\n", number_of_records);

    fclose(file);
    file = fopen("Cacti.txt", "r");

    while (fgets(record, 500, (FILE *) file) != NULL)  ///we search for the plant name in the file
    {
        current_position++; ///we save the current position

        if (record[strlen(record) - 1] == '\n')   ///checking if there is a trailing newline & eliminating it
        {
            record[strlen(record) - 1] = '\0';
        }


        for (i = 0; record[i] != '\0'; i++) ///we save only the name of the plant
        {
            if (record[i] == '(') ///we search only for the name, not the temperature
            {
                record[i - 1] = '\0';

                break;
            }
        }


        if (string_compare(record, to_delete) == 0) ///we found the record that must be deleted
        {
            flag = 1;

            printf("\nThe record was found (position %d).\n", current_position);
            printf("\nYou were searching for the plants : %s\n\n", record);


            char auxiliary[1000][1000];

            int position = current_position;
            int index = 0;


            fclose(file);
            file = fopen("Cacti.txt", "r"); ///reopening file

            for (i = 1; i <= position - 1; i++) ///we copy all data until the record that will be deleted
            {
                fgets(record, 500, (FILE *) file);

                if (record[strlen(record) - 1] == '\n') ///checking if there is a trailing newline & eliminating it
                {
                    record[strlen(record) - 1] = '\0';
                }

                index++;
                strcpy(auxiliary[index], record);
            }


            fgets(record, 500, (FILE *) file);


            for (i = position + 1; i <= number_of_records; i++) ///we copy all data from that point towards the end
            {
                fgets(record, 500, (FILE *) file);

                if (record[strlen(record) - 1] == '\n') ///checking if there is a trailing newline & eliminating it
                {
                    record[strlen(record) - 1] = '\0';
                }

                index++;
                strcpy(auxiliary[index], record);
            }


            fclose(file);

            file = fopen("Cacti.txt", "w"); ///we empty the file in order to rewrite information
            fclose(file);

            for (i = 1; i <= index; i++) ///we replace the old data with the new information
            {
                add_record(auxiliary[i]);
            }

            printf("\nData was succesfully deleted!\n\n");

            return;
        }
    }

    if (flag == 0) ///we did not found the record
    {
        printf("!There is no record with that name.");
        printf("\nTry to type again the name of the plant.\n\n");
    }
}


void show_genera(char genera_name[]) {

    int i;
    int index = 0;

    char save_data[1000][1000]; ///here we will save all the plants from a specific genera
    char record[500];
    char *token;
    char save_string[500];

    FILE *file;
    file = fopen("Cacti.txt", "r"); ///we read all records and search for the given one


    while (fgets(record, 500, (FILE *) file) != NULL) ///we are not at the end of the file
        ///we search for all plants with that first name and we save them in an array of strings
    {


        if (record[strlen(record) - 1] == '\n')   ///checking if there is a trailing newline & eliminating it
        {
            record[strlen(record) - 1] = '\0';
        }


        strcpy(save_string, record);
        token = strtok(record, " "); ///we save here the first name (the genera)


        if (string_compare(genera_name, token) == 0) ///we found a plant from that genera
        {
            index++;
            strcpy(save_data[index], save_string); ///we save the full name of the plant (+ minimum temperature)
        }
    }

    if (index != 0) {
        for (i = 1; i <= index; i++) ///we print all plants if we found at least one
        {
            printf("\n");
            printf("%d. ", i);
            printf("%s", save_data[i]);
        }

        printf("\n\n\n");
    } else {
        printf("\nThere is currently no plant from the requested genera.\n\n");
    }

}


void select_hardy_plants(int celsius_temperature, char hardy[500][500], char tender[500][500]) {
    int i;
    int negative; ///tells us if the number is negative (put -) or not (put +)
    int index = 0;
    int index_hardy = 0;
    int index_tender = 0;
    int save_temp;

    char record[500];

    FILE *file;
    file = fopen("Cacti.txt", "r");


    while (fgets(record, 500, (FILE *) file) != NULL) ///we search until the end of the file
    {
        save_temp = 0;
        negative = 0;

        if (record[strlen(record) - 1] == '\n')   ///checking if there is a trailing newline & eliminating it
        {
            record[strlen(record) - 1] = '\0';
        }

        for (i = 0; record[i] != '\0'; i++) ///we extract the minimum temperature from the name
        {
            if (record[i] == '(') ///we found the place reserved for the minimum temperature
            {
                i++; ///first letter

                if (record[i] == '-') ///we check if there is a negative number
                {
                    negative = 1;
                } else {
                    save_temp = record[i] - 48;
                }

                i++; ///second letter

                if (record[i] == ')')///we finished reading the minimum temperature
                {
                    break;
                } else {
                    save_temp = save_temp * 10 + (record[i] - 48);
                }

                i++; ///third letter

                if (record[i] == ')')///we finished reading the minimum temperature
                {
                    break;
                } else {
                    save_temp = save_temp * 10 + (record[i] - 48);
                }
            }
        }

        if (negative == 1)///checking if it was a negative number
        {
            save_temp = 0 - save_temp;
        }


        if (save_temp < celsius_temperature) ///we found a hardy plant
        {
            index_hardy++;
            strcpy(hardy[index_hardy], record);
        } else ///we found a tender plant
        {
            index_tender++;
            strcpy(tender[index_tender], record);
        }
    }

    ///we put a special mark at the end of each array of strings in order to know when to stop printing plant names
    ///this will be the string "end"

    strcpy(hardy[index_hardy + 1], "end");
    strcpy(tender[index_tender + 1], "end");
}


char data[500];
char name_to_delete[500];
char genera[500];
char selection[100];
char key;
char main_key;
char hardy[500][500];
char tender[500][500];

int select;
int number_of_records;
int celsius_temperature;


int main() {

    while (main_key != 'x' && main_key != 'X') {

        printf("                                   WELCOME!");
        printf("\n\n\n\n\n\nPlease choose an option from the list below\n\n\n");
        printf("\n1. Add a new plant");
        printf("\n2. Display a number of plants");
        printf("\n3. Display all records in order");
        printf("\n4. Delete a plant by its name");
        printf("\n5. Show a particular genera");
        printf("\n6. Show hardy / tender plants");
        printf("\n\n\n");
        printf("Your selection : ");

        scanf("%d", &select);


        if (select == 1) {

            key = '1'; ///we make sure that key != 'x' or 'X' from the start

            while (key != 'x' && key != 'X') {
                printf("\n\n\nIn order to add a new record, please insert the name of the plant below.");
                printf("\n\n");

                getchar();  /// get the newline left by scanf before using fgets
                fgets(data, 500, stdin);

                if (data[strlen(data) - 1] == '\n')   ///checking if there is a trailing newline & eliminating it
                {
                    data[strlen(data) - 1] = '\0';
                }

                add_record(data);

                printf("\n\n");
                printf("Data was successfully added!\n");
                printf("         To exit, press x, then ENTER\n");
                printf("         For a new record press any other key\n");
                printf("->  ");

                scanf(" %c", &key);
            }

        }


        if (select == 2) {
            printf("Please insert the number of records that will be displayed.");
            printf("\n");
            printf("-> ");

            scanf("%d", &number_of_records);

            printf("\n\nThe first %d plants :", number_of_records);
            printf("\n\n\n");

            show_records(&number_of_records);
        }


        if (select == 3) {
            printf("                          \nTHE COMPLETE LIST\n\n");

            show_ordered_records();
        }


        if (select == 4) {
            printf("\n\n");
            printf("Please insert the name of the plant that you would like to delete\n");
            printf("->  ");

            getchar();  /// get the newline left by scanf before using fgets
            fgets(name_to_delete, 500, stdin); ///reading the name of the plant that we want to delete

            if (name_to_delete[strlen(name_to_delete) - 1] ==
                '\n')   ///checking if there is a trailing newline & eliminating it
            {
                name_to_delete[strlen(name_to_delete) - 1] = '\0';
            }

            delete_record(name_to_delete);

        }


        if (select == 5) {
            printf("\n\n");
            printf("Please insert the name of the genera you would like to see\n");
            printf("->  ");

            getchar(); /// get the newline left by scanf before using fgets
            fgets(genera, 500, stdin); ///reading the name of the genera that will be desplayed

            if (genera[strlen(genera) - 1] == '\n')   ///checking if there is a trailing newline & eliminating it
            {
                genera[strlen(genera) - 1] = '\0';
            }

            printf("\n\nPlants from the %s genera:\n", genera);

            show_genera(genera);
        }


        if (select == 6) {
            int i;
            int flag = 0;

            printf("\n\n");
            printf("This function of the program will sort your plants into two categories.\n");
            printf("By selecting a minimum temperature, you decide what you condider 'hardy'.\n");
            printf("Plants that require a higher minimum temperature will be listed as 'tender'.\n\n");
            printf("Now, please insert the temperature limit (in Celsius degrees) ->  ");

            scanf("%d", &celsius_temperature);


            while (flag != 1) ///this selection part will repeat until we write a correct selection
            {
                printf("\n\nPlease choose one option from below by typing 'hardy' or 'tender':\n\n");
                printf("1. Show hardy plants -> type 'hardy'\n");
                printf("2. Show tender plants -> type 'tender'\n\n");
                printf("Plase insert your selection -> ");

                scanf("%s", &selection);


                if (string_compare(selection, "hardy") == 0) ///we want to see the hardy plant list
                {
                    select_hardy_plants(celsius_temperature, hardy,
                                        tender); ///save in the arrays the two lists of plants (hardy and tender)

                    i = 1;

                    printf("\n\n           LIST OF PLANTS < %d degrees Celsius\n\n", celsius_temperature);

                    while (string_compare(hardy[i], "end") != 0)///we are not at the end of the array
                    {
                        printf("%d. %s\n", i, hardy[i]);

                        i++;
                    }

                    flag = 1; ///made a correct selection
                } else {
                    if (string_compare(selection, "tender") == 0) ///we want to see the tender plant list
                    {
                        select_hardy_plants(celsius_temperature, hardy,
                                            tender); ///save in the arrays the two lists of plants (hardy and tender)

                        i = 1;

                        printf("\n\n           LIST OF PLANTS >= %d degrees Celsius\n\n", celsius_temperature);

                        while (string_compare(tender[i], "end") != 0)///we are not at the end of the array
                        {
                            printf("%d. %s\n", i, tender[i]);

                            i++;
                        }

                        flag = 1; ///made a correct selection
                    } else {
                        printf("\n\nERROR! Please type 'hardy' or 'tender'\n\n"); ///we typed a wrong option
                    }
                }
            }
        }


        printf("\n\n");
        printf("  ->  If you want to exit the application, press x, then ENTER\n");
        printf("  ->  If you want to continue, any other key, followed by ENTER\n");
        printf("  ->  ");

        scanf(" %c", &main_key);

        printf("\n\n\n");

        if (main_key != 'x' && main_key != 'X') {
            clear_screen();
        }


    }

    return 0;
}

