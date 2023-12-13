#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

char global_arrays[6] = {'a', 'b', 'c', 'd', 'e', 'f'};

int main(){
    int pid;
    char a[6] = {'a', 'b', 'c', 'd', 'e', 'f'};
    if ((pid = fork()) == -1){
        perror("fork() failed");
        exit(1);
    }
    if(pid == 0){
        printf("Child process has variable a = ");
        int i;
        for(i=0; i<3; i++){
            global_arrays[i] = 'a';
            a[i] = 'a';
        }
        printf("%s\n", a);
    }else{
        printf("Parent process has variable a = ");
        int i;
        for(i=3; i<6; i++){
            global_arrays[i] = 'b';
            a[i] = 'b';
        }
        printf("%s\n", a);
    }
}