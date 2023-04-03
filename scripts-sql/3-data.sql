
-- Supprime toutes les données

DELETE FROM service;
DELETE FROM agir;
DELETE FROM abonner;
DELETE FROM memo;
DELETE FROM telephone;
DELETE FROM personne;
DELETE FROM categorie;
DELETE FROM role;
DELETE FROM compte;


-- Compte

INSERT INTO compte (idcompte, pseudo, motdepasse, email ) VALUES 
( 1, 'geek', 'geek', 'geek@jfox.fr' ),
( 2, 'chef', 'chef', 'chef@jfox.fr' ),
( 3, 'job', 'job', 'job@jfox.fr' ),
( 4, 'max', 'max', 'max@jfox.fr' ),
( 5, 'mika', 'mika', 'mika@jfox.fr' ),
( 6, 'tom', 'tom', 'tom@jfox.fr' ),
( 7, 'eva', 'eva', 'eva@jfox.fr' ),
( 8, 'lulu', 'lulu', 'lulu@jfox.fr' );

ALTER TABLE compte ALTER COLUMN idcompte RESTART WITH 9;


-- Role

INSERT INTO role (idcompte, role) VALUES 
( 1, 'ADMINISTRATEUR' ),
( 1, 'UTILISATEUR' ),
( 2, 'UTILISATEUR' ),
( 3, 'UTILISATEUR' ),
( 4, 'UTILISATEUR' ),
( 5, 'UTILISATEUR' ),
( 6, 'UTILISATEUR' ),
( 7, 'UTILISATEUR' ),
( 8, 'UTILISATEUR' );


-- Categorie
  
INSERT INTO categorie (idcategorie, libelle, debut ) VALUES 
(  1, 'Employés', {d '2017-06-18' } ),
(  2, 'Partenaires', {d '2020-04-05' } ),
(  3, 'Clients', {d '2015-08-24' } ),
(  4, 'Fournisseurs', {d '2020-12-10' } ),
(  5, 'Sous-traitants', {d '2012-08-14' } ),
(  6, 'Avocats', {d '2020-10-16' } ),
(  7, 'Administrations', {d '2016-12-11' } ),
(  8, 'Actionnaires', {d '2020-04-20' } ),
(  9, 'Banques', {d '2018-07-27' } ),
( 10, 'Dirigeants', {d '2013-03-05' } );

ALTER TABLE categorie ALTER COLUMN idcategorie RESTART WITH 11;


-- Personne

INSERT INTO personne (idpersonne, idcategorie, nom, prenom) VALUES 
(  1,  1, 'Longuet', 'Gérard' ),
(  2,  3, 'Kanner', 'Patrick' ),
(  3,  7, 'Houllegatte', 'Jean-Michel' ),
(  4,  1, 'Husson', 'Jean-François' ),
(  5,  6, 'Segouin', 'Vincent' ),
(  6,  3, 'Roux', 'Jean-Yves' ),
(  7,  3, 'Savin', 'Michel' ),
(  8,  4, 'Chauvet', 'Patrick' ),
(  9,  8, 'Duplomb', 'Laurent' ),
( 10,  2, 'Gremillet', 'Daniel' ),
( 11,  1, 'Jacquin', 'Olivier' ),
( 12,  4, 'Menonville', 'Franck' ),
( 13,  5, 'Tissot', 'Jean-Claude' ),
( 14,  2, 'Schillinger', 'Patricia' ),
( 15,  9, 'Dagbert', 'Michel' ),
( 16,  5, 'Louault', 'Pierre' ),
( 17,  5, 'Janssens', 'Jean-Marie' ),
( 18,  3, 'Wattebled', 'Dany' ),
( 19,  5, 'Le Nay', 'Jacques' ),
( 20,  4, 'Moga', 'Jean-Pierre' ),
( 21,  9, 'Bigot', 'Joël' ),
( 22,  7, 'Hassani', 'Abdallah' ),
( 23,  1, 'Prince', 'Jean-Paul' ),
( 24,  8, 'Cazabonne', 'Alain' ),
( 25,  1, 'Dennemont', 'Michel' ),
( 26,  5, 'Boyer', 'Jean-Marc' ),
( 27,  1, 'Decool', 'Jean-Pierre' ),
( 28,  3, 'Préville', 'Angèle' ),
( 29,  7, 'Eustache-Brinio', 'Jacqueline' ),
( 30,  9, 'Chauvin', 'Marie-Christine' ),
( 31,  4, 'Filleul', 'Martine' ),
( 32,  5, 'de Cidrac', 'Marta' ),
( 33,  4, 'Gontard', 'Guillaume' ),
( 34,  4, 'Ract-Madoux', 'Daphné' ),
( 35,  3, 'Guidez', 'Jocelyne' ),
( 36,  8, 'Allizard', 'Pascal' ),
( 37,  4, 'Labbé', 'Joël' ),
( 38,  2, 'Micouleau', 'Brigitte' ),
( 39,  5, 'Van Heghe', 'Sabine' ),
( 40,  1, 'Raimond-Pavero', 'Isabelle' ),
( 41,  5, 'Létard', 'Valérie' ),
( 42,  7, 'Panunzi', 'Jean-Jacques' ),
( 43,  5, 'Bascher', 'Jérôme' ),
( 44,  8, 'Iacovelli', 'Xavier' ),
( 45,  5, 'Longeot', 'Jean-François' ),
( 46,  7, 'Bonnefoy', 'Nicole' ),
( 47,  9, 'Noël', 'Sylviane' ),
( 48,  8, 'Apourceau-Poly', 'Cathy' ),
( 49,  4, 'Brulin', 'Céline' ),
( 50,  4, 'Lopez', 'Vivette' );

ALTER TABLE personne ALTER COLUMN idpersonne RESTART WITH 51;


-- Telephone

INSERT INTO telephone (idtelephone, idpersonne, libelle, numero ) VALUES 
(   1,  1, 'Bureau', '01.68.04.38.07' ),
(   2,  1, 'Portable', '06.94.14.22.53' ),
(   3,  2, 'Bureau', '02.18.81.29.61' ),
(   4,  2, 'Secrétaire', '02.18.25.24.35' ),
(   5,  2, 'Permanence', '02.18.06.76.56' ),
(   6,  2, 'Portable', '06.09.27.99.01' ),
(   7,  2, 'Portable', '06.09.68.82.37' ),
(   8,  3, 'Bureau', '01.98.16.88.23' ),
(   9,  3, 'Permanence', '01.98.08.83.81' ),
(  10,  3, 'Portable', '06.35.98.81.66' ),
(  11,  3, 'Portable', '06.35.95.19.22' ),
(  12,  4, 'Secrétaire', '04.37.51.03.91' ),
(  13,  4, 'Permanence', '04.37.23.78.68' ),
(  14,  4, 'Portable', '06.40.19.93.09' ),
(  15,  4, 'Portable', '06.40.10.45.98' ),
(  16,  5, 'Bureau', '03.21.23.36.01' ),
(  17,  5, 'Portable', '06.56.69.67.31' ),
(  18,  5, 'Portable', '06.56.65.92.25' ),
(  19,  5, 'Portable', '06.56.30.66.06' ),
(  20,  6, 'Secrétaire', '01.12.74.03.23' ),
(  21,  6, 'Permanence', '01.12.35.78.93' ),
(  22,  6, 'Portable', '06.01.12.30.18' ),
(  23,  8, 'Portable', '06.49.27.85.67' ),
(  24,  9, 'Secrétaire', '04.02.76.27.22' ),
(  25,  9, 'Permanence', '04.02.46.09.91' ),
(  26,  9, 'Portable', '06.87.66.50.92' ),
(  27, 10, 'Bureau', '05.64.32.85.70' ),
(  28, 10, 'Secrétaire', '05.64.62.98.03' ),
(  29, 10, 'Portable', '06.85.06.77.59' ),
(  30, 10, 'Portable', '06.85.67.31.63' ),
(  31, 10, 'Portable', '06.85.61.32.79' ),
(  32, 11, 'Secrétaire', '04.68.32.79.21' ),
(  33, 11, 'Permanence', '04.68.75.14.71' ),
(  34, 11, 'Portable', '06.05.75.38.53' ),
(  35, 12, 'Bureau', '03.79.81.84.07' ),
(  36, 12, 'Secrétaire', '03.79.71.20.82' ),
(  37, 12, 'Portable', '06.59.26.80.20' ),
(  38, 12, 'Portable', '06.59.84.09.26' ),
(  39, 14, 'Secrétaire', '01.32.36.99.66' ),
(  40, 14, 'Permanence', '01.32.86.71.75' ),
(  41, 14, 'Portable', '06.49.69.44.78' ),
(  42, 14, 'Portable', '06.49.77.29.77' ),
(  43, 15, 'Secrétaire', '04.55.87.52.55' ),
(  44, 15, 'Permanence', '04.55.01.67.76' ),
(  45, 15, 'Portable', '06.34.71.43.49' ),
(  46, 15, 'Portable', '06.34.36.39.20' ),
(  47, 16, 'Secrétaire', '05.53.30.82.13' ),
(  48, 16, 'Permanence', '05.53.65.86.03' ),
(  49, 16, 'Portable', '06.95.72.73.20' ),
(  50, 16, 'Portable', '06.95.76.12.35' ),
(  51, 16, 'Portable', '06.95.41.22.48' ),
(  52, 17, 'Bureau', '02.11.66.41.53' ),
(  53, 17, 'Secrétaire', '02.11.64.93.94' ),
(  54, 17, 'Portable', '06.36.15.31.51' ),
(  55, 17, 'Portable', '06.36.21.40.30' ),
(  56, 18, 'Bureau', '04.72.93.76.46' ),
(  57, 18, 'Secrétaire', '04.72.23.20.91' ),
(  58, 18, 'Permanence', '04.72.12.42.07' ),
(  59, 18, 'Portable', '06.23.84.62.20' ),
(  60, 18, 'Portable', '06.23.40.69.39' ),
(  61, 19, 'Bureau', '03.01.39.49.74' ),
(  62, 19, 'Secrétaire', '03.01.63.20.54' ),
(  63, 19, 'Portable', '06.67.57.38.58' ),
(  64, 20, 'Portable', '06.03.78.61.67' ),
(  65, 21, 'Permanence', '03.28.03.37.33' ),
(  66, 21, 'Portable', '06.13.68.61.63' ),
(  67, 21, 'Portable', '06.13.14.22.72' ),
(  68, 22, 'Bureau', '04.35.42.30.21' ),
(  69, 22, 'Secrétaire', '04.35.81.66.85' ),
(  70, 22, 'Permanence', '04.35.68.82.99' ),
(  71, 22, 'Portable', '06.76.27.57.66' ),
(  72, 22, 'Portable', '06.76.05.59.37' ),
(  73, 23, 'Bureau', '02.03.77.47.00' ),
(  74, 23, 'Secrétaire', '02.03.21.60.89' ),
(  75, 23, 'Permanence', '02.03.14.62.77' ),
(  76, 23, 'Portable', '06.60.49.05.97' ),
(  77, 23, 'Portable', '06.60.20.88.01' ),
(  78, 24, 'Bureau', '02.80.46.18.84' ),
(  79, 24, 'Secrétaire', '02.80.55.67.44' ),
(  80, 24, 'Permanence', '02.80.83.25.96' ),
(  81, 24, 'Portable', '06.00.56.28.84' ),
(  82, 24, 'Portable', '06.00.22.00.89' ),
(  83, 25, 'Bureau', '02.08.73.35.68' ),
(  84, 25, 'Secrétaire', '02.08.05.26.50' ),
(  85, 25, 'Portable', '06.82.53.37.33' ),
(  86, 26, 'Secrétaire', '02.18.03.73.63' ),
(  87, 26, 'Portable', '06.62.99.85.92' ),
(  88, 27, 'Bureau', '05.24.96.31.03' ),
(  89, 27, 'Permanence', '05.24.24.40.28' ),
(  90, 27, 'Portable', '06.02.39.61.43' ),
(  91, 27, 'Portable', '06.02.17.95.68' ),
(  92, 27, 'Portable', '06.02.14.14.62' ),
(  93, 29, 'Bureau', '03.35.29.49.07' ),
(  94, 29, 'Portable', '06.83.75.49.42' ),
(  95, 29, 'Portable', '06.83.19.25.19' ),
(  96, 29, 'Portable', '06.83.42.27.50' ),
(  97, 30, 'Bureau', '05.20.45.86.35' ),
(  98, 30, 'Secrétaire', '05.20.31.21.90' ),
(  99, 30, 'Permanence', '05.20.35.70.39' ),
( 100, 31, 'Bureau', '05.29.44.41.01' ),
( 101, 31, 'Portable', '06.20.58.13.99' ),
( 102, 31, 'Portable', '06.20.79.43.77' ),
( 103, 34, 'Bureau', '02.43.63.20.38' ),
( 104, 34, 'Secrétaire', '02.43.19.77.47' ),
( 105, 34, 'Permanence', '02.43.27.07.90' ),
( 106, 34, 'Portable', '06.18.46.23.55' ),
( 107, 34, 'Portable', '06.18.12.74.41' ),
( 108, 34, 'Portable', '06.18.07.42.77' ),
( 109, 35, 'Bureau', '02.48.76.57.69' ),
( 110, 35, 'Secrétaire', '02.48.16.31.48' ),
( 111, 35, 'Permanence', '02.48.91.75.43' ),
( 112, 35, 'Portable', '06.46.60.46.29' ),
( 113, 35, 'Portable', '06.46.74.62.01' ),
( 114, 36, 'Portable', '06.88.49.20.92' ),
( 115, 36, 'Portable', '06.88.19.07.71' ),
( 116, 36, 'Portable', '06.88.88.79.60' ),
( 117, 37, 'Bureau', '02.69.51.28.14' ),
( 118, 37, 'Secrétaire', '02.69.39.84.98' ),
( 119, 37, 'Permanence', '02.69.27.21.69' ),
( 120, 37, 'Portable', '06.15.33.53.21' ),
( 121, 37, 'Portable', '06.15.07.68.10' ),
( 122, 38, 'Secrétaire', '02.75.10.11.04' ),
( 123, 38, 'Portable', '06.54.75.90.08' ),
( 124, 39, 'Bureau', '03.14.19.52.98' ),
( 125, 39, 'Permanence', '03.14.77.39.62' ),
( 126, 39, 'Portable', '06.23.42.30.06' ),
( 127, 40, 'Bureau', '01.17.74.59.70' ),
( 128, 40, 'Secrétaire', '01.17.12.24.92' ),
( 129, 40, 'Permanence', '01.17.82.32.63' ),
( 130, 40, 'Portable', '06.52.80.59.59' ),
( 131, 41, 'Secrétaire', '04.74.29.80.43' ),
( 132, 42, 'Bureau', '05.31.18.65.84' ),
( 133, 42, 'Secrétaire', '05.31.04.55.13' ),
( 134, 42, 'Permanence', '05.31.41.90.63' ),
( 135, 42, 'Portable', '06.74.13.85.49' ),
( 136, 44, 'Bureau', '03.90.76.55.13' ),
( 137, 44, 'Portable', '06.48.89.94.87' ),
( 138, 44, 'Portable', '06.48.00.40.38' ),
( 139, 44, 'Portable', '06.48.62.47.38' ),
( 140, 45, 'Bureau', '02.31.59.28.65' ),
( 141, 45, 'Permanence', '02.31.59.62.54' ),
( 142, 45, 'Portable', '06.97.30.85.82' ),
( 143, 45, 'Portable', '06.97.93.41.19' ),
( 144, 46, 'Bureau', '04.95.09.94.21' ),
( 145, 46, 'Permanence', '04.95.90.67.91' ),
( 146, 48, 'Bureau', '02.11.15.32.40' ),
( 147, 48, 'Permanence', '02.11.17.96.61' ),
( 148, 48, 'Portable', '06.59.33.72.17' ),
( 149, 48, 'Portable', '06.59.32.11.70' ),
( 150, 48, 'Portable', '06.59.61.57.87' ),
( 151, 49, 'Bureau', '05.58.57.43.52' ),
( 152, 49, 'Permanence', '05.58.81.90.17' ),
( 153, 49, 'Portable', '06.25.90.41.10' ),
( 154, 50, 'Portable', '06.02.59.65.89' ),
( 155, 50, 'Portable', '06.02.69.37.23' );

ALTER TABLE telephone ALTER COLUMN idtelephone RESTART WITH 200;


-- mémo

INSERT INTO memo (idmemo, titre, description, flagurgent, idcategorie, statut, effectif, budget, echeance, heure ) VALUES 
( 1, 'Vidéos à regarder', E'Apprnedre Java #14\nTutoriel JavaFX\nPostgreSQL pour les débutants', TRUE, 1 , 'F', 2, 1234.56, {d '2022-02-25' }, {t '18:30' } ),
( 2, 'Recette de l''omelette', E'Casser des oeufs\nBien mélanger\nFaire cuire à la poêle', FALSE, 1, 'E', 4, 5000.00, {d '2022-05-18' }, {t '09:15' } ),
( 3, 'Acheter un réveil', NULL, TRUE, NULL, 'A',   NULL,   NULL,   NULL, NULL ),
( 4, 'Réserver Hôtel à Paris', 'A proximité de la tour Eiffel', TRUE, 1 , 'F', 2, 1234.56, {d '2022-02-25' }, {t '18:30' } ),
( 5, 'Nettoyer la moquette', 'Dans  la chambre et dansle couloir', FALSE, 1, 'E', 4, 5000.00, {d '2022-05-18' }, {t '09:15' } ),
( 6, 'Répondre  au mail de Martine', NULL, TRUE, NULL, 'A',   NULL,   NULL,   NULL, NULL );

ALTER TABLE memo ALTER COLUMN idmemo RESTART WITH 7;


-- abonner

INSERT INTO abonner (idmemo, idCompte) VALUES 
( 1, 1 ),
( 1, 2 ),
( 1, 3 ),
( 1, 4 ),
( 2, 4 ),
( 2, 5 ),
( 4, 6 ),
( 4, 1 ),
( 4, 2 ),
( 4, 3 ),
( 5, 3 ),
( 5, 4 );


-- agir

INSERT INTO agir (idmemo, idpersonne, fonction, debut) VALUES 
( 1, 1, 'pilote', {d '2022-01-01' } ),
( 1, 2, 'secrétaire', {d '2022-01-06' } ),
( 1, 3, 'trésorier', {d '2022-01-15' } ),
( 2, 4, NULL, NULL ),
( 2, 5, 'pilote', NULL ),
( 4, 6, 'pilote', {d '2022-01-01' } ),
( 4, 7, 'secrétaire', {d '2022-01-06' } ),
( 4, 8, 'trésorier', {d '2022-01-15' } ),
( 5, 9, NULL, NULL ),
( 6, 10, 'pilote', NULL );


-- Service

INSERT INTO service ( idservice, nom, anneecreation, flagsiege, idpersonne ) VALUES 
( 1, 'Direction', 2007, TRUE, 1 ),
( 2, 'Comptabilité', NULL, TRUE, 2 ),
( 3, 'Agence Limoges', 2008, FALSE, 3 ),
( 4, 'Agence Brive', 2014, FALSE, NULL );

ALTER TABLE service ALTER COLUMN idservice RESTART WITH 5;
