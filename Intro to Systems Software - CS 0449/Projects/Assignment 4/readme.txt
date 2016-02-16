/*
    DEVANSH DESAI
    CS 0449 - FALL 2015
    PROJECT 4 - readme.txt
*/

Requirements of running on the QEMU virtual machine:
Do not try to do "cat /dev/deck" after installing the device. This causes
the virtual machine to act very weirdly on my computer and I cannot do anything else.
Weird characters start coming up and you cannot run the poker file afterwards. All you
need to do is "insmod deck_dev.ko","cd /dev", and "mknod deck c 10 63". Stop after these
steps or otherwise the poker file will not run properly.
