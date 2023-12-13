#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <sys/wait.h>

void parent_have_executed(int signum){}

int main(){
    int pid;
    if((pid = fork()) == -1){
        perror("fork() failed");
        exit(1);
    }
    if(pid == 0){
        signal(SIGUSR1, parent_have_executed);
        pause();
        printf("World!\n");
        fflush(stdout);
    }else{
        printf("Hello, ");
        fflush(stdout);
        kill(pid, SIGUSR1);
        wait(NULL);
    }
    exit(0);
}