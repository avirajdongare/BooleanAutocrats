#include <stdio.h>
#include <math.h>

int main () {
    float p, r, t, CI=0, a;
    printf ("Enter Principle amount : Rs.");
    scanf ("%f", &p);
    printf ("Enter Rate of Intrest per annum : ");
    scanf ("%f", &r);
    printf ("Enter no. of Years : ");
    scanf ("%f", &t);

    CI = p* (pow((1 + r / 100), t));

    printf ("\n\nThe Intrest is Rs.%.3f", CI);
    printf ("\nThe Total Amount is Rs.%.3f", CI + p);
return 0;
}