#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/types.h>
#include <unistd.h>
#define FILLED 0
#define Ready 1
#define NotReady (-1)

struct memory {
 char buff[100];
 int status, pid1, pid2;
};

struct memory* shared_memory;

void handler(int signum)
{
 if (signum == SIGUSR1) {
   printf("Received User2: ");
   puts(shared_memory->buff);
 }
}

int main()
{
 int pid = getpid();
 int shmid;
 int key = 12345;

 shmid = shmget(key, sizeof(struct memory), IPC_CREAT | 0666);
 shared_memory = (struct memory*)shmat(shmid, NULL, 0);
 shared_memory->pid1 = pid;
 shared_memory->status = NotReady;
 signal(SIGUSR1, handler);

 while (1) {
   printf("User1: ");
   fgets(shared_memory->buff, 100, stdin);
   if (strcmp(shared_memory->buff, "q\n") == 0) break;
   shared_memory->status = FILLED;
   kill(shared_memory->pid2, SIGUSR2);
 }
 shmdt((void*)shared_memory);
 shmctl(shmid, IPC_RMID, NULL);
 return 0;
}
