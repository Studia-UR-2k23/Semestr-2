#include <ncurses.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <unistd.h>
#include <signal.h>
#include <string.h>
#include <malloc.h>

#define BRIGHT_WHITE 15

char* local_buffer;

struct memory{
    char buff1[100];
    char buff2[100];
    int size, size2;
    int status, block, pid1, pid2;
};

struct memory* shared_memory;

void print_and_clear_screen_if_needed(char* s){
    int ok = printw("%s",s);
    if (ok == ERR) {
        clear();
        printw("%s",s);
    }
}
void handler(int signum){
    if(signum == SIGUSR1){
        start_color();
        init_pair(1, COLOR_BLUE, COLORS > 15 ? 15 : COLOR_WHITE);
        init_pair(2, COLOR_BLACK, COLORS > 15 ? 15 : COLOR_WHITE);
        attron(COLOR_PAIR(1));
        int ok;
        print_and_clear_screen_if_needed("\nReceived From User2: ");
        print_and_clear_screen_if_needed(shared_memory->buff2);
        shared_memory->buff2[0] = '\0';
        memset(shared_memory->buff2, 0, 100);
        shared_memory->size2 = 0;
        attroff(COLOR_PAIR(1));
        attron(COLOR_PAIR(2));
        refresh();
        print_and_clear_screen_if_needed("\nUser1: ");
        printw("%s",local_buffer);
        refresh();
    }
}

int main() {
    int pid = getpid();
    int shmid;
    int key = 123456;
    shmid = shmget(key, sizeof(struct memory), IPC_CREAT | 0666);
    shared_memory = (struct memory*)shmat(shmid, NULL, 0);
    shared_memory->pid1 = pid;
    shared_memory->size = 0;
    signal(SIGUSR1, handler);
    WINDOW *w;
    char c;
    if (can_change_color() && COLORS >= 15) {
        init_color(BRIGHT_WHITE, 1000, 1000, 1000);
    }
    if (!can_change_color()) {
        printw("%s", "unfortunately, you can't change color!");
    }
    w = initscr();
    nodelay(w, 1);
    raw();
    noecho();
    wclrtoeol(w);
    printw("%s", "User1: ");
    local_buffer = malloc(100 * sizeof(char));
    int size = 0;
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
            refresh();
            local_buffer[size] = c;
            size++;
        }
    }
    if (c == 10) {
        local_buffer[size] = '\n';
        memcpy(shared_memory->buff1, local_buffer, size);
        size = 0;
        memset(local_buffer, 0, 100);
        int ok = addstr("\nUser1: ");
        while (ok == ERR) {
            clear();
            ok = addstr("\nUser1: ");
        }
        refresh();
        kill(shared_memory->pid2, SIGUSR2);
    }
}
    refresh();
    endwin();
}