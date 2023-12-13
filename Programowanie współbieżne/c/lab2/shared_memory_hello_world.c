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
        char list_a[5] = {'H', 'e', 'l', 'l', 'o'};
        int i;
        for(i=0; i<=sizeof(list_a); i++){
            sprintf(s, "%s%c", s, list_a[i]);
        }
        shmdt(shm);
    }else{
        shmid = shmget(2009, SHMSIZE, 0666|IPC_CREAT);
        shm = shmat(shmid, 0, 0);
        wait(NULL);
        char *s = (char *)shm;
        char list_b[7] = {' ', 'w', 'o', 'r', 'l', 'd', '!'};
        int i;
        for(i=0; i<=sizeof(list_b); i++){
            sprintf(s, "%s%c", s, list_b[i]);
        }
        strcat(s, "\n");
        printf("%s\n", shm);
        shmdt(shm);
        shmctl(shmid, IPC_RMID, NULL);
    }
    return 0;
}