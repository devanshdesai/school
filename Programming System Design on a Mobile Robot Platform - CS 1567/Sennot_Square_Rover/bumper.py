#!/usr/bin/env python

import rospy
from kobuki_msgs.msg import BumperEvent

def bumperCallback(data):
    str = ""
    if data.bumper == 0:
        str = str + "Left bumper is "
    elif data.bumper == 1:
        str = str + "Center bumper is "
    else:
        str = str + "Right bumper is "

    if data.state == 0:
        str = str + "released."
	rospy.loginfo(str)
    else:
        str = str + "pressed."
	command.linear.x = 0.0
	command.linear.z = 0.0
	pub.publish(command)
    	rospy.loginfo(str)

def bumper():
    rospy.init_node('bumper', anonymous=True)
    rospy.Subscriber('/mobile_base/events/bumper', BumperEvent, bumperCallback)
    rospy.spin()

if __name__ == '__main__':
    bumper()
