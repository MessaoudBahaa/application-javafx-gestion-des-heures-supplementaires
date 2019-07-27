


////////////// afficher la somme des heures par mois
SELECT year(dateS) as annee , monthname(dateS) as mois, SUM(HTot) as totalParMois
FROM lignSup
GROUP BY YEAR(dateS), monthname(dateS);



/////////////// afficher la somme des heures d un mois XXXX et une annee YYYY
Select * from (SELECT year(dateS) as annee , monthname(dateS) as mois, SUM(HTot) as totalParMois
FROM lignSup
GROUP BY YEAR(dateS), monthname(dateS) )as T
 where annee=2019 and mois='April' ;