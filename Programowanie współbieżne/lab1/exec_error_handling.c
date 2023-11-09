#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <stdio.h>
#include <errno.h>

int main(){
    char *myargs[3];
    myargs[0] = strdup("wc");
    myargs[1] = strdup("exec_example.c");
    myargs[2] = NULL;
    int status = execv(myargs[0], myargs);
    printf("%s %i\n", "Status of executing process: ", status);
    if(status == -1){
        int errsv = errno;
        if(errsv == ENOENT){
            printf("%s%s\n","The is no such file as", myargs[0]);
        }
    }
    printf("%s\n", "I winn be printed, because exec will fail.");
    exit(0);
}