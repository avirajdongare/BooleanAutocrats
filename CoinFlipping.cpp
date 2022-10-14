#include <iostream>
#include <ctime>
#include <iomanip>
#include <cstdlib>
#include <fstream>
#include <string>
using namespace std;
int CnToss(void) {
	int tempnum;
	tempnum = 1 + rand() %2;
	return tempnum;
}
int main() {
	int Times = 0,tempnum = 0;
	string HeadTail = "";
	cout << "Number of times a coin to be flipped=";
	cin >>Times;
	srand((time(0)));
	for (int i = 1; i <= Times; ++i) {
		tempnum = CnToss();
		if (tempnum ==1)
		{
			HeadTail= "head";
		}
		else{
			HeadTail= "tail";
		cout << HeadTail<< endl;
		}
	}
}
