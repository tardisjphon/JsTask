package js.task.data.remote.retrofit.converter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Inject;
import js.task.data.local.db.model.DailyMotionPage;
import js.task.data.local.db.model.DataModel;
import js.task.data.local.db.model.GithubPage;
import js.task.data.remote.retrofit.converter.model.ApiName;


//TODO

public class RemoteDataConverter
{
    @Inject
    public RemoteDataConverter() {}

    public List<DataModel> getDataModel(DailyMotionPage data)
    {
        int index = 0;
        return Objects.requireNonNull(data.getList()).stream().map (
                dailyMotion ->

            new DataModel(0,
                    index, //TODO przepisywalny
                    dailyMotion.getScreenname(),
                    "",
                    ApiName.DAILY_MOTION
            )).collect(Collectors.toList());

        //.sortedByDescending { it.apiName?.name } ?: ArrayList()
    }

    public List<DataModel> getDataModel(List<GithubPage> data)
    {
        return data.stream().map( mapping ->
            new DataModel(
                    0, //TODO nie wstawiac
                    mapping.getId(),
                    mapping.getLogin(),
                    mapping.getAvatar_url(),
                    ApiName.GITHUB
        )).collect(Collectors.toList());

        //.sortedByDescending { it.apiName?.name }
    }
}
