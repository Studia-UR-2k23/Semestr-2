#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <sys/wait.h>

int main(){
    int pid;
    if((pid = fork()) == -1){
        perror("fork() failed");
        exit(1);
    }
    if(pid == 0){
        printf("Hello, ");
    }
    else{
        wait(NULL);
        printf("World!\n");
    }
    exit(0);
}
