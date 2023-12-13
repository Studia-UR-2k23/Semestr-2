#include <sys/ipc.h>
#include <sys/shm.h>
#include <stdio.h>
#include <sys/wait.h>
#include <unistd.h>
#include <string.h>
#define SHMSIZE 27

int main(){
    int shmid;
    char *shm;
    if(fork() == 0){
        shmid = shmget(2009, SHMSIZE, 0666|IPC_CREAT);
        shm = shmat(shmid, 0, 0);
        char *s = (char *)shm;
        *s = '\0';
        int i;
        for(i=0; i<3; i++){
            sprintf(s, "%s%c", s, 'a');
        }
        printf("Child append %s\n", shm);
        shmdt(shm);
    }else{
        shmid = shmget(2009, SHMSIZE, 0666|IPC_CREAT);
        shm = shmat(shmid, 0, 0);
        //wait(NULL);
        printf("Parent reads %s\n", shm);
        char *s = (char *)shm;
        int i;
        for(i=0; i<3; i++){
            sprintf(s, "%s%c", s, 'b');
        }
        strcat(s, "\n");
        printf("Parent append %s\n", shm);
        shmdt(shm);
        shmctl(shmid, IPC_RMID, NULL);
    }
    return 0;
}