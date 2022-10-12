#include<stdio.h>
void main()
{
    int i,n,high,low,mid,key,array[100];
    printf("Enter number of elements\n");
    scanf("%d",&n);
    printf("Enter %d elements\n",n);
    for(i=0;i<n;i++)
    scanf("%d",&array[i]);
    printf("Enter Value to search");
    scanf("%d",&key);
    low=0;
    high=n-1;
    mid=(low+high)/2;
    while(low<=high)
    {
        if(array[mid]<key)
        low=mid+1;
        else if(array[mid]==key)
        {
            printf("%d found at location %d\n",key,mid+1);
            break;
        }
        else
        high=mid-1;
        mid=(low+high)/2;
    }
    if(low>high)
    printf("%d Not present in the list\n",key);
    return 0;
}






