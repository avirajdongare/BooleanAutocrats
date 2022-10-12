

#include <stdio.h>
int main()
{
    int n;
    printf("Enter the number");
    scanf("%d", &n );

    for(int i = 1; i<=10; i++) {
    printf("%d", n); 
    printf("*%d=", i);
    printf("%d\n", n*i);
    }

return 0;
}
