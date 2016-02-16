#!/usr/bin/env python

import rospy
from cmvision.msg import Blobs, Blob
from geometry_msgs.msg import Twist

pub = rospy.Publisher('/mobile_base/commands/velocity', Twist, queue_size=10)

def blobsCallback(data):
    x = 0
    y = 0
    area = 0
    if data.blob_count > 0:
        for box in data.blobs:
            area = area + box.area
            x = x + (box.x * box.area)
            y = y + (box.y * box.area)
        x = x / area
        y = y / area
    print "(%i,%i)" % (x,y)
    command = Twist()

    # Linear if statements
    if (area < 50000 and area > 7000):
        command.linear.x = 0.15
    elif (area < 7000 and area > 50):
        command.linear.x = 0.3
    elif (area > 90000 and area < 130000):
        command.linear.x = -0.15
    elif (area > 130000):
        command.linear.x = -0.30
    else:
        command.linear.x = 0.0

    # Angluar if statements
    if (x == 0):
        command.angular.z = 0.0
    elif (x > 300 and x < 340):
        command.angular.z = 0.0
    elif (x <= 300 and x > 220):
        command.angular.z = 0.5
    elif (x > 0 and x <= 220):
        command.angular.z = 1.0
    elif (x >= 340 and x < 420):
        command.angular.z = -0.5
    else:
        command.angular.z = -1.0
    pub.publish(command)

def detect_blob():
    rospy.init_node('blob_tracker', anonymous = True)
    rospy.Subscriber('/blobs', Blobs, blobsCallback)
    rospy.spin()

if __name__ == '__main__':
    detect_blob()
