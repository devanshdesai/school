#!/usr/bin/env python

import rospy
from std_msgs.msg import String

pub = rospy.Publisher('command_feed', String, queue_size=10)


'''
        get_mode() asks the user for the type of mode he/she would like and calls
        itself if the user does not enter a valid input. After a valid input is given
        the command(s) is published to 'command_feed.'
'''
def get_mode(data):
    mode = raw_input("Select mode (1 for single, 2 for multiple): ")
    if mode == "1":
        commandstr = raw_input("Enter a command and press 'ENTER' to execute: ")
        pub.publish(commandstr)
    elif mode == "2":
        commandstr = raw_input("Enter a series of commands and press 'ENTER' to execute: ")
        pub.publish(commandstr)
    else:
        print "Please enter a valid command mode (1 for single, 2 for multiple)"
        get_mode()
    waiting()

def waiting():
    print "Waiting for movement function"
    rospy.spin()

def setup():
    rospy.init_node('main', anonymous=True)
    rospy.Subscriber('new_command', String, get_mode())
    waiting()
    

if __name__ =='__main__':
    try:
        setup()
    except rospy.ROSInterruptException:
        pass
