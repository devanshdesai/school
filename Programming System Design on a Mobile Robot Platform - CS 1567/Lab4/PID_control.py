#!/usr/bin/env python

import rospy
from cmvision.msg import Blobs, Blob
from geometry_msgs.msg import Twist

pub = rospy.Publisher('/mobile_base/commands/velocity', Twist, queue_size=10)
command = Twist()
command.linear.x = 0.2

def blobsCallback(data):
    global command
    x = 0
    y = 0
    area = 0
    kp = 0.0035
    errorX = 0
    errorY = 0
    if data.blob_count > 0:
        for box in data.blobs:
            area = area + box.area
            x = x + (box.x * box.area)
            y = y + (box.y * box.area)
        x = x / area
        y = y / area
    print "(%i,%i)" % (x,y)
    errorX = 320 - x
    command.angular.z = errorX * kp
    pub.publish(command)

def detect_blob():
    rospy.init_node('blob_tracker', anonymous = True)
    rospy.Subscriber('/blobs', Blobs, blobsCallback)
    rospy.spin()

if __name__ == '__main__':
    detect_blob()
