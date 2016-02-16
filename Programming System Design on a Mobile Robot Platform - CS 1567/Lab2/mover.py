#!/usr/bin/env python

import rospy
from geometry_msgs.msg import Twist

def send_commands():
    pub = rospy.Publisher('kobuki_command', Twist, queue_size=10)
    rospy.init_node('mover', anonymous=True)
    while pub.get_num_connections() == 0:
        pass
    command = Twist()
    command.linear.x = 0.5
    command.angular.z= 0.0
    for i in range(0,10):
        pub.publish(command)
        rospy.sleep(.5)
        i+=1
    command.linear.x = 0
    pub.publish(command)
    
if __name__ =='__main__':
    try:
        send_commands()
    except rospy.ROSInterruptException:
        pass
