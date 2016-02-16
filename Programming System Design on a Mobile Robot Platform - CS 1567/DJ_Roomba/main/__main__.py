import speech_recognition as sr
import sys
import rospy
import spotipy
import spotipy.util as util
import requests
import requests.packages.urllib3
import webbrowser
from std_msgs.msg import String
import os
import time
import rospy
from kobuki_msgs.msg import Sound
from random import randint

requests.packages.urllib3.disable_warnings()

r = sr.Recognizer()
m = sr.Microphone()
spotify = spotipy.Spotify()
rospy.init_node('sound_sender', anonymous=True)
pubSpeed = rospy.Publisher('speed', String, queue_size=10)
pubSound = rospy.Publisher('/mobile_base/commands/sound', Sound, queue_size=10)
s = Sound()
listening = False

try:
    print("A moment of silence, please...")
    with m as source:
        r.adjust_for_ambient_noise(source)
        print("Set minimum energy threshold to {}".format(r.energy_threshold))
        while True:
            print("Say something!")
            audio = r.listen(source)
            print("Got it! Now to recognize it...")
            try:
                # recognize speech using Google Speech Recognition
                value = r.recognize_google(audio)

                # we need some special handling here to correctly print unicode characters to standard output
                if str is bytes: # this version of Python uses bytes for strings (Python 2)
                    if (listening == True):
                        webbrowser.open("spotify:track:57g9uWuZI1t822eLvEVQjn", new=0, autoraise=False)
                        if ("play artist" in format(value).encode("utf-8") or "Play artist" in format(value).encode("utf-8")):
                            toSearch = format(value).encode("utf-8").split("play artist",1)[1]
                            if (toSearch != ""):
                                results = spotify.search(toSearch, limit=20)
                                items = results['tracks']['items']
                                rand = randint(0, 20)
                                track = items[rand]
                                url = "spotify:track:" + track['id']
                                webbrowser.open(url, new=0, autoraise=False)
                                listening = False
                                pubSpeed.publish("speed up")
                                os.system('python -m main')
                                sys.exit()
                        elif (("play" in format(value).encode("utf-8") or "Play" in format(value).encode("utf-8")) and ("by" in format(value).encode("utf-8") or "bye" in format(value).encode("utf-8"))):
                            toSearchSong = format(value).encode("utf-8").split("play",1)[1]
                            if (toSearchSong != ""):
                                song = toSearchSong.split("by",1)[0]
                                artist = toSearchSong.split("by",1)[1]
                                results = spotify.search(song, limit=20)
                                items = results['tracks']['items']
                                for i in range(0, 20):
                                    print items[i]['artists'][0]['name']
                                    if (" " + items[i]['artists'][0]['name']) == artist:
                                        print "artist found"
                                        url = "spotify:track:" + items[i]['id']
                                        webbrowser.open(url, new=0, autoraise=False)
                                        listening = False
                                        pubSpeed.publish("speed up")
                                        os.system('python -m main')
                                        sys.exit()
                                        break
                        elif ("play song" in format(value).encode("utf-8") or "Play song" in format(value).encode("utf-8")):
                            toSearch = format(value).encode("utf-8").split("Play song", 1)[1]
                            if (toSearch != ""):
                                results = spotify.search(toSearch, limit=1, type='track')
                                items = results['tracks']['items']
                                track = items[0]
                                url = "spotify:track:" + track['id']
                                webbrowser.open(url, new=0, autoraise=False)
                                pubSpeed.publish("speed up")
                                listening = False
                                os.system('python -m main')
                                sys.exit()
                    if ("data" in format(value).encode("utf-8") or "mr" in format(value).encode("utf-8") or "Mr" in format(value).encode("utf-8") or "MR" in format(value).encode("utf-8")
                    or "mister" in format(value).encode("utf-8") or  "mister data" in format(value).encode("utf-8") or "mr. data" in format(value).encode("utf-8") or "mr Dale" in format(value).encode("utf-8")
                    or "mr. Data" in format(value).encode("utf-8")):
                        webbrowser.open("spotify:track:57g9uWuZI1t822eLvEVQjn", new=0, autoraise=False)
                        time.sleep(1)
                        pubSound.publish(0)
                        pubSpeed.publish("")
                        listening = True
                    print(u"You said {}".format(value).encode("utf-8"))
                else: # this version of Python uses unicode for strings (Python 3+)
                    print("You said {}".format(value))
            except sr.UnknownValueError:
                print("Oops! Didn't catch that")
            except sr.RequestError as e:
                print("Uh oh! Couldn't request results from Google Speech Recognition service; {0}".format(e))
except KeyboardInterrupt:
    pass
