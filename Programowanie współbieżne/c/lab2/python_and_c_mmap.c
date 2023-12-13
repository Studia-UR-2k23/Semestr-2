#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/mman.h>
#include <fcntl.h>
#include <string.h>

int main(){
    static char* shared_variable;
    int fd = open("./temp_mmap.txt", O_RDWR | O_CREAT, (mode_t)0600);
    shared_variable = (char*) mmap(NULL, 
                                   sizeof(char)*6, 
                                   PROT_READ | PROT_WRITE, 
                                   MAP_SHARED, fd, 0);
    shared_variable[0] = 'a';
    shared_variable[1] = 'b';
    shared_variable[2] = 'c';
    shared_variable[3] = 'd';
    shared_variable[4] = 'e';
    shared_variable[5] = 'f';
    int i = 0;
    while(i < 50){
        printf("%c%c%c%c%c%c\n", shared_variable[0], 
                                 shared_variable[1], 
                                 shared_variable[2],
                                 shared_variable[3],
                                 shared_variable[4],
                                 shared_variable[5]);
    }
    munmap(shared_variable, 6);
    close(fd);
}