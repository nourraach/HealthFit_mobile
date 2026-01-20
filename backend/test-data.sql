-- Script SQL pour insérer des données de test
-- Base de données: BDMobile

-- 1. Insérer des programmes de test
INSERT INTO programmes (nom, description, duree_jours, objectif, image_url) VALUES
('Programme Perte de Poids 30 jours', 'Programme complet pour perdre du poids sainement en 30 jours', 30, 'PERTE_POIDS', 'https://example.com/perte-poids.jpg'),
('Programme Prise de Masse 60 jours', 'Programme intensif pour gagner de la masse musculaire', 60, 'PRISE_MASSE', 'https://example.com/prise-masse.jpg'),
('Programme Maintien 21 jours', 'Programme pour maintenir votre forme actuelle', 21, 'MAINTIEN', 'https://example.com/maintien.jpg');

-- 2. Insérer des plats de test
INSERT INTO plats (nom, description, calories, categorie, temps_preparation, image_url) VALUES
-- Petit-déjeuner
('Omelette aux légumes', 'Omelette avec poivrons, tomates et oignons', 250, 'PETIT_DEJEUNER', 15, 'https://example.com/omelette.jpg'),
('Porridge aux fruits', 'Flocons d''avoine avec fruits frais et miel', 300, 'PETIT_DEJEUNER', 10, 'https://example.com/porridge.jpg'),
('Smoothie protéiné', 'Smoothie banane, épinards et protéines', 200, 'PETIT_DEJEUNER', 5, 'https://example.com/smoothie.jpg'),

-- Déjeuner
('Salade César au poulet', 'Salade verte avec poulet grillé et parmesan', 400, 'DEJEUNER', 20, 'https://example.com/cesar.jpg'),
('Quinoa aux légumes', 'Quinoa avec légumes grillés et feta', 350, 'DEJEUNER', 25, 'https://example.com/quinoa.jpg'),
('Wrap au thon', 'Wrap complet avec thon, avocat et crudités', 380, 'DEJEUNER', 15, 'https://example.com/wrap.jpg'),

-- Dîner
('Saumon grillé', 'Saumon avec brocolis et riz complet', 450, 'DINER', 30, 'https://example.com/saumon.jpg'),
('Poulet rôti', 'Poulet avec patates douces et haricots verts', 420, 'DINER', 40, 'https://example.com/poulet.jpg'),
('Steak de boeuf', 'Steak avec légumes vapeur', 480, 'DINER', 25, 'https://example.com/steak.jpg'),

-- Collations
('Yaourt grec', 'Yaourt grec nature avec noix', 150, 'COLLATION', 2, 'https://example.com/yaourt.jpg'),
('Fruits secs', 'Mélange de fruits secs et amandes', 180, 'COLLATION', 1, 'https://example.com/fruits-secs.jpg'),
('Barre protéinée', 'Barre protéinée maison', 200, 'COLLATION', 1, 'https://example.com/barre.jpg');

-- 3. Insérer des ingrédients pour les plats
INSERT INTO plat_ingredients (plat_id, ingredient) VALUES
-- Omelette
(1, '3 oeufs'),
(1, '1 poivron rouge'),
(1, '2 tomates'),
(1, '1 oignon'),
(1, 'Sel et poivre'),

-- Porridge
(2, '50g flocons d''avoine'),
(2, '200ml lait d''amande'),
(2, '1 banane'),
(2, 'Fruits rouges'),
(2, '1 cuillère miel'),

-- Smoothie
(3, '1 banane'),
(3, 'Poignée d''épinards'),
(3, '30g protéine whey'),
(3, '200ml lait'),
(3, 'Glaçons');

-- 4. Insérer des activités sportives
INSERT INTO activites_sportives (nom, description, duree_minutes, calories_brulees, niveau, type, image_url) VALUES
-- Cardio
('Course à pied débutant', 'Course légère pour débutants', 30, 300, 'DEBUTANT', 'CARDIO', 'https://example.com/course.jpg'),
('Course à pied intermédiaire', 'Course modérée avec intervalles', 45, 450, 'INTERMEDIAIRE', 'CARDIO', 'https://example.com/course-inter.jpg'),
('Vélo d''appartement', 'Séance de vélo en salle', 40, 350, 'DEBUTANT', 'CARDIO', 'https://example.com/velo.jpg'),

-- Musculation
('Pompes et abdos', 'Circuit de renforcement musculaire', 20, 200, 'DEBUTANT', 'MUSCULATION', 'https://example.com/pompes.jpg'),
('Full body workout', 'Entraînement complet du corps', 45, 400, 'INTERMEDIAIRE', 'MUSCULATION', 'https://example.com/fullbody.jpg'),
('Haltères avancé', 'Séance intensive avec haltères', 60, 500, 'AVANCE', 'MUSCULATION', 'https://example.com/halteres.jpg'),

-- Yoga
('Yoga débutant', 'Séance de yoga pour débutants', 30, 120, 'DEBUTANT', 'YOGA', 'https://example.com/yoga.jpg'),
('Yoga intermédiaire', 'Yoga avec postures avancées', 45, 180, 'INTERMEDIAIRE', 'YOGA', 'https://example.com/yoga-inter.jpg'),

-- HIIT
('HIIT débutant', 'Entraînement fractionné haute intensité', 20, 250, 'DEBUTANT', 'HIIT', 'https://example.com/hiit.jpg'),
('HIIT avancé', 'HIIT intensif pour sportifs confirmés', 30, 400, 'AVANCE', 'HIIT', 'https://example.com/hiit-avance.jpg'),

-- Stretching
('Étirements matinaux', 'Routine d''étirements pour bien commencer', 15, 50, 'DEBUTANT', 'STRETCHING', 'https://example.com/stretch.jpg'),
('Stretching complet', 'Séance complète d''assouplissement', 25, 80, 'INTERMEDIAIRE', 'STRETCHING', 'https://example.com/stretch-complet.jpg');

-- 5. Associer des plats aux programmes
INSERT INTO programme_plats (programme_id, plat_id) VALUES
-- Programme Perte de Poids (ID 1)
(1, 1), (1, 2), (1, 3),  -- Petit-déjeuners
(1, 4), (1, 5), (1, 6),  -- Déjeuners
(1, 7), (1, 8),          -- Dîners
(1, 10), (1, 11),        -- Collations

-- Programme Prise de Masse (ID 2)
(2, 1), (2, 2), (2, 3),
(2, 4), (2, 5), (2, 6),
(2, 7), (2, 8), (2, 9),
(2, 10), (2, 11), (2, 12),

-- Programme Maintien (ID 3)
(3, 1), (3, 2),
(3, 4), (3, 5),
(3, 7), (3, 8),
(3, 10);

-- 6. Associer des activités aux programmes
INSERT INTO programme_activites (programme_id, activite_id) VALUES
-- Programme Perte de Poids (ID 1)
(1, 1), (1, 3), (1, 4), (1, 9),

-- Programme Prise de Masse (ID 2)
(2, 5), (2, 6), (2, 10),

-- Programme Maintien (ID 3)
(3, 1), (3, 4), (3, 7), (3, 11);

-- 7. Ajouter des conseils aux programmes
INSERT INTO programme_conseils (programme_id, conseil) VALUES
-- Programme Perte de Poids
(1, 'Buvez au moins 2 litres d''eau par jour'),
(1, 'Évitez les aliments transformés et sucrés'),
(1, 'Dormez 7-8 heures par nuit'),
(1, 'Pesez-vous une fois par semaine, le matin à jeun'),
(1, 'Privilégiez les légumes à chaque repas'),

-- Programme Prise de Masse
(2, 'Augmentez votre apport calorique progressivement'),
(2, 'Consommez 1.8-2g de protéines par kg de poids'),
(2, 'Entraînez-vous 4-5 fois par semaine'),
(2, 'Reposez-vous suffisamment entre les séances'),
(2, 'Suivez votre progression avec des photos'),

-- Programme Maintien
(3, 'Maintenez une alimentation équilibrée'),
(3, 'Restez actif au quotidien'),
(3, 'Écoutez votre corps'),
(3, 'Variez vos activités physiques');

-- 8. Vérification des données insérées
SELECT 'Programmes insérés:' as info, COUNT(*) as count FROM programmes
UNION ALL
SELECT 'Plats insérés:', COUNT(*) FROM plats
UNION ALL
SELECT 'Activités insérées:', COUNT(*) FROM activites_sportives
UNION ALL
SELECT 'Associations programme-plats:', COUNT(*) FROM programme_plats
UNION ALL
SELECT 'Associations programme-activités:', COUNT(*) FROM programme_activites;
