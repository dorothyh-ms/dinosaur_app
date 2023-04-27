package be.kdg.dinosaurs.repository.periods;

import be.kdg.dinosaurs.domain.Period;

import java.util.List;

public interface PeriodRepository {

    public Period createPeriod(Period period);

    public Period readByName(String name);

    public List<Period> readPeriods();
}
