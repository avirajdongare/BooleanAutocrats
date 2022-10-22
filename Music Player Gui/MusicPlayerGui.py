Python 3.10.8 (tags/v3.10.8:aaaf517, Oct 11 2022, 16:50:30) [MSC v.1933 64 bit (AMD64)] on win32
Type "help", "copyright", "credits" or "license()" for more information.
>>> import pygame
... from pygame import mixer
... from tkinter import *
... import os
... 
... def playsong():
...     currentsong=playlist.get(ACTIVE)
...     print(currentsong)
...     mixer.music.load(currentsong)
...     songstatus.set("Playing")
...     mixer.music.play()
... 
... def pausesong():
...     songstatus.set("Paused")
...     mixer.music.pause()
... 
... def stopsong():
...     songstatus.set("Stopped")
...     mixer.music.stop()
... 
... def resumesong():
...     songstatus.set("Resuming")
...     mixer.music.unpause()    
... 
... root=Tk()
... root.title('Music player project')
... 
... mixer.init()
... songstatus=StringVar()
... songstatus.set("choosing")
... 
... #playlist---------------
... 
... playlist=Listbox(root,selectmode=SINGLE,bg="DodgerBlue2",fg="white",font=('arial',15),width=40)
... playlist.grid(columnspan=5)
... 
os.chdir(r'C:\Users\DELL\Music')
songs=os.listdir()
for s in songs:
    playlist.insert(END,s)

playbtn=Button(root,text="play",command=playsong)
playbtn.config(font=('arial',20),bg="DodgerBlue2",fg="white",padx=7,pady=7)
playbtn.grid(row=1,column=0)

pausebtn=Button(root,text="Pause",command=pausesong)
pausebtn.config(font=('arial',20),bg="DodgerBlue2",fg="white",padx=7,pady=7)
pausebtn.grid(row=1,column=1)

stopbtn=Button(root,text="Stop",command=stopsong)
stopbtn.config(font=('arial',20),bg="DodgerBlue2",fg="white",padx=7,pady=7)
stopbtn.grid(row=1,column=2)

Resumebtn=Button(root,text="Resume",command=resumesong)
Resumebtn.config(font=('arial',20),bg="DodgerBlue2",fg="white",padx=7,pady=7)
Resumebtn.grid(row=1,column=3)

mainloop()
SyntaxError: multiple statements found while compiling a single statement
