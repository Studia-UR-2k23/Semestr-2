#include <ncurses.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <unistd.h>
#include <signal.h>
#include <string.h>
#include <malloc.h>

#define BRIGHT_WHITE 15

char* local_buffer;

struct memory {
    char buff1[100];
    char buff2[100];
    int size, size2;
    int status, block, pid1, pid2;
};

struct memory* shared_memory;

void print_and_clear_screen_if_needed(char* s) {
    int ok = printf("%s", s);
    if (ok == ERR) {
        clear();
        printf("%s",s);
    }
}
void handler(int signum){
    if(signum == SIGUSR2){
        start_color();
        init_pair(1, COLOR_BLUE, COLORS > 15 ? 15 : COLOR_WHITE);
        init_pair(2, COLOR_BLACK, COLORS > 15 ? 15 : COLOR_WHITE);
        attron(COLOR_PAIR(1));
        int ok;
        print_and_clear_screen_if_needed("\nReceived From User1: ");
        printw("%s",shared_memory->buff1);
        attroff(COLOR_PAIR(1));
        attron(COLOR_PAIR(2));
        shared_memory->buff1[0] = '\0';
        memset(shared_memory->buff1, 0, 100);
        print_and_clear_screen_if_needed("\nUser2: ");
        printw("%s",local_buffer);
    }
}
int main(){
    int pid = getpid();
    int shmid;
    int key = 123456;
    shmid = shmget(key, sizeof(struct memory), IPC_CREAT | 0666);
    shared_memory = (struct memory*)shmat(shmid, NULL, 0);
    shared_memory->pid2 = pid;
    shared_memory->size2 = 0;
    signal(SIGUSR2, handler);
    WINDOW *w;
    char c;
    w = initscr();
    nodelay(w, 1);
    raw();
    noecho();
    wclrtoeol(w);
    local_buffer = malloc(100 * sizeof(char));
    int size = 0;
    printw("%s", "User2: ");
    for (;;) {
    c = getch();
    if (c == 'q' || (int) c == 27) {
        shmdt((void*)shared_memory);
        shmctl(shmid, IPC_RMID, NULL);
        break;
    }
    if (c != ERR) {
        if (c == KEY_BACKSPACE || c == 127) { // Obsługa klawisza Backspace
            if (size > 0) {
                mvdelch(getcury(w), getcurx(w) - 1); // Usuń znak z ekranu
                size--;
                local_buffer[size] = '\0'; // Usuń znak z lokalnego bufora
            }
        } else if (c != 10) {
            addch(c);
            local_buffer[size] = c;
            size++;
        }
    }
    if (c == 10) {
        local_buffer[size] = '\n';
        memcpy(shared_memory->buff2, local_buffer, size);
        shared_memory->size2 = 0;
        size = 0;
        memset(local_buffer, 0, 100);
        print_and_clear_screen_if_needed("\nUser2: ");
        kill(shared_memory->pid1, SIGUSR1);
    }
    refresh();
}
    refresh();
    endwin();
}