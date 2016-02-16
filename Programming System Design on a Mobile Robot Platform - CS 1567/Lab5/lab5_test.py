#!/usr/bin/env python

import rospy
from sensor_msgs.msg import Image
import math
from struct import unpack
from geometry_msgs.msg import Twist

pub = rospy.Publisher('/mobile_base/commands/velocity', Twist, queue_size=10)
command = Twist()
command.linear.x = 0.3
depthData = Image()
isDepthReady = False

def depthCallback(data):
    global depthData, isDepthReady
    depthData = data
    isDepthReady = True

def main():
    global depthData, isDepthReady, command, pub
    rospy.init_node('depth_example', anonymous=True)
    rospy.Subscriber("/camera/depth/image", Image, depthCallback, queue_size=10)

    while not isDepthReady and not rospy.is_shutdown():
        pass

    while not rospy.is_shutdown():
        step = depthData.step
        sumDist = 0
        actualDist = 0
        distCounter = 0
        for i in range (0,31,5):
            for j in range (0, 31, 5):
                midX = 300 + i
                midY = 220 + j
                offset = (midY * step) + (midX * 4)

                (dist,) = unpack('f', depthData.data[offset] + depthData.data[offset+1] + depthData.data[offset+2] + depthData.data[offset+3])
                if (math.isnan(dist) == False):
                    sumDist += dist
                    distCounter += 1
        if (distCounter == 0):
            actualDist = float('NaN')
        else:
            actualDist = sumDist / distCounter
        if (actualDist > 1.0):
            command.linear.x = 0.3
            pub.publish(command)
        elif (math.isnan(actualDist)):
            command.angular.z = 0
            command.linear.x = 0
            pub.publish(command)
            print "dist is nan"
            pass
        else:
            command.linear.x = 0
            pub.publish(command)
            angularCounter = 0
            while (angularCounter < 2):
                command.angular.z = 1
                pub.publish(command)
                rospy.sleep(0.5)
                angularCounter += 1

            command.angular.z = 0
            pub.publish(command)

        print "Distance: %f" % actualDist


if __name__ == '__main__':
    main()
