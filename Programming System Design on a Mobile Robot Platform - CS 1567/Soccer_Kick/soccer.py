#!/usr/bin/env python

import rospy
import os
from nav_msgs.msg import Odometry
import math
from tf.transformations import euler_from_quaternion
from std_msgs.msg import String
from cmvision.msg import Blobs, Blob
from geometry_msgs.msg import Twist

pub = rospy.Publisher('/mobile_base/commands/velocity', Twist, queue_size=10)
resetOdom = rospy.Publisher('reset_odom', String, queue_size=10)
command = Twist()
command.linear.x = 0.3
followingLine = True
firstTurn = False
secondTurn = False
thirdTurn = False
moveToKick = False
currentDistance = 0
currentAngle = 0
errorX = 0
errorY = 0
angle1ball = 0
angle2ball = 0
angle1goal = 0
angle2goal = 0

def blobsCallback(data):
    global command, followingLine, currentAngle, currentDistance, firstTurn, errorX, errorY
    global resetOdom, angle1ball, angle2ball, angle1goal, angle2goal, moveToKick, secondTurn, thirdTurn
    x = 0
    y = 0
    area = 0
    kp = 0.0035
    kd = .02
    if followingLine:
        if data.blob_count > 0:
            errorY = errorX
            for box in data.blobs:
                area = area + box.area
                x = x + (box.x * box.area)
                y = y + (box.y * box.area)
            x = x / area
            y = y / area
            errorX = 320 - x
            command.angular.z = errorX * kp + (kd * (errorX - errorY))
        else:
            command.angular.z = 0
            command.linear.x = 0.05
            pub.publish(command)
            raw_input("Press Enter to continue")
            followingLine = False
            rospy.sleep(.1)
            command.linear.x = 0
            firstTurn = True
        print "(%i,%i)" % (x,y)
        pub.publish(command)
    if secondTurn:
        if data.blob_count > 0:
            for box in data.blobs:
                if box.name == "theBall":
                    #print(box.x)
                    if box.x > 315 and box.x < 325:
                        angle1ball = abs(currentAngle)
                        print "Seeing angle1ball"
                if box.name == "theGoal":
                    #print(box.x)
                    if box.x > 315 and box.x < 325 and box.area > 1000:
                        angle1goal = abs(currentAngle)
                        print "Seeing angle1goal"

    if thirdTurn:
        if data.blob_count > 0:
            for box in data.blobs:
                if box.name == "theBall":
                    #print(box.x)
                    if box.x > 315 and box.x < 325:
                        angle2ball = 180 - abs(currentAngle)
                        print "Seeing angle2ball"
                if box.name == "theGoal":
                    #print(box.x)
                    if box.x > 315 and box.x < 325 and box.area > 1000:
                        angle2goal = 180 - abs(currentAngle)
                        print "Seeing angle2goal"

def odomCallback(data):
    # Convert quaternion to degree
    global currentDistance, currentAngle
    q = [data.pose.pose.orientation.x,
         data.pose.pose.orientation.y,
         data.pose.pose.orientation.z,
         data.pose.pose.orientation.w]
    roll, pitch, yaw = euler_from_quaternion(q)
    # roll, pitch, and yaw are in radian
    currentAngle = yaw * 180 / math.pi
    x = data.pose.pose.position.x
    currentDistance = data.pose.pose.position.x
    y = data.pose.pose.position.y
    msg = "(%.6f,%.6f) at %.6f degree." % (x, y, currentAngle)
    #rospy.loginfo(msg)


def detect_blob():
    global command, followingLine, currentAngle, currentDistance, firstTurn
    global resetOdom, angle1ball, angle2ball, angle1goal, angle2goal, moveToKick, secondTurn, thirdTurn
    rospy.init_node('blob_tracker', anonymous = True)
    rospy.Subscriber('/blobs', Blobs, blobsCallback)
    rospy.Subscriber('/odom', Odometry, odomCallback)
    while(True):
        if firstTurn:
            print("Starting to turn")
            #rospy.sleep(6)
            currentAngle = 0
            currentDistance = 0
            resetOdom.publish("")
            rospy.sleep(0.5)
            while (abs(currentAngle) < 90):
                command.angular.z = -.5
                pub.publish(command)

            command.angular.z = 0
            pub.publish(command)
            firstTurn = False
            secondTurn = True
        if secondTurn:
            currentAngle = 0
            currentDistance = 0
            resetOdom.publish("")
            rospy.sleep(0.5)
            while (currentAngle < 179):
                command.angular.z = .3
                pub.publish(command)
            command.angular.z = 0
            pub.publish(command)
            print "\n\nBall located at: "
            print angle1ball
            print "\nGoal located at: "
            print angle1goal
            currentAngle = 0
            resetOdom.publish("")
            rospy.sleep(0.5)
            while (currentAngle < 1):
                command.angular.z = .2
                pub.publish(command)
            command.angular.z = 0
            pub.publish(command)
            currentDistance = 0
            resetOdom.publish("")
            rospy.sleep(0.5)
            while (currentDistance <= 0.80):
                command.linear.x = 0.2
                pub.publish(command)
            command.linear.x = 0
            pub.publish(command)
            secondTurn = False
            thirdTurn = True
        if thirdTurn:
            currentAngle = 0
            currentDistance = 0
            resetOdom.publish("")
            rospy.sleep(0.5)
            while (abs(currentAngle) < 179):
				command.angular.z = -.3
				pub.publish(command)
            command.angular.z = 0
            pub.publish(command)
            print "\n\nBall located at: "
            print angle2ball
            print "\nGoal located at: "
            print angle2goal
            currentAngle = 0
            resetOdom.publish("")
            rospy.sleep(0.5)
            while (abs(currentAngle) < 1):
                command.angular.z = -.2
                pub.publish(command)
            command.angular.z = 0
            pub.publish(command)
            thirdTurn = False
            moveToKick = True
        if moveToKick:
            if (angle1ball <= 90 and angle1goal <= 90):
                print "in the if statement"
                xball = (-0.8 * math.tan(angle2ball*3.1415/180) / (math.tan(angle2ball*3.1415/180) - math.tan(angle1ball*3.1415/180)))
                xgoal = (-0.8 * math.tan(angle2goal*3.1415/180) / (math.tan(angle2goal*3.1415/180) - math.tan(angle1goal*3.1415/180)))
                yball = xball * math.tan(angle1ball*3.1415/180)
                ygoal = xgoal * math.tan(angle1goal*3.1415/180)
                distToMove = 0
                print "xball = "
                print xball
                print "yball = "
                print yball
                print "xgoal =  "
                print xgoal
                print "ygoal =  "
                print ygoal
                if (xball < xgoal):
                    z = ((yball*xgoal) - (yball*xball))/ (ygoal - yball)
                    distToMove = (xball + 0.8) - z
                    angleToMove = math.atan2(yball,z) * (180/3.1415)
                elif (xball > xgoal):
                    z = ((yball*xball) - (yball*xgoal))/ (ygoal - yball)
                    distToMove = (xball + 0.8) + z
                    angleToMove = 180 - (math.atan2(yball,z) * (180/3.1415))
                print "distToMove: "
                print distToMove
                if (distToMove <= 0):
                    command.linear.x = -0.2
                else:
                    command.linear.x = 0.2
                currentDistance = 0
                resetOdom.publish("")
                rospy.sleep(0.5)
                while (abs(currentDistance) < abs(distToMove)):
                    pub.publish(command)
                command.linear.x = 0
                pub.publish(command)
                currentDistance = 0
                currentAngle = 0
                resetOdom.publish("")
                rospy.sleep(0.5)
                print "angleToMove: "
                print angleToMove
                while (abs(currentAngle) < angleToMove):
                    command.angular.z = .5
                    pub.publish(command)
                command.angular.z = 0
                pub.publish(command)
                currentDistance = 0
                currentAngle = 0
                resetOdom.publish("")
                rospy.sleep(0.5)
                while (abs(currentDistance) < ((yball/math.sin(angleToMove*3.1415/180)) - 0.10)):
                    command.linear.x = 1.0
                    pub.publish(command)
                command.linear.x = 0
                pub.publish(command)
                currentDistance = 0
                resetOdom.publish("")
                moveToKick = False
if __name__ == '__main__':
    detect_blob()
