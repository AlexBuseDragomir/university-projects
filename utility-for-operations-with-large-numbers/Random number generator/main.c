# include <stdlib.h>
# include <stdio.h>

void number_generator(size_t length) {
    int i;
    int first_digit;

    srand(time(NULL));

    if (length > 1) ///more than one digit
    {
        first_digit = rand() % 9 + 1; ///first digit from 1 - 9

        printf("%d", first_digit);

        for (i = 2; i <= length; i++) ///the other digits from 0 - 9
        {
            printf("%d", rand() % 10);
        }
    } else {
        if (length == 1) ///if we have just one digit we generate from 0 - 9
        {
            printf("%d", rand() % 10);
        }
    }
}

int main() {
    int length;

    printf("Please insert the number of digits ->  ");

    scanf("%d", &length);

    number_generator(length);

    return 0;
}
