from IPython.Shell import IPShellEmbed

import stomp
import sys

conn = stomp.Connection()
conn.start()
conn.connect()

def send_tracker(id, lat, lng, battery):
    send('id: %d\nposition: %f %f\nbattery: %d' % (id, lat, lng, battery))

def send(msg):
    conn.send(msg, destination='trackers')

def quit():
    conn.disconnect()
    sys.exit()
    
ipshell = IPShellEmbed()
ipshell() 
