#include <bits/stdc++.h>
using namespace std;

// returns index of largest element of array

//explanation:
/*
This program works in 0(n) time. It keeps the track of largest number upto current element and then it compare the largest elememt with current element.Then 
their are two choices than:
1. current elemnt is greater than the largest (largest element is updated , largest = arr[i]) 
2.If current element is equal to or less than the current largest than the program ignores it , because it is not maximum

after finishing all the itetrations the function returns the index of largest element
*/
int largestElement(int arr[], int n)
{

    int indexMax = 0;
    for (int i = 1; i < n; i++)
    {
        if (arr[i] > arr[indexMax])
            indexMax = i;
    }
    return indexMax;
} //tc = 0(n)

int main()
{
    int arr[5] = {1,560,2,69,3};
    cout<<largestElement(arr,5);
    return 0;
}
