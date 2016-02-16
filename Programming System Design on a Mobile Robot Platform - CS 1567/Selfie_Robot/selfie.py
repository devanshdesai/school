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
blobData = Blobs()
isBlobReady = False
pink = []
green = []
gold = []
blobToTrack = Blob()
colorImage = Image()

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
seesColors = False
isStopped = False

takePicture = False
firstMerge = True

oldTime = int(round(time.time() * 1000000))

def depthCallback(data):
    global depthData, isDepthReady
    depthData = data
    isDepthReady = True

def blobsCallback(data):
    global blobData, isBlobReady, gold, pink, green, blobToTrack, seesColors, firstMerge, takePicture
    blobData = data
    pink = []
    green = []
    gold = []
    if data.blob_count > 0:
        for i in data.blobs:
            if i.name == "pink":
                pink.append(i)
            elif i.name == "green":
                green.append(i)
            else:
                gold.append(i)
        merge(green)
        merge(pink)
        merge(gold)
        mergeAll(gold, pink, green)
        blobToTrack.x = 320
        seesColors = False
        mergeBoth(gold, pink)
        isBlobReady = True
    else:
        blobToTrack.x = 320

def rgbCallback(data):
    global colorImage
    colorImage = data

def mergeBoth(color1, color2):
    global gold, pink, green, blobToTrack, seesColors, firstMerge, takePicture
    for i in color1:
        for j in color2:
            if (i.left < j.left and i.top < j.top and i.right > j.right and i.bottom > j.bottom):
                blobToTrack = j
                seesColors = True
                return

def mergeAll(color1, color2, color3):
    global gold, pink, green, blobToTrack, takePicture, seesColors
    for i in color1:
        for j in color2:
            for k in color3:
                if ((i.left > j.left and i.left > k.left and j.left > k.left) and\
                (i.top > j.top and i.top > k.top and j.top > k.top) and\
                (i.right < j.right and i.right < k.right and j.right < k.right) and\
                (i.bottom < j.bottom and i.bottom < k.bottom and j.bottom < k.bottom)):
                    takePicture = True
                    return

def merge(color):
    global gold, pink, green
    merged = True
    while (merged):
        merged = False
        for i in color:
            for j in color:
                if (j != i):
                    if ((i.right > j.left and i.right < j.right and i.bottom > j.top and i.top < j.bottom) or\
                    (j.right > i.left and j.left < i.right and j.bottom > i.top and j.top < i.bottom) or\
                    (j.bottom > i.top and j.top < i.bottom and j.left < i.right and j.right > i.left) or\
                    (i.bottom > j.top and i.top < j.bottom and i.left < j.right and i.right > j.left)):
                        i.top = min(i.top, j.top)
                        i.left = min(i.left, j.left)
                        i.right = max(i.right, j.right)
                        i.bottom = max(i.bottom, j.bottom)
                        color.remove(j)
                        merged = True

def main():
    global isStopped, oldTime, dist, blobData, colorImage, isBlobReady, gold, pink, green, blobToTrack, errorXAngle, errorYAngle, errorXDist, errorYDist, command, oldX, oldXCounter, depthData, isDepthReady, seesColors, oldDist, oldDistCounter, oldDistZeroCounter, takePicture
    rospy.init_node('selfie_robot', anonymous=True)
    rospy.Subscriber("/camera/rgb/image_color", Image, rgbCallback, queue_size=10)
    rospy.Subscriber('/blobs', Blobs, blobsCallback)
    rospy.Subscriber("/camera/depth/image", Image, depthCallback, queue_size=10)
    bridge = CvBridge()
    blobToTrack.x = 320
    dist = 2
    for i in range(0, 1000):
        oldDist.append(2)

    while not isBlobReady and not isDepthReady and not rospy.is_shutdown():
        pass

    while not rospy.is_shutdown():
	#Pid for angular control
        kpAngle = 0.008
        kdAngle = 0.1
        errorYAngle = errorXAngle
        errorXAngle = 320 - (blobToTrack.x + oldX[0] + oldX[1] + oldX[2] + oldX[3] + oldX[4])/6
        oldX[oldXCounter % 5] = blobToTrack.x
        command.angular.z = errorXAngle * kpAngle + (kdAngle * (errorXAngle - errorYAngle))
        oldXCounter += 1

        if (seesColors):
            step = depthData.step
            midX = blobToTrack.x
            midY = blobToTrack.y
            offset = (midY * step) + (midX * 4)
            (dist,) = unpack('f', depthData.data[offset] + depthData.data[offset+1] + depthData.data[offset+2] + depthData.data[offset+3])
            if (dist > 2.0):
                dist += .000001
            elif(dist < 2.0):
                dist -= .000001
            oldDistZeroCounter = 0
        else:
            oldDistZeroCounter += 1

        oldDistCounter += 1


        if (oldDistZeroCounter > 25):
            if (dist > 2.0):
                dist -= .01
            elif(dist < 2.0):
                dist += .01

        if(math.isnan(dist) == False):
            oldDist[oldDistCounter % 1000] = dist


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
	    newTime = int(round(time.time() * 1000000))
            if ((newTime - oldTime) > 7):
                oldTime = newTime
                #os.system('spd-say \"Excuse me.\"')
        else:
            isStopped = False

        #newTime = int(round(time.time() * 1000000))
        #if ((newTime - oldTime) > 7):
        #    if (averageDist > 5):
        #        os.system('spd-say \"Wait, you are going too fast.\"')
        #        oldTime = newTime
        #    elif (averageDist < 1):
        #        os.system('spd-say \"Why are you getting so close to me?\"')
        #        oldTime = newTime

        if (takePicture == True):
            try:
                rospy.sleep(1)
                os.system('spd-say \"Three\"')
                rospy.sleep(1)
                os.system('spd-say \"Two\"')
                rospy.sleep(1)
                os.system('spd-say \"One\"')
                rospy.sleep(1)
                os.system('spd-say \"Smile\"')
                color_image = bridge.imgmsg_to_cv2(colorImage, "bgr8")
                takePicture = False
            except CvBridgeError, e:
                print e

            filename = "/home/student/cs1567/src/mypackage/scripts/Selfie_Robot/image/"
            filename = filename + strftime("%Y-%m-%d %H:%M:%S", gmtime()) + ".jpg"
            cv2.imwrite(filename, color_image, [cv2.IMWRITE_JPEG_QUALITY, 100])
            rospy.sleep(2)
            os.system('spd-say \"Bro, that is a fresh picture\"')
            print "Took picture"
            takePicture = False

        pub.publish(command)


if __name__ == '__main__':
    main()
