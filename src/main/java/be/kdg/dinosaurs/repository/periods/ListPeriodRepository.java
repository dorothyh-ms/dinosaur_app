package be.kdg.dinosaurs.repository.periods;

import be.kdg.dinosaurs.domain.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("listrepo")
public class ListPeriodRepository implements PeriodRepository{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final List<Period> periods = new ArrayList<>();

    public Period createPeriod(Period period){
        period.setId(periods.size());
        LOGGER.info("ListPeriodRepository is adding period {} to its list of periods", period);
        periods.add(period);
        return period;
    }

    @Override
    public Period readByName(String name) {
        LOGGER.info("ListPeriodRepository is getting period with name {} from its list of periods", name);
        return periods.stream().filter(p -> p.getName().equalsIgnoreCase(name) ).findFirst().get();
    }

    @Override
    public List<Period> readPeriods() {
        LOGGER.info("ListPeriodRepository is getting all periods from its list of periods");
        return periods;
    }
}
