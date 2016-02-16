#include <linux/init.h>
#include <linux/module.h>
#include <linux/kernel.h>	// printk()
#include <linux/slab.h> 	// kmalloc()
#include <linux/fs.h>		// everything
#include <linux/errno.h>	// error codes
#include <linux/types.h>	// ssize_t
#include <linux/fcntl.h>	// O_ACCMODE
#include <linux/miscdevice.h>
#include <asm/uaccess.h>	// copy_to_user copy_from_user

MODULE_LICENSE("GPL");

char * a;	// first operand (does not have to be a pointer)
char * b;	// second operand (does not have to be a pointer)

static ssize_t adder_read(struct file * file, char * buf, size_t count, loff_t *ppos)
{
	char outBuffer;

	if(count != sizeof(char))
	{
		return -EINVAL;
	}

	outBuffer = *a + *b;

	if (copy_to_user(buf, &outBuffer, 1))
	{
		return -EINVAL;
	}

	return 1;
}

static ssize_t adder_write(struct file * file, const char * buf, size_t count, loff_t *ppos)
{
	char inBuffer[2];

	if(count != 2)
	{
		return -EINVAL;
	}

	if(copy_from_user(inBuffer, buf, 2))
	{
		return -EINVAL;
	}

	*a = inBuffer[0];
	*b = inBuffer[1];

	return 2;
}

static const struct file_operations adder_file_operations = {
	.owner		= THIS_MODULE,
	.read		= adder_read,
	.write		= adder_write
};

static struct miscdevice adder_dev = {
	MISC_DYNAMIC_MINOR,
	"adder",
	&adder_file_operations
};

static int __init adder_init(void)
{
	int returnValue;

	a = kmalloc(1, GFP_KERNEL);
	if(a == NULL)
	{
		printk(KERN_ERR "Unable to allocate memory for a");
	}
	*a = 0;

	b = kmalloc(1, GFP_KERNEL);
	if(b == NULL)
	{
		printk(KERN_ERR "Unable to allocate memory for b");
	}
	*b = 0;

	returnValue = misc_register(&adder_dev);
	if (returnValue)
	{
		printk(KERN_ERR "Unable to register \"adder\" misc device\n");
	}

	return returnValue;
}

module_init(adder_init);

static void __exit adder_exit(void)
{
	misc_deregister(&adder_dev);
	kfree(a);
	kfree(b);
}

module_exit(adder_exit);

