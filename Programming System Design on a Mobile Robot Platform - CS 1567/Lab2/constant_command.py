#!/usr/bin/env python

import rospy
from geometry_msgs.msg import Twist

pub = rospy.Publisher('/mobile_base/commands/velocity', Twist, queue_size=10)

def setup():
    rospy.Subscriber('kobuki_command', Twist, send)
    rospy.init_node('constant_command', anonymous=True)
    rospy.spin()
    
def send(data):
    command = Twist()
    command.linear.x = data.linear.x
    command.angular.z= data.angular.z
    pub.publish(command)
    
if __name__ =='__main__':
    try:
        setup()
    except rospy.ROSInterruptException:
        pass
