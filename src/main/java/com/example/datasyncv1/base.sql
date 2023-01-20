
create sequence sqtokenadmin;
create sequence sqtokenuser;

create table Utilisateur(
idUtilisateur serial primary key,
nom varchar(20),
prenom varchar(20),
email varchar(20),
mdp varchar(20),
DateInscription date default CURRENT_DATE,
compte float default 0
);


create table Admin(
idAdmin serial primary key,    
email varchar(20),
mdp varchar(20)
);

create table RechargementCompte(
idRechargementCompte serial primary key,    
idUtilisateur int references Utilisateur(idUtilisateur),
montant float,
DateHeureRechargement TIMESTAMP default CURRENT_TIMESTAMP ,
estValider int default 0
);

create table CategorieProduit(
idCategorieProduit serial primary key,
typeCategorie varchar(20)
);
INSERT INTO CategorieProduit(typeCategorie) VALUES('Oeuvre d''arts'),('Bijoux'),('Objet de collection'),('Livre');


create table Produit(
idProduit serial primary key,
nomProduit varchar(50),
description text,
prix float,
numero_serie varchar(30),
DateSortie date,
Etat varchar(10),
Provenance varchar(10),
idCategorieProduit int REFERENCES CategorieProduit(idCategorieProduit)
);

INSERT INTO Produit(nomProduit,description,prix,numero_serie,DateSortie,Etat,PRovenance,idCategorieProduit)
VALUES('Collier en Or massif','Collier d''une grande valeur.',600000.0,'C19204OR',DATE '2022-10-10','Bon Etat','Egypte',2);
INSERT INTO Produit(nomProduit,description,prix,numero_serie,DateSortie,Etat,PRovenance,idCategorieProduit)
VALUES('Vase d''outre tombe','Vase de l''Egypte antique d''une grande valeur.',1000000.0,'C19214VS',DATE '2022-12-12','Bon Etat','Egypte',1);


create table Enchere(
idEnchere serial primary key,
idUtilisateur int references Utilisateur(idUtilisateur),
description text,
prixMinimumVente float,
durreEnchere int,
DateHeureEnchere TIMESTAMP default CURRENT_TIMESTAMP,
status int default 0
);


create table Photo_Produit(
idProduit int references Produit(idProduit),
photo text
);


create table Produit_Enchere(
idEnchere int references Enchere(idEnchere),
idProduit int references Produit(idProduit)
);


create table HistoriqueOffre(
idHistoriqueOffre serial primary key,
idEnchere int references Enchere(idEnchere),
idUtilisateur int references Utilisateur(idUtilisateur),
montant_offre float,
DateHeureOffre TIMESTAMP default CURRENT_TIMESTAMP
);


create table ResultatEnchere(
idResultatEnchere serial primary key,
idEnchere int references Enchere(idEnchere),
idUtilisateur int references Utilisateur(idUtilisateur),
prix_gagnant float,
DateHeureGagnat TIMESTAMP default CURRENT_TIMESTAMP
);

create table PourcentagePrelevee(
pourcentage float
);


create table PrelevementEnchere(
idPrelevement serial primary key,
idEnchere int references Enchere(idEnchere),
montant float,
DatePrelevement DATE default CURRENT_DATE
);



create table tokenAdmin(
idtokenadmin varchar(10) primary key not null default 'tokena'||nextval('sqtokenadmin'),
idadmin int references Admin(idAdmin),
token varchar(100),
datecreation date,
dateexpiration date,
role varchar(10)
);


create table tokenUser(
idtokenuser varchar(10) primary key not null default 'tokena'||nextval('sqtokenuser'),
idUtilisateur int references Utilisateur(idUtilisateur),    
token varchar(100),
datecreation date,
dateexpiration date,
role varchar(10)
);



create or replace view categorieProduitVendu as  
WITH all_categories AS (SELECT idCategorieProduit FROM CategorieProduit)
SELECT cp2.idCategorieProduit, cp2.typeCategorie , COUNT(re.idEnchere) as total_produit_vendu
FROM all_categories cp
LEFT JOIN Produit p 
using(idCategorieProduit)
LEFT JOIN Produit_Enchere pe 
using(idProduit)
LEFT JOIN Enchere e 
using(idEnchere)
LEFT JOIN ResultatEnchere re 
ON re.idEnchere = e.idEnchere AND re.idEnchere = pe.idEnchere
LEFT JOIN CategorieProduit cp2
ON cp2.idCategorieProduit = cp.idCategorieProduit
GROUP BY cp2.idCategorieProduit,cp2.typeCategorie order by COUNT(re.idEnchere) desc;


create or replace view StatClient as
WITH all_utilisateurs AS ( SELECT idUtilisateur FROM utilisateur)
SELECT cp2.nom ,cp2.prenom , cp2.idUtilisateur , COUNT(e.idUtilisateur) as nombre_produit_vendu
FROM all_utilisateurs cp
LEFT JOIN Enchere e 
using(idUtilisateur)
LEFT JOIN utilisateur cp2
ON cp2.idUtilisateur = cp.idUtilisateur
GROUP BY cp2.idUtilisateur order by COUNT(e.idUtilisateur) desc;

-----view-----



create or replace view ProduitCategorie as
select p.idProduit , p.nomProduit , p.description , p.prix , p.numero_serie , p.DateSortie , p.Etat , p.Provenance , pp.photo , c.idCategorieProduit , c.typeCategorie  from Produit p inner join CategorieProduit c using(idCategorieProduit) inner join Photo_Produit pp on p.idProduit = pp.idProduit;


create or replace view v_total_membre as
select count(idUtilisateur) as nombre , extract(year from DateInscription) as annee , extract(month from DateInscription) as mois, extract(day from DateInscription) as jour from Utilisateur group by
extract(year from DateInscription) , extract(month from DateInscription) , extract(day from DateInscription);


INSERT INTO Utilisateur (nom, prenom, email, mdp) VALUES ('John', 'Doe', 'john.doe@example.com', 'password123');
INSERT INTO Utilisateur (nom, prenom, email, mdp) VALUES ('Jane', 'Smith', 'jane@example.com', 'password456');



--- chiffre d'affaire par annee , mois ----
create or replace view chiffreAffaireMoisAnnee as
WITH months(month, year) AS (SELECT generate_series(1, 12), extract(year from current_date))SELECT months.month, months.year, coalesce(SUM(pe.montant),0) as montant FROM months LEFT JOIN PrelevementEnchere pe ON extract(month from pe.DatePrelevement) = months.month AND extract(year from pe.DatePrelevement) = months.year GROUP BY months.month, months.year;

INSERT INTO Enchere (idUtilisateur, description, prixMinimumVente,durreEnchere)
VALUES (1, 'Enchere pour un iPhone', 700,30);
INSERT INTO Enchere (idUtilisateur, description, prixMinimumVente,durreEnchere)
VALUES (2, 'Enchere pour une chemise',300,40);

INSERT INTO HistoriqueOffre (idEnchere, idUtilisateur, montant_offre) VALUES (1, 1, 750);
INSERT INTO HistoriqueOffre (idEnchere, idUtilisateur, montant_offre) VALUES (1, 2, 800);
INSERT INTO HistoriqueOffre (idEnchere, idUtilisateur, montant_offre) VALUES (2, 2,600);
INSERT INTO HistoriqueOffre (idEnchere, idUtilisateur, montant_offre) VALUES (2, 1,900);


INSERT INTO Admin (email, mdp) VALUES ('admin@example.com', 'adminpassword');






INSERT INTO ResultatEnchere (idEnchere, idUtilisateur, prix_gagnant) VALUES (1, 1, 800);
INSERT INTO ResultatEnchere (idEnchere, idUtilisateur, prix_gagnant) VALUES (2, 2, 40);


insert into RechargementCompte(idUtilisateur,montant) values (1,300) , (1,400) ;


INSERT INTO Produit_Enchere (idEnchere, idProduit) VALUES (1, 1);
INSERT INTO Produit_Enchere (idEnchere, idProduit) VALUES (2, 2);
--INSERT INTO Produit_Enchere (idEnchere, idProduit) VALUES (2, 5);