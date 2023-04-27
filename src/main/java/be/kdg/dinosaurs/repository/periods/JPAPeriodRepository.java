package be.kdg.dinosaurs.repository.periods;

import be.kdg.dinosaurs.domain.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Profile("jparepo")
public class JPAPeriodRepository implements PeriodRepository{

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Period createPeriod(Period period) {
        entityManager.persist(period);
        return period;
    }

    @Override
    public Period readByName(String name) {
        LOGGER.info("JPAPeriodRepository is getting period with name {} from PostgresSQL database", name);
        Query query = entityManager.createQuery("select p from Period p where LOWER(p.name) = :name");
        query.setParameter("name", name);
        Period period = (Period) query.getResultList().get(0);
        return period;
    }

    @Override
    public List<Period> readPeriods() {
        LOGGER.info("JPAPeriodRepository is getting all periods from PostgresSQL database");
        List<Period> periods = entityManager.createQuery("select period from Period period", Period.class).getResultList();
        return periods;
    }


}
