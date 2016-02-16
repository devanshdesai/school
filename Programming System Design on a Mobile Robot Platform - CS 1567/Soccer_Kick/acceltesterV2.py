#!/usr/bin/env python

import rospy
from geometry_msgs.msg import Twist
import math
from nav_msgs.msg import Odometry
from tf.transformations import euler_from_quaternion
from std_msgs.msg import String
from kobuki_msgs.msg import BumperEvent

currentDistance = 0.0
resetOdom = rospy.Publisher('reset_odom', String, queue_size=10)
pub = rospy.Publisher('/mobile_base/commands/velocity', Twist, queue_size=10)
commandCountTotal = 0
commandCountCurrent = 0
maxDistance = 0.0
maxSpeed = 0.0
turn = False
maxAngle = 0.0
currentAngle = 0.0
frontOrRight = True
x = []
bumperPressed = False
waitingForCommand = False

def nextCommand():
    global commandCountCurrent, x, commandCountCurrent, maxDistance, maxSpeed, turn, maxAngle, currentAngle, frontOrRight, waitingForCommand
    waitingForCommand = False
    if(commandCountCurrent < commandCountTotal):
        print x
        print "commandCountCurrent = %d" %(commandCountCurrent)
        print "commandCountTotal = %d" %(commandCountTotal)
        if x[commandCountCurrent][0] == "F":
            maxSpeed = float(x[commandCountCurrent][1])
            maxDistance = float(x[commandCountCurrent][2])
            turn = False
            frontOrRight = True
        elif x[commandCountCurrent][0] == "B":
            maxSpeed = -1 * float(x[commandCountCurrent][1])
            maxDistance = float(x[commandCountCurrent][2])
            turn = False
            frontOrRight = False
        elif x[commandCountCurrent][0] == "R":
            maxSpeed = -1 * float(x[commandCountCurrent][1])
            maxAngle = float(x[commandCountCurrent][2])
            turn = True
            frontOrRight = True
        else:
            maxSpeed = float(x[commandCountCurrent][1])
            maxAngle = float(x[commandCountCurrent][2])
            turn = True
            frontOrRight = False

        print "maxSpeed = %f" %(maxSpeed)
        print "maxDistance = %f" %(maxDistance)
        commandCountCurrent += 1
        send_commands()

    else:
        #Call for more user input
        waiting()

def setMovement(commandstr):
    global x, commandCountTotal, commandCountCurrent, maxDistance, maxSpeed, turn, maxAngle, currentAngle, frontOrRight
    splitString = str(commandstr)
    comm = splitString.split(',')
    commandCountTotal = len(comm)
    commandCountCurrent = 0
    x = [[0 for j in range(commandCountTotal)] for j in range(commandCountTotal)]
    for i in range(0,len(comm)):
        x[i] = comm[i].split(' ')

    nextCommand()

def odomCallback(data):
    # Convert quaternion to degree
    global currentDistance, currentAngle, bumperPressed, commandCountCurrent, commandCountTotal
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

def send_commands():
    global currentDistance, commandCountTotal, commandCountCurrent, currentAngle, maxAngle, maxDistance, angleToStartSlowing
    currentDistance = 0.0
    #Reset Odom before each move
    resetOdom.publish("")
    rospy.sleep(1.0)
    #Define variables
    if maxDistance > 1.0:
        startSlowing = maxDistance - 0.6
    elif maxDistance > 0.75:
        startSlowing = 0.38 * maxDistance
    elif maxDistance > 0.5:
        startSlowing = .30 * maxDistance
    elif maxDistance > 0.25:
        startSlowing = .2 * maxDistance
    else:
        startSlowing = 0.05 * maxDistance
	if maxAngle > 60:
		angleToStartSlowing = maxAngle - 40
	elif maxAngle > 45:
		angleToStartSlowing = 0.4 * maxAngle	
	elif maxAngle > 30:
		angleToStartSlowing = 0.3 * maxAngle
	else:
		angleToStartSlowing = .1 * maxAngle
    i = 0
    startedToSlow = False # Horizontal
    startedSlowing = False # Angluar
    command = Twist()
    command.linear.x = 0.0
    command.angular.z= 0.0

    # Check to see if the command is F/B or L/R
    if turn == False:

        #Check to see if it is F or B
        if frontOrRight == True:
            #Loop until we reach the max distance

            while (currentDistance < maxDistance):
                #Start slowing down once we reach 1/2 of max distance
                if (currentDistance > startSlowing):
                    if(startedToSlow == False):
                        i = 10
                        startedToSlow = True
                    command.linear.x = (i) / 10.0
                    i-=1
                    #If we are going 0 before we reach destination, set speed to .05
                    if (command.linear.x <= 0.05):
                        command.linear.x = .05
                else:
                    command.linear.x = i/25.0
                    i+=1
                #Check if the speed is greater than user defined max
                if (command.linear.x > maxSpeed):
                    command.linear.x = maxSpeed
                pub.publish(command)
                rospy.sleep(0.1)
                rospy.loginfo ("X = %.2f" % (currentDistance))
                if bumperPressed == True:
                    commandCountCurrent = commandCountTotal + 1
                    currentDistance = maxDistance
                    nextCommand()

        #If we're moving backwards
        else:
            #Loop until we reach the max distance
            while (abs(currentDistance) < maxDistance):
                #Start slowing down once we reach 1/2 of max distance
                if (abs(currentDistance) > startSlowing):
                    if(startedToSlow == False):
                        i = 10
                        startedToSlow = True
                    command.linear.x = (i) * -1 / 10.0
                    i-=1
                    #If we are going 0 before we reach destination, set speed to .05
                    if (command.linear.x >= -0.05):
                        command.linear.x = -.05
                else:
                    command.linear.x = i * -1 /25.0
                    i+=1
                #Check if the speed is greater than user defined max
                if (command.linear.x < maxSpeed):
                    command.linear.x = maxSpeed
                pub.publish(command)
                rospy.sleep(0.1)
                rospy.loginfo ("X = %.2f" % (currentDistance))

    #If we're rotating left or right
    else:
        #If we're rotating left
        if frontOrRight == False:
            while (currentAngle < maxAngle):
                if (currentAngle > angleToStartSlowing):
                    if (startedSlowing == False):
                        i = 15
                        startedSlowing = True
                    command.angular.z = i/ 15.0
                    i-=1
                    if (command.angular.z <= 0.2):
                        command.angular.z = 0.2
                else:
                    command.angular.z = i/25.0
                    i+=1

                if (command.angular.z > maxSpeed):
                    command.angular.z = maxSpeed
                pub.publish(command)
                rospy.sleep(0.1)
                rospy.loginfo("Angle = %.2f" % (currentAngle))
                if bumperPressed == True:
                    commandCountCurrent = commandCountTotal + 1
                    currentAngle = maxAngle
                    nextCommand()

        #If we're rotating right
        else:
            while (abs(currentAngle) < maxAngle):
                if (abs(currentAngle) > angleToStartSlowing):
                    if (startedSlowing == False):
                        i = 15
                        startedSlowing = True
                    command.angular.z = i*-1/ 15.0
                    i-=1
                    if (command.angular.z >= -0.2):
                        command.angular.z = -0.2
                else:
                    command.angular.z = i*-1/25.0
                    i+=1

                if (command.angular.z < maxSpeed):
                    command.angular.z = maxSpeed
                pub.publish(command)
                rospy.sleep(0.1)
                rospy.loginfo("Angle = %.2f" % (currentAngle))
                if bumperPressed == True:
                    commandCountCurrent = commandCountTotal + 1
                    currentAngle = maxAngle
                    nextCommand()

    command.linear.x = 0
    command.angular.z = 0
    pub.publish(command)
    nextCommand()

def bumperCallback(data):
    global bumperPressed
    command = Twist()
    command.linear.x = 0
    command.angular.z = 0
    pub.publish(command)

    str = ""
    if data.bumper == 0:
        str = str + "Left bumper is "
    elif data.bumper == 1:
        str = str + "Center bumper is "
    else:
        str = str + "Right bumper is "

    if data.state == 0:
        str = str + "released."
        bumperPressed = False
    else:
        str = str + "pressed."
        bumperPressed = True

    if waitingForCommand == False:
        rospy.loginfo(str)

def waiting():
    global waitingForCommand
    print "Waiting for command"
    waitingForCommand = True
    mode = raw_input("Select mode (1 for single, 2 for multiple): ")
    if mode == "1":
        commandstr = raw_input("Enter a command and press 'ENTER' to execute: ")
        setMovement(commandstr)
    elif mode == "2":
        commandstr = raw_input("Enter a series of commands and press 'ENTER' to execute: ")
        setMovement(commandstr)
    else:
        print "Please enter a valid command mode (1 for single, 2 for multiple)"
        waiting()

def setup():
    rospy.Subscriber('/odom', Odometry, odomCallback)
    rospy.Subscriber('command_feed', String, setMovement)
    rospy.Subscriber('/mobile_base/events/bumper', BumperEvent, bumperCallback)
    rospy.init_node('acceltester', anonymous=True)
    while pub.get_num_connections() == 0:
        pass
    waiting()

if __name__ =='__main__':
    try:
        setup()
    except rospy.ROSInterruptException:
        pass
