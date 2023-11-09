#include <fcntl.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>
int main()
{
    int fd;
    char * myfifo = "/tmp/my_first_named_pipe";
    mkfifo(myfifo, 0666);
    fd = open(myfifo, O_WRONLY);
    char* buffer = "Hello world named pipe!";
    write(fd, buffer, sizeof("Hello world named pipe!"));
    close(fd);
    unlink(myfifo);
    return 0;
}