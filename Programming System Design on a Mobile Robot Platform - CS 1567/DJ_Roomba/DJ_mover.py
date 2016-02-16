#!/usr/bin/env python

import rospy
import math
import os
import sys
import numpy
import cv2
from sensor_msgs.msg import Image
from cv_bridge import CvBridge, CvBridgeError
from struct import unpack
from std_msgs.msg import String
from geometry_msgs.msg import Twist

pub = rospy.Publisher('/mobile_base/commands/velocity', Twist, queue_size=10)
command = Twist()
command.linear.x = 0.0
targetSpeed = 0.15
distCounter = 0
distCounterRight = 0
distCounterLeft = 0

depthData = Image()
isDepthReady = False
isRotating = False
listening = False
rotateDirection = 0 #0 = none, 1 = left, 2 = right

def depthCallback(data):
    global depthData, isDepthReady
    depthData = data
    isDepthReady = True

def setSpeed(x):
    global targetSpeed, isRotating, listening
    if (x.data == ""):
        targetSpeed = 0.0
        isRotating = False
        listening = True
    else:
        targetSpeed = 0.15
        listening = False

def main():
    global depthData, isDepthReady, command, targetSpeed, distCounter, isRotating, distCounterRight, distCounterLeft, listening, rotateDirection
    rospy.init_node('DJ_roomba', anonymous=True)
    rospy.Subscriber("/camera/depth/image", Image, depthCallback, queue_size=10)
    rospy.Subscriber('speed', String, setSpeed, queue_size=10)
    while not isDepthReady and not rospy.is_shutdown():
        pass

    while not rospy.is_shutdown():
        if (targetSpeed > command.linear.x):
            command.linear.x += 0.0005
        elif (targetSpeed < command.linear.x):
            command.linear.x -= 0.0005
        pub.publish(command)

	for i in range(25, 615, 10):
            for j in range(0, 220, 10):
                step = depthData.step
                midX = i
                midY = j
                offset = (midY * step) + (midX * 4)
                (distClose,) = unpack('f', depthData.data[offset] + depthData.data[offset+1] + depthData.data[offset+2] + depthData.data[offset+3])
                if (distClose < 0.6 or math.isnan(distClose) == True):
                    distCounter += 1
                    if (i < 320):
                        distCounterLeft += 1
                    else:
                        distCounterRight += 1

        if (isRotating == True and listening == False):
            if (distCounter > 300 and rotateDirection == 0):
                if (distCounterLeft > distCounterRight):
                    command.angular.z = -0.5
                    rotateDirection = 1
                elif (distCounterLeft < distCounterRight):
                    command.angular.z = 0.5
                    rotateDirection = 1
            elif(distCounter < 300 and rotateDirection == 1):
                isRotating = False
                command.angular.z = 0.0
                rotateDirection = 0
        else:
            isRotating = False
            command.angular.z = 0.0

        if (distCounter > 600):
            targetSpeed = 0.0
            if (listening == False):
                isRotating = True
            else:
                isRotating = False
        else:
            if (listening != True):
                targetSpeed = .15

        print "DistCounter: %d" %(distCounter)
        distCounter = 0
        distCounterLeft = 0
        distCounterRight = 0

if __name__ == '__main__':
    main()
