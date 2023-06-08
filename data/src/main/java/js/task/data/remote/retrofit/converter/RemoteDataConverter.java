package js.task.data.remote.retrofit.converter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.inject.Inject;

import js.task.data.local.db.model.DailyMotionPage;
import js.task.data.local.db.model.DataModel;
import js.task.data.local.db.model.GithubPage;
import js.task.data.remote.retrofit.converter.model.ApiName;


public class RemoteDataConverter {
    @Inject
    public RemoteDataConverter() {
    }

    public List<DataModel> getDataModel(DailyMotionPage data) {
        if (data.getList() == null) {
            return new ArrayList<>();
        }

        AtomicInteger index = new AtomicInteger();
        return data.getList().stream().map(
                dailyMotion ->
                        new DataModel(
                                null,
                                index.getAndIncrement(),
                                dailyMotion.getScreenname(),
                                "",
                                ApiName.DAILY_MOTION
                        )).sorted(getApiNameComparator()).collect(Collectors.toList());
    }

    public List<DataModel> getDataModel(List<GithubPage> data) {
        return data.stream().map(mapping ->
                new DataModel(
                        null,
                        mapping.getId(),
                        mapping.getLogin(),
                        mapping.getAvatar_url(),
                        ApiName.GITHUB
                )).sorted(getApiNameComparator()).collect(Collectors.toList());
    }

    private Comparator<DataModel> getApiNameComparator() {
        return Comparator.comparing(m -> Objects.requireNonNull(m.getApiName()).name());
    }
}
