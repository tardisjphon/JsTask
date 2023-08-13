package js.task.data.local.db.model;

import java.util.List;

public class DailyMotionPage
{
    Integer page;
    Integer limit;
    Boolean explicit;
    Integer total;
    Boolean has_more;
    List<DailyMotion> list;

    public Integer getPage()
    {
        return page;
    }

    public Integer getLimit()
    {
        return limit;
    }

    public Boolean getExplicit()
    {
        return explicit;
    }

    public Integer getTotal()
    {
        return total;
    }

    public Boolean getHasMore()
    {
        return has_more;
    }

    public List<DailyMotion> getList()
    {
        return list;
    }
}
