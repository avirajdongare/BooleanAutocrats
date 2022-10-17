#include <bits/stdc++.h>
using namespace std;

// NOTE that function may return -1 also if all elelmets are same(since no second laregest exits then)
// eg: {26,26,26,26} --> first largest = 0  and second largest = -1  (indexes)

// int largestElement(int arr[], int n)
// {

//     int indexMax = 0;
//     for (int i = 1; i < n; i++)
//     {
//         if (arr[i] > arr[indexMax])
//             indexMax = i;
//     }
//     return indexMax;
// }

// int secondLarge(int arr[], int n)
// {
// int large = largestElement(arr,n);
// int res = -1;

//     for (int i = 0; i < n; i++)
//     {
//         if (arr[i] != arr[large])
//         {
//             if (res == -1)
//             {
//                 res = i;
//             }
//         else if (arr[i] > arr[res])
//         {
//             res = i;
//         }

//         }
//     }
//     return res;
// }
// Naive approach --> tc = 0(2n) two traversal

int secondLarge(int arr[], int n)
{
    int large = 0;
    int res = -1;

    for (int i = 1; i < n; i++)
    {
        if (arr[i] > arr[large])
        {
            res = large;
            large = i;
        }
        else if (arr[i] < arr[large])
        {
            if (res == -1 || arr[i] > arr[res])
                res = i;
        }
    }
    return res;
} //best approach --> tc(n) one traversal

int main()
{
    int arr[] = {100,100,100,100};
    cout << secondLarge(arr, 4);
    return 0;
}


