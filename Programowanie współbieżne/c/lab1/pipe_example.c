#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>

int main(void){
    char *param1[] = {"ls", NULL};
    char *param2[] = {"wc", NULL};
    int p[2];
    pipe(p);
    pid_t process1, process2;
    if((process1 = fork()) == 0){
        dup2(p[1], STDOUT_FILENO);
        close(p[0]);
        execvp("ls", param1);
        perror("execvp ls failed");
    }else if(process1 == -1){
        exit(1);
    }
    close(p[1]);
    if((process2 = fork()) == 0){
        dup2(p[0], STDIN_FILENO);
        close(p[1]);
        execvp(param2[0], param2);
        perror("execvp wc failed");
    }else if(process2 == -1){
        close(p[0]);
        wait(NULL);
        exit(1);
    }
    close(p[0]);
    wait(NULL);
    wait(NULL);
}