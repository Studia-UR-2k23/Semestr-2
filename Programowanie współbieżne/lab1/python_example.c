#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>

int main(void){
    int p[2];
    pipe(p);
    pid_t process1;
    if((process1 = fork()) == 0){
        dup2(p[1], STDOUT_FILENO);
        close(p[0]);
        execlp("python3", "python3", "-c", "for i in range(10): print(i)", NULL);
        perror("execlp python failed");
    }else if(process1 == -1){
        exit(1);
    }
    close(p[1]);
    char buf[1024];
    int count = 0;
    while(1){
        ssize_t bRead = read(p[0], buf, sizeof(buf));
        if(bRead == 0){
            break;
        }
        if(bRead == -1){
            perror("pipe read failed");
            exit(1);
        }
        int i;
        for(i=0; i<bRead; i++){
            if(buf[i] >= '0' && buf[i] <= '9'){
                int num = buf[i] - '0';
                if(num % 2 == 0){
                    count++;
                }
            }
        }
    }
    close(p[0]);
    wait(NULL);
    printf("Ilość liczb parzystych na wyjściu procesu Python: %d\n", count);
    return 0;
}
