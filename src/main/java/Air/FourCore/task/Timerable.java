package Air.FourCore.task;

public abstract class Timerable {

    public long timer = 0L;

    public abstract void tick();
    public abstract void end();

}
