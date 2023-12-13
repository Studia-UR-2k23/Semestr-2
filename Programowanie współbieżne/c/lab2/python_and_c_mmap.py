import mmap
import os
import random
import time

fname = './temp_mmap.txt'

if not os.path.isfile(fname):
    with open(fname, "wb+") as fd:
        fd.write('abcdef')

with open(fname, "rb+") as fd:
    mm = mmap.mmap(fd.fileno(), 6, access = mmap.ACCESS_WRITE, offset = 0)
    print(mm.read(6))
    counter = 0
    while True:
        index = random.randint(0, 5)
        mm[index] = 120
        time.sleep(1)
        mm.seek(0)
        print(mm.readline())
        counter += 1
        if counter == 5:
            break
mm.close()
fd.clode()