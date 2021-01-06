package ccbus.desktop.worker;

public class WorkerError
{
    public String error = "";
    public int errno = 0;

    public WorkerError() {
    }

    public WorkerError(String error, int errno) {
        this.error = error;
        this.errno = errno;
    }

    public WorkerError(String error) {
        this.error = error;
        this.errno = 1;
    }
}
