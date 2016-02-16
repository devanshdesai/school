#!/usr/bin/env python

import math

xball = abs(-0.8 * math.tan(35) / (math.tan(35) - math.tan(80)))
print xball
yball = abs(xball * math.tan(80))
print yball
print math.tan(180*3.1415/180)
