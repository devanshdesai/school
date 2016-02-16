#include <linux/fs.h>
#include <linux/init.h>
#include <linux/miscdevice.h>
#include <linux/module.h>
#include <asm/uaccess.h>
#include <linux/time.h>

/*
 * mytime_read is the function called when a process calls read() on
 * /dev/mytime.  It writes "hh:mm:ss" to the buffer passed in the
 * read() call.
 */

static ssize_t mytime_read(struct file * file, char * buf, size_t count, loff_t *ppos)
{
	struct timespec ts;	// Time structure
	int hours = 0;
	int mins = 0;
	int secs = 0;
	char outBuffer[9];

	getnstimeofday(&ts);	// Get time

	if (*ppos != 0)
	{
		return 0;
	}

	secs = ts.tv_sec % 60;
	mins = (ts.tv_sec / 60) % 60;
	hours = (((ts.tv_sec) / 3600) - 5) % 24;

	if (hours < 10)
	{
		outBuffer[0] = 0 + '0';
		outBuffer[1] = hours + '0';
	}
	else
	{
		outBuffer[0] = ((hours - (hours % 10)) / 10) + '0';
		outBuffer[1] = (hours % 10) + '0';
	}

	outBuffer[2] = ':';

	if (mins < 10)
	{
		outBuffer[3] = 0 + '0';
		outBuffer[4] = mins + '0';
	}
	else
	{
		outBuffer[3] = ((mins - (mins % 10)) / 10) + '0';
		outBuffer[4] = (mins % 10) + '0';
	}

	outBuffer[5] = ':';

	if (secs < 10)
	{
		outBuffer[6] = 0 + '0';
		outBuffer[7] = secs + '0';
	}
	else
	{
		outBuffer[6] = ((secs - (secs % 10)) / 10) + '0';
		outBuffer[7] = (secs % 10) + '0';
	}

	outBuffer[8] = '\n';

	if (copy_to_user(buf, &outBuffer, 9))
	{
		return -EINVAL;
	}

	*ppos = 9;

	return 9;
}

/*
 * The only file operation we care about is read.
 */

static const struct file_operations mytime_fops = {
	.owner		= THIS_MODULE,
	.read		= mytime_read,
};

static struct miscdevice mytime_dev = {
	/*
	 * We don't care what minor number we end up with, so tell the
	 * kernel to just pick one.
	 */
	MISC_DYNAMIC_MINOR,
	/*
	 * Name ourselves /dev/mytime.
	 */
	"mytime",
	/*
	 * What functions to call when a program performs file
	 * operations on the device.
	 */
	&mytime_fops
};

static int __init mytime_init(void)
{
	int ret;

	/*
	 * Create the "mytime" device in the /sys/class/misc directory.
	 * Udev will automatically create the /dev/mytime device using
	 * the default rules.
	 */
	ret = misc_register(&mytime_dev);
	if (ret)
	{
		printk(KERN_ERR "Unable to register \"mytime\" misc device\n");
	}
	return ret;
}

module_init(mytime_init);

static void __exit mytime_exit(void)
{
	misc_deregister(&mytime_dev);
}

module_exit(mytime_exit);

MODULE_LICENSE("GPL");
