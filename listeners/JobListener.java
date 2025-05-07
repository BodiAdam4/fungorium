package listeners;

public interface JobListener {
    public void jobSuccessfull(String msg);
    public void jobFailed(String msg);
}
