package js.task.domain.usecase.model;


public class DomainModel
{
    private final Integer id;
    private final String userName;
    private final String imageUrl;
    private final String apiName;

    public DomainModel(
            Integer id,
            String userName,
            String imageUrl,
            String apiName
    )
    {
        this.id       = id;
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.apiName  = apiName;
    }

    public Integer getId()
    {
        return id;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public String getApiName()
    {
        return apiName;
    }
}
