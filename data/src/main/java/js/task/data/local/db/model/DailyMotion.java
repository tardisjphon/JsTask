package js.task.data.local.db.model;


public class DailyMotion
{
    String id;
    String screenname;

    public String getId()
    {
        return id;
    }

    public String getScreenname()
    {
        return screenname;
    }

    public DailyMotion(
            String id,
            String screenname
    )
    {
        this.id         = id;
        this.screenname = screenname;
    }
}
