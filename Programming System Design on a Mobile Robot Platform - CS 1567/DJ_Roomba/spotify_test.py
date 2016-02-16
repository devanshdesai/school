import sys
import spotipy
import spotipy.util as util
import requests
import requests.packages.urllib3
import webbrowser
from random import randint

requests.packages.urllib3.disable_warnings()
spotify = spotipy.Spotify()
results = spotify.search("the weeknd", limit=15, type='track')
items = results['tracks']['items']

r = randint(0, 15)
track = items[r]
url = track['id']
url = "spotify:track:" + url
webbrowser.open(url, new=0, autoraise=False)
