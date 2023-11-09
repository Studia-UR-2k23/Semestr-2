#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <stdio.h>

int main(){
    char *myargs[3];
    myargs[0] = strdup("/usr/bin/wc");
    myargs[1] = strdup("exec_example.c");
    myargs[2] = NULL;
    execv(myargs[0], myargs);
    printf("%s", "I will never be printed!");
    exit(0);
}
