#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/mman.h>

int main(){
    int pid;
    static char a[6] = {'a', 'b', 'c', 'd', 'e', 'f'};
    static char *shared_variable;
    shared_variable = mmap(NULL, 
                           sizeof(char) * 6, 
                           PROT_READ | PROT_WRITE, 
                           MAP_SHARED | MAP_ANONYMOUS, 
                           -1, 0);
    shared_variable[0] = 'a';
    shared_variable[1] = 'b';
    shared_variable[2] = 'c';
    shared_variable[3] = 'd';
    shared_variable[4] = 'e';
    shared_variable[5] = 'f';
    if((pid = fork()) == -1){
        perror("fork() failed");
        exit(1);
    }
    if(pid == 0){
        printf("%s", "Child process has variable a = ");
        int i = 0;
        for(i=0; i<3; i++){
            shared_variable[i] = (char)97;
            a[i] = (char)97;
        }
        printf("%s\n", a);
        printf("%s", "Child process has shared variable = ");
        printf("%s\n", shared_variable);
    }else{
        wait(NULL);
        printf("%s", "Parent process has variable a = ");
        int i = 3;
        for(i=3; i<6; i++){
            shared_variable[i] = (char)98;
            a[i] = (char)98;
        }
        printf("%s\n", a);
        printf("%s", "Parent process has shared variable = ");
        printf("%s\n", shared_variable);
    }
    printf("%s", "Shared variable outside process block = ");
    printf("%s\n", shared_variable);
    exit(0);
}