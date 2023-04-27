INSERT INTO application_user(username, password, user_role) VALUES ('hans', '$2a$10$lb295CjmnGSIydJJgOSLH.qSaWXZc.egxk6U83mZvgKkJ23qiIoSm', 0);
INSERT INTO application_user(username, password, user_role) VALUES ('lars', '$2a$10$.SdAcWjIQik7XHf1rGMZDe0x.e53tpeaW7z9up9xYp.pOLi9Ky.sS', 0);
INSERT INTO application_user(username, password, user_role) VALUES ('toni', '$2a$10$lAFibg6.SD2DTaVIEiPvHunW5NrpfmYrO44ynyTOiylm1U1P0yplS', 0);
INSERT INTO application_user(username, password, user_role) VALUES ('piet', '$2a$10$Xr8hi7MFpFlQA3Oo016zhOSTDnsvf6tzTSWkiAzWNkcyblmV3jV9G', 1);
INSERT INTO application_user(username, password, user_role) VALUES ('jeroen', '$2a$10$5KSqiNWeLBTM9R9XiGruBu0aY/mmajGaXb5uoR/EndU6Q.D9cCk52', 1);
INSERT INTO application_user(username, password, user_role) VALUES ('rony', '$2a$10$ZTWofWOKSYTvXFJ0whodfOAp1UFDuEEa3WHhSawTmcuM8BRzke7SC', 1);
INSERT INTO application_user(username, password, user_role) VALUES ('jan', '$2a$10$CV/2kpdwgto2qvKoYps8huSca0ge36EdKjcPYWHR0rjfzz/Q1R.u2', 2);
INSERT INTO application_user(username, password, user_role) VALUES ('wim', '$2a$10$U/kiuiPDSlu.GboRLBYEkuwBvs/QN4prtDX0RRxscT8fzARCp0YDm', 2);

INSERT INTO periods (period_name, start_millions_years, end_millions_years) VALUES ('Cretaceous', 145.0, 100.5);
INSERT INTO periods (period_name, start_millions_years, end_millions_years) VALUES ('Jurassic', 201.3, 145.0);
INSERT INTO periods (period_name, start_millions_years, end_millions_years) VALUES ('Triassic', 251.902, 201.36);

INSERT INTO species (species_name, scientific_name, number_specimens_found, diet, period_id, image_url) VALUES ('Iguanodon', 'Iguanodon bernissartensis', 38, 'HERBIVORE', (SELECT period_id FROM periods WHERE LOWER(period_name) = 'cretaceous'), 'https://a-z-animals.com/media/2022/07/Iguanodon-header.jpg');
INSERT INTO species (species_name, scientific_name, number_specimens_found, diet, period_id, image_url) VALUES ('Archeopteryx', 'Archaeopteryx lithographica', 12, 'CARNIVORE', (SELECT period_id FROM periods WHERE LOWER(period_name) = 'jurassic'), 'https://upload.wikimedia.org/wikipedia/commons/thumb/9/9d/Archaeopteryx_lithographica_%28Berlin_specimen%29.jpg/1200px-Archaeopteryx_lithographica_%28Berlin_specimen%29.jpg');
INSERT INTO species (species_name, scientific_name, number_specimens_found, diet, period_id, image_url) VALUES ('T. rex', 'Tyrannosaurus rex', 32, 'CARNIVORE', (SELECT period_id FROM periods WHERE LOWER(period_name) = 'cretaceous'), 'https://media-cldnry.s-nbcnews.com/image/upload/rockcms/2021-04/210415-tyrannosaurus-rex-mn-1550-9612a9.jpg');
INSERT INTO species (species_name, scientific_name, number_specimens_found, diet, period_id, image_url) VALUES ('Triceratops', 'Triceratops horridus', 166, 'HERBIVORE', (SELECT period_id FROM periods WHERE LOWER(period_name) = 'cretaceous'), 'https://a-z-animals.com/media/2022/06/Triceratops.jpg');
INSERT INTO species (species_name, scientific_name, number_specimens_found, diet, period_id, image_url) VALUES ('Ankylosaurus', 'Ankylosaurus magniventris', 3, 'HERBIVORE', (SELECT period_id FROM periods WHERE LOWER(period_name) = 'cretaceous'), 'https://media.kidadl.com/Did_You_Know_21_Incredible_Ankylosaurus_Facts_8dacbb4e54.jpg');
INSERT INTO species (species_name, scientific_name, number_specimens_found, diet, period_id, image_url) VALUES ('Sauropod', 'Patagotitan mayorum', 150, 'HERBIVORE', (SELECT period_id FROM periods WHERE LOWER(period_name) = 'cretaceous'), 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/aa/Patagotitan-Scale-Diagram-Steveoc86.svg/400px-Patagotitan-Scale-Diagram-Steveoc86.svg.png');
INSERT INTO species (species_name, scientific_name, number_specimens_found, diet, period_id, image_url) VALUES ('Plesiosaur', 'Plesiosaurus dolichodeirus', 114, 'CARNIVORE', (SELECT period_id FROM periods WHERE LOWER(period_name) = 'triassic'), 'https://www.newdinosaurs.com/wp-content/uploads/2016/11/1362_plesiosaurus_esther_van_hulsen.jpg');
INSERT INTO species (species_name, scientific_name, number_specimens_found, diet, period_id, image_url) VALUES ('Stegosaur', 'Stegosaurus stenops', 80, 'HERBIVORE', (SELECT period_id FROM periods WHERE LOWER(period_name) = 'jurassic'), 'https://upload.wikimedia.org/wikipedia/commons/thumb/d/d8/Stegosaurus_stenops_Life_Reconstruction.png/1920px-Stegosaurus_stenops_Life_Reconstruction.png');
INSERT INTO species (species_name, scientific_name, number_specimens_found, diet, period_id, image_url) VALUES ('Imaginary Dinosaur', 'Imaginary Dinosaur', 1, 'HERBIVORE', (SELECT period_id FROM periods WHERE LOWER(period_name) = 'triassic'), 'https://cdn.vox-cdn.com/thumbor/WR9hE8wvdM4hfHysXitls9_bCZI=/0x0:1192x795/1400x1400/filters:focal(596x398:597x399)/cdn.vox-cdn.com/uploads/chorus_asset/file/22312759/rickroll_4k.jpg');


INSERT INTO digsites (digsite_name, country, latitude, longitude, first_excavation_date) VALUES ('Willow Creek Formation', 'Canada', 49.8, -113.4, DATE '1861-05-01');
INSERT INTO digsites (digsite_name, country, latitude, longitude, first_excavation_date) VALUES ('Hell Creek Formation', 'USA', 46.9, -101.5, DATE '1861-09-30');
INSERT INTO digsites (digsite_name, country, latitude, longitude, first_excavation_date) VALUES ('Bernissart Mine', 'Belgium', 50.5, 3.6, DATE '1902-01-30');
INSERT INTO digsites (digsite_name, country, latitude, longitude, first_excavation_date) VALUES ('Blumenberg Quarry', 'Germany', 49.0, 11.2, DATE '1889-04-26');
INSERT INTO digsites (digsite_name, country, latitude, longitude, first_excavation_date) VALUES ('Cerro Barcino Formation', 'Argentina', -43.8, -68.6, DATE '1902-6-14');
INSERT INTO digsites (digsite_name, country, latitude, longitude, first_excavation_date) VALUES ('La Sagnette', 'Belgium', 49.7, 5.7, DATE '2008-09-23');
INSERT INTO digsites (digsite_name, country, latitude, longitude, first_excavation_date) VALUES ('Morrison Formation', 'USA', 39.7, -105.2, DATE '1877-04-10');
INSERT INTO digsites (digsite_name, country, latitude, longitude, first_excavation_date) VALUES ('Casal Novo', 'Portugal', 39.7, 8.8, DATE '1999-10-03');


INSERT INTO discoveries (species_id, digsite_id, date_discovered) VALUES ((SELECT species_id FROM species WHERE LOWER(species_name)='triceratops'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'Hell Creek Formation'), DATE '1861-09-30');
INSERT INTO discoveries (species_id, digsite_id, date_discovered) VALUES ((SELECT species_id FROM species WHERE LOWER(species_name)='t. rex'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'Hell Creek Formation'), DATE '1861-09-30');
INSERT INTO discoveries (species_id, digsite_id, date_discovered) VALUES ((SELECT species_id FROM species WHERE LOWER(species_name)='t. rex'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'Willow Creek Formation'), DATE '1861-05-01');
INSERT INTO discoveries (species_id, digsite_id, date_discovered) VALUES ((SELECT species_id FROM species WHERE LOWER(species_name)='iguanodon'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'Bernissart Mine'), DATE '1902-01-30');
INSERT INTO discoveries (species_id, digsite_id, date_discovered) VALUES ((SELECT species_id FROM species WHERE LOWER(species_name)='ankylosaurus'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'Hell Creek Formation'), DATE '1861-09-30');
INSERT INTO discoveries (species_id, digsite_id, date_discovered) VALUES ((SELECT species_id FROM species WHERE LOWER(species_name)='ankylosaurus'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'Morrison Formation'), DATE '1877-04-10');
INSERT INTO discoveries (species_id, digsite_id, date_discovered) VALUES ((SELECT species_id FROM species WHERE LOWER(species_name)='archeopteryx'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'Blumenberg Quarry'), DATE '1889-04-26');
INSERT INTO discoveries (species_id, digsite_id, date_discovered) VALUES ((SELECT species_id FROM species WHERE LOWER(species_name)='sauropod'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'Cerro Barcino Formation'), DATE '1902-6-14');
INSERT INTO discoveries (species_id, digsite_id, date_discovered) VALUES ((SELECT species_id FROM species WHERE LOWER(species_name)='plesiosaur'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'La Sagnette'), DATE '2008-09-23');
INSERT INTO discoveries (species_id, digsite_id, date_discovered) VALUES ((SELECT species_id FROM species WHERE LOWER(species_name)='stegosaur'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'Morrison Formation'), DATE '1877-04-10' );
INSERT INTO discoveries (species_id, digsite_id, date_discovered) VALUES ((SELECT species_id FROM species WHERE LOWER(species_name)='stegosaur'), (SELECT digsite_id FROM digsites WHERE digsite_name = 'Casal Novo'), DATE '1999-10-03');
