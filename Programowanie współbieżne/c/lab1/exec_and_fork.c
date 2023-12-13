#include <unistd.h>
#include <string.h>
#include <sys/wait.h>

int main(){
    char *myargs[4];
    myargs[0] = strdup("git");
    myargs[1] = strdup("clone");
    myargs[2] = strdup("https://github.com/MarcinMrukowicz/CRUDDemoFront");
    myargs[3] = NULL;
    if(fork() == 0){
        execvp(myargs[0], myargs);
    }else{
        wait(NULL);
        chdir("CRUDDemoFront");
        myargs[0] = strdup("ls");
        myargs[1] = strdup("-l");
        myargs[2] = NULL;
        sleep(10);
        execvp(myargs[0], myargs);
    }
}