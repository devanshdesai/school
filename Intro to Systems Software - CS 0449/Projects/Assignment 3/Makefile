mytester: mymalloc.o mytester.o
	gcc -m32 -o mytester mytester.o mymalloc.o

mytester.o: mytester.c mymalloc.h
	gcc -m32 -c mytester.c

malloctest: mymalloc.o mallocdrv.o
	gcc -m32 -o malloctest mymalloc.o mallocdrv.o

mymalloc.o: mymalloc.c mymalloc.h
	gcc -m32 -c mymalloc.c

mallocdrv.o: mallocdrv.c mymalloc.h
	gcc -m32 -c mallocdrv.c

