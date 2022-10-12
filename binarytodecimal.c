#include <stdio.h>
#include <math.h>

int decimal(long long);

int main() {
  long long n;
  printf("Enter a binary number: ");
  scanf("%lld", &n);
  printf("Decimal Number: %d",  decimal(n));
  return 0;
}

// function definition
int decimal(long long n) {
  int d = 0, i = 0, r;

  while (n!=0) {
    r = n % 10;
    n /= 10;
    d += r * pow(2, i);
    ++i;
  }

  return d;
}