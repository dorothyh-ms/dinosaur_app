package be.kdg.dinosaurs.repository.database_creation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profile("jdbcbrepo")
public class H2DatabaseCreator {
    private JdbcTemplate jdbcTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public H2DatabaseCreator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void loadData(){
        LOGGER.info("H2DatabaseCreator creating tables digsites, periods, species, and discoveries");
        jdbcTemplate.update(
                "DROP TABLE IF EXISTS digsites CASCADE;" +
                "CREATE TABLE IF NOT EXISTS digsites" +
                "(" +
                "    digsite_id INTEGER auto_increment" +
                "        primary key" +
                "        unique," +
                "    digsite_name VARCHAR(30) NOT NULL," +
                "    country VARCHAR(56)," +
                "    latitude double," +
                "    longitude double," +
                "    first_excavation_date date" +
                ");" +
                "DROP TABLE IF EXISTS periods CASCADE;" +
                "CREATE TABLE IF NOT EXISTS periods" +
                "(" +
                "    period_id INTEGER auto_increment" +
                "        primary key" +
                "        unique," +
                "    period_name VARCHAR(20) NOT NULL," +
                "    start_millions_years double," +
                "    end_millions_years double" +
                ");" +
                "DROP TABLE IF EXISTS species CASCADE;" +
                "CREATE TABLE IF NOT EXISTS species" +
                "(" +
                "    species_id             INTEGER auto_increment" +
                "        primary key" +
                "        unique," +
                "    species_name                   VARCHAR(30) NOT NULL," +
                "    scientific_name        VARCHAR(50) NOT NULL," +
                "    diet                    VARCHAR(15) NOT NULL," +
                "    number_specimens_found NUMERIC(3)," +
                "    period_id              INTEGER," +
                "    foreign key (period_id) references periods (period_id)," +
                "    image_url              VARCHAR(300)" +
                ");" +
                "DROP TABLE IF EXISTS species_digsites CASCADE;" +
                "CREATE TABLE IF NOT EXISTS species_digsites" +
                "(" +
                "    species_id         INTEGER," +
                "    digsite_id         INTEGER," +
                "    foreign key (species_id) references species(species_id)," +
                "    foreign key (digsite_id) references digsites(digsite_id)," +
                "    primary key (species_id, digsite_id)" +
                ");");
        LOGGER.info("H2DatabaseCreator inserting data into digsites, periods, species, and discoveries");
        jdbcTemplate.update(
                "INSERT INTO periods(period_name, start_millions_years, end_millions_years) " +
                        "VALUES ('Cretaceous', 145.0, 100.5), " +
                        "       ('Jurassic', 201.3, 145.0), " +
                        "       ('Triassic', 251.902, 201.36); " +
                        " " +
                        "INSERT INTO species(species_name, " +
                        "                    scientific_name, " +
                        "                    number_specimens_found, " +
                        "                    diet, " +
                        "                    period_id, " +
                        "                    image_url) " +
                        "VALUES ('Iguanodon', 'Iguanodon bernissartensis', 38, 'HERBIVORE', " +
                        "        (SELECT period_id FROM periods WHERE LOWER(period_name) = 'cretaceous'), " +
                        "        'https://a-z-animals.com/media/2022/07/Iguanodon-header.jpg'), " +
                        "       ('Archeopteryx', 'Archaeopteryx lithographica', 12, 'CARNIVORE', " +
                        "        (SELECT period_id FROM periods WHERE LOWER(period_name) = 'jurassic'), " +
                        "        'https://upload.wikimedia.org/wikipedia/commons/thumb/9/9d/Archaeopteryx_lithographica_%28Berlin_specimen%29.jpg/1200px-Archaeopteryx_lithographica_%28Berlin_specimen%29.jpg'), " +
                        "       ('T. rex', 'Tyrannosaurus rex', 32, 'CARNIVORE', " +
                        "        (SELECT period_id FROM periods WHERE LOWER(period_name) = 'cretaceous'), " +
                        "        'https://media-cldnry.s-nbcnews.com/image/upload/rockcms/2021-04/210415-tyrannosaurus-rex-mn-1550-9612a9.jpg'), " +
                        "       ('Triceratops', 'Triceratops horridus', 166, 'HERBIVORE', " +
                        "        (SELECT period_id FROM periods WHERE LOWER(period_name) = 'cretaceous'), " +
                        "        'https://a-z-animals.com/media/2022/06/Triceratops.jpg'), " +
                        "       ('Ankylosaurus', 'Ankylosaurus magniventris', 3, 'HERBIVORE', " +
                        "        (SELECT period_id FROM periods WHERE LOWER(period_name) = 'cretaceous'), " +
                        "        'https://media.kidadl.com/Did_You_Know_21_Incredible_Ankylosaurus_Facts_8dacbb4e54.jpg'), " +
                        "       ('Sauropod', 'Patagotitan mayorum', 150, 'HERBIVORE', " +
                        "        (SELECT period_id FROM periods WHERE LOWER(period_name) = 'cretaceous'), " +
                        "        'https://upload.wikimedia.org/wikipedia/commons/thumb/a/aa/Patagotitan-Scale-Diagram-Steveoc86.svg/400px-Patagotitan-Scale-Diagram-Steveoc86.svg.png'), " +
                        "       ('Plesiosaur', 'Plesiosaurus dolichodeirus', 114, 'CARNIVORE', " +
                        "        (SELECT period_id FROM periods WHERE LOWER(period_name) = 'triassic'), " +
                        "        'https://www.newdinosaurs.com/wp-content/uploads/2016/11/1362_plesiosaurus_esther_van_hulsen.jpg')," +
                        "       ('Stegosaur', 'Stegosaurus stenops', 80, 'HERBIVORE', " +
                        "        (SELECT period_id FROM periods WHERE LOWER(period_name) = 'jurassic'), " +
                        "         'https://upload.wikimedia.org/wikipedia/commons/thumb/d/d8/Stegosaurus_stenops_Life_Reconstruction.png/1920px-Stegosaurus_stenops_Life_Reconstruction.png');" +
                        " " +
                        " " +
                        " " +
                        "INSERT INTO digsites(digsite_name, country, latitude, longitude, first_excavation_date) " +
                        "VALUES ('Willow Creek Formation', 'Canada', 49.8, -113.4, DATE '1861-05-01'), " +
                        "       ('Hell Creek Formation', 'USA', 46.9, -101.5, DATE '1861-09-30'), " +
                        "       ('Bernissart Mine', 'Belgium', 50.5, 3.6, DATE '1902-01-30'), " +
                        "       ('Blumenberg Quarry', 'Germany', 49.0, 11.2, DATE '1889-04-26'), " +
                        "       ('Cerro Barcino Formation', 'Argentina', -43.8, -68.6, DATE '1902-6-14'), " +
                        "       ('La Sagnette', 'Belgium', 49.7, 5.7, DATE '2008-09-23'), " +
                        "       ('Morrison Formation', 'USA', 39.7, -105.2, DATE '1877-04-10'), " +
                        "       ('Casal Novo', 'Portugal', 39.7, 8.8, DATE '1999-10-03'); " +
                        "INSERT INTO species_digsites(species_id, digsite_id) " +
                        "VALUES ((SELECT species_id FROM species WHERE LOWER(species_name)='triceratops'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'Hell Creek Formation')), " +
                        "       ((SELECT species_id FROM species WHERE LOWER(species_name)='t. rex'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'Hell Creek Formation')), " +
                        "       ((SELECT species_id FROM species WHERE LOWER(species_name)='t. rex'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'Willow Creek Formation')), " +
                        "       ((SELECT species_id FROM species WHERE LOWER(species_name)='iguanodon'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'Bernissart Mine')), " +
                        "       ((SELECT species_id FROM species WHERE LOWER(species_name)='ankylosaurus'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'Hell Creek Formation')), " +
                        "       ((SELECT species_id FROM species WHERE LOWER(species_name)='archeopteryx'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'Blumenberg Quarry')), " +
                        "       ((SELECT species_id FROM species WHERE LOWER(species_name)='sauropod'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'Cerro Barcino Formation')), " +
                        "       ((SELECT species_id FROM species WHERE LOWER(species_name)='plesiosaur'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'La Sagnette')), " +
                        "       ((SELECT species_id FROM species WHERE LOWER(species_name)='stegosaur'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'Morrison Formation')), " +
                        "       ((SELECT species_id FROM species WHERE LOWER(species_name)='stegosaur'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'Casal Novo'));");
    }

}
