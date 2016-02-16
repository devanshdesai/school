#!/usr/bin/env python

import rospy
from std_msgs.msg import Empty
from std_msgs.msg import String

def resetter(data):
    pub = rospy.Publisher('/mobile_base/commands/reset_odometry', Empty, queue_size=10)
    rospy.init_node('resetter', anonymous=True)
    while pub.get_num_connections() == 0:
        pass
    pub.publish(Empty())

def setup():
    rospy.init_node('resetter', anonymous=True)
    rospy.Subscriber('reset_odom', String, resetter)
    rospy.spin()

if __name__ == '__main__':
    try:
        setup()
    except rospy.ROSInterruptException:
        pass
