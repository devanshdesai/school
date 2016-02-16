#!/usr/bin/env python

import rospy
from time import gmtime, strftime
from sensor_msgs.msg import Image
import math
import os
import time
import numpy
import cv2
from cv_bridge import CvBridge, CvBridgeError
from struct import unpack
from geometry_msgs.msg import Twist
from cmvision.msg import Blobs, Blob

pub = rospy.Publisher('/mobile_base/commands/velocity', Twist, queue_size=10)
command = Twist()
command.linear.x = 0

errorXAngle = 0
errorYAngle = 0
errorXDist = 0
errorYDist = 0
oldX = [320, 320, 320, 320, 320]
oldDist = []
oldDistCounter = 0
oldDistZeroCounter = 0
oldXCounter = 0
dist = 2

depthData = Image()
isDepthReady = False


def depthCallback(data):
    global depthData, isDepthReady
    depthData = data
    isDepthReady = True





def main():
    global isStopped, dist, errorXDist, errorYDist, command, oldX, oldXCounter, depthData, isDepthReady, oldDist, oldDistCounter, oldDistZeroCounter
    rospy.init_node('selfie_robot', anonymous=True)
    rospy.Subscriber("/camera/depth/image", Image, depthCallback, queue_size=10)
    bridge = CvBridge()
    blobToTrack.x = 320
    dist = 2
    for i in range(0, 1000):
        oldDist.append(2)

    while not isDepthReady and not rospy.is_shutdown():
        pass

    while not rospy.is_shutdown():
	#Pid for angular control

        #Pid for distance control
        kpDist = 0.4
        kdDist = 0.4
        errorYDist = errorXDist
        averageDist = numpy.mean(oldDist)
        if (averageDist > 1.75 and averageDist < 2.25):
            errorXDist = 0
        else:
            errorXDist = averageDist - 2
        command.linear.x = errorXDist * kpDist #+ (kdDist * (errorXDist - errorYDist))

        distCounter = 0
        for i in range(0, 640, 10):
            for j in range(0, 320, 10):
                step = depthData.step
                midX = i
                midY = j
                offset = (midY * step) + (midX * 4)
                (distClose,) = unpack('f', depthData.data[offset] + depthData.data[offset+1] + depthData.data[offset+2] + depthData.data[offset+3])
                if (distClose < 0.7 or math.isnan(distClose) == True):
                    distCounter += 1

        print distCounter


        if (distCounter > 900):
            command.linear.x = 0.0
            command.angular.z = 0.0
            pub.publish(command)
            isStopped = True
            for i in range(0, 1000):
                oldDist[i] = 2
        else:
            isStopped = False



        pub.publish(command)


if __name__ == '__main__':
    main()
