import random
print("********************Coin Flipping Game********************")
choice=input("Enter Heads or Tails:\t")
number=random.randint(1,2)
if number==1:
    output="Heads"
elif number==2:
    output="Tails"
if choice==output:
    print("You Won.It flipped",output)
else:
    print("You lost.It flipped",output)
    
    
