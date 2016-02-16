/*
    DEVANSH DESAI
    CS 0449 - FALL 2015
    PROJECT 4 - deck_dev.c
*/

#include <linux/fs.h>
#include <linux/init.h>
#include <linux/miscdevice.h>
#include <linux/module.h>
#include <asm/uaccess.h>
#include <linux/kernel.h>
#include <linux/types.h>
#include <linux/errno.h>
#include <linux/slab.h>
#include <linux/random.h>

MODULE_LICENSE("GPL");

char *deck;
int *deckAt;

static ssize_t deck_read(struct file * file, char * buf, size_t count, loff_t *ppos)
{
    char outBuffer[2];

    if (*deckAt > 51)
    {
        return 0;
    }

    outBuffer[0] = deck[(*deckAt) * 2];
    outBuffer[1] = deck[((*deckAt) * 2) + 1];

    if (copy_to_user(buf, &outBuffer, 3))
    {
        return -EINVAL;
    }

    (*deckAt)++;

    return 2;
}

static ssize_t deck_write(struct file * file, char * buf, size_t count, loff_t *ppos)
{
    char inBuffer[1];
    int command;
    int i;
    int j;
    int deckCounter = 0;
    char suits[4] = {'S', 'H', 'D', 'C'};
    char temp[2];
    unsigned char c;

    if (count != 1)
    {
        return -EINVAL;
    }

    if (copy_from_user(inBuffer, buf, 1))
    {
        return -EINVAL;
    }

    command = inBuffer[0] - '0';

    if (command == 0)
    {
        *deckAt = 0;

        for (i = 0; i < 4; i++)
        {
            for (j = 2; j < 15; j++)
            {
                deck[deckCounter * 2] = j;
                deck[(deckCounter * 2) + 1] = suits[i];
                deckCounter++;
            }
        }
    }
    else if (command == 1)
    {
        *deckAt = 0;
        for (j = 0; j < 5; j++)
        {
            for (i = 0; i < 51; i++)
            {
                get_random_bytes(&c, 1);
                c = c % (51 - i);

                temp[0] = deck[(i + c) * 2];
                temp[1] = deck[((i + c) * 2) + 1];

                deck[(i + c) * 2] = deck[i * 2];
                deck[((i + c) * 2) + 1] = deck[(i * 2) + 1];

                deck[i * 2] = temp[0];
                deck[(i * 2) + 1] = temp[1];
            }
        }
    }
    else
    {
        return -EINVAL;
    }

    return 1;
}

static const struct file_operations deck_file_operations = {
    .owner = THIS_MODULE,
    .read = deck_read,
    .write = deck_write
};

static struct miscdevice deck_dev = {
    MISC_DYNAMIC_MINOR,
    "deck",
    &deck_file_operations
};

static int __init deck_init(void)
{
        int ret;
        int i;
        int j;
        int deckCounter = 0;
        char suits[4] = {'S', 'H', 'D', 'C'};

        deck = kmalloc(52*3, GFP_KERNEL);
        if (deck == NULL)
        {
            printk(KERN_ERR "Unable to allocate memory for deck");
        }

        deckAt = kmalloc(sizeof(int), GFP_KERNEL);
        if (deckAt == NULL)
        {
            printk(KERN_ERR "Unable to allocate memory for deckAt");
        }

        ret = misc_register(&deck_dev);
        if (ret)
        {
            printk(KERN_ERR "Unable to register \"deck\" misc device\n");
        }

        *deckAt = 0;
        for (i = 0; i < 4; i++)
        {
            for (j = 2; j < 15; j++)
            {
                deck[deckCounter * 2] = j;
                deck[(deckCounter * 2) + 1] = suits[i];
                deckCounter++;
            }
        }

        return ret;
}

module_init(deck_init);

static void __exit deck_exit(void)
{
    misc_deregister(&deck_dev);
    kfree(deck);
    kfree(deckAt);
}

module_exit(deck_exit);
