from tkinter import filedialog
from tkinter import ttk
import tkinter.messagebox
from tkinter import *
import os
import threading
from ttkthemes import themed_tk as tk
from mutagen.mp3 import MP3
import time
from pygame import mixer
root = tk.ThemedTk()
#root.geometry("555x290")
root["bg"]="#195FBA"
#setting theme for player
root.set_theme("elegance")
statusbar = ttk.Label(root, text="Welcome to Krazy Music Player", anchor=W, font='Arial 8 italic')
statusbar.pack(side=BOTTOM, fill=X)
statusbar1 = ttk.Label(root, text="Welcome to Krazy Music Player", anchor=W, font='Arial 8 italic')
statusbar1.pack(side=TOP, fill=X)
#// Create the menubar
menubar = Menu(root)
root.config(menu=menubar)
#// Create the submenu
subMenu = Menu(menubar, tearoff=0)
#list for storing play
songplaylist = []
def browse_file():
global filename_path
filename_path = filedialog.askopenfilename()
add_to_songplaylist(filename_path)
mixer.music.queue(filename_path)
def add_to_songplaylist(filename):
filename = os.path.basename(filename)
index = 0
songplaylistcontainer.insert(index, filename)
songplaylist.insert(index, filename_path)
index += 1
menubar.add_cascade(label="File", menu=subMenu)
subMenu.add_command(label="Open", command=browse_file)
subMenu.add_command(label="Exit", command=root.destroy)
mixer.init() #// initializing the mixer
root.title("Krazy Music Player")
ltframe = Frame(root,bg="#364C69")
ltframe.pack(side=LEFT, padx=30, pady=30)
songplaylistcontainer = Listbox(ltframe,fg='white')
songplaylistcontainer["bg"]="#364C69"
songplaylistcontainer.pack()
addBtn = ttk.Button(ltframe, text="+ Add",command=browse_file)
addBtn.pack(side=LEFT)
def remove_song():
sel_song = songplaylistcontainer.curselection()
sel_song = int(sel_song[0])
songplaylistcontainer.delete(sel_song)
songplaylist.pop(sel_song)
root.style = ttk.Style()
root.style.theme_use("clam")
remBtn = ttk.Button(ltframe, text="- Del", command=remove_song)
remBtn.pack(side=LEFT)
root.style.configure('TButton', background='#12936F')
rtframe = Frame(root,bg="#364C69")
rtframe.pack(pady=30,padx=20)
topframe = Frame(rtframe,bg="#364C69")
topframe.pack()
root.style = ttk.Style()
#root.style.theme_use("clam")
root.style.configure('TLabel', background='#364C69')
lengthlabel = ttk.Label(topframe,text='Total Time : --:--')
lengthlabel.pack(pady=5)
currenttimelabel = ttk.Label(topframe, text='Current Time : --:--')
currenttimelabel.pack()
def show_details(play_song):
file_data = os.path.splitext(play_song)
if file_data[1] == '.mp3':
audio = MP3(play_song)
total_length = audio.info.length
else:
a = mixer.Sound(play_song)
total_length = a.get_length()
mins, secs = divmod(total_length, 60)
mins = round(mins)
secs = round(secs)
timeformat = '{:02d}:{:02d}'.format(mins, secs)
lengthlabel['text'] = "Total Time" + ' - ' + timeformat
t1 = threading.Thread(target=start_count, args=(total_length,))
t1.start()
def start_count(t):
global paused
current_time = 0
while current_time <= t and mixer.music.get_busy():
if paused:
continue
else:
mins, secs = divmod(current_time, 60)
mins = round(mins)
secs = round(secs)
timeformat = '{:02d}:{:02d}'.format(mins, secs)
currenttimelabel['text'] = "Current Time" + ' - ' + timeformat
time.sleep(1)
current_time += 1
def play_music():
global paused
if paused:
mixer.music.unpause()
statusbar['text'] = "Music Resumed"
paused = FALSE
else:
try:
stop_music()
time.sleep(1)
sel_song = songplaylistcontainer.curselection()
sel_song = int(sel_song[0])
play_it = songplaylist[sel_song]
mixer.music.load(play_it)
mixer.music.play()
statusbar['text'] = "Playing music" + ' - ' + os.path.basename(play_it)
show_details(play_it)
except:
tkinter.messagebox.showerror('File not found', 'Krazy music player could not find the file. Please select again.')
def stop_music():
mixer.music.stop()
statusbar['text'] = "Music Stopped"
paused = FALSE
def pause_music():
global paused
paused = TRUE
mixer.music.pause()
statusbar['text'] = "Music Paused"
def rewind_music():
play_music()
statusbar['text'] = "Music Rewinded"
def set_vol(val):
volume = float(val) / 100
mixer.music.set_volume(volume)
muted = FALSE
def mute_music():
global muted
if muted:
mixer.music.set_volume(0.7)
volumeBtn.configure(text="Mute")
scale.set(70)
muted = FALSE
else:
mixer.music.set_volume(0)
volumeBtn.configure(text="Volume")
scale.set(0)
muted = TRUE
middleframe = Frame(rtframe,bg="#364C69")
middleframe.pack(pady=30, padx=30)
playBtn = ttk.Button(middleframe, text="Play", command=play_music)
playBtn.grid(row=0, column=0, padx=10)
stopBtn = ttk.Button(middleframe, text="Stop", command=stop_music)
stopBtn.grid(row=0, column=1, padx=10)
pauseBtn = ttk.Button(middleframe, text="Pause", command=pause_music)
pauseBtn.grid(row=0, column=2, padx=10)
bottomframe = Frame(rtframe,bg="gray")
bottomframe.pack(pady=10,padx=5)
rewindBtn = ttk.Button(bottomframe, text="Rewind", command=rewind_music)
rewindBtn.grid(row=0, column=0,padx=10)
volumeBtn = ttk.Button(bottomframe, text="Mute", command=mute_music)
volumeBtn.grid(row=0, column=1)
scale = ttk.Scale(bottomframe, from_=0, to=100, orient=HORIZONTAL, command=set_vol)
scale.set(70) # implement the default value of scale when music player starts
mixer.music.set_volume(0.7)
scale.grid(row=0, column=2, pady=15, padx=30)
def on_closing():
stop_music()
root.destroy()
root.protocol("WM_DELETE_WINDOW", on_closing)
if _name_==mainloop():
root.mainloop()
