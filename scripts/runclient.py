from org.microemu.device import DeviceFactory
from org.microemu.device.j2se import J2SEDevice;
dev = J2SEDevice()
DeviceFactory.setDevice(dev)

from com.mwicat.kstomp import *


class MyHandler(MessageHandler):
    
    def onMessage(self, msg):
        print msg



handler = MyHandler()
stman = TCPStreamManager('127.0.0.1:61613', 5000)
client = StompClient(stman, handler)
client.connect('', '')